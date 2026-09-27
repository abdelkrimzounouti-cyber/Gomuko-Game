package fr.esisar.in450.gomoku.player;

import fr.esisar.in450.gomoku.gamecore.AbstractPlayer;
import fr.esisar.in450.gomoku.gamecore.model.Coords;


/** 
 * IA très naive, qui place ses pierres à proximité des pierres déjà placées
 */
public class BasicAIPlayer extends AbstractPlayer
{
	@Override
	public Coords play()
	{
		// On recherche une cellule vide à côté d'une de nos pierres , en privilegiant le centre
		for (int i = 0; i < board.SIZE; i++)
		{
			for (int j = 0; j < board.SIZE; j++)
			{	
				int k = ( (board.SIZE-1)/2+i) % board.SIZE;
				int l = ( (board.SIZE-1)/2+j) % board.SIZE;
				
				if (isMatching(k,l))
				{
					return new Coords(k,l);
				}
			}
		}
		
		// Si on n'a pas trouvé : on retourne n'importe quelle cellule vide, en privilegiant le centre
		for (int i = 0; i < board.SIZE; i++)
		{
			for (int j = 0; j < board.SIZE; j++)
			{	
				int k = ( (board.SIZE-1)/2+i) % board.SIZE;
				int l = ( (board.SIZE-1)/2+j) % board.SIZE;
				
				if (board.getCellColor(k, l)==null)
				{
					return new Coords(k,l);
				}
			}
		}
		
		// Le tableau est plein : pas de solution 
		throw new RuntimeException();
	}

	/**
	 * Retourne true si la cellule i,j est vide et qu'une cellule adjacente contient 
	 * une pierre de la couleur de ce joueur  
	 */
	private boolean isMatching(int i, int j)
	{
		// On verifie si la case est vide 
		if (board.getCellColor(i, j)!=null)
		{
			return false;
		}
				
		// Horizontal
		if (isMyStone(i-1,j)) return true;
		if (isMyStone(i+1,j)) return true;
		
		// Vertical
		if (isMyStone(i,j-1)) return true;
		if (isMyStone(i,j+1)) return true;
		
		// Diagonal
		if (isMyStone(i-1,j-1)) return true;
		if (isMyStone(i-1,j+1)) return true;
		if (isMyStone(i+1,j-1)) return true;
		if (isMyStone(i+1,j+1)) return true;
		
		return false;
	}

	/**
	 * Retourne true si i,j sont des coordonnées valides et si 
	 * la cellule i,j contient une pierre de de la couleur de ce joueur  
	 * 
	 */
	private boolean isMyStone(int i, int j)
	{
		if (isValid(i) && isValid(j))
		{
			return board.getCellColor(i, j)==playerColor;
		}
		else
		{
			return false;
		}
	}

	private boolean isValid(int rowOrCol)
	{
		return rowOrCol>=0 && rowOrCol <board.SIZE;
	}

	
}
