package project2.dbawale.pdx.edu.quizapp;

import android.app.Activity;
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

    //String for identifying question number from a Bundle
    private final String QUESTION_NUMBER = "edu.pdx.dbawale.project2.questionnumber";
    private final String QUESTION_TEXT_VIEW = "edu.pdx.dbawale.project2.questiontextview";
    private final String RADIO_BTN_INDEX = "edu.pdx.dbawale.project2.radiobuttonindex";
    private final String IS_QUESTION_CORRECT = "edu.pdx.dbawale.project2.iscorrectboolean";
    private final String GAME_SCORE = "edu.pdx.dbawale.project2.score";

    //Android widgets for displaying questions and controlling the quiz
    TextView questionTextView;
    RadioGroup answergroup;
    Button nextbutton;

    //Quiz specific data structures, imported from Project 1
    ArrayList<Question>questions;
    int numberofquestions,currentquestionnumber=0;
    Quiz quiz;

    //Other variables required for app functionality
    Boolean isCorrect=false;
    int score=0;

    /**
     * The onCreate method for the main activity
     * Sets up the listeners for buttons and inflates the layout
     * @param savedInstanceState The instance state that may be previously saved
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_quiz);
        quiz = new Quiz();

        //Set up the default quiz, the same as Project1
        quiz.setupDefaultQuiz();
        questions = quiz.getQuestions();
        numberofquestions = questions.size();
        answergroup = (RadioGroup) findViewById(R.id.default_radio_group);

        //Dynamically add radio buttons to the layout, depending on the number of options
        //in the question
        int numberofradiobtns = questions.get(currentquestionnumber).getAnswers().getAnswers().size();
        drawRadioButtons(numberofradiobtns,answergroup );

        nextbutton = (Button)findViewById(R.id.button1);
        questionTextView = (TextView) findViewById(R.id.textview1);
        questionTextView.setText(questions.get(currentquestionnumber).getQuestion());

        //Anonymous onClickListener for the 'Next' button
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCorrect){
                    Toast.makeText(MainQuizActivity.this,R.string.correctanswer,Toast.LENGTH_SHORT).show();
                    score +=1;
                }
                else{
                    Toast.makeText(MainQuizActivity.this,R.string.incorrectanswer,Toast.LENGTH_SHORT).show();
                }

                //First, clear the layout
                answergroup.removeAllViews();

                //Then, load the layout again
                currentquestionnumber +=1;
                if(currentquestionnumber <numberofquestions){
                    addViewToLayout();
                }
                else{
                    String final_string = "Game over!\nYour score is: "+ score;
                    questionTextView.setText(final_string);
                    nextbutton.setVisibility(View.GONE);

                }
            }
        });

        answergroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                isCorrect = questions.get(currentquestionnumber).getCorrectanswer().equals(String.valueOf(checkedId));
            }
        });
        //Finally, call handleInstanceState, to handle device rotation
        handleInstanceState(savedInstanceState);
    }

    /**
     * Dynamically adds radiobuttons to the radio group
     * and set answer text on radio buttons
     * @param numberofradiobtns Number of buttons to draw
     * @param group The RadioGroup to add radio buttons to
     */
    private void drawRadioButtons(int numberofradiobtns, RadioGroup group) {
        for(int i=0;i<numberofradiobtns;i++) {
            RadioButton radiobutton = new RadioButton(this);
            radiobutton.setText(questions.get(currentquestionnumber).getAnswers().getAnswers().get(i).second);
            radiobutton.setId(i);
            group.addView(radiobutton);
        }
    }

    /**
     * Restores the instance state, if previously saved
     * @param savedInstanceState The previously saved instance state
     */
    private void handleInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            this.isCorrect = savedInstanceState.getBoolean(IS_QUESTION_CORRECT);
            this.score = savedInstanceState.getInt(GAME_SCORE);
            answergroup.removeAllViews();
            currentquestionnumber = savedInstanceState.getInt(QUESTION_NUMBER);
            if(currentquestionnumber <numberofquestions){
                addViewToLayout();
                answergroup.check(savedInstanceState.getInt(RADIO_BTN_INDEX));
            }
            else
            {
                questionTextView.setText(savedInstanceState.getString(QUESTION_TEXT_VIEW));
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
     * @param instanceState The instance state to save data into
     */
    @Override
    public void onSaveInstanceState(Bundle instanceState){
        instanceState.putInt(QUESTION_NUMBER,currentquestionnumber);
        instanceState.putString(QUESTION_TEXT_VIEW,questionTextView.getText().toString());
        instanceState.putInt(RADIO_BTN_INDEX,answergroup.getCheckedRadioButtonId());
        instanceState.putBoolean(IS_QUESTION_CORRECT,this.isCorrect);
        instanceState.putInt(GAME_SCORE,this.score);
    }
}