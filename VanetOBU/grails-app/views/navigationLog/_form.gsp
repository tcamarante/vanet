<%@ page import="vanet.automotive.NavigationLog" %>



<div class="fieldcontain ${hasErrors(bean: navigationLogInstance, field: 'collectTime', 'error')} ">
	<label for="collectTime">
		<g:message code="navigationLog.collectTime.label" default="Collect Time" />
		
	</label>
	<g:field name="collectTime" type="number" value="${navigationLogInstance.collectTime}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: navigationLogInstance, field: 'obuTime', 'error')} ">
	<label for="obuTime">
		<g:message code="navigationLog.obuTime.label" default="Obu Time" />
		
	</label>
	<g:field name="obuTime" type="number" value="${navigationLogInstance.obuTime}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: navigationLogInstance, field: 'rsuTime', 'error')} ">
	<label for="rsuTime">
		<g:message code="navigationLog.rsuTime.label" default="Rsu Time" />
		
	</label>
	<g:field name="rsuTime" type="number" value="${navigationLogInstance.rsuTime}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: navigationLogInstance, field: 'serverTime', 'error')} ">
	<label for="serverTime">
		<g:message code="navigationLog.serverTime.label" default="Server Time" />
		
	</label>
	<g:field name="serverTime" type="number" value="${navigationLogInstance.serverTime}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: navigationLogInstance, field: 'gpsSpeed', 'error')} ">
	<label for="gpsSpeed">
		<g:message code="navigationLog.gpsSpeed.label" default="Gps Speed" />
		
	</label>
	<g:field name="gpsSpeed" value="${fieldValue(bean: navigationLogInstance, field: 'gpsSpeed')}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: navigationLogInstance, field: 'gpsTime', 'error')} ">
	<label for="gpsTime">
		<g:message code="navigationLog.gpsTime.label" default="Gps Time" />
		
	</label>
	<g:field name="gpsTime" type="number" value="${navigationLogInstance.gpsTime}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: navigationLogInstance, field: 'lon', 'error')} ">
	<label for="lon">
		<g:message code="navigationLog.lon.label" default="Lon" />
		
	</label>
	<g:field name="lon" value="${fieldValue(bean: navigationLogInstance, field: 'lon')}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: navigationLogInstance, field: 'lat', 'error')} ">
	<label for="lat">
		<g:message code="navigationLog.lat.label" default="Lat" />
		
	</label>
	<g:field name="lat" value="${fieldValue(bean: navigationLogInstance, field: 'lat')}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: navigationLogInstance, field: 'obdSpeed', 'error')} ">
	<label for="obdSpeed">
		<g:message code="navigationLog.obdSpeed.label" default="Obd Speed" />
		
	</label>
	<g:field name="obdSpeed" value="${fieldValue(bean: navigationLogInstance, field: 'obdSpeed')}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: navigationLogInstance, field: 'isAirbagOpen', 'error')} ">
	<label for="isAirbagOpen">
		<g:message code="navigationLog.isAirbagOpen.label" default="Is Airbag Open" />
		
	</label>
	<g:checkBox name="isAirbagOpen" value="${navigationLogInstance?.isAirbagOpen}" />

</div>

<div class="fieldcontain ${hasErrors(bean: navigationLogInstance, field: 'lastNavigationLog', 'error')} ">
	<label for="lastNavigationLog">
		<g:message code="navigationLog.lastNavigationLog.label" default="Last Navigation Log" />
		
	</label>
	<g:select id="lastNavigationLog" name="lastNavigationLog.id" from="${vanet.automotive.NavigationLog.list()}" optionKey="id" value="${navigationLogInstance?.lastNavigationLog?.id}" class="many-to-one" noSelection="['null': '']"/>

</div>

<div class="fieldcontain ${hasErrors(bean: navigationLogInstance, field: 'rsu', 'error')} ">
	<label for="rsu">
		<g:message code="navigationLog.rsu.label" default="Rsu" />
		
	</label>
	<g:select id="rsu" name="rsu.id" from="${vanet.automotive.Rsu.list()}" optionKey="id" value="${navigationLogInstance?.rsu?.id}" class="many-to-one" noSelection="['null': '']"/>

</div>

<div class="fieldcontain ${hasErrors(bean: navigationLogInstance, field: 'car', 'error')} ">
	<label for="car">
		<g:message code="navigationLog.car.label" default="Car" />
		
	</label>
	<g:select id="car" name="car.id" from="${vanet.automotive.Car.list()}" optionKey="id" value="${navigationLogInstance?.car?.id}" class="many-to-one" noSelection="['null': '']"/>

</div>

<div class="fieldcontain ${hasErrors(bean: navigationLogInstance, field: 'code', 'error')} required">
	<label for="code">
		<g:message code="navigationLog.code.label" default="Code" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="code" required="" value="${navigationLogInstance?.code}"/>

</div>

