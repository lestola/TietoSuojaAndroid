package fi.xamk.tietosuoja;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fi.xamk.tietosuoja.models.QuestionModel;


public class QuestionActivity extends AppCompatActivity {

    private TextView tvData;
    private TextView tvScores;
    private ProgressBar pbTime;
    private int scores = 0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private int correctAnswer;
    private List<QuestionModel> allQuestions = new ArrayList<>();
    private int aika;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //näytön objektien määritykset
        tvScores = (TextView) findViewById(R.id.tvScores);
        tvData = (TextView) findViewById(R.id.tvQuestion);
        button1 = (Button) findViewById(R.id.btnAnswer1);
        button2 = (Button) findViewById(R.id.btnAnswer2);
        button3 = (Button) findViewById(R.id.btnAnswer3);
        button4 = (Button) findViewById(R.id.btnAnswer4);
        pbTime = (ProgressBar) findViewById(R.id.pbAika);

        //alustetaan aika 100 sekunttiin
        aika = 100;
        pbTime.setProgress(aika);

        //ladataan näytölle ensimmäinen kysymys, että peli pääsee alkamaan
        new JSONTask().execute("http://theordermusic.xyz/JSON/testaus.json");

        //vähennetään aikaa joka sekunti
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(200);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                aika --;
                                pbTime.setProgress(aika);
                                if (aika < 0) {
                                    wrongAnswer();
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();


    }

    public void rightAnswer(){
        //jos vastaus on oikea, lisätään piste ja ladataan uusi kysymys
        scores++;
        new JSONTask().execute("http://theordermusic.xyz/JSON/testaus.json");
        //päivitetään piste taulukko
        tvScores.setText(String.valueOf(scores));
        //säädetään aika takaisin 100 sekunttiin
        aika = 100;
        pbTime.setProgress(aika);
    }

    public void wrongAnswer(){
        aika = 20000;
        Intent startNewActivity = new Intent(this, GameOver.class);
        startNewActivity.putExtra("pisteet", scores);
        startActivity(startNewActivity);


    }

    public void button1Pushed(View view){
        //jos painetaan nappulaa 1
        if (correctAnswer == 1){
            rightAnswer();
        }
        else{
            wrongAnswer();
        }
    }
    public void button2Pushed(View view){
        //jos painetaan nappulaa 2
        if (correctAnswer == 2){
            rightAnswer();
        }
        else{
            wrongAnswer();
        }
    }
    public void button3Pushed(View view){
        //jos painetaan nappulaa 3
        if (correctAnswer == 3){
            rightAnswer();
        }
        else{
            wrongAnswer();
        }
    }
    public void button4Pushed(View view){
        //jos painetaan nappulaa 4
        if (correctAnswer == 4){
            rightAnswer();
        }
        else{
            wrongAnswer();
        }
    }

    private class JSONTask extends AsyncTask<String, String, List<QuestionModel> > {
        //ladataan verkosta JSON taulukko ja tehdään siitä array lista
        @Override
        protected List<QuestionModel> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();


                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("Tietosuoja");

                List<QuestionModel> questionModelList = new ArrayList<>();


                for(int i=0; i<parentArray.length(); i++) {

                    JSONObject finalObject = parentArray.getJSONObject(i);
                    QuestionModel questionModel = new QuestionModel();
                    questionModel.setQuestion(finalObject.getString("Question"));
                    questionModel.setAnswer1(finalObject.getString("Answer1"));
                    questionModel.setAnswer2(finalObject.getString("Answer2"));
                    questionModel.setAnswer3(finalObject.getString("Answer3"));
                    questionModel.setAnswer4(finalObject.getString("Answer4"));
                    questionModel.setCorrectAnswer(finalObject.getInt("CorrectAnswer"));
                    questionModelList.add(questionModel);


                }

                return questionModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<QuestionModel> result) {
            super.onPostExecute(result);
            //ladataan objekti listasta random kysymys näytölle
            allQuestions = result;
            Random rand = new Random();
            int randomNumber = rand.nextInt(allQuestions.size());
            tvData.setText(allQuestions.get(randomNumber).getQuestion());
            button1.setText(allQuestions.get(randomNumber).getAnswer1());
            button2.setText(allQuestions.get(randomNumber).getAnswer2());
            button3.setText(allQuestions.get(randomNumber).getAnswer3());
            button4.setText(allQuestions.get(randomNumber).getAnswer4());
            correctAnswer = allQuestions.get(randomNumber).getCorrectAnswer();
        }
    }
}