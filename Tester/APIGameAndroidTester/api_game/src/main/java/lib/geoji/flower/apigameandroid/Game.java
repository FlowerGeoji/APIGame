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
import io.reactivex.subjects.BehaviorSubject;
import lib.geoji.flower.apigameandroid.model.GameModule;
import lib.geoji.flower.apigameandroid.model.Round;
import lib.geoji.flower.apigameandroid.model.User;
import lib.geoji.flower.apigameandroid.ox.OxInitController;

public class Game extends LinearLayout {
    private View gameListView;
    private Map<GameModule.GameType, GameModule> gameList = new HashMap<>();
    private GameView currentView = null;
    final public BehaviorSubject<GameState> stateSubject = BehaviorSubject.<GameState>create();
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
            gameList.put(GameModule.GameType.OX, new GameModule(GameModule.GameType.OX, OxInitController.class, "OX", "OX description", "", 0));
            gameList.put(GameModule.GameType.CHOICE, new GameModule(GameModule.GameType.CHOICE, OxInitController.class, "Choice", "Choice description", "", 0));
            gameList.put(GameModule.GameType.SURVIVAL, new GameModule(GameModule.GameType.SURVIVAL, OxInitController.class, "Survival", "Survival description", "", 0));
            this.setBackgroundColor(Color.RED);

            this.stateDisposable = stateSubject.subscribe(
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
            this.stateSubject.onNext(new GameState());
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
            gameListAdapter.addItem(gameList.get(GameModule.GameType.OX));
            gameListAdapter.addItem(gameList.get(GameModule.GameType.CHOICE));
            gameListAdapter.addItem(gameList.get(GameModule.GameType.SURVIVAL));

            addView(this.gameListView);
        }

        /* initialize state */
        GameState state = this.stateSubject.getValue();

        state.setRoomId(roomId);
        state.setUser(user);
        state.setRole(role);

        stateSubject.onNext(state);
    }

    private void onClickGameModule(GameModule gameModule) {
        this.createGame(new SingleObserver<JsonObject>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(JsonObject jsonObject) {
                String gameId = jsonObject.get("gameId").getAsString();

                Game.this.changeView(gameModule.getViewClass());

                GameState state = Game.this.stateSubject.getValue();
                state.setGameId(gameId);
                state.setGameType(gameModule.getGameType());
                Game.this.stateSubject.onNext(state);
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
    public interface OnStateChangedListener {
        void stateChanged(String state);
    }
    private OnStateChangedListener onStateChangedListener;
    public void setOnStateChangedListener(OnStateChangedListener onStateChangedListener) {
        this.onStateChangedListener = onStateChangedListener;
    }

    public void onRemoteMessage(String message) {
        if (stateSubject.getValue().getRole() != GameState.Role.GUEST) {
            return;
        }

        try {
            GameState state = stateSubject.getValue();
            GameState hostState = new Gson().fromJson(message, GameState.class);

            // Check game type
            if (state.getGameType() != hostState.getGameType()) {
                quitGame();
            }

            if (currentView == null) {
                GameModule.GameType gameType = hostState.getGameType();
                if (gameType != null) {
                    changeView(this.gameList.get(gameType).getViewClass());
                }
            }

            state.merge(hostState);
            stateSubject.onNext(state);
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

    public void quitGame() {
        if (currentView != null) {
            this.removeView(currentView);
        }
        currentView = null;
    }

    public void addRound(Round round) {
        GameState state = this.stateSubject.getValue();
        state.getRounds().add(round);
        this.stateSubject.onNext(state);
    }

    public void setCurrentRound(int index) {
        GameState state = this.stateSubject.getValue();

        Round currentRound = null;
        try {
            currentRound = state.getRounds().get(index);
        }
        catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }


        if (currentRound != null) {
            state.setCurrentRoundIndex(index);
            state.setCurrentRound(currentRound);
            stateSubject.onNext(state);
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
        GameState state = stateSubject.getValue();

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
        data.addProperty("user_id", stateSubject.getValue().getUser().getId());

        this.onRequestApiListener.requestApi(method, url, data, new SingleObserver<JsonObject>() {
            @Override
            public void onSubscribe(Disposable d) { }

            @Override
            public void onSuccess(JsonObject jsonObject) {
                try {
                    JsonObject resultUser = jsonObject.get("user").getAsJsonObject();

                    GameState state = stateSubject.getValue();

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
                    ));

                    stateSubject.onNext(state);

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

        GameState state = stateSubject.getValue();

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

        GameState state = stateSubject.getValue();

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

            GameState state = stateSubject.getValue();

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

        GameState state = stateSubject.getValue();

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
        private ArrayList<GameModule> games = new ArrayList<>();

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_module_view, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            try {
                final GameModule game = this.games.get(position);
                holder.bind(game);

                holder.getView().setOnClickListener(view -> {
                    Game.this.onClickGameModule(game);
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

        void addItem(GameModule game) {
            this.games.add(game);
            this.notifyItemInserted(this.games.size()-1);
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private View view;
            private GameModule gameModule;
            private ImageView imageView;
            private TextView nameTextView;
            private TextView descriptionTextView;

            private ViewHolder(View itemView) {
                super(itemView);
                this.view = itemView;
                this.imageView = itemView.findViewById(R.id.gameImageView);
                this.nameTextView = itemView.findViewById(R.id.gameNameTextView);
                this.descriptionTextView = itemView.findViewById(R.id.gameDescriptionTextView);
            }

            void bind(GameModule gameModule) {
                this.gameModule = gameModule;
//                Picasso.get()
//                        .load(game.getThumbnail())
//                        .into(this.imageView);
                this.nameTextView.setText(gameModule.getDisplayName());
                this.descriptionTextView.setText(gameModule.getDescription());
            }

            public View getView() {
                return this.view;
            }
        }
    }
}
