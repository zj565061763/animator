package com.fanwe.www.animator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickSimpleDemo(View v)
    {
        startActivity(new Intent(this, SimpleDemoActivity.class));
    }

    public void onClickRocketDemo(View v)
    {
        startActivity(new Intent(this, RocketDemoActivity.class));
    }

    public void onClickCarDemo(View v)
    {
        startActivity(new Intent(this, CarDemoActivity.class));
    }
}
