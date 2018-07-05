package com.pikchillytechnologies.squeezequiz;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

public class QuizListActivity extends AppCompatActivity {

    // Variable Declaration
    String m_Name;
    Bundle m_UserNameBundle;
    String m_ToastMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);


        // Get user name passed from previous Main Activity screen
        m_UserNameBundle = getIntent().getExtras();
        m_Name = m_UserNameBundle.getString("userName", "Guest");

        // Show welcome Message Toast with user name
        m_ToastMsg = "Welcome " + m_Name;
        showToastMessage(m_ToastMsg);

    }

    /**
     * Function to Navigate user based on mode selected
     */
    public void levelSelected(View view) {

        if (view.getId() == R.id.cardView_Basic) {
            questionActivityIntent("Basic");
        } else if (view.getId() == R.id.cardView_Medium) {
            // Navigate user to Medium Level Quiz
            questionActivityIntent("Medium");

        } else if (view.getId() == R.id.cardView_Advanced) {
            // Navigate user to Advanced Level Quiz
            questionActivityIntent("Advanced");
        }
    }

    /**
     * Function that takes the quiz mode selected and passes that as value to QuestionActivity
     */
    private void questionActivityIntent(String quizMode) {

        Intent questionsIntent = new Intent(this, QuizActivity.class);
        questionsIntent.putExtra("quizMode", String.valueOf(quizMode));
        questionsIntent.putExtra("userName", String.valueOf(m_Name));
        startActivity(questionsIntent);
    }

    /**
     * Function to show Toast Message
     */
    public void showToastMessage(String msg) {
        Toast.makeText(QuizListActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
