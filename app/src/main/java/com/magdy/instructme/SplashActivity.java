package com.magdy.instructme;


import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 1000;
    ImageView imageView ;
    Animation an1 , an2 ;
    AnimationSet animationSet ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView = (ImageView)findViewById(R.id.splash_img);
        an1 = AnimationUtils.loadAnimation(this,R.anim.anim_alpha);
        an2 = AnimationUtils.loadAnimation(this,R.anim.anim_scale);
        animationSet = new AnimationSet(false);
        animationSet.addAnimation(an1);
        animationSet.addAnimation(an2);
        imageView.startAnimation(animationSet);
        try {
            new Thread()
            {
                public void run()
                {

                    try {
                        sleep(SPLASH_TIME_OUT);
                        Intent i = new Intent(getBaseContext(),MainActivity.class);
                        startActivity(i);
                        finish();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
