package fr.wildcodeschool.florianscoffee;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView _imageViewLogoMenu = (ImageView) findViewById(R.id.imageViewLogoMenu);
        TextView _textViewDescriptionMenu = (TextView) findViewById(R.id.textViewDescriptionMenu);

        _textViewDescriptionMenu.setPaintFlags(_textViewDescriptionMenu.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    }
}
