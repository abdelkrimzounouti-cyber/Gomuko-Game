package fr.esisar.in450.gomoku.player;

import java.util.Scanner;

import fr.esisar.in450.gomoku.gamecore.AbstractPlayer;
import fr.esisar.in450.gomoku.gamecore.model.Coords;


/** 
 * Représente un joueur de Gomoku humain 
 */
public class HumanPlayer extends AbstractPlayer
{
	private Scanner scanner;

	public HumanPlayer()
	{
		scanner = new Scanner(System.in);
	}
	

	@Override
	public Coords play()
	{
		Coords coords = new Coords();

		while (coords.row == -1)
		{ // Tant que la ligne n'est pas définie
			try
			{
				System.out.print("Ligne: ");
				coords.row = scanner.nextInt()-1;

				if (!isValid(coords.row))
				{
					System.out.println("Cette ligne n'existe pas.");
					coords.row = -1; // Réinitialiser la ligne
				}
			} 
			catch (Exception e)
			{
				System.out.println("Valeur invalide.");
			}
		}

		while (coords.col== -1)
		{
			try
			{
				System.out.print("Colonne: ");
				coords.col = scanner.nextInt()-1;

				if (!isValid(coords.col))
				{
					System.out.println("Cette colonne n'existe pas.");
					coords.col = -1; // Réinitialiser la colonne
				} 
				else if (board.getCellColor(coords.row,coords.col) != null)
				{
					System.out.println("Cette case est déjà occupée.");
					coords.col = -1; // Réinitialiser la colonne
				}
			} 
			catch (Exception e)
			{
				System.out.println("Valeur invalide.");
			}
		}

		return coords;
	}

	private boolean isValid(int rowOrCol)
	{
		return rowOrCol>=0 && rowOrCol <board.SIZE;
	}

	
}
