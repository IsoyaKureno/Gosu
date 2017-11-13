package fr.wildcodeschool.florianscoffee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {

    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TextView _editTextExample = (TextView) findViewById(R.id.textViewExampleSplash);

        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            public void run() {
                SplashScreenActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                    }
                });
            }
        }, 5000);
    }
}
