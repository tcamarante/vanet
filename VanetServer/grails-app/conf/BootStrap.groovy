
class BootStrap {

	def broadcastService
	def accidentDetectionService
	
    def init = { servletContext ->
		
		broadcastService.receive()
//		accidentDetectionService.start()
		
//		broadcastService.send()
//		sleep(100)
//		broadcastService.send()
    }
    def destroy = {
    }
}
