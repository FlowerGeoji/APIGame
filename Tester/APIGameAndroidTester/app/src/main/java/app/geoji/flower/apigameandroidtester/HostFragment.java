package app.geoji.flower.apigameandroidtester;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.geoji.flower.apigameandroidtester.mock.Data;
import app.geoji.flower.apigameandroidtester.mock.LiveCast;
import lib.geoji.flower.apigameandroid.GameState;
import lib.geoji.flower.apigameandroid.model.User;

public class HostFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_host, container, false);
        TextView textView = view.findViewById(R.id.stateTextView);

        // User
        Data.UserData userData = Data.USER_DATAS()[0];

        initialize(view, userData, GameState.Role.HOST);

        this.game.setOnStateChangedListener(message -> {
            textView.setText(message);
            LiveCast.getInstance().sendMetadata(message);
        });

//        gameView.setDataImporter(() -> DB.gameData);
//
//        while (api == null) {
//            try {
//                Log.d(TAG, "wait to initialize api...");
//                Thread.sleep(1000);
//            } catch (Exception e){
//                Log.e(TAG, "failed to sleep: " + e.getMessage());
//                break;
//            }
//        }

        return view;
    }
}
