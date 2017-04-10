package com.uic.edu.databaseAccess;

import java.util.ArrayList;

public class DatabaseAccessInformationBean {
    String userName;
    String password;
    String dbms;
    String dbmsHost;
    String databaseSchema;
    String jdbcDriver;
    String url;
    ArrayList<String> databaseTypes = new ArrayList();

    public DatabaseAccessInformationBean() {
        this.databaseTypes.add("DB2");
        this.databaseTypes.add("MySQL");
        this.databaseTypes.add("Oracle");
        this.userName = "root";
        this.password = "";
        this.dbms = "MySQL";
        this.dbmsHost = "localhost";
        this.databaseSchema = "quiz";
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbms() {
        return this.dbms;
    }

    public void setDbms(String dbms) {
        this.dbms = dbms;
    }

    public String getDbmsHost() {
        return this.dbmsHost;
    }

    public void setDbmsHost(String dbmsHost) {
        this.dbmsHost = dbmsHost;
    }

    public String getDatabaseSchema() {
        return this.databaseSchema;
    }

    public String getJdbcDriver() {
        return this.jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDatabaseSchema(String databaseSchema) {
        this.databaseSchema = databaseSchema;
    }

    public ArrayList<String> getAvailableDatabaseType() {
        return this.databaseTypes;
    }

    public void addDatabaseTypes(String databaseType) {
        this.databaseTypes.add(databaseType);
    }
}