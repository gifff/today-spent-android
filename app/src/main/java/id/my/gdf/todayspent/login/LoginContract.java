package id.my.gdf.todayspent.login;

import id.my.gdf.todayspent.BasePresenter;
import id.my.gdf.todayspent.BaseView;

/**
 * Created by prime10 on 1/3/18.
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void showSpendingsScreen();

        void showToast(String message);

        void setLoadingIndicator(boolean active);

    }

    interface Presenter extends BasePresenter {

        void checkUserToken();

        void attemptLogin(String email, String password);

    }

}
