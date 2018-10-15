package lib.geoji.flower.apigameandroid.ox;

import android.view.View;

import lib.geoji.flower.apigameandroid.Game;
import lib.geoji.flower.apigameandroid.GameView;

public class OxInitPresenter {
    private OxInitView view;
    private Game game;

    OxInitPresenter(OxInitView view, Game game) {
        this.view = view;
        this.game = game;
    }

    public void onClickButtonStart() {
        GameView nextView = new OxMainView(game.getContext(), game);
        game.changeView(nextView);
    }
}
