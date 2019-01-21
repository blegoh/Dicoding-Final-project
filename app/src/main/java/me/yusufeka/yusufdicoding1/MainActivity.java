package me.yusufeka.yusufdicoding1;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import me.yusufeka.yusufdicoding1.db.AppPreference;
import me.yusufeka.yusufdicoding1.fragments.FavoriteFragment;
import me.yusufeka.yusufdicoding1.fragments.NowPlayingFragment;
import me.yusufeka.yusufdicoding1.fragments.SearchFragment;
import me.yusufeka.yusufdicoding1.fragments.UpComingFragment;

public class MainActivity extends AppCompatActivity {

    FragmentTransaction mFragmentTransaction;

    FragmentManager mFragmentManager;

    NowPlayingFragment nowPlayingFragment;

    UpComingFragment upComingFragment;

    SearchFragment searchFragment;

    FavoriteFragment favoriteFragment;

    BottomNavigationView bottomNavigationView;

    private AlarmReceiver alarmReceiver;

    private int activeTab = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();
        nowPlayingFragment = new NowPlayingFragment();
        upComingFragment = new UpComingFragment();
        searchFragment = new SearchFragment();
        favoriteFragment = new FavoriteFragment();

        if (savedInstanceState != null) {
            Fragment f = getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
            if (f instanceof NowPlayingFragment) {
                nowPlayingFragment = (NowPlayingFragment) getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
                activeTab = 1;
            }else if (f instanceof UpComingFragment){
                upComingFragment = (UpComingFragment) getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
                activeTab = 2;
            }else if (f instanceof SearchFragment){
                searchFragment = (SearchFragment) getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
                activeTab = 3;
            }else if (f instanceof FavoriteFragment){
                favoriteFragment = (FavoriteFragment) getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
                activeTab = 4;
            }
        }
        updateFragment();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavListener());
        alarmReceiver = new AlarmReceiver(this);
        setAlarmReceiver();
    }

    private void setAlarmReceiver(){
        AppPreference appPreference = new AppPreference(this);
        boolean dailyReminder = appPreference.getDailyActive();
        boolean releaseReminder = appPreference.getReleaseActive();
        if (dailyReminder){
            alarmReceiver.setDailyReminder();
        }
        if (releaseReminder){
            alarmReceiver.setReleaseTodayReminder();
        }
    }

    private void updateFragment(){
        mFragmentTransaction = mFragmentManager.beginTransaction();
        switch (activeTab) {
            case 1:
                mFragmentTransaction.replace(R.id.frame_container, nowPlayingFragment, NowPlayingFragment.class.getSimpleName());
                break;
            case 2:
                mFragmentTransaction.replace(R.id.frame_container, upComingFragment, UpComingFragment.class.getSimpleName());
                break;
            case 3:
                mFragmentTransaction.replace(R.id.frame_container, searchFragment, SearchFragment.class.getSimpleName());
                break;
            case 4:
                mFragmentTransaction.replace(R.id.frame_container, favoriteFragment, FavoriteFragment.class.getSimpleName());
                break;
        }
        mFragmentTransaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        switch (activeTab) {
            case 1:
                getSupportFragmentManager().putFragment(savedInstanceState, "fragment", nowPlayingFragment);
                break;
            case 2:
                getSupportFragmentManager().putFragment(savedInstanceState, "fragment", upComingFragment);
                break;
            case 3:
                getSupportFragmentManager().putFragment(savedInstanceState, "fragment", searchFragment);
                break;
            case 4:
                getSupportFragmentManager().putFragment(savedInstanceState, "fragment", favoriteFragment);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                return true;
            default:
                Intent intent = new Intent(this,SettingActivity.class);
                startActivity(intent);
                return true;
        }
    }

    class BottomNavListener implements BottomNavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_now_playing:
                    activeTab = 1;
                    break;
                case R.id.action_up_coming:
                    activeTab = 2;
                    break;
                case R.id.action_search:
                    activeTab = 3;
                    break;
                case R.id.action_favorite:
                    activeTab = 4;
                    break;
            }
            updateFragment();
            updateNavigationBarState(item.getItemId());
            return true;
        }

        private void updateNavigationBarState(int actionId) {
            Menu menu = bottomNavigationView.getMenu();

            for (int i = 0; i < menu.size(); i++) {
                MenuItem item = menu.getItem(i);
                item.setChecked(item.getItemId() == actionId);
            }
        }

    }
}
