
<%@ page import="vanet.alert.Alert" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'alert.label', default: 'Alert')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
		<script type="text/javascript"
	    	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDASDPNgmpma4_1exX12PNJvc-oBInx9R4&sensor=false&region=BR">
	    </script>
	    <g:set var="alertInstance" value="${alertInstanceList[0]}"></g:set>
   		<script type="text/javascript">
			var map
			var marker
			var alertMarker
			var mapOptions
   			function initialize() {
				mapOptions = {
						zoom: 17,
						center: new google.maps.LatLng(${alertInstance.lat},${alertInstance.lng}),
						mapTypeId: google.maps.MapTypeId.ROADMAP
				};

				map = new google.maps.Map(document.getElementById('map_canvas'),mapOptions);

				marker = new google.maps.Marker({
					position: new google.maps.LatLng(${alertInstance.lat},${alertInstance.lng}),
					map: map,
					icon:'${resource(dir: 'images', file: 'car2.png')}',
					title: 'Acidente'
				});
	
				var infowindow = new google.maps.InfoWindow({
					content: 'Nada ainda'
<%--						content: '${i} <br> id:${log.id} <br>Latitude: ${log.lat} <br>Longitude: ${log.lon} <br> ${log.collectTime}'--%>
				});
	
				/*google.maps.event.addListener(map, 'center_changed', function() {
					// 3 seconds after the center of the map has changed, pan back to the
					// marker.
					window.setTimeout(function() {
						map.panTo(marker.getPosition());
					}, 3000);
				});
				*/
				google.maps.event.addListener(marker, 'click', function() {
					infowindow.open(map,marker);
				});
				google.maps.event.addListener(marker, 'blur', function() {
					infowindow.open(map,marker);
				});
			}

			google.maps.event.addDomListener(window, 'load', initialize);

			function monitoraAlerta() {
			    setInterval(function(){ 
			    	$.ajax({
			    	    url:"${g.createLink(controller:'alert',action:'monitoraAlerta')}",
			    	    dataType: 'html',
			    	    success: function(data) {
				    	    var lat = data.split(" ")[0];
				    	    var lng = data.split(" ")[1];
			    	        //alert(data)
							alertMarker = new google.maps.Marker({
								position: new google.maps.LatLng(${alertInstance.lat},${alertInstance.lng}),
								map: map,
								icon:'${resource(dir: 'images', file: 'car2.png')}',
								title: 'Acidente'
							});
						    //var latlng = new google.maps.LatLng(lat, lng);
						    //marker.setPosition(latlng);
			    	    },
			    	    error: function(request, status, error) {
			    	        alert(error)
			    	    },
			    	    complete: function() {
			    	    }
			    	}); 
			    }, 20000);
			}

			function monitoraPosicao() {
				setInterval(function(){ 
			    	$.ajax({
			    	    url:"${g.createLink(controller:'navigationLog',action:'monitoraPosicao')}",
			    	    dataType: 'json',
			    	    success: function(data) {
				    	    var lat = data.lat//split(" ")[0];
				    	    var lng = data.lon//split(" ")[1];
			    	        //alert(data)
						    var latlng = new google.maps.LatLng(lat, lng);
						    marker.setPosition(latlng);
			    	    },
			    	    error: function(request, status, error) {
			    	        alert(error)
			    	    },
			    	    complete: function() {
			    	    }
			    	}); 
			    }, 2000);
			}
			monitoraPosicao();
			monitoraAlerta();
   		</script>
	</head>
	<body onload="initialize()">
			<div id="map_canvas" class="map" ></div>
			<div class="details">
				<div id="show-alert" class="content scaffold-show" role="main">
					<h3>Details</h3>
					<table style="width:100%;margin-left:0em;">
						<tr>
							<td>
								<g:if test="${alertInstance?.code}">
									<b><span id="code-label" class="property-label"><g:message code="alert.code.label" default="Code" /></span></b>
									<span class="property-value" id="code" aria-labelledby="code-label"><g:fieldValue id="code" bean="${alertInstance}" field="code"/></span>
								</g:if>
							</td>
							<td>
								<g:if test="${alertInstance?.carCode}">
									<b><span id="carCode-label" class="property-label"><g:message code="alert.carCode.label" default="Car Code" /></span></b>
									<span class="property-value" id="carCode" aria-labelledby="carCode-label"><g:fieldValue bean="${alertInstance}" field="carCode"/></span>
								</g:if>
							</td>
						</tr>
						<tr>
							<td>
								<g:if test="${alertInstance?.alertDate}">
									<b><span id="alertDate-label" class="property-label"><g:message code="alert.alertDate.label" default="Alert Date" /></span></b>
									<span class="property-value" id="alertDate" aria-labelledby="alertDate-label"><g:fieldValue id="alertDate" bean="${alertInstance}" field="alertDate"/></span>
								</g:if>
							</td>
							<td>
								<g:if test="${alertInstance?.receivedDate}">
									<b><span id="receivedDate-label" class="property-label"><g:message code="alert.receivedDate.label" default="Received Date" /></span></b>
									<span class="property-value" id="receivedDate" aria-labelledby="receivedDate-label"><g:fieldValue bean="${alertInstance}" field="receivedDate"/></span>
								</g:if>
							</td>
						</tr>
						<tr>
							<td>
								<g:if test="${alertInstance?.lat}">
									<b><span id="lat-label" class="property-label"><g:message code="alert.lat.label" default="Latitude" /></span></b>
									<span class="property-value" id="lat" aria-labelledby="lat-label"><g:fieldValue bean="${alertInstance}" field="lat"/></span>
								</g:if>
							</td>
							<td>
								<g:if test="${alertInstance?.lng}">
									<b><span id="lng-label" class="property-label"><g:message code="alert.lng.label" default="Longitude" /></span></b>
									<span class="property-value" id="lng" aria-labelledby="lng-label"><g:fieldValue bean="${alertInstance}" field="lng"/></span>
								</g:if>
							</td>
						</tr>
						<tr>
							<td>					
								<g:if test="${alertInstance?.message}">
									<b><span id="message-label" class="property-label"><g:message code="alert.message.label" default="Message" /></span></b>
									<span class="property-value" id="message" aria-labelledby="message-label"><g:fieldValue bean="${alertInstance}" field="message"/></span>
								</g:if>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			var id
			$(".alert").click(function(){
				id = jQuery(this).attr('id');
				updateDetails(id);
			});

			function updateDetails(id){
				$.ajax({
				    url:"${g.createLink(controller:'alert',action:'show')}/"+".json",
				    dataType: 'json',
				    data: {
				        id: id,
				    },
				    success: function(data) {
					    $('#alertDate').html(data.alertDate)
					    $('#code').html(data.code)
					    $('#carCode').html(data.carCode)
					    $('#receivedDate').html(data.receivedDate)
					    $('#lat').html(data.lat)
					    $('#lng').html(data.lng)
					    $('#message').html(data.message)

					    marker.setMap(null)
					    
					    marker = new google.maps.Marker({
							position: new google.maps.LatLng(data.lat, data.lng),
							map: map,
							icon:'${resource(dir: 'images', file: 'car2.png')}',
							title: 'Acidente'
						});
				    },
				    error: function(request, status, error) {
				        alert(error)
				    },
				    complete: function() {
				    }
				});

				function loadAlerts(){
					$.ajax({
						url:"${g.createLink(controller:'alert',action:'index')}/"+".json",
					    dataType: 'json',
					    data: {
					        max: 10,
					        offset: 0
					    },
					    success: function(data) {
					    	
						    //alert(data)
					    },
					    error: function(request, status, error) {
					        alert(error)
					    },
					    complete: function() {
					    }
					})
				}

				//myFunction()
				function myFunction() {
				     setTimeout(function(){ loadAlerts(); myFunction();}, 3000);
				}
			
			}
			</script>
	</body>
</html>
