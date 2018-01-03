package id.my.gdf.todayspent.spendings;

import id.my.gdf.todayspent.BasePresenter;
import id.my.gdf.todayspent.BaseView;
import id.my.gdf.todayspent.model.SpendingList;

/**
 * Created by prime10 on 1/3/18.
 */

public interface SpendingsContract {

    interface View extends BaseView<Presenter> {

        void showSpendings(SpendingList spendingList);

        void showLogin();

        void showErrorToast(String message);

        // void setLoadingIndicator(boolean active);

    }

    interface Presenter extends BasePresenter {

        void loadSpendings();

        void logout();

    }

}
