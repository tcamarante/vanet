
<%@ page import="vanet.automotive.NavigationLog" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'navigationLog.label', default: 'NavigationLog')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-navigationLog" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-navigationLog" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="collectTime" title="${message(code: 'navigationLog.collectTime.label', default: 'Collect Time')}" />
					
						<g:sortableColumn property="obuTime" title="${message(code: 'navigationLog.obuTime.label', default: 'Obu Time')}" />
					
						<g:sortableColumn property="rsuTime" title="${message(code: 'navigationLog.rsuTime.label', default: 'Rsu Time')}" />
					
						<g:sortableColumn property="serverTime" title="${message(code: 'navigationLog.serverTime.label', default: 'Server Time')}" />
					
						<g:sortableColumn property="gpsSpeed" title="${message(code: 'navigationLog.gpsSpeed.label', default: 'Gps Speed')}" />
					
						<g:sortableColumn property="gpsTime" title="${message(code: 'navigationLog.gpsTime.label', default: 'Gps Time')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${navigationLogInstanceList}" status="i" var="navigationLogInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${navigationLogInstance.id}">${fieldValue(bean: navigationLogInstance, field: "collectTime")}</g:link></td>
					
						<td>${fieldValue(bean: navigationLogInstance, field: "obuTime")}</td>
					
						<td>${fieldValue(bean: navigationLogInstance, field: "rsuTime")}</td>
					
						<td>${fieldValue(bean: navigationLogInstance, field: "serverTime")}</td>
					
						<td>${fieldValue(bean: navigationLogInstance, field: "gpsSpeed")}</td>
					
						<td>${fieldValue(bean: navigationLogInstance, field: "gpsTime")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${navigationLogInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
