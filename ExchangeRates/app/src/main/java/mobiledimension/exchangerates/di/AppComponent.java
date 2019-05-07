package mobiledimension.exchangerates.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import mobiledimension.exchangerates.data.network.ApiFixer;
import mobiledimension.exchangerates.di.module.AppModule;
import mobiledimension.exchangerates.di.module.DbModule;
import mobiledimension.exchangerates.di.module.PathModule;
import mobiledimension.exchangerates.presenter.MainMenu.MainMenuPresenter;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    Context context();
}
