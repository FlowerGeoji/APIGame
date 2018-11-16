package lib.geoji.flower.apigameandroid.scene;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;

import lib.geoji.flower.apigameandroid.Game;
import lib.geoji.flower.apigameandroid.GameState;
import lib.geoji.flower.apigameandroid.GameView;
import lib.geoji.flower.apigameandroid.databinding.MainChoiceViewBinding;
import lib.geoji.flower.apigameandroid.databinding.MainOxViewBinding;
import lib.geoji.flower.apigameandroid.model.Round;

public class MainController extends GameView {
    public MainController(Context context) {
        super(context);
    }

    private Game game;
    private ViewDataBinding binding;
    private Round round;

    @Override
    protected void initialize(Game game) {
        this.game = game;

        this.round = this.game.getState().getCurrentRound();

        this.initRoundView();
    }

    private void initRoundView() {
        if (this.round == null) {
            removeAllViewsInLayout();
            return;
        }
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null) {
            switch (this.round.getType()) {
                case OX:
                    this.binding = MainOxViewBinding.inflate(layoutInflater, this, true);
                    ((MainOxViewBinding)this.binding).setController(this);
                    return;
                case CHOICE:
                    this.binding = MainChoiceViewBinding.inflate(layoutInflater, this, true);
                    ((MainChoiceViewBinding)this.binding).setController(this);
                    return;
                case SUBJECTIVE:
                    return;
            }

        }
    }

    @Override
    protected void onChangedGameState(GameState gameState) {

    }

    public void onClickOButton(View view) {

    }

    public void onClickXButton(View view) {

    }

    public void onClickNextButton(View view) {

    }

    public void onClickExitButton(View view) {

    }
}
