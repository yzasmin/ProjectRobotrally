import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

//Création serveur Multi-Thread extends Thread
public class Serveur extends Thread {
	private int nbClients;
	private List<Socket> listSocket = new ArrayList<Socket>();
	private List<Connection> listConnection = new ArrayList<Connection>();
	
	public void run() {
		try {
			//Création Serveur Socket
			ServerSocket ss = new ServerSocket(61234);
			System.out.println("Démarrage du server");
			//Boucle d'attente de connexion
			while(nbClients < 2) {
				//Création Socket client / ServeurSocket attend connexion
				Socket s = ss.accept();
				listSocket.add(s);
				
				nbClients++;
				//Création (connexion) de la conversation prend en parametre un Socket(Client)
				Connection connection = new Connection(s, nbClients);
				connection.start();
				listConnection.add(connection);
			}
			System.out.println("Waiting for players...");
			while(true) {
				Thread.sleep(2000);
				if(this.isStartGame(listConnection)) {
					this.messageToClients("[start]");
					System.out.println("Starting game...");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
				
		}
		}
}
