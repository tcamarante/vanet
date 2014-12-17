package vanet.control

import static org.springframework.http.HttpStatus.*
import grails.rest.RestfulController

class TimeControlController  extends RestfulController {

	static responseFormats = ['json', 'xml']
	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	
	def timeControlService
	
    def getTime() { 
		TimeControl timeControlInstance = new TimeControl()
		timeControlInstance.time = System.currentTimeMillis()
		timeControlInstance.ip = InetAddress.getLocalHost().getHostAddress()
		respond timeControlInstance, [status:OK]
	}
	
	def getTimeDiff(){
		TimeControl timeControlInstance = new TimeControl()
		bindData(timeControlInstance, timeControlService.calculateTimeDiff(params.address))
		timeControlInstance.save(flush:true)
		respond timeControlInstance
		
	}
	
}
