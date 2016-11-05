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

    //Constant strings for identifying data passed around in a bundle or intent
    private final String CURRENT_QUESTION_NUMBER="edu.pdx.dbawale.project2.questionnumber";
    private final String HAS_CHEATED = "edu.pdx.dbawale.project2.hascheated";
    private final String CHEAT_TEXT = "edu.pdx.dbawale.project2.cheattext";

    //Android widgets required for implementing cheat activity
    TextView cheattext;
    Button cheatbutton,backbutton;

    boolean hascheated=false;

    //Quiz API-specific data structures
    Quiz quiz;
    ArrayList<Question> questions;
    int currentquestionnumber;

    /**
     * The onCreate method for CheatActivity.
     * Sets up the listeners for buttons and inflates the layout
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Call super.onCreate and inflate the view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        //Set up the Android widgets on this activity
        cheattext = (TextView) findViewById(R.id.cheattext);
        cheatbutton = (Button) findViewById(R.id.cheatbuttoncheatactivity);
        backbutton = (Button) findViewById(R.id.backbutton);

        //Set up a new quiz, because it is unnecessary to pass the whole question in the bundle
        quiz = new Quiz();
        quiz.setupDefaultQuiz();
        questions = quiz.getQuestions();

        //Read the data that was passed from the main activity
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            currentquestionnumber = extras.getInt(CURRENT_QUESTION_NUMBER);
            hascheated = extras.getBoolean(HAS_CHEATED);
        }

        //If the user has already cheated on this question, don't let him cheat again
        //but display the answer by default
        if(hascheated){
            setCheatedView();
        }

        //When the user clicks 'Cheat', show the answer
        cheatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCheatedView();
                hascheated=true;
            }
        });

        //When the user clicks 'Back', send back whether the user has cheated or not
        //and end this activity
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(HAS_CHEATED,hascheated);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        //Finally, call handleInstanceState to handle device rotation
        handleInstanceState(savedInstanceState);
    }

    /**
     * Sets the view to show the user if he has cheated
     */
    private void setCheatedView() {
        int correctanswerindex = Integer.parseInt(questions.get(currentquestionnumber).getCorrectanswer());
        cheattext.setText(questions.get(currentquestionnumber).getAnswers().getAnswers().get(correctanswerindex).second);
        cheatbutton.setEnabled(false);
    }

    /**
     * Restores the instance state, if previously saved
     *
     * @param savedInstanceState The previously saved instance state
     */
    private void handleInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState!=null) {
            cheattext.setText(savedInstanceState.getString(CHEAT_TEXT));
            hascheated = savedInstanceState.getBoolean(HAS_CHEATED);
            if(hascheated){
                cheatbutton.setEnabled(false);
            }
        }
    }

    /**
     * Fired when the user presses the Back button on Android phone
     */
    @Override
    public void onBackPressed(){
        //Do the same thing regardless of whether the user presses the back button on the
        //layout or on the phone
        Intent returnIntent = new Intent();
        returnIntent.putExtra(HAS_CHEATED,hascheated);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    /**
     * Fired when an instance state is being saved
     *
     * @param instanceState The instance state to save data into
     */
    @Override
    public void onSaveInstanceState(Bundle instanceState){
        instanceState.putString(CHEAT_TEXT,String.valueOf(cheattext.getText()));
        instanceState.putBoolean(HAS_CHEATED,hascheated);

    }
}