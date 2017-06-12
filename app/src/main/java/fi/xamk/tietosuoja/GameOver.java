package fi.xamk.tietosuoja;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {


    private TextView tvScores;
    private TextView tvSyy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        //määritellään objektit
        tvScores = (TextView) findViewById(R.id.tvScores);
        tvSyy = (TextView) findViewById(R.id.tvSyy);

        //tulostetaan edellisen sivun pisteet
        int scores = getIntent().getIntExtra("pisteet", 0);
        String syy = getIntent().getStringExtra("syy");
        tvScores.setText("Pisteet:" + scores);
        tvSyy.setText(syy);


    }

    public void ContinuePushed(){

    }
}
