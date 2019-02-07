package iutdelaval.taupe_l;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.media.AudioManager;
import android.media.SoundPool;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class GameOverActivity extends AppCompatActivity implements SoundPool.OnLoadCompleteListener {

    private Button retourAccueil;
    private TextView affichageMeilleurScore;
    private GameOverActivity context;
    SoundPool soundPool;
    HashMap<String, Integer> mapDeSons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        context =this;

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
        mapDeSons = new HashMap<String, Integer>(1);
        mapDeSons.put("sonGO", soundPool.load(context, R.raw.game_over_sound, 1));
        soundPool.setOnLoadCompleteListener(this);
        retourAccueil = (Button) findViewById(R.id.boutonRetourAccueil);
        affichageMeilleurScore = (TextView) findViewById(R.id.affichageMeilleurScore);
        retourAccueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ouvertureAccueil();
            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.i("Difficulté", preferences.getString("Difficulte", "rien"));
        if (preferences.getString("Difficulte", "").equals("FACILE")) {
            affichageMeilleurScore.setText("Meilleur score pour la difficulté FACILE : " + preferences.getInt("ScoreFACILE", 0));
        }
        else if (preferences.getString("Difficulte", "").equals("NORMAL")) {
            affichageMeilleurScore.setText("Meilleur score pour la difficulté NORMALE : " + preferences.getInt("ScoreNORMAL", 0));
        }
        else if (preferences.getString("Difficulte", "").equals("DIFFICILE")) {
            affichageMeilleurScore.setText("Meilleur score pour la difficulté DIFFICILE : " + preferences.getInt("ScoreDIFFICILE", 0));
        }else{
            affichageMeilleurScore.setText("Un problème est survenu pendant le chargement du score.");
        }

        // Sert à modifier la couleur de la barre de notifications
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.rouge));

    }

    private void ouvertureAccueil() {
        Intent intentAccueil = new Intent(this, MainActivity.class);
        startActivity(intentAccueil);
    }

    @Override
    public void onBackPressed() {
        Intent accueil = new Intent(this, MainActivity.class);
        startActivity(accueil);
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int i, int i1) {
        float volume = 1;
        soundPool.play(mapDeSons.get("sonGO"), volume, volume, 1,0,1f);
    }
}
