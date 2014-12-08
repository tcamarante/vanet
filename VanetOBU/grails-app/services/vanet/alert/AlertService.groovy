package vanet.alert

import grails.transaction.Transactional

@Transactional
class AlertService {

	def broadcastService
	def navigationLogService
	def mapService
	
    def alertReceive(Alert alert) {
		def lastLog = navigationLogService.readNewNavigationLog()
		boolean redundantAlert = broadcastService.isSending(alert)//Alert.findByCode(alert.code)
		println "<<<<<< Alerta detectado a "+mapService.distance(alert.lat, alert.lng, lastLog.lat, lastLog.lon)+" metros."
		if(!redundantAlert){
			println " Repassando o alerta..."
			alert.receivedDate = System.currentTimeMillis()
			alert.save(flush:true)
			broadcastService.sendAlertWhileNear(alert)
		}
    }
}
