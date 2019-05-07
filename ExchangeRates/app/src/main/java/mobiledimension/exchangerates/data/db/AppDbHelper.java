package mobiledimension.exchangerates.data.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;


import static mobiledimension.exchangerates.data.db.ExchangeRatesTable.COLUMN.CURRENCY;
import static mobiledimension.exchangerates.data.db.ExchangeRatesTable.COLUMN.DATE;
import static mobiledimension.exchangerates.data.db.ExchangeRatesTable.COLUMN.JSON;
import static mobiledimension.exchangerates.data.db.ExchangeRatesTable.TABLE;
import static mobiledimension.exchangerates.presenter.MainMenu.MainMenuPresenter.LOG_TAG;


/**
 * Created by Tural on 23.12.2017.
 */

public class  AppDbHelper implements DbHelper{

    private SQLiteDatabase exchangeRatesDatabase;

    @Inject
    AppDbHelper(SQLiteDatabase sqLiteDatabase) {
        exchangeRatesDatabase = sqLiteDatabase;
    }

    public void setDataBase(String strDate, String currency, String json) {
        // создаем объект для данных
        ContentValues cv = new ContentValues();
        try {
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("yyyy-MM-dd"); //проверить не парсит ли формат в котором дата находится изначально
            Date date = format.parse(strDate);
            long unixTimeDate = date.getTime();
            Log.d(LOG_TAG, unixTimeDate + "");
            // подготовим данные для вставки в виде пар: наименование столбца -
            // значение
            cv.put(DATE, unixTimeDate);
            cv.put(CURRENCY, currency);
            cv.put(JSON, json);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d(LOG_TAG, "--- Insert in ExchangeRatesDatabase: ---");
        // вставляем запись и получаем ее ID
        long rowID = exchangeRatesDatabase.insert(TABLE, null, cv);
        Log.d(LOG_TAG, "row inserted, ID = " + rowID);

        // делаем запрос всех данных из таблицы ExchangeRatesDatabase, получаем Cursor
        Cursor c = exchangeRatesDatabase.query(TABLE, null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idDate = c.getColumnIndex(DATE);
            int currencyColIndex = c.getColumnIndex(CURRENCY);
            int jsonColIndex = c.getColumnIndex(JSON);

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d(LOG_TAG,
                        "date = " + c.getLong(idDate) +
                                ", currency = " + c.getString(currencyColIndex) +
                                ", json = " + c.getString(jsonColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();
    }


    public String getStrPostModel(String strDate, String currency) {

        long unixTimeDate;
        String postModel = null;

        try {
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("yyyy-MM-dd");
            Date date = format.parse(strDate);
            unixTimeDate = date.getTime();

            Cursor c = exchangeRatesDatabase.query(TABLE,
                    new String[]{JSON},
                    "date = ? AND currency = ?",
                    new String[]{Long.toString(unixTimeDate), currency},
                    null, null, null);

            postModel = (c != null && c.moveToFirst()) ? c.getString(c.getColumnIndex(JSON)) : null;
            if (c != null) c.close();
        } catch (ParseException e) { //ловим не только ParseException, но и исключение, которое выбросится,
            e.printStackTrace();     // если в момент выполнения программы, удалить базу данных с
        }
        return postModel;
    }

}
 