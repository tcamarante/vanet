package vanet.automotive



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.rest.RestfulController;
import java.util.Map;
import grails.converters.JSON

@Transactional(readOnly = true)
class CarController extends RestfulController{

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	@Override
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Car.list(params), [status: OK]
    }

	@Override
	def create(){
		respond new Car()
	}
	
	@Override
    @Transactional
    def save() {
		Car carInstance = Car.findByCode(request.JSON.code)
		if(carInstance){
			respond carInstance, [status: CREATED]
		}else{
			carInstance = new Car()
			bindCar(carInstance,request.JSON)
			
	        carInstance.validate()
	        if (carInstance.hasErrors()) {
				respond carInstance.errors, [status: NOT_ACCEPTABLE]
	            return
	        }
	
	        carInstance.save flush:true
	        respond carInstance, [status: CREATED]
		}
    }

	@Override
	def show(){
		Car carInstance = Car.findById(request.JSON.id)
		respond carInstance
	}
	
	@Override
    @Transactional
    def update() {
		
		Car carInstance
		
   		try {
		
			carInstance = Car.findById(request.JSON.id)
		
  		} catch (NumberFormatException) {
		
   			respond JSON.parse('{"errors": [{"message":"'+ message(code: 'default.id.notANumber.message') +'"}]}'),
   					[status: NOT_ACCEPTABLE]
		
		   return
  		}
		
        if (carInstance == null) {
			respond JSON.parse('{"message":"'+message(code: 'default.not.found.message', 
					args: [message('car.label', default:'Car'),request.JSON.id])+'"}') ,[status: NOT_FOUND]
            return
        }

		bindCar(carInstance,request.JSON)
		
        carInstance.validate()
        if (carInstance.hasErrors()) {
			respond carInstance.errors, [status: NOT_ACCEPTABLE]
            return
        }

        carInstance.save flush:true
        respond carInstance, [status: OK]
    }

    @Transactional
    def delete() {

		Car carInstance
		
   		try {
		
			carInstance = Car.findById(request.JSON.id)
		
  		} catch (NumberFormatException) {
		
   			respond JSON.parse('{"errors": [{"message":"'+ message(code: 'default.id.notANumber.message') +'"}]}'),
   					[status: NOT_ACCEPTABLE]
		
		   return
  		}
		
        if (carInstance == null) {
            respond JSON.parse('{"message":"'+message(code: 'default.not.found.message', 
					args: [message('car.label', default:'Car'),params.id])+'"}') ,[status: NOT_FOUND]
            return
        }

        carInstance.delete flush:true
		render status: NO_CONTENT
    }
	
	protected bindCar(Car carInstance, Map json){
		
		bindData(carInstance,json)
		
	
		carInstance.navigatonLog.each { carInstance.addToNavigatonLog(it)}
	
	}
}


