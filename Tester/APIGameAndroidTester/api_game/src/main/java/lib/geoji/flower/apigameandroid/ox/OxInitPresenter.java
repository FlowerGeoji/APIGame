package lib.geoji.flower.apigameandroid.ox;

import android.view.View;

import lib.geoji.flower.apigameandroid.Game;
import lib.geoji.flower.apigameandroid.GameView;
import lib.geoji.flower.apigameandroid.model.Round;

public class OxInitPresenter {
    private OxInitView view;
    private Game game;

    OxInitPresenter(OxInitView view, Game game) {
        this.view = view;
        this.game = game;
    }

    public void onClickButtonStart() {
        Round round = new Round();
        round.initOX("김봡왕자는 잘 생겼다", null, "1", 0);
        this.game.addRound(round);

        game.changeView(OxMainView.class);
    }
}
