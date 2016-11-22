package com.lebartodev.mercurylab4;

import android.app.Application;

import com.orm.SugarContext;

/**
 * Created by Александр on 22.11.2016.
 */

public class ApplicationArticles extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();

    }
}
