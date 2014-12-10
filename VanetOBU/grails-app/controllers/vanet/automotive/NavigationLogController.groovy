package vanet.automotive



import static org.springframework.http.HttpStatus.*
import grails.plugins.rest.client.RestBuilder
import grails.rest.RestfulController
import grails.transaction.Transactional

import org.apache.commons.lang.RandomStringUtils

import vanet.alert.Alert

@Transactional(readOnly = true)
class NavigationLogController  extends RestfulController{

	static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	
	def accidentDetectionService
	def broadcastService
	
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
    def save(NavigationLog navigationLogInstance) {
        if (navigationLogInstance == null) {
            notFound()
            return
        }

        if (navigationLogInstance.hasErrors()) {
            respond navigationLogInstance.errors, view:'create'
            return
        }

        navigationLogInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'navigationLog.label', default: 'NavigationLog'), navigationLogInstance.id])
                redirect navigationLogInstance
            }
            '*' { respond navigationLogInstance, [status: CREATED] }
        }
    }

	def saveTorkLog(){
		
		println params
		def rest = new RestBuilder()
		def date = new Date()
		
		NavigationLog navigationLogInstance = new NavigationLog(
			infoTime: date,
			gpsSpeed: params.kff1001,
			gpsDate: params.time,
			lon: params.kff1005,
			lat: params.kff1006,
			obdSpeed: params.kd,
			lastNavegationLog: NavigationLog.createCriteria().get {
						car{
							eq("code",params.id)
						}
					    projections {
					        max "infoTime"
					    }
					} as Long,
			car:Car.findByCode(params.id)
		)
		
		// Enviando ao servidor para salvar no banco
		def resp = rest.post(grailsApplication.config.vanet.rsuUrl+"/VanetRSU/navigationLog/save"){
			//auth System.getProperty("artifactory.user"), System.getProperty("artifactory.pass")
			contentType "application/vnd.org.jfrog.artifactory.security.Group+json"
			json navigationLogInstance 
		}
		
//		navigationLogInstance.save(flush:true)
//		respond navigationLogInstance, [status:OK]
		
		respond resp, [status:OK]
		
//		Float k11 // 26.27451 // ?
//		String id // 17f7b2401840805935d2dffa365a3fe7
//		Float kff1001 // 0.0 // GPSspeed
//		Integer v // 3 // Horizontal Dilution of Precision?
//		Float k5 // 102.0 // Temperatura do motor
//		Long time // 1410788764730 // deviceTime
//		Double kff1005 // -44.9787456149532 // Longitude
//		Long session // 1410788652328 // GPStime?
//		Float kd // 72.0 // Velocidade
//		Float kc // 4422.5 // rpm
//		Double kff1006 // -21.22808219311608 // Latitude
//		
	}
	
	@Transactional
	def saveObdLog(){
		
		println params
		println params.kff1005.class
		def rest = new RestBuilder()
		def date = Calendar.instance
		date.setTimeInMillis(params.time.toLong()*1000)
		Car carInstance = Car.findAll().first()
//		def lastLog = NavigationLog.find("from NavigationLog nl where nl.car = :car order by id desc", [car:carInstance])
		
		NavigationLog navigationLogInstance = new NavigationLog(
//			params."Throttle Position", 
//			params."Engine RPM", 
			code:carInstance.code + RandomStringUtils.random(5, true, true),
			obdSpeed:params.kd,//?.toDouble(),//"Vehicle Speed"?.replace("km/h","").toInteger(), 
//			params."Trouble Codes", 
//			params."Mass Air Flow", 
			collectTime:params.time.toLong(),//"Obs Time"?.toLong(),// O tempo passado é em milisegundos por enquanto
			obuTime: System.currentTimeMillis(),
			rsuTime:null,
			serverTime:null,
			gpsSpeed:params.kff1001.toDouble(),//.toInteger(),//"GPS Speed"?.replace("m/s","")?.toInteger(),
			lat:params.kff1006.toDouble(),//"Latitude",
			gpsTime:params."session".toLong(),//"GPS Time"?.toLong(),
			isAirbagOpen:false,//(params."Trouble Codes".find("99 94")!=null||params."Trouble Codes".find("9994")!=null),
			lon:params.kff1005.toDouble(),//"Longitude",
			lastNavigationLog: NavigationLog.find("from NavigationLog nl where nl.car = :car order by id desc", [car:carInstance]),
			car: carInstance
		)
		
//		navigationLogInstance.lastNavegationLog = lastLog
		
		accidentDetectionService.accidentVerify(navigationLogInstance)
		
		navigationLogInstance.validate()
		if (navigationLogInstance.hasErrors()) {
			respond navigationLogInstance.errors, view:'create'
			return
		}

		navigationLogInstance.save flush:true
		
//		def jsonObject = JSON.parse((navigationLogInstance as JSON).toString())
//		jsonObject.put("carCode", navigationLogInstance.car.code)

//		broadcastService.sendLog(navigationLogInstance)
		
		respond navigationLogInstance, [status:CREATED]
		
		// Enviando ao servidor para salvar no banco
		// TODO: contar pacotes enviados
//		def resp
//		try{
//			resp = rest.post(grailsApplication.config.vanet.rsuUrl+"/navigationLog/save"){
//				//auth System.getProperty("artifactory.user"), System.getProperty("artifactory.pass")
//				contentType "application/vnd.org.jfrog.artifactory.security.Group+json"
//				json jsonObject.toString()
//			}
//		}catch(ConnectException e){
//			println("Não foi possível enviar pacote")
//			// TODO: contar pacotes perdidos
//		}

//		respond resp.json, [status:CREATED]
		
//		[
//			Throttle Position:144 %,
//			Fuel Economy MAP:NODATA,
//			Engine RPM:5742 RPM,
//			Vehicle Speed:113 km/h,
//			GPS Speed:0 m/s,
//			Latitude:-21,22798,
//			GPS Time:1412944269,
//			Longitude:-44,97875,
//			Trouble Codes:43 01 03 99 94 00 00,
//			Mass Air Flow:257.33,
//			Obs Time:1412944269
//		]
		
	
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

	def testAlert(){
		broadcastService.testAlert(new Alert(code:RandomStringUtils.random(5, true, true)))
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
