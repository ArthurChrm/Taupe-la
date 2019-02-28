package iutdelaval.taupe_l.Donnees;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Score.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ScoreDAO scoreDAO();
}
