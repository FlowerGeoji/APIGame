package app.geoji.flower.apigameandroidtester;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import app.geoji.flower.apigameandroidtester.lib.Api;
import app.geoji.flower.apigameandroidtester.mock.Data;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import lib.geoji.flower.apigameandroid.Game;
import lib.geoji.flower.apigameandroid.GameState;
import lib.geoji.flower.apigameandroid.model.User;


public class BaseFragment extends Fragment {
    protected Api api;
    protected Game game;

    protected void initialize(View view, Data.UserData userData, GameState.Role role) {
        // game
        this.game = view.findViewById(R.id.game);

        // Api
        this.api = new Api(userData.getToken());
        JsonObject user = new JsonObject();
        user.addProperty("user_id", userData.getId());

        Disposable request = this.api.request("get", "/user/info", user)
                .subscribe(
                        result -> {
                            JsonObject resultUser = result.get("user").getAsJsonObject();
                            game.initialize(
                                    1,
                                    new User(
                                            resultUser.get("user_id").getAsInt(),
                                            resultUser.get("user_name").getAsString(),
                                            resultUser.get("user_level").getAsInt(),
                                            resultUser.get("user_profile_image").getAsString(),
                                            resultUser.get("user_description").getAsString(),
                                            resultUser.get("user_jam").getAsInt(),
                                            resultUser.get("user_cream").getAsInt(),
                                            resultUser.get("user_choux").getAsInt(),
                                            resultUser.get("won_amount").getAsInt(),
                                            resultUser.get("heart_amount").getAsInt(),
                                            resultUser.get("heart_gauge_amount").getAsInt()
                                    ),
                                    role
                            );
                        },
                        error -> Log.e("API", "get user info error: " + error.toString())
                );

        // Game listeners
        this.game.setOnRequestApiListener((method, url, data, observer) -> {
            boolean isGameApi = url.startsWith("/game");

            api.request(method, url, data)
                    .subscribe(
                            (result)->{
                                Log.d("API", "request success: " + (result == null ? "null" : result.toString()));
                                if (observer == null) {
                                    return;
                                }

                                if (isGameApi) {
                                    JsonElement error = result.get("error");
                                    if (error != null && error.getAsBoolean()) {
                                        observer.onError(new Throwable());
                                    }
                                    else {
                                        JsonObject rData = result.getAsJsonObject("data");
                                        if (rData == null) {
                                            rData = new JsonObject();
                                        }
                                        observer.onSuccess(rData);
                                    }
                                }
                                else {
                                    observer.onSuccess(result);
                                }
                            },
                            (error)->{
                                error.printStackTrace();
                                if (observer == null) {
                                    return;
                                }

                                observer.onError(error);
                            }
                    );
        });
    }
}
