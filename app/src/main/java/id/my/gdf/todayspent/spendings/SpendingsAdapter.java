package id.my.gdf.todayspent.spendings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import id.my.gdf.todayspent.R;
import id.my.gdf.todayspent.data.Spending;

/**
 * Created by prime10 on 1/2/18.
 */

public class SpendingsAdapter extends RecyclerView.Adapter<SpendingsAdapter.SpendingListViewHolder>{

    private final SpendingsContract.View mSpendingsView;
    private List<Spending> mSpendingData;
    private java.text.DateFormat dateFormatter;

    public SpendingsAdapter(SpendingsContract.View mSpendingsView) {
        this.mSpendingsView = mSpendingsView;
    }

    public void setSpendingData(List<Spending> data) {
        this.mSpendingData = data;
        notifyDataSetChanged();
    }

    @Override
    public SpendingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");


        int layoutIdForListItem = R.layout.spending_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new SpendingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SpendingListViewHolder holder, final int position) {
        Date date = null;
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                mSpendingsView.editSpending(mSpendingData.get(holder.getAdapterPosition()).getLocalId());
            }
        });

        try {
            date = dateFormatter.parse(mSpendingData.get(position).getDate());
            SimpleDateFormat formatter = new SimpleDateFormat("d/MM/yyyy");
            holder.mSpendingDateTextView.setText("on " + formatter.format(date));
            holder.mSpendingAmountTextView.setText("Rp" + mSpendingData.get(position).getAmount());
        }catch (ParseException e) {
            Log.wtf("TODAYSPENT", "Error formatting");
        }

    }

    @Override
    public int getItemCount() {
        if (mSpendingData == null)
            return 0;
        return mSpendingData.size();
    }

    public long getSpendingLocalIdByPosition(int position) {
        return mSpendingData.get(position).getLocalId();
    }

    public void removeItem(int position) {
        mSpendingData.remove(position);
        notifyItemRemoved(position);
    }

    public static class SpendingListViewHolder extends RecyclerView.ViewHolder {

        public final TextView mSpendingDateTextView;
        public final TextView mSpendingAmountTextView;
        public final RelativeLayout mBackgroundLayout;
        public final LinearLayout mForegroundLayout;

        public SpendingListViewHolder(View itemView) {
            super(itemView);

            mSpendingAmountTextView = (TextView) itemView.findViewById(R.id.tv_spending_amount);
            mSpendingDateTextView = (TextView) itemView.findViewById(R.id.tv_spending_date);
            mBackgroundLayout = (RelativeLayout) itemView.findViewById(R.id.view_background);
            mForegroundLayout = (LinearLayout) itemView.findViewById(R.id.view_foreground);
        }
    }

}
