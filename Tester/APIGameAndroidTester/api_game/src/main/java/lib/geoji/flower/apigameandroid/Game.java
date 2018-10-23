package lib.geoji.flower.apigameandroid;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import lib.geoji.flower.apigameandroid.model.GameModule;
import lib.geoji.flower.apigameandroid.model.Round;
import lib.geoji.flower.apigameandroid.model.User;
import lib.geoji.flower.apigameandroid.ox.OxInitView;

public class Game extends LinearLayout {
    private View gameListView;
    private GameView currentView = null;
    private GameState state;
    private Subject<GameState> stateSubject = PublishSubject.create();
    Disposable stateDisposable;

    public Game(@NonNull Context context) {
        super(context);
        this.initView(context);
    }

    public Game(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.initView(context);
    }

    public Game(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context);
    }

    private void initView(Context context) {
        try {
            this.setBackgroundColor(Color.RED);
            /* initView view */
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gameListView = layoutInflater.inflate(R.layout.game_view, this, false);

            // recycler view
            RecyclerView gameListView = this.gameListView.findViewById(R.id.gameRecyclerView);
            GameListAdapter gameListAdapter = new GameListAdapter();
            gameListView.setAdapter(gameListAdapter);
            gameListAdapter.addItem(new GameModule(GameModule.Type.OX, OxInitView.class, "OX", "OX description", "", 0));
            gameListAdapter.addItem(new GameModule(GameModule.Type.CHOICE, OxInitView.class, "Choice", "Choice description", "", 0));
            gameListAdapter.addItem(new GameModule(GameModule.Type.SURVIVAL, OxInitView.class, "Survival", "Survival description", "", 0));

            addView(this.gameListView);

            this.stateDisposable = stateSubject.subscribe(
                    nextState -> {
                        this.state = nextState;
                        if (this.currentView != null) {
                            this.currentView.onChangedGameState(this.state);
                        }
                    },
                    error -> {

                    }
            );
            this.stateSubject.onNext(new GameState());
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }



    public void init(int roomId, User user) {
//        this.roomId = roomId;
//        this.user = user;
    }

    public void changeView(Class<? extends GameView> viewClass) {
        try {
            GameView newView = viewClass.getConstructor(Context.class).newInstance(this.getContext());
            newView.initialize(this);

            if (currentView != null) {
                this.removeView(currentView);
            }

            this.gameListView.setVisibility(GONE);
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

    public void addRound(Round round) {

    }

    private void onClickGameModule(GameModule gameModule) {
        this.changeView(gameModule.getViewClass());
    }

    /* Request api */
    public interface OnRequestApiListener {
        Single<JsonObject> requestApi(@io.reactivex.annotations.NonNull String url, @io.reactivex.annotations.NonNull String method);
    }
    private OnRequestApiListener onRequestApiListener;
    public void setOnRequestApiListener(OnRequestApiListener onRequestApiListener) {
        this.onRequestApiListener = onRequestApiListener;
    }
    public Single<JsonObject> requestApi(@io.reactivex.annotations.NonNull String url, @io.reactivex.annotations.NonNull String method) {
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
