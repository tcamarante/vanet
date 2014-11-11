package vanet.automotive

import grails.transaction.Transactional

@Transactional
class CarService {

	def grailsApplication
	def navigationLogService
	
    def isInArea(Double lat, Double lng){
		int raio = grailsApplication.config.vanet.sendRadiusLimit 
		def nLog = navigationLogService.readNewNavigationLog()
		def distance = calculateDistance(lat, lng, nLog.lat, nLog.lon)
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
}
