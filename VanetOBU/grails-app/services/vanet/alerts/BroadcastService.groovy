package vanet.alerts

import static grails.async.Promises.*
import grails.converters.JSON
import grails.transaction.Transactional

import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.codehaus.groovy.grails.web.json.JSONObject
import org.codehaus.groovy.grails.web.binding.DataBindingUtils
import vanet.alert.Alert

@Transactional
class BroadcastService {

	def grailsApplication
	def navigationLogService
	def carService
	def alertService
	final List<AlertWrapper> alertList = new ArrayList<AlertWrapper>()
	final List<String> confirmedInfoCodeList = new ArrayList<String>()
	
	private class AlertWrapper{
		Object obj
		def sendOneMore
	}
	
	/**
	 * Adiciona pacotes na lista de pacotes a serem enviados em broadcast
	 * @param sendObj
	 * @return
	 */
	def sendAlert(Object sendObj, Integer qtd=1, Closure sendOneMore={return false}){
		
		for(int i=0; i<qtd; i++){
			def pct = null
			if(sendOneMore()){
				// Impedindo que alertas contínuos redundantes sejam inseridos na lista
				// Ex: dois alertas de acidente de um mesmo veículo
				if(sendObj.instanceOf(Alert)){
					if(sendObj.messageCode == 2 || sendObj.messageCode == 3){
						pct = alertList.find{(it.obj.messageCode == 2||it.obj.messageCode == 3) && 
							it.obj.carCode == sendObj.carCode && it.sendOneMore()==true}
					}
					i=qtd
				}
			}
			if(!pct){
				alertList.add(new AlertWrapper(obj:sendObj,sendOneMore:sendOneMore))
			}
		}
	}
	
	/**
	 * Envio indefinido de pacotes em broadcast
	 * @param sendObj
	 * @return
	 */
	def sendInfiniteAlert(Object sendObj){
		sendAlert(sendObj,1,{return true})
	}
	
	/**
	 * Envia a informação em broadcast enquanto estiver à uma certa distância da origem da informação (atualmente 2 Km)
	 * @param sendObj
	 * @return
	 */
	def sendAlertWhileNear(Object sendObj){
		if(sendObj.lat && sendObj.lng){
			sendAlert(sendObj,1,{carService.isInArea(sendObj.lat, sendObj.lng)})
		}else{
			throw new Exception("ERROR -> Objeto incompatível com o método broadcastService.sendAlertWhileNear(), não peossui coordenadas!!!")
		}
	}

	/**
	 * Envia log para o RSU	
	 * @param logObj
	 * @return
	 */
	def sendLog(Object logObj){
		if(!isSent(logObj.code)){
			sendAlert(logObj,1,{return isSent(logObj.code)})
		}
	}
	
	/**
	 * Verifica se a informação já foi enviada para uma RSU
	 * @param code
	 * @return
	 */
	def isSent(String code){
		return confirmedInfoCodeList.find{it = code}
	}
	
	/**
	 * Para um alerta infinito de um determinado obj
	 * @param stopObj
	 * @return
	 */
	def stopInfiniteAlert(Object stopObj){
		def pct = alertList.find{it.obj.id == stopObj.id && it.sendOneMore()==true && it.obj.class == stopObj.class}
		pct.sendOneMore = {return false}
	}
	
	/**
	 * Envia um pacote até receber confirmação de um RSU
	 * @param info
	 * @return
	 */
	def sendUntilConfirm(Object info){
		//TODO terminar
		 send(info, true)
	}
	
	/**
	 * Thread que esvazia a lista de pacotes a enviar
	 * @return
	 */
	def alertSender(){
		task{
			println ">>>>>>>> Serviço de envio de alertas Ativado!!!"
			while(true){
				try{
					if(alertList.isEmpty()){
						sleep(1000)
					}else{
						println alertList
						def a = alertList.first()
						// Se for alerta de acidente não espera confirmação
						Boolean confirm = !(a.obj.instanceOf(Alert))
						send(a.obj, confirm)
						if(a.sendOneMore()){
							alertList.remove(0)
							alertList.add(a)
						}else{
							alertList.remove(0)
						}
						// Tempo entre 2 alertas em ms
						sleep(100)
					}
				}catch(Exception e){
					println e.message()
				}
			}
		}
	}
	
	/**
	 * Envio de pacotes broadcast
	 * @param msg
	 * @return
	 */
	def send(Object msg, Boolean confirm=true) {
		//Procurando o servidor UDP utilizando brodcast
		try {
			//Abrindo uma porta qualquer para enviar o pacote
			DatagramSocket c = new DatagramSocket();
			c.setBroadcast(true);

			//msg = msg+" - "+(new Date()).time.toString()
			byte[] sendData = (msg as JSON).toString().getBytes();

			//Tentando 255.255.255.255 primeiro
			try {
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("192.168.188.255"), 8082);
				c.send(sendPacket);
				System.out.println(getClass().getName() + "CLIENT>>> Requisição enviada para: 255.255.255.255 (DEFAULT)");
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Enviando a mensagem por todas interfaces de rede
			Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface networkInterface = interfaces.nextElement();

				if (networkInterface.isLoopback() || !networkInterface.isUp()) {
					continue; // Não transmitir na interface loopback
				}

				for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
					InetAddress broadcast = interfaceAddress.getBroadcast();
					if (broadcast == null) {
						continue;
					}

					// Enviando o pacote broadcast
					try {
						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8082);
						c.send(sendPacket);
					} catch (Exception e) {
					}

					System.out.println(getClass().getName() + "CLIENT>>> Requisição enviada para: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
				}
			}

			System.out.println(getClass().getName() + "CLIENT>>> Requisições enviadas para todas as interfaces, esperando resposta.");

			if(confirm){
			
				// TODO: receber lista de pacotes já recebidos pela RSU
				
				/* Resposta */
				//Esperando pela resposta
				byte[] recvBuf = new byte[15000];
				DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
				c.receive(receivePacket);
	
				//Nós temos uma resposta
				System.out.println(getClass().getName() + "CLIENT>>> Broadcast respondido pelo servidor: " + receivePacket.getAddress().getHostAddress());
	
				//Verificando se a mensagem é correta
				String message = new String(receivePacket.getData()).trim();
				ArrayList otherCodeList = message.replaceAll("\\[","").replaceAll("]","").split(",")
				otherCodeList = (otherCodeList + confirmedInfoCodeList).unique()
				confirmedInfoCodeList.clear() 
				// TODO: Receber uns 50 elementos apenas e juntar com os 50 primeiros, descartar o resto
				confirmedInfoCodeList.addAll(otherCodeList.subList(0,50))
				println(message)
			}
	
			//Fechando a porta
			c.close();
		} catch (IOException ex) {
			println ex
		} catch (Exception ex) {
			println ex
		}
	}
	
	/**
	 * Recebimento de broadcast
	 */
	public void receive() {
		def p = task{
			try {
				//Mantem um canal aberto para ouvir tudo o trafic UDP que é destinado para esta porta
				DatagramSocket socket = new DatagramSocket(8082);
//				socket.setBroadcast(true);

				while (true) {
					try{
						System.out.println(getClass().getName() + "SERVER>>>Pronto para receber pacotes Broadcast!");
	
						//Recebendo um pacote
						byte[] recvBuf = new byte[15000];
						DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
						socket.receive(packet);
	
						//Pacote recebido
						println(getClass().getName() + "SERVER>>>Analisando pacote recebido de: " + packet.getAddress().getHostAddress());
						println(getClass().getName() + "SERVER>>>Pacotes recebidos; dados: " + new String(packet.getData()));
	
						//Vendo se o pacote contém o comando correto (mensagem)
						String message = new String(packet.getData()).trim();
						println("SERVER"+message)
						JSONObject json = new  JSONObject(message)
						if(json."class" == "vanet.alert.Alert"){
							Alert alert = new Alert()
							DataBindingUtils.bindObjectToInstance(alert, json)//,include, exclude, filter)
							alertService.alertReceive(alert)
							sendConfirmation(socket, packet)
						}else if(json."class" == "vanet.automotive.NavigationLog"){
							// TODO: terminar
							navigationLogService.navigationLogReceive()
							sendConfirmation(socket, packet)
						}else if(json."class" == "vanet.automotive.Car"){
							def carInstance = carService.sendToServer()
							sendConfirmation(socket, packet, (carInstance as JSON).toString().getBytes())
						}
					} catch (Exception ex) {
						ex.printStackTrace()
					}
				}
			} catch (IOException ex) {
				ex.printStackTrace()
			}
		}

		p.onError{ Throwable err ->
			println "Ocorreu um erro: ${err.message}";
		}
	}
	
	
	/**
	 * Envio de confirmação de broadcast
	 * @param socket
	 * @param packet
	 * @return
	 */
	def sendConfirmation(DatagramSocket socket, DatagramPacket packet, byte[] sendData=null){
		
		// TODO: Enviar lista de pacotes recebidos pelo RSU 
		
		if(!sendData){
			sendData = ("Alerta Recebido. - "+(new Date()).time.toString()).getBytes();
		}
		
		//Enviando resposta
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
		socket.send(sendPacket);

		System.out.println(getClass().getName() + "SERVER>>>Sent packet to: " + sendPacket.getAddress().getHostAddress());
	}
}
