package vanet.alert

import grails.transaction.Transactional

@Transactional
class AlertService {

	def broadcastService
	
    def alertReceive(Alert alert) {
		def alertList = Alert.findAll()
		Alert redundantAlert = Alert.findByCode(alert.code)
		if(!redundantAlert){
			alert.receivedDate = System.currentTimeMillis()
			alert.save(flush:true)
			broadcastService.sendAlertWhileNear(alert)
		}
    }
}
