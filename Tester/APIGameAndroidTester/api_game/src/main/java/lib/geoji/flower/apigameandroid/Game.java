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

import java.util.ArrayList;

import lib.geoji.flower.apigameandroid.model.GameModule;

public class Game extends RelativeLayout {
    private GamePresenter presenter;
    private View gameListView;
    private GameView currentView = null;

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
            this.presenter = new GamePresenter(this);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public void initialize(int roomId) {
        this.presenter.setRoomId(roomId);
    }

    public GamePresenter getPresenter() {
        return presenter;
    }

    public void changeView(GameView gameView) {
        if (currentView != null) {
            this.removeView(currentView);
        }

        if (gameView == null) {
            this.gameListView.setVisibility(VISIBLE);
        }
        else {
            this.gameListView.setVisibility(GONE);
            this.addView(gameView);
            this.currentView = gameView;
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

                holder.getView().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Game.this.presenter.onClickGameModule(game);
                    }
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

        public void addItem(GameModule game) {
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

            public void bind(GameModule gameModule) {
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
