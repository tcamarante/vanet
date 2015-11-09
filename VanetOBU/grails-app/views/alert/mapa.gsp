<%@page import="vanet.automotive.NavigationLog"%>
<%@page import="vanet.automotive.OtherNavigationLog"%>
<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <style type="text/css">
      html { height: 100% }
      body { height: 100%; margin: 0; padding: 0 }
      #map_canvas { height: 100% }
    </style>
    <script type="text/javascript"
      src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDASDPNgmpma4_1exX12PNJvc-oBInx9R4&sensor=false&region=BR">
    </script>
    <script type="text/javascript">
    function initialize() {
		var mapOptions = {
			zoom: 17,
			center: new google.maps.LatLng(-21.227745188567903, -44.97704905231015),
			mapTypeId: google.maps.MapTypeId.ROADMAP
		};

		var map = new google.maps.Map(document.getElementById('map_canvas'),
			mapOptions);

<%--	Experimento 1--%>
<%--rep1--%>
<%--		<g:each in="${NavigationLog.findAll("from NavigationLog where collectTime > 1423333017950 and collectTime <= 1423333249949 order by collectTime")}" var="log" status="i">--%>
<%--		<g:each in="${NavigationLog.findAll("from NavigationLog where collectTime >= 1423333055950 and collectTime <= 1423333109950 order by collectTime")}" var="log" status="i">--%>
<%--		<g:each in="${NavigationLog.findAll("from NavigationLog where collectTime > 1423333078949 and collectTime <= 1423333249949 order by collectTime")}" var="log" status="i">--%>
<%--rep2--%>
<%--		<g:each in="${NavigationLog.findAll("from NavigationLog where collectTime > 1423333840773 and collectTime <= 1423334121774 order by collectTime")}" var="log" status="i">--%>
<%--		<g:each in="${NavigationLog.findAll("from NavigationLog where (collectTime >= 1423333879774 and collectTime <= 1423333942774) or (collectTime >= 1423333957773 and collectTime <= 1423333963774) order by collectTime")}" var="log" status="i">--%>
<%--		<g:each in="${NavigationLog.findAll("from NavigationLog where collectTime > 1423333905773 and collectTime <= 1423334068774 order by collectTime")}" var="log" status="i">--%>
<%--rep3--%>
<%--		<g:each in="${NavigationLog.findAll("from NavigationLog where collectTime > 1423334121774 and collectTime <= 1423334389774 order by collectTime")}" var="log" status="i">--%>
<%--		<g:each in="${NavigationLog.findAll("from NavigationLog where collectTime > 1423334162773  and collectTime <= 1423334212774 order by collectTime")}" var="log" status="i">--%>
<%--		<g:each in="${NavigationLog.findAll("from NavigationLog where collectTime > 1423334183773 and collectTime <= 1423334344773 order by collectTime")}" var="log" status="i">--%>
<%--rep4--%>
<%--		<g:each in="${NavigationLog.findAll("from NavigationLog where collectTime > 1423334389774  and collectTime <= 1423334616773 order by collectTime")}" var="log" status="i">--%>
<%--		<g:each in="${NavigationLog.findAll("from NavigationLog where collectTime > 1423334426773 and collectTime <= 1423334496774 order by collectTime")}" var="log" status="i">--%>
<%--		<g:each in="${NavigationLog.findAll("from NavigationLog where collectTime > 1423334445773 and collectTime <= 1423334609773 order by collectTime")}" var="log" status="i">--%>

<%--Experimento 2 - vanetObu_exp2_valido--%>
<%--rep1--%>
<%--<g:each in="${NavigationLog.findAll("from NavigationLog where collectTime >= 1423688484499 and collectTime <= 1423688825499 order by collectTime")}" var="log" status="i">--%>
<%--<g:each in="${NavigationLog.findAll("from NavigationLog where collectTime >= 1423688522499 and collectTime <= 1423688562499 order by collectTime")}" var="log" status="i">--%>
<%--<g:each in="${NavigationLog.findAll("from NavigationLog where collect_time >= 1423688559233 and collectTime <= 1423688667233 order by collectTime")}" var="log" status="i">--%>
<%--rep2--%>
<%--<g:each in="${NavigationLog.findAll("from NavigationLog where collectTime >= 1423688838499 and collectTime <= 1423689205499 order by collectTime")}" var="log" status="i">--%>
<%--<g:each in="${NavigationLog.findAll("from NavigationLog where collectTime >= 1423688879499 and collectTime <= 1423688952499 order by collectTime")}" var="log" status="i">--%>
<%--<g:each in="${NavigationLog.findAll("from NavigationLog where collect_time >= 1423688939231 and collectTime <= 1423689069231 order by collectTime")}" var="log" status="i">--%>
<%--rep3--%>
<%--<g:each in="${NavigationLog.findAll("from NavigationLog where collectTime >= 1423689213499 and collectTime <= 1423689554499 order by collectTime")}" var="log" status="i">--%>
<%--<g:each in="${NavigationLog.findAll("from NavigationLog where collectTime >= 1423689253499 and collectTime <= 1423689309500 order by collectTime")}" var="log" status="i">--%>
<%--<g:each in="${NavigationLog.findAll("from NavigationLog where collect_time >= 1423689290231 and collectTime <= 1423689413231 order by collectTime")}" var="log" status="i">--%>
<%--rep4--%>
<%--<g:each in="${NavigationLog.findAll("from NavigationLog where collectTime >= 1423689581499 and collectTime <= 1423690020499 order by collectTime")}" var="log" status="i">--%>
<%--<g:each in="${NavigationLog.findAll("from NavigationLog where collectTime >= 1423689704499 and collectTime <= 1423689759499 order by collectTime")}" var="log" status="i">--%>
<%--<g:each in="${NavigationLog.findAll("from NavigationLog where collect_time >= 1423689744232 and collectTime <= 1423689866231 order by collectTime")}" var="log" status="i">--%>
<%--rep5--%>
<%--<g:each in="${NavigationLog.findAll("from NavigationLog where collectTime >= 1423690079500 and collectTime <= 1423690241499 order by collectTime")}" var="log" status="i">--%>
<%--<g:each in="${NavigationLog.findAll("from NavigationLog where collectTime >= 1423690116499 and collectTime <= 1423690157499 order by collectTime")}" var="log" status="i">--%>
<%--<g:each in="${NavigationLog.findAll("from NavigationLog where collect_time >= 1423690154232 and collectTime <= 1423690264232 order by collectTime")}" var="log" status="i">--%>

<%--Exp3--%>
<%--Carro 2 - vanetObu --%>
<%--<g:each in="${NavigationLog.findAll("from NavigationLog where collectTime >= 1427478648548 and collectTime <= 1427478803549 order by collectTime")}" var="log" status="i">--%>
<%--Carro 3 - vanetObu_exp3_car3--%>
<g:each in="${NavigationLog.findAll("from NavigationLog where collectTime >= 1426948876310 and collectTime <= 1426949045310 order by collectTime")}" var="log" status="i">
<%--Carro 4 - vanetObu --%>
<%--<g:each in="${NavigationLog.findAll("from NavigationLog where collectTime >= 1427478865548 and collectTime <= 1427479075548 order by collectTime")}" var="log" status="i">--%>
	<g:if test="${i%3 == 0}">
		var marker${log.id} = new google.maps.Marker({
			position: new google.maps.LatLng(${log.lat},${log.lon}),
			map: map,
			icon:'${resource(dir: 'images', file: 'car3.png')}',
			title: '${i} - ${log.id} - ${log.collectTime}'
		});
		
		var infowindow${log.id} = new google.maps.InfoWindow({
			content: '${i} <br> id:${log.id} <br>Latitude: ${log.lat} <br>Longitude: ${log.lon} <br> ${log.collectTime}'
		});
		
		/*google.maps.event.addListener(map, 'center_changed', function() {
			// 3 seconds after the center of the map has changed, pan back to the
			// marker.
			window.setTimeout(function() {
				map.panTo(marker.getPosition());
			}, 3000);
		});
		*/
		google.maps.event.addListener(marker${log.id}, 'click', function() {
			infowindow${log.id}.open(map,marker${log.id});
		});
		google.maps.event.addListener(marker${log.id}, 'blur', function() {
			infowindow${log.id}.open(map,marker${log.id});
		});
		</g:if>
	</g:each>
<%--	Experimento 2 - carro 2 - vanetObu_exp2_valido --%>
<%--Rep1--%>
<%--<g:each in="${OtherNavigationLog.findAll("from OtherNavigationLog where collect_time >= 1423688559233 and collectTime <= 1423688702233 order by collectTime")}" var="log" status="i">--%>
<%--	<g:each in="${OtherNavigationLog.findAll("from OtherNavigationLog where collect_time >= 1423688559233 and collectTime <= 1423688667233 order by collectTime")}" var="log" status="i">--%>
<%--Rep2--%>
<%--	<g:each in="${OtherNavigationLog.findAll("from OtherNavigationLog where collect_time >= 1423688939231 and collectTime <= 1423689069231 order by collectTime")}" var="log" status="i">--%>
<%--Rep3--%>
<%--	<g:each in="${OtherNavigationLog.findAll("from OtherNavigationLog where collect_time >= 1423689290231 and collectTime <= 1423689413231 order by collectTime")}" var="log" status="i">--%>
<%--Rep4--%>
<%--	<g:each in="${OtherNavigationLog.findAll("from OtherNavigationLog where collect_time >= 1423689744232 and collectTime <= 1423689866231 order by collectTime")}" var="log" status="i">--%>
<%--Rep5--%>
<%--	<g:each in="${OtherNavigationLog.findAll("from OtherNavigationLog where collectTime >= 1423690154232 and collectTime <= 1423690264232 order by collectTime")}" var="log" status="i">--%>
<%--	<g:if test="${i%70 == 0}">--%>
<%--		var marker${log.id} = new google.maps.Marker({--%>
<%--			position: new google.maps.LatLng(${log.lat},${log.lon}),--%>
<%--			map: map,--%>
<%--			icon:'${resource(dir: 'images', file: 'car3.png')}',--%>
<%--			title: '${i} - ${log.id} - ${log.collectTime}'--%>
<%--		});--%>
<%--		--%>
<%--		var infowindow${log.id} = new google.maps.InfoWindow({--%>
<%--			content: '${i} <br> id:${log.id} <br>Latitude: ${log.lat} <br>Longitude: ${log.lon} <br> ${log.collectTime}'--%>
<%--		});--%>
<%--		--%>
<%--		google.maps.event.addListener(marker${log.id}, 'click', function() {--%>
<%--			infowindow${log.id}.open(map,marker${log.id});--%>
<%--		});--%>
<%--		google.maps.event.addListener(marker${log.id}, 'blur', function() {--%>
<%--			infowindow${log.id}.open(map,marker${log.id});--%>
<%--		});--%>
<%--	</g:if>--%>
<%--	</g:each>	--%>
	}

   	google.maps.event.addDomListener(window, 'load', initialize);
    </script>
    
  </head>
  <body onload="initialize()">
    <div id="map_canvas" style="width:100%; height:100%"></div>
  </body>
</html>