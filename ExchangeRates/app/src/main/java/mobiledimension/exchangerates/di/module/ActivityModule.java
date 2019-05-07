package mobiledimension.exchangerates.di.module;

import dagger.Module;
import dagger.Provides;
import mobiledimension.exchangerates.Utils.NetworkChangeReceiver;
import mobiledimension.exchangerates.data.DataManager;
import mobiledimension.exchangerates.di.PerActivity;
import mobiledimension.exchangerates.presenter.MainMenu.MainMenuPresenter;

@Module
public class ActivityModule {

    @Provides
    NetworkChangeReceiver provideNetworkChangeReceiver() {
        return new NetworkChangeReceiver();
    }

    @Provides
    @PerActivity
    MainMenuPresenter providerMainMenuPresenter(DataManager dataManager) {
        return new MainMenuPresenter(dataManager);
    }

}
