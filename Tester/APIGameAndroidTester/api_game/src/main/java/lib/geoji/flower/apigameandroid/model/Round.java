package lib.geoji.flower.apigameandroid.model;

public class Round {
    public enum Type {
        OX, CHOICE, SUBJECTIVE
    }

    private Type type;

    private String question;
    private String imageUrl;
    private String[] choices;
    private String solution;
    private int answerTimeLimit;

    public Round() { }

    public Round(Type type, String question, String imageUrl, String[] choices, String solution, int answerTimeLimit) {
        this.type = type;
        this.question = question;
        this.imageUrl = imageUrl;
        this.choices = choices;
        this.solution = solution;
        this.answerTimeLimit = answerTimeLimit;
    }

    public void initOX(String question, String imageUrl, String solution, int answerTimeLimit) {
        this.question = question;
        this.imageUrl = imageUrl;
        this.solution = solution;
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
