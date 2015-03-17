package vanet.alert



import static org.springframework.http.HttpStatus.*
import vanet.alerts.BroadcastService;
import grails.transaction.Transactional
import groovy.sql.Sql
import groovy.sql.GroovyRowResult

@Transactional(readOnly = true)
class AlertController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	def broadcastService
	def dataSource
	def mapService
	
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
		params.sort = "receivedDate"
		params.order = "desc"
        respond Alert.list(params), model:[alertInstanceCount: Alert.count()]
    }

    def show() {
		def alertInstance = Alert.findById(params.id) 
        respond alertInstance
    }

    def create() {
        respond new Alert(params)
    }

    @Transactional
    def save(Alert alertInstance) {
        if (alertInstance == null) {
            notFound()
            return
        }

        if (alertInstance.hasErrors()) {
            respond alertInstance.errors, view:'create'
            return
        }

        alertInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'alert.label', default: 'Alert'), alertInstance.id])
                redirect alertInstance
            }
            '*' { respond alertInstance, [status: CREATED] }
        }
    }

    def edit(Alert alertInstance) {
        respond alertInstance
    }

    @Transactional
    def update(Alert alertInstance) {
        if (alertInstance == null) {
            notFound()
            return
        }

        if (alertInstance.hasErrors()) {
            respond alertInstance.errors, view:'edit'
            return
        }

        alertInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Alert.label', default: 'Alert'), alertInstance.id])
                redirect alertInstance
            }
            '*'{ respond alertInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Alert alertInstance) {

        if (alertInstance == null) {
            notFound()
            return
        }

        alertInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Alert.label', default: 'Alert'), alertInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'alert.label', default: 'Alert'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
	
	def sendAlert(){
		broadcastService.send("Vladmir na escuta")
		respond 200
	}
	
	def mapa(){
		
	}
	
	def mapa2(){
		
	}
	
	def grafico(){
		
		final Sql sql = new Sql(dataSource)
		
//		String query = 
//			"select DISTINCT on (collect_time) b.collect_time, b.id, b.clazz, b.time, b.code, b.obd_speed, a.* from ( "+
//			"	select id, 'nl' as clazz, obu_time as time, code, collect_time, obd_speed/10 as obd_speed "+
//			"	from other_navigation_log "+
//			"	where collect_time >= 1423688484499 "+// and collect_time <= 1423334616773 "+
//			"union "+
//			"	select id, 'a' as clazz, received_date, code as time, received_date as collect_time , 0 as obd_speed "+
//			"	from other_alert "+  
//			"	where received_date >= 1423688484499 "+// and received_date <=1423334616773 "+
//			")b left join other_alert a on a.id=b.id "+
//			"order by collect_time "
			
//		String query =
//			"select distinct nl.collect_time, nl.lon as log_lng, nl.lat as log_lat, "+
//			"a.received_date - a.send_time  as time_diff "+
//			"from alert a inner join navigation_log nl on nl.id = "+
//			"(select id from navigation_log nl2 where nl2.collect_time < a.received_date  order by nl2.collect_time desc limit 1) "+
//			//"where nl.collect_time >= 1423333078949 and nl.collect_time <=1423334609773 "+
//			//"	and a.received_date >= 1423333078949 and a.received_date <=1423334609773 "+
//			"order by collect_time "
			
		String query =
			"select distinct nl.collect_time, nl.lon as log_lng, nl.lat as log_lat, gnl.lon as gol_lon, gnl.lat as gol_lat, "+
			"a.received_date - a.send_time  as time_diff "+
			"from other_alert a inner join other_navigation_log nl on nl.id = "+
			"(select id from other_navigation_log nl2 where nl2.collect_time < a.received_date  order by nl2.collect_time desc limit 1) "+
			"inner join navigation_log gnl on gnl.id = "+
			"(select id from navigation_log nl2 where nl2.collect_time < a.received_date  order by nl2.collect_time desc limit 1) "+
			//"--where nl.collect_time >= 1423333078949 and nl.collect_time <=1423334609773 "+
			//"	and a.received_date >= 1423333078949 and a.received_date <=1423334609773 "+
			"order by collect_time "
			
		final results = sql.rows(query)
		results.each{
			//-21.227593, -44.978472 // Coordenadas da porta do dcc
//			it.distance = mapService.distance(it.alert_lng, it.alert_lat, it.log_lng, it.log_lat)
			it.distance = mapService.distance( it.log_lng, it.log_lat, it.gol_lon, it.gol_lat)// -44.972367, -21.227620)
			// -44.971888, -21.227642 // rsu
			// -44.978472, -21.227593
			println it
		}
		respond results
	}
	
	def grafico2(){
		
		final Sql sql = new Sql(dataSource)
					
		String query =
			"select distinct nl.collect_time, nl.lon as log_lng, nl.lat as log_lat, "+
			"a.received_date - a.send_time as time_diff "+
			"from alert a inner join navigation_log nl on nl.id = "+
			"(select id from navigation_log nl2 where nl2.collect_time < a.received_date  order by nl2.collect_time desc limit 1) "+
			"where nl.collect_time >= 1423333078949 and nl.collect_time <=1423334609773 "+
			"	and a.received_date >= 1423333078949 and a.received_date <=1423334609773 "+
			"order by collect_time "
		final results = sql.rows(query)
		results.each{
			//-21.227593, -44.978472 // Coordenadas da porta do dcc
//			it.distance = mapService.distance(it.alert_lng, it.alert_lat, it.log_lng, it.log_lat)
			it.distance = mapService.distance( it.log_lng, it.log_lat, -44.972367, -21.227620)
			println it
		}
		respond results
	}
	
	def stopAll(){
		broadcastService.stopAllAlerts()
		def result = new HashMap()
		result.put('message',"Todos alertas parados!!!")
		respond result, ['status':OK, formats:['json']]
	}
}
