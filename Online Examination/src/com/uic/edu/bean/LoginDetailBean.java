/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  javax.faces.bean.ManagedBean
 *  javax.faces.bean.SessionScoped
 */
package com.uic.edu.bean;

import java.sql.Timestamp;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class LoginDetailBean {
    private int userId;
    private String ipAddress;
    private Timestamp loginTime;
    private Timestamp logoutTime;

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Timestamp getLoginTime() {
        return this.loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public Timestamp getLogoutTime() {
        return this.logoutTime;
    }

    public void setLogoutTime(Timestamp logoutTime) {
        this.logoutTime = logoutTime;
    }
}