package id.my.gdf.todayspent.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import id.my.gdf.todayspent.data.Spending;

/**
 * Created by prime10 on 1/4/18.
 */

@Dao
public interface SpendingsDao {

    @Query("SELECT * FROM spendings")
    List<Spending> getSpendings();

    @Query("SELECT * FROM spendings WHERE local_id = :localId")
    Spending getSpendingByLocalId(long localId);

    @Query("SELECT * FROM spendings WHERE remote_id = :remoteId")
    Spending getSpendingByRemoteId(long remoteId);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insertSpending(Spending spending);

    @Update
    int updateSpending(Spending spending);

    @Query("UPDATE spendings SET amount = :amount WHERE local_id = :localId")
    void updateAmount(long localId, long amount);

    @Query("UPDATE spendings SET date = :date WHERE local_id = :localId")
    void updateDate(long localId, String date);

    @Query("DELETE FROM spendings WHERE local_id = :localId")
    int deleteSpendingByLocalId(long localId);

    @Query("DELETE FROM spendings")
    void deleteSpendings();
}
