package me.yusufeka.yusufdicoding1.services;

import android.content.Intent;
import android.widget.RemoteViewsService;

import me.yusufeka.yusufdicoding1.StackRemoteViewsFactory;

public class StackWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
