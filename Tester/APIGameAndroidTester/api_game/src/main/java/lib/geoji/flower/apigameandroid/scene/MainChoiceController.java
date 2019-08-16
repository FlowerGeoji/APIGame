package lib.geoji.flower.apigameandroid.scene;

import android.content.Context;
import android.view.LayoutInflater;

import lib.geoji.flower.apigameandroid.Game;
import lib.geoji.flower.apigameandroid.GameState;
import lib.geoji.flower.apigameandroid.GameView;
import lib.geoji.flower.apigameandroid.databinding.MainChoiceViewBinding;

public class MainChoiceController extends GameView {
    public MainChoiceController(Context context) {
        super(context);
    }

    private Game game;
    private MainChoiceViewBinding binding;

    @Override
    protected void initialize(Game game) {
        this.game = game;

        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null) {
            this.binding = MainChoiceViewBinding.inflate(layoutInflater, this, true);
            this.binding.setController(this);
        }

        // init view
    }

    @Override
    protected void onChangedGameState(GameState gameState) {

    }
}
