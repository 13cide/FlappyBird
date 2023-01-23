package com.example.flappybird.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.flappybird.R;

public class Bird extends GameObject{

    private Bitmap sprite;
    private Bitmap forward_sprite;
    private Bitmap up_sprite;
    private Bitmap down_sprite;
    private float ySpeed = 0.0f;
    private static final float Y_ACCELERATION = 9f;

    public Bird(Context context, float x, float y) {
        super(x, y);
        forward_sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.bluebird_2);
        up_sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.bluebird_1);
        down_sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.bluebird_3);
        sprite = forward_sprite;

    }
    public void fly() {
        ySpeed = -50;
    }

    @Override
    public void update(){
        y += ySpeed;
        ySpeed += Y_ACCELERATION;

        if (ySpeed < -10) sprite = up_sprite;
        else if (ySpeed > 10) sprite = down_sprite;
        else sprite = forward_sprite;
    }

    public Bitmap getSprite() {
        return sprite;
    }
}
