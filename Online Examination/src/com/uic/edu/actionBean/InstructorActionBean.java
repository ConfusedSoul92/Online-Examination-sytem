/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.uic.edu.bean.CourseBean
 *  com.uic.edu.bean.MathBean
 *  com.uic.edu.bean.TestBean
 *  com.uic.edu.bean.UserBean
 *  com.uic.edu.databaseAccess.DatabaseAccessBean
 *  com.uic.edu.databaseAccess.DatabaseQueries
 *  javax.faces.application.FacesMessage
 *  javax.faces.bean.ManagedBean
 *  javax.faces.bean.SessionScoped
 *  javax.faces.component.UIData
 *  javax.faces.context.ExternalContext
 *  javax.faces.context.FacesContext
 *  javax.servlet.jsp.jstl.sql.Result
 *  javax.servlet.jsp.jstl.sql.ResultSupport
 *  org.apache.commons.math3.stat.StatUtils
 *  org.apache.myfaces.custom.datascroller.HtmlDataScroller
 *  org.apache.myfaces.custom.fileupload.UploadedFile
 *  org.jfree.chart.ChartFactory
 *  org.jfree.chart.ChartUtilities
 *  org.jfree.chart.JFreeChart
 *  org.jfree.chart.plot.PlotOrientation
 *  org.jfree.data.category.CategoryDataset
 *  org.jfree.data.category.DefaultCategoryDataset
 *  org.jfree.data.general.DefaultPieDataset
 *  org.jfree.data.general.PieDataset
 */
package com.uic.edu.actionBean;

import com.uic.edu.bean.CourseBean;
import com.uic.edu.bean.MathBean;
import com.uic.edu.bean.TestBean;
import com.uic.edu.bean.UserBean;
import com.uic.edu.databaseAccess.DatabaseAccessBean;
import com.uic.edu.databaseAccess.DatabaseQueries;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIData;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.myfaces.custom.datascroller.HtmlDataScroller;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

@ManagedBean
@SessionScoped
public class InstructorActionBean<HttpServletResponse> {
    private UploadedFile uploadedFile;
    private ArrayList<String> tableList = null;
    private ArrayList<Integer> columnList;
    private String[] columnArray;
    private String[][] tableArray;
    private ArrayList<String> colList = null;
    private String selectedTable = "";
    private String selectedTableName = "";
    private String fileType = "";
    private String message = "";
    private boolean renderTable = false;
    private boolean booleanUpload = false;
    private boolean renderInsertData = false;
    private boolean renderMathReport = false;
    private String headersProvided = "N";
    private String primaryKey;
    private HtmlDataScroller htmlScroller;
    private String[][] insertArray;
    private String[][] insertDataArray;
    private ArrayList<CourseBean> teachingCourseList;
    private double[] score;
    double totalMarks = 250.0;
    ResultSet rsTbl;
    UserBean userBean;
    private String selectedCourseId;
    private String selectedCourseCRN;
	private int selectedTestId;
    private boolean renderTest;
    private boolean renderCourseDetails;
    private FacesContext context;
    private HashMap<String[][], Boolean> selected = new HashMap();
    private ExternalContext ec;
    DatabaseAccessBean databaseAccessBean;
    private ArrayList<TestBean> relatedTestList = new ArrayList<TestBean>();
    private ArrayList<MathBean> descriptiveStatsList = new ArrayList<MathBean>();

    public ArrayList<MathBean> getDescriptiveStatsList() {
    	try {
        block6 : {
            if (this.selectedTestId != 0) break block6;
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Please Select a Test from Dropdown and click submit"));
            return null;
        }
            this.descriptiveStatsList = new ArrayList();
            MathBean mathBean = new MathBean();
            String testName = "";
            int j = 0;
            while (j < this.relatedTestList.size()) {
                TestBean tb = this.relatedTestList.get(j);
                if (tb.getTestId() == this.selectedTestId) {
                    testName = tb.getTestName();
                    break;
                }
                ++j;
            }
            String sQuery = "select " + testName + " from f16g323_roster where CRN in (select CRN from f16g323_teach where USER_ID = " + this.userBean.getUserId() + " and COURSE_ID = " + this.selectedCourseId + ");";
            ResultSet rs = this.databaseAccessBean.executeSelectQuery(sQuery);
            rs.last();
            int rowCount = rs.getRow();
            this.score = new double[rowCount];
            rs.beforeFirst();
            int k = 0;
            while (rs.next()) {
                this.score[k] = rs.getDouble(1);
                ++k;
            }
            double minValue = StatUtils.min((double[])this.score);
            double maxValue = StatUtils.max((double[])this.score);
            double mean = StatUtils.mean((double[])this.score);
            double variance = StatUtils.variance((double[])this.score, (double)mean);
            double std = Math.sqrt(variance);
            double median = StatUtils.percentile((double[])this.score, (double)50.0);
            double q1 = StatUtils.percentile((double[])this.score, (double)25.0);
            double q3 = StatUtils.percentile((double[])this.score, (double)75.0);
            double iqr = q3 - q1;
            double range = maxValue - minValue;
            mathBean.setFieldName("Min Marks");
            mathBean.setFieldValue(minValue);
            this.descriptiveStatsList.add(mathBean);
            mathBean = new MathBean();
            mathBean.setFieldName("Max Marks");
            mathBean.setFieldValue(maxValue);
            this.descriptiveStatsList.add(mathBean);
            mathBean = new MathBean();
            mathBean.setFieldName("First Quartile");
            mathBean.setFieldValue(q1);
            this.descriptiveStatsList.add(mathBean);
            mathBean = new MathBean();
            mathBean.setFieldName("Median");
            mathBean.setFieldValue(median);
            this.descriptiveStatsList.add(mathBean);
            mathBean = new MathBean();
            mathBean.setFieldName("Third Quartile");
            mathBean.setFieldValue(q3);
            this.descriptiveStatsList.add(mathBean);
            mathBean = new MathBean();
            mathBean.setFieldName("Range");
            mathBean.setFieldValue(range);
            this.descriptiveStatsList.add(mathBean);
            mathBean = new MathBean();
            mathBean.setFieldName("Standard Deviation");
            mathBean.setFieldValue(std);
            this.descriptiveStatsList.add(mathBean);
            mathBean = new MathBean();
            mathBean.setFieldName("Inter Quartile Range");
            mathBean.setFieldValue(iqr);
            this.descriptiveStatsList.add(mathBean);
            this.createPiechart();
            this.createBar();
        }
        catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error in fetching test marks from Roster. Message" + e.getMessage()));
            e.printStackTrace();
        }
        return this.descriptiveStatsList;
    }
    public String getSelectedCourseCRN() {
		return selectedCourseCRN;
	}

	public void setSelectedCourseCRN(String selectedCourseCRN) {
		this.selectedCourseCRN = selectedCourseCRN;
	}

    public String getSelectedCourseId() {
        return this.selectedCourseId;
    }

    public boolean isRenderMathReport() {
        return this.renderMathReport;
    }

    public void setRenderMathReport(boolean renderMathReport) {
        this.renderMathReport = renderMathReport;
    }

    public void setSelectedCourseId(String selectedCourseId) {
        this.selectedCourseId = selectedCourseId;
    }

    public boolean isRenderCourseDetails() {
        return this.renderCourseDetails;
    }

    public boolean isRenderTest() {
        return this.renderTest;
    }

    public void setRenderTest(boolean renderTest) {
        this.renderTest = renderTest;
    }

    public int getSelectedTestId() {
        return this.selectedTestId;
    }

    public void setSelectedTestId(int selectedTestId) {
        this.selectedTestId = selectedTestId;
    }

    public void setRenderCourseDetails(boolean renderCourseDetails) {
        this.renderCourseDetails = renderCourseDetails;
    }

    public ArrayList<String> getTableList() {
        return this.tableList;
    }

    public String getMessage() {
        return this.message;
    }

    public HashMap<String[][], Boolean> getSelected() {
        return this.selected;
    }

    public void setSelected(HashMap<String[][], Boolean> selected) {
        this.selected = selected;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRenderInsertData() {
        return this.renderInsertData;
    }

    public void setRenderInsertData(boolean renderInsertData) {
        this.renderInsertData = renderInsertData;
    }

    public String getHeadersProvided() {
        return this.headersProvided;
    }

    public void setHeadersProvided(String headersProvided) {
        this.headersProvided = headersProvided;
    }

    public HtmlDataScroller getHtmlScroller() {
        return this.htmlScroller;
    }

    public String[][] getInsertArray() {
        return this.insertArray;
    }

    public void setInsertArray(String[][] insertArray) {
        this.insertArray = insertArray;
    }

    public String[][] getInsertDataArray() {
        return this.insertDataArray;
    }

    public void setInsertDataArray(String[][] insertDataArray) {
        this.insertDataArray = insertDataArray;
    }

    public void setHtmlScroller(HtmlDataScroller htmlScroller) {
        this.htmlScroller = htmlScroller;
    }

    public String getSelectedTable() {
        return this.selectedTable;
    }

    public void setSelectedTable(String selectedTable) {
        this.selectedTable = selectedTable;
    }

    public boolean isRenderTable() {
        return this.renderTable;
    }

    public void setRenderTable(boolean renderTable) {
        this.renderTable = renderTable;
    }

    public boolean isBooleanUpload() {
        return this.booleanUpload;
    }

    public void setBooleanUpload(boolean booleanUpload) {
        this.booleanUpload = booleanUpload;
    }

    public void setTableList(ArrayList<String> tableList) {
        this.tableList = tableList;
    }

    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public ArrayList<Integer> getColumnList() {
        return this.columnList;
    }

    public void setColumnList(ArrayList<Integer> columnList) {
        this.columnList = columnList;
    }

    public String[] getColumnArray() {
        return this.columnArray;
    }

    public void setColumnArray(String[] columnArray) {
        this.columnArray = columnArray;
    }

    public String[][] getTableArray() {
        return this.tableArray;
    }

    public void setTableArray(String[][] tableArray) {
        this.tableArray = tableArray;
    }

    public ArrayList<String> getColList() {
        return this.colList;
    }

    public void setColList(ArrayList<String> colList) {
        this.colList = colList;
    }

    public FacesContext getContext() {
        return this.context;
    }

    public void setContext(FacesContext context) {
        this.context = context;
    }

    public ExternalContext getEc() {
        return this.ec;
    }

    public void setEc(ExternalContext ec) {
        this.ec = ec;
    }

    public DatabaseAccessBean getDatabaseAccessBean() {
        return this.databaseAccessBean;
    }

    public void setDatabaseAccessBean(DatabaseAccessBean databaseAccessBean) {
        this.databaseAccessBean = databaseAccessBean;
    }

    @PostConstruct
    public void init() {
        this.context = FacesContext.getCurrentInstance();
        this.ec = this.context.getExternalContext();
        Map m = this.ec.getSessionMap();
        this.databaseAccessBean = (DatabaseAccessBean)m.get("databaseAccessBean");
        this.userBean = (UserBean)m.get("userBean");
    }

    public ArrayList<CourseBean> getTeachingCourseList() {
        this.teachingCourseList = new ArrayList();
        try {
            String sQuery = "select * from f16g323_courses a where COURSE_ID in (select COURSE_ID from f16g323_teach t where USER_ID = " + this.userBean.getUserId() + " );";
            ResultSet rs = this.databaseAccessBean.executeSelectQuery(sQuery);
            while (rs.next()) {
                CourseBean courseBean = new CourseBean();
                courseBean.setCourseId(rs.getString("COURSE_ID"));
                courseBean.setCourseCode(rs.getString("CODE"));
                courseBean.setCourseDescription(rs.getString("DESCRIPTION"));
                this.teachingCourseList.add(courseBean);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return this.teachingCourseList;
    }

    public String generateMathReport() {
        this.renderMathReport = true;
        return "SUCCESS";
    }

    public ArrayList<TestBean> getRelatedTestList() {
        this.renderTest = true;
        this.renderCourseDetails = true;
        this.relatedTestList = new ArrayList();
        try {
            String sQuery = "select * from f16g323_test where CRN in (select CRN from f16g323_teach where USER_ID = " + this.userBean.getUserId() + " and COURSE_ID = " + this.selectedCourseId + ");";
            ResultSet rs = this.databaseAccessBean.executeSelectQuery(sQuery);
            while (rs.next()) {
                TestBean testBean = new TestBean();
                testBean.setTestId(rs.getInt("TEST_ID"));
                testBean.setTestName(rs.getString("TEST_NAME"));
                testBean.setAvailabilityStart((java.util.Date)rs.getDate("AVAILABILITY_START"));
                testBean.setAvailabilityEnd((java.util.Date)rs.getDate("AVAILABILITY_END"));
                testBean.setDuration(rs.getInt("DURATION"));
                testBean.setCrn(rs.getInt("CRN"));
                this.relatedTestList.add(testBean);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return this.relatedTestList;
    }

    public UploadedFile getUploadedFile() {
        return this.uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    private void resetScrollerIndex() {
        if (this.htmlScroller != null && this.htmlScroller.isPaginator()) {
            this.htmlScroller.getUIData().setFirst(0);
        }
    }

    public String exportFile() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        FileOutputStream fos = null;
        String path = ec.getRealPath("/temp/");
        String fileNameBase = String.valueOf(this.selectedTable) + ".csv";
        String fileName = String.valueOf(path) + "\\" + fileNameBase;
        File f = new File(fileName);
        Result result = ResultSupport.toResult((ResultSet)this.rsTbl);
        Object[][] sData = result.getRowsByIndex();
        String[] columnNames = result.getColumnNames();
        StringBuffer sb = new StringBuffer();
        try {
            fos = new FileOutputStream(f);
            int i = 0;
            while (i < columnNames.length) {
                sb.append(String.valueOf(columnNames[i].toString()) + ",");
                ++i;
            }
            sb.append("\n");
            fos.write(sb.toString().getBytes());
            i = 0;
            while (i < sData.length) {
                sb = new StringBuffer();
                int j = 0;
                while (j < sData[0].length) {
                    sb.append(String.valueOf(sData[i][j].toString()) + ",");
                    ++j;
                }
                sb.append("\n");
                fos.write(sb.toString().getBytes());
                ++i;
            }
            fos.flush();
            fos.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String mimeType = ec.getMimeType(fileName);
        FileInputStream in = null;
        byte b = 0;
        ec.responseReset();
        ec.setResponseContentType(mimeType);
        ec.setResponseContentLength((int)f.length());
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileNameBase + "\"");
        try {
            try {
                in = new FileInputStream(f);
                OutputStream output = ec.getResponseOutputStream();
                while ((b = (byte)in.read()) >= 0) {
                    output.write(b);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                fc.responseComplete();
                try {
                    in.close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
                return "fail";
            }
        }
        finally {
            fc.responseComplete();
            try {
                in.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "SUCCESS";
    }

    public String setFileUpload() {
        this.booleanUpload = true;
        return "SUCCESS";
    }

    public String renderForInsert() {
        try {
            this.renderInsertData = true;
            this.columnList = new ArrayList();
            DatabaseMetaData dbmd = this.databaseAccessBean.getDbMetaData();
            ResultSet colSet = dbmd.getColumns(null, null, this.selectedTableName, null);
            int i2 = 0;
            colSet.last();
            int columnNo = colSet.getRow();
            this.insertArray = new String[1][columnNo];
            this.insertDataArray = new String[1][columnNo];
            colSet.beforeFirst();
            while (colSet.next()) {
                this.insertArray[0][i2] = colSet.getString("COLUMN_NAME");
                this.columnList.add(i2);
                ++i2;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "SUCCESS";
    }

    public String insertData() {
        this.message = "";
        this.renderInsertData = false;
        this.renderTable = false;
        try {
            String query = "";
            int j = 0;
            while (j < this.insertDataArray.length) {
                query = "Insert into " + this.selectedTableName + " values(";
                int i = 0;
                while (i < this.insertDataArray[j].length) {
                    query = String.valueOf(query) + "'" + this.insertDataArray[j][i] + "'";
                    if (i < this.insertDataArray[j].length - 1) {
                        query = String.valueOf(query) + ",";
                    }
                    ++i;
                }
                query = String.valueOf(query) + ");";
                this.message = this.databaseAccessBean.executeUpdateQuery(query);
                ++j;
            }
            this.message = "Data inserted successfully";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(this.message));
        }
        catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error in insert data " + e.getMessage()));
        }
        return "SUCCESS";
    }

    public String getDetails() {
        this.renderCourseDetails = true;
        return "SUCCESS";
    }

    public String deleteData() {
        try {
            boolean unselected = false;
            this.renderTable = false;
            String status = "";
            boolean statusInt = false;
            ArrayList<String> tableid = new ArrayList<String>();
            String tableSelected = (String)DatabaseQueries.class.getDeclaredField(this.selectedTable).get(DatabaseQueries.class);
            int j = 0;
            while (j < this.tableArray.length) {
                if (this.selected.containsKey(this.tableArray[j]) && this.selected.get(this.tableArray[j]).booleanValue()) {
                    tableid.add(this.tableArray[j][0]);
                    unselected = true;
                }
                ++j;
            }
            int i = 0;
            while (i < tableid.size()) {
                String dQuery = "Delete from " + tableSelected + " where " + this.primaryKey + "=" + (String)tableid.get(i) + ";";
                status = this.databaseAccessBean.executeUpdateQuery(dQuery);
                if (!"SUCCESS".equals(status)) {
                    statusInt = true;
                }
                ++i;
            }
            status = !unselected ? "Select rows to delete" : (!statusInt ? "Data deleted successfully" : "Deletion failed");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(status));
            this.selected.clear();
            return "SUCCESS";
        }
        catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error in Deleting Data " + e.getMessage()));
            return "FAIL";
        }
    }
    
    
    public void registerUser(String[] pArr){
    	try {
            String sQuery = "select CRN from f16g323_teach where USER_ID = " + this.userBean.getUserId()+" and COURSE_ID = "+this.selectedCourseId;
            ResultSet rss = this.databaseAccessBean.executeSelectQuery(sQuery);
            if (rss.next()) {
            	this.selectedCourseCRN = rss.getString(1);
                String iQuery = "insert into f16g323_STUDENT_ENROLL (CRN , STUDENT_ID) values("+selectedCourseCRN+", "+pArr[3]+ ")";
                this.databaseAccessBean.executeUpdateQuery(iQuery);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void insertUser(String[] pArr) {
        try {
            String sQuery = "select * from f16g323_users where USER_ID = " + pArr[3];
            ResultSet rss = this.databaseAccessBean.executeSelectQuery(sQuery);
            if (!rss.next()) {
                String iQuery = "insert into f16g323_users (Last_Name,\tFirst_Name,\tUsername,\tuser_id, user_type, password)  values ('" + pArr[0] + "','" + pArr[1] + "','" + pArr[2] + "','" + pArr[3] + "','student','" + pArr[2] + "')";
                this.databaseAccessBean.executeUpdateQuery(iQuery);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String uploadFile() {
        try {
            String tableSelected = (String)DatabaseQueries.class.getDeclaredField(this.selectedTable).get(DatabaseQueries.class);
            String insertCol = (String)DatabaseQueries.class.getDeclaredField(String.valueOf(this.selectedTable) + "Columns").get(DatabaseQueries.class);
            int numberUpload = 0;
            int numberUploadFailed = 0;
            this.message = "";
            Connection con = this.databaseAccessBean.getConn();
            String[] perItem = null;
            String delimiter = "\t";
            if ("c".equals(this.fileType)) {
                delimiter = ",";
            }
            InputStream is = this.uploadedFile.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = br.readLine();
            if ("Y".equals(this.headersProvided)) {
                perItem = line.split(delimiter);
                insertCol = perItem[0];
                int i = 1;
                while (i < perItem.length) {
                    insertCol = String.valueOf(insertCol) + "," + perItem[i];
                    ++i;
                }
            }
            if ("f16g323_ROSTER".equals(tableSelected)) {
                insertCol = String.valueOf(insertCol) + "," + "CRN";
            }
            if ("f16g323_QUESTIONS".equals(tableSelected)) {
                insertCol = String.valueOf(insertCol) + "," + "TEST_ID";
            }
            boolean checkMsg = false;
            if ("Y".equals(this.headersProvided)) {
                line = br.readLine();
            }
            while (line != null) {
                int check;
                PreparedStatement statement;
                perItem = line.split(delimiter);
                String insQuery = "Insert into " + tableSelected + "( " + insertCol + ") values(";
                int i = 0;
                if ("f16g323_ROSTER".equals(tableSelected)) {
                    this.insertUser(perItem);
                    this.registerUser(perItem);
                }
                while (i < perItem.length) {
                    insQuery = String.valueOf(insQuery) + "'" + perItem[i] + "'";
                    if (i < perItem.length - 1) {
                        insQuery = String.valueOf(insQuery) + ",";
                    }
                    ++i;
                }
                if ("f16g323_ROSTER".equals(tableSelected)) {
                    insQuery = String.valueOf(insQuery) + "," + this.selectedCourseCRN;
                }
                if ("f16g323_QUESTIONS".equals(tableSelected)) {
                    insQuery = String.valueOf(insQuery) + "," + this.selectedTestId;
                }
                if ((check = (statement = con.prepareStatement(insQuery = String.valueOf(insQuery) + ");")).executeUpdate()) != 1) {
                    checkMsg = true;
                    ++numberUploadFailed;
                }
                ++numberUpload;
                if ((line = br.readLine()) == null) break;
            }
            this.message = !checkMsg ? String.valueOf(numberUpload) + " Records Uploaded Successfully" : String.valueOf(numberUpload) + " Records Uploaded Successfully and " + numberUploadFailed + " Upload failed";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(this.message));
        }
        catch (IOException e) {
            this.message = "Upload failed Error Message:" + e.getMessage();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(this.message));
            return this.message;
        }
        catch (SQLException e) {
            this.message = "Upload failed for the following error :" + e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(this.message));
            e.printStackTrace();
            return this.message;
        }
        catch (Exception e) {
            this.message = "Upload failed for the following error :" + e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(this.message));
        }
        this.renderInsertData = false;
        this.booleanUpload = false;
        this.renderTable = false;
        return "SUCCESS";
    }

    public String updateDB() {
        String value;
        try {
        block6 : {
            String tableName = (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tableName");
            this.renderTable = false;
            this.renderInsertData = false;
            this.booleanUpload = false;
            this.renderInsertData = false;
            value = (String)DatabaseQueries.class.getDeclaredField(tableName).get(DatabaseQueries.class);
            if (!"SUCCESS".equals(this.databaseAccessBean.executeUpdateQuery(value))) break block6;
            this.message = "Successfully executed the Request";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(this.message));
            return "SUCCESS";
        }
            this.message = "Error Occured for " + value;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(this.message));
            return "FAIL";
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "FAIL";
        }
        catch (SecurityException e) {
            e.printStackTrace();
            return "FAIL";
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
            return "FAIL";
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
            return "FAIL";
        }
    }

    public String viewTable() {
        String tableName = (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tableName");
        Connection con = this.databaseAccessBean.getConn();
        this.resetScrollerIndex();
        String sQuery = null;
        this.message = " ";
        try {
            this.selectedTable = tableName;
            this.selectedTableName = tableName = (String)DatabaseQueries.class.getDeclaredField(tableName).get(DatabaseQueries.class);
            sQuery = "select * from " + tableName + " where CRN in ( select CRN from f16g323_teach where COURSE_ID = " + this.selectedCourseId + " and USER_ID = " + this.userBean.getUserId() + ") ;";
            if ("f16g323_QUESTIONS".equals(tableName) || "f16g323_RESULT".equals(tableName)) {
                sQuery = "select * from " + tableName + " where TEST_ID = " + this.selectedTestId;
            }
            PreparedStatement statement = con.prepareStatement(sQuery);
            this.rsTbl = statement.executeQuery(sQuery);
            ResultSet colSet = this.databaseAccessBean.getDbMetaData().getColumns(null, null, tableName, null);
            int i2 = 0;
            ResultSetMetaData rsmd = this.rsTbl.getMetaData();
            this.rsTbl.beforeFirst();
            int count = rsmd.getColumnCount();
            int k = 0;
            this.rsTbl.last();
            int rowcount = this.rsTbl.getRow();
            this.rsTbl.beforeFirst();
            this.tableArray = new String[rowcount][count];
            colSet.last();
            colSet.beforeFirst();
            colSet.next();
            this.primaryKey = colSet.getString("COLUMN_NAME");
            colSet.beforeFirst();
            this.colList = new ArrayList(count);
            this.columnList = new ArrayList(count);
            this.colList.add("Select");
            while (colSet.next()) {
                this.colList.add(colSet.getString("COLUMN_NAME"));
                this.columnList.add(i2);
                ++i2;
            }
            if (!this.rsTbl.next()) {
                this.message = "No data present in table";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(this.message));
            } else {
                this.rsTbl.beforeFirst();
                while (this.rsTbl.next()) {
                    int i = 1;
                    while (i <= count) {
                        this.tableArray[k][i - 1] = this.rsTbl.getString(i);
                        ++i;
                    }
                    ++k;
                }
                this.rsTbl.beforeFirst();
            }
            this.renderTable = true;
            this.renderInsertData = false;
        }
        catch (Exception e) {
            this.message = "Error :" + e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(this.message));
            e.printStackTrace();
        }
        this.booleanUpload = false;
        return "SUCCESS";
    }

    public String createBar() {
        int i1;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        ArrayList<Double> scoreA = new ArrayList<Double>();
        ArrayList<Double> scoreB = new ArrayList<Double>();
        ArrayList<Double> scoreC = new ArrayList<Double>();
        ArrayList<Double> scoreD = new ArrayList<Double>();
        ArrayList<Double> scoreF = new ArrayList<Double>();
        int i = 0;
        while (i < this.score.length) {
            if (this.score[i] / this.totalMarks >= 0.9) {
                scoreA.add(this.score[i]);
            } else if (this.score[i] / this.totalMarks >= 0.8) {
                scoreB.add(this.score[i]);
            } else if (this.score[i] / this.totalMarks >= 0.7) {
                scoreC.add(this.score[i]);
            } else if (this.score[i] / this.totalMarks >= 0.5) {
                scoreD.add(this.score[i]);
            } else {
                scoreF.add(this.score[i]);
            }
            ++i;
        }
        int k = 0;
        if (scoreA != null) {
            i1 = 0;
            while (i1 < scoreA.size()) {
                dataset.addValue((Number)scoreA.get(i1), (Comparable)((Object)"Grade A"), (Comparable)((Object)("S" + ++k + ":" + scoreA.get(i1))));
                ++i1;
            }
        }
        if (scoreB != null) {
            i1 = 0;
            while (i1 < scoreB.size()) {
                dataset.addValue((Number)scoreB.get(i1), (Comparable)((Object)"Grade B"), (Comparable)((Object)("S" + ++k + ":" + scoreB.get(i1))));
                ++i1;
            }
        }
        if (scoreC != null) {
            i1 = 0;
            while (i1 < scoreC.size()) {
                dataset.addValue((Number)scoreC.get(i1), (Comparable)((Object)"Grade C"), (Comparable)((Object)("S" + ++k + ":" + scoreC.get(i1))));
                ++i1;
            }
        }
        if (scoreD != null) {
            i1 = 0;
            while (i1 < scoreD.size()) {
                dataset.addValue((Number)scoreD.get(i1), (Comparable)((Object)"Grade D"), (Comparable)((Object)("S" + ++k + ":" + scoreD.get(i1))));
                ++i1;
            }
        }
        if (scoreF != null) {
            i1 = 0;
            while (i1 < scoreF.size()) {
                dataset.addValue((Number)scoreF.get(i1), (Comparable)((Object)"Grade F"), (Comparable)((Object)("S" + ++k + ":" + scoreF.get(i1))));
                ++i1;
            }
        }
        JFreeChart chart = ChartFactory.createBarChart3D((String)"Bar Chart", (String)"Category", (String)"Marks", (CategoryDataset)dataset, (PlotOrientation)PlotOrientation.VERTICAL, (boolean)true, (boolean)true, (boolean)false);
        try {
            this.context = FacesContext.getCurrentInstance();
            String path = this.context.getExternalContext().getRealPath("/temp");
            File path1 = new File(String.valueOf(path) + "/" + "bar" + ".png");
            ChartUtilities.saveChartAsJPEG((File)path1, (JFreeChart)chart, (int)600, (int)450);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "SUCCESS";
    }

    public String createPiechart() {
        DefaultPieDataset data = new DefaultPieDataset();
        int i = 0;
        while (i < this.score.length) {
            data.setValue((Comparable)((Object)("Student" + (i + 1))), this.score[i]);
            ++i;
        }
        JFreeChart chart = ChartFactory.createPieChart((String)"Pie Chart", (PieDataset)data, (boolean)true, (boolean)true, (boolean)false);
        try {
            this.context = FacesContext.getCurrentInstance();
            String path = this.context.getExternalContext().getRealPath("/temp");
            File path1 = new File(String.valueOf(path) + "/" + "pie" + ".png");
            ChartUtilities.saveChartAsJPEG((File)path1, (JFreeChart)chart, (int)600, (int)450);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "SUCCESS";
    }
}