package vanet.control

import grails.plugins.rest.client.RestBuilder
import grails.transaction.Transactional

@Transactional
class TimeControlService {

    def calculateTimeDiff(String url) {
		def rest = new RestBuilder()
		println url+"/VanetOBU/timeControl/getTime"
		// Enviando ao servidor para salvar no banco
		def resp = rest.get(url+"/VanetOBU/timeControl/getTime")
		println resp.statusCode.class
		resp.json.timeDiff = System.currentTimeMillis() - resp.json.time 
		return resp.json
    }
}
