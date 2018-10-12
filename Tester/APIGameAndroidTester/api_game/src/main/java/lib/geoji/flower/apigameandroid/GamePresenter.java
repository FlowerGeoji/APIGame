package lib.geoji.flower.apigameandroid;

import com.google.gson.JsonObject;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import lib.geoji.flower.apigameandroid.model.GameModule;
import lib.geoji.flower.apigameandroid.ox.OxInitView;

public class GamePresenter {
    private Game game;
    private int roomId;

    GamePresenter(Game game) {
        this.game = game;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void onClickGameModule(GameModule gameModule) {
        switch (gameModule.getType()) {
            case OX:
                game.changeView(new OxInitView(game.getContext(), game));
                break;
            case CHOICE:
                break;
            case SURVIVAL:
                break;
        }
    }
    /* Request api */
    public interface OnRequestApiListener {
        Single<JsonObject> requestApi(@NonNull String url, @NonNull String method);
    }
    private OnRequestApiListener onRequestApiListener;
    public void setOnRequestApiListener(OnRequestApiListener onRequestApiListener) {
        this.onRequestApiListener = onRequestApiListener;
    }
    public Single<JsonObject> requestApi(@NonNull String url, @NonNull String method) {
        if (this.onRequestApiListener != null) {
            return Single.error(new Throwable(""));
        }
        try {
            return this.onRequestApiListener.requestApi(url, method);
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return Single.error(e);
        }
    }
}
