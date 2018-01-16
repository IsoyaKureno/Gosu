package fr.wcs.gosu;

import java.util.ArrayList;
import java.util.List;

public class PlayerModel {
    private String name;
    private List<Integer> hand;
    private int nbVictories,nbAction;
    private boolean advantage;
    private int[][] army;

    PlayerModel(String name){
        this.name = name;
        this.hand = new ArrayList<>();
        this.nbVictories = 0;
        this.nbAction = 1;
        this.advantage = false;

        int[][] anArmy = new int[3][5];
        for (int rank = 0; rank<anArmy.length; rank++) {
            for (int column = 0; column<anArmy[rank].length; column++) {
                anArmy[rank][column] = 404;}}
        this.army = anArmy;
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public List<Integer> getHand() {return hand;}
    public boolean badHand(List<CardModel> deck){
        boolean noRank1 = true;
        for (int idCard : this.hand){
            if (deck.get(idCard).getRank()==1){
                noRank1 = false;}}
        return noRank1;
    }

    public int getNbVictories() {return nbVictories;}
    public void addVictory() {this.nbVictories++;}

    public int getNbAction() {return nbAction;}
    public void addNbAction() {this.nbAction++;}

    public boolean getAdvantage() {return advantage;}
    public void setAdvantage(boolean advantage) {this.advantage = advantage;}

    public int[][] getArmy() {return army;}
    public void setArmy(int[][] army) {this.army = army;}
}
