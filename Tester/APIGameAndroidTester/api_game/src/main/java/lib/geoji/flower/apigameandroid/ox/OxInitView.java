package lib.geoji.flower.apigameandroid.ox;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import lib.geoji.flower.apigameandroid.Game;
import lib.geoji.flower.apigameandroid.R;

public class OxInitView extends LinearLayout implements android.view.View.OnClickListener {
    /* constructor */
    public OxInitView(Context context, Game game) {
        super(context);
        this.initialize(game);
    }

    /* valuse */
    private Button buttonStart;
    private OxInitPresenter presenter;

    private void initialize(Game game) {
        // init view
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.ox_init, this, false);
        addView(view);
        this.buttonStart = view.findViewById(R.id.buttonStart);
        this.buttonStart.setOnClickListener(this);

        // init presenter
        this.presenter = new OxInitPresenter(this, game);
    }

    @Override
    public void onClick(android.view.View view) {
        if (view.equals(this.buttonStart)) {
            this.presenter.onClickButtonStart();
        }
    }
}
