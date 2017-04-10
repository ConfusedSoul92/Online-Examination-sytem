<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="t" uri="http://myfaces.apache.org/tomahawk"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student Home</title>
<style>
.customClass {
	background-color: aliceblue;
	width: 80%;
	border-radius: 10px;
	margin-top: 2%;
}
</style>
</head>
<body style="background-color: graytext;">
	<f:view>


		<h:form>
			<!--center> <a href="index.jsp">Home</a> </center> <br-->
			<center
				style="background-color: white; width: 20%; border-radius: 10px">
				<h:commandLink value="Switch User"
					action="#{databaseAccessBean.switchUser}" />
				<br>

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
		<center>
			<h:form id="testList" styleClass="customClass">
				<t:dataTable value="#{studentActionBean.testList}" var="rowNumber"
					border="1" cellspacing="0" cellpadding="1" width="1000">
					<h:column>
						<f:facet name="header">
							<h:outputText>Test Name</h:outputText>
						</f:facet>
						<h:outputText value="#{rowNumber.testName}" />
					</h:column>
					<h:column>
						<f:facet name="header">
							<h:outputText>DueDate</h:outputText>
						</f:facet>
						<h:outputText value="#{rowNumber.availabilityEnd}" />
					</h:column>
					<h:column>
						<f:facet name="header">
							<h:outputText>Status</h:outputText>
						</f:facet>
						<h:commandLink value="Take Test"
							action="#{studentActionBean.takeTest}">
							<f:param name="testId" value="#{rowNumber.testId}" />
						</h:commandLink>
					</h:column>
				</t:dataTable>
			</h:form>
		</center>
		<br>
		<br>
		<center>
			<h:form id="unavailableTestList" styleClass="customClass">
				<t:dataTable value="#{studentActionBean.unavailableTestList}"
					var="rowNumber" border="1" cellspacing="0" cellpadding="1"
					width="1000">
					<h:column>
						<f:facet name="header">
							<h:outputText>Test Name</h:outputText>
						</f:facet>
						<h:outputText value="#{rowNumber.testName}" />
					</h:column>
					<h:column>
						<f:facet name="header">
							<h:outputText>DueDate</h:outputText>
						</f:facet>
						<h:outputText value="#{rowNumber.availabilityEnd}" />
					</h:column>
					<h:column>
						<f:facet name="header">
							<h:outputText>Status</h:outputText>
						</f:facet>
						<h:commandLink value="Get Feedback"
							action="#{studentActionBean.getFeedback}">
							<f:param name="testId" value="#{rowNumber.testId}" />
						</h:commandLink>
					</h:column>
				</t:dataTable>
			</h:form>
		</center>
		<br>
		<br>

		<center>
			<h:form rendered="#{studentActionBean.booleanUnavailableTestList}"
				styleClass="customClass">
				<h:panelGrid>

					<t:dataTable value="#{studentActionBean}" var="row"
						style="table-layout: fixed" border="1" cellspacing="0"
						cellpadding="1" width="1000">

						<t:columns value="#{studentActionBean.colList}" var="column">

							<b> <h:outputText value="#{column}" /></b>
						</t:columns>
					</t:dataTable>

					<t:dataTable value="#{studentActionBean.tableArray}" id="tableData"
						var="row" style="table-layout: fixed" rows="5" border="1"
						cellspacing="0" cellpadding="1" width="1000">
						<t:columns value="#{studentActionBean.columnList}" var="column">

							<h:outputText value="#{row[column]}" />
						</t:columns>
					</t:dataTable>
				</h:panelGrid>
			</h:form>
		</center>
		<center>
			<h:form styleClass="customClass"
				rendered="#{studentActionBean.renderQuestion}">
				<t:dataTable id="questionList"
					value="#{studentActionBean.questionList}"
					rendered="#{studentActionBean.renderQuestion}" var="row" border="1"
					cellspacing="0" cellpadding="1" width="800">
					<h:column>
						<f:facet name="header">
							<h:outputText>QuestionNo</h:outputText>
						</f:facet>
						<h:outputText value="#{row.questionNo}" />
					</h:column>
					<h:column>
						<f:facet name="header">
							<h:outputText>Question Type</h:outputText>
						</f:facet>
						<h:outputText value="#{row.questionType}" />
					</h:column>
					<h:column>
						<f:facet name="header">
							<h:outputText>Question</h:outputText>
						</f:facet>
						<h:outputText value="#{row.questionText}" />
					</h:column>
					<h:column>
						<f:facet name="header">
							<h:outputText>Answer</h:outputText>
						</f:facet>
						<h:inputText value="#{row.answerSubmitted}"></h:inputText>
					</h:column>
				</t:dataTable>
				<br />

				<h:commandButton type="submit" value="Submit"
					rendered="#{studentActionBean.renderQuestion}"
					action="#{studentActionBean.submitTest}" />
			</h:form>
		</center>

	</f:view>
</body>
</html>