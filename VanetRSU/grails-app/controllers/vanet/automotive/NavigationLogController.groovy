package vanet.automotive



import static org.springframework.http.HttpStatus.*

import java.util.Date;

import grails.rest.RestfulController;
import grails.transaction.Transactional
import grails.plugins.rest.client.RestBuilder

@Transactional(readOnly = true)
class NavigationLogController extends RestfulController{

	static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond NavigationLog.list(params), model:[navigationLogInstanceCount: NavigationLog.count()]
    }

    def show(NavigationLog navigationLogInstance) {
        respond navigationLogInstance
    }

    def create() {
        respond new NavigationLog(params)
    }

    @Transactional
    def save() {
		
		def rest = new RestBuilder()
		println request.JSON
		println params
		NavigationLog navigationLogInstance = new NavigationLog()
		bindData(navigationLogInstance, request.JSON, [exclude:['id','class']])
        if (navigationLogInstance == null) {
            notFound()
            return
        }
		
		navigationLogInstance.carCode = request.JSON.carCode
		navigationLogInstance.rsuTime = System.currentTimeMillis()
		
		navigationLogInstance.validate()
        if (navigationLogInstance.hasErrors()) {
            respond navigationLogInstance.errors, view:'create'
            return
        }

//        navigationLogInstance.save flush:true
		
		def resp
		try{
			// Enviando ao servidor para salvar no banco
//			resp = rest.post("{grailsApplication.config.vanet.serverUrl}/navigationLog/save"){
			resp = rest.post("http://localhost:8080/VanetServer/navigationLog/save"){
				//auth System.getProperty("artifactory.user"), System.getProperty("artifactory.pass")
				contentType "application/vnd.org.jfrog.artifactory.security.Group+json"
				json navigationLogInstance
			}
		}catch(ConnectException e){
			println("N�o foi poss�vel enviar pacote")
			// TODO: contar pacotes perdidos
		}
		
		respond resp.json, [status: CREATED]
//        request.withFormat {
//            form multipartForm {
//                flash.message = message(code: 'default.created.message', args: [message(code: 'navigationLog.label', default: 'NavigationLog'), navigationLogInstance.id])
//                redirect navigationLogInstance
//            }
//            '*' { respond navigationLogInstance, [status: CREATED] }
//        }
    }

	def saveTorkLog(){
		
		println params
		println request.JSON
		
		def date = new Date()
		
		NavigationLog navigationLogInstance = new NavigationLog(
			infoTime: date,
			gpsSpeed: 0,
			gpsDate: params.time,
			lon: params.kff1005,
			lat: params.kff1006,
			obdSpeed: params.kd,
			lastNavegationLog: null)
		
		navigationLogInstance.save()
		
//		Float k11 // 26.27451 // ?
//		String id // 17f7b2401840805935d2dffa365a3fe7
//		Float kff1001 // 0.0
//		Integer v // 3 // ?
//		Float k5 // 102.0 // Temperatura do motor
//		Long time // 1410788764730
//		Double kff1005 // -44.9787456149532 // Longitude
//		Long session // 1410788652328
//		Float kd // 72.0 // Velocidade
//		Float kc // 4422.5 // rpm
//		Double kff1006 // -21.22808219311608 // Latitude
//		
	}
	
    def edit(NavigationLog navigationLogInstance) {
        respond navigationLogInstance
    }

    @Transactional
    def update(NavigationLog navigationLogInstance) {
        if (navigationLogInstance == null) {
            notFound()
            return
        }

        if (navigationLogInstance.hasErrors()) {
            respond navigationLogInstance.errors, view:'edit'
            return
        }

        navigationLogInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'NavigationLog.label', default: 'NavigationLog'), navigationLogInstance.id])
                redirect navigationLogInstance
            }
            '*'{ respond navigationLogInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(NavigationLog navigationLogInstance) {

        if (navigationLogInstance == null) {
            notFound()
            return
        }

        navigationLogInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'NavigationLog.label', default: 'NavigationLog'), navigationLogInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'navigationLog.label', default: 'NavigationLog'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
