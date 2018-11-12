package lib.geoji.flower.apigameandroid.model;

public class User {
    private int id;
    private String name;
    private int level;
    private String profileImage;
    private String description;

    private int jam;
    private int cream;
    private int choux;
    private int won;

    private int heart;
    private int heartGauge;

    public User(int id, String name, int level, String profileImage, String description, int jam, int cream, int choux, int won, int heart, int heartGauge) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.profileImage = profileImage;
        this.description = description;
        this.jam = jam;
        this.cream = cream;
        this.choux = choux;
        this.won = won;
        this.heart = heart;
        this.heartGauge = heartGauge;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getDescription() {
        return description;
    }

    public int getJam() {
        return jam;
    }

    public int getCream() {
        return cream;
    }

    public int getChoux() {
        return choux;
    }

    public int getWon() {
        return won;
    }

    public int getHeart() {
        return heart;
    }

    public int getHeartGauge() {
        return heartGauge;
    }
}
