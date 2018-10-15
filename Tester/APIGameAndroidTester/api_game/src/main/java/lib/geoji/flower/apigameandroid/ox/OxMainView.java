package lib.geoji.flower.apigameandroid.ox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import lib.geoji.flower.apigameandroid.Game;
import lib.geoji.flower.apigameandroid.R;

public class OxMainView extends LinearLayout implements View.OnClickListener {
    private OxMainPresenter presenter;
    TextView questionTextView;
    Button oButton;
    Button xButton;

    public OxMainView(Context context, Game game) {
        super(context);
        this.initialize(game);
    }

    private void initialize(Game game) {
        // init view
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.ox_main, this, false);
        addView(view);

        this.questionTextView = view.findViewById(R.id.text_view_question);
        this.oButton = view.findViewById(R.id.button_choice_true);
        this.oButton.setOnClickListener(this);
        this.xButton = view.findViewById(R.id.button_choice_false);
        this.xButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(this.oButton)) {
            this.presenter.onClickOButton();
        }
        else if (view.equals(this.xButton)) {
            this.presenter.onClickXButton();
        }
    }
}
