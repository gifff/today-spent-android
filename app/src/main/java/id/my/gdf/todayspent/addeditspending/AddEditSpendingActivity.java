package id.my.gdf.todayspent.addeditspending;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import id.my.gdf.todayspent.R;
import id.my.gdf.todayspent.data.Spending;
import id.my.gdf.todayspent.data.SpendingsDataSource;
import id.my.gdf.todayspent.data.source.local.SpendingsLocalDataSource;
import id.my.gdf.todayspent.data.source.local.TodaySpentDatabase;
import id.my.gdf.todayspent.util.ActivityUtils;
import id.my.gdf.todayspent.util.AppExecutors;

public class AddEditSpendingActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_SPENDING = 1;

    public final String TAG = getClass().getSimpleName();

    private AddEditSpendingPresenter mAddEditSpendingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_spending);

        AddEditSpendingFragment addEditSpendingFragment = (AddEditSpendingFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        long spendingLocalId = getIntent().getLongExtra(AddEditSpendingFragment.ARGUMENT_EDIT_SPENDING_ID, Spending.NULL_ID);
        Log.d(TAG, "spendingLocalId: " + spendingLocalId);

        if (addEditSpendingFragment == null) {
            addEditSpendingFragment = AddEditSpendingFragment.newInstance();

            if (getIntent().hasExtra(AddEditSpendingFragment.ARGUMENT_EDIT_SPENDING_ID)) {
                Bundle bundle = new Bundle();
                bundle.putLong(AddEditSpendingFragment.ARGUMENT_EDIT_SPENDING_ID, spendingLocalId);
                addEditSpendingFragment.setArguments(bundle);
            }

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    addEditSpendingFragment, R.id.contentFrame);
        }

        TodaySpentDatabase database = TodaySpentDatabase.getInstance(getApplicationContext());
        SpendingsDataSource spendingsDataSource = SpendingsLocalDataSource.getInstance(
                new AppExecutors(),
                database.spendingsDao()
        );

        mAddEditSpendingPresenter = new AddEditSpendingPresenter(spendingLocalId, addEditSpendingFragment, spendingsDataSource);
    }
}
