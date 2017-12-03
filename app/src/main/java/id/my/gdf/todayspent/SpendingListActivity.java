package id.my.gdf.todayspent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by prime10 on 12/3/17.
 */

public class SpendingListActivity extends AppCompatActivity {
    private TextView mAmountTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending_list);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        mAmountTextView = (TextView)findViewById(R.id.tv_spending_amount);
//        mAmountTextView.setText("Amount: " + getIntent().getStringExtra("amount"));
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
