package lib.geoji.flower.apigameandroid.scene;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import lib.geoji.flower.apigameandroid.GameView;

public class GameScene {
    public enum SceneName {
        INIT,
        MAIN_OX,
        MAIN_CHOICE,
        MAIN_SUBJ,
        RESULT
    }

    @NonNull
    public Class<? extends GameView> getControllerClass(SceneName sceneName) {
        switch (sceneName) {
            case INIT:
                return InitController.class;
            case MAIN_OX:
                return MainOxController.class;
            case MAIN_CHOICE:
                return MainChoiceController.class;
            case MAIN_SUBJ:
                return MainChoiceController.class;
            case RESULT:
                return ResultController.class;
            default:
                return InitController.class;
        }
    }
}
