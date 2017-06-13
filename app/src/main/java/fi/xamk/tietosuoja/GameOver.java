package fi.xamk.tietosuoja;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {


    private TextView tvScores;
    private TextView tvHeader;
    private TextView tvSyy;
    private Button btnShareFacebook;
    private TextView tvHighScore;
    private TextView tvRank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        //määritellään objektit
        tvScores = (TextView) findViewById(R.id.tvScores);
        tvHeader = (TextView) findViewById(R.id.tvHeader);
        tvSyy = (TextView) findViewById(R.id.tvSyy);
        btnShareFacebook = (Button) findViewById(R.id.btnShareFacebook);
        tvHighScore = (TextView) findViewById(R.id.tvHighScore);
        tvRank = (TextView) findViewById(R.id.tvRank);

        //tulostetaan edellisen sivun pisteet
        int scores = getIntent().getIntExtra("pisteet", 0);
        String syy = getIntent().getStringExtra("syy");
        int round = getIntent().getIntExtra("kierros",0);
        tvScores.setText("Pisteet:" + scores);
        tvSyy.setText(syy);

        //annetaan arvonimi
        giveRank(round);

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
            //btnShareFacebook.setClickable(false);
        }


    }

    public void giveRank (int howManyScores){

        if (howManyScores > 1){
            tvRank.setText("Olet: Tietosuojanoviisi");
        }
        if (howManyScores > 3){
            tvRank.setText("Olet: Tietosuoja-amatööri");
        }
        if (howManyScores > 5){
            tvRank.setText("Olet: Tietotsuojatietäjä");
        }
        if (howManyScores > 7){
            tvRank.setText("Olet: Tietosuojamestari");
        }
        if (howManyScores > 9){
            tvRank.setText("Olet: Tietosuojaguru");
        }
    }

    public void ContinuePushed(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    public void ShareButtonPushed(View view){

        Intent startNewActivity = new Intent(this, ShareScores.class); //valmistutaan avaamaan seuraava sivu
        startActivity(startNewActivity);//avataan seuraava sivu

    }
}
