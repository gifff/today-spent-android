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

public class AddEditSpendingPresenter implements AddEditSpendingContract.Presenter {

    @NonNull
    private final SpendingsDataSource mSpendingsDataSource;

    @NonNull
    private final AddEditSpendingContract.View mAddEditSpendingView;

    public AddEditSpendingPresenter(@NonNull AddEditSpendingContract.View view, @NonNull SpendingsDataSource dataSource) {
        mAddEditSpendingView = view;
        mSpendingsDataSource = dataSource;
        mAddEditSpendingView.setPresenter(this);
    }
    @Override
    public void start() {

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
            spending = new Spending(Long.parseLong(amount), formatter.format(date));
            mSpendingsDataSource.saveSpending(spending);
            mAddEditSpendingView.showSpendingsList();
        } catch (NumberFormatException ex) {
            mAddEditSpendingView.showEmptyAmountError();
        }
    }

    @Override
    public void populateTask() {

    }
}
