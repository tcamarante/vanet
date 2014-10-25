package vanet.person

import vanet.automotive.Car
import vanet.security.SecUser

class Person {

	String name
	String cpf
	
	static hasMany = [car:Car]
	
	static belongsTo = [secUser:SecUser]
	
    static constraints = {
    }
}
