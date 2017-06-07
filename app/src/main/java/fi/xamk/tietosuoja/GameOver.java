package fi.xamk.tietosuoja;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        int scores = getIntent().getIntExtra("pisteet", 0);

    }

    /*public void ContinuePushed(){
        Intent startNewActivity = new Intent(this, MainActivity.class);

        startActivity(startNewActivity);
    }*/
}
