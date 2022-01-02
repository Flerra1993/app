package it.uniba.sms2122.thehillreloded;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Animazione extends AppCompatActivity {
    private GameView gioco;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gioco=new GameView(this);
        setContentView(gioco);

    }

    @Override
    protected void onResume() {
        super.onResume();
        gioco.resume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        gioco.pause();
    }

    class GameView extends SurfaceView implements Runnable{

        private Thread gameThread;
        private SurfaceHolder ourHolder;
        private volatile boolean playing;
        private Canvas canvas;
        private Bitmap bitmapRunning;
        private boolean isMoving;
        private float runspeedPerSecond = 250;
        private float manXpos = 10, manYpos=10;
        private int frameWidth=115,frameHeight=137;
        private int framecoount=4;
        private int currentframe=0;
        private long fps;
        private long timethisframe;
        private long lastframeChangetime=0;
        private int framelenghtinmillisecond=100;

        private Rect frametodraw= new Rect(0,0,frameWidth,frameHeight);
        private RectF wheretodraw=new RectF(manXpos,manYpos,manXpos+frameWidth,frameHeight);

        public GameView(Context context){
            super(context);
            ourHolder=getHolder();
            bitmapRunning= BitmapFactory.decodeResource(getResources(),R.drawable.anim2);
            bitmapRunning=Bitmap.createScaledBitmap(bitmapRunning,frameWidth*framecoount,frameHeight,false);
        }
        public void run(){
            while (playing){
                long startFrametime=System.currentTimeMillis();
                update();
                draw();

                timethisframe=System.currentTimeMillis()-startFrametime;

                if(timethisframe >= 1){
                    fps=1000/ timethisframe;
                }
            }
        }

        public void update(){

        }

        public void managecurrentframe(){
            long time=System.currentTimeMillis();

            if(isMoving){
                if(time>lastframeChangetime+framelenghtinmillisecond){
                    lastframeChangetime=time;
                    currentframe++;


                    if(currentframe>=framecoount){
                        currentframe=0;
                    }
                }
            }
            frametodraw.left=currentframe*frameWidth;
            frametodraw.right=frametodraw.left+frameWidth;



        }

        public void draw(){
            if(ourHolder.getSurface().isValid()){
                canvas=ourHolder.lockCanvas();
                canvas.drawColor(Color.BLACK);
                wheretodraw.set((int) manXpos,(int)manYpos,(int)manXpos+frameWidth,(int)manYpos+frameHeight);
                managecurrentframe();
                canvas.drawBitmap(bitmapRunning,frametodraw,wheretodraw,null);
                ourHolder.unlockCanvasAndPost(canvas);
            }
        }

        public void pause(){
            playing=false;

            try{
                gameThread.join();
            } catch (InterruptedException e){
                Log.e("ERR","join thread");
            }
        }

        public void resume(){
            playing=true;
            gameThread=new Thread(this);
            gameThread.start();
        }


        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:
                    isMoving= !isMoving;
                    break;
            }
            return true;
        }

    }

}
