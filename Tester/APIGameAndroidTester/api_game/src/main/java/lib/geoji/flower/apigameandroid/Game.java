package lib.geoji.flower.apigameandroid;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import lib.geoji.flower.apigameandroid.model.GameInfo;
import lib.geoji.flower.apigameandroid.model.Round;
import lib.geoji.flower.apigameandroid.model.User;
import lib.geoji.flower.apigameandroid.ox.OxInitController;
import lib.geoji.flower.apigameandroid.scene.GameScene;

public class Game extends LinearLayout {
    private View gameListView;
    private ArrayList<GameInfo> gameList = new ArrayList<>();
    private GameView currentView = null;
    private GameState state = new GameState();
    Disposable stateDisposable;

    public Game(@NonNull Context context) {
        super(context);
        this.init(context);
    }

    public Game(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public Game(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    private void init(Context context) {
        try {
            GameInfo oxGame = new GameInfo();
            oxGame.setGameTitle("OX Game");
            oxGame.addRound(new Round(Round.Type.OX, "question", "imageUrl", new String[]{"chioce1","choicd2"}, "solution", 10));
            this.gameList.add(oxGame);

            GameInfo normalGame = new GameInfo();
            normalGame.setGameTitle("Normal Game");
            normalGame.addRound(new Round(Round.Type.OX, "question", "imageUrl", new String[]{"chioce1","choicd2"}, "solution", 10));
            normalGame.addRound(new Round(Round.Type.CHOICE, "question", "imageUrl", new String[]{"chioce1","choicd2"}, "solution", 10));
            normalGame.addRound(new Round(Round.Type.CHOICE, "question", "imageUrl", new String[]{"chioce1","choicd2"}, "solution", 10));
            this.gameList.add(normalGame);

            this.setBackgroundColor(Color.RED);

            this.stateDisposable = this.state.stateSubject.subscribe(
                    nextState -> {
                        if (this.currentView != null) {
                            this.currentView.onChangedGameState(nextState);
                        }
                        if (this.onStateChangedListener != null) {
                            this.onStateChangedListener.stateChanged(nextState.toJsonString());
                        }
                    },
                    Throwable::printStackTrace
            );
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void initialize(int roomId, User user, GameState.Role role) {
        if (role == GameState.Role.HOST) {
            /* initialize view */
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gameListView = layoutInflater.inflate(R.layout.game_list_view, this, false);

            // recycler view
            RecyclerView gameListView = this.gameListView.findViewById(R.id.gameRecyclerView);
            GameListAdapter gameListAdapter = new GameListAdapter();
            gameListView.setAdapter(gameListAdapter);
            gameListAdapter.addItem(gameList.get(0));
            gameListAdapter.addItem(gameList.get(1));

            addView(this.gameListView);
        }

        /* initialize state */
        state.setRoomId(roomId)
                .setUser(user)
                .setRole(role);
    }

    private void onClickGameInfo(GameInfo gameInfo) {
        this.createGame(new SingleObserver<JsonObject>() {
            @Override
            public void onSubscribe(Disposable d) { }

            @Override
            public void onSuccess(JsonObject jsonObject) {
                String gameId = jsonObject.get("gameId").getAsString();

                GameScene scene = new GameScene();
                Game.this.changeView(GameScene.SceneName.INIT);

                state.setGameId(gameId)
                        .addRounds(gameInfo.getRounds())
                        .next();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        });
    }

    /**************************************************************************************************************************************************
     * State handler
     **************************************************************************************************************************************************/
    public GameState getState() {
        return this.state;
    }

    public interface OnStateChangedListener {
        void stateChanged(String state);
    }
    private OnStateChangedListener onStateChangedListener;
    public void setOnStateChangedListener(OnStateChangedListener onStateChangedListener) {
        this.onStateChangedListener = onStateChangedListener;
    }

    public void onRemoteMessage(String message) {
        Log.d("@@@", message);
        if (state.getRole() != GameState.Role.GUEST) {
            return;
        }

        try {
            GameState hostState = new Gson().fromJson(message, GameState.class);

            if (currentView == null) {

            }

            state.setGameId(hostState.getGameId())
                    .setCurrentRoundIndex(hostState.getCurrentRoundIndex())
                    .setCurrentRound(hostState.getCurrentRound())
                    .next();
        }
        catch (JsonParseException e) {
            e.printStackTrace();
        }
    }

    public void changeView(Class<? extends GameView> viewClass) {
        try {
            GameView newView = viewClass.getConstructor(Context.class).newInstance(this.getContext());
            newView.initialize(this);

            if (currentView != null) {
                this.removeView(currentView);
            }

            if (this.gameListView != null) {
                this.gameListView.setVisibility(GONE);
            }

            this.addView(newView);
            this.currentView = newView;
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void changeView(GameScene.SceneName sceneName) {
        try {
            GameScene scene = new GameScene();
            GameView newView = scene.getControllerClass(GameScene.SceneName.INIT)
                    .getConstructor(Context.class).newInstance(this.getContext());
            newView.initialize(this);

            if (currentView != null) {
                this.removeView(currentView);
            }

            if (this.gameListView != null) {
                this.gameListView.setVisibility(GONE);
            }

            this.addView(newView);
            this.currentView = newView;
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public void quitGame() {
        if (currentView != null) {
            this.removeView(currentView);
        }
        currentView = null;
    }

    public void addRound(Round round) {
        state.getRounds().add(round);
        state.next();
    }

    public void setCurrentRound(int index) {
        Round currentRound = null;
        try {
            currentRound = state.getRounds().get(index);
        }
        catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }


        if (currentRound != null) {
            state.setCurrentRoundIndex(index)
                    .setCurrentRound(currentRound)
                    .next();
        }
    }

    /**************************************************************************************************************************************************
     * Request api
     **************************************************************************************************************************************************/
    public interface OnRequestApiListener {
        void requestApi(@NonNull String method, @NonNull String url, @Nullable JsonObject data, @Nullable SingleObserver<JsonObject> observer);
    }
    private OnRequestApiListener onRequestApiListener;
    public void setOnRequestApiListener(OnRequestApiListener onRequestApiListener) {
        this.onRequestApiListener = onRequestApiListener;
    }

    private Throwable checkHostApi(boolean checkRoomId, boolean checkGameId) {
        if (state.getRole() != GameState.Role.HOST) {
            return new Throwable("You must be a HOST");
        }

        if (this.onRequestApiListener == null) {
            return new Throwable("There is no RequestApi listener");
        }

        if (checkRoomId && state.getRoomId() < 0) {
            return new Throwable("This api need roomId");
        }

        if (checkGameId && (state.getGameId() == null || state.getGameId().equals(""))) {
            return new Throwable("This api need gameId");
        }

        return null;
    }

    public void refreshUserInfo(CompletableObserver observer) {
        String method = "get";
        String url = "/user/info";
        JsonObject data = new JsonObject();
        data.addProperty("user_id", state.getUser().getId());

        this.onRequestApiListener.requestApi(method, url, data, new SingleObserver<JsonObject>() {
            @Override
            public void onSubscribe(Disposable d) { }

            @Override
            public void onSuccess(JsonObject jsonObject) {
                try {
                    JsonObject resultUser = jsonObject.get("user").getAsJsonObject();

                    state.setUser(new User(
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
                    )).next();

                    observer.onComplete();
                }
                catch(Throwable e) {
                    observer.onError(e);
                }
            }

            @Override
            public void onError(Throwable e) {
                observer.onError(e);
            }
        });
    }

    public void createGame(SingleObserver<JsonObject> observer) {
        Throwable error = checkHostApi(true, false);
        if (error != null) {
            observer.onError(error);
            return;
        }

        String method = "post";
        String url = String.format(Locale.ROOT, "/game/%d", state.getRoomId());
        JsonObject data = new JsonObject();
        data.addProperty("name", "name");
        data.addProperty("displayName", "displayName");
        data.addProperty("version", "version");
        data.addProperty("appModuleVersion", "appModuleVersion");

        this.onRequestApiListener.requestApi(method, url, data, observer);
    }

    public void updateReward(int reward, int userHeart, int finalRound, @Nullable SingleObserver<JsonObject> observer) {
        Throwable error = checkHostApi(true, true);
        if (error != null) {
            if (observer != null) {
                observer.onError(error);
            }
            return;
        }

        String method = "post";
        String url = String.format(Locale.ROOT, "/game/%d/%s/reward", state.getRoomId(), state.getGameId());
        JsonObject data = new JsonObject();
        data.addProperty("reward", reward);
        data.addProperty("userHeart", userHeart);
        data.addProperty("finalRound", finalRound);

        this.onRequestApiListener.requestApi(method, url, data, observer);
    }

    public void updateSolution(String solution, @IntRange(from = 0) int round, boolean marking, @Nullable SingleObserver<JsonObject> observer) {
        try {
            Throwable error = checkHostApi(true, true);
            if (error != null) {
                if (observer != null) {
                    observer.onError(error);
                }
                return;
            }

            if (round < 0) {
                if (observer != null) {
                    observer.onError(new Throwable(""));
                }
                return;
            }

            String method = "put";
            String url = String.format(Locale.ROOT, "/game/%d/%s/solutions", state.getRoomId(), state.getGameId());
            JsonObject data = new JsonObject();
            data.addProperty("round", round);
            data.addProperty("solution", solution);
            data.addProperty("marking", marking);

            this.onRequestApiListener.requestApi(method, url, data, observer);
        }
        catch (Exception e) {
            if (observer != null) {
                observer.onError(e);
            }
        }
    }

    public void updateAnswer(String answer, int round, @Nullable SingleObserver<JsonObject> observer) {
        Throwable error = checkHostApi(true, true);
        if (error != null) {
            if (observer != null) {
                observer.onError(error);
            }
            return;
        }

        String method = "post";
        String url = String.format(Locale.ROOT, "/game/%d/%s/answers", state.getRoomId(), state.getGameId());
        JsonObject data = new JsonObject();
        data.addProperty("answer", answer);
        data.addProperty("round", round);

        this.onRequestApiListener.requestApi(method, url, data, observer);
    }

    public void getAnswersCount(@Nullable SingleObserver<JsonObject> observer) {

    }










    /***********************************************************************************************************
     * Recycler view
     ***********************************************************************************************************/
    private class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder> {
        private ArrayList<GameInfo> games = new ArrayList<>();

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_list_item_view, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            try {
                final GameInfo game = this.games.get(position);
                holder.bind(game);

                holder.getView().setOnClickListener(view -> {
                    Game.this.onClickGameInfo(game);
                });
            }
            catch(IndexOutOfBoundsException e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return this.games.size();
        }

        void addItem(GameInfo game) {
            this.games.add(game);
            this.notifyItemInserted(this.games.size()-1);
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private View view;
            private GameInfo gameInfo;
            private TextView titleTextView;

            private ViewHolder(View itemView) {
                super(itemView);
                this.view = itemView;
                this.titleTextView = itemView.findViewById(R.id.gameTitleTextView);
            }

            void bind(GameInfo gameInfo) {
                this.gameInfo = gameInfo;
                this.titleTextView.setText(gameInfo.getGameTitle());
            }

            public View getView() {
                return this.view;
            }
        }
    }
}
