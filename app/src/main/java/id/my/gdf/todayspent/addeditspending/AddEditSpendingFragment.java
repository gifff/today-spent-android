package id.my.gdf.todayspent.addeditspending;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import id.my.gdf.todayspent.R;

/**
 * Created by prime10 on 1/9/18.
 */

public class AddEditSpendingFragment extends Fragment implements AddEditSpendingContract.View {

    private final Calendar mCalendar = Calendar.getInstance();
    private AddEditSpendingContract.Presenter mPresenter;
    private EditText mAmountEditText;
    private EditText mDateEditText;
    private Button mSaveButton;

    public AddEditSpendingFragment() {
        // Required empty public constructor
    }

    public static AddEditSpendingFragment newInstance() {
        return new AddEditSpendingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_addeditspending, container, false);
        mAmountEditText = (EditText)root.findViewById(R.id.edt_spending_amount);
        mDateEditText = (EditText)root.findViewById(R.id.edt_spending_date);
        mSaveButton = (Button)root.findViewById(R.id.btn_save_spending);
        // TODO: Populate date when creation and both amount and date when updating
        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String format = "dd/MM/yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);

                mDateEditText.setText(simpleDateFormat.format(mCalendar.getTime()));
            }
        };

        mDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), dateSetListener, mCalendar.get(Calendar.YEAR),
                        mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mPresenter.saveSpending(mAmountEditText.getText().toString().trim(),
                        mCalendar.getTime());
            }
        });

        // setHasOptionMenu(true);
        return root;
    }

    @Nullable
    @Override
    public void setPresenter(AddEditSpendingContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showSpendingsList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void showEmptyDateError() {
        Toast.makeText(getContext(), "Date is empty", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyAmountError() {
        Toast.makeText(getContext(), "Amount is empty", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setAmount(String amount) {
        mAmountEditText.setText(amount);
    }

    @Override
    public void setDate(String date) {

    }
}
