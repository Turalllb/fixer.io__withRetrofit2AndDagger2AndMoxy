package mobiledimension.exchangerates.di.module;

import android.content.Context;
import android.os.Environment;

import dagger.Module;
import dagger.Provides;

@Module
public class PathModule {

    @Provides
    String getPath(Context context) {
        //Проверяем наличие внешней памяти и возращаем путь для создания БД
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return context.getExternalFilesDir(null).getAbsolutePath();
        } else {
            return context.getFilesDir().getAbsolutePath();
        }
    }
}
