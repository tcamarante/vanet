<%@ page import="vanet.alert.Alert" %>



<div class="fieldcontain ${hasErrors(bean: alertInstance, field: 'messageCode', 'error')} required">
	<label for="messageCode">
		<g:message code="alert.messageCode.label" default="Message Code" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="messageCode" from="${alertInstance.constraints.messageCode.inList}" required="" value="${fieldValue(bean: alertInstance, field: 'messageCode')}" valueMessagePrefix="alert.messageCode"/>

</div>

<div class="fieldcontain ${hasErrors(bean: alertInstance, field: 'receivedDate', 'error')} ">
	<label for="receivedDate">
		<g:message code="alert.receivedDate.label" default="Received Date" />
		
	</label>
	<g:field name="receivedDate" type="number" value="${alertInstance.receivedDate}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: alertInstance, field: 'port', 'error')} ">
	<label for="port">
		<g:message code="alert.port.label" default="Port" />
		
	</label>
	<g:textField name="port" value="${alertInstance?.port}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: alertInstance, field: 'lat', 'error')} ">
	<label for="lat">
		<g:message code="alert.lat.label" default="Lat" />
		
	</label>
	<g:field name="lat" value="${fieldValue(bean: alertInstance, field: 'lat')}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: alertInstance, field: 'lng', 'error')} ">
	<label for="lng">
		<g:message code="alert.lng.label" default="Lng" />
		
	</label>
	<g:field name="lng" value="${fieldValue(bean: alertInstance, field: 'lng')}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: alertInstance, field: 'sendTime', 'error')} ">
	<label for="sendTime">
		<g:message code="alert.sendTime.label" default="Send Time" />
		
	</label>
	<g:field name="sendTime" type="number" value="${alertInstance.sendTime}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: alertInstance, field: 'alertDate', 'error')} required">
	<label for="alertDate">
		<g:message code="alert.alertDate.label" default="Alert Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="alertDate" type="number" value="${alertInstance.alertDate}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: alertInstance, field: 'carCode', 'error')} required">
	<label for="carCode">
		<g:message code="alert.carCode.label" default="Car Code" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="carCode" required="" value="${alertInstance?.carCode}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: alertInstance, field: 'code', 'error')} required">
	<label for="code">
		<g:message code="alert.code.label" default="Code" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="code" required="" value="${alertInstance?.code}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: alertInstance, field: 'distance', 'error')} required">
	<label for="distance">
		<g:message code="alert.distance.label" default="Distance" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="distance" type="number" value="${alertInstance.distance}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: alertInstance, field: 'ip', 'error')} required">
	<label for="ip">
		<g:message code="alert.ip.label" default="Ip" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="ip" required="" value="${alertInstance?.ip}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: alertInstance, field: 'message', 'error')} required">
	<label for="message">
		<g:message code="alert.message.label" default="Message" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="message" required="" value="${alertInstance?.message}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: alertInstance, field: 'seen', 'error')} ">
	<label for="seen">
		<g:message code="alert.seen.label" default="Seen" />
		
	</label>
	<g:checkBox name="seen" value="${alertInstance?.seen}" />

</div>

<div class="fieldcontain ${hasErrors(bean: alertInstance, field: 'sendDate', 'error')} required">
	<label for="sendDate">
		<g:message code="alert.sendDate.label" default="Send Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="sendDate" type="number" value="${alertInstance.sendDate}" required=""/>

</div>

