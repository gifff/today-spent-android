package id.my.gdf.todayspent.data.source.local;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import java.util.List;

import id.my.gdf.todayspent.data.Spending;
import id.my.gdf.todayspent.data.SpendingsDataSource;
import id.my.gdf.todayspent.util.AppExecutors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by prime10 on 1/4/18.
 */

public class SpendingsLocalDataSource implements SpendingsDataSource {

    private static volatile SpendingsLocalDataSource INSTANCE;

    private SpendingsDao mSpendingsDao;

    private AppExecutors mAppExecutors;

    // Prevent direct instantiation.
    private SpendingsLocalDataSource(@NonNull AppExecutors appExecutors,
                                     @NonNull SpendingsDao spendingsDao) {
        mAppExecutors = appExecutors;
        mSpendingsDao = spendingsDao;
    }

    public static SpendingsLocalDataSource getInstance(@NonNull AppExecutors appExecutors,
                                                       @NonNull SpendingsDao spendingsDao) {
        if (INSTANCE == null) {
            synchronized (SpendingsLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SpendingsLocalDataSource(appExecutors, spendingsDao);
                }
            }
        }

        return INSTANCE;
    }

    @VisibleForTesting
    static void clearInstance() { INSTANCE = null; }

    @Override
    public void getSpendings(@NonNull final LoadSpendingsCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Spending> spendings = mSpendingsDao.getSpendings();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (spendings.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onSpendingsLoaded(spendings);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getSpending(@NonNull final long spendingLocalId, @NonNull final GetSpendingCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Spending spending = mSpendingsDao.getSpendingByLocalId(spendingLocalId);

                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (spending != null) {
                            callback.onSpendingLoaded(spending);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveSpending(@NonNull final Spending spending) {
        checkNotNull(spending);
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mSpendingsDao.insertSpending(spending);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void updateSpendingAmount(@NonNull final long spendingLocalId, @NonNull final long amount) {
        Runnable updateRunnable = new Runnable() {
            @Override
            public void run() {
                mSpendingsDao.updateAmount(spendingLocalId, amount);
            }
        };

        mAppExecutors.diskIO().execute(updateRunnable);
    }

    @Override
    public void updateSpendingDate(@NonNull final long spendingLocalId, @NonNull final String date) {
        Runnable updateRunnable = new Runnable() {
            @Override
            public void run() {
                mSpendingsDao.updateDate(spendingLocalId, date);
            }
        };

        mAppExecutors.diskIO().execute(updateRunnable);
    }

    @Override
    public void synchronize() {
        // This involves networkIO
        /*
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };

        mAppExecutors.networkIO().execute(runnable);
        */
    }

    @Override
    public void deleteAllSpendings() {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mSpendingsDao.deleteSpendings();
            }
        };

        mAppExecutors.diskIO().execute(deleteRunnable);
    }

    @Override
    public void deleteSpending(@NonNull final long spendingLocalId) {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mSpendingsDao.deleteSpendingByLocalId(spendingLocalId);
            }
        };

        mAppExecutors.diskIO().execute(deleteRunnable);
    }
}
