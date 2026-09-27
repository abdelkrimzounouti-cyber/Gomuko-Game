package fr.esisar.in450.gomoku.gamecore.model;

import fr.esisar.in450.gomoku.gamecore.enums.WinnerState;

public class TestGomokuBoard
{
	public static void main(String[] args)
	{
		new TestGomokuBoard().test();
	}

	private void test()
	{
		String s = 	"               \n"+
					"               \n"+
					"WWWW           \n"+
					"               \n"+
					"               \n"+
					"       B       \n"+
					"       B       \n"+
					"               \n"+
					"               \n"+
					"               \n"+
					"               \n"+
					" W             \n"+
					" W             \n"+
					"               \n"+
					"               \n";
		
		
		check(s,WinnerState.NONE);
		
		
		s = 	"W              \n"+
				" W             \n"+
				"  W            \n"+
				"   W           \n"+
				"    W          \n"+
				"       B       \n"+
				"       B       \n"+
				"               \n"+
				"               \n"+
				"               \n"+
				"               \n"+
				" W             \n"+
				" W             \n"+
				"               \n"+
				"               \n";
	
	
		check(s,WinnerState.WHITE);
		
		
		
		s = 	"    B          \n"+
				"   B           \n"+
				"  B            \n"+
				" B             \n"+
				"B              \n"+
				"       B       \n"+
				"       B       \n"+
				"               \n"+
				"               \n"+
				"               \n"+
				"               \n"+
				" W             \n"+
				" W             \n"+
				"               \n"+
				"               \n";
	
	
		check(s,WinnerState.BLACK);
		

		
		
	}

	private void check(String s, WinnerState expected)
	{
		GomokuBoard board = GomokuBoard.fromString(s);
		WinnerState actual = board.getWinnerState();
		
		if (actual!=expected)
		{
			throw new RuntimeException("Erreur actual = "+actual+" expected = "+expected);
		}
	}
}
