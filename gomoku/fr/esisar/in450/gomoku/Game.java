package fr.esisar.in450.gomoku;

import java.io.IOException;

import fr.esisar.in450.gomoku.gamecore.ClientNetWorkGameManager;
import fr.esisar.in450.gomoku.gamecore.LocalGameManager;
import fr.esisar.in450.gomoku.gamecore.server.GomokuServer;
import fr.esisar.in450.gomoku.player.*;


/**
 * Cette classe permet de lancer le jeu de Gomoku 
 * Merci de lire les commentaires des fonctions ci dessous pour voir 
 * les différents cas d'usage 
 */
public class Game
{
	public static void main(String[] args)
	{
		ex1();
		// ex2();
		// ...
	}
	
	/**
	 * Exemple 1 : permet de faire une partie en local entre une IA basique et un joueur humain 
	 */
	public static void ex1()
	{
		new LocalGameManager().startMatch(new Minmax(), new Minmax());
	}
	
	
	/**
	 * Exemple 2 : permet de faire une partie en local entre deux IA basiques  
	 */
	public static void ex2()
	{
		new LocalGameManager().startMatch(new BasicAIPlayer(), new BasicAIPlayer());
	}
	
	
	/**
	 * Exemple 3
	 * 
	 * Vous souhaitez faire un match en réseau avec un autre binôme de TP
	 * 
	 * On considère que vous disposez de 3 machines : 
	 * la machine avec l'IP 192.168.130.9 est le serveur 
	 * la machine avec l'IP 192.168.130.1 est le joueur 1 
	 * la machine avec l'IP 192.168.130.2 est le joueur 2
	 *  
	 * voici les étapes à réaliser  
	 * 
	 * Etape 0 : sur la machine serveur (192.168.130.9), vous lancez le code suivant  
	 * 		GomokuServer.main(null);
	 * 
	 * Etape 1 : sur la machine joueur 1 (192.168.130.1), vous lancez le code suivant  
	 * 		new ClientNetWorkGameManager().startMatches(new BasicAIPlayer(),4,"192.168.130.9","equipe 1");
	 * Ceci va lancer un tournoi de 4 matchs, qui démarrera dés que le joueur 2 va se connecter  
	 * 
	 * Etape 2 : sur la machine joueur 2 (192.168.130.2), vous lancez le code suivant  
	 * 		new ClientNetWorkGameManager().startMatches(new BasicAIPlayer(),4,"192.168.130.9","equipe 2");
	 *  
	 * Le tournoi démarre, vous pouvez voir son avancement à la fois sur les clients (joueur1 et jouer2) 
	 * et sur le serveur 
	 * 
	 */

	public static void etape0() throws IOException
	{
		GomokuServer.main(null);
	}
	
	public static void etape1() throws IOException
	{
		new ClientNetWorkGameManager().startMatches(new BasicAIPlayer(),4,"192.168.130.9","equipe 1");
	}
	
	public static void etape2() throws IOException
	{
		new ClientNetWorkGameManager().startMatches(new DummyPlayer(),4,"192.168.130.9","equipe 2");
	}


}
