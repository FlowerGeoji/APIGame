package lib.geoji.flower.apigameandroid;

import android.content.Context;
import android.widget.RelativeLayout;

public abstract class GameView extends RelativeLayout {
    public GameView(Context context) {
        super(context);
        this.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    abstract protected void initialize(Game game);

    abstract protected void onChangedGameState(GameState gameState);
}
