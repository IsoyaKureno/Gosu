package fr.wcs.gosu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ListCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_card);

        RecyclerView view_list_card = findViewById(R.id.view_list_card);
        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(getApplicationContext(),3);
        view_list_card.setLayoutManager(layoutManager);
        view_list_card.setItemAnimator(new DefaultItemAnimator());

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("DECK", "");
        Type type = new TypeToken<List<CardModel>>(){}.getType();
        List<CardModel> deck = gson.fromJson(json,type);

        view_list_card.setAdapter(new ListCardAdapter(deck));
    }

}
