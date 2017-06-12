package fi.xamk.tietosuoja;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {


    private TextView tvScores;
    private TextView tvHeader;
    private TextView tvSyy;
    private Button btnShareFacebook;
    private Button btnShareEmail;
    private TextView tvHighScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        //määritellään objektit
        tvScores = (TextView) findViewById(R.id.tvScores);
        tvHeader = (TextView) findViewById(R.id.tvHeader);
        tvSyy = (TextView) findViewById(R.id.tvSyy);
        btnShareFacebook = (Button) findViewById(R.id.btnShareFacebook);
        btnShareEmail = (Button) findViewById(R.id.btnShareEmail);
        tvHighScore = (TextView) findViewById(R.id.tvHighScore);

        //tulostetaan edellisen sivun pisteet
        int scores = getIntent().getIntExtra("pisteet", 0);
        String syy = getIntent().getStringExtra("syy");
        int round = getIntent().getIntExtra("kierros",0);
        tvScores.setText("Pisteet:" + scores);
        tvSyy.setText(syy);

        //ladataan High Scoret
        SharedPreferences sharedPref = getSharedPreferences("ScoredTable", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int loadedScores = sharedPref.getInt("HighScore", 0);
        tvHighScore.setText("Paras tulos:" + loadedScores);

        //jos saatiin hyvät pisteet, annetaan onnittelut!
        if (round > 9){
            tvHeader.setText("Onnittelut! Sait " + round + " okein!");
        }
        else {
            tvHeader.setText("HUPS! sait vain " + round + " oikein. :(");
            btnShareFacebook.setClickable(false);
            btnShareEmail.setClickable(false);
        }


    }

    public void ContinuePushed(){
        //TODO tähän pitää tehdä pelin uudelleen aloitus
    }
}
