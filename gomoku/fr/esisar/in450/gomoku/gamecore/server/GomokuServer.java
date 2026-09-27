package fr.esisar.in450.gomoku.gamecore.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GomokuServer extends Thread
{
	public static void main(String[] args) throws IOException
	{
		System.out.println("Lancement du serveur de jeu GOMOKU");
		System.out.println("Attente de la première connexion ....");
		
		ServerSocket ss = new ServerSocket(7200);
		
		ConcurrentServer cc = new ConcurrentServer();
		
		Socket s = ss.accept();
		System.out.println("Le joueur 1 s'est connecté - IP = "+s.getInetAddress());
		GomokuServerPc pc0 = new GomokuServerPc(0,s,cc);
		pc0.start();
		
		s = ss.accept();
		System.out.println("Le joueur 2 s'est connecté - IP = "+s.getInetAddress());
		GomokuServerPc pc1 = new GomokuServerPc(1,s,cc);
		pc1.start();

		
	}
}
