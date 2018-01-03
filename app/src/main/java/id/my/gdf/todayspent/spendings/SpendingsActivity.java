package id.my.gdf.todayspent.spendings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import id.my.gdf.todayspent.R;
import id.my.gdf.todayspent.util.ActivityUtils;

/**
 * Created by prime10 on 12/3/17.
 */

public class SpendingsActivity extends AppCompatActivity {


    private SpendingsPresenter mSpendingsPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spendings);


        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        SpendingsFragment spendingsFragment =
                (SpendingsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (spendingsFragment == null) {
            // Create the fragment
            spendingsFragment = SpendingsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), spendingsFragment, R.id.contentFrame
            );
        }

        // Create the presenter
        mSpendingsPresenter = new SpendingsPresenter(spendingsFragment,
                this.getSharedPreferences("todayspent", MODE_PRIVATE));


    }


}
