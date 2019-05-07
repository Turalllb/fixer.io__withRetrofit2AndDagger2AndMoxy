package mobiledimension.exchangerates;

import android.app.Application;

import mobiledimension.exchangerates.di.AppComponent;
import mobiledimension.exchangerates.di.DaggerAppComponent;
import mobiledimension.exchangerates.di.module.AppModule;


public class MyApplication extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();


    }

    public static AppComponent getAppComponent(){
        return appComponent;
    }


}
