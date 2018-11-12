package lib.geoji.flower.apigameandroid;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;

import io.reactivex.subjects.BehaviorSubject;
import lib.geoji.flower.apigameandroid.model.GameModule;
import lib.geoji.flower.apigameandroid.model.Round;
import lib.geoji.flower.apigameandroid.model.User;

public class GameState {
    public enum Role {
        HOST, GUEST, SETTING
    }

    final public BehaviorSubject<GameState> stateSubject = BehaviorSubject.<GameState>create();

    @Expose private int roomId;

    @Expose private GameModule.GameType gameType;
    @Expose private String gameId;

    private User user;
    private Role role;

    private ArrayList<Round> rounds;
    @Expose private Round currentRound;
    @Expose private int currentRoundIndex;

    GameState() {
        this.roomId = -1;
        this.gameId = null;

        this.rounds = new ArrayList<>();
        this.currentRoundIndex = -1;
    }

    public int getRoomId() {
        return roomId;
    }

    public GameModule.GameType getGameType() {
        return gameType;
    }

    public String getGameId() {
        return gameId;
    }

    public User getUser() {
        return user;
    }

    public Role getRole() {
        return role;
    }

    public ArrayList<Round> getRounds() {
        return rounds;
    }

    public int getCurrentRoundIndex() {
        return currentRoundIndex;
    }

    public Round getCurrentRound() {
        return currentRound;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setGameType(GameModule.GameType gameType) {
        this.gameType = gameType;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setRounds(ArrayList<Round> rounds) {
        this.rounds = rounds;
    }

    public void setCurrentRoundIndex(int currentRoundIndex) {
        this.currentRoundIndex = currentRoundIndex;
    }

    public void setCurrentRound(Round currentRound) {
        this.currentRound = currentRound;
    }

    public void merge(GameState hostState) {
        this.roomId = hostState.roomId;
        this.gameType = hostState.gameType;
        this.gameId = hostState.gameId;
        this.currentRoundIndex = hostState.currentRoundIndex;
        this.currentRound = hostState.currentRound;
    }

    public String toJsonString() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        try{
            return gson.toJson(this);
        }
        catch (Exception e) {
            return "";
        }
    }
}
