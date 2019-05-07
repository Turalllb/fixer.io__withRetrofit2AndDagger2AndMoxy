package mobiledimension.exchangerates.data.network;


import mobiledimension.exchangerates.data.db.model.PostModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiFixerHelper {

    @GET("{date}")
    Call<PostModel> getData(@Path("date") String date, @Query("access_key") String accessKey, @Query("base") String  base);
}
