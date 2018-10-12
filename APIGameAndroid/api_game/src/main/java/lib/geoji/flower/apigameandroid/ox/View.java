package lib.geoji.flower.apigameandroid.ox;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class View extends LinearLayout {

    public View(Context context) {
        super(context);
    }

    public View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initialize() {
        Presenter presenter = new Presenter();
    }

    private void changeScene() {

    }
}
