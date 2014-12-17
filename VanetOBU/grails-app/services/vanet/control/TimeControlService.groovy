package vanet.control

import grails.transaction.Transactional
import grails.plugins.rest.client.RestBuilder

@Transactional
class TimeControlService {

    def calculateTimeDiff(String url) {
		def rest = new RestBuilder()
		println url+"/VanetOBU/timeControl/getTime"
		// Enviando ao servidor para salvar no banco
		def resp = rest.get(url+"/VanetOBU/timeControl/getTime"){
			contentType "application/vnd.org.jfrog.artifactory.security.Group+json"
		}
		println resp.statusCode.class
		println resp.JSON
    }
}
