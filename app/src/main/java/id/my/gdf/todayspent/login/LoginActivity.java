package id.my.gdf.todayspent.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import id.my.gdf.todayspent.R;
import id.my.gdf.todayspent.service.TodaySpentService;
import id.my.gdf.todayspent.util.ActivityUtils;

public class LoginActivity extends AppCompatActivity{
    public final static String APP_TAG = LoginActivity.class.getSimpleName();
    private TodaySpentService todaySpentService;

    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginFragment loginFragment =
                (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (loginFragment == null) {
            // Create the fragment
            loginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), loginFragment, R.id.contentFrame
            );
        }

        // Create the presenter
        mLoginPresenter = new LoginPresenter(loginFragment,
                this.getSharedPreferences("todayspent", MODE_PRIVATE));

    }

}
