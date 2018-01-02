package id.my.gdf.todayspent.service;

import id.my.gdf.todayspent.model.Index;
import id.my.gdf.todayspent.model.Login;
import id.my.gdf.todayspent.model.LoginBody;
import id.my.gdf.todayspent.model.SingleSpending;
import id.my.gdf.todayspent.model.SpendingList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Created by prime10 on 12/3/17.
 */

public interface TodaySpentService {

    @GET("/{base}")
    Call<Index> getIndex(@Path("base") String uri);


    @Headers({
            "Content-Type: application/json",
            "User-Agent: TodaySpent-Android"
    })
    @POST("/login")
    Call<Login> postLogin(@Body LoginBody body);

    @GET("/spending/{spendingId}")
    Call<SingleSpending> getSpendingDetail(@Header("Authorization") String token, @Path("spendingId") String spendingId);

    @GET("/spending/all")
    Call<SpendingList> getSpendingList(@Header("Authorization") String token);
}
