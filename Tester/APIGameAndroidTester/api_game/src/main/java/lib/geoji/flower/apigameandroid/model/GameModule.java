package lib.geoji.flower.apigameandroid.model;

public class GameModule {
    public static enum Type {
        OX, CHOICE, SURVIVAL
    }

    private Type type;
    private String displayName;
    private String description;
    private String thumbnail;
    private int requiredLevel;

    public GameModule(Type type, String displayName, String description, String thumbnail, int requiredLevel) {
        this.type = type;
        this.displayName = displayName;
        this.description = description;
        this.thumbnail = thumbnail;
        this.requiredLevel = requiredLevel;
    }

    public Type getType() {
        return type;
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


