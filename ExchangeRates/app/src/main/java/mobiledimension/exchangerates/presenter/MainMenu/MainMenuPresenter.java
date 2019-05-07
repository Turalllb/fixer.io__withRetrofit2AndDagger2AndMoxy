package mobiledimension.exchangerates.presenter.MainMenu;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import mobiledimension.exchangerates.R;
import mobiledimension.exchangerates.data.DataManager;
import mobiledimension.exchangerates.data.db.model.ModelData;
import mobiledimension.exchangerates.data.db.model.PostModel;
import mobiledimension.exchangerates.ui.MainMenu.MainView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static mobiledimension.exchangerates.data.db.model.ModelData.COMPARATOR_NAME;
import static mobiledimension.exchangerates.data.db.model.ModelData.COMPARATOR_VALUE_ASCENDING;
import static mobiledimension.exchangerates.data.db.model.ModelData.COMPARATOR_VALUE_DESCENDING;

@InjectViewState
public class MainMenuPresenter extends MvpPresenter<MainView>  {

    public static final String LOG_TAG = "myLogs";
    private final String ACCESS_KEY = "0cd4416cd335bb08486b95e597b8c6b3"; //Для доступа к апи сайта. Есть ограничения в бесплатной версии.
    private String currentCurrency = "EUR";
    private List<ModelData> ratesArrayList = new ArrayList<>(); //список из моделей (валюта курс)
    private List<String> currenciesArrayList = new ArrayList<>(Arrays.asList("EUR")); //Список валют для спиннера
    private int checkedId;
    private DataManager dataManager;
    private String currentDate;


    @Inject
    public MainMenuPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }


    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }


    public void currencyChanged(int position) {
        currentCurrency = currenciesArrayList.get(position);
        uploadData();
        getViewState().refreshAdapterModelDate();
    }


    private void uploadData() {
        ratesArrayList.clear(); //очищаю взарание список валют
        final PostModel postModel = dataManager.downloadFromDataBase(currentDate, currentCurrency);
         if (postModel != null) {
            setData(postModel);
        } else {
            dataManager.downloadDataFromNetwork(currentDate, ACCESS_KEY, currentCurrency, new Callback<PostModel>() {
                @Override
                public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                    PostModel postModel = response.body();
                    validationOfData(postModel);
                }

                @Override
                public void onFailure(Call<PostModel> call, Throwable t) {
                    getViewState().showToast("Сетевая ошибка");
                }
            });
        }
    }

    //Некоторые специфические для Fixer апи проверки
    private void validationOfData(PostModel postModel) {
        if (postModel.getDate() == null) {
            getViewState().refreshAdapterModelDate();
            getViewState().showToast("В бесплатной версии доступны только курсы по отношению к EUR");
        } else if (!postModel.getDate().equals(currentDate)) {
            getViewState().refreshAdapterModelDate(); //обновляю, чтобы показать List без результатов
            getViewState().showToast("Курсы обновляются в рабочие дни после 16.00 по msk");
        } else {
            //Если всё в порядке
            setData(postModel);
            //Сохраняю в БД
            dataManager.setDataBase(postModel);
        }
    }

    private void setData(PostModel postModel) {
        ratesArrayList.addAll(postModel.getRates());
        currenciesArrayList.clear();
        currenciesArrayList.addAll(postModel.getCurrenciesArrayList());
        Collections.sort(currenciesArrayList);
        getViewState().refreshSpinner();
        /* Не всегда в спиннере после обновления будет стоять валюта по которой сделан запрос,
           так как список спиннера тоже всегда обновляется, поэтому вручную устанавливаю текущую валюту*/
        getViewState().spinnerSetSelection(currenciesArrayList.indexOf(currentCurrency));
        sorting();
        getViewState().refreshAdapterModelDate();
    }


    public void onDatePicked(String date) {
        currentDate = date;
        uploadData();
    }


    public void onChangedSortType(int checkedId) {
        this.checkedId = checkedId;
        sorting();
        getViewState().refreshAdapterModelDate();
    }

    private void sorting() {
        switch (checkedId) {
            case R.id.radioButton1:
                Collections.sort(ratesArrayList, COMPARATOR_NAME);
                break;
            case R.id.radioButton2:
                Collections.sort(ratesArrayList, COMPARATOR_VALUE_ASCENDING);
                break;
            case R.id.radioButton3:
                Collections.sort(ratesArrayList, COMPARATOR_VALUE_DESCENDING);
                break;
        }
    }


    public List<String> getCurrencies() {
        return currenciesArrayList;
    }


    public List<ModelData> getRates() {
        return ratesArrayList;
    }


    public String getCurrentCurrency(){
        return currentCurrency;
    }
}
