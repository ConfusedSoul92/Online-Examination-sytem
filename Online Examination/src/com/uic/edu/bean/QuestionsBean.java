package com.uic.edu.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class QuestionsBean {
    private int questionNo;
    private int questionId;
    private String questionType;
    private String questionText;
    private String correctAnswer;
    private String tolerance;
    private String answerSubmitted;

    public int getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuestionNo() {
        return this.questionNo;
    }

    public void setQuestionNo(int questionNo) {
        this.questionNo = questionNo;
    }

    public String getQuestionType() {
        return this.questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestionText() {
        return this.questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getCorrectAnswer() {
        return this.correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getTolerance() {
        return this.tolerance;
    }

    public void setTolerance(String tolerance) {
        this.tolerance = tolerance;
    }

    public String getAnswerSubmitted() {
        return this.answerSubmitted;
    }

    public void setAnswerSubmitted(String answerSubmitted) {
        this.answerSubmitted = answerSubmitted;
    }
}