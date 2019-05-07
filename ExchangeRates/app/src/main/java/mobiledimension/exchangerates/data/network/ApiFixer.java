package mobiledimension.exchangerates.data.network;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiFixer {

   private ApiFixerHelper apiFixerHelper;

    @Inject
    public ApiFixer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiFixerHelper = retrofit.create(ApiFixerHelper.class);
    }

    @Provides
    ApiFixerHelper getApiFixerHelper(){
        return apiFixerHelper;
    }

}
