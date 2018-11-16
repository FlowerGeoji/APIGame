package lib.geoji.flower.apigameandroid;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;

import io.reactivex.subjects.BehaviorSubject;
import lib.geoji.flower.apigameandroid.model.Round;
import lib.geoji.flower.apigameandroid.model.User;

public class GameState {
    public enum Role {
        HOST, GUEST, SETTING
    }

    final public BehaviorSubject<GameState> stateSubject = BehaviorSubject.<GameState>create();

    @Expose private int roomId;
    @Expose private String gameId;

    private User user;
    private Role role;

    final private ArrayList<Round> rounds;
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

    public GameState setRoomId(int roomId) {
        this.roomId = roomId;
        return this;
    }

    public GameState setGameId(String gameId) {
        this.gameId = gameId;
        return this;
    }

    public GameState setUser(User user) {
        this.user = user;
        return this;
    }

    public GameState setRole(Role role) {
        this.role = role;
        return this;
    }

    public GameState addRound(Round round) {
        this.rounds.add(round);
        return this;
    }

    public GameState addRounds(ArrayList<Round> rounds) {
        this.rounds.addAll(rounds);
        return this;
    }

    public GameState setCurrentRoundIndex(int currentRoundIndex) {
        this.currentRoundIndex = currentRoundIndex;
        return this;
    }

    public GameState setCurrentRound(Round currentRound) {
        this.currentRound = currentRound;
        return this;
    }

    public void next() {
        stateSubject.onNext(this);
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
