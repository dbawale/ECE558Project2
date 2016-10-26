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
    //String for identifying question in from a Bundle
    private final String QUESTION_STRING = "edu.pdx.dbawale.project2.questionstring";
    //Android widgets for displaying questions
    TextView textView;
    ArrayList<Question>questions;
    RadioGroup answergroup;
    int numberofquestions,currentquestionnumber=0;

    /**
     * The onCreate method for the main activity
     * Sets up the listeners for buttons and inflates the layout
     * @param savedInstanceState The instance state that may be previously saved
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_quiz);
        final Quiz quiz = new Quiz();

        //Set up the default quiz, the same as Project1
        quiz.setupDefaultQuiz();

        if(savedInstanceState==null) {
            //Get the questions
            questions = quiz.getQuestions();
        }

        numberofquestions = questions.size();

        answergroup = (RadioGroup) findViewById(R.id.default_radio_group);

        //Dynamically add radio buttons to the layout, depending on the number of options
        //in the question
        int numberofradiobtns = questions.get(currentquestionnumber).getAnswers().getAnswers().size();
        for(int i=0;i<numberofradiobtns;i++) {
            RadioButton radiobutton = new RadioButton(this);
            radiobutton.setText(questions.get(currentquestionnumber).getAnswers().getAnswers().get(i).second);
            answergroup.addView(radiobutton);
        }

        Button button = (Button)findViewById(R.id.button1);
        textView = (TextView) findViewById(R.id.textview1);
        textView.setText(questions.get(currentquestionnumber).getQuestion());

        //Anonymous onClickListener for the 'Next' button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //First, clear the layout
                answergroup.removeAllViews();
                //Dynamically add radio buttons for the next question
                currentquestionnumber +=1;
                if(currentquestionnumber <=numberofquestions){
                    final int numberofradiobtns = questions.get(currentquestionnumber).getAnswers().getAnswers().size();
                    for(int i=0;i<numberofradiobtns;i++) {
                        RadioButton radiobutton = new RadioButton(MainQuizActivity.this);
                        radiobutton.setText(questions.get(currentquestionnumber).getAnswers().getAnswers().get(i).second);
                        answergroup.addView(radiobutton);
                    }
                }
                textView = (TextView) findViewById(R.id.textview1);
                textView.setText(questions.get(currentquestionnumber).getQuestion());

            }
        });
        //Finally, call handleInstanceState, to handle device rotation
        handleInstanceState(savedInstanceState);
    }

    /**
     * Restores the instance state, if previously saved
     * @param savedInstanceState The previously saved instance state
     */
    private void handleInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            textView.setText(savedInstanceState.getString(QUESTION_STRING));
        }
    }

    /**
     * Fired when an instance state is being saved
     * @param instanceState The instance state to save data into
     */
    @Override
    public void onSaveInstanceState(Bundle instanceState){
        instanceState.putString(QUESTION_STRING,textView.getText().toString());
    }
}
