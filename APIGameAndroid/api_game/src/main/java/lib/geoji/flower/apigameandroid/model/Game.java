package lib.geoji.flower.apigameandroid.model;

public class Game {
    public static enum GameModules {
        OX, CHOICE, SURVIVAL
    }

    private GameModules gameModules;
    private String displayName;
    private String description;
    private String thumbnail;
    private int requiredLevel;

    public Game(GameModules gameModules, String displayName, String description, String thumbnail, int requiredLevel) {
        this.gameModules = gameModules;
        this.displayName = displayName;
        this.description = description;
        this.thumbnail = thumbnail;
        this.requiredLevel = requiredLevel;
    }

    public GameModules getGameModules() {
        return gameModules;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }
}


