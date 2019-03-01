package iutdelaval.taupe_l.Donnees;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ScoreDAO {
    @Query("SELECT * FROM score")
    List<Score> getAll();

    @Query("SELECT * FROM score WHERE nomJoueur = (:nomJoueur) AND difficulte = (:difficulte)")
    List<Score> getScoresByName(String nomJoueur, String difficulte);

    @Query("SELECT * FROM score WHERE nomJoueur LIKE :nomJoueur AND difficulte LIKE :difficulte")
    List<Score> findByName(String nomJoueur, String difficulte);

    @Update
    void update(Score score);

    @Insert
    void insertAll(Score... scores);

    @Delete
    void delete(Score score);

}
