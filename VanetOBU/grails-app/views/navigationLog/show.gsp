
<%@ page import="vanet.automotive.NavigationLog" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'navigationLog.label', default: 'NavigationLog')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-navigationLog" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-navigationLog" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list navigationLog">
			
				<g:if test="${navigationLogInstance?.collectTime}">
				<li class="fieldcontain">
					<span id="collectTime-label" class="property-label"><g:message code="navigationLog.collectTime.label" default="Collect Time" /></span>
					
						<span class="property-value" aria-labelledby="collectTime-label"><g:fieldValue bean="${navigationLogInstance}" field="collectTime"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${navigationLogInstance?.obuTime}">
				<li class="fieldcontain">
					<span id="obuTime-label" class="property-label"><g:message code="navigationLog.obuTime.label" default="Obu Time" /></span>
					
						<span class="property-value" aria-labelledby="obuTime-label"><g:fieldValue bean="${navigationLogInstance}" field="obuTime"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${navigationLogInstance?.rsuTime}">
				<li class="fieldcontain">
					<span id="rsuTime-label" class="property-label"><g:message code="navigationLog.rsuTime.label" default="Rsu Time" /></span>
					
						<span class="property-value" aria-labelledby="rsuTime-label"><g:fieldValue bean="${navigationLogInstance}" field="rsuTime"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${navigationLogInstance?.serverTime}">
				<li class="fieldcontain">
					<span id="serverTime-label" class="property-label"><g:message code="navigationLog.serverTime.label" default="Server Time" /></span>
					
						<span class="property-value" aria-labelledby="serverTime-label"><g:fieldValue bean="${navigationLogInstance}" field="serverTime"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${navigationLogInstance?.gpsSpeed}">
				<li class="fieldcontain">
					<span id="gpsSpeed-label" class="property-label"><g:message code="navigationLog.gpsSpeed.label" default="Gps Speed" /></span>
					
						<span class="property-value" aria-labelledby="gpsSpeed-label"><g:fieldValue bean="${navigationLogInstance}" field="gpsSpeed"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${navigationLogInstance?.gpsTime}">
				<li class="fieldcontain">
					<span id="gpsTime-label" class="property-label"><g:message code="navigationLog.gpsTime.label" default="Gps Time" /></span>
					
						<span class="property-value" aria-labelledby="gpsTime-label"><g:fieldValue bean="${navigationLogInstance}" field="gpsTime"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${navigationLogInstance?.lon}">
				<li class="fieldcontain">
					<span id="lon-label" class="property-label"><g:message code="navigationLog.lon.label" default="Lon" /></span>
					
						<span class="property-value" aria-labelledby="lon-label"><g:fieldValue bean="${navigationLogInstance}" field="lon"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${navigationLogInstance?.lat}">
				<li class="fieldcontain">
					<span id="lat-label" class="property-label"><g:message code="navigationLog.lat.label" default="Lat" /></span>
					
						<span class="property-value" aria-labelledby="lat-label"><g:fieldValue bean="${navigationLogInstance}" field="lat"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${navigationLogInstance?.obdSpeed}">
				<li class="fieldcontain">
					<span id="obdSpeed-label" class="property-label"><g:message code="navigationLog.obdSpeed.label" default="Obd Speed" /></span>
					
						<span class="property-value" aria-labelledby="obdSpeed-label"><g:fieldValue bean="${navigationLogInstance}" field="obdSpeed"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${navigationLogInstance?.isAirbagOpen}">
				<li class="fieldcontain">
					<span id="isAirbagOpen-label" class="property-label"><g:message code="navigationLog.isAirbagOpen.label" default="Is Airbag Open" /></span>
					
						<span class="property-value" aria-labelledby="isAirbagOpen-label"><g:formatBoolean boolean="${navigationLogInstance?.isAirbagOpen}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${navigationLogInstance?.lastNavigationLog}">
				<li class="fieldcontain">
					<span id="lastNavigationLog-label" class="property-label"><g:message code="navigationLog.lastNavigationLog.label" default="Last Navigation Log" /></span>
					
						<span class="property-value" aria-labelledby="lastNavigationLog-label"><g:link controller="navigationLog" action="show" id="${navigationLogInstance?.lastNavigationLog?.id}">${navigationLogInstance?.lastNavigationLog?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${navigationLogInstance?.rsu}">
				<li class="fieldcontain">
					<span id="rsu-label" class="property-label"><g:message code="navigationLog.rsu.label" default="Rsu" /></span>
					
						<span class="property-value" aria-labelledby="rsu-label"><g:link controller="rsu" action="show" id="${navigationLogInstance?.rsu?.id}">${navigationLogInstance?.rsu?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${navigationLogInstance?.car}">
				<li class="fieldcontain">
					<span id="car-label" class="property-label"><g:message code="navigationLog.car.label" default="Car" /></span>
					
						<span class="property-value" aria-labelledby="car-label"><g:link controller="car" action="show" id="${navigationLogInstance?.car?.id}">${navigationLogInstance?.car?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${navigationLogInstance?.code}">
				<li class="fieldcontain">
					<span id="code-label" class="property-label"><g:message code="navigationLog.code.label" default="Code" /></span>
					
						<span class="property-value" aria-labelledby="code-label"><g:fieldValue bean="${navigationLogInstance}" field="code"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:navigationLogInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${navigationLogInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
