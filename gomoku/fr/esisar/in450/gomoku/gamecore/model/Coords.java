package fr.esisar.in450.gomoku.gamecore.model;

/**
 * Coordonnées sur le plateau de jeu
 * */
public class Coords
{
    // Numéro de ligne depuis le haut à partir de 0
    public int row;
 
    // Numéro de colonne depuis la gauche à partir de 0
    public int col;

    public Coords()
    {
    	row = -1;
        col = -1;
    }

    public Coords(int row, int column)
    {
        this.row = row;
        this.col = column;
    }
    
}
