package fr.esisar.in450.gomoku.player;

import fr.esisar.in450.gomoku.gamecore.AbstractPlayer;
import fr.esisar.in450.gomoku.gamecore.model.Coords;

public class RansomPlayer extends AbstractPlayer {
    public Coords play(){

        for(int i = 0; i<board.SIZE;  i++){
            for(int j = 0; j<board.SIZE; j++){
                int k = (int)(Math.random()*board.SIZE);
                int l = (int)(Math.random()*board.SIZE);
                return new Coords(k,l);
            }
        }
        throw new RuntimeException();
    }

}
