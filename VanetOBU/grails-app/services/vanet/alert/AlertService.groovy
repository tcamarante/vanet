package vanet.alert

import grails.transaction.Transactional

@Transactional
class AlertService {

	def broadcastService
	def navigationLogService
	def mapService
	
    def alertReceive(Alert alert) {
		def lastLog = navigationLogService.readNewNavigationLog()
		println "<<<<<< Alerta detectado a "+mapService.distance(alert.lat, alert.lng, lastLog.lat, lastLog.lon)+" metros."
		Alert redundantAlert = Alert.findByCode(alert.code)
		if(!redundantAlert){
			alert.receivedDate = System.currentTimeMillis()
			alert.save(flush:true)
			broadcastService.sendAlertWhileNear(alert)
		}
    }
}
