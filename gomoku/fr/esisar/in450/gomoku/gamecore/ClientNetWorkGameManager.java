package fr.esisar.in450.gomoku.gamecore;

import fr.esisar.in450.gomoku.gamecore.enums.CellColor;
import fr.esisar.in450.gomoku.gamecore.enums.WinnerState;
import fr.esisar.in450.gomoku.gamecore.model.Coords;
import fr.esisar.in450.gomoku.gamecore.model.GomokuBoard;
import fr.esisar.in450.gomoku.gamecore.network.GomokuProxy;
import fr.esisar.in450.gomoku.gamecore.network.NetworkPlayer;


public class ClientNetWorkGameManager 
{
	
	public void startMatches(AbstractPlayer player, int nbGames,String ipServer,String teamName)
	{
		System.out.println("Le nom de votre équipe est : "+teamName);
		System.out.println("Tentative de connexion au serveur "+ipServer+" ...");
		GomokuProxy proxy = new GomokuProxy(ipServer);
		proxy.connect();
		System.out.println("Connexion réussie, attente du joueur adverse ...");
		
		
		int realNbGames = proxy.startAllGames(nbGames,teamName);
		System.out.println("Début du tournoi. Nb de matchs : "+realNbGames);
		
		for (int i = 0; i < realNbGames; i++)
		{
			System.out.println("=============================================");
			System.out.println("Démarrage du match "+(i+1));
			performOneGame(player,proxy);
		}
	}
	
    
   
    public WinnerState performOneGame(AbstractPlayer player, GomokuProxy proxy)
    {
        GomokuBoard board = new GomokuBoard();
        AbstractPlayer player1;
        AbstractPlayer player2;
        
        CellColor localCellColor = proxy.startOneGame();
        
    	
		System.out.println("Sur ce match, vous jouez en tant que "+localCellColor);
    	System.out.println("=============================================");
		
        
        if (localCellColor==CellColor.WHITE)
        {
        	player1 = player;
        	player2 = new NetworkPlayer(proxy);
        }
        else
        {
        	player1 = new NetworkPlayer(proxy);
        	player2 = player;
        }
        
        // Les Blancs commencent 
        player1.setColor(CellColor.WHITE);
        player2.setColor(CellColor.BLACK);
        player1.setBoard(board);
        player2.setBoard(board);
        
        
        WinnerState winnerState = WinnerState.NONE;
        AbstractPlayer currentPlayer = player1;

        int roundCount = 0;

        long player1TotalPlayTime = 0;
        long player2TotalPlayTime = 0;
        long player1LongestPlayTime = 0;
        long player2LongestPlayTime = 0;
        long player1ShortestPlayTime = Long.MAX_VALUE;
        long player2ShortestPlayTime = Long.MAX_VALUE;
        int player1MoveCount = 0;
        int player2MoveCount = 0;
        

        // Tant que la partie n'est pas finie
        while (winnerState == WinnerState.NONE)
        { 
            roundCount++;
            
            // On affiche le plateau 
            board.print();
            System.out.println("Tour " + roundCount + ": " + currentPlayer.getColor());
            
            // On demande au joueur courant de jouer en mesurant le temps de jeu 
            long startTime = System.currentTimeMillis();
            Coords move = currentPlayer.play();
            long moveDuration = System.currentTimeMillis() - startTime;

            // Mise à jour des statistiques
            if (currentPlayer == player1)
            {
                player1TotalPlayTime += moveDuration;
                player1LongestPlayTime = Math.max(player1LongestPlayTime, moveDuration);
                player1ShortestPlayTime = Math.min(player1ShortestPlayTime, moveDuration);
                player1MoveCount++;
            }
            else
            {
                player2TotalPlayTime += moveDuration;
                player2LongestPlayTime = Math.max(player2LongestPlayTime, moveDuration);
                player2ShortestPlayTime = Math.min(player2ShortestPlayTime, moveDuration);
                player2MoveCount++;
            }

            // Jouer le coup sur le plateau 
            board.setCellColor(move.row, move.col, currentPlayer.getColor());
            System.out.println("Coup joué par les "+currentPlayer.getColor()+" Ligne: " + (move.row+1)+" Colonne: " + (move.col+1)+" en "+moveDuration+" ms");
            System.out.println();
            System.out.println();
            
            // On avertit le serveur principal si nécessaire 
            if ((currentPlayer instanceof NetworkPlayer)==false)
            {
            	proxy.play(move.row, move.col);
            }
            
            // On récupère l'état du plateau 
            winnerState = proxy.getWinnerState();

            // Changer de joueur
            currentPlayer = currentPlayer == player1 ? player2 : player1; 
        }

        // Fin de la partie
        
        // On affiche le plateau 
        board.print();
        
        if (winnerState == WinnerState.TIE)
        {
        	System.out.println("Égalité !");
        }
        else
        {	
        	System.out.println("Vainqueur: " + winnerState);
        }

        System.out.println("\nStatistiques :");
        System.out.println("Blanc: " + player1MoveCount + " coups, " + (player1TotalPlayTime/player1MoveCount) + " ms/coup, " + player1LongestPlayTime + " ms (max), " + player1ShortestPlayTime + " ms (min)");
        System.out.println("Noir: " + player2MoveCount + " coups, " + (player2TotalPlayTime/player2MoveCount) + " ms/coup, " + player2LongestPlayTime + " ms (max), " + player2ShortestPlayTime + " ms (min)");

        return winnerState;
    }
      

}
