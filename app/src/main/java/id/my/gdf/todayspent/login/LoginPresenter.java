package id.my.gdf.todayspent.login;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import id.my.gdf.todayspent.model.Login;
import id.my.gdf.todayspent.model.LoginBody;
import id.my.gdf.todayspent.model.SingleSpending;
import id.my.gdf.todayspent.service.Service;
import id.my.gdf.todayspent.service.TodaySpentService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by prime10 on 1/3/18.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private final String TAG = getClass().getSimpleName();

    private final LoginContract.View mLoginView;

    private TodaySpentService todaySpentService;
    private SharedPreferences sharedPref;

    public LoginPresenter(@NonNull LoginContract.View mLoginView, @NonNull SharedPreferences sharedPref) {
        this.mLoginView = checkNotNull(mLoginView, "loginView cannot be null!");
        this.sharedPref = checkNotNull(sharedPref, "sharedPref cannot be null!");

        mLoginView.setPresenter(this);

        todaySpentService = Service.getInstance().getService();
    }

    @Override
    public void checkUserToken() {
        final String token = sharedPref.getString("token", null);
        if (token != null) {

            // freeze the UI
            mLoginView.setLoadingIndicator(true);

            todaySpentService.getSpendingDetail(token, "1")
                    .enqueue(new Callback<SingleSpending>() {
                        @Override
                        public void onResponse(Call<SingleSpending> call, Response<SingleSpending> response) {
                            try{
                                if (response.isSuccessful()) {

                                    // Open up the Spendings Screen
                                    mLoginView.showSpendingsScreen();

                                } else if(response.code() == 401) {

                                    // Unauthorized
                                    // Clear user token
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.remove("token");
                                    editor.commit();

                                }

                            } catch (Exception e) {
                                mLoginView.showToast("Auto login failed");
                            } finally {

                                // unfreeze the UI
                                mLoginView.setLoadingIndicator(false);

                            }
                        }

                        @Override
                        public void onFailure(Call<SingleSpending> call, Throwable t) {
                            mLoginView.showToast("Auto login failed");
                            mLoginView.setLoadingIndicator(false);
                        }
                    });
        }
    }

    @Override
    public void start() {
        checkUserToken();
    }

    @Override
    public void attemptLogin(String email, String password) {

        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            mLoginView.showToast("Username or password is empty");
            return;
        }

        // freeze login UI
        mLoginView.setLoadingIndicator(true);

//        mLoginView.showToast("Logging in...");

        LoginBody requestBody = new LoginBody();
        requestBody.setEmail(email.trim());
        requestBody.setPassword(password.trim());
        todaySpentService.postLogin(requestBody)
                .enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        try {
                            Login login = response.body();
                            if(response.isSuccessful()){

                                mLoginView.showToast("Login success :)");

                                // Save the token
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("token", login.getToken());
                                editor.commit();

                                mLoginView.showSpendingsScreen();

                            } else
                                throw new Exception(""+response.code());
                        } catch (Exception e) {
                            Log.e(TAG, "Response Code: " + e.getMessage());
                            mLoginView.showToast("Login failed :(");
                        } finally {
                            // unfreeze login UI
                            mLoginView.setLoadingIndicator(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        Log.e(TAG, "onFailure is called");
                        mLoginView.showToast("Login failed :(");

                        // unfreeze login UI
                        mLoginView.setLoadingIndicator(false);
                    }
                });
    }
}
