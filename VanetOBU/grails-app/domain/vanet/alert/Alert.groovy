package vanet.alert

class Alert {

	String code
	Integer messageCode // 1-OK, 2-Atencao provavel acidente, 3- Acidente, 4-Outros
	String message //Utilizado apenas com codigo 4
	String ip
	String port
	String carCode
	Double lat
	Double lng
	Long alertDate // Data de disparo do alerta
	Long sendDate //
	Long receivedDate // Data que o alerta foi recebido pelo dispositivo atual
	Long sendTime // Data que o alerta saiu da ultima OBU que ele esteve
	Long distance
	Boolean seen = false
	
    static constraints = {
		messageCode(inList:[1,2,3,4])
		receivedDate(nullable:true)
		port(nullable:true)
		lat(nullable:true)
		lng(nullable:true)
		sendTime(nullable:true)
    }
	
	@Override
	public String toString() {
		return code;
	}
}
