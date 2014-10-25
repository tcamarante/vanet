package vanet.alert



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AlertController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	def broadcastService
	
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Alert.list(params), model:[alertInstanceCount: Alert.count()]
    }

    def show(Alert alertInstance) {
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
}
