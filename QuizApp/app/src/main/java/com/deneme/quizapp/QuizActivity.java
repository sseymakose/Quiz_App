package com.deneme.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView tvQuestion, tvScore, tvQuestionNo, tvTimer;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3;
    private Button btnNext;

    int totalQuestions;
    int qCounter = 0;
    int score;

    ColorStateList dfRbColor;
    boolean answered;

    CountDownTimer countDownTimer;


    private QuestionModel currentQuestion;

    private List<QuestionModel> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionList = new ArrayList<>();
        tvQuestion = findViewById(R.id.textQuestion);
        tvScore = findViewById(R.id.textScore);
        tvQuestionNo = findViewById(R.id.textQuestionNo);
        tvTimer = findViewById(R.id.textTimer);

        radioGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        btnNext = findViewById(R.id.btnNext);

        dfRbColor = rb1.getTextColors();

        addQuestions();
        totalQuestions = questionList.size();
        showNextQuestion();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answered == false){
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked()){
                        checkAnswer();
                        countDownTimer.cancel();
                    }else {
                        Toast.makeText(QuizActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    showNextQuestion();
                }
            }
        });

    }

    private void checkAnswer() {
        answered = true;
        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNo = radioGroup.indexOfChild(rbSelected) + 1;
        if (answerNo == currentQuestion.getCorrectAnswerNo()){
            score += 10;
            tvScore.setText("Score: "+score);
        }

        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        switch (currentQuestion.getCorrectAnswerNo()){
            case 1:
                rb1.setTextColor(Color.GREEN);
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                break;
        }
        if (qCounter < totalQuestions){
            btnNext.setText("Next");
        }else{
            btnNext.setText("Finish");
        }
    }

    private void showNextQuestion() {

        radioGroup.clearCheck();
        rb1.setTextColor(dfRbColor);
        rb2.setTextColor(dfRbColor);
        rb3.setTextColor(dfRbColor);

        if (qCounter < totalQuestions){
            timer();
            currentQuestion = questionList.get(qCounter);
            tvQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());

            qCounter++;
            btnNext.setText("Submit");
            tvQuestionNo.setText("Question: "+qCounter+"/"+totalQuestions);
            answered = false;

        }else{
            finish();
        }
    }

    private void timer() {
        countDownTimer = new CountDownTimer(20000,1000) {
            @Override
            public void onTick(long l) {
                tvTimer.setText("00:" + l/1000);
            }

            @Override
            public void onFinish() {
                showNextQuestion();
            }
        }.start();
    }

    private void addQuestions() {
        questionList.add(new QuestionModel("What does the following Java code print to the screen? \nint x = 10;\n" +
                "int y = x++;\n" +
                "System.out.println(x + y);",
                "11", "20", "21", 3));
        questionList.add(new QuestionModel("What does the following Java code print to the screen? \nint x = 5;\n" +
                "if (x == 5) {\n" +
                "    System.out.println(\"x is equal to 5.\");\n" +
                "} else {\n" +
                "    System.out.println(\"x is not equal to 5.\");\n" +
                "}",
                "x is equal to 5.", "x is not equal to 5.", "It does not print anything.", 1));
        questionList.add(new QuestionModel("Which of the following is used in Java to search for a specific string of characters in a string expression?",
                "contains()", "find()", "search()", 1));
        questionList.add(new QuestionModel("What does the following Java code print to the screen? \nint x = 5;\n" +
                "x *= 2;\n" +
                "System.out.println(x);",
                "5", "10", "20", 2));
        questionList.add(new QuestionModel("Which of the following is used to calculate the difference between two dates in Java?",
                "TimeUnit", "TimeDifference", "TimeSpan", 1));
        questionList.add(new QuestionModel("What does the following Java code print to the screen? \nint sum = 0;\n" +
                "for (int i = 1; i <= 10; i++) {\n" +
                "    sum += i;\n" +
                "}\n" +
                "System.out.println(sum);",
                "10", "45", "55", 3));
        questionList.add(new QuestionModel("What does the following Java code print to the screen? \nint x = 5;\n" +
                "int square = x * x;\n" +
                "System.out.println(square);",
                "5", "25", "125", 2));
        questionList.add(new QuestionModel("What does the following Java code print to the screen? \ndouble x = 16;\n" +
                "double squareRoot = Math.sqrt(x);\n" +
                "System.out.println(squareRoot);",
                "2", "4", "16", 2));
        questionList.add(new QuestionModel("What does the public keyword in Java indicate?",
                "Accessible from any class in any package", "Accessible only within the same package", "Accessible only within the same class", 1));
        questionList.add(new QuestionModel("In Java, which keyword allows for only a single instance of a class to be created?",
                "static", "final", "singleton", 3));

    }
}