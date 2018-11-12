package app.geoji.flower.apigameandroidtester;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import lib.geoji.flower.apigameandroid.Game;
import lib.geoji.flower.apigameandroid.model.User;

public class MainActivity extends AppCompatActivity {
    private GamePagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewPager);
        adapter = new GamePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }


    static class GamePagerAdapter extends FragmentStatePagerAdapter {
        private final HostFragment hostFragment = new HostFragment();
        private final GuestFragment guestFragment = new GuestFragment();
        private final SettingFragment settingFragment = new SettingFragment();

        public GamePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return hostFragment;
                case 1:
                    return guestFragment;
                case 2:
                    return settingFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        adapter.settingFragment.onActivityResult(requestCode, resultCode, data);
    }
}
