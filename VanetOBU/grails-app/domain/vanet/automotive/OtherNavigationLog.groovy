package vanet.automotive

class OtherNavigationLog {

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

	Long lastNavigationLogId
	Long rsuId
	
	static belongsTo = [carId:Long]
		
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
		lastNavigationLogId(nullable:true)
		rsuId(nullable:true)
		carId(nullable:true)
    }
	
}
