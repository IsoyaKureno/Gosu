package fr.wcs.gosu;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    List<Integer> pile,grave;
    List<PlayerModel> players;
    int turn,currentPlayer;
    GameModel(){
        this.pile = new ArrayList<>();
        for(int i=0;i<75;i++){
            this.pile.add(i);
        }
        this.grave = new ArrayList<>();
        this.players = new ArrayList<>();
        this.players.add(new PlayerModel("player1"));
        this.players.add(new PlayerModel("player2"));
        this.turn = 1;
        this.currentPlayer = 0;
    }

    public List<Integer> getGrave() {return grave;}

    public void shuffle(){
        List<Integer> oldDeck = this.pile;
        List<Integer> newDeck = new ArrayList<>();
        while(!oldDeck.isEmpty()){
            int random = (int)(Math.random()*oldDeck.size());
            int idCard = oldDeck.get(random);
            oldDeck.remove(random);
            newDeck.add(idCard);
        }
        this.pile = newDeck;
    }

    //TODO : fire effects

    public void destroy(Integer numberPlayer, Integer rankGoblin){
        int[][] army = this.players.get(numberPlayer).getArmy();
        int colunm = 4;
        while (army[rankGoblin][colunm]==404 && colunm>=0){
            colunm--;}
        if(colunm>=0){
            int idCard = army[rankGoblin][colunm]%1000;
            this.grave.add(idCard);
            army[rankGoblin][colunm]=404;
        }
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

    public void draw(Integer numberPlayer){
        int idCard = this.pile.get(0);
        this.pile.remove(0);
        this.players.get(numberPlayer).getHand().add(idCard);
    }

    //TODO : Vision

    //TODO : Return

    //TODO : Advantage
    public void advantage(Integer numberPlayer) {
        for (int i=0; i<players.size(); i++){
            this.players.get(i).setAdvantage(false);}
        this.players.get(numberPlayer).setAdvantage(true);
        this.players.get(numberPlayer).addNbAction();
    }

    public boolean someoneWin(List<CardModel> deck){
        for (PlayerModel currentPlayer:this.players) {
            int[][] army = currentPlayer.getArmy();
            boolean fullArmy = true, nineOfOne = false;
            int[] cptRank = new int[]{0,0,0,0,0};

            for (int[] anArmy : army) {
                for (int column : anArmy) {
                    int idCard = anArmy[column];
                    if (idCard == 404) {fullArmy = false;}
                    if (idCard < 75) {
                        switch (deck.get(idCard).getClan()) {
                            case "Ancient":cptRank[0]++;break;
                            case "Alpha":cptRank[1]++;break;
                            case "Dark":cptRank[2]++;break;
                            case "Méka":cptRank[3]++;break;
                            case "Fire":cptRank[4]++;break;
                            default:break;}}}
            }

            for (int i : cptRank) {if(i>=9){nineOfOne = true;}}

            if (fullArmy||nineOfOne||currentPlayer.getNbVictories()==3){return true;}
        }
        return false;
    }
}
