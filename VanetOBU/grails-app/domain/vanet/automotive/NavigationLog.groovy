package vanet.automotive

class NavigationLog {

	String code
	Long collectTime
	Long obuTime
	Long rsuTime
	Long serverTime
	// Informações de GPS
	Double gpsSpeed
	Long gpsTime
	Double lon
	Double lat
	// Informações do OBD
	Double obdSpeed
	Boolean isAirbagOpen = false

	NavigationLog lastNavigationLog
	Rsu rsu
	
	static belongsTo = [car:Car]
		
    static constraints = {
		collectTime(nullable:true)
		obuTime(nullable:true)
		rsuTime(nullable:true)
		serverTime(nullable:true)
		gpsSpeed(nullable:true)
		gpsTime(nullable:true)
		lon(nullable:true)
		lat(nullable:true)
		obdSpeed(nullable:true)
		isAirbagOpen()
		lastNavigationLog(nullable:true)
		rsu(nullable:true)
		car(nullable:true)
    }
	
}
