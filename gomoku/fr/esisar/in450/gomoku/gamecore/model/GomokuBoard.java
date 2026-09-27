package fr.esisar.in450.gomoku.gamecore.model;
import java.util.ArrayList;
import java.util.List;

import fr.esisar.in450.gomoku.gamecore.enums.CellColor;
import fr.esisar.in450.gomoku.gamecore.enums.WinnerState;
/**
 * Modelisation d'un plateau de Gomoku
 *
 * Un plateau de Gomuku est composé de SIZE * SIZE cellules (cells)
 *
 * Chaque cellule est soit vide (null) , soit d'une couleur (CellColor)  
 */
public class GomokuBoard
{
	// Taille du plateau
	public final int SIZE = 15;

	// Indice 0 : la ligne
	// Indice 1 : la colonne
	public CellColor [][] cells;

	/**
	 * Création d'un plateau de Gomoku vide
	 * Toutes les cellules sont vides (null)
	 */
	public GomokuBoard()
	{
		cells = new CellColor[SIZE][SIZE];
	}

	/**
	 * Permet d'ajouter une pierre sur le plateau
	 *
	 * Pour enlenver une pierre, mettre CellColor à null
	 */
	public void setCellColor(int row, int col, CellColor cellColor)
	{
		cells[row][col] = cellColor;
	}

	/**
	 * Permet de connaitre la couleur d'une case du plateau
	 * Retourne null si la case est vide
	 */
	public CellColor getCellColor(int row, int col)
	{
		return cells[row][col];
	}

	/**
	 * Retourne true si toutes les cellules sont vides
	 */
	public boolean isEmpty()
	{
		for (int i = 0; i < SIZE; i++)
		{
			for (int j = 0; j < SIZE; j++)
			{
				if (cells[i][j]!=null)
				{
					return false;
				}
			}
		}
		return true;
	}


	/**
	 * Retourne true si au moins une cellule est vide
	 */
	public boolean hasAnEmptyCell() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (cells[i][j] == null) {
					return true;
				}
			}
		}
		return false;
	}
	public boolean coorempty(int row,int col){
		return cells[row][col] == null;
	}
	/**
	 * Obtenir l'état du plateau
	 */
	public WinnerState getWinnerState()
	{
		// Test des lignes
		for (int i = 0; i < SIZE; i++)
		{
			WinnerState winner = hasWinner(getLine(i));
			if (winner!=null)
			{
				return winner;
			}
		}

		// Test des colonnes
		for (int i = 0; i < SIZE; i++)
		{
			WinnerState winner = hasWinner(getColonne(i));
			if (winner!=null)
			{
				return winner;
			}
		}

		// Test des diagonales ↘
		List<String> diags = getAllDiagDesc();
		for (String diag : diags)
		{
			WinnerState winner = hasWinner(diag);
			if (winner!=null)
			{
				return winner;
			}
		}

		// Test des diagonales ↗
		diags = getAllDiagAsc();
		for (String diag : diags)
		{
			WinnerState winner = hasWinner(diag);
			if (winner!=null)
			{
				return winner;
			}
		}


		// Test du tie
		if (hasAnEmptyCell()==false)
		{
			return WinnerState.TIE;
		}
		else
		{
			return WinnerState.NONE;
		}
	}

	private String getLine(int i)
	{
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < SIZE; j++)
		{
			sb.append(getCellAsString(i,j));
		}
		return sb.toString();
	}

	private String getColonne(int j)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < SIZE; i++)
		{
			sb.append(getCellAsString(i,j));
		}
		return sb.toString();
	}

	private List<String> getAllDiagDesc()
	{
		List<String> res = new ArrayList<String>();

		// Les diagonales ↘ partant de l'axe horizontal du haut
		for (int len = 5; len <= SIZE; len++)
		{
			res.add(getDiag(len, 0, SIZE-len, 1, 1));
		}

		// Les diagonales ↘ partant de l'axe vertical de gauche
		for (int len = 5; len <= SIZE-1; len++)
		{
			res.add(getDiag(len, SIZE-len, 0 , 1, 1));
		}

		return res;
	}

	private List<String> getAllDiagAsc()
	{
		List<String> res = new ArrayList<String>();

		// Les diagonales ↗ partant de l'axe horizontal du bas
		for (int len = 5; len <= SIZE; len++)
		{
			res.add(getDiag(len, SIZE-1, SIZE-len, -1, 1));
		}

		// Les diagonales ↗ partant de l'axe vertical de gauche
		for (int len = 5; len <= SIZE-1; len++)
		{
			res.add(getDiag(len, len-1, 0 , -1, 1));
		}

		return res;
	}

	private String getDiag(int len,int startRow,int startCol,int dx,int dy)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++)
		{
			sb.append(getCellAsString(startRow+dx*i,startCol+dy*i));
		}
		return sb.toString();
	}


	private String getCellAsString(int i, int j)
	{
		CellColor cellColor = getCellColor(i, j);

		if (cellColor==null)
		{
			return " ";
		}
		else if (cellColor==CellColor.BLACK)
		{
			return "B";
		}
		else if (cellColor==CellColor.WHITE)
		{
			return "W";
		}
		else
		{
			throw new RuntimeException();
		}
	}

	private WinnerState hasWinner(String st)
	{
		if (st.indexOf("WWWWW")!=-1)
		{
			return WinnerState.WHITE;
		}

		if (st.indexOf("BBBBB")!=-1)
		{
			return WinnerState.BLACK;
		}
		return null;
	}



	/**
	 * Affichage du plateau à l'écran
	 */
	public void print()
	{
		System.out.print("   ");
		for (int i = 0; i < SIZE; i++)
		{
			System.out.print(String.format("%02d ", i+1)); // Afficher le numéro des colonnes
		}
		System.out.println();
		for (int i = 0; i < SIZE; i++)
		{
			System.out.print(String.format("%02d ", (i+1))); // Afficher le numéro de la ligne
			for (int j = 0; j < SIZE; j++)
			{
				displayCell(cells[i][j]);
			}
			System.out.println();
		}
	}


	private void displayCell(CellColor cellColor)
	{
		if (cellColor==null)
		{
			System.out.print("-  "); //
		}
		else if (cellColor==CellColor.BLACK)
		{
			System.out.print("B  "); // ■
		}
		else if (cellColor==CellColor.WHITE)
		{
			System.out.print("W  "); // □
		}
		else
		{
			throw new RuntimeException();
		}
	}


	static public GomokuBoard fromString(String in)
	{
		GomokuBoard board = new GomokuBoard();
		String[] ls = in.split("\n");
		for (int i = 0; i < ls.length; i++)
		{
			String s = ls[i];
			for (int j = 0; j < s.length(); j++)
			{
				char c = s.charAt(j);
				if (c=='W')
				{
					board.setCellColor(i, j, CellColor.WHITE);
				}
				if (c=='B')
				{
					board.setCellColor(i, j, CellColor.BLACK);
				}
			}
		}
		return board;
	}
}
