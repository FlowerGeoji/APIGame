package lib.geoji.flower.apigameandroid.model;

import lib.geoji.flower.apigameandroid.GameView;

public class GameModule {
    public static enum Type {
        OX, CHOICE, SURVIVAL
    }

    private Type type;
    Class<? extends GameView> viewClass;

    private String displayName;
    private String description;
    private String thumbnail;
    private int requiredLevel;

    public GameModule(Type type, Class<? extends GameView> viewClass, String displayName, String description, String thumbnail, int requiredLevel) {
        this.type = type;
        this.viewClass = viewClass;
        this.displayName = displayName;
        this.description = description;
        this.thumbnail = thumbnail;
        this.requiredLevel = requiredLevel;
    }

    public Type getType() {
        return type;
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


