<%@page import="vanet.automotive.NavigationLog"%>
<html>
  <head>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load("visualization", "1", {packages:["map"]});
      google.setOnLoadCallback(drawMap);
      function drawMap() {
        var data = google.visualization.arrayToDataTable([
          ['Lat', 'Lon'],
          <%
		  /*def c = NavigationLog.createCriteria()
		  def result = NavigationLog.where {
			  id == max(id)
		  }.property("latitude").property("longitude").property("gpsDate").get()
		  NavigationLog log = NavigationLog.findAllByGpsDate(result).get(0)
		  println "[${log.latitude},${log.longitude}]"
		  */
		  for(log in NavigationLog.findAll()){
			  println "[${log.latitude},${log.longitude}],"
		  }
		  
          %>
        ]);

        var map = new google.visualization.Map(document.getElementById('map_div'));
        map.draw(data, {showTip: true});
      }

      
    </script>
  </head>

  <body>
    <div id="map_div" style="width: 800px; height: 600px"></div>
  </body>
</html>

