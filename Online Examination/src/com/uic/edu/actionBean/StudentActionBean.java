/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.uic.edu.bean.QuestionsBean
 *  com.uic.edu.bean.TestBean
 *  com.uic.edu.bean.UserBean
 *  com.uic.edu.databaseAccess.DatabaseAccessBean
 *  javax.faces.application.FacesMessage
 *  javax.faces.bean.ManagedBean
 *  javax.faces.bean.SessionScoped
 *  javax.faces.context.ExternalContext
 *  javax.faces.context.FacesContext
 */
package com.uic.edu.actionBean;

import com.uic.edu.bean.QuestionsBean;
import com.uic.edu.bean.TestBean;
import com.uic.edu.bean.UserBean;
import com.uic.edu.databaseAccess.DatabaseAccessBean;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class StudentActionBean {
    private ArrayList<QuestionsBean> questionList;
    private ArrayList<TestBean> testList;
    private ArrayList<TestBean> unavailableTestList;
    private boolean renderQuestion = false;
    private boolean booleanUnavailableTestList = false;
    private String[][] tableArray;
    private ArrayList<String> colList = null;
    private ArrayList<Integer> columnList;
    private String primaryKey;
    private String message = "";
    private int selectedTestId;
    private FacesContext context;
    private ExternalContext ec;
    UserBean userBean;
    DatabaseAccessBean databaseAccessBean;

    @PostConstruct
    public void init() {
        this.context = FacesContext.getCurrentInstance();
        this.ec = this.context.getExternalContext();
        Map m = this.ec.getSessionMap();
        this.databaseAccessBean = (DatabaseAccessBean)m.get("databaseAccessBean");
        this.userBean = (UserBean)m.get("userBean");
    }

    public boolean isBooleanUnavailableTestList() {
        return this.booleanUnavailableTestList;
    }

    public void setBooleanUnavailableTestList(boolean booleanUnavailableTestList) {
        this.booleanUnavailableTestList = booleanUnavailableTestList;
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

    public ArrayList<Integer> getColumnList() {
        return this.columnList;
    }

    public void setColumnList(ArrayList<Integer> columnList) {
        this.columnList = columnList;
    }

    public String getPrimaryKey() {
        return this.primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTestList(ArrayList<TestBean> testList) {
        this.testList = testList;
    }

    public void setUnavailableTestList(ArrayList<TestBean> unavailableTestList) {
        this.unavailableTestList = unavailableTestList;
    }

    public ArrayList<TestBean> getUnavailableTestList() {
        this.unavailableTestList = new ArrayList();
        try {
            String sQuery = "select * from f16g323_TEST where CRN in (select CRN from f16g323_STUDENT_ENROLL where STUDENT_ID = " + this.userBean.getUserId() + ") and AVAILABILITY_END <= NOW();";
            Connection con = this.databaseAccessBean.getConn();
            PreparedStatement statement = con.prepareStatement(sQuery);
            ResultSet rs = statement.executeQuery(sQuery);
            if (!rs.next()) {
                this.message = "No Test Available";
                this.context = FacesContext.getCurrentInstance();
                this.context.addMessage(null, new FacesMessage(this.message));
            } else {
                rs.beforeFirst();
                while (rs.next()) {
                    TestBean testBean = new TestBean();
                    testBean.setTestId(rs.getInt("TEST_ID"));
                    testBean.setTestName(rs.getString("TEST_NAME"));
                    testBean.setAvailabilityStart((java.util.Date)rs.getDate("AVAILABILITY_START"));
                    testBean.setAvailabilityEnd((java.util.Date)rs.getDate("AVAILABILITY_END"));
                    testBean.setDuration(rs.getInt("DURATION"));
                    testBean.setCrn(rs.getInt("CRN"));
                    this.unavailableTestList.add(testBean);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return this.unavailableTestList;
    }

    public ArrayList<TestBean> getTestList() {
        this.testList = new ArrayList();
        try {
            String sQuery = "select * from f16g323_TEST where CRN in (select CRN from f16g323_STUDENT_ENROLL where STUDENT_ID = " + this.userBean.getUserId() + ") AND AVAILABILITY_START <= NOW() AND AVAILABILITY_END >= NOW();";
            Connection con = this.databaseAccessBean.getConn();
            PreparedStatement statement = con.prepareStatement(sQuery);
            ResultSet rs = statement.executeQuery(sQuery);
            if (!rs.next()) {
                this.message = "No Test Available";
                this.context = FacesContext.getCurrentInstance();
                this.context.addMessage(null, new FacesMessage(this.message));
            } else {
                rs.beforeFirst();
                while (rs.next()) {
                    TestBean testBean = new TestBean();
                    testBean.setTestId(rs.getInt("TEST_ID"));
                    testBean.setTestName(rs.getString("TEST_NAME"));
                    testBean.setAvailabilityStart((java.util.Date)rs.getDate("AVAILABILITY_START"));
                    testBean.setAvailabilityEnd((java.util.Date)rs.getDate("AVAILABILITY_END"));
                    testBean.setDuration(rs.getInt("DURATION"));
                    testBean.setCrn(rs.getInt("CRN"));
                    this.testList.add(testBean);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return this.testList;
    }

    public String takeTest() {
        this.renderQuestion = true;
        this.selectedTestId = Integer.parseInt((String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("testId"));
        this.questionList = new ArrayList();
        try {
            String sQuery = "select * from f16g323_QUESTIONS where TEST_ID = " + this.selectedTestId;
            Connection con = this.databaseAccessBean.getConn();
            PreparedStatement statement = con.prepareStatement(sQuery);
            ResultSet rs = statement.executeQuery(sQuery);
            int questionNumber = 1;
            while (rs.next()) {
                QuestionsBean questionBean = new QuestionsBean();
                questionBean.setQuestionNo(questionNumber);
                questionBean.setQuestionText(rs.getString("QUESTION_TEXT"));
                questionBean.setQuestionType(rs.getString("QUESTION_TYPE"));
                questionBean.setCorrectAnswer(rs.getString("CORRECT_ANSWER"));
                questionBean.setTolerance(rs.getString("TOLERANCE"));
                questionBean.setAnswerSubmitted("");
                this.questionList.add(questionBean);
                ++questionNumber;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "SUCCESS";
    }

    public String getFeedback() {
        Connection con = this.databaseAccessBean.getConn();
        String sQuery = null;
        String tableName = "f16g323_RESULT";
        this.message = " ";
        this.booleanUnavailableTestList = true;
        try {
            sQuery = "select * from " + tableName + " ;";
            PreparedStatement statement = con.prepareStatement(sQuery);
            ResultSet rsTbl = statement.executeQuery(sQuery);
            ResultSet colSet = this.databaseAccessBean.getDbMetaData().getColumns(null, null, tableName, null);
            int i2 = 0;
            ResultSetMetaData rsmd = rsTbl.getMetaData();
            rsTbl.beforeFirst();
            int count = rsmd.getColumnCount();
            int k = 0;
            rsTbl.last();
            int rowcount = rsTbl.getRow();
            rsTbl.beforeFirst();
            this.tableArray = new String[rowcount][count];
            colSet.last();
            colSet.beforeFirst();
            colSet.next();
            this.primaryKey = colSet.getString("COLUMN_NAME");
            colSet.beforeFirst();
            this.colList = new ArrayList(count);
            this.columnList = new ArrayList(count);
            while (colSet.next()) {
                this.colList.add(colSet.getString("COLUMN_NAME"));
                this.columnList.add(i2);
                ++i2;
            }
            if (!rsTbl.next()) {
                this.message = "No data present in table";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(this.message));
            } else {
                rsTbl.beforeFirst();
                while (rsTbl.next()) {
                    int i = 1;
                    while (i <= count) {
                        this.tableArray[k][i - 1] = rsTbl.getString(i);
                        ++i;
                    }
                    ++k;
                }
                rsTbl.beforeFirst();
            }
        }
        catch (Exception e) {
            this.message = "Error :" + e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(this.message));
            e.printStackTrace();
        }
        return "SUCCESS";
    }

    public String submitTest() {
        this.renderQuestion = false;
        try {
            String iQuery2;
            String testName = "";
            int total = 0;
            int i = 0;
            while (i < this.questionList.size()) {
                int questionId = this.questionList.get(i).getQuestionId();
                String answerSubmitted = this.questionList.get(i).getAnswerSubmitted();
                String answer = this.questionList.get(i).getCorrectAnswer();
                String tolerance = this.questionList.get(i).getTolerance();
                double tolAns = Double.parseDouble(answer)- Double.parseDouble(tolerance);
                double tolAnsPlus = Double.parseDouble(answer)+ Double.parseDouble(tolerance);
                int result = 0;
                if (answerSubmitted == null) {
                    answerSubmitted = "Not Answered";
                }
                if (answer.equals(answerSubmitted) || (Double.toString(tolAns)).equals(answerSubmitted) || (Double.toString(tolAnsPlus)).equals(answerSubmitted) ) {
                    result = 1;
                }
                total += result;
                String iQuery = "Insert into f16g323_RESULT values( " + this.selectedTestId + "," + this.userBean.getUserId() + "," + questionId + ",'" + answer + "' , '" + answerSubmitted + "'," + result + ");";
                Connection con = this.databaseAccessBean.getConn();
                PreparedStatement statement = con.prepareStatement(iQuery);
                statement.executeUpdate(iQuery);
                ++i;
            }
            int j = 0;
            while (j < this.testList.size()) {
                TestBean tb = this.testList.get(j);
                if (tb.getTestId() == this.selectedTestId) {
                    testName = tb.getTestName();
                    break;
                }
                ++j;
            }
            String sQuery = "select " + testName + " from f16g323_ROSTER where STUDENT_ID = " + this.userBean.getUserId();
            ResultSet rs1 = this.databaseAccessBean.executeSelectQuery(sQuery);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Test Submitted"));
            if (rs1.next() && this.databaseAccessBean.executeUpdateQuery(iQuery2 = "update f16g323_roster set " + testName + " = " + total + " where STUDENT_ID = " + this.userBean.getUserId() + ";").equals("SUCCESS")) {
                context.addMessage(null, new FacesMessage("Successfully"));
            }
        }
        catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error in Submitting the Test. Message " + e.getMessage()));
            e.printStackTrace();
            return "FAIL";
        }
        return "Success";
    }

    public ArrayList<QuestionsBean> getQuestionList() {
        return this.questionList;
    }

    public void setQuestionList(ArrayList<QuestionsBean> questionList) {
        this.questionList = questionList;
    }

    public boolean isRenderQuestion() {
        return this.renderQuestion;
    }

    public void setRenderQuestion(boolean renderQuestion) {
        this.renderQuestion = renderQuestion;
    }
}