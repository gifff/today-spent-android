package id.my.gdf.todayspent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import id.my.gdf.todayspent.model.SpendingList;
import id.my.gdf.todayspent.service.Service;
import id.my.gdf.todayspent.service.TodaySpentService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by prime10 on 12/3/17.
 */

public class SpendingListActivity extends AppCompatActivity {
    private TextView mAmountTextView;
    private RecyclerView mRecyclerViewSpendingList;
    private SpendingListAdapter mSpendingListAdapter;

    private TodaySpentService todaySpentService;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending_list);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerViewSpendingList = (RecyclerView)findViewById(R.id.rcv_spending_list);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewSpendingList.setLayoutManager(layoutManager);
        mRecyclerViewSpendingList.setHasFixedSize(true);

        mSpendingListAdapter = new SpendingListAdapter();

        mRecyclerViewSpendingList.setAdapter(mSpendingListAdapter);

        todaySpentService = Service.getInstance().getService();
        sharedPref = this.getSharedPreferences("todayspent", MODE_PRIVATE);


//        mAmountTextView = (TextView)findViewById(R.id.tv_spending_amount);
//        mAmountTextView.setText("Amount: " + getIntent().getStringExtra("amount"));
        loadData();
    }

    private void loadData() {
        todaySpentService.getSpendingList(this.sharedPref.getString("token", null))
                .enqueue(new Callback<SpendingList>() {
                    @Override
                    public void onResponse(Call<SpendingList> call, Response<SpendingList> response) {
                        try {
                            if (response.isSuccessful()) {
                                mSpendingListAdapter.setSpendingData(response.body().getSpendings());
                            } else {
                                Toast.makeText(getApplicationContext(), "Fetching Spending List failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Fetching Spending List failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SpendingList> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Fetching Spending List failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void logout() {
        SharedPreferences sharedPref = this.getSharedPreferences("todayspent", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("token");
        editor.commit();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_spending_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItemId = item.getItemId();

        if (selectedItemId == R.id.action_logout) {
            logout();
        }

        return super.onOptionsItemSelected(item);
    }
}
