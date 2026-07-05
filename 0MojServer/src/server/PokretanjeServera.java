package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PokretanjeServera extends Thread {
    
    private List<NitKlijenta> listaKlijenata;
    private static final int SERVER_PORT = 9000;
    private ServerSocket serverSocket;
    private boolean radi;
    
    public PokretanjeServera() {
        listaKlijenata = new ArrayList<>();
        radi = true;
    }
    
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server je pokrenut na portu: " + SERVER_PORT);
            
            while (radi) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("Klijent se povezao sa IP: " + socket.getInetAddress());
                    
                    NitKlijenta nitKlijenta = new NitKlijenta(listaKlijenata, socket);
                    listaKlijenata.add(nitKlijenta);
                    nitKlijenta.start();
                } catch (IOException ex) {
                    if (radi) {
                        Logger.getLogger(PokretanjeServera.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            System.out.println("Server je zaustavljen.");
            
        } catch (IOException ex) {
            if (radi) {
                Logger.getLogger(PokretanjeServera.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void zaustaviServer() {
        radi = false;
        try {
            // Zaustavi sve klijentske niti
            for (NitKlijenta nit : listaKlijenata) {
                nit.interrupt();
            }
            listaKlijenata.clear();
            
            // Zatvori server socket
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            
            System.out.println("Server se gasi...");
            
        } catch (IOException ex) {
            Logger.getLogger(PokretanjeServera.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}