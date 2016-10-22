package project2.dbawale.pdx.edu.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import project2.dbawale.pdx.edu.quizlibrary.*;


public class MainQuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_quiz);
        final Quiz quiz = new Quiz();
        quiz.setupDefaultQuiz();
        Button button = (Button)findViewById(R.id.button1);
        final TextView textView = (TextView) findViewById(R.id.textview1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(quiz.getQuestions().get(0).toString());
            }
        });
    }
}
