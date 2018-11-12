package lib.geoji.flower.apigameandroid.model;

import lib.geoji.flower.apigameandroid.GameView;

public class GameModule {
    public static enum GameType {
        OX, CHOICE, SURVIVAL
    }

    private GameType gameType;
    private Class<? extends GameView> viewClass;

    private String displayName;
    private String description;
    private String thumbnail;
    private int requiredLevel;

    public GameModule(GameType gameType, Class<? extends GameView> viewClass, String displayName, String description, String thumbnail, int requiredLevel) {
        this.gameType = gameType;
        this.viewClass = viewClass;
        this.displayName = displayName;
        this.description = description;
        this.thumbnail = thumbnail;
        this.requiredLevel = requiredLevel;
    }

    public GameType getGameType() {
        return gameType;
    }

    public Class<? extends GameView> getViewClass() {
        return viewClass;
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


