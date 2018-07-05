package com.pikchillytechnologies.squeezequiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Variable Declaration
    Button start_Button;
    EditText name_EditText;
    Boolean userNameFlag;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize button instance
        start_Button = findViewById(R.id.button_Start);

        // Action to perform on Start Button click
        start_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Call Function to check if user Name is entered
                checkName();

                // If userName entered, navigate to quiz screen with user name else show message to enter user name
                if (userNameFlag) {
                    Intent quizIntent = new Intent(MainActivity.this, QuizListActivity.class);
                    quizIntent.putExtra("userName", String.valueOf(userName));
                    startActivity(quizIntent);

                } else {

                    Toast.makeText(MainActivity.this, "Please Enter Name!", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    // Function to check if the userName is entered
    public void checkName() {

        // Initialize the name Edit text field instance
        name_EditText = findViewById(R.id.editText_Name);

        // Store the username entered in the variable
        userName = String.valueOf(name_EditText.getText());

        // Check if the userName is null or empty
        if (userName == null || userName.isEmpty()) {
            userNameFlag = false;
        } else {
            userNameFlag = true;
        }
    }
}
