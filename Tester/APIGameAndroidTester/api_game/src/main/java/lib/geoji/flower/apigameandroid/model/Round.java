package lib.geoji.flower.apigameandroid.model;

import com.google.gson.annotations.Expose;

public class Round {
    public enum Type {
        OX, CHOICE, SUBJECTIVE
    }

    @Expose private Type type;

    @Expose private String question;
    @Expose private String imageUrl;
    @Expose private String[] choices;
    private String solution;
    @Expose private int answerTimeLimit;

    public Round() { }

    public Round(Type type, String question, String imageUrl, String[] choices, String solution, int answerTimeLimit) {
        this.type = type;
        this.question = question;
        this.imageUrl = imageUrl;
        this.choices = choices;
        this.solution = solution;
        this.answerTimeLimit = answerTimeLimit;
    }

    public void initOX(String question, boolean isAnswerTrue, String imageUrl, int answerTimeLimit) {
        this.question = question;
        this.imageUrl = imageUrl;
        this.answerTimeLimit = answerTimeLimit;

        this.type = Type.OX;

        if (isAnswerTrue) {
            this.solution = "1";
        }
        else {
            this.solution = "0";
        }
    }

    public void initChoice(String question, String[] choices, String solution, String imageUrl, int answerTimeLimit) {
        this.question = question;
        this.choices = choices;
        this.solution = solution;
        this.imageUrl = imageUrl;
        this.answerTimeLimit = answerTimeLimit;

        this.type = Type.CHOICE;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public void setAnswerTimeLimit(int answerTimeLimit) {
        this.answerTimeLimit = answerTimeLimit;
    }

    public Type getType() {
        return type;
    }

    public int getAnswerTimeLimit() {
        return answerTimeLimit;
    }

    public String getQuestion() {
        return question;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String[] getChoices() {
        return choices;
    }

    public String getSolution() {
        return solution;
    }
}
