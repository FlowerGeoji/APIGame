package lib.geoji.flower.apigameandroid.ox;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;

import java.util.Locale;

import lib.geoji.flower.apigameandroid.Game;
import lib.geoji.flower.apigameandroid.GameState;
import lib.geoji.flower.apigameandroid.GameView;
import lib.geoji.flower.apigameandroid.databinding.OxInitBinding;
import lib.geoji.flower.apigameandroid.model.Round;

public class OxInitController extends GameView {
    /* constructor */
    public OxInitController(Context context) {
        super(context);
        this.setBackgroundColor(Color.WHITE);
    }

    /* valuse */
    private Game game;
    private OxInitBinding binding;

    @Override
    protected void initialize(Game game) {
        this.game = game;

        if (game.getState().getRole() != GameState.Role.HOST) {
            return;
        }

        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null) {
            this.binding = OxInitBinding.inflate(layoutInflater, this, true);
            this.binding.setController(this);
        }
    }

    @Override
    protected void onChangedGameState(GameState gameState) {

    }

    public void onQuestionChanged(CharSequence s, int start, int before, int count) {
        this.binding.textViewQuestionCount.setText(String.format(Locale.ROOT, "(%d/20)", s.length()));
    }

    public void onClickCancelButton(View view) {
//        game.quitGame();
    }

    public void onClickNextButton(View view) {
        Round round = new Round();
        String question = binding.editTextQuestion.getText().toString();
        round.initOX(question, null, null, 0);
        this.game.addRound(round);

        game.changeView(OxMainController.class);
    }
}
