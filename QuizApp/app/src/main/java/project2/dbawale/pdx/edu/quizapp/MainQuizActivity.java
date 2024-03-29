package project2.dbawale.pdx.edu.quizapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Import statement for the quiz library that encapsulates Quiz.
 * This was copied over from Project 1 with minor changes
 */
import project2.dbawale.pdx.edu.quizlibrary.*;

/**
 * Class for the main quiz app activity
 */
public class MainQuizActivity extends Activity {
    //String for log tag for main activity
    private final String LOG_TAG = "MAIN_ACTIVITY";

    //Constant strings for identifying data passed around in a bundle or intent
    private final String QUESTION_NUMBER = "edu.pdx.dbawale.project2.questionnumber";
    private final String QUESTION_TEXT_VIEW = "edu.pdx.dbawale.project2.questiontextview";
    private final String RADIO_BTN_INDEX = "edu.pdx.dbawale.project2.radiobuttonindex";
    private final String IS_QUESTION_CORRECT = "edu.pdx.dbawale.project2.iscorrectboolean";
    private final String GAME_SCORE = "edu.pdx.dbawale.project2.score";
    private final String IS_GAME_OVER = "edu.pdx.dbawale.project2.isgameover";
    private final String HAS_CHEATED = "edu.pdx.dbawale.project2.hascheated";
    private final String SCORE_TEXT = "edu.pdx.dbawale.project2.scoretext";

    //Request code for cheat activity
    private final int CHEAT_ACTIVITY_REQUEST =1;

    //Android widgets for displaying questions and controlling the quiz
    TextView questionTextView,currentScoreTextView;
    RadioGroup answergroup;
    Button nextbutton,playagainbutton,cheatbutton;

    //Quiz specific data structures, imported from Project 1
    ArrayList<Question> questions;
    int numberofquestions, currentquestionnumber = 0;
    Quiz quiz;

    //Other variables required for app functionality
    Boolean isCorrect = false;
    int score = 0;
    Boolean isGameOver = false;
    boolean hasCheated=false;

    /**
     * The onCreate method for the main activity
     * Sets up the listeners for buttons and inflates the layout
     *
     * @param savedInstanceState The instance state that may be previously saved
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Call super.onCreate and inflate the view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_quiz);

        //Set up the default quiz, the same as Project1
        quiz = new Quiz();
        quiz.setupDefaultQuiz();
        questions = quiz.getQuestions();
        numberofquestions = questions.size();

        //Set the required widgets for the UI
        nextbutton = (Button) findViewById(R.id.button1);
        playagainbutton = (Button) findViewById(R.id.playagain);
        questionTextView = (TextView) findViewById(R.id.textview1);
        questionTextView.setText(questions.get(currentquestionnumber).getQuestion());
        cheatbutton = (Button) findViewById(R.id.cheatbutton);
        answergroup = (RadioGroup) findViewById(R.id.default_radio_group);
        currentScoreTextView = (TextView) findViewById(R.id.livescore);

        //Dynamically add radio buttons to the layout, depending on the number of options
        //in the question
        int numberofradiobtns = questions.get(currentquestionnumber).getAnswers().getAnswers().size();
        drawRadioButtons(numberofradiobtns, answergroup);

        questionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextQuestion();
            }
        });

        //Anonymous onClickListener for the 'Next' button
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextQuestion();

            }
        });

        //Anonymous onClickListener for 'Play Again' button
        playagainbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Restart the game when Play again is clicked
                playagainbutton.setVisibility(View.INVISIBLE);
                cheatbutton.setVisibility(View.VISIBLE);
                currentquestionnumber=0;
                int numberofradiobtns = questions.get(currentquestionnumber).getAnswers().getAnswers().size();
                drawRadioButtons(numberofradiobtns, answergroup);
                questionTextView = (TextView) findViewById(R.id.textview1);
                questionTextView.setText(questions.get(currentquestionnumber).getQuestion());
                nextbutton.setVisibility(View.VISIBLE);
                score=0;
                isGameOver = false;
                currentScoreTextView.setText(R.string.defaultscorestring);
            }
        });

        //Anonymous onCheckedChangeListener for the RadioGroup
        answergroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Set whether the answer is correct, but don't show it yet
                isCorrect = questions.get(currentquestionnumber).getCorrectanswer().equals(String.valueOf(checkedId));
            }
        });

        //Anonymous onClickListener for Cheat button
        cheatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start a new activity, and pass whether the current question number
                //and whether the user has already cheated on this question
                Intent intent = new Intent();
                intent.setClass(MainQuizActivity.this,CheatActivity.class);
                intent.putExtra(QUESTION_NUMBER,currentquestionnumber);
                intent.putExtra(HAS_CHEATED,hasCheated);
                startActivityForResult(intent,CHEAT_ACTIVITY_REQUEST);
            }
        });

        //Finally, call handleInstanceState, to handle device rotation
        handleInstanceState(savedInstanceState);
    }

    private void showNextQuestion() {
        //Display a toast that tells whether the previous answer was correct or not
        if (isCorrect) {
            Toast.makeText(MainQuizActivity.this, R.string.correctanswer, Toast.LENGTH_SHORT).show();
            if(!hasCheated){
                score += 1;
                currentScoreTextView.setText("Your score is: " + score + " /" + numberofquestions);
            }
            isCorrect=false;
        } else if(!isGameOver){
            Toast.makeText(MainQuizActivity.this, R.string.incorrectanswer, Toast.LENGTH_SHORT).show();
        }

        //For each new question, give the user an opportunity to think about conscience
        hasCheated=false;

        //Clear the layout
        answergroup.removeAllViews();

        //Then, load the layout again
        currentquestionnumber += 1;
        if (currentquestionnumber < numberofquestions) {
            addViewToLayout();
        } else {
            isGameOver = true;
            String final_string = "Game over!\nFinal score is: " + score + "/" + numberofquestions;
            questionTextView.setText(final_string);
            nextbutton.setVisibility(View.GONE);
            playagainbutton.setVisibility(View.VISIBLE);
            cheatbutton.setVisibility(View.INVISIBLE);
            currentScoreTextView.setText("");
        }
    }

    /**
     * Fired when an activity that has been started for result returns a result
     * @param requestCode The request code that was used to start the activity
     * @param resultCode The result code from the activity
     * @param data //The intent from the activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //Process result, only if it it was requested from cheat activity
        if(requestCode==CHEAT_ACTIVITY_REQUEST){
            //Get result from bundle, and chastise user accordingly
            if(resultCode==RESULT_OK){
                 Bundle bundle = data.getExtras();
                if(bundle!=null) {
                    hasCheated = bundle.getBoolean(HAS_CHEATED);
                    if(hasCheated){
                        Toast.makeText(MainQuizActivity.this, "If you cheat, you won't win. Score won't be counted for this question.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainQuizActivity.this, "I'm glad you didn't go over to the dark side!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    /**
     * Dynamically adds radiobuttons to the radio group
     * and set answer text on radio buttons
     *
     * @param numberofradiobtns Number of buttons to draw
     * @param group             The RadioGroup to add radio buttons to
     */
    private void drawRadioButtons(int numberofradiobtns, RadioGroup group) {
        for (int i = 0; i < numberofradiobtns; i++) {
            RadioButton radiobutton = new RadioButton(this);
            radiobutton.setText(questions.get(currentquestionnumber).getAnswers().getAnswers().get(i).second);
            radiobutton.setTextAppearance(MainQuizActivity.this,android.R.style.TextAppearance_DeviceDefault_Large);
            radiobutton.setId(i);
            group.addView(radiobutton);
        }
    }

    /**
     * Restores the instance state, if previously saved
     *
     * @param savedInstanceState The previously saved instance state
     */
    private void handleInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            playagainbutton.setVisibility(View.INVISIBLE);
            nextbutton.setVisibility(View.VISIBLE);
            this.isCorrect = savedInstanceState.getBoolean(IS_QUESTION_CORRECT);
            this.score = savedInstanceState.getInt(GAME_SCORE);
            this.isGameOver = savedInstanceState.getBoolean(IS_GAME_OVER);
            currentScoreTextView.setText(savedInstanceState.getString(SCORE_TEXT));
            answergroup.removeAllViews();
            currentquestionnumber = savedInstanceState.getInt(QUESTION_NUMBER);
            if (currentquestionnumber < numberofquestions) {
                addViewToLayout();
                answergroup.check(savedInstanceState.getInt(RADIO_BTN_INDEX));
            } else {
                questionTextView.setText(savedInstanceState.getString(QUESTION_TEXT_VIEW));
            }
            if (isGameOver) {
                nextbutton.setVisibility(View.INVISIBLE);
                playagainbutton.setVisibility(View.VISIBLE);
                cheatbutton.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * Adds the question to textview, sets the correct number of radio buttons for that question
     * and adds the radio buttons to the view
     */
    private void addViewToLayout() {
        final int numberofradiobtns = questions.get(currentquestionnumber).getAnswers().getAnswers().size();
        drawRadioButtons(numberofradiobtns, answergroup);
        questionTextView = (TextView) findViewById(R.id.textview1);
        questionTextView.setText(questions.get(currentquestionnumber).getQuestion());
    }


    /**
     * Fired when an instance state is being saved
     *
     * @param instanceState The instance state to save data into
     */
    @Override
    public void onSaveInstanceState(Bundle instanceState) {
        instanceState.putInt(QUESTION_NUMBER, currentquestionnumber);
        instanceState.putString(QUESTION_TEXT_VIEW, questionTextView.getText().toString());
        instanceState.putInt(RADIO_BTN_INDEX, answergroup.getCheckedRadioButtonId());
        instanceState.putBoolean(IS_QUESTION_CORRECT, this.isCorrect);
        instanceState.putBoolean(IS_GAME_OVER, isGameOver);
        instanceState.putString(SCORE_TEXT,currentScoreTextView.getText().toString());
    }
}