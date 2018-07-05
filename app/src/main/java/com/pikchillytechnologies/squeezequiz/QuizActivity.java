package com.pikchillytechnologies.squeezequiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    // Variable Declaration
    String m_Name;
    Bundle m_UserNameBundle;
    String m_ToastMsg;
    List<Quiz> m_Questions;
    Integer m_TotalQuestions;
    Integer m_QuesNum;
    Integer m_NotAnswered;
    Integer m_Correct;
    Integer m_Wrong;
    String m_Points = "10";
    String m_QuizMode;

    Boolean m_answer1Clicked;
    Boolean m_answer2Clicked;
    Boolean m_answer3Clicked;
    Boolean m_answer4Clicked;

    TextView m_Question_TextView;
    EditText m_UI_Answer_EditText;
    LinearLayout m_UserInputLayout;
    LinearLayout m_RadioButtonLayout;
    LinearLayout m_CheckBoxLayout;
    Button m_Prev_Button;
    Button m_Next_Button;
    Button m_SubmitButton;
    TextView m_QuestionNumber;
    TextView m_Points_TextView;
    ImageView m_QuizMode_ImageView;

    RadioGroup m_RadioGroup;
    RadioButton m_Answer1_RadioButton;
    RadioButton m_Answer2_RadioButton;
    RadioButton m_Answer3_RadioButton;
    RadioButton m_Answer4_RadioButton;

    CheckBox m_Answer1_CheckBox;
    CheckBox m_Answer2_CheckBox;
    CheckBox m_Answer3_CheckBox;
    CheckBox m_Answer4_CheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Set default Values
        reset();

        // Get user name passed from previous Main Activity screen
        m_UserNameBundle = getIntent().getExtras();
        m_Name = m_UserNameBundle.getString("userName", "Guest");
        m_QuizMode = m_UserNameBundle.getString("quizMode", "Basics");

        // Initialize the view instances
        m_Question_TextView = findViewById(R.id.textView_Question);
        m_UI_Answer_EditText = findViewById(R.id.editText_Answer);
        m_UserInputLayout = findViewById(R.id.layout_UI_Answers);
        m_RadioButtonLayout = findViewById(R.id.layout_RB_Answers);
        m_CheckBoxLayout = findViewById(R.id.layout_CB_Answers);
        m_QuestionNumber = findViewById(R.id.textView_Question_Number);
        m_Points_TextView = findViewById(R.id.textView_Points);
        m_Prev_Button = findViewById(R.id.button_Prev);
        m_Next_Button = findViewById(R.id.button_Next);
        m_SubmitButton = findViewById(R.id.button_Submit);

        m_RadioGroup = findViewById(R.id.radioGroup_Answers);

        m_Answer1_RadioButton = findViewById(R.id.radioButton_Answer1);
        m_Answer2_RadioButton = findViewById(R.id.radioButton_Answer2);
        m_Answer3_RadioButton = findViewById(R.id.radioButton_Answer3);
        m_Answer4_RadioButton = findViewById(R.id.radioButton_Answer4);

        m_Answer1_CheckBox = findViewById(R.id.checkBox_Answer1);
        m_Answer2_CheckBox = findViewById(R.id.checkBox_Answer2);
        m_Answer3_CheckBox = findViewById(R.id.checkBox_Answer3);
        m_Answer4_CheckBox = findViewById(R.id.checkBox_Answer4);

        // Set the Image to quizMode that was passed i.e either Basics/Medium/Advanced
        m_QuizMode_ImageView = findViewById(R.id.imageView_QuizMode);

        // Show welcome Message Toast with user name
        m_ToastMsg = "Welcome " + m_Name;
        showToastMessage(m_ToastMsg);

        if (m_QuizMode.equals("Basics")) {
            m_QuizMode_ImageView.setImageResource(R.drawable.basic);

        } else if (m_QuizMode.equals("Medium")) {
            m_QuizMode_ImageView.setImageResource(R.drawable.medium);

        } else if (m_QuizMode.equals("Advanced")) {
            m_QuizMode_ImageView.setImageResource(R.drawable.advanced);
        }

        // Call Function to set Question and Show Question
        setQuestion();
        showQuestion();

        // Save Total Questions in quiz
        m_TotalQuestions = m_Questions.size();

        /**
         * Action to perform when Next button is clicked
         * 1. Check if current question number is less than total questions in quiz
         * 2. Call function to save user response to a question
         * 3. Increment the question number
         * 4. Call function to Reset Answers of previous question
         * 5. Call function to Show next question if available
         * */
        findViewById(R.id.button_Next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (m_QuesNum < (m_Questions.size() - 1)) {

                    saveUserAnswers();
                    m_QuesNum++;

                    /*
                    m_answer1Clicked = false;
                    m_answer2Clicked = false;
                    m_answer3Clicked = false;
                    m_answer4Clicked = false;

                    m_Answer1_RadioButton.setChecked(false);
                    m_Answer2_RadioButton.setChecked(false);
                    m_Answer3_RadioButton.setChecked(false);
                    m_Answer4_RadioButton.setChecked(false);

                    m_Answer1_CheckBox.setChecked(false);
                    m_Answer2_CheckBox.setChecked(false);
                    m_Answer3_CheckBox.setChecked(false);
                    m_Answer4_CheckBox.setChecked(false);

                    m_UI_Answer_EditText.setText("");
*/
                    showQuestion();
                }
            }
        });

        /**
         * Action to perform when Previous button is clicked
         * 1. Check if current question number is greater than 0
         * 2. if current question number is greated only then decrement it and call showQuestion function
         * */
        findViewById(R.id.button_Prev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (m_QuesNum > 0) {
                    // saveUserAnswers();
                    m_QuesNum--;
                    showQuestion();
                }
            }
        });

        /**
         * Action to perform when submit button is clicked
         * 1. Save Answer from the user for last question
         * 2. Check Answers from the user
         * 3. Calculate and show summary of correct / wrong / not answered questions
         * 4. Navigate user to Score Summary Activity
         *
         * */
        findViewById(R.id.button_Submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Call function to save user response to a question
                saveUserAnswers();

                // Call function to check response from user
                checkAnswers();

                m_ToastMsg = " End of Quiz:\n" + "Correct:" + m_Correct + "\nNot Answered:" + m_NotAnswered + "\nWrong:" + m_Wrong;
                showToastMessage(m_ToastMsg);

                // Show Score Summary Activity to the user
                Intent showSummaryIntent = new Intent(QuizActivity.this, ResultActivity.class);
                showSummaryIntent.putExtra("userName", String.valueOf(m_Name));
                showSummaryIntent.putExtra("totalQuestions", String.valueOf(m_TotalQuestions));
                showSummaryIntent.putExtra("correct", String.valueOf(m_Correct));
                showSummaryIntent.putExtra("wrong", String.valueOf(m_Wrong));
                showSummaryIntent.putExtra("notAnswered", String.valueOf(m_NotAnswered));
                startActivity(showSummaryIntent);
            }
        });
    }

    /**
     * Function to Set questions for the quiz
     * 1. Disable Back button for Medium and Advanced Level
     * 2. Enable Back button for Basic Level only
     */
    public void setQuestion() {

        if (m_QuizMode.equals("Medium")) {
            m_Questions.add(new Quiz("CB", "What are the main components in android?", "Activity", "Services", "Broadcast Receiver", "Content provider", true, true, true, true));
            m_Questions.add(new Quiz("CB", "What are commands needed to create APK in android?", "No need to write any commands", "Create apk_android in command line", "Javac,dxtool, aapt tool", "jarsigner tool, and zipalign", false, false, true, true));
            m_Questions.add(new Quiz("CB", "What are the type of flags to run an application in android?", "FLAG_ACTIVITY_NEW_TASK", "FLAG_ACTIVITY_CLEAR_TOP", "FLAG_NEW_ACTIVITY", "None of the Above", true, true, false, false));
            m_Questions.add(new Quiz("CB", "Which of these forms the Order in dialog-box in Android", "Positive", "Neutral", "Negative", "None of the Above", true, true, true, false));
            m_Questions.add(new Quiz("CB", "During an Activity life-cycle, what is the first callback method invoked by the system?", "onStop()", "onStart()", "onCreate()", "onRestore()", false, false, true, false));
            m_Questions.add(new Quiz("CB", "Which configuration file holds the permission to use the internet?", "Layout file", "Property file", "Java source file", "Manifest file", false, false, false, true));
            m_Questions.add(new Quiz("CB", "Which of the following is NOT a valid usage for Intents?", "Activate and Activity", "Activate a Service", "Activate a Broadcast receiver", "Activate a SQLite DB Connection", false, false, false, true));
            m_Questions.add(new Quiz("CB", "Which of the following is not a valid Android resource file name?", "mylayout.xml", "myLayout.xml", "my_layout.xml", "mylayout1.xml", false, true, false, false));
            m_Questions.add(new Quiz("CB", "Which of these is not defined as a process state?", "Non-visible", "Visible", "Foreground", "Background", true, false, false, false));
            m_Questions.add(new Quiz("CB", "What is a correct statement about an XML layout file?", "A layout PNG image file", "A file used to draw the content of an Activity", "A file that contains all application permission information", "A file that contains a single activity widget", false, true, false, false));

            // Disable Back Button
            m_Prev_Button.setEnabled(false);

        } else if (m_QuizMode.equals("Advanced")) {
            m_Questions.add(new Quiz("UI", "What is DDMS in android?", "DALVIK DEBUG MONITOR SERVICES"));
            m_Questions.add(new Quiz("UI", "What do ADT stands for?", "ANDROID DEVELOPMENT TOOL"));
            m_Questions.add(new Quiz("UI", "There can be only one running Activity at a given time. \n TRUE or FALSE", "TRUE"));
            m_Questions.add(new Quiz("UI", "All files are compressed to a single file is called APK \n TRUE or FALSE", "TRUE"));
            m_Questions.add(new Quiz("UI", "What is ANR in android?", "APPLICATION NOT RESPONDING"));
            m_Questions.add(new Quiz("UI", ".........acts as a bridge between emulator and IDE & executes remote shell commands to run applications on an emulator", "ANDROID DEBUG BRIDGE"));
            m_Questions.add(new Quiz("UI", "Is it possible to have an activity without UI to perform action/actions? \n YES or NO", "YES"));
            m_Questions.add(new Quiz("UI", "On which thread broadcast receivers will work in android?", "MAIN THREAD"));
            m_Questions.add(new Quiz("UI", "What is a GCM in android?", "GOOGLE CLOUD MESSAGING"));
            m_Questions.add(new Quiz("UI", "An Activity in a stopped state is doing nothing \n TRUE or FALSE", "FALSE"));
            // Disable Back Button
            m_Prev_Button.setEnabled(false);

        } else {
            m_Questions.add(new Quiz("RB", "What is Pending Intent in android?", "It is a kind of an intent", "It is used to pass the data between activities", "It will fire at a future point of time.", "None of the Above", "Answer3"));
            m_Questions.add(new Quiz("RB", "What is android view group?", "Collection of views and other child views", "Base class of building blocks", " Layouts", "None of the Above", "Answer1"));
            m_Questions.add(new Quiz("RB", "How many threads are there in asyncTask in android?", "Only one", "Two", "AsyncTask doesn't have thread", "None of the Above", "Answer1"));
            m_Questions.add(new Quiz("RB", "What is the time limit of broadcast receiver in android?", "10 sec", "15 sec", "20 sec", "25 sec", "Answer1"));
            m_Questions.add(new Quiz("RB", "What is an activity in Android?", "Activity performs the actions on the screen", "Manage the Application content", "Screen UI", "None of the Above", "Answer1"));
            m_Questions.add(new Quiz("RB", "Which of the following is/are are the subclasses in Android?", "Action Bar Activity", "Launcher Activity", "Preference Activity", "All of above", "Answer4"));
            m_Questions.add(new Quiz("RB", "On which thread broadcast receivers will work in android?", "Worker Thread", "Main Thread", "Activity Thread", "None of the Above", "Answer2"));
            m_Questions.add(new Quiz("RB", "What is sleep mode in android?", "Only Radio interface layer and alarm are in active mode", "Switched off", "Air plane mode", "None of the Above", "Answer1"));
            m_Questions.add(new Quiz("RB", "What is a base adapter in android?", "Common class for any adapter, can be used for both ListView and spinner", "A kind of adapter", "Data storage space", "None of the above.", "Answer1"));
            m_Questions.add(new Quiz("RB", "How to fix crash using log cat in android?", "Gmail", "log cat contains the exception name along with the line number", "Google Search", "None of the Above", "Answer2"));

            // Disable Back Button
            m_Prev_Button.setEnabled(true);
        }
    }

    /**
     * Function to Show Question and answer options to the user
     * 1. Show the current question number in UI
     * 2. Change the color of Next / Prev button
     * 3. If question is of type UI i.e. UserInput allow user to input answer
     * 4. If question is of type RB i.e RadioButton allow user to select one answer option
     * 5. If question is of type CB i.e CheckBox allow user to select multiple answers
     * 6. Check if user is at last question then only show Submit button else it is invisible
     */
    public void showQuestion() {

        // Show Question Number
        String questionNumber = (m_QuesNum + 1) + "/" + (m_Questions.size());
        m_QuestionNumber.setText(questionNumber);

        m_Points_TextView.setText(m_Points);

        // Call Function to Change the Button Color
        changeButtonColor();

        // Clear previous selection
        m_RadioGroup.clearCheck();

        if (m_Questions.get(m_QuesNum).getM_QuestionType().equals("UI")) {

            m_UserInputLayout.setVisibility(View.VISIBLE);
            m_RadioButtonLayout.setVisibility(View.INVISIBLE);
            m_CheckBoxLayout.setVisibility(View.INVISIBLE);

            m_UI_Answer_EditText.setText(m_Questions.get(m_QuesNum).getM_UserInputAnswer());

        } else if (m_Questions.get(m_QuesNum).getM_QuestionType().equals("RB")) {

            m_RadioButtonLayout.setVisibility(View.VISIBLE);
            m_UserInputLayout.setVisibility(View.INVISIBLE);
            m_CheckBoxLayout.setVisibility(View.INVISIBLE);

            m_Answer1_RadioButton.setText(m_Questions.get(m_QuesNum).getM_Answer1());
            m_Answer2_RadioButton.setText(m_Questions.get(m_QuesNum).getM_Answer2());
            m_Answer3_RadioButton.setText(m_Questions.get(m_QuesNum).getM_Answer3());
            m_Answer4_RadioButton.setText(m_Questions.get(m_QuesNum).getM_Answer4());

            // If already answered by user set the answer
            if (m_Questions.get(m_QuesNum).getM_UserAnswer1()) {
                m_Answer1_RadioButton.setChecked(true);
            } else if (m_Questions.get(m_QuesNum).getM_UserAnswer2()) {
                m_Answer2_RadioButton.setChecked(true);
            } else if (m_Questions.get(m_QuesNum).getM_UserAnswer3()) {
                m_Answer3_RadioButton.setChecked(true);
            } else if (m_Questions.get(m_QuesNum).getM_UserAnswer4()) {
                m_Answer4_RadioButton.setChecked(true);
            } else {
                m_Answer1_RadioButton.setChecked(false);
                m_Answer2_RadioButton.setChecked(false);
                m_Answer3_RadioButton.setChecked(false);
                m_Answer4_RadioButton.setChecked(false);
            }

        } else if (m_Questions.get(m_QuesNum).getM_QuestionType().equals("CB")) {

            m_CheckBoxLayout.setVisibility(View.VISIBLE);
            m_RadioButtonLayout.setVisibility(View.INVISIBLE);
            m_UserInputLayout.setVisibility(View.INVISIBLE);

            m_Answer1_CheckBox.setChecked(false);
            m_Answer2_CheckBox.setChecked(false);
            m_Answer3_CheckBox.setChecked(false);
            m_Answer4_CheckBox.setChecked(false);

            m_Answer1_CheckBox.setText(m_Questions.get(m_QuesNum).getM_Answer1());
            m_Answer2_CheckBox.setText(m_Questions.get(m_QuesNum).getM_Answer2());
            m_Answer3_CheckBox.setText(m_Questions.get(m_QuesNum).getM_Answer3());
            m_Answer4_CheckBox.setText(m_Questions.get(m_QuesNum).getM_Answer4());

            if (m_Questions.get(m_QuesNum).getM_UserAnswer1()) {
                m_Answer1_CheckBox.setChecked(true);
            }

            if (m_Questions.get(m_QuesNum).getM_UserAnswer2()) {
                m_Answer2_CheckBox.setChecked(true);
            }

            if (m_Questions.get(m_QuesNum).getM_UserAnswer3()) {
                m_Answer3_CheckBox.setChecked(true);
            }

            if (m_Questions.get(m_QuesNum).getM_UserAnswer4()) {
                m_Answer4_CheckBox.setChecked(true);
            }

            if (!m_Questions.get(m_QuesNum).getM_UserAnswer1() && !m_Questions.get(m_QuesNum).getM_UserAnswer2() && !m_Questions.get(m_QuesNum).getM_UserAnswer3() && !m_Questions.get(m_QuesNum).getM_UserAnswer4()) {
                m_Answer1_CheckBox.setChecked(false);
                m_Answer2_CheckBox.setChecked(false);
                m_Answer3_CheckBox.setChecked(false);
                m_Answer4_CheckBox.setChecked(false);
            }
        }

        if (m_QuesNum.equals(m_Questions.size() - 1)) {
            m_SubmitButton.setVisibility(View.VISIBLE);
        } else {
            m_SubmitButton.setVisibility(View.INVISIBLE);
        }

        m_Question_TextView.setText(m_Questions.get(m_QuesNum).getM_Question());
    }

    /**
     * Function to change the Button color
     * 1. If first question then gray out and disable the prev button
     * 2. If last question then gray out and disable the next button
     */

    public void changeButtonColor() {

        // If Disable Prev button for Medium and Advanced
        if (m_QuizMode.equals("Medium") || m_QuizMode.equals("Advanced")) {
            m_Prev_Button.setBackgroundResource(R.drawable.backbutton_gray);
            m_Prev_Button.setEnabled(false);
        } else if (m_QuesNum.equals(0)) {
            m_Prev_Button.setBackgroundResource(R.drawable.backbutton_gray);
            m_Prev_Button.setEnabled(false);
        } else if (m_QuesNum.equals(m_Questions.size() - 1)) {
            m_Next_Button.setBackgroundResource(R.drawable.nextbutton_gray);
            m_Next_Button.setEnabled(false);
        } else {
            m_Prev_Button.setBackgroundResource(R.drawable.backbutton_red);
            m_Next_Button.setBackgroundResource(R.drawable.nextbutton_red);
            m_Prev_Button.setEnabled(true);
            m_Next_Button.setEnabled(true);
        }
    }

    /**
     * Function to store user response to a question
     */
    public void saveUserAnswers() {

        if (m_Questions.get(m_QuesNum).getM_QuestionType().equals("UI")) {

            String userAns = String.valueOf(m_UI_Answer_EditText.getText());
            m_Questions.get(m_QuesNum).setM_UserInputAnswer(userAns);

        } else if (m_Questions.get(m_QuesNum).getM_QuestionType().equals("RB")) {

            m_Questions.get(m_QuesNum).setM_UserAnswer1(m_answer1Clicked);
            m_Questions.get(m_QuesNum).setM_UserAnswer2(m_answer2Clicked);
            m_Questions.get(m_QuesNum).setM_UserAnswer3(m_answer3Clicked);
            m_Questions.get(m_QuesNum).setM_UserAnswer4(m_answer4Clicked);

        } else if (m_Questions.get(m_QuesNum).getM_QuestionType().equals("CB")) {

            m_Questions.get(m_QuesNum).setM_UserAnswer1(m_answer1Clicked);
            m_Questions.get(m_QuesNum).setM_UserAnswer2(m_answer2Clicked);
            m_Questions.get(m_QuesNum).setM_UserAnswer3(m_answer3Clicked);
            m_Questions.get(m_QuesNum).setM_UserAnswer4(m_answer4Clicked);

        }

        m_answer1Clicked = false;
        m_answer2Clicked = false;
        m_answer3Clicked = false;
        m_answer4Clicked = false;
    }

    /**
     * Function to Check Answers
     * 1. Loop through all questions in quiz
     * 2. Calculate Correct / Wrong / Not Answered questions
     */
    public void checkAnswers() {

        for (int i = 0; i <= m_Questions.size() - 1; i++) {

            if (m_Questions.get(i).getM_QuestionType().equals("UI")) {

                if (m_Questions.get(i).getM_CorrectAnswer().equals(m_Questions.get(i).getM_UserInputAnswer())) {
                    m_Correct++;
                } else if (m_Questions.get(i).getM_UserInputAnswer().isEmpty() || m_Questions.get(i).getM_UserInputAnswer() != null) {
                    m_NotAnswered++;
                } else {
                    m_Wrong++;
                }
            } else if (m_Questions.get(i).getM_QuestionType().equals("RB")) {

                if ((m_Questions.get(i).getM_CorrectAnswer1() && m_Questions.get(i).getM_UserAnswer1())
                        || (m_Questions.get(i).getM_CorrectAnswer2() && m_Questions.get(i).getM_UserAnswer2())
                        || (m_Questions.get(i).getM_CorrectAnswer3() && m_Questions.get(i).getM_UserAnswer3())
                        || (m_Questions.get(i).getM_CorrectAnswer4() && m_Questions.get(i).getM_UserAnswer4())
                        ) {
                    m_Correct++;
                } else if (!m_Questions.get(i).getM_UserAnswer1() && !m_Questions.get(i).getM_UserAnswer2() && !m_Questions.get(i).getM_UserAnswer3() && !m_Questions.get(i).getM_UserAnswer4()) {
                    m_NotAnswered++;
                } else {
                    m_Wrong++;
                }
            } else if (m_Questions.get(i).getM_QuestionType().equals("CB")) {

                Boolean wrongFlag = false;
                Boolean correctAnswerFlag = false;

                if (!wrongFlag) {

                    if (m_Questions.get(i).getM_CorrectAnswer1()) {

                        if (m_Questions.get(i).getM_UserAnswer1()) {
                            correctAnswerFlag = true;
                        } else {
                            correctAnswerFlag = false;
                            wrongFlag = true;
                        }
                    } else {

                        if (m_Questions.get(i).getM_UserAnswer1()) {
                            correctAnswerFlag = false;
                            wrongFlag = true;
                        }
                    }
                }

                if (!wrongFlag) {

                    if (m_Questions.get(i).getM_CorrectAnswer2()) {

                        if (m_Questions.get(i).getM_UserAnswer2()) {
                            correctAnswerFlag = true;
                        } else {
                            correctAnswerFlag = false;
                            wrongFlag = true;
                        }
                    } else {

                        if (m_Questions.get(i).getM_UserAnswer2()) {
                            correctAnswerFlag = false;
                            wrongFlag = true;
                        }
                    }
                }

                if (!wrongFlag) {

                    if (m_Questions.get(i).getM_CorrectAnswer3()) {

                        if (m_Questions.get(i).getM_UserAnswer3()) {
                            correctAnswerFlag = true;
                        } else {
                            correctAnswerFlag = false;
                            wrongFlag = true;
                        }
                    } else {

                        if (m_Questions.get(i).getM_UserAnswer3()) {
                            correctAnswerFlag = false;
                            wrongFlag = true;
                        }
                    }
                }

                if (!wrongFlag) {

                    if (m_Questions.get(i).getM_CorrectAnswer4()) {

                        if (m_Questions.get(i).getM_UserAnswer4()) {
                            correctAnswerFlag = true;
                        } else {
                            correctAnswerFlag = false;
                            wrongFlag = true;
                        }
                    } else {

                        if (m_Questions.get(i).getM_UserAnswer4()) {
                            correctAnswerFlag = false;
                            wrongFlag = true;
                        }
                    }
                }

                if (!m_Questions.get(i).getM_UserAnswer1() && !m_Questions.get(i).getM_UserAnswer2() && !m_Questions.get(i).getM_UserAnswer3() && !m_Questions.get(i).getM_UserAnswer4()) {
                    m_NotAnswered++;
                } else if (!correctAnswerFlag && wrongFlag) {
                    m_Wrong++;
                } else {
                    m_Correct++;
                }
            }
        }
    }

    /**
     * Function that is called when a radio button is selected
     * Check which radio button was selected as answer
     * - Make its flag as true and other answer flag as false
     */

    public void onRadioButtonClicked(View view) {

        switch (view.getId()) {

            case R.id.radioButton_Answer1:
                m_answer1Clicked = true;
                m_answer2Clicked = false;
                m_answer3Clicked = false;
                m_answer4Clicked = false;
                break;
            case R.id.radioButton_Answer2:
                m_answer2Clicked = true;
                m_answer1Clicked = false;
                m_answer3Clicked = false;
                m_answer4Clicked = false;
                break;
            case R.id.radioButton_Answer3:
                m_answer3Clicked = true;
                m_answer1Clicked = false;
                m_answer2Clicked = false;
                m_answer4Clicked = false;
                break;
            case R.id.radioButton_Answer4:
                m_answer4Clicked = true;
                m_answer1Clicked = false;
                m_answer2Clicked = false;
                m_answer3Clicked = false;
                break;
        }
    }

    /**
     * Function that is called when a Check Box is selected
     * Check which Checkbox was selected as answer
     * - Make its flag as true and other answer flag as false
     */

    public void onCheckBoxClicked(View view) {

        switch (view.getId()) {

            case R.id.checkBox_Answer1:
                m_answer1Clicked = true;
                break;
            case R.id.checkBox_Answer2:
                m_answer2Clicked = true;
                break;
            case R.id.checkBox_Answer3:
                m_answer3Clicked = true;
                break;
            case R.id.checkBox_Answer4:
                m_answer4Clicked = true;
                break;
        }
    }

    /**
     * Function to set default values
     */
    public void reset() {

        m_Questions = new ArrayList<>();
        m_QuesNum = 0;
        m_NotAnswered = 0;
        m_Correct = 0;
        m_Wrong = 0;
        m_Name = "";
        m_answer1Clicked = false;
        m_answer2Clicked = false;
        m_answer3Clicked = false;
        m_answer4Clicked = false;
    }

    /**
     * Function to show Toast Message
     */
    public void showToastMessage(String msg) {
        Toast.makeText(QuizActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
