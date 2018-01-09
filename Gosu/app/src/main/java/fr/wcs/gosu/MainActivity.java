package fr.wcs.gosu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_to_list_card = findViewById(R.id.button_to_list_card);
        button_to_list_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toListCard = new Intent(MainActivity.this,ListCardActivity.class);
                startActivity(toListCard);
            }
        });

        List<CardModel> deck = new ArrayList<>();
        deck.add(new CardModel("Prescient","Ancient",1,1, new String[]{"Advantage"},new String[]{},new String[]{"Draw"}));
        deck.add(new CardModel("Prescient","Ancient",1,1, new String[]{"Advantage"},new String[]{},new String[]{"Draw"}));
        deck.add(new CardModel("PrescientX","Ancient",1,1, new String[]{"Advantage"},new String[]{"Advantage"},new String[]{"Draw"}));

        deck.add(new CardModel("Psi","Ancient",1,2, new String[]{"Vision"},new String[]{},new String[]{"Draw"}));
        deck.add(new CardModel("Psi","Ancient",1,2, new String[]{"Vision"},new String[]{},new String[]{"Draw"}));
        deck.add(new CardModel("PsiX","Ancient",1,2, new String[]{"Vision"},new String[]{"Draw"},new String[]{"Draw"}));

        deck.add(new CardModel("Seer","Ancient",1,2, new String[]{"Draw","Draw"},new String[]{},new String[]{}));
        deck.add(new CardModel("Seer","Ancient",1,2, new String[]{"Draw","Draw"},new String[]{},new String[]{}));
        deck.add(new CardModel("SeerX","Ancient",1,2, new String[]{"Draw","Draw"},new String[]{"Draw","Draw"},new String[]{}));

        deck.add(new CardModel("Adelam","Ancient",2,3, new String[]{"Advantage"},new String[]{},new String[]{"Destruction"}));
        deck.add(new CardModel("Awn","Ancient",2,1, new String[]{"VisionX"},new String[]{},new String[]{}));
        deck.add(new CardModel("Justice","Ancient",2,3, new String[]{"DrawX"},new String[]{},new String[]{"DrawX"}));
        deck.add(new CardModel("Patience","Ancient",2,0, new String[]{"Draw","Draw"},new String[]{"VisionX"},new String[]{}));

        deck.add(new CardModel("Kamakor","Ancient",3,0, new String[]{"DiscardX"},new String[]{"DiscardX"},new String[]{}));
        deck.add(new CardModel("Mind","Ancient",3,3, new String[]{"AdvantageX"},new String[]{},new String[]{}));



        deck.add(new CardModel("Protector","Alpha",1,3, new String[]{"Advantage"},new String[]{},new String[]{"Trap"}));
        deck.add(new CardModel("Protector","Alpha",1,3, new String[]{"Advantage"},new String[]{},new String[]{"Trap"}));
        deck.add(new CardModel("ProtectorX","Alpha",1,3, new String[]{"Advantage"},new String[]{"Advantage"},new String[]{"Trap"}));

        deck.add(new CardModel("Recruiter","Alpha",1,1, new String[]{"Vision"},new String[]{},new String[]{}));
        deck.add(new CardModel("Recruiter","Alpha",1,1, new String[]{"Vision"},new String[]{},new String[]{}));
        deck.add(new CardModel("RecruiterX","Alpha",1,1, new String[]{"Vision"},new String[]{"Vision"},new String[]{}));

        deck.add(new CardModel("Shaman","Alpha",1,2, new String[]{"Return"},new String[]{},new String[]{"Discard"}));
        deck.add(new CardModel("Shaman","Alpha",1,2, new String[]{"Return"},new String[]{},new String[]{"Discard"}));
        deck.add(new CardModel("ShamanX","Alpha",1,2, new String[]{"Return"},new String[]{"Discard"},new String[]{"Discard"}));

        deck.add(new CardModel("Dogopark","Alpha",2,1, new String[]{},new String[]{},new String[]{"DrawX"}));
        deck.add(new CardModel("Favre","Alpha",2,3, new String[]{"DestructionX"},new String[]{},new String[]{"DiscardX"}));
        deck.add(new CardModel("Prahs","Alpha",2,0, new String[]{"Draw","Draw"},new String[]{"ReturnX"},new String[]{}));
        deck.add(new CardModel("Sol","Alpha",2,1, new String[]{"DiscardX"},new String[]{},new String[]{}));

        deck.add(new CardModel("Kork","Alpha",3,3, new String[]{"AdvantageX"},new String[]{},new String[]{}));
        deck.add(new CardModel("Labdad","Alpha",3,0, new String[]{"TrapX"},new String[]{"TrapX"},new String[]{}));



        deck.add(new CardModel("Assassin","Dark",1,3, new String[]{"Destruction"},new String[]{},new String[]{}));
        deck.add(new CardModel("Assassin","Dark",1,3, new String[]{"Destruction"},new String[]{},new String[]{}));
        deck.add(new CardModel("AssassinX","Dark",1,3, new String[]{"Destruction"},new String[]{"Destruction"},new String[]{}));

        deck.add(new CardModel("Avenger","Dark",1,3, new String[]{"Advantage"},new String[]{},new String[]{"TrapX"}));
        deck.add(new CardModel("Avenger","Dark",1,3, new String[]{"Advantage"},new String[]{},new String[]{"TrapX"}));
        deck.add(new CardModel("AvengerX","Dark",1,3, new String[]{"Advantage"},new String[]{"TrapX"},new String[]{"TrapX"}));

        deck.add(new CardModel("Necro","Dark",1,3, new String[]{"Return"},new String[]{},new String[]{"Trap"}));
        deck.add(new CardModel("Necro","Dark",1,3, new String[]{"Return"},new String[]{},new String[]{"Trap"}));
        deck.add(new CardModel("NecroX","Dark",1,3, new String[]{"Return"},new String[]{"Trap"},new String[]{"Trap"}));

        deck.add(new CardModel("Duwal","Dark",2,3, new String[]{"TrapX"},new String[]{},new String[]{"TrapX"}));
        deck.add(new CardModel("Kao","Dark",2,0, new String[]{"DrawX"},new String[]{"TrapX"},new String[]{}));
        deck.add(new CardModel("Kataclysm","Dark",2,3, new String[]{"Discard","Discard"},new String[]{},new String[]{"Advantage"}));
        deck.add(new CardModel("Trsck","Dark",2,3, new String[]{"DestructionX"},new String[]{},new String[]{"Draw"}));

        deck.add(new CardModel("Higuma","Dark",3,0, new String[]{"VisionX"},new String[]{"VisionX"},new String[]{}));
        deck.add(new CardModel("The Edge","Dark",3,3, new String[]{"AdvantageX"},new String[]{},new String[]{}));



        deck.add(new CardModel("Priest","Méka",1,3, new String[]{"Advantage"},new String[]{},new String[]{"Destruction","Advantage"}));
        deck.add(new CardModel("Priest","Méka",1,3, new String[]{"Advantage"},new String[]{},new String[]{"Destruction","Advantage"}));
        deck.add(new CardModel("PriestX","Méka",1,3, new String[]{"Advantage"},new String[]{"Advantage"},new String[]{"Destruction","Advantage"}));

        deck.add(new CardModel("Sentinel","Méka",1,2, new String[]{"Draw"},new String[]{},new String[]{"Discard"}));
        deck.add(new CardModel("Sentinel","Méka",1,2, new String[]{"Draw"},new String[]{},new String[]{"Discard"}));
        deck.add(new CardModel("SentinelX","Méka",1,2, new String[]{"Draw"},new String[]{"Draw"},new String[]{"Discard"}));

        deck.add(new CardModel("Tinker","Méka",1,1, new String[]{"Return"},new String[]{},new String[]{}));
        deck.add(new CardModel("Tinker","Méka",1,1, new String[]{"Return"},new String[]{},new String[]{}));
        deck.add(new CardModel("TinkerX","Méka",1,1, new String[]{"Return"},new String[]{"Return"},new String[]{}));

        deck.add(new CardModel("Kameo","Méka",2,5, new String[]{"Destruction"},new String[]{},new String[]{"DestructionX"}));
        deck.add(new CardModel("Meow","Méka",2,0, new String[]{},new String[]{"Draw","Draw"},new String[]{}));
        deck.add(new CardModel("MMO Twice","Méka",2,2, new String[]{"Draw","Advantage"},new String[]{},new String[]{"Draw","Advantage"}));
        deck.add(new CardModel("Time","Méka",2,3, new String[]{"Draw","Discard"},new String[]{},new String[]{"Draw","Discard"}));

        deck.add(new CardModel("Blind","Méka",3,0, new String[]{"DrawX"},new String[]{"DrawX"},new String[]{}));
        deck.add(new CardModel("Victhory","Méka",3,3, new String[]{"AdvantageX"},new String[]{},new String[]{}));



        deck.add(new CardModel("A.M.M.O.","Fire",1,2, new String[]{"Draw"},new String[]{},new String[]{"Draw"}));
        deck.add(new CardModel("A.M.M.O.","Fire",1,2, new String[]{"Draw"},new String[]{},new String[]{"Draw"}));
        deck.add(new CardModel("A.M.M.O.X","Fire",1,2, new String[]{"Draw"},new String[]{"Draw"},new String[]{"Draw"}));

        deck.add(new CardModel("Explosix","Fire",1,3, new String[]{"Advantage"},new String[]{},new String[]{"DestructionX"}));
        deck.add(new CardModel("Explosix","Fire",1,3, new String[]{"Advantage"},new String[]{},new String[]{"DestructionX"}));
        deck.add(new CardModel("ExplosixX","Fire",1,3, new String[]{"Advantage"},new String[]{"Advantage"},new String[]{"DestructionX"}));

        deck.add(new CardModel("Sniper","Fire",1,2, new String[]{"Destruction"},new String[]{},new String[]{}));
        deck.add(new CardModel("Sniper","Fire",1,2, new String[]{"Destruction"},new String[]{},new String[]{}));
        deck.add(new CardModel("SniperX","Fire",1,2, new String[]{"Destruction"},new String[]{"Destruction"},new String[]{}));

        deck.add(new CardModel("Dalister","Fire",2,4, new String[]{"DestructionX"},new String[]{},new String[]{"Discard","Discard"}));
        deck.add(new CardModel("Dom","Fire",2,0, new String[]{},new String[]{"Discard","Discard"},new String[]{}));
        deck.add(new CardModel("Dynamite","Fire",2,3, new String[]{"Return","Return"},new String[]{},new String[]{"Advantage"}));
        deck.add(new CardModel("Erxast","Fire",2,3, new String[]{"Draw","Discard"},new String[]{},new String[]{"Draw","Discard"}));

        deck.add(new CardModel("Dawn","Fire",3,0, new String[]{"ReturnX"},new String[]{"ReturnX"},new String[]{}));
        deck.add(new CardModel("Lipsy","Fire",3,3, new String[]{"AdvantageX"},new String[]{},new String[]{}));


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(deck);
        editor.putString("DECK", json);
        editor.apply();
    }
}
