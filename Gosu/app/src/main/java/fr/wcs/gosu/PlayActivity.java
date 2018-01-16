package fr.wcs.gosu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends AppCompatActivity {
    final Context context = this;
    GameModel game = new GameModel();
    List<CardModel> deck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String json = sharedPreferences.getString("DECK",null);
        Type type = new TypeToken<List<CardModel>>(){}.getType();
        deck = new Gson().fromJson(json, type);

        Button buttonMutate = findViewById(R.id.buttonMutate);
        Button buttonPlay = findViewById(R.id.buttonPlay);
        Button buttonPass = findViewById(R.id.buttonPass);

        if (game.isBegin){game.beginGame(deck);}

        final RecyclerView view_list_card = findViewById(R.id.view_list_card);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),5);
        view_list_card.setLayoutManager(layoutManager);
        view_list_card.setItemAnimator(new DefaultItemAnimator());

        List<Integer> hand = game.players.get(game.currentPlayer).getHand();
        List<CardModel> handOfCards = new ArrayList<>();
        for (int idCard : hand){handOfCards.add(deck.get(idCard));}
        view_list_card.setAdapter(new ListCardAdapter(handOfCards));

        if (game.someoneWin(deck)){
            buttonMutate.setVisibility(View.INVISIBLE);
            buttonPlay.setVisibility(View.INVISIBLE);
            buttonPass.setVisibility(View.INVISIBLE);
        }

        buttonMutate.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                //TODO : Mutate
            }
        });

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                playACard(game.players.get(game.currentPlayer).getHand());
            }
        });

        buttonPass.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Do you really wanna pass your turn ?")
                        .setCancelable(true)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                //TODO : Pass
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {dialog.dismiss();}
                        });
                try {alertDialogBuilder.show();} catch(Exception e){}
            }
        });
    }

    void playACard(List<Integer> hand){
        List<CardModel> handOfCards = new ArrayList<>();
        for (int idCard : hand){handOfCards.add(deck.get(idCard));}

        //TODO : Play (create a dialogFragment)
        Dialog reactionsDialog = new Dialog(context, R.style.Theme_AppCompat_Dialog);
        //reactionsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reactionsDialog.setCancelable(true);
        reactionsDialog.setTitle("Choose a card to play :");
        //reactionsDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(mContext,R.drawable.shape));
        RecyclerView reactionsRecyclerView = new RecyclerView(context);
        reactionsRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context,handOfCards.size());
        reactionsRecyclerView.setLayoutManager(layoutManager);
        reactionsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        reactionsRecyclerView.setAdapter(new ListCardAdapter(handOfCards));

        reactionsDialog.setContentView(reactionsRecyclerView);
        reactionsDialog.show();
    }
}
