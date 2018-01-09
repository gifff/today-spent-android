package id.my.gdf.todayspent.spendings;

import java.util.List;

import id.my.gdf.todayspent.BasePresenter;
import id.my.gdf.todayspent.BaseView;
import id.my.gdf.todayspent.data.Spending;

/**
 * Created by prime10 on 1/3/18.
 */

public interface SpendingsContract {

    interface View extends BaseView<Presenter> {

        void showSpendings(List<Spending> spendingList);

        void showLogin();

        void showErrorToast(String message);

        // void setLoadingIndicator(boolean active);

    }

    interface Presenter extends BasePresenter {

        void loadSpendings();

        void logout();

    }

}
