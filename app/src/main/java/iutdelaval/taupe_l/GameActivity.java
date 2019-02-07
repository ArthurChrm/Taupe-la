package iutdelaval.taupe_l;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private List<Trou> listeTrou = new ArrayList<Trou>();
    private CountDownTimer timer;
    private double delais;
    private boolean startGame; // Va servir à remettre à 0 le score quand une nouvelle partie démarre
    private SharedPreferences preferences;
    private int score;
    private ImageView imageCoeur;
    private boolean imageSet = false;

    private Boolean pause;

    private TextView scoreJeu;
    static ProgressBar barreVie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        scoreJeu = (TextView) findViewById(R.id.scoreJeu);
        scoreJeu.setText("0");
        startGame = true;
        pause = false;
        barreVie = (ProgressBar) findViewById(R.id.barreVie);
        imageCoeur = findViewById(R.id.imageCoeur);

        barreVie.setProgress(100);
        delais = 2500;
        resetCoeur();

        // On va initialiser tout les trous
        // Pour ce faire on va parcourir l'élément root (le linearLayout contenant tout les autres).
        // Dés que l'on tombera sur un élément "LinearLayout) on le parcourera et on ajoutera tout les ImageButton à listeTrou
        LinearLayout rootLayout = (LinearLayout) findViewById(R.id.rootLayout);
        LinearLayout childLayout;
        for (int i = 0; i < rootLayout.getChildCount(); i++) {
            if (rootLayout.getChildAt(i) instanceof LinearLayout) {
                childLayout = (LinearLayout) rootLayout.getChildAt(i);
                for (int j = 0; j < childLayout.getChildCount(); j++) {
                    if (childLayout.getChildAt(j) instanceof ImageButton) {
                        listeTrou.add(new Trou((ImageButton) childLayout.getChildAt(j)));
                    }
                }
            }
        }

        //On va gérer la fréquence d'apparition des taupes en fonction du mode de jeu
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!preferences.contains("ScoreFACILE")) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("ScoreFACILE", 0);
            delais = 3500;
            editor.apply();
        }
        if (!preferences.contains("ScoreNORMAL")) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("ScoreNORMAL", 0);
            delais = 3250;
            editor.apply();
        }
        if (!preferences.contains("ScoreDIFFICILE")) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("ScoreDIFFICILE", 0);
            delais = 3000;
            editor.apply();
        }

        // Va servir à changer la couleur de la barre de notification
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blanc_fond));

        imageCoeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicCoeur();
            }
        });
        faireApparaitreEntite();
    }

    private void faireApparaitreEntite() {
        // On va générer un nombre aléatoire entre 0 inclus et 8 exclus (donc 8 nombre au total)
        Random r = new Random();
        int nombreAleat = r.nextInt(8);

        // Un chance sur 8 de faire apparaitre une bombe
        if (nombreAleat == 0) {
            faireApparaitreBombe();
        } else {
            faireApparaitreTaupe();
        }
    }

    private void faireApparaitreTaupe() {
        //On va séléctionner un trou au hasard et y ajouter une taupe
        final int nombreAleat = (int) (Math.random() * 9);
        listeTrou.get(nombreAleat).ajouterTaupe();
        if (startGame) {
            listeTrou.get(nombreAleat).resetScore();
            scoreJeu.setText("0");
            startGame = false;
        }

        //La taupe est maintenant ajoutée, on va faire en sorte qu'elle disparaisse dans 1 secondes
        timer = new CountDownTimer((int) delais, 1) {
            @Override
            public void onTick(long l) {
                scoreJeu.setText(String.valueOf(listeTrou.get(nombreAleat).getScore()));
                score = listeTrou.get(nombreAleat).getScore();
                if (!listeTrou.get(nombreAleat).isSometingHere()) {
                    stopTimer(this);
                }
                gestionAffichageBonus();
            }

            public void onFinish() {
                listeTrou.get(nombreAleat).supprimerElement();
                barreVie.setProgress(barreVie.getProgress() - 10);
                if (barreVie.getProgress() <= 0) {
                    this.cancel();
                    gameOver();
                    return;
                }
                faireApparaitreEntite();

            }
        }.start();
    }

    private void faireApparaitreBombe() {
        //On va séléctionner un trou au hasard et y ajouter une taupe
        final int nombreAleat = (int) (Math.random() * 9);
        listeTrou.get(nombreAleat).ajouterBombe();
        if (startGame) {
            listeTrou.get(nombreAleat).resetScore();
            scoreJeu.setText("0");
            startGame = false;
        }

        //La bombe est maintenant ajoutée, on va faire en sorte qu'elle disparaisse dans le délais imparti
        timer = new CountDownTimer((int) delais, 1) {
            @Override
            public void onTick(long l) {
                scoreJeu.setText(String.valueOf(listeTrou.get(nombreAleat).getScore()));
                score = listeTrou.get(nombreAleat).getScore();
                if (!listeTrou.get(nombreAleat).isSometingHere()) {
                    stopTimer(this);
                }
                if (barreVie.getProgress() <= 0) {
                    this.cancel();
                    gameOver();
                    return;
                }
                gestionAffichageBonus();
            }

            public void onFinish() {
                listeTrou.get(nombreAleat).supprimerElement();
                if (barreVie.getProgress() <= 0) {
                    this.cancel();
                    gameOver();
                    return;
                }
                faireApparaitreEntite();
            }
        }.start();
    }

    private void gameOver() {
        finish();
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.getString("Difficulte", "NORMAL") == "FACILE") {
            if (preferences.getInt("ScoreFACILE", 0) < score) {
                editor.putInt("ScoreFACILE", score);
                editor.apply();
            }
        }
        if (preferences.getString("Difficulte", "NORMAL") == "NORMAL") {
            if (preferences.getInt("ScoreNORMAL", 0) < score) {
                editor.putInt("ScoreNORMAL", score);
                editor.apply();
            }
        }
        if (preferences.getString("Difficulte", "NORMAL") == "DIFFICILE") {
            if (preferences.getInt("ScoreDIFFICILE", 0) < score) {
                editor.putInt("ScoreDIFFICILE", score);
                editor.apply();
            }
        }

        Intent intentGameOver = new Intent(this, GameOverActivity.class);
        startActivity(intentGameOver);
    }

    private void stopTimer(CountDownTimer timer) {
        timer.cancel();
        if (preferences.getString("Difficulte", "").equals("FACILE")) {
            delais = delais * 0.98;
        } else if (preferences.getString("Difficulte", "").equals("NORMAL")) {
            delais = delais * 0.95;
        } else if (preferences.getString("Difficulte", "").equals("DIFFICILE")) {
            delais = delais * 0.92;
        } else {
            Log.e("ERREUR_PREF_DELAIS", "Aucune préféreces détéctée");
            delais = delais * 0.95;
        }

        faireApparaitreEntite();
    }

    private void gestionAffichageBonus() {
        if (Trou.getTaupemorte() >= 10 && imageSet == false) {
            imageCoeur.setImageResource(R.drawable.heart_plus);
            imageSet = true;
        }
    }

    private void clicCoeur() {
        if (Trou.getTaupemorte() >= 10) {
            barreVie.setProgress(100);
            resetCoeur();
            imageSet = false;
        }
    }

    private void resetCoeur() {
        Trou.resetTaupeMorte();
        imageCoeur.setImageResource(R.drawable.heart_plus_desactive);
    }

    @Override
    public void onPause() {
        super.onPause();
        timer.cancel();
        pause = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (pause) {
            Intent accueil = new Intent(this, MainActivity.class);
            startActivity(accueil);
        }
    }
}
