package iutdelaval.taupe_l.Donnees;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Score {
    @PrimaryKey
    public String nomJoueur;

    @ColumnInfo(name = "score")
    public int score;

    @ColumnInfo(name = "difficulte")
    public String difficulte;
}
