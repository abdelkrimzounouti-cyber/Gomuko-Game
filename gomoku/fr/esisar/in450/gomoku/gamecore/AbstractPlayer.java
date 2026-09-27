package fr.esisar.in450.gomoku.gamecore;

import fr.esisar.in450.gomoku.gamecore.enums.CellColor;
import fr.esisar.in450.gomoku.gamecore.model.Coords;
import fr.esisar.in450.gomoku.gamecore.model.GomokuBoard;



/**
 * Représente un joueur de Gomoku  
 * */
public abstract class AbstractPlayer 
{
	protected CellColor playerColor;
	
	protected GomokuBoard board;
	
	
	/**
	 * Permet d'indiquer au joueur quelle sera sa couleur 
	 */
	final public void setColor(CellColor playerColor)
	{
		this.playerColor = playerColor;
	}
	

	final public CellColor getColor()
	{
		return playerColor;
	}
	
	/**
	 * Permet d'indiquer au joueur le plateau
	 */
	final public void setBoard(GomokuBoard board)
	{
		this.board = board;
	}
	
	
    /**
     * Permet à un joueur de jouer un coup
     */
    public abstract Coords play();
}
