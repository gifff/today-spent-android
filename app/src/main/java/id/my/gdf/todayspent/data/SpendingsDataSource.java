package id.my.gdf.todayspent.data;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by prime10 on 1/4/18.
 */

public interface SpendingsDataSource {

    void getSpendings(@NonNull LoadSpendingsCallback callback);

    void getSpending(@NonNull long spendingLocalId, @NonNull GetSpendingCallback callback);

    void saveSpending(@NonNull Spending spending);

    void updateSpendingAmount(long spendingLocalId, long amount);

    void updateSpendingDate(long spendingLocalId, @NonNull String date);

    void updateSpending(@NonNull Spending spending);

    void synchronize();

    void deleteAllSpendings();

    void deleteSpending(long spendingLocalId);

    interface LoadSpendingsCallback {

        void onSpendingsLoaded(List<Spending> spendings);

        void onDataNotAvailable();

    }

    interface GetSpendingCallback {

        void onSpendingLoaded(Spending spending);

        void onDataNotAvailable();

    }

}
