package fr.esisar.in450.gomoku.gamecore.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class GomokuResponse
{
	// Indique le nombre réel de partie à jouer
	public int realNbGames;
	
	// Indique la couleur du joueur 
	public boolean isWhite;
	
	// 0 : on continue , 1 : les blancs gagnent , 2 : les noirs gagnent , 3 : tie
	public int winnerState;   
	
	// Coordonnées du coup que l'on souhaite jouer 
	public int row;
	
	public int col;

	public String errorMessage;

	public static GomokuResponse error(String errorMessage)
	{
		GomokuResponse res = new GomokuResponse();
		res.errorMessage = errorMessage;
		return res;
	}
	
	
	/**
     * Sérialise cet objet dans le flux fourni.
     */
    public void serialize(DataOutputStream out) throws IOException
    {
        out.writeInt(realNbGames);
        out.writeBoolean(isWhite);
        out.writeInt(winnerState);
        out.writeInt(row);
        out.writeInt(col);

        // Gestion du cas où errorMessage pourrait être null
        if (errorMessage != null)
        {
            out.writeBoolean(true);
            out.writeUTF(errorMessage);
        }
        else
        {
            out.writeBoolean(false);
        }
    }

    /**
     * Désérialise un objet GomokuResponse depuis le flux fourni.
     */
    public static GomokuResponse deserialize(DataInputStream in) throws IOException
    {
        GomokuResponse response = new GomokuResponse();

        response.realNbGames = in.readInt();
        response.isWhite = in.readBoolean();
        response.winnerState = in.readInt();
        response.row = in.readInt();
        response.col = in.readInt();

        boolean hasErrorMessage = in.readBoolean();
        if (hasErrorMessage)
        {
            response.errorMessage = in.readUTF();
        }
        else
        {
            response.errorMessage = null;
        }

        return response;
    }
	
}
