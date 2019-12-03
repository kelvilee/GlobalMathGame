package com.example.globalmathgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class JavaMainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtQuestion;
    TextView txtCorrect;
    TextView txtCorrectStatus;
    Button btnAns1;
    Button btnAns2;
    Button btnAns3;
    Button btnReset;
    int answer;
    int qCount;
    int correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_main);

        txtQuestion = findViewById(R.id.txtQuestion);
        txtCorrect = findViewById(R.id.txtCorrect);
        txtCorrectStatus = findViewById(R.id.txtCorrectStatus);
        btnAns1 = findViewById(R.id.btnAns1);
        btnAns2 = findViewById(R.id.btnAns2);
        btnAns3 = findViewById(R.id.btnAns3);
        btnReset = findViewById(R.id.btnReset);
        btnAns1.setOnClickListener(this);
        btnAns2.setOnClickListener(this);
        btnAns3.setOnClickListener(this);
        btnReset.setOnClickListener(this);

        answer = generateQuestion(); // initial question
        String strAns = String.valueOf(answer);
        generateAnswers(strAns);
        txtCorrect.setText(String.valueOf(correct));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAns1:
                if (answer == Integer.parseInt(btnAns1.getText().toString())) {
                    ++correct;
                    txtCorrectStatus.setText(R.string.correct);
                } else {
                    txtCorrectStatus.setText(R.string.wrong);
                }
                break;
            case R.id.btnAns2:
                if (answer == Integer.parseInt(btnAns2.getText().toString())) {
                    ++correct;
                    txtCorrectStatus.setText(R.string.correct);
                } else {
                    txtCorrectStatus.setText(R.string.wrong);
                }
                break;
            case R.id.btnAns3:
                if (answer == Integer.parseInt(btnAns3.getText().toString())) {
                    ++correct;
                    txtCorrectStatus.setText(R.string.correct);
                } else {
                    txtCorrectStatus.setText(R.string.wrong);
                }
                break;
            case R.id.btnReset:
                txtCorrectStatus.setText("");
                qCount = -1;
                correct = 0;
                break;
        }
        ++qCount;
        if (qCount == 5) {
            // android notification
            txtCorrect.setText(String.valueOf(correct));
            ++qCount;
            Intent intent = new Intent(this, DelayedMessageService.class);
            intent.putExtra(DelayedMessageService.EXTRA_MESSAGE, "Your score is " + correct + "/5");
            startService(intent);
        } else if (qCount > 5) {
            // don't do anything past 5Q
        } else {
            // next set of questions
            txtCorrect.setText(String.valueOf(correct));
            answer = generateQuestion(); // initial question
            String strAns = String.valueOf(answer);
            generateAnswers(strAns);
        }
    }

    private int generateQuestion() {
        Random rand = new Random();

        int randOp = rand.nextInt(4);
        int answer;
        String question = "";

        int randInt1 = rand.nextInt(101);
        int randInt2 = rand.nextInt(101);
        if (randOp == 0) { // do addition
            question = randInt1 + " + " + randInt2;
            answer = randInt1 + randInt2;
        } else if (randOp == 1) { // do multiplication
            question = randInt1 + " X " + randInt2;
            answer = randInt1 * randInt2;
        } else if (randOp == 2) { // do subtraction
            while (randInt1 - randInt2 < 0) {
                randInt1 = rand.nextInt(101);
                randInt2 = rand.nextInt(101);
            }
            question = randInt1 + " - " + randInt2;
            answer = randInt1 - randInt2;
        } else { // do division
            while (randInt2 == 0 || randInt1 % randInt2 != 0) {
                randInt1 = rand.nextInt(101);
                randInt2 = rand.nextInt(101);
            }
            question = randInt1 + " / " + randInt2;
            answer = randInt1 / randInt2;
        }
        txtQuestion.setText(question);
        return answer;
    }

    private void generateAnswers(String strAns) {
        // setting button answers
        Random rand = new Random();
        int randAnsPos = rand.nextInt(3);
        if (randAnsPos == 0) {
            btnAns1.setText(strAns);
            btnAns2.setText(String.valueOf(rand.nextInt(5000)));
            btnAns3.setText(String.valueOf(rand.nextInt(5000)));
        } else if (randAnsPos == 1) {
            btnAns1.setText(String.valueOf(rand.nextInt(5000)));
            btnAns2.setText(strAns);
            btnAns3.setText(String.valueOf(rand.nextInt(5000)));
        } else {
            btnAns1.setText(String.valueOf(rand.nextInt(5000)));
            btnAns2.setText(String.valueOf(rand.nextInt(5000)));
            btnAns3.setText(strAns);
        }
    }
}
