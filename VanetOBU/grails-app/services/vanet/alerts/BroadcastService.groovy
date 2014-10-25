package vanet.alerts

import java.net.DatagramSocket;
import grails.transaction.Transactional
import static grails.async.Promises.*

@Transactional
class BroadcastService {

	def send(String msg) {
		//Procurando o servidor UDP utilizando brodcast
		try {
			//Abrindo uma porta qualquer para enviar o pacote
			DatagramSocket c = new DatagramSocket();
			c.setBroadcast(true);

			msg = msg+" - "+(new Date()).time.toString()
			byte[] sendData = msg.getBytes();

			//Tentando 255.255.255.255 primeiro
			try {
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), 8888);
				c.send(sendPacket);
				System.out.println(getClass().getName() + "CLIENT>>> Requisição enviada para: 255.255.255.255 (DEFAULT)");
			} catch (Exception e) {
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
						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8888);
						c.send(sendPacket);
					} catch (Exception e) {
					}

					System.out.println(getClass().getName() + "CLIENT>>> Requisição enviada para: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
				}
			}

			System.out.println(getClass().getName() + "CLIENT>>> Requisições enviadas para todas as interfaces, esperando resposta.");

			/* Resposta */
			//Esperando pela resposta
			byte[] recvBuf = new byte[15000];
			DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
			c.receive(receivePacket);

			//Nós temos uma resposta
			System.out.println(getClass().getName() + "CLIENT>>> Broadcast respondido pelo servidor: " + receivePacket.getAddress().getHostAddress());

			//Verificando se a mensagem é correta
			String message = new String(receivePacket.getData()).trim();
			println(message)

			//Fechando a porta
			c.close();
		} catch (IOException ex) {
			Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void receive() {
		def p = task{
			try {
				//Mantem um canal aberto para ouvir tudo o trafic UDP que é destinado para esta porta
				DatagramSocket socket = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
				socket.setBroadcast(true);

				while (true) {
					System.out.println(getClass().getName() + "SERVER>>>Pronto para receber pacotes Broadcast!");

					//Recebendo um pacote
					byte[] recvBuf = new byte[15000];
					DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
					socket.receive(packet);

					//Pacote recebido
					println(getClass().getName() + "SERVER>>>Analisando pacote recebido de: " + packet.getAddress().getHostAddress());
					println(getClass().getName() + "SERVER>>>Pacotes recebidos; dados: " + new String(packet.getData()));

					//Vendo se o pacote contém o comando correto (menssagem)
					String message = new String(packet.getData()).trim();
					println("SERVER"+message)
					byte[] sendData = ("Alerta Recebido. - "+(new Date()).time.toString()).getBytes();

					//Enviando resposta
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
					socket.send(sendPacket);

					System.out.println(getClass().getName() + "SERVER>>>Sent packet to: " + sendPacket.getAddress().getHostAddress());
				}
			} catch (IOException ex) {
				Logger.getLogger(DiscoveryThread.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		p.onError{ Throwable err ->
			println "Ocorreu um erro: ${err.message}";
		}
	}
}
