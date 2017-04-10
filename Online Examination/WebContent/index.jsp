<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>f16g323_sshani4</title>
<style>
.customClass {
	background-color: aliceblue;
	width: 40%;
	border-radius: 10px;
	margin-top: 2%;
}
</style>
</head>
<body style="background-color: graytext;">
	<f:view>
		<h3>IDS517 f16g323_ssahni4</h3>
		<h:form>
			<center
				style="background-color: white; width: 20%; border-radius: 10px; margin-top: 2%">
				<a href="Documentation/user_guide.jsp" target="_blank">User's
					Guide</a> <br> <a href="Documentation/programmerDocumentation.jsp"
					target="_blank">Programmer's Documentation</a>
			</center>
		</h:form>
		<center
			style="background-color: appworkspace; width: 50%; border-radius: 10px">
			<h:messages globalOnly="true" />
		</center>
	<center>
		<h:form styleClass="customClass">
			<h3><h:outputText value='Connect to Database' style="font-weight:bold" /></h3>
			<h:inputHidden value="#{userBean.username}" />
			<h:inputHidden value="#{loginDetailBean.userId}" />
			<h:panelGrid columns="3">
				<h:outputText value='UserName' />
				<h:inputText id="userName"
					value='#{databaseAccessInformationBean.userName}' required="true"
					requiredMessage="Please Enter Username" />
				<h:message for="userName" showSummary="true" showDetail="false"></h:message>
				<h:outputText value='Password' />
				<h:inputSecret value='#{databaseAccessInformationBean.password}'
					id="password" required="true"
					requiredMessage="Please Enter Password" />
				<h:message for="password" showSummary="true" showDetail="false"></h:message>
				<h:outputText value='Database Server' />
				<h:selectOneListbox id="db"
					value="#{databaseAccessInformationBean.dbms}" required="true"
					requiredMessage="Please select a database">
					<f:selectItem itemValue="MySQL" itemLabel="My SQL" />
					<f:selectItem itemValue="Oracle" itemLabel="Oracle" />
					<f:selectItem itemValue="DB2" itemLabel="DB2" />
				</h:selectOneListbox>
				<h:message for="db" showSummary="true" showDetail="false"></h:message>
				<h:outputText value='HostName' />
				<h:selectOneMenu id="host"
					value="#{databaseAccessInformationBean.dbmsHost}" required="true"
					requiredMessage="Please select the server">
					<f:selectItem itemValue="" itemLabel="<select>" />
					<f:selectItem itemValue="131.193.209.54" itemLabel="Server 54" />
					<f:selectItem itemValue="131.193.209.57" itemLabel="Server 57" />
					<f:selectItem itemValue="localhost" itemLabel="localhost" />
				</h:selectOneMenu>
				<h:message for="host" showSummary="true" showDetail="false"></h:message>
				<h:outputText value='Schema' />
				<h:inputText id="schema"
					value='#{databaseAccessInformationBean.databaseSchema}'
					required="true" requiredMessage="Please Enter Schema" />
				<h:message for="schema" showSummary="true" showDetail="false"></h:message>


				<h:commandButton action="#{databaseAccessBean.createConnection}"
					value="Connect" style="margin-top:20%;margin-left:100px" />


			</h:panelGrid>

		</h:form>
</center>

	</f:view>
</body>
</html>