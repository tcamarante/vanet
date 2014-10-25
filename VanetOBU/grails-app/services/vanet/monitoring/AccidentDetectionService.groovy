package vanet.monitoring

import grails.transaction.Transactional
import static grails.async.Promises.*

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
	
	def accidentAlert(int i){
		// sendVanetAccidentAlert()
		println("Possível acidente detectado!")
		sleep(3)//30 segundos
		if(!readVel(i)||readVel(i+1) == 0){
			println "Enviando alerta ao motorista..."
			def accident = true
			//def accident = driverAlert("Um acidente foi detectado, você confirma?","Sim","Não",60000)
			sleep(6)
			if(accident){
				println "Acidente"
				broadcastService.send("Possível acidente detectado, fique atento!")
			}else{
				return
			}
		}else{
			return
		}
	}
	
	def readVel(int i){
		return velocities[i]/3.6
	}
	
	def readAirbag(int i){
		return airbags[i]
	}
}
