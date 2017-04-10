
package com.uic.edu.databaseAccess;

import com.uic.edu.bean.LoginDetailBean;
import com.uic.edu.bean.UserBean;
import com.uic.edu.databaseAccess.DatabaseAccessInformationBean;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@SessionScoped
public class DatabaseAccessBean {
    Connection conn = null;
    DatabaseMetaData dbMetaData;
    PreparedStatement statement;
    ResultSet resultSet;
    ResultSet rs;
    ResultSetMetaData resultSetMetaData;
    private String message = " ";
    private FacesContext context;
    private static final String[] TABLE_TYPES = new String[]{"TABLE", "VIEW"};
    DatabaseAccessInformationBean databaseAccessInformationBean;
    UserBean userBean;
    LoginDetailBean loginDetailBean;

    @PostConstruct
    public void init() {
        this.context = FacesContext.getCurrentInstance();
        Map m = this.context.getExternalContext().getSessionMap();
        this.databaseAccessInformationBean = (DatabaseAccessInformationBean)m.get("databaseAccessInformationBean");
        this.userBean = (UserBean)m.get("userBean");
        this.loginDetailBean = (LoginDetailBean)m.get("loginDetailBean");
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Connection getConn() {
        return this.conn;
    }

    public DatabaseMetaData getDbMetaData() {
        return this.dbMetaData;
    }

    public PreparedStatement getStatement() {
        return this.statement;
    }

    public ResultSet getResultSet() {
        return this.resultSet;
    }

    public ResultSet getRs() {
        return this.rs;
    }

    public ResultSetMetaData getResultSetMetaData() {
        return this.resultSetMetaData;
    }

    public void getUrl() {

		String jdbcDriver = null;
		String DB_URL = null;
		String host = null;
		String dbSchema = null;

		if (databaseAccessInformationBean != null) {
			host = databaseAccessInformationBean.getDbmsHost();
			dbSchema = databaseAccessInformationBean.getDatabaseSchema();
		}

		switch (databaseAccessInformationBean.getDbms()) {
		case "MySQL":
			jdbcDriver = "com.mysql.jdbc.Driver";
			DB_URL = "jdbc:mysql://" + host + ":3306/" + dbSchema;
			break;

		case "DB2":
			jdbcDriver = "com.ibm.db2.jcc.DB2Driver";
			DB_URL = "jdbc:db2://" + host + ":50000/" + dbSchema;
			break;

		case "Oracle":
			jdbcDriver = "oracle.jdbc.driver.OracleDriver";
			DB_URL = "jdbc:oracle:thin:@" + host + ":1521:" + dbSchema;
			break;
		}
		databaseAccessInformationBean.setUrl(DB_URL);
		databaseAccessInformationBean.setJdbcDriver(jdbcDriver);
    }

    public String createConnection() {
    	try {
        block6 : {
            this.message = " ";
            this.getUrl();
            Class.forName(this.databaseAccessInformationBean.getJdbcDriver());
            this.conn = DriverManager.getConnection(this.databaseAccessInformationBean.getUrl(), this.databaseAccessInformationBean.getUserName(), this.databaseAccessInformationBean.getPassword());
            this.dbMetaData = this.conn.getMetaData();
            if (this.conn == null) break block6;
            ResultSet res = this.dbMetaData.getTables(null, null, "f16g323_USERS", new String[]{"TABLE"});
            if (!res.next()) {
                this.setupDB();
            }
            return "/UserLogin.jsp?faces-redirect=true";
        }
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Connection failed"));
            return "False";
        }
        catch (ClassNotFoundException ce) {
            this.message = "Connection Failed! Error: " + ce.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(this.message));
            ce.printStackTrace();
        }
        catch (SQLException se) {
            this.message = "Connection Failed! Error: " + se.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(this.message));
            se.printStackTrace();
        }
        catch (Exception e) {
            this.message = "Connection Failed! Error: " + e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(this.message));
            e.printStackTrace();
        }
        return "SUCCESS";
    }

    public void closeResources() {
        try {
            if (this.resultSet != null) {
                this.resultSet.close();
            }
            if (this.rs != null) {
                this.rs.close();
            }
            if (this.statement != null) {
                this.statement.close();
            }
            if (this.conn != null) {
                this.conn.close();
            }
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void setupDB() {
    }

    public String validateUser() {
        this.message = "";
        if (this.userBean.getUsername().equalsIgnoreCase("admin") && this.userBean.getPassword().equals("admin")) {
            return "admin";
        }
        try {
            String sQuery = "select * from f16g323_USERS where USERNAME = '" + this.userBean.getUsername() + "' and PASSWORD = '" + this.userBean.getPassword() + "';";
            Connection con = this.getConn();
            ResultSet rs = this.getRs();
            PreparedStatement statement = con.prepareStatement(sQuery);
            rs = statement.executeQuery(sQuery);
            if (rs.next()) {
                this.userBean.setUserId(Integer.valueOf(rs.getInt("user_id")));
                this.userBean.setFirstName(rs.getString("first_name"));
                this.userBean.setUser_type(rs.getString("USER_TYPE"));
                this.userBean.setLastName(rs.getString("last_name"));
                this.setLoginDetailBean();
                return this.userBean.getUser_type();
            }
            this.message = "Invalid Credentials";
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(this.message));
            return "false";
        }
        catch (SQLException sqle) {
            this.message = "Login Failed " + sqle.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(this.message));
            sqle.printStackTrace();
            return "false";
        }
        catch (Exception e) {
            this.message = "Login Failed " + e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(this.message));
            e.printStackTrace();
            return "false";
        }
    }

    public void setLoginDetailBean() {
        this.loginDetailBean.setUserId(this.userBean.getUserId().intValue());
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        this.loginDetailBean.setLoginTime(timestamp);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)ec.getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        this.loginDetailBean.setIpAddress(ipAddress);
    }

    public void insertLoggingDetails() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        this.loginDetailBean.setLogoutTime(timestamp);
        String iQuery = "insert into f16g323_LOGIN_DETAILS(USER_ID, IP_ADDRESS, LOGIN_TIME, LOGOUT_TIME) values(" + this.loginDetailBean.getUserId() + ",'" + this.loginDetailBean.getIpAddress() + "','" + this.loginDetailBean.getLoginTime() + "','" + this.loginDetailBean.getLogoutTime() + "')";
        this.executeUpdateQuery(iQuery);
    }

    public void updateData(String tableName, Scanner scan) {
        List<String> colNames = this.getColumnNames(tableName);
        try {
            System.out.println("Please Select a Column Name whose value you want to update");
            int j = scan.nextInt();
            System.out.println("Please Enter the new Column Value for Column " + colNames.get(j) + " : ");
            String s = scan.next();
            this.getColumnNames(tableName);
            System.out.println("Please Select a Column Name against which you want to update the row(for where Clause)");
            int k = scan.nextInt();
            System.out.println("Please Enter the Column Value for Column (for where clause)" + colNames.get(k) + " : ");
            String w = scan.next();
            String query = "UPDATE " + tableName + " set " + colNames.get(j) + " = '" + s + "'  where " + colNames.get(k) + " = '" + w + "'";
            System.out.println("Query To be Executed  : " + query);
            this.statement = this.conn.prepareStatement(query);
            this.statement.executeUpdate(query);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteData(String tableName) {
        List<String> colNames = this.getColumnNames(tableName);
        try {
            System.out.println("Please Select a Column Name against which you want to delete the row");
            int j = 2;
            System.out.println("Please Enter the Column Value for Column " + colNames.get(j) + " : ");
            String s = "12";
            String query = "DELETE FROM " + tableName + " where " + colNames.get(j) + " = '" + s + "'";
            System.out.println("Query To be Executed  : " + query);
            this.statement = this.conn.prepareStatement(query);
            this.statement.executeUpdate(query);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewData(String tableName, Scanner scan) {
        List<String> colNames = this.getColumnNames(tableName);
        try {
            String query;
            System.out.println(String.valueOf(colNames.size()) + "                                          All Columns");
            System.out.println("Please Select a Column Name whose value you want to view");
            int j = scan.nextInt();
            if (j == colNames.size()) {
                System.out.println("Showing all column's data for table : " + tableName);
                query = "Select * from " + tableName;
            } else {
                System.out.println("      " + colNames.get(j));
                query = "Select " + colNames.get(j) + " from " + tableName;
            }
            this.statement = this.conn.prepareStatement(query);
            this.resultSet = this.statement.executeQuery(query);
            while (this.resultSet.next()) {
                if (j == colNames.size()) {
                    StringBuilder builder = new StringBuilder();
                    int a = 1;
                    while (a < colNames.size()) {
                        builder.append(this.resultSet.getString(a));
                        builder.append("              ");
                        ++a;
                    }
                    System.out.println(builder);
                    continue;
                }
                System.out.println("       " + this.resultSet.getString(1));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getColumnNames(String tableName) {
        ArrayList<String> columnList = new ArrayList<String>();
        try {
            this.resultSet = this.dbMetaData.getColumns(null, null, tableName, null);
            String columnName = "";
            if (this.resultSet != null) {
                System.out.println("S. Number                               Column Name");
                while (this.resultSet.next()) {
                    columnName = this.resultSet.getString("COLUMN_NAME");
                    columnList.add(columnName);
                    System.out.println(String.valueOf(columnList.size() - 1) + "                                          " + columnName);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return columnList;
    }

    public List<String> getTablenames(PrintWriter out) {
        ArrayList<String> tableList = new ArrayList<String>();
        try {
            this.rs = this.dbMetaData.getTables(null, null, null, TABLE_TYPES);
            this.rs.last();
            this.rs.beforeFirst();
            String tableName = "";
            if (this.rs != null) {
                while (this.rs.next()) {
                    tableName = this.rs.getString("TABLE_NAME");
                    if (tableName.length() < 4) {
                        tableList.add(tableName);
                    } else if (!tableName.substring(0, 4).equalsIgnoreCase("BIN$")) {
                        tableList.add(tableName);
                    }
                    out.println(String.valueOf(tableList.size()) + ".) " + tableName + "<br><br>");
                }
            }
        }
        catch (SQLException tableName) {
            // empty catch block
        }
        return tableList;
    }

    public String executeUpdateQuery(String pQuery) {
        try {
            this.statement = this.conn.prepareStatement(pQuery);
            this.statement.executeUpdate(pQuery);
            return "SUCCESS";
        }
        catch (SQLException sqle) {
            this.message = sqle.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(this.message));
            sqle.printStackTrace();
            return "FAIL";
        }
        catch (Exception e) {
            this.message = e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(this.message));
            e.printStackTrace();
            return "FAIL";
        }
    }

    public ResultSet executeSelectQuery(String pQuery) {
        this.rs = null;
        try {
            this.statement = this.conn.prepareStatement(pQuery);
            this.rs = this.statement.executeQuery(pQuery);
        }
        catch (SQLException sqle) {
            this.message = sqle.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(this.message));
            sqle.printStackTrace();
        }
        catch (Exception e) {
            this.message = e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(this.message));
            e.printStackTrace();
        }
        return this.rs;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        this.insertLoggingDetails();
        this.closeResources();
        return "/index.jsp?faces-redirect=true";
    }

    public String switchUser() {
        this.insertLoggingDetails();
        return "/UserLogin.jsp?faces-redirect=true";
    }
}