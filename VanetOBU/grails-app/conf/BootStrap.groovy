import static grails.async.Promises.*
import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder

import org.codehaus.groovy.grails.web.converters.configuration.ChainedConverterConfiguration
import org.codehaus.groovy.grails.web.converters.configuration.ConvertersConfigurationHolder
import org.codehaus.groovy.grails.web.converters.configuration.DefaultConverterConfiguration
import org.springframework.http.HttpStatus

import vanet.automotive.Car
import vanet.automotive.NavigationLog

class BootStrap {

	def broadcastService
	def accidentDetectionService
	def grailsApplication
	
    def init = { servletContext ->
		
		// Lendo arquivo com informações do veículo
		// Getting context path here
//		def webRootDir = sch.servletContext.getRealPath ("/")
		def currentCar = new Car(code:InetAddress.getLocalHost().getHostAddress().toString()).save(flush:true)
		
//		task{
//			def rest = new RestBuilder()
//			Boolean hasCar = false
//			while(!hasCar){
//				println hasCar
//				// Enviando ao servidor para salvar no banco
//				try{
//					def resp = rest.post(grailsApplication.config.vanet.rsuUrl+"/VanetRSU/car/save"){
//						contentType "application/vnd.org.jfrog.artifactory.security.Group+json"
//						json currentCar
//					}
//					println resp.statusCode.class
//					if(resp.statusCode == HttpStatus.CREATED){
//						hasCar=true
//					}
//				}catch(Exception e){
//					println("Não foi possível enviar pacote")
//					Thread.currentThread().sleep((long)(1000));
//				}
//			}
//		}
//		
		broadcastService.alertSender()
		broadcastService.receive()
//		accidentDetectionService.start()
		
//		broadcastService.send()
//		sleep(100)
//		broadcastService.send()
		
//		DefaultConverterConfiguration<JSON> cfg = (DefaultConverterConfiguration<JSON>)ConvertersConfigurationHolder.getConverterConfiguration(JSON)
//		ConvertersConfigurationHolder.setDefaultConfiguration(JSON.class, new ChainedConverterConfiguration<JSON>(cfg, cfg.proxyHandler));
//
//		
//		JSON.registerObjectMarshaller( NavigationLog ) {
//			def returnSet = [:]
//			returnSet.id = it.id
//			returnSet.collectTime = it.collectTime
//			returnSet.obuTime = it.obuTime
//			returnSet.rsuTime = it.rsuTime
//			returnSet.serverTime = it.serverTime
//			returnSet.gpsSpeed = it.gpsSpeed
//			returnSet.gpsTime = it.gpsTime
//			returnSet.lon = it.lon
//			returnSet.lat = it.lat
//			returnSet.obdSpeed = it.obdSpeed
//			returnSet.isAirbagOpen = it.isAirbagOpen
//			returnSet.lastNavegationLog = it.lastNavegationLog
//			returnSet.rsu = it.rsu
//			returnSet.car = ["id":it.car?.id,"code":it.car?.code]
//			return returnSet
//		}

    }
    def destroy = {
    }
}
