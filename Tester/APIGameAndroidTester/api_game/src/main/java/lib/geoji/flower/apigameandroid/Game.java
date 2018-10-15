package lib.geoji.flower.apigameandroid;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import io.reactivex.Single;
import lib.geoji.flower.apigameandroid.model.GameModule;
import lib.geoji.flower.apigameandroid.model.Round;
import lib.geoji.flower.apigameandroid.ox.OxInitView;

public class Game extends RelativeLayout {
    private View gameListView;
    private ViewGroup currentView = null;

    private int roomId;
    private ArrayList<Round> rounds;
    private int currentRoundIndex;

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
            /* init view */
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gameListView = layoutInflater.inflate(R.layout.game_view, this, false);

            // recycler view
            RecyclerView gameListView = this.gameListView.findViewById(R.id.gameRecyclerView);
            GameListAdapter gameListAdapter = new GameListAdapter();
            gameListView.setAdapter(gameListAdapter);
            gameListAdapter.addItem(new GameModule(GameModule.Type.OX, "OX", "OX description", "", 0));
            gameListAdapter.addItem(new GameModule(GameModule.Type.CHOICE, "Choice", "Choice description", "", 0));
            gameListAdapter.addItem(new GameModule(GameModule.Type.SURVIVAL, "Survival", "Survival description", "", 0));

            addView(this.gameListView);

            /* init presenter */
            this.rounds = new ArrayList<>();
            this.rounds.add(new Round(Round.Type.OX, 10, "", "", new String[]{"0", "1"}, "0"));
            this.currentRoundIndex = 0;
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public void initRoom(int roomId) {
        this.roomId = roomId;
    }

    void changeView(@NonNull ViewGroup view) {
        if (currentView != null) {
            this.removeView(currentView);
        }

        this.gameListView.setVisibility(GONE);
        this.addView(view);
        this.currentView = view;
    }

    private void onClickGameModule(GameModule gameModule) {
        switch (gameModule.getType()) {
            case OX:

                this.changeView(new OxInitView(this.getContext(), this));
                break;
            case CHOICE:
                break;
            case SURVIVAL:
                break;
        }
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
