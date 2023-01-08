package com.example.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class mult extends AppCompatActivity {

    Button ok,next;
    TextView score,life,time,question;
    EditText answer;
    Random r = new Random();
    int num1,num2;
    int cans;
    int userans;
    int userscore = 0;
    int userlife = 3;
    CountDownTimer timer;
    private static final long STRAT_TIMER_IN_MILIS = 60000;
    Boolean timer_running;
    long time_lest_in_milis = STRAT_TIMER_IN_MILIS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mult);

        score = findViewById(R.id.score);
        life = findViewById(R.id.lifeSub);
        time = findViewById(R.id.timeSub);
        question = findViewById(R.id.questionSub);
        answer = findViewById(R.id.answerSub);
        ok = findViewById(R.id.okSub);
        next = findViewById(R.id.nextSub);

        Question();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userans = Integer.valueOf(answer.getText().toString());

                pauseTimer();
                if(userans == cans)
                {
                    question.setText("You answer is correct");
                    userscore = userscore +10;
                    score.setText(""+userscore);
                }
                else
                {
                    question.setText("Your answer in wrong");
                    userlife = userlife - 1;
                    life.setText(""+userlife);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetTimer();

                if(userlife == 0)
                {
                    Intent i = new Intent(mult.this,Result_page.class);
                    i.putExtra("Score",userscore);
                    startActivity(i);
                    finish();
                }
                else
                {

                    Question();
                }

                answer.setText("");
            }
        });
    }

    public void Question(){
        num1 = r.nextInt(10);
        num2 = r.nextInt(10);

        startTimer();
        question.setText(num1 +"*"+ num2);
        cans = num1 * num2;
    }

    public void startTimer()
    {
        timer = new CountDownTimer(time_lest_in_milis,1000) {
            @Override
            public void onTick(long l) {
                time_lest_in_milis = l;
                updateText();
            }

            @Override
            public void onFinish() {
                timer_running = false;
                pauseTimer();
                resetTimer();
                updateText();
                userlife = userlife-1;
                life.setText(""+userlife);
                question.setText("Sorry! Time is up");
            }
        }.start();
        timer_running = true;
    }
    public void updateText()
    {
        int second = (int)(time_lest_in_milis/1000)%60;
        //String timeLeft = String.format(Locale.getDefault(),"%02d",second);
        time.setText(""+second);
    }

    public void pauseTimer()
    {
        timer.cancel();
        timer_running = false;
    }

    public void resetTimer()
    {
        time_lest_in_milis = STRAT_TIMER_IN_MILIS;
        updateText();
    }
}