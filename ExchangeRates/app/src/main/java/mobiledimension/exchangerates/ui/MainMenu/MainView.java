package mobiledimension.exchangerates.ui.MainMenu;


import com.arellomobile.mvp.MvpView;

public interface MainView extends MvpView {

    void refreshSpinner();

    void spinnerSetSelection(int position);

    void refreshAdapterModelDate();

    void showToast(String message);
}
