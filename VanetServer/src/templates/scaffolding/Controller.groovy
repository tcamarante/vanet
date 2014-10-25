<%=packageName ? "package ${packageName}\n\n" : ''%>

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.rest.RestfulController;
import java.util.Map;
import grails.converters.JSON

@Transactional(readOnly = true)
class ${className}Controller extends RestfulController{

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	@Override
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ${className}.list(params), [status: OK]
    }

	@Override
	def create(){
		respond new ${className}()
	}
	
	@Override
    @Transactional
    def save() {
		${className} ${propertyName} = new ${className}()
		bind${className}(${propertyName},request.JSON)
		
        ${propertyName}.validate()
        if (${propertyName}.hasErrors()) {
			respond ${propertyName}.errors, [status: NOT_ACCEPTABLE]
            return
        }

        ${propertyName}.save flush:true
        respond ${propertyName}, [status: CREATED]
    }

	@Override
	def show(){
		${className} ${propertyName} = ${className}.findById(request.JSON.id)
		respond ${propertyName}
	}
	
	@Override
    @Transactional
    def update() {
		
		${className} ${propertyName}
		
   		try {
		
			${propertyName} = ${className}.findById(request.JSON.id)
		
  		} catch (NumberFormatException) {
		
   			respond JSON.parse('{"errors": [{"message":"'+ message(code: 'default.id.notANumber.message') +'"}]}'),
   					[status: NOT_ACCEPTABLE]
		
		   return
  		}
		
        if (${propertyName} == null) {
			respond JSON.parse('{"message":"'+message(code: 'default.not.found.message', 
					args: [message('${domainClass.propertyName}.label', default:'${className}'),request.JSON.id])+'"}') ,[status: NOT_FOUND]
            return
        }

		bind${className}(${propertyName},request.JSON)
		
        ${propertyName}.validate()
        if (${propertyName}.hasErrors()) {
			respond ${propertyName}.errors, [status: NOT_ACCEPTABLE]
            return
        }

        ${propertyName}.save flush:true
        respond ${propertyName}, [status: OK]
    }

    @Transactional
    def delete() {

		${className} ${propertyName}
		
   		try {
		
			${propertyName} = ${className}.findById(request.JSON.id)
		
  		} catch (NumberFormatException) {
		
   			respond JSON.parse('{"errors": [{"message":"'+ message(code: 'default.id.notANumber.message') +'"}]}'),
   					[status: NOT_ACCEPTABLE]
		
		   return
  		}
		
        if (${propertyName} == null) {
            respond JSON.parse('{"message":"'+message(code: 'default.not.found.message', 
					args: [message('${domainClass.propertyName}.label', default:'${className}'),params.id])+'"}') ,[status: NOT_FOUND]
            return
        }

        ${propertyName}.delete flush:true
		render status: NO_CONTENT
    }
	
	protected bind${className}(${className} ${propertyName}, Map json){
		
		bindData(${propertyName},json)
		
	<%
		for(property in domainClass.properties){
			if(property.oneToMany && property.owningSide){
	%>
		${propertyName}.${property.name}.each { ${propertyName}.addTo${removeEspaco(property.naturalName)}(it)}
	<%
			}
		}
	%>
	}
}

<%
private removeEspaco(String str){
	def ret = ""
	for(v in str){
		if(v != ' ' && v != '	'){
			ret += v
		}
	}
	return ret
}
%>
