package id.my.gdf.todayspent.addeditspending;

import java.util.Date;

import id.my.gdf.todayspent.BasePresenter;
import id.my.gdf.todayspent.BaseView;

/**
 * Created by prime10 on 1/9/18.
 */

public interface AddEditSpendingContract {

    interface View extends BaseView<Presenter> {

        void showEmptyDateError();

        void showEmptyAmountError();

        void setAmount(String amount);

        void setDate(String date);

        void showSpendingsList();

    }

    interface Presenter extends BasePresenter {

        void saveSpending(String amount, Date date);

        void populateTask();

    }
}
