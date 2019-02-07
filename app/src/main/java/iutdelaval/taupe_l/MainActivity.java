package iutdelaval.taupe_l;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button boutonJeu;
    private Button boutonAPropos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //On va initialiser bouteuJeu avec le bouton de l'interface correspondant
        boutonJeu = findViewById(R.id.boutonJeu);
        boutonAPropos = findViewById(R.id.btnAPropos);
        //Ob va associer une méthode qui s'éxécutera quand on cliquera sur le bouton "Jouer"
        boutonJeu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ouvertureIntentSelectionDifficulte(view);
            }
        });
        boutonAPropos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ouvertureAPropos();
            }
        });

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.vert));
    }

    private void ouvertureAPropos() {
        //On va afficher l'activity "A propos"
        Intent aPropos = new Intent(this, AProposActivity.class);
        startActivity(aPropos);
    }

    private void ouvertureIntentSelectionDifficulte(View mainActivityView) {
        //On va afficher l'activity du choix de la difficulté
        Intent selectDifficulte = new Intent(this, ChoixDifficulteActivity.class);
        startActivity(selectDifficulte);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
