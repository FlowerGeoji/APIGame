package lib.geoji.flower.apigameandroid.ox;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.renderscript.ScriptGroup;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import lib.geoji.flower.apigameandroid.Game;
import lib.geoji.flower.apigameandroid.GameState;
import lib.geoji.flower.apigameandroid.GameView;
import lib.geoji.flower.apigameandroid.R;
import lib.geoji.flower.apigameandroid.databinding.OxInitBinding;

public class OxInitView extends GameView implements android.view.View.OnClickListener {
    /* constructor */
    public OxInitView(Context context) {
        super(context);
        this.setBackgroundColor(Color.WHITE);
    }

    /* valuse */
    private Button buttonStart;
    private OxInitPresenter presenter;

    @Override
    protected void initialize(Game game) {
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null) {
            this.presenter = new OxInitPresenter(this, game);

            OxInitBinding binding = OxInitBinding.inflate(layoutInflater, this, true);
            binding.setPresenter(this.presenter);
        }
    }

    @Override
    protected void onChangedGameState(GameState gameState) {

    }

    @Override
    public void onClick(android.view.View view) {
        if (view.equals(this.buttonStart)) {
            this.presenter.onClickButtonStart();
        }
    }
}
