package vanet.securityimport java.util.Date;
class SecUser extends SpringUser implements Serializable{

	Date lastAccess
	Date since
	boolean enabled =  false

	static hasMany = [secRole: SecRole]

	static constraints = {
		username()
		password(password: true, minSize: 6)
		lastAccess(nullable:true)
		since()
		secRole()
	}

	String toString() {
		return username
	}
}
