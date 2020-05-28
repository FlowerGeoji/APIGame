package lib.geoji.flower.apigameandroid;

import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.subjects.BehaviorSubject;
import lib.geoji.flower.apigameandroid.model.Round;
import lib.geoji.flower.apigameandroid.model.User;
import lib.geoji.flower.apigameandroid.scene.GameScene;

public class GameState {
    public enum Role {
        HOST, GUEST, SETTING
    }

    final public BehaviorSubject<GameState> stateSubject = BehaviorSubject.<GameState>create();

    @Expose private int roomId = -1;
    @Expose private String gameId;

    private User user;
    private Role role;

    @Expose private GameScene.SceneName sceneName;

    final private ArrayList<Round> rounds = new ArrayList<>();
    final private SparseArray<String> answers = new SparseArray<>();
    @Expose private Round currentRound;
    @Expose private int currentRoundIndex = -1;

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

    public GameScene.SceneName getSceneName() {
        return sceneName;
    }

    public ArrayList<Round> getRounds() {
        return rounds;
    }

    public SparseArray<String> getAnswers() {
        return answers;
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

    public GameState setSceneName(GameScene.SceneName sceneName) {
        this.sceneName = sceneName;
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

    public GameState updateAnswer(String answer, int round) {
        this.answers.put(round, answer);
        return this;
    }

    public GameState setCurrentRound(Round currentRound) {
        this.currentRound = currentRound;
        return this;
    }

    public GameState setCurrentRound(int currentRoundIndex) {
        if (currentRoundIndex >= rounds.size()) {
            return this;
        }

        try {
            this.currentRoundIndex = currentRoundIndex;
            this.currentRound = this.rounds.get(currentRoundIndex);
        }
        catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return this;
    }

    public GameState setCurrentRoundIndex(int currentRoundIndex) {
        this.currentRoundIndex = currentRoundIndex;
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
