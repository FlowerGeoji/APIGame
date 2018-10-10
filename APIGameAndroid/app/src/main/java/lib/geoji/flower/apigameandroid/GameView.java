package lib.geoji.flower.apigameandroid;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.game_view, this, false);
        addView(view);
    }

    public void initialize(int roomId) {
        this.roomId = roomId;
    }
}
