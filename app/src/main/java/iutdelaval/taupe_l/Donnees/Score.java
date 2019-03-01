package iutdelaval.taupe_l.Donnees;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Score {

    @PrimaryKey
    @NonNull
    public String nomJoueur;

    @ColumnInfo(name = "score")
    public int point;

    @ColumnInfo(name = "difficulte")
    public String difficulte;

    public Score(String nomJoueur, int point, String difficulte) {
        this.nomJoueur = nomJoueur;
        this.point = point;
        this.difficulte = difficulte;
    }

}
