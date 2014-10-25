package vanet.alert

class Alert {

	String code
	Integer messageCode // 1-OK, 2-Atencao provavel acidente, 3- Acidente, 4-Outros
	String message //Utilizado apenas com codigo 4
	String ip
	String port
	String car
	Double lat
	Double lng
	Date alertDate
	
    static constraints = {
		messageCode(inList:[1,2,3,4])
    }
}
