package vanet.monitoring

import static grails.async.Promises.*
import grails.transaction.Transactional

import org.apache.commons.lang.RandomStringUtils

import vanet.alert.Alert
import vanet.automotive.NavigationLog

@Transactional
class AccidentDetectionService {

	def broadcastService
	def grailsApplication
	def carService
	def navigationLogService

	def accidentVerify(NavigationLog currentLog) {
		task{
			try{
				if(currentLog){
	
//					println("Airbag is open = "+currentLog.isAirbagOpen)
					if(currentLog.isAirbagOpen){
						accidentAlert(currentLog)
					}else{
						NavigationLog lastLog = currentLog.lastNavigationLog
						if(lastLog){
							def lastVel=lastLog.obdSpeed/3.6
//							println("lastVel = "+lastVel+" m/s")
							def currentVel=currentLog.obdSpeed/3.6
//							println("currentVel = "+currentVel+" m/s")
							def deltaSpeed = currentVel-lastVel
//							println("deltaSpeed = "+deltaSpeed+" m/s")
							def deltaTime = currentLog.collectTime - lastLog.collectTime
//							println("deltaTime = "+deltaTime+" ms")
								
							def a = deltaSpeed/deltaTime
//							println("accel = "+a+"m/ms2")
							
							if(a < -0.0626){//-62.6){
								accidentAlert(currentLog)
							}
//							println("------------------------------------")
						}
					}
				}
			}catch(Exception e){
				println e.message
			}
		}
	}
			
	def accidentAlert(NavigationLog navigationLogInstance){
		def alert = new Alert()
		alert.code=RandomStringUtils.random(5, true, true)// Gerando uma chave aleatória de 5 caracteres
		alert.messageCode= 2 // 1-OK, 2-Atencao provavel acidente, 3- Acidente, 4-Outros
		alert.message= "Provavel acidente detectado!" //Utilizado apenas com codigo 4
		alert.ip=InetAddress.getLocalHost().getHostAddress().toString()
		alert.carCode=navigationLogInstance.car.code
		alert.lat=navigationLogInstance.lat
		alert.lng=navigationLogInstance.lon
		alert.alertDate=navigationLogInstance.collectTime
		alert.sendDate=System.currentTimeMillis()
							
		alert.save(flush:true)
		
		broadcastService.sendAlertWhileNear(alert)

		sleep(30000)//30 segundos
		def newNavigationLog = navigationLogService.readNewNavigationLog()

		// Se o ultimo log enviado for o do acidente (equipamento comprometido) ou a velocidade atual for 0
		if(newNavigationLog.id == navigationLogInstance.id || newNavigationLog.obdSpeed == 0){
			println "Enviando alerta ao motorista..."
			def accident = true
			//def accident = driverAlert("Um acidente foi detectado, você confirma?","Sim","Não",60000)
			sleep(6)
			broadcastService.stopInfiniteAlert(alert)
			if(accident){
				def confirmedAlert = new Alert() 
				confirmedAlert.code=RandomStringUtils.random(5, true, true)// Gerando uma chave aleatória de 5 caracteres
				confirmedAlert.messageCode= 3 // 1-OK, 2-Atencao provavel acidente, 3- Acidente, 4-Outros
				confirmedAlert.message= "Acidente detectado!" //Utilizado apenas com codigo 4
				confirmedAlert.ip=InetAddress.getLocalHost().getHostAddress().toString()
				confirmedAlert.carCode=navigationLogInstance.car.code
				confirmedAlert.lat=navigationLogInstance.lat
				confirmedAlert.lng=navigationLogInstance.lon
				confirmedAlert.alertDate=navigationLogInstance.collectTime
				confirmedAlert.sendDate=System.currentTimeMillis()
									
				confirmedAlert.save(flush:true)
				broadcastService.sendAlertWhileNear(confirmedAlert)
			}else{
				return
			}
		}else{
			broadcastService.stopInfiniteAlert(alert)
			return
		}
	}
	
}
