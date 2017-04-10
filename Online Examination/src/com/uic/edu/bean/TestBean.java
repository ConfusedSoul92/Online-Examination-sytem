package com.uic.edu.bean;

import java.util.Date;

public class TestBean {
    private int testId;
    private int crn;
    private String testName;
    private Date availabilityStart;
    private Date availabilityEnd;
    private int duration;

    public int getTestId() {
        return this.testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getCrn() {
        return this.crn;
    }

    public void setCrn(int crn) {
        this.crn = crn;
    }

    public String getTestName() {
        return this.testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Date getAvailabilityStart() {
        return this.availabilityStart;
    }

    public void setAvailabilityStart(Date availabilityStart) {
        this.availabilityStart = availabilityStart;
    }

    public Date getAvailabilityEnd() {
        return this.availabilityEnd;
    }

    public void setAvailabilityEnd(Date availabilityEnd) {
        this.availabilityEnd = availabilityEnd;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}