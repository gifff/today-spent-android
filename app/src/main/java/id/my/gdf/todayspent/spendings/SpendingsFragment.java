package id.my.gdf.todayspent.spendings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import id.my.gdf.todayspent.R;
import id.my.gdf.todayspent.addeditspending.AddEditSpendingActivity;
import id.my.gdf.todayspent.addeditspending.AddEditSpendingFragment;
import id.my.gdf.todayspent.data.Spending;
import id.my.gdf.todayspent.login.LoginActivity;
import id.my.gdf.todayspent.service.TodaySpentService;

/**
 * Spendings Fragment
 */
public class SpendingsFragment extends Fragment implements SpendingsContract.View, SpendingListItemTouchHelper.SpendingListItemTouchHelperListener{

    private TextView mAmountTextView;
    private RecyclerView mRecyclerViewSpendingList;
    private SpendingsAdapter mSpendingsAdapter;

    private TodaySpentService todaySpentService;
    private SharedPreferences sharedPref;

    private SpendingsContract.Presenter mPresenter;

    public SpendingsFragment() {
        // Required empty public constructor
    }

    public static SpendingsFragment newInstance() {
        return new SpendingsFragment();
    }

    @Override
    public void setPresenter(SpendingsContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_spendings, container, false);

        mRecyclerViewSpendingList = (RecyclerView)root.findViewById(R.id.rcv_spending_list);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewSpendingList.setLayoutManager(layoutManager);
        mRecyclerViewSpendingList.setHasFixedSize(true);

        mSpendingsAdapter = new SpendingsAdapter(this);

        mRecyclerViewSpendingList.setAdapter(mSpendingsAdapter);
        mRecyclerViewSpendingList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        ItemTouchHelper.SimpleCallback itemTouchHelperSimpleCallback = new SpendingListItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperSimpleCallback).attachToRecyclerView(mRecyclerViewSpendingList);

        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_add_spending);

        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.addSpending();
            }
        });

        mPresenter.loadSpendings();

        this.setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof SpendingsAdapter.SpendingListViewHolder) {
            long localId = mSpendingsAdapter.getSpendingLocalIdByPosition(viewHolder.getAdapterPosition());
            mPresenter.deleteSpending(localId);
            mSpendingsAdapter.removeItem(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_spending_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItemId = item.getItemId();

        if (selectedItemId == R.id.action_logout) {
            mPresenter.logout();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showSpendings(List<Spending> spendingList) {
        mSpendingsAdapter.setSpendingData(spendingList);
    }

    @Override
    public void showLogin() {

        this.startActivity(new Intent(this.getContext(), LoginActivity.class));
        this.getActivity().finish();

    }

    @Override
    public void showErrorToast(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAddSpending() {
        Intent intent = new Intent(getContext(), AddEditSpendingActivity.class);
        startActivityForResult(intent, AddEditSpendingActivity.REQUEST_ADD_SPENDING);

    }

    @Override
    public void showEditSpending(Spending spending) {
        Intent intent = new Intent(getContext(), AddEditSpendingActivity.class);
        intent.putExtra(AddEditSpendingFragment.ARGUMENT_EDIT_SPENDING_ID, spending.getLocalId());
        startActivityForResult(intent, 0);
    }

    @Override
    public void editSpending(long spendingLocalId) {
        mPresenter.editSpending(spendingLocalId);
    }
}
