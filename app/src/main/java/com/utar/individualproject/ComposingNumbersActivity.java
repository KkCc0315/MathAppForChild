package com.utar.individualproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class ComposingNumbersActivity extends AppCompatActivity {
    private int questionCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composing_numbers);

        TextView num1TextView = findViewById(R.id.num1TextView);
        TextView num2TextView = findViewById(R.id.num2TextView);
        TextView instructionTextView = findViewById(R.id.instructionTextView);

        askQuestion(num1TextView, num2TextView, instructionTextView);
    }

    @SuppressLint("SetTextI18n")
    private void askQuestion(TextView num1TextView, TextView num2TextView, TextView instructionTextView) {
        if (questionCount == 6) {
            showEndDialog();
            return;
        }

        TextView questionCountTextView = findViewById(R.id.questionCountTextView);
        questionCountTextView.setText("Question: " + questionCount); // Update question count
        questionCount++;
        Random random = new Random();
        int num1 = random.nextInt(10); // Generate number between 0 and 10
        int num2 = random.nextInt(10); // Generate number between 0 and 10
        boolean isAddition = random.nextBoolean();
        int correctAnswer = isAddition ? num1 + num2 : num1 - num2;

        num1TextView.setText(String.valueOf(num1));
        num2TextView.setText(String.valueOf(num2));
        instructionTextView.setText(isAddition ? "Add these two numbers" : "Subtract these two numbers");

        displayButtons(correctAnswer);
    }

    private void displayButtons(int correctAnswer) {
        Button[] buttons = new Button[4];
        buttons[0] = findViewById(R.id.button1);
        buttons[1] = findViewById(R.id.button2);
        buttons[2] = findViewById(R.id.button3);
        buttons[3] = findViewById(R.id.button4);

        Random random = new Random();
        int correctButtonIndex = random.nextInt(4); // Index of the button that will show the correct answer

        for (int i = 0; i < 4; i++) {
            if (i == correctButtonIndex) {
                buttons[i].setText(String.valueOf(correctAnswer));
            } else {
                int randomNumber = random.nextInt(20); // Generating random numbers between 0 and 19
                buttons[i].setText(String.valueOf(randomNumber));
            }
            int finalI = i;
            buttons[i].setOnClickListener(v -> onButtonClick(buttons[finalI], correctAnswer));
        }
    }

    private void onButtonClick(Button clickedButton, int correctAnswer) {
        int selectedNumber = Integer.parseInt(clickedButton.getText().toString());

        if (selectedNumber == correctAnswer) {
            Toast.makeText(this, "Correct Answer", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Incorrect Answer", Toast.LENGTH_SHORT).show();
        }

        TextView num1TextView = findViewById(R.id.num1TextView);
        TextView num2TextView = findViewById(R.id.num2TextView);
        TextView instructionTextView = findViewById(R.id.instructionTextView);
        askQuestion(num1TextView, num2TextView, instructionTextView);
    }

    private void showEndDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Congratulation!!")
                .setMessage("You have attempted 5 questions. Do you want to continue?")
                .setPositiveButton("Continue", (dialog, which) -> {
                    questionCount = 1;
                    TextView num1TextView = findViewById(R.id.num1TextView);
                    TextView num2TextView = findViewById(R.id.num2TextView);
                    TextView instructionTextView = findViewById(R.id.instructionTextView);
                    askQuestion(num1TextView, num2TextView, instructionTextView);
                })
                .setNegativeButton("Back to Main", (dialog, which) -> {
                    // Show confirmation dialog before finishing the activity
                    AlertDialog.Builder confirmDialogBuilder = new AlertDialog.Builder(this);
                    confirmDialogBuilder.setTitle("Confirm");
                    confirmDialogBuilder.setMessage("Are you sure you want to go back to the main activity?");
                    confirmDialogBuilder.setPositiveButton("Yes", (dialogInterface, i) -> {
                        // Finish this activity and go back to the main activity
                        finish();
                    });
                    confirmDialogBuilder.setNegativeButton("No", (dialogInterface, i) -> {
                        // Dismiss the dialog
                        dialogInterface.dismiss();
                    });
                    confirmDialogBuilder.show();
                })
                .setCancelable(false)
                .show();
    }
}





