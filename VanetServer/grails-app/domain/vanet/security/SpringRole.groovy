package vanet.security

class SpringRole {

	String authority
	
	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}
}