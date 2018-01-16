package fr.wcs.gosu;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    List<Integer> pile,grave;
    List<PlayerModel> players;
    int turn,currentPlayer;
    boolean isBegin;
    GameModel(){
        this.pile = new ArrayList<>();
        for(int i=0;i<75;i++){this.pile.add(i);}
        this.grave = new ArrayList<>();
        this.players = new ArrayList<>();
        this.players.add(new PlayerModel("player1"));
        this.players.add(new PlayerModel("player2"));
        this.turn = 1;
        this.currentPlayer = 0;
        this.isBegin = true;
    }

    public void shuffle(){
        List<Integer> oldDeck = this.pile;
        List<Integer> newDeck = new ArrayList<>();
        while(!oldDeck.isEmpty()){
            int random = (int)(Math.random()*oldDeck.size());
            int idCard = oldDeck.get(random);
            oldDeck.remove(random);
            newDeck.add(idCard);}
        this.pile = newDeck;
    }

    public void destroy(Integer numberPlayer, Integer rankGoblin){
        int[][] army = this.players.get(numberPlayer).getArmy();
        int colunm = 4;
        while (army[rankGoblin][colunm]==404 && colunm>=0){colunm--;}
        if(colunm>=0){
            int idCard = army[rankGoblin][colunm]%1000;
            this.grave.add(idCard);
            army[rankGoblin][colunm]=404;}
        this.players.get(numberPlayer).setArmy(army);
    }

    public void destroyX(Integer numberPlayer, Integer rankGoblin, Integer column1, Integer column2,
                         Integer numberPlayerDestroy, Integer rankGoblinDestroy){
        int[][] army = this.players.get(numberPlayer).getArmy();
        int idCard = army[rankGoblin][column1];
        army[rankGoblin][column1] = army[rankGoblin][column2];
        army[rankGoblin][column2] = idCard;
        destroy(numberPlayerDestroy,rankGoblinDestroy);
    }

    public void trap(Integer numberPlayer, Integer rankGoblin, Integer columnGoblin){
        int[][] army = this.players.get(numberPlayer).getArmy();
        army[rankGoblin][columnGoblin]+=1000;
        this.players.get(numberPlayer).setArmy(army);
    }

    public void trapX(Integer numberPlayer, Integer rankGoblin, Integer columnGoblin){
        int[][] army = this.players.get(numberPlayer).getArmy();
        if(army[rankGoblin][columnGoblin]>=1000){
            army[rankGoblin][columnGoblin]-=1000;}
        else {army[rankGoblin][columnGoblin]+=1000;}
        this.players.get(numberPlayer).setArmy(army);
    }

    public void discard(Integer numberPlayer, Integer numberCard) {
        int idCard = this.players.get(numberPlayer).getHand().get(numberCard);
        this.players.get(numberPlayer).getHand().remove(numberCard);
        this.grave.add(idCard);
    }

    // TODO : DiscardX
    public void discardX(Integer numberPlayer, Integer numberCard) {
        int idCard = this.players.get(numberPlayer).getHand().get(numberCard);
        this.players.get(numberPlayer).getHand().remove(numberCard);
        this.grave.add(idCard);
    }

    //TODO : Aléa draw
    public void draw(Integer numberPlayer){
        int idCard = this.pile.get(0);
        this.pile.remove(0);
        this.players.get(numberPlayer).getHand().add(idCard);
        if (this.pile.isEmpty()){
            this.pile = this.grave;
            this.grave = new ArrayList<>();
            shuffle();}
    }

    public void drawX(Integer numberPlayer, List<CardModel> deck){
        int[][] army = this.players.get(numberPlayer).getArmy();
        int haveAncient = 0, haveAlpha = 0, haveDark = 0, haveMéka = 0, haveFire = 0;
        for (int[] rankArmy : army) {
            for (int idCard : rankArmy) {
                if (idCard < 404) {
                    switch (deck.get(idCard).getClan()) {
                        case "Ancient": haveAncient = 1; break;
                        case "Alpha": haveAlpha = 1; break;
                        case "Dark": haveDark = 1; break;
                        case "Méka": haveMéka = 1; break;
                        case "Fire": haveFire = 1; break;
                        default: break;}}}}
        int cptArmy = haveAncient + haveAlpha + haveDark + haveMéka + haveFire;
        for (int i=0; i<cptArmy; i++){draw(numberPlayer);}
    }

    //TODO : Vision et VisionX
    public void vision(Integer numberPlayer){}
    public void visionX(Integer numberPlayer){}

    //TODO : Return et ReturnX
    public void returnFromGrave(List<CardModel> deck, Integer idCardPlayed, Integer numberPlayer){
        if (!this.grave.isEmpty()){}
    }
    public void returnFromGraveX(List<CardModel> deck, Integer numberPlayer){
        if (!this.grave.isEmpty()){}
    }

    //TODO : Advantage
    public void advantage(Integer numberPlayer) {
        for (int i=0; i<players.size(); i++){
            this.players.get(i).setAdvantage(false);}
        this.players.get(numberPlayer).setAdvantage(true);
        this.players.get(numberPlayer).addNbAction();
        //TODO : replay
    }
    public void advantageX(Integer numberPlayer) {
        int outsiderVictories = 2;
        for (int i=0; i<players.size(); i++){
            if (this.players.get(i).getNbVictories()<outsiderVictories){
                outsiderVictories = this.players.get(i).getNbVictories();}}
        for (int i=0; i<players.size(); i++){
            if (this.players.get(i).getNbVictories()==outsiderVictories){
                draw(i);draw(i);}}

        advantage(numberPlayer);
    }

    public boolean someoneWin(List<CardModel> deck){
        for (PlayerModel currentPlayer:this.players) {
            int[][] army = currentPlayer.getArmy();
            boolean fullArmy = true, nineOfOne = false;
            int[] cptRank = new int[]{0,0,0,0,0};

            for (int[] rank : army) {
                for (int idCard : rank) {
                    if (idCard == 404) {fullArmy = false;}
                    if (idCard < 75) {
                        switch (deck.get(idCard).getClan()) {
                            case "Ancient": cptRank[0]++; break;
                            case "Alpha": cptRank[1]++; break;
                            case "Dark": cptRank[2]++; break;
                            case "Méka": cptRank[3]++; break;
                            case "Fire": cptRank[4]++; break;
                            default: break;}}}}

            for (int i : cptRank) {if(i>=9){nineOfOne = true;}}

            if (fullArmy||nineOfOne||currentPlayer.getNbVictories()==3){return true;}
        }
        return false;}

    //TODO : BeginGame
    public void beginGame(List<CardModel> deck){
        this.shuffle();
        for (int i=0; i<this.players.size(); i++){
            while (this.players.get(i).getHand().size()<10){draw(i);}
            if (this.players.get(i).badHand(deck)){
                while (this.players.get(i).badHand(deck)){
                    while (this.players.get(i).getHand().size()>0){
                        discard(i,0);}
                    while (this.players.get(i).getHand().size()<10){
                        draw(i);}}}}
        this.players.get(0).setAdvantage(true);
        this.isBegin = false;
    }

    public void endTurn(){
        this.turn++;
        this.currentPlayer = (this.turn-1)%this.players.size();}

    //TODO : EndRound
    public void endRound(List<CardModel> deck){
        List<Integer> powerOfArmy = new ArrayList<>();
        List<Boolean> advantages = new ArrayList<>();
        int bestPower = 0;
        for (int numberPlayer=0; numberPlayer<this.players.size(); numberPlayer++){
            int[][] army = this.players.get(numberPlayer).getArmy();
            int powerArmy = 0;
            for (int[] rankArmy : army) {
                for (int idCard : rankArmy) {
                    if (idCard < 404) {powerArmy+=deck.get(idCard).getPower();}}}
            powerOfArmy.add(powerArmy);
            advantages.add(this.players.get(numberPlayer).getAdvantage());
            if (powerArmy>bestPower){bestPower = powerArmy;}}

        boolean someoneAdvantage = false;
        for (int numberPlayer=0; numberPlayer<this.players.size(); numberPlayer++){
            if (powerOfArmy.get(numberPlayer)==bestPower && advantages.get(numberPlayer)){
                someoneAdvantage = true;
                this.players.get(numberPlayer).addVictory();}}
        if (!someoneAdvantage){
            for (int numberPlayer=0; numberPlayer<this.players.size(); numberPlayer++){
                if (powerOfArmy.get(numberPlayer)==bestPower){
                    this.players.get(numberPlayer).addVictory();}}}

        for (int numberPlayer=0; numberPlayer<this.players.size(); numberPlayer++){
            while (this.players.get(numberPlayer).getHand().size()<(7-this.players.get(numberPlayer).getNbVictories())){
                draw(numberPlayer);}}

        //TODO : advantage -> 1rst player
    }
}
