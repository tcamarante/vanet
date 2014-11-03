package vanet.monitoring

import static grails.async.Promises.*
import grails.transaction.Transactional

import org.apache.commons.lang.RandomStringUtils

import vanet.alert.Alert
import vanet.automotive.NavigationLog

@Transactional
class AccidentDetectionService {

	def broadcastService
	def velocities = [0,15,0,30,0,22.53,0,22.54,0,40,0,60,0,30,0,0,0,0]
	def airbags = [false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,false]
	def stop = false
	
	def stopAccidentDetection(){
		stop = true
	}
	
    def start() {
		task{
			def vel1=0, vel2=0, i=0
			while(!stop){
				if(i == 14)i=0
				
				if(readAirbag(i)){
					accidentAlert(i)
				}
				vel1 = vel2
				sleep(100)
				vel2 = readVel(i)
				
				println("vel1 = "+vel1+"m/s")
				println("vel2 = "+vel2+"m/s")
				println("deltaV = "+(vel2-vel1)+"m/s")
				def a = (vel2-vel1)/0.1
				println("accel = "+a+"m/s2")
				
				if(a < -62.6){
					accidentAlert(i)
				}
				println("------------------------------------")
				i++
			}
		}
    }
	
	def accidentAlert(NavigationLog navigationLogInstance){
		def alert = new Alert(code:RandomStringUtils.random(5, true, true),// Gerando uma chave aleat�ria de 5 caracteres
								messageCode: 2, // 1-OK, 2-Atencao provavel acidente, 3- Acidente, 4-Outros
								message: "Provavel acidente detectado!", //Utilizado apenas com codigo 4
								ip:InetAddress.getLocalHost().getHostAddress().toString(),
								car:navigationLogInstance.car,
								lat:navigationLogInstance.lat,
								lng:navigationLogInstance.lon,
								alertDate:new Date()
							)
		broadcastService.send(alert)
		// sendVanetAccidentAlert()
		println("Poss�vel acidente detectado!")
		sleep(3)//30 segundos
		if(!readVel(i)||readVel(i+1) == 0){
			println "Enviando alerta ao motorista..."
			def accident = true
			//def accident = driverAlert("Um acidente foi detectado, voc� confirma?","Sim","N�o",60000)
			sleep(6)
			if(accident){
				println "Acidente"
				broadcastService.send("Poss�vel acidente detectado, fique atento!")
			}else{
				return
			}
		}else{
			return
		}
	}
	
	def accidentVerify(NavigationLog currentLog) {
		NavigationLog lastLog = currentLog.lastNavigationLog
		task{
			if(currentLog && lastLog){

				if(currentLog.isAirbagOpen){
					accidentAlert(currentLog)
				}
				
				def lastVel=lastLog.obdSpeed/3.6
				println("lastVel = "+lastVel+" m/s")
				def currentVel=currentLog.obdSpeed/3.6
				println("currentVel = "+currentVel+" m/s")
				def deltaSpeed = currentVel-lastVel
				println("deltaSpeed = "+deltaSpeed+" m/s")
				def deltaTime = currentLog.collectTime - lastLog.collectTime
				println("deltaTime = "+deltaTime+" s")
					
				def a = deltaSpeed/deltaTime
				println("accel = "+a+"m/s2")
				
				if(a < -62.6){
					accidentAlert(currentLog)
				}
				println("------------------------------------")
				i++
			}
		}
	}
	
	
	def readVel(int i){
		return velocities[i]/3.6
	}
	
	def readAirbag(int i){
		return airbags[i]
	}
}
