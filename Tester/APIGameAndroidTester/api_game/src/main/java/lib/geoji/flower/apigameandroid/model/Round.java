package lib.geoji.flower.apigameandroid.model;

public class Round {
    public enum Type {
        OX, CHOICE, SUBJECTIVE
    }

    private Type type;
    private int answerTimeLimit;

    private String question;
    private String imageUrl;
    private String[] choices;
    private String solution;

    public Round(Type type, int answerTimeLimit, String question, String imageUrl, String[] choices, String solution) {
        this.type = type;
        this.answerTimeLimit = answerTimeLimit;
        this.question = question;
        this.imageUrl = imageUrl;
        this.choices = choices;
        this.solution = solution;
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
