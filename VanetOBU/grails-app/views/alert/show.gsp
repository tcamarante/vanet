
<%@ page import="vanet.alert.Alert" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'alert.label', default: 'Alert')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-alert" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-alert" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list alert">
			
				<g:if test="${alertInstance?.messageCode}">
				<li class="fieldcontain">
					<span id="messageCode-label" class="property-label"><g:message code="alert.messageCode.label" default="Message Code" /></span>
					
						<span class="property-value" aria-labelledby="messageCode-label"><g:fieldValue bean="${alertInstance}" field="messageCode"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${alertInstance?.receivedDate}">
				<li class="fieldcontain">
					<span id="receivedDate-label" class="property-label"><g:message code="alert.receivedDate.label" default="Received Date" /></span>
					
						<span class="property-value" aria-labelledby="receivedDate-label"><g:fieldValue bean="${alertInstance}" field="receivedDate"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${alertInstance?.port}">
				<li class="fieldcontain">
					<span id="port-label" class="property-label"><g:message code="alert.port.label" default="Port" /></span>
					
						<span class="property-value" aria-labelledby="port-label"><g:fieldValue bean="${alertInstance}" field="port"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${alertInstance?.lat}">
				<li class="fieldcontain">
					<span id="lat-label" class="property-label"><g:message code="alert.lat.label" default="Lat" /></span>
					
						<span class="property-value" aria-labelledby="lat-label"><g:fieldValue bean="${alertInstance}" field="lat"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${alertInstance?.lng}">
				<li class="fieldcontain">
					<span id="lng-label" class="property-label"><g:message code="alert.lng.label" default="Lng" /></span>
					
						<span class="property-value" aria-labelledby="lng-label"><g:fieldValue bean="${alertInstance}" field="lng"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${alertInstance?.sendTime}">
				<li class="fieldcontain">
					<span id="sendTime-label" class="property-label"><g:message code="alert.sendTime.label" default="Send Time" /></span>
					
						<span class="property-value" aria-labelledby="sendTime-label"><g:fieldValue bean="${alertInstance}" field="sendTime"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${alertInstance?.alertDate}">
				<li class="fieldcontain">
					<span id="alertDate-label" class="property-label"><g:message code="alert.alertDate.label" default="Alert Date" /></span>
					
						<span class="property-value" aria-labelledby="alertDate-label"><g:fieldValue bean="${alertInstance}" field="alertDate"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${alertInstance?.carCode}">
				<li class="fieldcontain">
					<span id="carCode-label" class="property-label"><g:message code="alert.carCode.label" default="Car Code" /></span>
					
						<span class="property-value" aria-labelledby="carCode-label"><g:fieldValue bean="${alertInstance}" field="carCode"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${alertInstance?.code}">
				<li class="fieldcontain">
					<span id="code-label" class="property-label"><g:message code="alert.code.label" default="Code" /></span>
					
						<span class="property-value" aria-labelledby="code-label"><g:fieldValue bean="${alertInstance}" field="code"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${alertInstance?.distance}">
				<li class="fieldcontain">
					<span id="distance-label" class="property-label"><g:message code="alert.distance.label" default="Distance" /></span>
					
						<span class="property-value" aria-labelledby="distance-label"><g:fieldValue bean="${alertInstance}" field="distance"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${alertInstance?.ip}">
				<li class="fieldcontain">
					<span id="ip-label" class="property-label"><g:message code="alert.ip.label" default="Ip" /></span>
					
						<span class="property-value" aria-labelledby="ip-label"><g:fieldValue bean="${alertInstance}" field="ip"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${alertInstance?.message}">
				<li class="fieldcontain">
					<span id="message-label" class="property-label"><g:message code="alert.message.label" default="Message" /></span>
					
						<span class="property-value" aria-labelledby="message-label"><g:fieldValue bean="${alertInstance}" field="message"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${alertInstance?.seen}">
				<li class="fieldcontain">
					<span id="seen-label" class="property-label"><g:message code="alert.seen.label" default="Seen" /></span>
					
						<span class="property-value" aria-labelledby="seen-label"><g:formatBoolean boolean="${alertInstance?.seen}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${alertInstance?.sendDate}">
				<li class="fieldcontain">
					<span id="sendDate-label" class="property-label"><g:message code="alert.sendDate.label" default="Send Date" /></span>
					
						<span class="property-value" aria-labelledby="sendDate-label"><g:fieldValue bean="${alertInstance}" field="sendDate"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:alertInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${alertInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
