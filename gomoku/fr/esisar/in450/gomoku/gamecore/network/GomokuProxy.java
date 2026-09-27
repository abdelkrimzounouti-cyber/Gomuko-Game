package fr.esisar.in450.gomoku.gamecore.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import fr.esisar.in450.gomoku.gamecore.enums.CellColor;
import fr.esisar.in450.gomoku.gamecore.enums.WinnerState;
import fr.esisar.in450.gomoku.gamecore.model.Coords;

public class GomokuProxy
{
	private String ipServer;

	private DataInputStream dis;
	private DataOutputStream dos;
	

	public GomokuProxy(String ipServer)
	{
		this.ipServer = ipServer;
	}
	

	public void connect()
	{
		try
		{
			Socket s = new Socket(ipServer, 7200);
	
			dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(s.getOutputStream());
		} 
		catch (IOException e)
		{
			throw new RuntimeException("Impossible de se connecter au serveur "+ipServer,e);
		}
	}




	public int startAllGames(int nbGames, String teamName)
	{
		GomokuRequest req = new GomokuRequest();
		req.type = RequestResponseType.START_ALL_GAMES;
		req.nbGame = nbGames;
		req.teamName = teamName;
				
		GomokuResponse res = sendRequest(req);
		return res.realNbGames;
		
	}

	
	

	public CellColor startOneGame()
	{
		GomokuRequest req = new GomokuRequest();
		req.type = RequestResponseType.START_ONE_GAME;
		
		GomokuResponse res = sendRequest(req);
		return res.isWhite ? CellColor.WHITE : CellColor.BLACK;
	}


	public void play(int row, int col)
	{
		GomokuRequest req = new GomokuRequest();
		req.type = RequestResponseType.PLAY;
		req.row = row;
		req.col = col;
		
		 sendRequest(req);
	}
	
	public WinnerState getWinnerState()
	{
		GomokuRequest req = new GomokuRequest();
		req.type = RequestResponseType.GET_WINNER_STATE;
		
		GomokuResponse res = sendRequest(req);
		
		if (res.winnerState==0) return WinnerState.NONE;
		if (res.winnerState==1) return WinnerState.WHITE;
		if (res.winnerState==2) return WinnerState.BLACK;
		if (res.winnerState==3) return WinnerState.TIE;
		throw new RuntimeException();
	}


	
	public Coords waitOpposingPlayer()
	{
		GomokuRequest req = new GomokuRequest();
		req.type = RequestResponseType.WAITING_OPPOSING_PLAYER;
		
		GomokuResponse res = sendRequest(req);
		return new Coords(res.row, res.col);
	}	
	
	
	
	
	private GomokuResponse sendRequest(GomokuRequest req)
	{
		try
		{
			// Serialisation de la requete  
			req.serialize(dos);
			dos.flush();
			
			// Deserialisation de la reponse
			GomokuResponse res = GomokuResponse.deserialize(dis);
			if (res.errorMessage!=null)
			{
				throw new RuntimeException("Le serveur indiquer une erreur : "+res.errorMessage);
			}
			return res;
		} 
		catch (IOException e)
		{
			throw new RuntimeException("Erreur de comunication",e);
		} 
	}
}
