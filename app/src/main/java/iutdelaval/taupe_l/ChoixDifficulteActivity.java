package iutdelaval.taupe_l;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class ChoixDifficulteActivity extends AppCompatActivity {

    private Button btnFacile;
    private Button btnNormal;
    private Button btnDifficile;

    private ChoixDifficulteActivity context;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_difficulte);

        btnFacile = findViewById(R.id.btnFacile);
        btnNormal = findViewById(R.id.btnNormal);
        btnDifficile = findViewById(R.id.btnDifficile);
        context = this;

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(!preferences.contains("Difficulte")){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Difficulte", "NORMAL");
        }

        btnFacile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast t = Toast.makeText(context, "Difficulté : Facile", Toast.LENGTH_SHORT);
                t.show();

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Difficulte", "FACILE");
                editor.apply();

                ouvertureGameActivity();
            }
        });

        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast t = Toast.makeText(context, "Difficulté : Normal", Toast.LENGTH_SHORT);
                t.show();

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Difficulte", "NORMAL");
                editor.apply();

                ouvertureGameActivity();
            }
        });

        btnDifficile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast t = Toast.makeText(context, "Difficulté : Difficile", Toast.LENGTH_SHORT);
                t.show();

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Difficulte", "DIFFICILE");
                editor.apply();

                ouvertureGameActivity();
            }
        });

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.vert));
    }

    private void ouvertureGameActivity(){
        Intent jeu = new Intent(this, GameActivity.class);
        startActivity(jeu);
    }

    @Override
    public void onBackPressed(){
        Intent accueil = new Intent(this, MainActivity.class);
        startActivity(accueil);
    }
}
