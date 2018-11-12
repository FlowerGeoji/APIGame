package lib.geoji.flower.apigameandroid.ox;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import lib.geoji.flower.apigameandroid.Game;
import lib.geoji.flower.apigameandroid.GameState;
import lib.geoji.flower.apigameandroid.GameView;
import lib.geoji.flower.apigameandroid.R;
import lib.geoji.flower.apigameandroid.databinding.OxMainBinding;
import lib.geoji.flower.apigameandroid.model.Round;

public class OxMainController extends GameView {
    /* Contructor */
    public OxMainController(Context context) {
        super(context);
        this.setBackgroundColor(Color.WHITE);
    }

    /* Values */
    private Game game;
    private Round round;
    private OxMainBinding binding;

    @Override
    protected void initialize(Game game) {
        this.game = game;
        game.setCurrentRound(0);
        this.round = game.getState().getCurrentRound();

        // init view
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null) {
            this.binding = OxMainBinding.inflate(layoutInflater, this, true);
            this.binding.setController(this);
        }

        this.binding.textViewQuestion.setText(this.round.getQuestion());
    }

    @Override
    protected void onChangedGameState(GameState gameState) {

    }

    public void onClickOButton(View view) {
        this.binding.buttonChoiceTrue.setSelected(true);
        this.binding.buttonChoiceFalse.setSelected(false);
    }

    public void onClickXButton(View view) {
        this.binding.buttonChoiceTrue.setSelected(false);
        this.binding.buttonChoiceFalse.setSelected(true);
    }

    public void onClickNextButton(View view) {

    }
}
