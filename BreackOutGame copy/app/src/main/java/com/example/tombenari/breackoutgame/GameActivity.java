package com.example.tombenari.breackoutgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class GameActivity extends Activity {

    public static final String SCORE = "score";
    BreakoutView breakoutView;
    Context context1 = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        breakoutView = new BreakoutView(this);
        setContentView(breakoutView);

    }

    class BreakoutView extends SurfaceView implements Runnable {

        Thread gameThread = null;
        private Handler handler;

        SurfaceHolder ourHolder;

        volatile boolean playing;
        boolean paused = true;

        Canvas canvas;
        Paint paint;
        long fps;
        private long timeThisFrame;
        int screenX;
        int screenY;
        Paddle paddle;
        Ball ball;
        Brick[] bricks = new Brick[200];
        int numBricks = 0;
        SoundPool soundPool;

        // For sound FX;
        int beep1ID = -1;
        int beep2ID = -1;
        int beep3ID = -1;
        int loseLifeID = -1;
        int explodeID = -1;
        // The score
        int score = 0;
        // Lives
        int lives = 3;



        public BreakoutView(Context context) {
            super(context);
            ourHolder = getHolder();
            paint = new Paint();

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenX = size.x;
            screenY = size.y;
            paddle = new Paddle(screenX ,screenY);
            ball = new Ball(screenX, screenY);

            // Load the sounds
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);

            try{
                AssetManager assetManager = context.getAssets();
                AssetFileDescriptor descriptor;

                descriptor = assetManager.openFd("beep1.ogg");
                beep1ID = soundPool.load(descriptor, 0);

                descriptor = assetManager.openFd("beep2.ogg");
                beep2ID = soundPool.load(descriptor, 0);

                descriptor = assetManager.openFd("beep3.ogg");
                beep3ID = soundPool.load(descriptor, 0);

                descriptor = assetManager.openFd("loseLife.ogg");
                loseLifeID = soundPool.load(descriptor, 0);

                descriptor = assetManager.openFd("explode.ogg");
                explodeID = soundPool.load(descriptor, 0);

            }catch(IOException e){
                Log.e("error", "failed to load sound files");
            }


            createBricksAndRestart();
        }

        public void createBricksAndRestart(){

            // Put the ball back to the start
            ball.reset(screenX, screenY);
            int brickWidth = screenX / 8;
            int brickHeight = screenY / 10;

            numBricks = 0;
            for(int column = 0; column < 8; column ++ ){
                for(int row = 0; row < 3; row ++ ){
                    bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight);
                    numBricks ++;
                }
            }


            // Reset scores and lives
            score = 0;
            lives = 3;
        }

        @Override
        public void run() {

            while (playing){
                long startFrameTime = System.currentTimeMillis();

                if (!paused){
                  update();
                }
                if (paused==true && lives==0 || paused == true && score == numBricks * 10 )  {
                    playing = false;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(context1, NameActivity.class);
                            intent.putExtra(SCORE, score);
                            startActivity(intent);
                            finish();
                        }
                    });

                }
                if (lives > 0) {
                    draw();
                }
                timeThisFrame = System.currentTimeMillis() - startFrameTime;
                if (timeThisFrame >= 1){
                    fps = 1000/ timeThisFrame;
                }
            }

        }

        public void update (){
            paddle.update(fps);
            ball.update(fps);
            for(int i = 0; i < numBricks; i++){

                if (bricks[i].getVisibility()){

                    if(RectF.intersects(bricks[i].getRect(),ball.getRect())) {
                        bricks[i].setInvisible();
                        ball.reverseYVelocity();
                        score = score + 10;
                        soundPool.play(explodeID, 1, 1, 0, 0, 1);
                    }
                }
            }

            // Check for ball colliding with paddle
            if(RectF.intersects(paddle.getRect(),ball.getRect())) {
                ball.setRandomXVelocity();
                ball.reverseYVelocity();
                ball.clearObstacleY(paddle.getRect().top - 2);
                soundPool.play(beep1ID, 1, 1, 0, 0, 1);
            }

            if(ball.getRect().bottom > screenY){
                ball.reverseYVelocity();
                ball.clearObstacleY(screenY - 2);

                // Lose a life
                lives --;
                soundPool.play(loseLifeID, 1, 1, 0, 0, 1);

                if(lives == 0){
                   paused = true;

                }

            }
            if(ball.getRect().top < 0){
                ball.reverseYVelocity();
                ball.clearObstacleY(12);
                soundPool.play(beep2ID, 1, 1, 0, 0, 1);
            }

            if(ball.getRect().left < 0){
                ball.reverseXVelocity();
                ball.clearObstacleX(2);
                soundPool.play(beep3ID, 1, 1, 0, 0, 1);
            }
            if(ball.getRect().right > screenX - 10) {
                ball.reverseXVelocity();
                ball.clearObstacleX(screenX - 22);
                soundPool.play(beep3ID, 1, 1, 0, 0, 1);
            }
            // Pause if cleared screen
            if(score == numBricks * 10){
                paused = true;
                //createBricksAndRestart();
            }
        }

        public void draw(){

            if (ourHolder.getSurface().isValid()){
                canvas = ourHolder.lockCanvas();
                canvas.drawColor(Color.argb(255, 26, 12, 100));
                paint.setColor(Color.argb(255,255,255,255));
                canvas.drawRect(paddle.getRect(),paint);
                canvas.drawRect(ball.getRect(),paint);

                // Change the brush color for drawing
                paint.setColor(Color.argb(255,  249, 20, 12));
                for (int i = 0; i < numBricks; i++) {
                    if (bricks[i].getVisibility()){
                        canvas.drawRect(bricks[i].getRect(),paint);
                    }
                }

                paint.setColor(Color.argb(255,  255, 255, 255));

                // Draw the score
                paint.setTextSize(40);
                canvas.drawText("Score: " + score + "   Lives: " + lives, 10,50, paint);

                // Has the player cleared the screen?
                if(score == numBricks * 10){
                    paint.setTextSize(90);
                    canvas.drawText("YOU HAVE WON!", 10,screenY/2, paint);
                }

                // Has the player lost?
                if(lives <= 0){
                    paint.setTextSize(90);
                    canvas.drawText("YOU HAVE LOST!", 10,screenY/2, paint);
                }

                ourHolder.unlockCanvasAndPost(canvas);
            }
        }
       public void pause (){
           playing = false;
           try {
               gameThread.join();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
        public void resume () {
            playing = true;
            gameThread = new Thread(this);
            handler = new Handler();
            gameThread.start();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:
                    paused = false;

                    if (event.getX() > screenX/2){
                        paddle.setMovementState(paddle.RIGHT);
                    }else {
                        paddle.setMovementState(paddle.LEFT);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    paddle.setMovementState(paddle.STOPPED);
                    break;
            }
            return  true;
        }
    }// end class breakoutview

    @Override
    protected void onResume() {
        super.onResume();
        breakoutView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        breakoutView.pause();
    }
}
