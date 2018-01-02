package id.my.gdf.todayspent;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by prime10 on 12/3/17.
 */

public class SpendingDetailFragment extends Fragment {

    private TextView mSpendingAmount;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.spending_detail_fragment, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();

        mSpendingAmount = (TextView) getActivity().findViewById(R.id.tv_spending_detail_amount);

        mSpendingAmount.setText("Amount: " + getActivity().getIntent().getStringExtra("amount"));
    }


}
