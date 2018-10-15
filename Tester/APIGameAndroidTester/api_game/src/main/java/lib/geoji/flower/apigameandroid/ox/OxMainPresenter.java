package lib.geoji.flower.apigameandroid.ox;

import io.reactivex.Observable;
import lib.geoji.flower.apigameandroid.Game;

public class OxMainPresenter {
    private OxMainView view;
    private Game game;
    private String selectedAnswer = null;

    OxMainPresenter(OxMainView view, Game game) {
        this.view = view;
        this.game = game;
    }

    void onClickOButton() {

    }

    void onClickXButton() {

    }
}
