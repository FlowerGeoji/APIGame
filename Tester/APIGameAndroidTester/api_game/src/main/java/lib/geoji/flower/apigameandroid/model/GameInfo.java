package lib.geoji.flower.apigameandroid.model;

import java.util.ArrayList;

public class GameInfo {
    private String gameTitle;
    final private ArrayList<Round> rounds = new ArrayList<>();

    public String getGameTitle() {
        return gameTitle;
    }

    public ArrayList<Round> getRounds() {
        return rounds;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public void addRounds(ArrayList<Round> rounds) {
        this.rounds.addAll(rounds);
    }

    public void addRound(Round round) {
        this.rounds.add(round);
    }
}
