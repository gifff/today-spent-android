package id.my.gdf.todayspent.addeditspending;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import id.my.gdf.todayspent.R;
import id.my.gdf.todayspent.data.SpendingsDataSource;
import id.my.gdf.todayspent.data.source.local.SpendingsLocalDataSource;
import id.my.gdf.todayspent.data.source.local.TodaySpentDatabase;
import id.my.gdf.todayspent.util.ActivityUtils;
import id.my.gdf.todayspent.util.AppExecutors;

public class AddEditSpendingActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_SPENDING = 1;

    private AddEditSpendingPresenter mAddEditSpendingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_spending);

        AddEditSpendingFragment addEditSpendingFragment = (AddEditSpendingFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (addEditSpendingFragment == null) {
            addEditSpendingFragment = AddEditSpendingFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    addEditSpendingFragment, R.id.contentFrame);
        }

        TodaySpentDatabase database = TodaySpentDatabase.getInstance(getApplicationContext());
        SpendingsDataSource spendingsDataSource = SpendingsLocalDataSource.getInstance(
                new AppExecutors(),
                database.spendingsDao()
        );

        mAddEditSpendingPresenter = new AddEditSpendingPresenter(addEditSpendingFragment, spendingsDataSource);
    }
}
