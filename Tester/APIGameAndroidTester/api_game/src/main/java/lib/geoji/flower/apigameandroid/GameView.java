package lib.geoji.flower.apigameandroid;

import android.content.Context;
import android.widget.LinearLayout;

public class GameView extends LinearLayout {
    protected Game game;

    public GameView(Context context, Game game) {
        super(context);
        this.game = game;
    }
}
