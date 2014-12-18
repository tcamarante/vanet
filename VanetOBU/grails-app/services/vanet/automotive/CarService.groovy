package vanet.automotive

import grails.plugins.rest.client.RestBuilder
import grails.transaction.Transactional
import vanet.alert.Alert

@Transactional
class CarService {

	def grailsApplication
	def navigationLogService
	
    def isInArea(Double lat, Double lng){
		int raio = grailsApplication.config.vanet.sendRadiusLimit 
		def nLog = navigationLogService.readNewNavigationLog()
		def distance
		if(nLog){
			distance = calculateDistance(lat, lng, nLog.lat, nLog.lon)
		}else{
			return true
		}
		if(!(distance < raio)){
			println " >>>>>>>>>> Alerta de acidente parou, fora do raio de envio!!!"
		}
		return (distance < raio)
	}
	
	def calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2){
		def R = 6378100 //metros //6371; // km
		def o1 = Math.toRadians(lat1)
		def o2 = Math.toRadians(lat2)
		def deltaO = Math.toRadians(lat2-lat1)
		def deltaL = Math.toRadians(lon2-lon1)
		
		def a = Math.sin(deltaO/2) * Math.sin(deltaO/2) +
				Math.cos(o1) * Math.cos(o2) *
				Math.sin(deltaL/2) * Math.sin(deltaL/2);
		def c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		
		return R * c;
	}
	
	def calculateDistance(Alert alert){
		def navLog = navigationLogService.readNewNavigationLog()
		if(navLog){
			return calculateDistance(alert.lat, alert.lng, navLog.lat, navLog.lon)
		}else{
			return -1
		}
	}
	
	def sendToServer(def json){
		if(grailsApplication.config.isRSU){
			Car carInstance = new Car()
			bindCar(carInstance,json)
			
			carInstance.validate()
			if (carInstance.hasErrors()) {
				respond carInstance.errors, [status: NOT_ACCEPTABLE]
				return
			}
			
			def rest = new RestBuilder()
			def resp
			try{
				resp = rest.post("http://localhost:8080/VanetServer/car/save"){
					contentType "application/vnd.org.jfrog.artifactory.security.Group+json"
					json carInstance
				}
			}catch(Exception e){
				println("N�o foi poss�vel enviar pacote")
//				respond e, [status: 500]
				return null
	//			Thread.currentThread().sleep((long)(1000));
			}
			
			carInstance.save flush:true
			return carInstance
		}else{
			
		}
	}
}
