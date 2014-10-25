package vanet.automotive

import vanet.person.Person

class Car {
	
	String code
	String color
	String model
	String plaque
	String mark
	Integer year

	static hasMany = [navigatonLog:NavigationLog]
	
	static belongsTo = [person:Person]
	
    static constraints = {
		code()
		color(nullable:true)
		model(nullable:true)
		plaque(nullable:true)
		mark(nullable:true)
		year(nullable:true)
		navigatonLog(nullable:true)
		person(nullable:true)
    }
}
