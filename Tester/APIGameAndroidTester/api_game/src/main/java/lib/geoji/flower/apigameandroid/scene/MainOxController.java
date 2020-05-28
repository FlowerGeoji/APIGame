package lib.geoji.flower.apigameandroid.scene;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import lib.geoji.flower.apigameandroid.Game;
import lib.geoji.flower.apigameandroid.GameState;
import lib.geoji.flower.apigameandroid.GameView;
import lib.geoji.flower.apigameandroid.databinding.MainOxViewBinding;
import lib.geoji.flower.apigameandroid.model.Round;

public class MainOxController extends GameView {
    public MainOxController(Context context) {
        super(context);
    }

    private Game game;
    private MainOxViewBinding binding;

    @Override
    protected void initialize(Game game) {
        this.game = game;

        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null) {
            this.binding = MainOxViewBinding.inflate(layoutInflater, this, true);
            this.binding.setController(this);
        }

        // init view
        if (game.getState().getCurrentRound() != null) {
            Round round = game.getState().getCurrentRound();
            binding.textViewQuestion.setText(round.getQuestion());
        }
    }

    @Override
    protected void onChangedGameState(GameState gameState) {
        for(int i = 0; i < gameState.getAnswers().size(); i++) {
            Log.d("@@@Answered", gameState.getAnswers().toString());
        }
        String answer = gameState.getAnswers().get(3);
        if (answer != null) {
            if (answer.equals("1")) {
                binding.buttonChoiceTrue.setSelected(true);
                binding.buttonChoiceFalse.setSelected(false);
            }
            else if (answer.equals("0")) {
                binding.buttonChoiceTrue.setSelected(false);
                binding.buttonChoiceFalse.setSelected(true);
            }
        }
    }

    public void onClickOButton(View view) {
        game.updateAnswer("1", 3, null);
    }

    public void onClickXButton(View view) {
        game.updateAnswer("0", 3, null);
    }

    public void onClickNextButton(View view) {

    }
}
