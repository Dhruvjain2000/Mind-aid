package com.example.android.myapplication.SpaceWars;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.myapplication.MenuScreen;
import com.example.android.myapplication.R;

public class Spacewar extends AppCompatActivity implements View.OnTouchListener {
    private boolean misPlaying = true;
    private MusicService mServ = new MusicService();
    int score = Spaceship.score;
    MediaPlayer pewPlayer;
    MediaPlayer boomPlayer;
    int oldScore = 0;
    int btn = 0;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getBaseContext(),MenuScreen.class));
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spacewar);
        Button up = (Button) findViewById(R.id.upBtn);
        up.setOnTouchListener(this); // calling onClick() method
        Button down = (Button) findViewById(R.id.downBtn);
        down.setOnTouchListener(this);
        Button right = (Button) findViewById(R.id.rightBtn);
        right.setOnTouchListener(this);
        Button left = (Button) findViewById(R.id.leftBtn);
        left.setOnTouchListener(this);
        Button fire = (Button) findViewById(R.id.fireBtn);
        fire.setOnTouchListener(this);

        //Start the threads
        t.start();
        drawIt.start();
    }



    //Updates the canvas, checks for updated score and checks for playing the explosion sound
    Thread drawIt = new Thread() {
        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    Thread.sleep(30); //DuH FPS
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Do button stuff yeah boi
                            View v = findViewById(R.id.table);
                            v.postInvalidate();
                            updateScore();
                            if (Spaceship.boomPlay){boom();};
                        }
                    });
                }
            } catch (InterruptedException e) {
            }
        }
    };


    //Checks for button pushes
    //If a button is clicked it increases the x and y coordinates of
    //the spaceship by the velocity variable
    Thread t = new Thread() {
        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    Thread.sleep(30);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Do button stuff yeah boi
                            switch (btn) {
                                case 1:
                                    Spaceship.cx += Spaceship.vl;
                                    Spaceship.dr = Spaceship.Direction.EAST;
                                    break;
                                case 2:
                                    Spaceship.cx -= Spaceship.vl;
                                    Spaceship.dr = Spaceship.Direction.WEST;
                                    break;
                                case 3:
                                    Spaceship.cy -= Spaceship.vl;
                                    Spaceship.dr = Spaceship.Direction.NORTH;
                                    break;
                                case 4:
                                    Spaceship.cy += Spaceship.vl;
                                    Spaceship.dr = Spaceship.Direction.SOUTH;
                                    break;
                            }
                        }
                    });
                }
            } catch (InterruptedException e) {
            }
        }
    };


    //Find what buttons are pushed
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // default method for handling onClick Events..
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.rightBtn:
                    Log.d("Action", "Right button");
                    btn = 1;
                    break;
                case R.id.leftBtn:
                    Log.d("Action", "Left button");
                    btn = 2;
                    break;
                case R.id.upBtn:
                    Log.d("Action", "Up button");
                    btn = 3;
                    break;
                case R.id.downBtn:
                    Log.d("Action", "Down button");
                    btn = 4;
                    break;
                case R.id.fireBtn:
                    Log.d("Action", "Fire button");
                    pewPew();
                    Spaceship.dahFlag = true;
                    break;
            }
        } else {
            //No btn
            btn = 0;
        }
        return false;
    }


    //Updates the score in the textview
    public void updateScore() {
        score = Spaceship.score;
        if (score - oldScore >= 0 | score==0){
            //setContentView(R.layout.activity_spacewar);
            TextView tv1 = (TextView) findViewById(R.id.score);
            String thescore = Integer.toString(score);
            tv1.setText(thescore);
            oldScore = score;
        }
    }

    //Binds the music service
    private ServiceConnection Scon = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder binder) {
            mServ = ((MusicService.ServiceBinder) binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };


    @Override
    protected void onPause() {
        //Pause the sound
        super.onPause();
        if (misPlaying){mServ.pauseMusic(); misPlaying=false;}
    }

    @Override
    protected void onResume() {
        //Start the background sound
        super.onResume();
        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        startService(music);
        if (!misPlaying){mServ.resumeMusic(); misPlaying=true;}
    }

    //Plays the laser firing sound
    void pewPew(){
        if(pewPlayer!=null){
            pewPlayer.release();
            pewPlayer=null;
        }
        pewPlayer = MediaPlayer.create(this,R.raw.lasershoot);
        pewPlayer.start();
    }

    //Plays the explosion sound
    void boom(){
        if(boomPlayer!=null){
            boomPlayer.release();
            boomPlayer=null;
        }
        boomPlayer = MediaPlayer.create(this, R.raw.boom);
        boomPlayer.start();
        Spaceship.boomPlay = false;
    }

}


