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

    void updateSpendingAmount(@NonNull long spendingLocalId, @NonNull long amount);

    void updateSpendingDate(@NonNull long spendingLocalId, @NonNull String date);

    void synchronize();

    void deleteAllSpendings();

    void deleteSpending(@NonNull long spendingLocalId);

    interface LoadSpendingsCallback {

        void onSpendingsLoaded(List<Spending> spendings);

        void onDataNotAvailable();

    }

    interface GetSpendingCallback {

        void onSpendingLoaded(Spending spending);

        void onDataNotAvailable();

    }

}
