<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="t" uri="http://myfaces.apache.org/tomahawk"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Instructor</title>
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
			<h:form
				style="background-color: appworkspace; width: 50%; border-radius: 10px; margin-top:5%; margin-bottom:5%">
				<h:selectOneMenu id="selectCourse"
					value="#{instructorActionBean.selectedCourseId}" required="true"
					requiredMessage="Please select a course"
					style="width:40%; margin-top:2%;border-radius:10px">
					<f:selectItem itemValue="" itemLabel="Select" />
					<f:selectItems value="#{instructorActionBean.teachingCourseList}"
						var="courseBean" itemLabel="#{courseBean.courseCode}"
						itemValue="#{courseBean.courseId}" />
				</h:selectOneMenu>
				<h:message for="selectCourse" showSummary="true" showDetail="false"></h:message>
				<br>
				<h:commandLink value="Get Test List For Selected Course"
					action="#{instructorActionBean.getRelatedTestList}" />
			</h:form>
		</center>
		<center>
			<h:form rendered="#{instructorActionBean.renderTest}"
				style="background-color: appworkspace; width: 50%; border-radius: 10px">
				<h:selectOneMenu id="selectTest"
					value="#{instructorActionBean.selectedTestId}" required="true"
					requiredMessage="Please select a Test"
					style="width:40%; margin-top:2%;border-radius:10px">
					<f:selectItem itemValue="" itemLabel="Select" />
					<f:selectItems value="#{instructorActionBean.relatedTestList}"
						var="testBean" itemLabel="#{testBean.testName}"
						itemValue="#{testBean.testId}" />
				</h:selectOneMenu>
				<h:message for="selectTest" showSummary="true" showDetail="false"></h:message>

				<br>
				<h:commandLink value="Submit"
					action="#{instructorActionBean.getDetails}" />
			</h:form>
		</center>

		<h:form rendered="#{instructorActionBean.renderCourseDetails}">
			<center>
				<div
					style="background-color: aliceblue; width: 95%; border-radius: 10px; margin-top: 2%">

					<h:panelGrid columns="8" cellspacing="20">
						<h:commandLink action="#{instructorActionBean.viewTable}"
							value="View available Test">
							<f:param name="tableName" value="testTable" />
						</h:commandLink>
						<h:commandLink action="#{instructorActionBean.viewTable}"
							value="View Questions">
							<f:param name="tableName" value="questionTable" />
						</h:commandLink>
						<h:commandLink action="#{instructorActionBean.viewTable}"
							value="View Roster">
							<f:param name="tableName" value="rosterTable" />
						</h:commandLink>
						<h:commandLink action="#{instructorActionBean.viewTable}"
							value="View Result">
							<f:param name="tableName" value="resultTable" />
						</h:commandLink>
						<h:commandLink action="#{instructorActionBean.generateMathReport}"
							value="Generate Statistical Result">
						</h:commandLink>
					</h:panelGrid>
				</div>
			</center>
		</h:form>
		<center>
			<center
				style="background-color: aliceblue; width: 95%; border-radius: 10px; margin-top: 2%">
				<h:form rendered="#{instructorActionBean.renderTable}">
					<h:panelGrid>

						<t:dataTable value="#{instructorActionBean}" var="row"
							style="table-layout: fixed" border="1" cellspacing="0"
							cellpadding="1" width="1270">

							<t:columns value="#{instructorActionBean.colList}" var="column">

								<b> <h:outputText value="#{column}" /></b>
							</t:columns>
						</t:dataTable>

						<t:dataTable value="#{instructorActionBean.tableArray}"
							id="tableData" var="row" style="table-layout: fixed" rows="5"
							border="1" cellspacing="0" cellpadding="1" width="1270">
							<h:column id="column1">
								<h:selectBooleanCheckbox id="sbc1"
									value="#{instructorActionBean.selected[row]}" />
							</h:column>

							<t:columns value="#{instructorActionBean.columnList}"
								var="column">

								<h:outputText value="#{row[column]}" />
							</t:columns>

						</t:dataTable>
						<t:dataScroller id="dataScrollerId" for="tableData" fastStep="10"
							pageIndexVar="pageIndex" renderFacetsIfSinglePage="false"
							pageCountVar="pageCount" paginator="true" paginatorMaxPages="9"
							immediate="true" binding="#{instructorActionBean.htmlScroller}">
							<f:facet name="first">
								<t:outputText value="<<"></t:outputText>
							</f:facet>
							<f:facet name="last">
								<t:outputText value=">>"></t:outputText>
							</f:facet>
							<f:facet name="previous">
								<t:outputText value="<"></t:outputText>
							</f:facet>
							<f:facet name="next">
								<t:outputText value=">"></t:outputText>
							</f:facet>
						</t:dataScroller>

					</h:panelGrid>

					<h:commandLink action="#{instructorActionBean.setFileUpload}"
						value="Upload Data">
					</h:commandLink>&nbsp;&nbsp;&nbsp;
					<h:commandLink action="#{instructorActionBean.exportFile}"
						value="Export Data">
					</h:commandLink>&nbsp;&nbsp;&nbsp;
					<h:commandLink action="#{instructorActionBean.renderForInsert}"
						value="Insert Data">
					</h:commandLink>&nbsp;&nbsp;&nbsp;
					<h:commandLink action="#{instructorActionBean.deleteData}"
						value="Delete Selected Data">
					</h:commandLink>
				</h:form>
			</center>
		</center>
		<center>
			<center
				style="background-color: aliceblue; width: 90%; border-radius: 10px; margin-top: 2%">
				<h:form rendered="#{instructorActionBean.renderInsertData}">
					<t:dataTable value="#{instructorActionBean.insertArray}" var="row"
						style="table-layout: fixed" border="1" cellspacing="0"
						cellpadding="1" width="1100">

						<t:columns value="#{instructorActionBean.columnList}" var="column">
							<h:outputText value="#{row[column]}" />
						</t:columns>
					</t:dataTable>
					<t:dataTable value="#{instructorActionBean.insertDataArray}"
						var="row" style="table-layout: fixed" border="1" cellspacing="0"
						cellpadding="1" width="1100">

						<t:columns value="#{instructorActionBean.columnList}" var="column">
							<h:inputText size="14" id="o3" value="#{row[column]}"></h:inputText>
						</t:columns>
					</t:dataTable>
					<h:commandLink action="#{instructorActionBean.insertData}"
						value="Insert Record">
					</h:commandLink>&nbsp;&nbsp;&nbsp;
					
			</h:form>
			</center>
		</center>

		<h:form enctype="multipart/form-data"
			rendered="#{instructorActionBean.booleanUpload}">
			<div style="margin-left: 5%;" class="customClass">
				<div style="text-align: center;">
					<h3>
						<h:outputLabel value="Please Select file to be Uploaded:" />
					</h3>
					<t:inputFileUpload value="#{instructorActionBean.uploadedFile}"
						storage="file" />
					<h5>
						<h:outputLabel value="Select File Type:" />
					</h5>
					<h:selectOneListbox value="#{instructorActionBean.fileType}"
						size="2">
						<f:selectItem itemValue="t" itemLabel="Tab Delimited" />
						<f:selectItem itemValue="c" itemLabel="Comma Seperated" />
					</h:selectOneListbox>
					<h5>
						<h:outputLabel value="Column Headers Provided:" />
					</h5>
					<h:selectOneListbox value="#{instructorActionBean.headersProvided}"
						size="2">
						<f:selectItem itemValue="Y" itemLabel="Yes" />
						<f:selectItem itemValue="N" itemLabel="No" />
					</h:selectOneListbox>
					<br>
					<h:commandLink action="#{instructorActionBean.uploadFile}"
						value="Upload">
					</h:commandLink>
				</div>
			</div>
		</h:form>
		<center>
			<h:form rendered="#{instructorActionBean.renderMathReport}"
				style="background-color: appworkspace; width: 98%; border-radius: 10px">
				<t:dataTable value="#{instructorActionBean.descriptiveStatsList}"
					var="mathRow" style="table-layout: fixed" border="1"
					cellspacing="0" cellpadding="1" width="1200">
					<h:column>
						<f:facet name="header">
							<h:outputText>Statistical Parameter</h:outputText>
						</f:facet>
						<h:outputText value="#{mathRow.fieldName}" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText>Values</h:outputText>
						</f:facet>
						<h:outputText value="#{mathRow.fieldValue}" />
					</h:column>

				</t:dataTable>
				<h:graphicImage value="temp/pie.png" height="450"
					width="600"  alt="barChart" />
				<h:graphicImage value="temp/bar.png" height="450"
					width="600"  alt="barChart" />
					
			</h:form>
		</center>
	</f:view>
</body>
</html>