package lib.geoji.flower.apigameandroid.scene;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;

import lib.geoji.flower.apigameandroid.Game;
import lib.geoji.flower.apigameandroid.GameState;
import lib.geoji.flower.apigameandroid.GameView;
import lib.geoji.flower.apigameandroid.databinding.InitViewBinding;

public class InitController extends GameView {
    public InitController(Context context) {
        super(context);
        setBackgroundColor(Color.WHITE);
    }

    /* Variables */
    private Game game;
    private InitViewBinding binding;

    @Override
    protected void initialize(Game game) {
        this.game = game;

        if (game.getState().getRole() != GameState.Role.HOST) {
            return;
        }

        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null) {
            this.binding = InitViewBinding.inflate(layoutInflater, this, true);
            this.binding.setController(this);
        }
    }

    @Override
    protected void onChangedGameState(GameState gameState) {

    }

    public void onClickStartGameButton(View view) {
        game.setCurrentRound(0);
        game.changeView(GameScene.SceneName.MAIN);
    }

    public void onClickCancelButton(View view) {

    }
}
