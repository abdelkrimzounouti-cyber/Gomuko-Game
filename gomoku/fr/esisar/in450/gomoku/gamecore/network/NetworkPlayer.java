package fr.esisar.in450.gomoku.gamecore.network;

import fr.esisar.in450.gomoku.gamecore.AbstractPlayer;
import fr.esisar.in450.gomoku.gamecore.model.Coords;


/** 
 * Représente un joueur de Gomoku en réseau 
 */
public class NetworkPlayer extends AbstractPlayer
{
	private GomokuProxy proxy;

	public NetworkPlayer(GomokuProxy proxy)
	{
		this.proxy = proxy;
	}

	@Override
	public Coords play()
	{
		System.out.println("Attente de la réponse du joueur adverse ...");
		return proxy.waitOpposingPlayer();
	}

	
}
