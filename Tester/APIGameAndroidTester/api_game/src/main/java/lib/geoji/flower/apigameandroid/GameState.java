package lib.geoji.flower.apigameandroid;

import java.util.ArrayList;

import lib.geoji.flower.apigameandroid.model.Round;
import lib.geoji.flower.apigameandroid.model.User;

public class GameState {
    private int roomId;
    private User user;
    private ArrayList<Round> rounds;
    private int currentRoundIndex;

    GameState() {
        this.roomId = -1;
        this.user = null;
        this.rounds = new ArrayList<>();
        this.currentRoundIndex = -1;
    }

    GameState(int roomId, User user, ArrayList<Round> rounds, int currentRoundIndex) {
        this.roomId = roomId;
        this.user = user;
        this.rounds = rounds;
        this.currentRoundIndex = currentRoundIndex;
    }

    public int getRoomId() {
        return roomId;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<Round> getRounds() {
        return rounds;
    }

    public int getCurrentRoundIndex() {
        return currentRoundIndex;
    }
}
