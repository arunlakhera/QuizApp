package com.pikchillytechnologies.squeezequiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {

    // Variable Declaration
    Bundle m_UserScoreBundle;
    String m_Name;
    String m_TotalQuestions;
    String m_NotAnswered;
    String m_Correct;
    String m_Wrong;
    String m_Score;

    TextView m_UserName_TextView;
    TextView m_TotalQuestions_TextView;
    TextView m_Correct_TextView;
    TextView m_Wrong_TextView;
    TextView m_NotAnswered_TextView;
    TextView m_Score_TextView;
    RatingBar m_Score_RatingBar;
    Button m_Restart_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Get user Score data passed from Quiz Activity screen
        m_UserScoreBundle = getIntent().getExtras();
        m_Name = m_UserScoreBundle.getString("userName","0");
        m_TotalQuestions = m_UserScoreBundle.getString("totalQuestions","0");
        m_Correct = m_UserScoreBundle.getString("correct","0");
        m_Wrong = m_UserScoreBundle.getString("wrong","0");
        m_NotAnswered = m_UserScoreBundle.getString("notAnswered","0");
        m_Score = String.valueOf(Integer.valueOf(m_Correct) * 10);

        // Initialize the View instances in Result Activity
        m_UserName_TextView = findViewById(R.id.textView_UserName);
        m_TotalQuestions_TextView = findViewById(R.id.textView_TotalQuestions);
        m_Correct_TextView = findViewById(R.id.textView_Correct);
        m_Wrong_TextView = findViewById(R.id.textView_Wrong);
        m_NotAnswered_TextView = findViewById(R.id.textView_NotAnswered);
        m_Score_TextView = findViewById(R.id.textView_Score);
        m_Score_RatingBar = findViewById(R.id.score_RatingBar);
        m_Restart_Button = findViewById(R.id.button_Restart);

        // Call Function to Update the UserInterface views
        updateUI();

        m_Restart_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check the Button Text and update the next level or previous level
                nextLevelIntent();
            }
        });
    }

    /**
     *  Function to show the Score Summary of user in views
     *  1. Calculate percentage
     *  2. Show Rating as per percentage
     * */
    public void updateUI(){

        m_UserName_TextView.setText(m_Name);
        m_TotalQuestions_TextView.setText(m_TotalQuestions);
        m_Correct_TextView.setText(m_Correct);
        m_Wrong_TextView.setText(m_Wrong);
        m_NotAnswered_TextView.setText(m_NotAnswered);
        m_Score_TextView.setText(m_Score);

        Integer totalQues = Integer.valueOf(m_TotalQuestions);
        Integer correctAns = Integer.valueOf(m_Correct);
        Integer percent = (correctAns * 100)/totalQues;

        if (percent > 90) {
            // 5 star
            m_Score_RatingBar.setRating(5);
        }else if (percent > 80) {
            // 4.5 star
            m_Score_RatingBar.setRating((float) 4.5);
        }else if (percent > 70) {
            // 4 star
            m_Score_RatingBar.setRating(4);
        }else if (percent > 60) {
            // 3.5 star
            m_Score_RatingBar.setRating((float) 3.5);
        }else if (percent > 50) {
            // 3 star
            m_Score_RatingBar.setRating(3);
        }else if (percent > 40) {
            // 2 star
            m_Score_RatingBar.setRating(2);

        }else {
            // 1 star
            m_Score_RatingBar.setRating(1);
        }
    }

    // Check which Level user in to Lock or Unlock the next Level
    public void nextLevelIntent() {
        Intent nextLevelIntent = new Intent(this, QuizListActivity.class);
        nextLevelIntent.putExtra("userName", String.valueOf(m_Name));
        startActivity(nextLevelIntent);
    }
}