package id.my.gdf.todayspent.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;

/**
 * Created by prime10 on 1/4/18.
 */

@Entity(tableName = "spendings")
public final class Spending {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "local_id")
    private final long mLocalId;

    @Nullable
    @ColumnInfo(name = "remote_id")
    private final long mRemoteId;

    @NonNull
    @ColumnInfo(name = "amount")
    private final long mAmount;

    @NonNull
    @ColumnInfo(name = "date")
    private final String mDate;

    @Ignore
    public Spending(@NonNull long mLocalId, @NonNull long mAmount, @NonNull String mDate) {
        this(mLocalId, -1, mAmount, mDate);
    }

    public Spending(@NonNull long mLocalId, long mRemoteId, @NonNull long mAmount, @NonNull String mDate) {
        this.mLocalId = mLocalId;
        this.mRemoteId = mRemoteId;
        this.mAmount = mAmount;
        this.mDate = mDate;
    }

    @NonNull
    public long getLocalId() { return mLocalId; }

    @Nullable
    public long getRemoteId() { return mRemoteId; }

    @NonNull
    public long getAmount() { return mAmount; }

    public String getDate() { return mDate; }

    @Override
    public int hashCode() {
        return Objects.hashCode(mLocalId, mRemoteId, mAmount, mDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass())
            return  false;
        Spending spending = (Spending) obj;
        return Objects.equal(mLocalId, spending.mLocalId) &&
                Objects.equal(mRemoteId, spending.mRemoteId) &&
                Objects.equal(mDate, spending.mDate) &&
                Objects.equal(mAmount, spending.mAmount);
    }

    @Override
    public String toString() {
        return "Spending (LocalID: " + mLocalId + ")[RemoteID: " + mRemoteId + "]: Amount: " + mAmount + " @ " + mDate;
    }
}
