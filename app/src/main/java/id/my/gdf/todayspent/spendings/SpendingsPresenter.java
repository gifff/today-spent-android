package id.my.gdf.todayspent.spendings;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import id.my.gdf.todayspent.model.SpendingList;
import id.my.gdf.todayspent.service.Service;
import id.my.gdf.todayspent.service.TodaySpentService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by prime10 on 1/3/18.
 */

public class SpendingsPresenter implements SpendingsContract.Presenter {

    private final String TAG = getClass().getSimpleName();

    private final SpendingsContract.View mSpendingsView;

    private final TodaySpentService todaySpentService;
    private SharedPreferences sharedPref;

    public SpendingsPresenter(@NonNull SpendingsContract.View spendingsView, @NonNull SharedPreferences sharedPref) {

        mSpendingsView = checkNotNull(spendingsView, "spendingsView cannot be null!");
        this.sharedPref = checkNotNull(sharedPref, "sharedPref cannot be null!");

        mSpendingsView.setPresenter(this);

        todaySpentService = Service.getInstance().getService();


    }

    @Override
    public void start() {
        loadSpendings();
    }

    @Override
    public void loadSpendings() {
        todaySpentService.getSpendingList(this.sharedPref.getString("token", null))
                .enqueue(new Callback<SpendingList>() {
                    @Override
                    public void onResponse(Call<SpendingList> call, Response<SpendingList> response) {
                        try {
                            if (response.isSuccessful()) {
                                mSpendingsView.showSpendings(response.body());
                            } else {
                                mSpendingsView.showErrorToast("Fetching spending list failed");
                                Log.e(TAG, "Fetching spending list failed");
                            }
                        } catch (Exception e) {
                            mSpendingsView.showErrorToast("Fetching spending list failed");
                            Log.e(TAG, "Fetching spending list failed");
                        }
                    }

                    @Override
                    public void onFailure(Call<SpendingList> call, Throwable t) {
                        mSpendingsView.showErrorToast("Fetching spending list failed");
                        Log.e(TAG, "Fetching spending list failed");
                    }
                });
    }

    @Override
    public void logout() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("token");
        editor.commit();
        mSpendingsView.showLogin();
    }
}
