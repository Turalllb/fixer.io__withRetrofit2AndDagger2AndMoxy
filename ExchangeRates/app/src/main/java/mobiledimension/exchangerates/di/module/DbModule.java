package mobiledimension.exchangerates.di.module;

import android.database.sqlite.SQLiteDatabase;

import dagger.Module;
import dagger.Provides;
import mobiledimension.exchangerates.data.db.AppDbHelper;
import mobiledimension.exchangerates.data.db.DbHelper;
import mobiledimension.exchangerates.data.db.DbOpenHelper;

@Module
public class DbModule {

    @Provides
    SQLiteDatabase provideSQLiteDatabase(DbOpenHelper dbOpenHelper) {
        return dbOpenHelper.getWritableDatabase();
    }

    @Provides
    DbHelper provideDbHelper(AppDbHelper appDbHelper){
        return  appDbHelper;
    }

}
