package fi.xamk.tietosuoja.models;

/**
 * Created by marko on 6.6.2017.
 */

public class QuestionModel {

    private String Question;
    private String Answer1;
    private String Answer2;
    private String Answer3;
    private String Answer4;
    private int CorrectAnswer;
    private String CorrectArg;
    private String IncorrectArg;
    private String Topic;

    public String getQuestion() { return Question; }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer1() {
        return Answer1;
    }

    public void setAnswer1(String answer1) {
        Answer1 = answer1;
    }

    public String getAnswer2() {
        return Answer2;
    }

    public void setAnswer2(String answer2) {
        Answer2 = answer2;
    }

    public String getAnswer3() {
        return Answer3;
    }

    public void setAnswer3(String answer3) {
        Answer3 = answer3;
    }

    public String getAnswer4() {
        return Answer4;
    }

    public void setAnswer4(String answer4) {
        Answer4 = answer4;
    }

    public String getCorrectArg() {
        return CorrectArg;
    }

    public void setCorrectArg(String correctArg) {
        CorrectArg = correctArg;
    }

    public String getIncorrectArg() {
        return IncorrectArg;
    }

    public void setIncorrectArg(String incorrectArg) {
        IncorrectArg = incorrectArg;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public int getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) { CorrectAnswer = correctAnswer; }

}