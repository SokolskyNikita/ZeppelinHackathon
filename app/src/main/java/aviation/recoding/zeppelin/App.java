package aviation.recoding.zeppelin;

import android.app.Application;

public final class App extends Application {

    private static App mInstance = null;


    public static App getInstance() {
        return mInstance;
    }

}

