package project2.dbawale.pdx.edu.quizapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import project2.dbawale.pdx.edu.quizlibrary.Question;
import project2.dbawale.pdx.edu.quizlibrary.Quiz;

public class CheatActivity extends AppCompatActivity {
    private final String CURRENT_QUESTION_NUMBER="edu.pdx.dbawale.project2.questionnumber";
    private final String HAS_CHEATED = "edu.pdx.dbawale.project2.hascheated";
    private final String CHEAT_TEXT = "edu.pdx.dbawale.project2.cheattext";

    TextView cheattext;
    Button cheatbutton,backbutton;
    boolean hascheated=false;

    Quiz quiz;
    ArrayList<Question> questions;

    int currentquestionnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        quiz = new Quiz();
        quiz.setupDefaultQuiz();
        questions = quiz.getQuestions();
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            currentquestionnumber = extras.getInt(CURRENT_QUESTION_NUMBER);
        }
        cheattext = (TextView) findViewById(R.id.cheattext);
        cheatbutton = (Button) findViewById(R.id.cheatbuttoncheatactivity);
        backbutton = (Button) findViewById(R.id.backbutton);


        cheatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int correctanswerindex = Integer.parseInt(questions.get(currentquestionnumber).getCorrectanswer());
                cheattext.setText(questions.get(currentquestionnumber).getAnswers().getAnswers().get(correctanswerindex).second);
                cheatbutton.setEnabled(false);
                hascheated=true;
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(HAS_CHEATED,hascheated);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        handleInstanceState(savedInstanceState);
    }

    private void handleInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState!=null) {
            cheattext.setText(savedInstanceState.getString(CHEAT_TEXT));
            hascheated = savedInstanceState.getBoolean(HAS_CHEATED);
            if(hascheated){
                cheatbutton.setEnabled(false);
            }
        }
    }

    @Override
    public void onBackPressed(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra(HAS_CHEATED,hascheated);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle instanceState){
        instanceState.putString(CHEAT_TEXT,String.valueOf(cheattext.getText()));
        instanceState.putBoolean(HAS_CHEATED,hascheated);

    }
}