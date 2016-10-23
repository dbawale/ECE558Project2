package project2.dbawale.pdx.edu.quizapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import project2.dbawale.pdx.edu.quizlibrary.*;


public class MainQuizActivity extends Activity {
    private final String QUESTION_STRING = "edu.pdx.dbawale.project2.questionstring";
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_quiz);
        final Quiz quiz = new Quiz();
        final LinearLayout layout = (LinearLayout) findViewById(R.id.activity_main_quiz);
        quiz.setupDefaultQuiz();
        Button button = (Button)findViewById(R.id.button1);
        textView = (TextView) findViewById(R.id.textview1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView.setText(quiz.getQuestions().get(0).toString());
            }
        });
        handleInstanceState(savedInstanceState);
    }

    private void handleInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            textView.setText(savedInstanceState.getString(QUESTION_STRING));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle instanceState){
        instanceState.putString(QUESTION_STRING,textView.getText().toString());
    }
}
