package mobiledimension.exchangerates.data;

import com.google.gson.Gson;

import javax.inject.Inject;

import mobiledimension.exchangerates.data.db.AppDbHelper;
import mobiledimension.exchangerates.data.db.DbHelper;
import mobiledimension.exchangerates.data.db.model.PostModel;
import mobiledimension.exchangerates.data.network.ApiFixerHelper;
import retrofit2.Callback;

public class DataManager {

    private DbHelper dbHelper;
    private ApiFixerHelper apiFixerHelper;


    @Inject
    DataManager(DbHelper dbHelper, ApiFixerHelper apiFixerHelper) {
        this.dbHelper = dbHelper;
        this.apiFixerHelper = apiFixerHelper;
    }

    public void downloadDataFromNetwork(String currentDate, String ACCESS_KEY, String currentCurrency, Callback<PostModel> callback) {
        apiFixerHelper.getData(currentDate, ACCESS_KEY, currentCurrency).enqueue(callback);
    }


    public PostModel downloadFromDataBase(String currentDate, String currentCurrency) {
        String strPostModel = dbHelper.getStrPostModel(currentDate, currentCurrency);
        if (strPostModel != null) {
            //Если в БД уже есть результат
            return new Gson().fromJson(strPostModel, PostModel.class);
        }
        return null;
    }

    public void setDataBase(PostModel postModel) {
        dbHelper.setDataBase(postModel.getDate(), postModel.getBase(), postModel.toString());
    }


}
