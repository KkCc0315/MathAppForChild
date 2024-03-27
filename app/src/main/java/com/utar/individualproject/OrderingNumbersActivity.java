package com.utar.individualproject;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class OrderingNumbersActivity extends AppCompatActivity {
    private static final int MAX_NUMBERS = 5;
    private int[] numbers;
    private final List<Integer> userOrder = new ArrayList<>();
    private TextView questionTextView;
    private TextView userOrderTextView;
    private TextView questionCountTextView;
    private int questionCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_numbers);

        initializeViews();
        setListeners();

        askQuestion();
        displayNumbers();
    }

    private void initializeViews() {
        questionTextView = findViewById(R.id.questionTextView);
        userOrderTextView = findViewById(R.id.userOrderTextView);
        questionCountTextView = findViewById(R.id.questionCountTextView);
    }

    private void setListeners() {
        Button checkOrderButton = findViewById(R.id.checkOrderButton);
        checkOrderButton.setOnClickListener(v -> checkOrder());
    }

    @SuppressLint("SetTextI18n")
    private void askQuestion() {
        if (questionCount == 6) {
            showEndDialog();
            return;
        }

        questionCountTextView.setText("Question: " + questionCount);
        questionCount++;
        boolean isDescending = new Random().nextBoolean();
        String orderType = isDescending ? "descending" : "ascending";
        questionTextView.setText("Please order the numbers in " + orderType + " order");
    }

    private void generateRandomNumbers() {
        numbers = new Random().ints(MAX_NUMBERS, 0, 100).toArray();
    }

    private void displayNumbers() {
        generateRandomNumbers();

        int[] buttonIds = {R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5};

        for (int i = 0; i < MAX_NUMBERS; i++) {
            Button button = findViewById(buttonIds[i]);
            button.setText(String.valueOf(numbers[i]));
            button.setEnabled(true); // Re-enable the button
            button.setOnClickListener(createButtonClickListener(numbers[i]));
        }
    }


    private View.OnClickListener createButtonClickListener(int number) {
        return v -> {
            userOrder.add(number);
            updateUserOrderText();
            // Disable the button after it's clicked
            v.setEnabled(false);
        };
    }

    @SuppressLint("SetTextI18n")
    private void updateUserOrderText() {
        StringBuilder builder = new StringBuilder();
        for (int num : userOrder) {
            builder.append(num).append(" ");
        }
        userOrderTextView.setText("Your Order: " + builder);
    }

    @SuppressLint("SetTextI18n")
    private void checkOrder() {
        if (userOrder.size() != MAX_NUMBERS) {
            Toast.makeText(this, "Please arrange all numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        int[] sortedNumbers = Arrays.copyOf(numbers, numbers.length);
        Arrays.sort(sortedNumbers);

        boolean isDescending = new Random().nextBoolean();
        boolean isCorrect = isDescending
                ? Arrays.equals(sortedNumbers, userOrder.stream().mapToInt(i -> i).toArray())
                : Arrays.equals(sortedNumbers, reverseIntArray(userOrder.stream().mapToInt(i -> i).toArray()));

        Toast.makeText(this, isCorrect ? "Correct Order" : "Incorrect Order", Toast.LENGTH_SHORT).show();

        askQuestion();
        displayNumbers();
        userOrder.clear();
        userOrderTextView.setText("Your Order: ");
    }

    private int[] reverseIntArray(int[] array) {
        int[] reversedArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            reversedArray[i] = array[array.length - 1 - i];
        }
        return reversedArray;
    }

    @SuppressLint("SetTextI18n")
    private void showEndDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Congratulations!!")
                .setMessage("You have attempted 5 questions. Do you want to continue?")
                .setPositiveButton("Continue", (dialog, which) -> {
                    questionCount = 1; // Reset question count
                    askQuestion();
                    displayNumbers();
                    userOrder.clear();
                    userOrderTextView.setText("Your Order: ");
                })
                .setNegativeButton("Back to Main", (dialog, which) -> {
                    AlertDialog.Builder confirmDialogBuilder = new AlertDialog.Builder(this);
                    confirmDialogBuilder.setTitle("Confirm");
                    confirmDialogBuilder.setMessage("Are you sure you want to go back to the main activity?");
                    confirmDialogBuilder.setPositiveButton("Yes", (dialogInterface, i) -> finish());
                    confirmDialogBuilder.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
                    confirmDialogBuilder.show();
                })
                .setCancelable(false)
                .show();
    }
}






