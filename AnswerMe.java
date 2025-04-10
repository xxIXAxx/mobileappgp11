package com.example.gptry;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Rect;
import android.animation.ValueAnimator;
import android.animation.ObjectAnimator;

import android.widget.Button;
import android.widget.ImageView;

public class AnswerMe extends AppCompatActivity {
    private ImageView rat, croc, dad, grapes, defaultface, MVP, ratface, crocface,tick2;

    private float originalX_rat, originalY_rat, originalX_croc, originalY_croc;
    private boolean faceChanged = false;

    private float xDelta, yDelta;

    @SuppressLint({"ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_look_in_my_eyes);

        rat = findViewById(R.id.rat);
        croc = findViewById(R.id.croc);
        dad = findViewById(R.id.dad);
        grapes = findViewById(R.id.grapes);
        defaultface = findViewById(R.id.defaultface);


        originalX_rat = rat.getX();
        originalY_rat = rat.getY();
        originalX_croc = croc.getX();
        originalY_croc = croc.getY();
        tick2 = findViewById(R.id.tick2);

        rat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        xDelta = rat.getX() - event.getRawX();
                        yDelta = rat.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        rat.setX(event.getRawX() + xDelta);
                        rat.setY(event.getRawY() + yDelta);
                        handleTouch(rat);

                }
                return true;
            }
        });

        croc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        xDelta = croc.getX() - event.getRawX();
                        yDelta = croc.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        croc.setX(event.getRawX() + xDelta);
                        croc.setY(event.getRawY() + yDelta);

                        handleTouch(croc);


                }
                return true;
            }
        });

        dad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(dad, "translationX", 1000f);
                animator.setDuration(1000);
                animator.start();
                defaultface.setImageResource(R.drawable.mvp);
            }
        });

        grapes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xDelta = grapes.getX() - event.getRawX();
                        yDelta = grapes.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        grapes.setX(event.getRawX() + xDelta);
                        grapes.setY(event.getRawY() + yDelta);
                        handleTouch(grapes);
                        if (isColliding(grapes, defaultface)) {
                            tick2.setVisibility(View.VISIBLE);
                        }

                }
                return true;
            }
        });


        Bundle extras = getIntent().getExtras();

        String value1 = extras.getString("Key1");
        String value2 = extras.getString("Key2");


        TextView tv2 = (TextView) findViewById(R.id.title);
        tv2.setText(value1 + value2);


    }


    private void handleTouch(View view) {
        Rect rect1 = new Rect((int) view.getX(), (int) view.getY(), (int) (view.getX() + view.getWidth()), (int) (view.getY() + view.getHeight()));
        Rect rect2 = new Rect((int) defaultface.getX(), (int) defaultface.getY(), (int) (defaultface.getX() + defaultface.getWidth()), (int) (defaultface.getY() + defaultface.getHeight()));

        if (rect1.intersect(rect2)) {
            if (view == grapes) {
                defaultface.setImageResource(R.drawable.grapeface);
                tick2.setVisibility(View.VISIBLE);
            } else {
                // Change defaultface to ratface if rat touches it, otherwise to crocface
                defaultface.setImageResource(view == rat ? R.drawable.ratface : R.drawable.crocface);
            }
        }
    }

    private boolean isColliding(View view1, View view2) {
        Rect rect1 = new Rect(view1.getLeft(), view1.getTop(), view1.getRight(), view1.getBottom());
        Rect rect2 = new Rect(view2.getLeft(), view2.getTop(), view2.getRight(), view2.getBottom());
        return rect1.intersect(rect2);
    }
}