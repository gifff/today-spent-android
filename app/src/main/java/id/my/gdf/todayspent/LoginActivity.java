package id.my.gdf.todayspent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import id.my.gdf.todayspent.model.Login;
import id.my.gdf.todayspent.model.LoginBody;
import id.my.gdf.todayspent.model.SingleSpending;
import id.my.gdf.todayspent.service.Service;
import id.my.gdf.todayspent.service.TodaySpentService;
import id.my.gdf.todayspent.spendings.SpendingsActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    public final static String APP_TAG = LoginActivity.class.getSimpleName();
    private TodaySpentService todaySpentService;

    private EditText mUsername;
    private EditText mPassword;
    private Button mLoginButton;
//    private ProgressBar mLoadingIndicator;
    private FrameLayout mLoadingLayout;

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsername = (EditText) findViewById(R.id.edt_username);
        mPassword = (EditText) findViewById(R.id.edt_password);
        mLoginButton = (Button) findViewById(R.id.btn_login_submit);
//        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mLoadingLayout = (FrameLayout) findViewById(R.id.layout_loading);


        todaySpentService = Service.getInstance().getService();

        sharedPref = this.getSharedPreferences("todayspent", MODE_PRIVATE);

        checkUserToken();

        mLoginButton.setOnClickListener(this);

    }

    private void checkUserToken() {
        final String token = sharedPref.getString("token", null);
        if (token != null) {
            disableLogin();
            todaySpentService.getSpendingDetail(token, "1")
                        .enqueue(new Callback<SingleSpending>() {
                        @Override
                        public void onResponse(Call<SingleSpending> call, Response<SingleSpending> response) {
                            try{
                                if (response.isSuccessful()) {
                                    showSpendingDetail(response.body());
                                } else if(response.code() == 401) {
                                    // Unauthorized
                                    // Clear user token
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.remove("token");
                                    editor.commit();
                                    enableLogin();
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Auto login failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SingleSpending> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Auto login failed", Toast.LENGTH_SHORT).show();
                            enableLogin();
                        }
                    });
        }
    }

    private void disableLogin() {
        mUsername.setEnabled(false);
        mPassword.setEnabled(false);
        mLoginButton.setEnabled(false);
//        mLoadingIndicator.setVisibility(View.VISIBLE);
        mLoadingLayout.setVisibility(View.VISIBLE);
    }
    private void enableLogin() {
        mUsername.setEnabled(true);
        mPassword.setEnabled(true);
        mLoginButton.setEnabled(true);
//        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mLoadingLayout.setVisibility(View.INVISIBLE);
    }

    private void attemptLogin() {

        if (mUsername.getText().toString().trim().isEmpty() || mPassword.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Username or password is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        disableLogin();
        Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show();

        LoginBody requestBody = new LoginBody();
        requestBody.setEmail(mUsername.getText().toString().trim());
        requestBody.setPassword(mPassword.getText().toString().trim());
        todaySpentService.postLogin(requestBody)
                .enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        try {
                            Login login = response.body();
                            if(response.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Login success :)", Toast.LENGTH_SHORT).show();

                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("token", login.getToken());
                                editor.commit();

                                fetchSpendingDetail(login.getToken(), "1");
                            } else
                                throw new Exception(""+response.code());
                        } catch (Exception e) {
                            Log.e(APP_TAG, "Response Code: " + e.getMessage());
                            Toast.makeText(getApplicationContext(), "Login failed :(", Toast.LENGTH_SHORT).show();
                        }
                        enableLogin();
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        Log.e(APP_TAG, "onFailure is called");
                        Toast.makeText(getApplicationContext(), "Login failed :(", Toast.LENGTH_SHORT).show();
                        enableLogin();
                    }
                });
    }

    private void fetchSpendingDetail(String token, String id) {
        todaySpentService.getSpendingDetail(token, id)
                .enqueue(new Callback<SingleSpending>() {
                    @Override
                    public void onResponse(Call<SingleSpending> call, Response<SingleSpending> response) {
                        try {
                            if (response.body() == null)
                                Log.v(APP_TAG, "body is null");
                            showSpendingDetail(response.body());
                        }catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Unable to fetch spending d detail", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SingleSpending> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Unable to fetch spending detail", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showSpendingDetail(SingleSpending spending) {
        if (spending != null){
            Intent intent = new Intent(this, SpendingsActivity.class);
            intent.putExtra("amount", "" + spending.getSpending().getUser_id());
            startActivity(intent);
            this.finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_submit: {
                attemptLogin();
                break;
            }
        }
    }
}
