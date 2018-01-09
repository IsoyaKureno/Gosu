package fr.wcs.gosu;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class PlayActivity extends AppCompatActivity {
    GameModel game = new GameModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        String json = sharedPreferences.getString("Deck", "");
        Type type = new TypeToken<List<CardModel>>(){}.getType();
        List<CardModel> deck = new Gson().fromJson(json, type);

        Button buttonMutate = findViewById(R.id.buttonMutate);
        Button buttonPlay = findViewById(R.id.buttonPlay);
        Button buttonPass = findViewById(R.id.buttonPass);

        RecyclerView view_list_card = findViewById(R.id.view_list_card);
        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(getApplicationContext(),5);
        view_list_card.setLayoutManager(layoutManager);
        view_list_card.setItemAnimator(new DefaultItemAnimator());

        //TODO : view_list_card.setAdapter(new ListCardAdapter(new List<CardModel>(){}));

        while (!game.someoneWin(deck)){
            buttonMutate.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) {
                    //TODO
                    endTurn();
                }
            });
            buttonPlay.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) {
                    //TODO
                    endTurn();
                }
            });
            buttonPass.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) {
                    //TODO
                    endTurn();
                }
            });
        }
    }

    public void endTurn(){
        game.turn++;
        game.currentPlayer = (game.turn-1)%game.players.size();
    }
}
