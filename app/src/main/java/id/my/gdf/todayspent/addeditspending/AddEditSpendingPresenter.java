package id.my.gdf.todayspent.addeditspending;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import id.my.gdf.todayspent.data.Spending;
import id.my.gdf.todayspent.data.SpendingsDataSource;

/**
 * Created by prime10 on 1/9/18.
 */

public class AddEditSpendingPresenter implements AddEditSpendingContract.Presenter,
        SpendingsDataSource.GetSpendingCallback {

    @NonNull
    private final SpendingsDataSource mSpendingsDataSource;
    @NonNull
    private final AddEditSpendingContract.View mAddEditSpendingView;
    @NonNull
    private long mSpendingLocalId;
    private Spending mSpending;

    public AddEditSpendingPresenter(@NonNull long spendingLocalId,
                                    @NonNull AddEditSpendingContract.View view,
                                    @NonNull SpendingsDataSource dataSource) {
        mSpendingLocalId = spendingLocalId;
        mAddEditSpendingView = view;
        mSpendingsDataSource = dataSource;
        mAddEditSpendingView.setPresenter(this);
    }

    private boolean isNewSpending() {
        return mSpendingLocalId == Spending.NULL_ID;
    }

    @Override
    public void start() {
        if (!isNewSpending()) {
            populateSpending();
        }
    }

    @Override
    public void saveSpending(String amount, Date date) {
        if (amount.isEmpty()) {
            mAddEditSpendingView.showEmptyAmountError();
        }

        Spending spending;
        SimpleDateFormat formatter;
        try {
            formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);

            if (isNewSpending()) {
                createSpending(Long.parseLong(amount), formatter.format(date));
            } else {
                updateSpending(Long.parseLong(amount), formatter.format(date));
            }


            mAddEditSpendingView.showSpendingsList();
        } catch (NumberFormatException ex) {
            mAddEditSpendingView.showEmptyAmountError();
        }
    }

    private void createSpending(long amount, String date) {
        Spending spending = new Spending(amount, date);
        mSpendingsDataSource.saveSpending(spending);
    }

    private void updateSpending(long amount, String date) {
        Spending spending = new Spending(this.mSpendingLocalId, amount, date);
        mSpendingsDataSource.updateSpending(spending);
    }

    @Override
    public void populateSpending() {
        if (isNewSpending()) {
            throw new RuntimeException("populateSpending() was called but the mSpending is new.");
        }
        mSpendingsDataSource.getSpending(mSpendingLocalId, this);
    }

    @Override
    public void onSpendingLoaded(Spending spending) {
        mAddEditSpendingView.setAmount("" + spending.getAmount());
        mAddEditSpendingView.setDate(spending.getDate());
    }

    @Override
    public void onDataNotAvailable() {

    }
}
