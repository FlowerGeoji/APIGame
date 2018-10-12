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

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;

import lib.geoji.flower.apigameandroid.model.Game;

public class GameView extends RelativeLayout {
    private int roomId;

    public GameView(@NonNull Context context) {
        super(context);
        this.initView();
    }

    public GameView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.initView();
    }

    public GameView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView();
    }

    private void initView() {
        try {
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.game_view, this, false);

            // recycler view
            RecyclerView gameListView = view.findViewById(R.id.gameRecyclerView);
            GameListAdapter gameListAdapter = new GameListAdapter();
            gameListView.setAdapter(gameListAdapter);
            gameListAdapter.addItem(new Game(Game.GameModules.OX, "OX", "OX description", "", 0));
            gameListAdapter.addItem(new Game(Game.GameModules.CHOICE, "Choice", "Choice description", "", 0));
            gameListAdapter.addItem(new Game(Game.GameModules.SURVIVAL, "Survival", "Survival description", "", 0));

            addView(view);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public void initialize(int roomId) {
        this.roomId = roomId;
    }

    // RecyclerView adapter
    private class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder> {
        private ArrayList<Game> games = new ArrayList<>();

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_module_view, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            try {
                Game game = this.games.get(position);
                holder.bind(game);
            }
            catch(IndexOutOfBoundsException e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return this.games.size();
        }

        public void addItem(Game game) {
            this.games.add(game);
            this.notifyItemInserted(this.games.size()-1);
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private Game game;
            private ImageView imageView;
            private TextView nameTextView;
            private TextView descriptionTextView;

            private ViewHolder(View itemView) {
                super(itemView);
                this.imageView = itemView.findViewById(R.id.gameImageView);
                this.nameTextView = itemView.findViewById(R.id.gameNameTextView);
                this.descriptionTextView = itemView.findViewById(R.id.gameDescriptionTextView);
            }

            public void bind(Game game) {
                this.game = game;
                Picasso.get()
                        .load(game.getThumbnail())
                        .into(this.imageView);
                this.nameTextView.setText(game.getDisplayName());
                this.descriptionTextView.setText(game.getDescription());
            }
        }
    }
}
