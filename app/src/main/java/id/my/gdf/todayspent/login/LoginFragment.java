package id.my.gdf.todayspent.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import id.my.gdf.todayspent.R;
import id.my.gdf.todayspent.spendings.SpendingsActivity;

/**
 * Created by prime10 on 1/3/18.
 */

public class LoginFragment extends Fragment
        implements LoginContract.View, View.OnClickListener {

    private LoginContract.Presenter mPresenter;

    private EditText mUsername;
    private EditText mPassword;
    private Button mLoginButton;
    private FrameLayout mLoadingLayout;


    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        mUsername = (EditText) root.findViewById(R.id.edt_username);
        mPassword = (EditText) root.findViewById(R.id.edt_password);
        mLoginButton = (Button) root.findViewById(R.id.btn_login_submit);
        mLoadingLayout = (FrameLayout) root.findViewById(R.id.layout_loading);


        mLoginButton.setOnClickListener(this);


        mPresenter.checkUserToken();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
//        mPresenter.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_submit: {
                mPresenter.attemptLogin(mUsername.getText().toString(), mPassword.getText().toString());
                break;
            }
        }
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showSpendingsScreen() {
        startActivity(new Intent(this.getContext(), SpendingsActivity.class));
        this.getActivity().finish();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        mUsername.setEnabled(!active);
        mPassword.setEnabled(!active);
        mLoginButton.setEnabled(!active);
        mLoadingLayout.setVisibility(active ? View.VISIBLE : View.INVISIBLE);
    }
}
