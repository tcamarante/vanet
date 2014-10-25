package vanet.security

import java.io.Serializable;

class SecRole extends SpringRole implements Serializable {
	
	transient springSecurityService

	String authority

	static constraints = {
		authority()
	}
}
