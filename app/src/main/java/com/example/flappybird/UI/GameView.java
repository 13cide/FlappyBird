package com.example.flappybird.UI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.flappybird.R;
import com.example.flappybird.model.Bird;
import com.example.flappybird.model.Pipe;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    private final Bitmap background;
    private DrawThread drawThread;

    private int score = 0;

    private Bird bird;
    private Pipe pipe;

    private Paint textPaint;


    public GameView(Context context) {
        super(context);
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.back);
        drawThread = new DrawThread();
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        surfaceHolder = holder;
        drawThread.start();
        pipe = new Pipe(getContext(), getHeight(), getWidth());
        bird = new Bird(getContext(), 200, getHeight()/2);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(150);
    }

    public void drawFrames(Canvas canvas){
        Rect backGroundRect = new Rect(0, 0, getWidth(), getHeight());
        canvas.drawBitmap(background, null, backGroundRect, null);
        canvas.drawBitmap(bird.getSprite(), bird.x, bird.y, null);
        canvas.drawBitmap(pipe.getBottomPipe(), pipe.x, getHeight() - pipe.getBottomPipe().getHeight(), null);
        canvas.drawBitmap(pipe.getTopPipe(), pipe.x, 0, null);

        String scoreText = String.valueOf(score);
        canvas.drawText(scoreText, getWidth()-scoreText.length()*150, 150, textPaint);
    }

    public void update(){
        bird.update();
        pipe.update();

        if (bird.x > pipe.x && bird.x < pipe.x + 49) score++;

        if (pipe.isCollision(bird) || bird.y <= 0 || bird.y >= getHeight())
            drawThread.running = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (drawThread.running) bird.fly();
        else {

            drawThread = new DrawThread();
            pipe = new Pipe(getContext(), getHeight(), getWidth());
            bird = new Bird(getContext(), 200, getHeight()/2);
            score = 0;
            drawThread.start();
        }
        return super.onTouchEvent(event);
    }

    private class DrawThread extends Thread{
        private volatile boolean running = true;

        @Override
        public void run() {
            Canvas canvas;
            while (running) {
                canvas = surfaceHolder.lockCanvas();
                try {
                    drawFrames(canvas);
                    update();
                }catch (Exception e){
                    Log.d("TLOU", "run: ", e);
                }
                finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            canvas = surfaceHolder.lockCanvas();

            Paint p = new Paint();
            p.setTextSize(250);
            p.setColor(Color.DKGRAY);

            canvas.drawText("Restart", getWidth()/7f,getHeight()/2f, p);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }


    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
}
