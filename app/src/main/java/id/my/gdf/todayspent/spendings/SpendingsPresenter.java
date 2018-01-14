package id.my.gdf.todayspent.spendings;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import id.my.gdf.todayspent.data.Spending;
import id.my.gdf.todayspent.data.SpendingsDataSource;
import id.my.gdf.todayspent.service.Service;
import id.my.gdf.todayspent.service.TodaySpentService;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by prime10 on 1/3/18.
 */

public class SpendingsPresenter implements SpendingsContract.Presenter {

    private final String TAG = getClass().getSimpleName();

    private final SpendingsContract.View mSpendingsView;

    private final TodaySpentService todaySpentService;
    private final SpendingsDataSource mDataSource;
    private SharedPreferences sharedPref;

    public SpendingsPresenter(@NonNull SpendingsContract.View spendingsView, @NonNull SharedPreferences sharedPref, @NonNull SpendingsDataSource dataSource) {

        mSpendingsView = checkNotNull(spendingsView, "spendingsView cannot be null!");
        this.sharedPref = checkNotNull(sharedPref, "sharedPref cannot be null!");

        mSpendingsView.setPresenter(this);

        todaySpentService = Service.getInstance().getService();
        mDataSource = dataSource;


    }

    @Override
    public void start() {
        loadSpendings();
    }

    @Override
    public void loadSpendings() {
        mDataSource.getSpendings(new SpendingsDataSource.LoadSpendingsCallback() {
            @Override
            public void onSpendingsLoaded(List<Spending> spendings) {
                mSpendingsView.showSpendings(spendings);
            }

            @Override
            public void onDataNotAvailable() {
                mSpendingsView.showErrorToast("The list is empty");
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

    @Override
    public void addSpending() {
        mSpendingsView.showAddSpending();
    }

    @Override
    public void deleteSpending(long spendingLocalId) {
        mDataSource.deleteSpending(spendingLocalId);
    }

    @Override
    public void editSpending(final long spendingLocalId) {
        mDataSource.getSpending(spendingLocalId, new SpendingsDataSource.GetSpendingCallback() {
            @Override
            public void onSpendingLoaded(Spending spending) {
                mSpendingsView.showEditSpending(spending);
            }

            @Override
            public void onDataNotAvailable() {
                Log.v(TAG, "Data not available");
            }
        });
    }
}
