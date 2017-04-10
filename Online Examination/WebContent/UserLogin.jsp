<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
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

		<h:form>
			<center
				style="background-color: white; width: 20%; border-radius: 10px">
				<h:commandLink value="Logout" action="#{databaseAccessBean.logout}" />
			</center>
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
		<center><center>
		<h:form styleClass="customClass">

			<h:outputText value='Enter Your Credentials' style="font-weight:bold" />
			<br />
			<br />
			<h:panelGrid columns="3">
				<h:outputText value='UserName' />
				<h:inputText id="userName" value='#{userBean.username}'
					required="true" requiredMessage="Please Enter Username" />
				<h:message for="userName" showSummary="true" showDetail="false"></h:message>
				<h:outputText value='Password' />
				<h:inputSecret value='#{userBean.password}' id="password"
					required="true" requiredMessage="Please Enter Password" />
				<h:message for="password" showSummary="true" showDetail="false"></h:message>
				<h:commandButton action="#{databaseAccessBean.validateUser}"
					value="UserLogin" />
			</h:panelGrid>
		</h:form>
		</center></center>
	</f:view>
</body>
</html>