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

        int[][] a = new int[3][5];
        for (int rank=0; rank<a.length; rank++) {
            for (int column : a[rank]) {
                a[rank][column] = 404;}}
        this.army = a;
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public List<Integer> getHand() {return hand;}

    public int getNbVictories() {return nbVictories;}
    public void addVictory(int nbVictories) {this.nbVictories++;}

    public int getNbAction() {return nbAction;}
    public void addNbAction() {this.nbAction++;}

    public boolean getAdvantage() {return advantage;}
    public void setAdvantage(boolean advantage) {this.advantage = advantage;}

    public int[][] getArmy() {return army;}
    public void setArmy(int[][] army) {this.army = army;}
}
