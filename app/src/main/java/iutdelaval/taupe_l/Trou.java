package iutdelaval.taupe_l;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import java.util.Random;

public class Trou {

    private ImageButton trou;
    private boolean taupe;
    private boolean bombe;
    private static int score = 0;
    private static int taupemorte = 0;

    public Trou(ImageButton trou) {
        this.trou = trou;
        //A la création du trou la taupe ne peut pas être déjà là
        taupe = false;
        bombe = false;

        this.trou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action();
            }
        });
    }

    private void action() {
        CountDownTimer timer;
        // Si il y a quelque chose
        if (isSometingHere()) {
            //Si il y a une taupe
            if (taupe) {
                taupemorte++;
                this.trou.setBackgroundResource(R.color.vert);
                // Va servir à afficher une couleur sur le fond de l'image en question pendant 75 ms
                timer = new CountDownTimer(75, 10) {
                    @Override
                    public void onTick(long l) {}

                    @Override
                    public void onFinish() {
                        trou.setBackground(null);
                    }

                }.start();
                this.supprimerElement();
                score++;
            } else { //Si il y a quelque chose mais pas une taupe (= une bombe)
                GameActivity.barreVie.setProgress(GameActivity.barreVie.getProgress() - 10);
                this.trou.setBackgroundResource(R.color.rouge);
                // Va servir à afficher une couleur sur le fond de l'image en question
                timer = new CountDownTimer(75, 10) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        trou.setBackground(null);
                    }

                }.start();
                this.supprimerElement();
            }
        } else { // Si il n'y a rien
            GameActivity.barreVie.setProgress(GameActivity.barreVie.getProgress() - 10);
            this.trou.setBackgroundResource(R.color.rouge);
            // Va servir à mettre un fond roue sur le bouton pendant 75 ms
            timer = new CountDownTimer(75, 10) {
                @Override
                public void onTick(long l) {
                }

                @Override
                public void onFinish() {
                    trou.setBackground(null);
                }
            }.start();
        }
    }

    public boolean isSometingHere() {
        if (taupe || bombe) {
            return true;
        }
        return false;
    }

    public void ajouterTaupe() {
        if (!isSometingHere()) {
            this.trou.setImageResource(R.drawable.holewithmole);
            bombe = false;
            taupe = true;
        }
    }

    public void ajouterBombe() {
        if (!isSometingHere()) {
            this.trou.setImageResource(R.drawable.holewithbomb);
            taupe = false;
            bombe = true;
        }
    }

    public void supprimerElement() {

        bombe = false;
        taupe = false;
        this.trou.setImageResource(R.drawable.hole);
    }

    public ImageButton getImageButton() {
        return trou;
    }

    public int getScore() {
        return score;
    }

    public static int getTaupemorte() {
        return taupemorte;
    }

    public static void resetTaupeMorte() {
        taupemorte = 0;
    }

    public void resetScore() {
        this.score = 0;
    }
}
