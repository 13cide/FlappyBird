package com.example.flappybird.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.loader.content.Loader;

import com.example.flappybird.R;

public class Pipe extends GameObject{
    private Bitmap topPipe;
    private Bitmap bottomPipe;

    private static final float X_SPEED = 50;
    private static final float SPACE_SIZE = 300;

    private float height;
    private float width;

    public Bitmap getTopPipe() {
        return topPipe;
    }

    public Bitmap getBottomPipe() {
        return bottomPipe;
    }

    public Pipe(Context context, float height, float width) {
        super(width, 0);
        this.width = width;
        this.height = height;
        topPipe = BitmapFactory.decodeResource(context.getResources(), R.drawable.pipe_rotated);
        bottomPipe = BitmapFactory.decodeResource(context.getResources(), R.drawable.pipe);
        generatePipe();
    }


    private void generatePipe(){
        y = random(height/4f, height*3/4f);

        topPipe = Bitmap.createScaledBitmap(topPipe, 200, (int) (y-SPACE_SIZE), false);
        bottomPipe = Bitmap.createScaledBitmap(bottomPipe, 200, (int) (height - y-SPACE_SIZE), false);
    }

    @Override
    public void update() {
        x -= X_SPEED;
        if (x < -bottomPipe.getWidth()) {
            x = width;
            generatePipe();
        }
    }


    public boolean isCollision(GameObject gameObject) {
        if (x - 50 < gameObject.x && x + bottomPipe.getWidth() > gameObject.x){
            if (gameObject.y < topPipe.getHeight()) return true;
            return gameObject.y - 10 > height - bottomPipe.getHeight();
        }
        return false;
    }

    private float random(float min, float max) {
        return (float) (min + (Math.random() * (max-min)));
    }
}
