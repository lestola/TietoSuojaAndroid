package fi.xamk.tietosuoja;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ShareScores extends AppCompatActivity {

    private TextView tvScores;
    private TextView tvRound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_scores);
        tvScores = (TextView) findViewById(R.id.tvScores);
        tvRound = (TextView) findViewById(R.id.tvRound);

        SharedPreferences sharedPref = getSharedPreferences("ScoredTable", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int loadedScores = sharedPref.getInt("HighScore", 0);
        int loadedRound = sharedPref.getInt("HighRound", 0);
        tvScores.setText(" "+loadedScores);
        tvRound.setText(" " + loadedRound + "/10");

    }

    public void BackToMainMenuPushed(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);

    }
}
