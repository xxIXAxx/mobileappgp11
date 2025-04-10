package com.example.gptry;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Rect;
import android.animation.ValueAnimator;

import android.widget.Button;
import android.widget.ImageView;




public class Goat extends Activity {

    private ImageView playerMessi;
    private ImageView playerRonaldo;
    private ImageView antony;
    private ImageView tick;
    private boolean messiTouchedBall = false;
    private boolean ronaldoTouchedBall = false;
    private float xDelta;
    private float yDelta;
    private float originalX;
    private float originalY;
    private float originalA;
    private float originalB;
    private float originalC;
    private float originalD;



    private ImageView ball;

    @SuppressLint({"ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerMessi = findViewById(R.id.pessi);
        playerRonaldo = findViewById(R.id.ronaldo);
        ball = findViewById(R.id.ball);
        originalX = getResources().getDisplayMetrics().widthPixels / 2 - ball.getWidth() / 2; // Center horizontally
        originalY = 1400;
        originalA = 0; // Center horizontally
        originalB = 1900;
        originalC = 0; // Center horizontally
        originalD = 900;
        antony = findViewById(R.id.antony);
        tick = findViewById(R.id.tick);
        Button btn2 = (Button) findViewById(R.id.Next);


        playerMessi.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int X = (int) event.getRawX();
                final int Y = (int) event.getRawY();
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        xDelta = X - v.getX();
                        yDelta = Y - v.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        v.setX(X - xDelta);
                        v.setY(Y - yDelta);

                        // Check for collision between Messi and the ball
                        if (isCollision(playerMessi, playerRonaldo, ball)) {
                            ball.setX(ball.getX() + 30);
                            ball.setY(ball.getY() - 30);

                            // Animate Messi back to the original position
                            animateView(playerMessi, originalA, originalB);

                            // Animate the ball back to the original position
                            animateView(ball, originalX, originalY);
                            messiTouchedBall = true;
                            if (messiTouchedBall && ronaldoTouchedBall) {
                                antony.setVisibility(View.VISIBLE); // Make Antony visible
                            }
                        }
                        break;
                }
                return true;
            }
        });

        playerRonaldo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int X = (int) event.getRawX();
                final int Y = (int) event.getRawY();
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        xDelta = X - v.getX();
                        yDelta = Y - v.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        v.setX(X - xDelta);
                        v.setY(Y - yDelta);


                        if (isCollision(playerMessi, playerRonaldo, ball)) {
                            ball.setX(ball.getX() + 30);
                            ball.setY(ball.getY() - 30);


                            animateView(playerRonaldo, originalC, originalD);

                            // Animate the ball back to the original position
                            animateView(ball, originalX, originalY);
                            ronaldoTouchedBall = true;
                            if (messiTouchedBall && ronaldoTouchedBall) {
                                antony.setVisibility(View.VISIBLE); // Make Antony visible
                            }

                        }

                        break;
                }
                return true;
            }
        });

        antony.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Make the tick image visible when Antony is clicked
                tick.setVisibility(View.VISIBLE);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent i = new Intent(Goat.this,AnswerMe.class);
                i.putExtra("Key1","Make little ");
                i.putExtra("Key2","Ming happy!");

                startActivity(i);
            }
        });

    }





    private void animateView(View view, float targetX, float targetY) {
        ValueAnimator animatorX = ValueAnimator.ofFloat(view.getX(), targetX);
        animatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                view.setX(value);
            }
        });

        ValueAnimator animatorY = ValueAnimator.ofFloat(view.getY(), targetY);
        animatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                view.setY(value);
            }
        });

        animatorX.setDuration(1000); // Animation duration in milliseconds
        animatorY.setDuration(1000); // Animation duration in milliseconds

        animatorX.start();
        animatorY.start();
    }

    private boolean isCollision(ImageView playerMessi, ImageView playerRonaldo, ImageView ball) {
        Rect rect1 = new Rect(playerMessi.getLeft(), playerMessi.getTop(), playerMessi.getRight(), playerMessi.getBottom());
        Rect rect2 = new Rect(playerRonaldo.getLeft(), playerRonaldo.getTop(), playerRonaldo.getRight(), playerRonaldo.getBottom());
        Rect rectBall = new Rect(ball.getLeft(), ball.getTop(), ball.getRight(), ball.getBottom());

        return rect1.intersect(rectBall) || rect2.intersect(rectBall);
    }


}





