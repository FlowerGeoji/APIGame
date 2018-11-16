package app.geoji.flower.apigameandroidtester;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.geoji.flower.apigameandroidtester.mock.Data;
import app.geoji.flower.apigameandroidtester.mock.LiveCast;
import io.reactivex.android.schedulers.AndroidSchedulers;
import lib.geoji.flower.apigameandroid.GameState;

public class GuestFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guest, container, false);
//        TextView textView = view.findViewById(R.id.stateTextView);

        // User
        Data.UserData userData = Data.USER_DATAS()[1];

        initialize(view, userData, GameState.Role.GUEST);

        LiveCast.getInstance()
                .getMetadata()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        message -> {
                            game.onRemoteMessage(message);
                        });

        return view;
    }
}
