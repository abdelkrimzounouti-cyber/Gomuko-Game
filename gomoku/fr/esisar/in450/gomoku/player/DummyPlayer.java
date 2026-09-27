package fr.esisar.in450.gomoku.player;

import fr.esisar.in450.gomoku.gamecore.AbstractPlayer;
import fr.esisar.in450.gomoku.gamecore.model.Coords;


/** 
 * IA buggée qui place son pion toujours au même endroit 
 */
public class DummyPlayer extends AbstractPlayer
{
	@Override
	public Coords play()
	{
		return new Coords(5,5);
	}
}
