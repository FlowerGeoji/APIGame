package lib.geoji.flower.apigameandroid.scene;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import lib.geoji.flower.apigameandroid.GameView;

public class GameScene {
    public enum SceneName {
        INIT,
        MAIN,
        RESULT
    }

    @NonNull
    public Class<? extends GameView> getControllerClass(SceneName sceneName) {
        switch (sceneName) {
            case INIT:
                return InitController.class;
            case MAIN:
                return MainController.class;
            case RESULT:
                return ResultController.class;
            default:
                return InitController.class;
        }
    }
}
