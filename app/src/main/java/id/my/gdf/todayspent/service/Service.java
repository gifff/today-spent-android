package id.my.gdf.todayspent.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by prime10 on 1/2/18.
 */

public class Service {
    private static final String SERVER_URL = "";
    private static Service instance;
    private final Retrofit retrofit;
    private final TodaySpentService todaySpentService;
    private Service() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://today-spent.gdf.my.id/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        todaySpentService = retrofit.create(TodaySpentService.class);
    }

    // Singleton Constructor
    public static Service getInstance() {
        if (Service.instance == null) {
            instance = new Service();
        }
        return instance;
    }

    public TodaySpentService getService() {
        return todaySpentService;
    }

}
