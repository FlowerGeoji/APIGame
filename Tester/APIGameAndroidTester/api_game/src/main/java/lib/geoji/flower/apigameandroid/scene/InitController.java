package lib.geoji.flower.apigameandroid.scene;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.completable.CompletableCache;
import lib.geoji.flower.apigameandroid.Game;
import lib.geoji.flower.apigameandroid.GameState;
import lib.geoji.flower.apigameandroid.GameView;
import lib.geoji.flower.apigameandroid.databinding.InitViewBinding;

public class InitController extends GameView {
    public InitController(Context context) {
        super(context);
        setBackgroundColor(Color.WHITE);
    }

    /* Variables */
    private Game game;
    private InitViewBinding binding;

    @Override
    protected void initialize(Game game) {
        this.game = game;

        if (game.getState().getRole() != GameState.Role.HOST) {
            return;
        }

        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null) {
            this.binding = InitViewBinding.inflate(layoutInflater, this, true);
            this.binding.setController(this);
        }

        game.createGame(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) { }

            @Override
            public void onComplete() {
                binding.buttonStartGame.setEnabled(true);
                binding.buttonCancel.setEnabled(true);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                game.quitGame();
            }
        });
    }

    @Override
    protected void onChangedGameState(GameState gameState) {

    }

    public void onClickStartGameButton(View view) {
        game.goNextMain();
    }

    public void onClickCancelButton(View view) {
        game.quitGame();
    }
}
