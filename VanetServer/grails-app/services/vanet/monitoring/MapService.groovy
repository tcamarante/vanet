package vanet.monitoring

import grails.transaction.Transactional

@Transactional
class MapService {

    def distance(lon1, lat1, lon2, lat2) {
		def R = 6371; // km
		def dLat = Math.toRadians(lat2-lat1)
		def dLon = Math.toRadians(lon2-lon1)
		lat1 = Math.toRadians(lat1)
		lat2 = Math.toRadians(lat2)
		
		def a = Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
		def c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		return R * c;// Km
    }
}
