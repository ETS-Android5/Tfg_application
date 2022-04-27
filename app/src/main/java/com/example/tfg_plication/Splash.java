package com.example.tfg_plication;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

public class Splash extends AppCompatActivity implements Animation.AnimationListener {
    private final String FONT_STYLE_SPLASH = "StreetExplorer.otf";
    private Typeface font;
    private TextView app_name;
    private GifImageView app_image;
    private Animation splash_anim_text, splash_anim_img;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        initValues();
        animations();
        updateFont();
        startMp3();
    }

    private void startMp3() {
        mediaPlayer.start();
    }

    private void updateFont() {
        font = Typeface.createFromAsset(getAssets(), FONT_STYLE_SPLASH);
        app_name = (TextView) findViewById(R.id.tws);
        app_name.setTypeface(font);
    }

    private void animations() {
        app_image.setAnimation(splash_anim_img);
        app_name.setAnimation(splash_anim_text);

        splash_anim_text.setAnimationListener(this);
    }

    private void initValues() {
        font = Typeface.createFromAsset(getAssets(), FONT_STYLE_SPLASH);
        app_name = (TextView) findViewById(R.id.tws);
        app_image = (GifImageView) findViewById(R.id.imgSplash);
        splash_anim_text = AnimationUtils.loadAnimation(this, R.anim.splash_text);
        splash_anim_img = AnimationUtils.loadAnimation(this, R.anim.splash_image);
        mediaPlayer = MediaPlayer.create(this, R.raw.frying);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Intent intent = new Intent(Splash.this, Login.class);
        startActivity(intent);
        mediaPlayer.stop();
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}