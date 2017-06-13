package fi.xamk.tietosuoja;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView tvTopic;
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
    private String correctArg;
    private String incorrectArg;
    private String topic;
    private boolean stopClock = true;
    private int round = 0;
    //pisteet
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private RadioButton rb5;
    private RadioButton rb6;
    private RadioButton rb7;
    private RadioButton rb8;
    private RadioButton rb9;
    private RadioButton rb10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //näytön objektien määritykset
        tvScores = (TextView) findViewById(R.id.tvScores);
        tvData = (TextView) findViewById(R.id.tvQuestion);
        tvTopic = (TextView) findViewById(R.id.tvTopic);
        button1 = (Button) findViewById(R.id.btnAnswer1);
        button2 = (Button) findViewById(R.id.btnAnswer2);
        button3 = (Button) findViewById(R.id.btnAnswer3);
        button4 = (Button) findViewById(R.id.btnAnswer4);
        pbTime = (ProgressBar) findViewById(R.id.pbAika);
        //pisteobjektit
        rb1 = (RadioButton) findViewById(R.id.rb1) ;
        rb2 = (RadioButton) findViewById(R.id.rb2) ;
        rb3 = (RadioButton) findViewById(R.id.rb3) ;
        rb4 = (RadioButton) findViewById(R.id.rb4) ;
        rb5 = (RadioButton) findViewById(R.id.rb5) ;
        rb6 = (RadioButton) findViewById(R.id.rb6) ;
        rb7 = (RadioButton) findViewById(R.id.rb7) ;
        rb8 = (RadioButton) findViewById(R.id.rb8) ;
        rb9 = (RadioButton) findViewById(R.id.rb9) ;
        rb10 = (RadioButton) findViewById(R.id.rb10) ;


        //alustetaan aika 100 prosenttiin
        aika = 100;
        pbTime.setProgress(aika);
        //alustetaan pelikierros 0
        round = 0;

        //ladataan näytölle ensimmäinen kysymys, että peli pääsee alkamaan
        new JSONTask().execute("http://theordermusic.xyz/JSON/testaus.json");

        //vähennetään aikaa tietyin väliajoin
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (stopClock) {
                        Thread.sleep(600);//näin monta millisekunttia menee yhden prosentin vähentämiseen
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
        scores += (aika*6);//jos vastaus on oikea, lisätään piste ja ladataan uusi kysymys (6 pistettä / prosentti --> 10 pistettä / sekunti)
        new JSONTask().execute("http://theordermusic.xyz/JSON/testaus.json");//haetaan seuraava kysymys
        tvScores.setText(String.valueOf(scores));//päivitetään pistenäyttö
        aika = 100;//säädetään aika takaisin 100 prosenttiin
        pbTime.setProgress(aika);//ja päivitetään se näyttöön
        round++;//lisätään pelikierrokseen 1
        scoresManager(round);
        if (round > 9){
            wrongAnswer();//jos kaikki 10 kysymystä oikein, niin siirrytään pistesivulle
        }
    }

    public void wrongAnswer(){
        stopClock = false; //pysäytetään kello!!!
        saveHighScore(scores); //tallennetaan high pisteet
        Intent startNewActivity = new Intent(this, GameOver.class); //valmistutaan avaamaan seuraava sivu
        startNewActivity.putExtra("pisteet", scores); //siirretään pistearvo seuraavalle sivulle
        startNewActivity.putExtra("syy", incorrectArg);//siirretään syy väärään vastaukseen seuraavalle sivulle
        startNewActivity.putExtra("kierros", round); //siirretään tieto, monesko kierros, seuraavalle sivulle
        startActivity(startNewActivity);//avataan seuraava sivu

    }

    public void scoresManager(int amount){
        //säädetään pisteet näyttöön oikein
        if (amount > 0){
            rb1.toggle();
        }
        if (amount > 1){
            rb2.toggle();
        }
        if (amount > 2){
            rb3.toggle();
        }
        if (amount > 3){
            rb4.toggle();
        }
        if (amount > 4){
            rb5.toggle();
        }
        if (amount > 5){
            rb6.toggle();
        }
        if (amount > 6){
            rb7.toggle();
        }
        if (amount > 7){
            rb8.toggle();
        }
        if (amount > 8){
            rb9.toggle();
        }
        if (amount > 9){
            rb10.toggle();
        }



    }

    public void saveHighScore(int data){

        SharedPreferences sharedPref = getSharedPreferences("ScoredTable", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int loadedScores = sharedPref.getInt("HighScore", 0);
        if (data > loadedScores) {
            editor.putInt("HighScore", data);
            editor.apply();
            Toast.makeText(this, "Ennätyspisteet tallennettu!", Toast.LENGTH_LONG).show();
        }

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
                    questionModel.setIncorrectArg(finalObject.getString("IncorrectArg"));
                    questionModel.setCorrectArg(finalObject.getString("CorrectArg"));
                    questionModel.setTopic(finalObject.getString("Topic"));
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
            correctArg = allQuestions.get(randomNumber).getCorrectArg();
            incorrectArg = allQuestions.get(randomNumber).getIncorrectArg();
            tvTopic.setText(allQuestions.get(randomNumber).getTopic());

        }
    }
}
