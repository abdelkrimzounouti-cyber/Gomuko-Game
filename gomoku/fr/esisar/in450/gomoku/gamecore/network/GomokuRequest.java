package fr.esisar.in450.gomoku.gamecore.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class GomokuRequest
{
	public RequestResponseType type;
	
	// Nombre de parties que l'on souhaite réaliser 
	public int nbGame;
	
	// Coordonnées du coup que l'on souhaite jouer 
	public int row;
	
	public int col;
	
	public String teamName;
	
	
	 /**
     * Sérialise cet objet dans le flux fourni.
     */
    public void serialize(DataOutputStream out) throws IOException
    {
        // On écrit l'enum sous forme d'entier (son ordinal)
        out.writeInt(type.ordinal());
        out.writeInt(nbGame);
        out.writeInt(row);
        out.writeInt(col);

        // Gestion du cas où teamName pourrait être null
        if (teamName != null)
        {
            out.writeBoolean(true);
            out.writeUTF(teamName);
        }
        else
        {
            out.writeBoolean(false);
        }
    }

    /**
     * Désérialise un objet GomokuRequest depuis le flux fourni.
     */
    public static GomokuRequest deserialize(DataInputStream in) throws IOException
    {
        GomokuRequest request = new GomokuRequest();

        int typeOrdinal = in.readInt();
        request.type = RequestResponseType.values()[typeOrdinal];

        request.nbGame = in.readInt();
        request.row = in.readInt();
        request.col = in.readInt();

        boolean hasTeamName = in.readBoolean();
        if (hasTeamName)
        {
            request.teamName = in.readUTF();
        }
        else
        {
            request.teamName = null;
        }

        return request;
    }


}
