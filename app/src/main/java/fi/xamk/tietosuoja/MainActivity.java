package fi.xamk.tietosuoja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void startNewGameButtonPushed(View view) {

        Intent startNewActivity = new Intent(this, QuestionActivity.class);
        startActivity(startNewActivity);
    }
}
