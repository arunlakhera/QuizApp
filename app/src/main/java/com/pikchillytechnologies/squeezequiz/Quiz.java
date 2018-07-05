package com.pikchillytechnologies.squeezequiz;

public class Quiz {

    private String m_QuestionType;
    private String m_Question;
    private String m_Answer1;
    private String m_Answer2;
    private String m_Answer3;
    private String m_Answer4;

    private Boolean m_CorrectAnswer1 = false;
    private Boolean m_CorrectAnswer2 = false;
    private Boolean m_CorrectAnswer3 = false;
    private Boolean m_CorrectAnswer4 = false;
    private String m_CorrectAnswer;

    private Boolean m_UserAnswer1 = false;
    private Boolean m_UserAnswer2 = false;
    private Boolean m_UserAnswer3 = false;
    private Boolean m_UserAnswer4 = false;
    private String m_UserInputAnswer;

    // Method to set question for CheckBox Question (Type: CB)
    Quiz(String questionType,String question, String answer1, String answer2, String answer3, String answer4, Boolean correctAnswer1, Boolean correctAnswer2, Boolean correctAnswer3, Boolean correctAnswer4){

        m_QuestionType = questionType;
        m_Question = question;
        m_Answer1 = answer1;
        m_Answer2 = answer2;
        m_Answer3 = answer3;
        m_Answer4 = answer4;
        m_CorrectAnswer1 = correctAnswer1;
        m_CorrectAnswer2 = correctAnswer2;
        m_CorrectAnswer3 = correctAnswer3;
        m_CorrectAnswer4 = correctAnswer4;
        m_UserInputAnswer = "";
    }

    // Method to set question for RadioButton Question (Type: RB)
    Quiz(String questionType,String question, String answer1, String answer2, String answer3, String answer4, String correctAnswer){

        m_QuestionType = questionType;
        m_Question = question;
        m_Answer1 = answer1;
        m_Answer2 = answer2;
        m_Answer3 = answer3;
        m_Answer4 = answer4;
        m_CorrectAnswer = correctAnswer;
        m_UserInputAnswer = "";
        switch (correctAnswer){
            case "Answer1":
                m_CorrectAnswer1 = true;
                m_CorrectAnswer2 = false;
                m_CorrectAnswer3 = false;
                m_CorrectAnswer4 = false;
                break;
            case "Answer2":
                m_CorrectAnswer1 = false;
                m_CorrectAnswer2 = true;
                m_CorrectAnswer3 = false;
                m_CorrectAnswer4 = false;
                break;
            case "Answer3":
                m_CorrectAnswer1 = false;
                m_CorrectAnswer2 = false;
                m_CorrectAnswer3 = true;
                m_CorrectAnswer4 = false;
                break;
            case "Answer4":
                m_CorrectAnswer1 = false;
                m_CorrectAnswer2 = false;
                m_CorrectAnswer3 = false;
                m_CorrectAnswer4 = true;
                break;
        }

    }

    // Method to set question for User Input Question (Type: UI)
    Quiz(String questionType,String question, String correctAnswer){

        m_QuestionType = questionType;
        m_Question = question;
        m_Answer1 = "";
        m_Answer2 = "";
        m_Answer3 = "";
        m_Answer4 = "";
        m_CorrectAnswer1 = false;
        m_CorrectAnswer2 = false;
        m_CorrectAnswer3 = false;
        m_CorrectAnswer4 = false;
        m_CorrectAnswer = correctAnswer;
        m_UserInputAnswer = "";
    }

    public void setM_UserAnswer1(Boolean m_UserAnswer1) {
        this.m_UserAnswer1 = m_UserAnswer1;
    }

    public void setM_UserAnswer2(Boolean m_UserAnswer2) {
        this.m_UserAnswer2 = m_UserAnswer2;
    }

    public void setM_UserAnswer3(Boolean m_UserAnswer3) {
        this.m_UserAnswer3 = m_UserAnswer3;
    }

    public void setM_UserAnswer4(Boolean m_UserAnswer4) {
        this.m_UserAnswer4 = m_UserAnswer4;
    }

    public void setM_UserInputAnswer(String m_UserInputAnswer) {
        this.m_UserInputAnswer = m_UserInputAnswer;
    }

    public String getM_QuestionType() {
        return m_QuestionType;
    }

    public String getM_Question() {
        return m_Question;
    }

    public Boolean getM_UserAnswer1() {
        return m_UserAnswer1;
    }

    public Boolean getM_UserAnswer2() {
        return m_UserAnswer2;
    }

    public Boolean getM_UserAnswer3() {
        return m_UserAnswer3;
    }

    public Boolean getM_UserAnswer4() {
        return m_UserAnswer4;
    }

    public String getM_Answer1() {
        return m_Answer1;
    }

    public String getM_Answer2() {
        return m_Answer2;
    }

    public String getM_Answer3() {
        return m_Answer3;
    }

    public String getM_Answer4() {
        return m_Answer4;
    }

    public Boolean getM_CorrectAnswer1() {
        return m_CorrectAnswer1;
    }

    public Boolean getM_CorrectAnswer2() {
        return m_CorrectAnswer2;
    }

    public Boolean getM_CorrectAnswer3() {
        return m_CorrectAnswer3;
    }

    public Boolean getM_CorrectAnswer4() {
        return m_CorrectAnswer4;
    }

    public String getM_CorrectAnswer() {
        return m_CorrectAnswer;
    }

    public String getM_UserInputAnswer() {
        return m_UserInputAnswer;
    }

}
