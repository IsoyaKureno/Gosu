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
import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends AppCompatActivity {
    GameModel game = new GameModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Button buttonMutate = findViewById(R.id.buttonMutate);
        Button buttonPlay = findViewById(R.id.buttonPlay);
        Button buttonPass = findViewById(R.id.buttonPass);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        String json = sharedPreferences.getString("Deck", "");
        Type type = new TypeToken<List<CardModel>>(){}.getType();
        final List<CardModel> deck = new Gson().fromJson(json, type);

        final RecyclerView view_list_card = findViewById(R.id.view_list_card);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),5);
        view_list_card.setLayoutManager(layoutManager);
        view_list_card.setItemAnimator(new DefaultItemAnimator());

        if (game.someoneWin(deck)){
            buttonMutate.setVisibility(View.INVISIBLE);
            buttonPlay.setVisibility(View.INVISIBLE);
            buttonPass.setVisibility(View.INVISIBLE);
        }

        buttonMutate.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                //TODO : Mutate
                endTurn(deck,view_list_card);
            }
        });
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                //TODO : Play
                game.draw(game.currentPlayer);
                endTurn(deck,view_list_card);
            }
        });
        buttonPass.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                //TODO : Pass
                endTurn(deck,view_list_card);
            }
        });
    }

    public void endTurn(List<CardModel> deck, RecyclerView view_list_card){
        game.turn++;
        game.currentPlayer = (game.turn-1)%game.players.size();

        List<Integer> hand = game.players.get(game.currentPlayer).getHand();
        List<CardModel> handOfCards = new ArrayList<>();
        for (int idCard : hand){handOfCards.add(deck.get(idCard));}
        view_list_card.setAdapter(new ListCardAdapter(handOfCards));
    }
}
