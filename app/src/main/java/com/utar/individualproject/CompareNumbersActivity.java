package com.utar.individualproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import java.util.Random;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class CompareNumbersActivity extends AppCompatActivity {
    private int num1, num2;
    private boolean isGreaterThanQuestion;
    private int questionCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_numbers);

        TextView questionTextView = findViewById(R.id.questionTextView);
        Button yesButton = findViewById(R.id.yesButton);
        Button noButton = findViewById(R.id.noButton);

        askQuestion(questionTextView);

        yesButton.setOnClickListener(v -> checkAnswer(true));

        noButton.setOnClickListener(v -> checkAnswer(false));
    }

    @SuppressLint("SetTextI18n")
    private void askQuestion(TextView questionTextView) {
        if (questionCount == 6) { // Check if the maximum question count has been reached
            showEndDialog();
            return;
        }

        // Update the question count text
        TextView questionCountTextView = findViewById(R.id.questionCountTextView);
        questionCountTextView.setText("Question: " + questionCount);
        questionCount++; // Increment the question counter

        generateRandomNumbers();

        // Randomly determine whether the question is about "greater than" or "less than"
        isGreaterThanQuestion = new Random().nextBoolean();

        // Set question based on the type of comparison
        if (isGreaterThanQuestion) {
            questionTextView.setText("Is " + num1 + " greater than " + num2 + "?");
        } else {
            questionTextView.setText("Is " + num1 + " less than " + num2 + "?");
        }
    }

    private void checkAnswer(boolean userAnswer) {
        boolean isCorrect;
        if (isGreaterThanQuestion) {
            isCorrect = num1 > num2;
        } else {
            isCorrect = num1 < num2;
        }

        String result = (userAnswer == isCorrect) ? "Correct!" : "Incorrect!";
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();

        // Check if user has attempted 5 questions
        if (questionCount == 6) {
            showEndDialog();
        } else {
            askQuestion(findViewById(R.id.questionTextView));
        }
    }

    private void showEndDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Congratulations!!")
                .setMessage("You have attempted 5 questions. Do you want to continue?")
                .setPositiveButton("Continue", (dialog, which) -> {
                    // Reset question counter and ask next question
                    questionCount = 1;
                    askQuestion(findViewById(R.id.questionTextView));
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
                .setCancelable(false) // Prevent dialog from being dismissed on outside touch
                .show();
    }

    private void generateRandomNumbers() {
        Random random = new Random();
        num1 = random.nextInt(50);
        num2 = random.nextInt(50);

        while (num1 == num2){ //ensure wont generate same num
            num1 = random.nextInt(50);
        }
    }
}










