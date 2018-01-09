package id.my.gdf.todayspent.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import id.my.gdf.todayspent.data.Spending;

/**
 * Created by prime10 on 1/4/18.
 */

@Database(entities = {Spending.class}, version = 1)
public abstract class TodaySpentDatabase extends RoomDatabase {

    private static final Object sLock = new Object();
    private static TodaySpentDatabase INSTANCE;

    public static TodaySpentDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        TodaySpentDatabase.class, "TodaySpent.db")
                        .build();
            }
            return INSTANCE;
        }
    }

    public abstract SpendingsDao spendingsDao();

}
