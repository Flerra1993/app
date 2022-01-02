package it.uniba.sms2122.thehillreloded;

import android.os.Handler;
import android.util.Log;

// questo è il thread di gioco a cui MainActivity trasferisce il controllo
// l'handler updateHandler viene caricato dalla chiamante "MainActivity" e collegato con "Game"
public class UpdateThread extends Thread{



    Handler updateHandler;
    private boolean isRunning = true;

    // da MainActivity collega il thread con Game, caricando il codice da eseguire passandolo a questo handler
    public UpdateThread(Handler uh) {
        super();
        updateHandler = uh;
    }


    public void run() {
        while (isRunning) {
            try {
                sleep(40);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            updateHandler.sendEmptyMessage(0);   // questo permette di far scattare in MainActivity l'aggiornamento
        }
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
