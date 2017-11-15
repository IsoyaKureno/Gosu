package fr.wildcodeschool.florianscoffee;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView _imageViewLogoMenu = (ImageView) findViewById(R.id.imageViewLogoMenu);
        TextView _textViewDescriptionMenu = (TextView) findViewById(R.id.textViewDescriptionMenu);
        TextView _textViewShowMap = (TextView) findViewById(R.id.textViewShowMap);
        Button _buttonTESTPrivatisation = (Button) findViewById(R.id.buttonTESTPrivatisation);

        _textViewShowMap.setPaintFlags(_textViewShowMap.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        _textViewShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PrivatisationUserActivity.class);
                startActivity(intent);
            }
        });
    }
}
