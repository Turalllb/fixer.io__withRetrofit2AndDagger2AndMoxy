package mobiledimension.exchangerates.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import javax.inject.Inject;

import static mobiledimension.exchangerates.presenter.MainMenu.MainMenuPresenter.LOG_TAG;


/**
 * Created by Tural on 23.12.2017.
 */

public class DbOpenHelper extends SQLiteOpenHelper {

    @Inject
    public DbOpenHelper(Context context, String path) {
        super(context, path + "/"
                + "ExchangeRatesDatabase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase ExchangeRatesDatabase) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        ExchangeRatesDatabase.execSQL(ExchangeRatesTable.CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

