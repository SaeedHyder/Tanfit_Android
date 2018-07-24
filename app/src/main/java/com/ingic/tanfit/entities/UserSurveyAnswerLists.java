package com.ingic.tanfit.entities;

/**
 * Created by saeedhyder on 6/12/2018.
 */

public class UserSurveyAnswerLists {

    String UserId;
    String FitnessClassId;
    String CancelBookingSurveyQuestionId;
    String Answer;

    public UserSurveyAnswerLists(String userId, String fitnessClassId, String cancelBookingSurveyQuestionId, String answer) {
        UserId = userId;
        FitnessClassId = fitnessClassId;
        CancelBookingSurveyQuestionId = cancelBookingSurveyQuestionId;
        Answer = answer;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getFitnessClassId() {
        return FitnessClassId;
    }

    public void setFitnessClassId(String fitnessClassId) {
        FitnessClassId = fitnessClassId;
    }

    public String getCancelBookingSurveyQuestionId() {
        return CancelBookingSurveyQuestionId;
    }

    public void setCancelBookingSurveyQuestionId(String cancelBookingSurveyQuestionId) {
        CancelBookingSurveyQuestionId = cancelBookingSurveyQuestionId;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

}
