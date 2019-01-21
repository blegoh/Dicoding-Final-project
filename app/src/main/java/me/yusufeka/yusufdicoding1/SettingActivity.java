package me.yusufeka.yusufdicoding1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import android.widget.Switch;

import me.yusufeka.yusufdicoding1.db.AppPreference;

public class SettingActivity extends AppCompatActivity {

    AppPreference appPreference;

    Switch dailyReminder;

    Switch releaseReminder;

    private boolean isDailyReminder;

    private boolean isReleaseReminder;

    private AlarmReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appPreference = new AppPreference(this);
        dailyReminder = findViewById(R.id.dailyReminder);
        releaseReminder = findViewById(R.id.releaseReminder);

        isDailyReminder = appPreference.getDailyActive();
        isReleaseReminder = appPreference.getReleaseActive();

        dailyReminder.setChecked(isDailyReminder);
        releaseReminder.setChecked(isReleaseReminder);

        alarmReceiver = new AlarmReceiver();

        dailyReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isDailyReminder = isChecked;
                appPreference.setDailyActive(isDailyReminder);
                alarmReceiver.cancelDailyReminder(SettingActivity.this);
            }
        });

        releaseReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isReleaseReminder = isChecked;
                appPreference.setReleaseActive(isReleaseReminder);
                alarmReceiver.cancelReleaseToday(SettingActivity.this);
            }
        });
    }

}
