package vanet.alert

import grails.transaction.Transactional

@Transactional
class AlertService {

	def broadcastService
	def navigationLogService
	def mapService
	def carService
	
    def alertReceive(Alert alert) {
		def lastLog = navigationLogService.readNewNavigationLog()
		boolean redundantAlert = broadcastService.isSending(alert)//Alert.findByCode(alert.code)
		if(lastLog){
			println "<<<<<< Alerta detectado a "+mapService.distance(alert.lat, alert.lng, lastLog.lat, lastLog.lon)+" metros!"
		}else{
			println "<<<<<< Alerta detectado! "
		}
		alert.receivedDate = System.currentTimeMillis()
		alert.distance = carService.calculateDistance(alert)
		alert.save(flush:true)
		println ">>>>>>>>>>>>>>>>>"+(alert.receivedDate - alert.sendTime)
		if(!redundantAlert){
			println " Repassando o alerta..."
			broadcastService.sendAlertWhileNear(alert)
		}
    }
}
