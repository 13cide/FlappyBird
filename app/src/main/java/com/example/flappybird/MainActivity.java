package com.example.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.flappybird.UI.GameView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));
    }
}