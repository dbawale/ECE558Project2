package project2.dbawale.pdx.edu.quizapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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
    //String for identifying question number from a Bundle
    private final String QUESTION_NUMBER = "edu.pdx.dbawale.project2.questionnumber";
    private final String QUESTION_TEXT_VIEW = "edu.pdx.dbawale.project2.questiontextview";

    //Android widgets for displaying questions and controlling the quiz
    TextView questionTextView;
    RadioGroup answergroup;
    Button nextbutton;

    //Quiz specific data structures, imported from Project 1
    ArrayList<Question>questions;
    int numberofquestions,currentquestionnumber=0;
    Quiz quiz;

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

                //First, clear the layout
                answergroup.removeAllViews();

                //The, load the layout again
                currentquestionnumber +=1;
                if(currentquestionnumber <numberofquestions){
                    addViewToLayout();
                }
                else{
                    questionTextView.setText(R.string.game_over);
                    nextbutton.setEnabled(false);
                }
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
            group.addView(radiobutton);
        }
    }

    /**
     * Restores the instance state, if previously saved
     * @param savedInstanceState The previously saved instance state
     */
    private void handleInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            answergroup.removeAllViews();
            currentquestionnumber = savedInstanceState.getInt(QUESTION_NUMBER);
            if(currentquestionnumber <numberofquestions){
                addViewToLayout();
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
    }
}