package it.uniba.sms2122.thehillreloded;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

// Classe che istanzia la classe Game e la lancia in un thread collegandola tramite un handler

public class MainActivity extends AppCompatActivity {

    private Game game;
    private UpdateThread myThread;
    private Handler updateHandler;
    private ShopFragment shopFragment;//menu shop unita
    private UpgradeAcciaioFragment upgradeAcciaioFragment;//menu upgrade acciaio
    private UpgradeAlluminioFragment upgradeAlluminioFragment;//menu upgrade alluminio
    private UpgradeCartaFragment upgradeCartaFragment;//menu upgrade carta
    private UpgradePlasticaFragment upgradePlasticaFragment;//menu upgrade plastica
    private UpgradeVetroFragment upgradeVetroFragment;//menu upgrade vetro
    private UpgradeTecnologiaFragment upgradeTecnologiaFragment;//menu upgrade tecnologia
    private UpgradeInceneritoreFragment upgradeInceneritoreFragment;//menu upgrade inceneritore
    private int unitaSelezionata = 0;//indica la posizione dell'unita acquistata (1,2,3,4,5)
    private boolean isPresent2 = false;//indica se un unita è presente nella posizione 2 o meno
    private boolean isPresent5 = false;//indica se un unita è presente nella posizione 5 o meno
    private ViewGroup.LayoutParams params;
    private ConstraintLayout gameLayout;//Layout contente il gioco
    private ImageButton upgradeVetro;//bottone upgrade per aprire il menu upgrade vetro
    private ImageButton upgradeCarta;//bottone upgrade per aprire il menu upgrade carta
    private ImageButton upgradePlastica;//bottone upgrade per aprire il menu upgrade plastica
    private ImageButton upgradeTecnologia;//bottone upgrade per aprire il menu upgrade tecnologia
    private ImageButton upgradeAlluminio;//bottone upgrade per aprire il menu upgrade alluminio
    private ImageButton upgradeAcciaio;//bottone upgrade per aprire il menu upgrade acciaio
    private ImageButton missionBtn;//bottone che apre il menu delle missioni
    private FragmentContainerView shopContainer;//Layout contenente i fragment di upgrade e shop
    private FloatingActionButton add1;//bottone che apre lo shop e inserisce l'unita, in caso di acquisto nella posizione 1
    private FloatingActionButton add2;//bottone che apre lo shop e inserisce l'unita, in caso di acquisto nella posizione 2
    private FloatingActionButton add3;//bottone che apre lo shop e inserisce l'unita, in caso di acquisto nella posizione 3
    private FloatingActionButton add4;//bottone che apre lo shop e inserisce l'unita, in caso di acquisto nella posizione 4
    private FloatingActionButton add5;//bottone che apre lo shop e inserisce l'unita, in caso di acquisto nella posizione 5
    private FragmentContainerView missionContainer;//layout contenente il fragment delle missioni
    private boolean tecnologia = false, plastica = false, acciaio = false, alluminio, carta = false; //indicano se l'unita è in posizione 5 o 2 (true-> 2 o 5, false-> 1,3 o 4)
    private static final int upgradePaddingButton = 250;//compensa la distanza per i bottoni upgrade
    private ViewGroup.LayoutParams paramsShop;
    private ImageButton menuBtn;//bottone che apre il menu di pausa
    private FragmentContainerView pauseContainer;//layout contenente il fragment di menu pausa
    private ImageButton upgradeInceneritore;//bottone che apre il menu di upgrade dell'inceneritore


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // imposta l'orientamento landscape come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //imposta come View la mainActivity
        setContentView(R.layout.activity_main);
        //istanzia la classe Game
        game = new Game(this);
        //aggiungo la classe Game alla View
        addContentView(game,findViewById(R.id.gameLayout).getLayoutParams());

        //istanzia i fragment (menu upgrade, menu shop)
        shopFragment = new ShopFragment();
        upgradeAcciaioFragment = new UpgradeAcciaioFragment();
        upgradeAlluminioFragment = new UpgradeAlluminioFragment();
        upgradeCartaFragment = new UpgradeCartaFragment();
        upgradePlasticaFragment = new UpgradePlasticaFragment();
        upgradeVetroFragment = new UpgradeVetroFragment();
        upgradeTecnologiaFragment = new UpgradeTecnologiaFragment();
        upgradeInceneritoreFragment = new UpgradeInceneritoreFragment();

        //istanzia il layout del game e ne prendo i parametri
        gameLayout = findViewById(R.id.gameLayout);
        params = gameLayout.getLayoutParams();

        //istanzio i bottoni di upgrade delle unita riciclo
        upgradeVetro = findViewById(R.id.upgradeVetroBtn);
        upgradeCarta = findViewById(R.id.upgradeCartaBtn);
        upgradeTecnologia = findViewById(R.id.upgradeTecnologiaBtn);
        upgradePlastica = findViewById(R.id.upgradePlasticaBtn);
        upgradeAlluminio = findViewById(R.id.upgradeAlluminioBtn);
        upgradeAcciaio = findViewById(R.id.upgradeAcciaioBtn);
        upgradeInceneritore = findViewById(R.id.upgradeInceneritoreBtn);

        //istanzio il layout dello shop e ne prendo i parametri
        shopContainer = findViewById(R.id.containerShopInfoUnita);
        paramsShop = shopContainer.getLayoutParams();

        //istanzio i bottoni di aggiunta unita riciclo per le varie posizioni
        add1 = findViewById(R.id.addUnitPos1);
        add2 = findViewById(R.id.addUnitPos2);
        add3 = findViewById(R.id.addUnitPos3);
        add4 = findViewById(R.id.addUnitPos4);
        add5 = findViewById(R.id.addUnitPos5);

        //istanzio il layout del menu missioni e pausa
        missionContainer = findViewById(R.id.missionContainer);
        pauseContainer = findViewById(R.id.pauseContainer);

        //istanzio i bottoni per aprire il menu dello shop e delle missioni
        missionBtn = findViewById(R.id.missionBtn);
        menuBtn = findViewById(R.id.menuBtn);

        if(Game.resize){//se il gioco è da ridimensionare
            findViewById(R.id.containerResize).setVisibility(View.VISIBLE);//layout contente il fragment di ridimensionamento
        }else//altrimenti
        {
            //non mostrare il pannello di ridimensionamento
            findViewById(R.id.containerResize).setVisibility(View.GONE);
            if(Game.dimension == 1){//se la dimensione è 5"
                //inizializza posizione bottoni a schermo
                upgradeVetro.setX(300);
                upgradeVetro.setY(90);
                upgradeInceneritore.setX(1090);
                upgradeInceneritore.setY(110);
                add1.setX(100);
                add1.setY(450);
                add2.setX(100);
                add2.setY(750);
                add3.setX(1600);
                add3.setY(150);
                add4.setX(1600);
                add4.setY(450);
                add5.setX(1600);
                add5.setY(750);
            }else if(Game.dimension == 2){//se la dimensione è 5"+
                //inizializza posizione bottoni a schermo
                upgradeVetro.setX(350);
                upgradeVetro.setY(90);
                upgradeInceneritore.setX(1180);
                upgradeInceneritore.setY(110);
                add1.setX(100);
                add1.setY(450);
                add2.setX(100);
                add2.setY(750);
                add3.setX(1800);
                add3.setY(150);
                add4.setX(1800);
                add4.setY(450);
                add5.setX(1800);
                add5.setY(750);
            }else{//se la dimesione è 7"+
                //inizializza posizione bottoni a schermo
                add1.setX(200);
                add1.setY(650);
                add2.setX(200);
                add2.setY(1150);
                add3.setX(1700);
                add3.setY(200);
                add4.setX(1700);
                add4.setY(650);
                add5.setX(1700);
                add5.setY(1150);
                upgradeVetro.setX(400);
                upgradeVetro.setY(200);
                upgradeInceneritore.setX(1080);
                upgradeInceneritore.setY(160);
            }
            //rendi visibile i bottoni a schermo
            upgradeVetro.setVisibility(View.VISIBLE);
            upgradeInceneritore.setVisibility(View.VISIBLE);
            add1.setVisibility(View.VISIBLE);
            add2.setVisibility(View.VISIBLE);
            add3.setVisibility(View.VISIBLE);
            add4.setVisibility(View.VISIBLE);
            add5.setVisibility(View.VISIBLE);
            missionBtn.setVisibility(View.VISIBLE);
            menuBtn.setVisibility(View.VISIBLE);
        }
        // crea e avvia il thread che deve gestire in parallelo update e draw di Game
        GameHandler();  // carica l'handler da passare al thread
        myThread = new UpdateThread(updateHandler);  // collega Game con il thread che 'vive' in questa classe
        myThread.start();
    }

    // questo gestore viene collegato al thread del gioco e  parallelizza come thread le chiamate update e draw di Game
    private void GameHandler() {
        updateHandler = new Handler() {
            public void handleMessage(Message msg) {
                game.invalidate();       // forza aggiornamento rendering della View ( -> game.onDraw)
                game.update();           // aggiorna la logica di gioco
                super.handleMessage(msg);
            }
        };
    }

    @Override
    public void onBackPressed() {
        //alla pressione del tasto back
        pauseContainer.setVisibility(View.VISIBLE);//apri menu pausa
        Game.pauseMission = true;//ferma il gioco

    }




    protected void onResume() {
        //toglie tendina notifiche e tasti di navigazione android
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Game.pauseMission = false;//avvia il gioco
        super.onResume();
    }


    protected void onDestroy() {
        stopLoop();//interrompe il thread
        super.onDestroy();
    }


    private void stopLoop() {
        myThread.setRunning(false);//thread ha terminato
        myThread.interrupt();//interrompe thread
    }

    protected void onRestart(){
        Game.pauseMission = false;//avvia gioco
        super.onRestart();
    }

    protected void onPause() {
        Game.pauseMission = true;//ferma gioco
        super.onPause();
    }

    protected void onStop() {
        Game.pauseMission = true;//ferma gioco
        super.onStop();
    }

    /*
    metodo che in caso di gameover al tocco dello schermo apre il menu dei risultati
     */
    public void tapToContinue(View view){
        if(Game.gameover) {//se il gioco è terminato
            setNotClickable();
            findViewById(R.id.backMenuContainer).setVisibility(View.VISIBLE);//rendi visibile il menu dei risultati
            //inizializza le TextView che conterranno le informazioni dei risultati
            TextView vetroRaccolto = view.findViewById(R.id.vetroRaccoltoNumeroTxt);
            TextView acciaioRaccolto = view.findViewById(R.id.acciaioRaccoltoNumeroTxt);
            TextView alluminioRaccolto = view.findViewById(R.id.alluminioRaccoltoNumeroTxt);
            TextView plasticaRaccolto = view.findViewById(R.id.plasticaRaccoltaNumeroTxt);
            TextView tecnologiaRaccolto = view.findViewById(R.id.tecnologiaRaccoltaNumeroTxt);
            TextView cartaRaccolto = view.findViewById(R.id.cartaRaccoltaNumeroTxt);
            TextView oggettiProdottoVetro = view.findViewById(R.id.oggettiProdottiNumeroVetroTxt);
            TextView oggettiProdottoCarta = view.findViewById(R.id.oggettiProdottiNumeroCartaTxt);
            TextView oggettiProdottovAlluminio = view.findViewById(R.id.oggettiProdottiNumeroAlluminioTxt);
            TextView oggettiProdottoAcciaio = view.findViewById(R.id.oggettiProdottiNumeroAcciaioTxt);
            TextView oggettiProdottoPlastica = view.findViewById(R.id.oggettiProdottiNumeroPlasticaTxt);
            TextView oggettiProdottoTecnologia = view.findViewById(R.id.oggettiProdottiNumeroTecnologiaTxt);
            TextView timeTxt = view.findViewById(R.id.timeTxt);

            //inizializza il totale oggetti prodotti per ogni unita
            int totVetro = Game.unitaRicicloArrayList.get(1).getOggettoRiciclo1() + Game.unitaRicicloArrayList.get(1).getOggettoRiciclo2() + Game.unitaRicicloArrayList.get(1).getOggettoRiciclo3();
            int totCarta = Game.unitaRicicloArrayList.get(2).getOggettoRiciclo1() + Game.unitaRicicloArrayList.get(2).getOggettoRiciclo2() + Game.unitaRicicloArrayList.get(2).getOggettoRiciclo3();
            int totAlluminio = Game.unitaRicicloArrayList.get(4).getOggettoRiciclo1() + Game.unitaRicicloArrayList.get(4).getOggettoRiciclo2() + Game.unitaRicicloArrayList.get(4).getOggettoRiciclo3();
            int totAcciaio = Game.unitaRicicloArrayList.get(3).getOggettoRiciclo1() + Game.unitaRicicloArrayList.get(3).getOggettoRiciclo2() + Game.unitaRicicloArrayList.get(3).getOggettoRiciclo3();
            int totPlastica = Game.unitaRicicloArrayList.get(5).getOggettoRiciclo1() + Game.unitaRicicloArrayList.get(5).getOggettoRiciclo2() + Game.unitaRicicloArrayList.get(5).getOggettoRiciclo3();
            int totTecnologia = Game.unitaRicicloArrayList.get(6).getOggettoRiciclo1() + Game.unitaRicicloArrayList.get(6).getOggettoRiciclo2() + Game.unitaRicicloArrayList.get(6).getOggettoRiciclo3();

            //istanzia e inizializza le stringhe contenenti le informazioni da presentare nel menu dei risultati
            String oggettiVetro = " " + totVetro;
            String oggettiCarta = " " + totCarta;
            String oggettiAlluminio = " " +totAlluminio;
            String oggettiAcciaio = " " + totAcciaio;
            String oggettiPlastica = " " + totPlastica;
            String oggettiTecnologia = " " + totTecnologia;
            String vetro = " " + Game.unitaRicicloArrayList.get(1).getTotOggettiRiciclati();
            String carta = " " + Game.unitaRicicloArrayList.get(2).getTotOggettiRiciclati();
            String alluminio = " " + Game.unitaRicicloArrayList.get(4).getTotOggettiRiciclati();
            String acciaio = " " + Game.unitaRicicloArrayList.get(3).getTotOggettiRiciclati();
            String plastica = " " + Game.unitaRicicloArrayList.get(5).getTotOggettiRiciclati();
            String tecnologia = " " + Game.unitaRicicloArrayList.get(6).getTotOggettiRiciclati();
            String minuteString, secondString;
            String time;

            //formato 00m 00s
            if(Game.minute < 10){
                minuteString = "0" + Game.minute + "m ";
            }else
            {
                minuteString = Game.minute + "m ";
            }
            if(Game.second < 10){
                secondString = "0" + Game.second + "s ";
            }else
            {
                secondString = Game.second + "s";
            }
            time = minuteString + secondString;

            //per ogni textView setto la stringa
            timeTxt.setText(time);
            oggettiProdottoAcciaio.setText(oggettiAcciaio);
            oggettiProdottoCarta.setText(oggettiCarta);
            oggettiProdottoPlastica.setText(oggettiPlastica);
            oggettiProdottoTecnologia.setText(oggettiTecnologia);
            oggettiProdottovAlluminio.setText(oggettiAlluminio);
            oggettiProdottoVetro.setText(oggettiVetro);
            vetroRaccolto.setText(vetro);
            cartaRaccolto.setText(carta);
            alluminioRaccolto.setText(alluminio);
            acciaioRaccolto.setText(acciaio);
            plasticaRaccolto.setText(plastica);
            tecnologiaRaccolto.setText(tecnologia);
            Game.cleanScreen = true;//pulizia schermo
            findViewById(R.id.gameLayout).setClickable(false);//il layout del game non è più clickabile
        }
    }



    public void returnToMenu(View view){
        Intent intent = new Intent(MainActivity.this,MainActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("dimension", Game.dimension);//passa la dimensione al menu per ricordare la dimesione
        startActivity(intent);//termina l'activity


    }

    /*
    metodo che chiude lo shop
     */
    public void closeShop(View view){
        //rimuovi il fragment dello shop dal layout containerShopInfoUnita
        getSupportFragmentManager().beginTransaction().remove(shopFragment).commit();
        findViewById(R.id.containerShopInfoUnita).setVisibility(View.GONE);
        if(!isPresent5) {//se non sono presenti unita riciclo in posizione 5
            add5.setVisibility(View.VISIBLE);
        }
        if(!isPresent2){//se non sono presenti unita riciclo in posizione 2
            add2.setVisibility(View.VISIBLE);
        }
        visibleButtonUpgrade();
        findViewById(R.id.closeShopBtn).setVisibility(View.GONE);//rendi gone il bottone per chiudere il menu
        missionBtn.setVisibility(View.VISIBLE);//rendi visibile il bottone per aprire il menu missioni
        Game.pauseShop = false;//gioco non più in pausa
        setClickable();
    }

    /*
    metodo che apre il menu dello shop
     */
    private void getShop(){

        //se il menu non è visibile
        if(findViewById(R.id.containerShopInfoUnita).getVisibility() == View.GONE){

            getSupportFragmentManager().beginTransaction().replace(R.id.containerShopInfoUnita,shopFragment).commit();//rimpiazza il fragment con quello del menu shop
            findViewById(R.id.containerShopInfoUnita).setVisibility(View.VISIBLE);//rendi visibile il menu
            add2.setVisibility(View.GONE);//bottone gone (coperto dal menu)
            add5.setVisibility(View.GONE);//bottone gone (coperto dal menu)


            goneButtonUpgrade();
            setNotClickable();
            findViewById(R.id.closeShopBtn).setVisibility(View.VISIBLE);//rende il menu shop visibile
            missionBtn.setVisibility(View.GONE);//rende gone il bottone delle missioni
            Game.pauseShop = true;//gioco in pausa
        }else
        {

            findViewById(R.id.containerShopInfoUnita).setVisibility(View.GONE);//rende gone il layout che contiene il menu shop fragment
            getSupportFragmentManager().beginTransaction().remove(shopFragment).commit();//rimuove il fragment shop dal layout
            if(!isPresent5) {//se non sono presenti unita riciclo in posizione 5
                add5.setVisibility(View.VISIBLE);
            }
            if(!isPresent2){//se non sono presenti unita riciclo in posizione 2
                add2.setVisibility(View.VISIBLE);
            }
            findViewById(R.id.closeShopBtn).setVisibility(View.GONE);//rende gone il bottone per chiudere lo shop
            missionBtn.setVisibility(View.VISIBLE);//rende visibile il bottone delle missioni
            visibleButtonUpgrade();
            Game.pauseShop = false;//gioco non in pausa
            setClickable();
        }

    }
    /*
    metodo che apre il menu dello shop, e in caso di acquisto di un'unita, la
    posiziona nel posizione 1
    */
    public void addUnit1(View view){
        getShop();
        unitaSelezionata = 1;

    }
    /*
    metodo che apre il menu dello shop, e in caso di acquisto di un'unita, la
    posiziona nel posizione 2
    */
    public void addUnit2(View view){
        getShop();
        unitaSelezionata = 2;

    }
    /*
    metodo che apre il menu dello shop, e in caso di acquisto di un'unita, la
    posiziona nel posizione 3
    */
    public void addUnit3(View view){
        getShop();
        unitaSelezionata = 3;

    }
    /*
    metodo che apre il menu dello shop, e in caso di acquisto di un'unita, la
    posiziona nel posizione 4
    */
    public void addUnit4(View view){
        getShop();
        unitaSelezionata = 4;

    }
    /*
    metodo che apre il menu dello shop, e in caso di acquisto di un'unita, la
    posiziona nel posizione 5
    */
    public void addUnit5(View view){
        getShop();
        unitaSelezionata = 5;

    }

    /*
    metodo che rende non cliccabili tutti i bottoni presenti
     */
    private void setNotClickable(){
        upgradePlastica.setClickable(false);
        upgradeAcciaio.setClickable(false);
        upgradeAlluminio.setClickable(false);
        upgradeCarta.setClickable(false);
        upgradeVetro.setClickable(false);
        upgradeTecnologia.setClickable(false);
        upgradeInceneritore.setClickable(false);
        add1.setClickable(false);
        add2.setClickable(false);
        add3.setClickable(false);
        add4.setClickable(false);
        add5.setClickable(false);
        missionBtn.setClickable(false);
        menuBtn.setClickable(false);

    }

    /*
    metodo che rende cliccabili tutti i bottoni presenti
     */
    private void setClickable(){
        upgradeInceneritore.setClickable(true);
        upgradePlastica.setClickable(true);
        upgradeAcciaio.setClickable(true);
        upgradeAlluminio.setClickable(true);
        upgradeCarta.setClickable(true);
        upgradeVetro.setClickable(true);
        upgradeTecnologia.setClickable(true);
        add1.setClickable(true);
        add2.setClickable(true);
        add3.setClickable(true);
        add4.setClickable(true);
        add5.setClickable(true);
        missionBtn.setClickable(true);
        menuBtn.setClickable(true);
    }

    /*
    metodo che apre il menu dell'upgrade dell'unita di riciclo vetro
     */
    public void getUpgradeVetro(View view){

        Game.pauseUpgrade = true;
        getSupportFragmentManager().beginTransaction().replace(R.id.containerShopInfoUnita,upgradeVetroFragment).commit();
        add5.setVisibility(View.GONE);//bottone gone (coperto dal menu)
        add2.setVisibility(View.GONE);//bottone gone (coperto dal menu)
        goneButtonUpgrade();
        findViewById(R.id.closeUpgradeMenuBtn).setVisibility(View.VISIBLE);//rende visibile il bottone di chiusura menu upgrade
        setNotClickable();
        missionBtn.setVisibility(View.GONE);//rende non visibile il bottone delle missioni
        findViewById(R.id.containerShopInfoUnita).setVisibility(View.VISIBLE);//rende visibile il layout

    }

    /*
    metodo che apre il menu dell'upgrade dell'inceneritore
     */
    public void getUpgradeInceneritore(View view){

        Game.pauseUpgrade = true;//gioco in pausa
        //rimpiazza il fragment attuale nel layout con il fragment di upgrade Inceneritore
        getSupportFragmentManager().beginTransaction().replace(R.id.containerShopInfoUnita,upgradeInceneritoreFragment).commit();
        add5.setVisibility(View.GONE);//bottone gone (coperto dal menu)
        add2.setVisibility(View.GONE);//bottone gone (coperto dal menu)
        goneButtonUpgrade();
        findViewById(R.id.closeUpgradeMenuBtn).setVisibility(View.VISIBLE);//rende visibile il bottone di chiusura menu upgrade
        setNotClickable();
        missionBtn.setVisibility(View.GONE);//rende non visibile il bottone delle missioni
        findViewById(R.id.containerShopInfoUnita).setVisibility(View.VISIBLE);//rende visibile il layout

    }

    /*
    metodo che apre il menu dell'upgrade dell'unita di riciclo carta
    */
    public void getUpgradeCarta(View view){

        Game.pauseUpgrade = true;//gioco in pausa
        //rimpiazza il fragment attuale nel layout con il fragment di upgrade Carta
        getSupportFragmentManager().beginTransaction().replace(R.id.containerShopInfoUnita,upgradeCartaFragment).commit();
        add5.setVisibility(View.GONE);//bottone gone (coperto dal menu)
        add2.setVisibility(View.GONE);//bottone gone (coperto dal menu)
        goneButtonUpgrade();
        findViewById(R.id.closeUpgradeMenuBtn).setVisibility(View.VISIBLE);//rende visibile il bottone di chiusura menu upgrade
        setNotClickable();
        missionBtn.setVisibility(View.GONE);//rende non visibile il bottone delle missioni
        findViewById(R.id.containerShopInfoUnita).setVisibility(View.VISIBLE);//rende visibile il layout

    }
    /*
   metodo che apre il menu dell'upgrade dell'unita di riciclo acciaio
   */
    public void getUpgradeAcciaio(View view){
        Game.pauseUpgrade = true;//gioco in pausa
        //rimpiazza il fragment attuale nel layout con il fragment di upgrade Acciaio
        getSupportFragmentManager().beginTransaction().replace(R.id.containerShopInfoUnita,upgradeAcciaioFragment).commit();
        add5.setVisibility(View.GONE);//bottone gone (coperto dal menu)
        add2.setVisibility(View.GONE);//bottone gone (coperto dal menu)
        goneButtonUpgrade();
        findViewById(R.id.closeUpgradeMenuBtn).setVisibility(View.VISIBLE);//rende visibile il bottone di chiusura menu upgrade
        setNotClickable();
        missionBtn.setVisibility(View.GONE);//rende non visibile il bottone delle missioni
        findViewById(R.id.containerShopInfoUnita).setVisibility(View.VISIBLE);//rende visibile il layout
    }

    /*
   metodo che apre il menu dell'upgrade dell'unita di riciclo alluminio
   */
    public void getUpgradeAlluminio(View view){
        Game.pauseUpgrade = true;//gioco in pausa
        //rimpiazza il fragment attuale nel layout con il fragment di upgrade Alluminio
        getSupportFragmentManager().beginTransaction().replace(R.id.containerShopInfoUnita,upgradeAlluminioFragment).commit();
        add5.setVisibility(View.GONE);//bottone gone (coperto dal menu)
        add2.setVisibility(View.GONE);//bottone gone (coperto dal menu)
        goneButtonUpgrade();
        findViewById(R.id.closeUpgradeMenuBtn).setVisibility(View.VISIBLE);//rende visibile il bottone di chiusura menu upgrade
        setNotClickable();
        missionBtn.setVisibility(View.GONE);//rende non visibile il bottone delle missioni
        findViewById(R.id.containerShopInfoUnita).setVisibility(View.VISIBLE);//rende visibile il layout
    }

    /*
   metodo che apre il menu dell'upgrade dell'unita di riciclo plastica
   */
    public void getUpgradePlastica(View view){
        Game.pauseUpgrade = true;//gioco in pausa
        //rimpiazza il fragment attuale nel layout con il fragment di upgrade Plastica
        getSupportFragmentManager().beginTransaction().replace(R.id.containerShopInfoUnita,upgradePlasticaFragment).commit();
        add5.setVisibility(View.GONE);//bottone gone (coperto dal menu)
        add2.setVisibility(View.GONE);//bottone gone (coperto dal menu)
        goneButtonUpgrade();
        findViewById(R.id.closeUpgradeMenuBtn).setVisibility(View.VISIBLE);//rende visibile il bottone di chiusura menu upgrade
        setNotClickable();
        missionBtn.setVisibility(View.GONE);//rende non visibile il bottone delle missioni
        findViewById(R.id.containerShopInfoUnita).setVisibility(View.VISIBLE);//rende visibile il layout
    }

    /*
   metodo che apre il menu dell'upgrade dell'unita di riciclo tecnologia
   */
    public void getUpgradeTecnologia(View view){
        Game.pauseUpgrade = true;//gioco in pausa
        //rimpiazza il fragment attuale nel layout con il fragment di upgrade Tecnologia
        getSupportFragmentManager().beginTransaction().replace(R.id.containerShopInfoUnita,upgradeTecnologiaFragment).commit();
        add5.setVisibility(View.GONE);//bottone gone (coperto dal menu)
        add2.setVisibility(View.GONE);//bottone gone (coperto dal menu)
        goneButtonUpgrade();
        findViewById(R.id.closeUpgradeMenuBtn).setVisibility(View.VISIBLE);//rende visibile il bottone di chiusura menu upgrade
        setNotClickable();
        missionBtn.setVisibility(View.GONE);//rende gone il bottone delle missioni
        findViewById(R.id.containerShopInfoUnita).setVisibility(View.VISIBLE);//rende visibile il layout
    }

    /*
    metodo che effettua l'acquisto e posizionamento dell'unita di riciclo carta
    */
    public void setUnitaRiciclaggioCarta(View view){
            Game.puntiSole = Game.puntiSole - 12;//prezzo unita riciclo
            findViewById(R.id.upgradeCartaBtn).setVisibility(View.VISIBLE);//rende visibile il bottone di upgrade unita
            deleteButtonAdd(unitaSelezionata);//elimina il bottone di aggiunta in base al bottone cliccato per acquistare l'unita
            Game.timeBarArrayList.get(2).setPosizioneSchermo(unitaSelezionata);//aggiunge la timebar corrispondente l'unita
            Game.unitaRicicloArrayList.get(2).setPosizioneSchermo(unitaSelezionata);//posizione unita
            Game.unitaRicicloArrayList.get(2).setPosizioneX(selectPositionUnit(unitaSelezionata).get(0));//posizione x unita
            Game.unitaRicicloArrayList.get(2).setPosizioneY(selectPositionUnit(unitaSelezionata).get(1));//posizione y unita
            upgradeCarta.setX((float) (Game.unitaRicicloArrayList.get(2).getPosizioneX()) + upgradePaddingButton);//posizione x bottone upgrade
            upgradeCarta.setY((float) (Game.unitaRicicloArrayList.get(2).getPosizioneY()));//posizione y bottone upgrade
            Game.unitaRicicloArrayList.get(2).setPresent(true);//unita acquistata
            Game.timeBarArrayList.get(2).setPresent(true);//timebar aggiunta
            if(unitaSelezionata == 2 || unitaSelezionata == 5){//se l'unita è messa in posizione 2 o 5
                carta = true;//ne teniamo conto per renderle invisibile al momento dell'apertura di un menu
            }
            controlloAcquistoUnita();
            getShop();
    }

    /*
    metodo che effettua l'acquisto e posizionamento dell'unita di riciclo plastica
    */
    public void setUnitaRiciclaggioPlastica(View view){
            Game.puntiSole = Game.puntiSole - 35;//prezzo unita riciclo
            findViewById(R.id.upgradePlasticaBtn).setVisibility(View.VISIBLE);//rende visibile il bottone di upgrade unita
            deleteButtonAdd(unitaSelezionata);//elimina il bottone di aggiunta in base al bottone cliccato per acquistare l'unita
            Game.timeBarArrayList.get(5).setPosizioneSchermo(unitaSelezionata);//aggiunge la timebar corrispondente l'unita
            Game.unitaRicicloArrayList.get(5).setPosizioneSchermo(unitaSelezionata);//posizione unita
            Game.unitaRicicloArrayList.get(5).setPosizioneX(selectPositionUnit(unitaSelezionata).get(0));//posizione x unita
            Game.unitaRicicloArrayList.get(5).setPosizioneY(selectPositionUnit(unitaSelezionata).get(1));//posizione y unita
            upgradePlastica.setX((float) (Game.unitaRicicloArrayList.get(5).getPosizioneX()) + upgradePaddingButton);//posizione x bottone upgrade
            upgradePlastica.setY((float) (Game.unitaRicicloArrayList.get(5).getPosizioneY()));//posizione y bottone upgrade
            Game.unitaRicicloArrayList.get(5).setPresent(true);//unita acquistata
            Game.timeBarArrayList.get(5).setPresent(true);//timebar aggiunta
            if(unitaSelezionata == 2 || unitaSelezionata == 5){//se l'unita è messa in posizione 2 o 5

                // ne teniamo conto per renderle invisibile al momento dell'apertura di un menu
                plastica = true;
            }
            controlloAcquistoUnita();
            getShop();

    }

    /*
    metodo che effettua l'acquisto e posizionamento dell'unita di riciclo alluminio
    */
    public void setUnitaRiciclaggioAlluminio(View view){
            Game.puntiSole = Game.puntiSole - 25;//prezzo unita riciclo
            findViewById(R.id.upgradeAlluminioBtn).setVisibility(View.VISIBLE);//rende visibile il bottone di upgrade unita
            deleteButtonAdd(unitaSelezionata);//elimina il bottone di aggiunta in base al bottone cliccato per acquistare l'unita
            Game.timeBarArrayList.get(4).setPosizioneSchermo(unitaSelezionata);//aggiunge la timebar corrispondente l'unita
            Game.unitaRicicloArrayList.get(4).setPosizioneSchermo(unitaSelezionata);//posizione unita
            Game.unitaRicicloArrayList.get(4).setPosizioneX(selectPositionUnit(unitaSelezionata).get(0));//posizione x unita
            Game.unitaRicicloArrayList.get(4).setPosizioneY(selectPositionUnit(unitaSelezionata).get(1));//posizione y unita
            upgradeAlluminio.setX((float) (Game.unitaRicicloArrayList.get(4).getPosizioneX()) + upgradePaddingButton);//posizione x bottone upgrade
            upgradeAlluminio.setY((float) (Game.unitaRicicloArrayList.get(4).getPosizioneY()));//posizione y bottone upgrade
            Game.unitaRicicloArrayList.get(4).setPresent(true);//unita acquistata
            Game.timeBarArrayList.get(4).setPresent(true);//timebar aggiunta
            if(unitaSelezionata == 2 || unitaSelezionata == 5){//se l'unita è messa in posizione 2 o 5
                //ne teniamo conto per renderle invisibile al momento dell'apertura di un menu
                alluminio = true;
            }
            controlloAcquistoUnita();
            getShop();


    }

    /*
    metodo che effettua l'acquisto e posizionamento dell'unita di riciclo acciaio
    */
    public void setUnitaRiciclaggioAcciaio(View view){
            Game.puntiSole = Game.puntiSole - 30;//prezzo unita riciclo
            findViewById(R.id.upgradeAcciaioBtn).setVisibility(View.VISIBLE);//rende visibile il bottone di upgrade unita
            deleteButtonAdd(unitaSelezionata);//elimina il bottone di aggiunta in base al bottone cliccato per acquistare l'unita
            Game.timeBarArrayList.get(3).setPosizioneSchermo(unitaSelezionata);//aggiunge la timebar corrispondente l'unita
            Game.unitaRicicloArrayList.get(3).setPosizioneSchermo(unitaSelezionata);//posizione unita
            Game.unitaRicicloArrayList.get(3).setPosizioneX(selectPositionUnit(unitaSelezionata).get(0));//posizione x unita
            Game.unitaRicicloArrayList.get(3).setPosizioneY(selectPositionUnit(unitaSelezionata).get(1));//posizione y unita
            upgradeAcciaio.setX((float) (Game.unitaRicicloArrayList.get(3).getPosizioneX()) + upgradePaddingButton);//posizione x bottone upgrade
            upgradeAcciaio.setY((float) (Game.unitaRicicloArrayList.get(3).getPosizioneY()));//posizione y bottone upgrade
            Game.unitaRicicloArrayList.get(3).setPresent(true);//unita acquistata
            Game.timeBarArrayList.get(3).setPresent(true);//timebar aggiunta
            if(unitaSelezionata == 2 || unitaSelezionata == 5){//se l'unita è messa in posizione 2 o 5
                //ne teniamo conto per renderle invisibile al momento dell'apertura di un menu
                acciaio = true;
            }
            controlloAcquistoUnita();
            getShop();


    }

    /*
    metodo che effettua l'acquisto e posizionamento dell'unita di riciclo tecnologia
    */
    public void setUnitaRiciclaggioTecnologia(View view){
            Game.puntiSole = Game.puntiSole - 40;//prezzo unita riciclo
            findViewById(R.id.upgradeTecnologiaBtn).setVisibility(View.VISIBLE);//rende visibile il bottone di upgrade unita
            deleteButtonAdd(unitaSelezionata);//elimina il bottone di aggiunta in base al bottone cliccato per acquistare l'unita
            Game.timeBarArrayList.get(6).setPosizioneSchermo(unitaSelezionata);//posizione unita
            Game.unitaRicicloArrayList.get(6).setPosizioneSchermo(unitaSelezionata);//posizione unita
            Game.unitaRicicloArrayList.get(6).setPosizioneX(selectPositionUnit(unitaSelezionata).get(0));//posizione x unita
            Game.unitaRicicloArrayList.get(6).setPosizioneY(selectPositionUnit(unitaSelezionata).get(1));//posizione y unita
            upgradeTecnologia.setX((float) (Game.unitaRicicloArrayList.get(6).getPosizioneX()) + upgradePaddingButton);//posizione x bottone upgrade
            upgradeTecnologia.setY((float) (Game.unitaRicicloArrayList.get(6).getPosizioneY()));//posizione y bottone upgrade
            Game.unitaRicicloArrayList.get(6).setPresent(true);//unita acquistata
            Game.timeBarArrayList.get(6).setPresent(true);//timebar aggiunta
            if(unitaSelezionata == 2 || unitaSelezionata == 5){//se l'unita è messa in posizione 2 o 5
                //ne teniamo conto per renderle invisibile al momento dell'apertura di un menu
                tecnologia = true;
            }
            controlloAcquistoUnita();
            getShop();


    }
    /*
    metodo che rende gone i bottoni presenti in posizione 5 o 2
    */
    private void goneButtonUpgrade(){
        if(tecnologia){
            upgradeTecnologia.setVisibility(View.GONE);
        }
        if(carta){
            upgradeCarta.setVisibility(View.GONE);
        }
        if(alluminio){
            upgradeAlluminio.setVisibility(View.GONE);
        }
        if(acciaio){
            upgradeAcciaio.setVisibility(View.GONE);
        }
        if(plastica){
            upgradePlastica.setVisibility(View.GONE);
        }
    }

    /*
    metodo che rende visibili i bottoni presenti in posizione 5 o 2
    */
    private void visibleButtonUpgrade(){
        if(tecnologia){
            upgradeTecnologia.setVisibility(View.VISIBLE);
        }
        if(carta){
            upgradeCarta.setVisibility(View.VISIBLE);
        }
        if(alluminio){
            upgradeAlluminio.setVisibility(View.VISIBLE);
        }
        if(acciaio){
            upgradeAcciaio.setVisibility(View.VISIBLE);
        }
        if(plastica){
            upgradePlastica.setVisibility(View.VISIBLE);
        }
    }

    /*
    metodo che elimina i bottoni in base alla posizione
    dell'acquisto
     */
    private void deleteButtonAdd(int unita){
        switch (unita){
            case 1:
                add1.setVisibility(View.GONE);
                break;
            case 2:
                add2.setVisibility(View.GONE);
                break;
            case 3:
                add3.setVisibility(View.GONE);
                break;
            case 4:
                add4.setVisibility(View.GONE);
                break;
            case 5:
                add5.setVisibility(View.GONE);
                break;

        }
    }

    public void closeUpgradeMenu(View view) {

            findViewById(R.id.containerShopInfoUnita).setVisibility(View.GONE);

            //se è presente un fragmente nel layout rimuovilo
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.containerShopInfoUnita);
            assert fragment != null;
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();


            if(!isPresent5) {
            add5.setVisibility(View.VISIBLE);
             }
            if(!isPresent2){
            add2.setVisibility(View.VISIBLE);
            }
            visibleButtonUpgrade();
            findViewById(R.id.closeUpgradeMenuBtn).setVisibility(View.GONE);//rende il bottone  di chiusura menu upgrade gone
            missionBtn.setVisibility(View.VISIBLE);//rende visibile il bottone delle missioni

            Game.pauseUpgrade = false;//gioco non in pausa
            setClickable();
        }

        /*
        metodo che, in base alla posizione selezionata, restituisce una
        coppia di valori (x,y) che rappresentano le coordinate dove è
        presente l'unita
         */
        private ArrayList<Double> selectPositionUnit(int unita){
        ArrayList<Double> posizione = new ArrayList<>();
        switch (unita){
            case 1://posizione 1
                posizione.add(0,Game.posizioneUnita1X);
                posizione.add(1,Game.posizioneUnita1Y);
                break;
            case 2://posizione 2
                posizione.add(0,Game.posizioneUnita2X);
                posizione.add(1,Game.posizioneUnita2Y);
                isPresent2 = true;
                break;
            case 3://posizione 3
                posizione.add(0,Game.posizioneUnita3X);
                posizione.add(1,Game.posizioneUnita3Y);
                break;
            case 4://posizione 4
                posizione.add(0,Game.posizioneUnita4X);
                posizione.add(1,Game.posizioneUnita4Y);
                break;
            case 5://posizione 5
                posizione.add(0,Game.posizioneUnita5X);
                posizione.add(1,Game.posizioneUnita5Y);
                isPresent5 = true;
                break;

        }

        return posizione;
        }

        /*
        metodo che controlla se le unita sono acquistabili o acquistate e in caso siano
        non acquistabili rende i bottoni di acquisto trasparenti e non cliccabili
         */
        private void controlloAcquistoUnita(){
            //carta
            if(Game.unitaRicicloArrayList.get(2).isPresent() || (Game.puntiSole < 12 && !Game.unitaRicicloArrayList.get(2).isPresent())) {
                findViewById(R.id.buyCartaBtn).setClickable(false);
                findViewById(R.id.buyCartaBtn).setAlpha((float) 0.20);
            }
            //plastica
            if(Game.unitaRicicloArrayList.get(5).isPresent() || (Game.puntiSole < 35 && !Game.unitaRicicloArrayList.get(5).isPresent())) {
                findViewById(R.id.buyPlasticaBtn).setClickable(false);
                findViewById(R.id.buyPlasticaBtn).setAlpha((float) 0.20);
            }
            //alluminio
            if(Game.unitaRicicloArrayList.get(4).isPresent() || (Game.puntiSole < 25 && !Game.unitaRicicloArrayList.get(4).isPresent())) {
                findViewById(R.id.buyAlluminioBtn).setClickable(false);
                findViewById(R.id.buyAlluminioBtn).setAlpha((float) 0.20);
            }
            //acciaio
            if(Game.unitaRicicloArrayList.get(3).isPresent() || (Game.puntiSole < 30 && !Game.unitaRicicloArrayList.get(3).isPresent())) {
                findViewById(R.id.buyAcciaioBtn).setClickable(false);
                findViewById(R.id.buyAcciaioBtn).setAlpha((float) 0.20);
            }
            //tecnologia
            if(Game.unitaRicicloArrayList.get(6).isPresent() || (Game.puntiSole < 40 && !Game.unitaRicicloArrayList.get(6).isPresent())) {
                findViewById(R.id.buyTecnologiaBtm).setClickable(false);
                findViewById(R.id.buyTecnologiaBtm).setAlpha((float) 0.20);
            }
        }

        /*
        metodo che apre il menu delle missioni
         */
        public void openMissionMenu(View view){
            missionContainer.setVisibility(View.VISIBLE);
            setNotClickable();
            setMissione();
            Game.pauseMission = true;
        }

         /*
        metodo che chiude il menu delle missioni
        */
        public void closeMissionMenu(View view){
            missionContainer.setVisibility(View.GONE);
            setClickable();
            Game.pauseMission = false;
        }

        /*
        metodo che apre il menu di pausa
        */
        public void openPauseMenu(View view){
            pauseContainer.setVisibility(View.VISIBLE);
            setNotClickable();
            Game.pauseMission = true;
        }

        /*
        metodo che chiude il menu di pausa
        */
        public void closePauseMenu(View view){
            pauseContainer.setVisibility(View.GONE);
            setClickable();
            Game.pauseMission = false;
        }

        private void setMissione(){
            TextView missioneTxt = findViewById(R.id.missioniTxt);
            String missione = " ";

            switch (MissionFragment.numeroMissione){

                case 1:
                    missione = "Effettua l'upgrade di un unita' di riciclo";
                    break;
                case 2:
                    missione = "Produci un nuovo oggetto con i materiali riciclati";
                    break;
                case 3:
                    missione = "Compra l'unita' di riciclo per la carta";
                    break;
                case 4:
                    missione = "Porta un' unita' al massimo livello";
                    break;
                case 5:
                    missione = "Compra l'unita' di a riciclo per l'acciaio";
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;

            }

            missioneTxt.setText(missione);
        }

        /*
        metodo che imposta la dimensione del layout di gioco per 5"
        e tutte le posizioni degli elementi view in base alla dimensione scelta
        */
        public void small(View view){

        params.width = 1920;
        params.height = 1080;
        gameLayout.setLayoutParams(params);
        add1.setX(100);
        add1.setY(450);
        add2.setX(100);
        add2.setY(750);
        add3.setX(1600);
        add3.setY(150);
        add4.setX(1600);
        add4.setY(450);
        add5.setX(1600);
        add5.setY(750);
        Game.dimension = 1;

        }

    /*
    metodo che imposta la dimensione del layout di gioco per 5"+
    e tutte le posizioni degli elementi view in base alla dimensione scelta
    */
    public void medium(View view){

        params.width = 2180;
        params.height = 1080;
        gameLayout.setLayoutParams(params);
        add1.setX(100);
        add1.setY(450);
        add2.setX(100);
        add2.setY(750);
        add3.setX(1800);
        add3.setY(150);
        add4.setX(1800);
        add4.setY(450);
        add5.setX(1800);
        add5.setY(750);
        Game.dimension = 2;

    }

    /*
   metodo che imposta la dimensione del layout di gioco per 7"+
   e tutte le posizioni degli elementi view in base alla dimensione scelta
   */
    public void big(View view){

        params.width = 2000;
        params.height = 1540;
        gameLayout.setLayoutParams(params);
        add1.setX(200);
        add1.setY(650);
        add2.setX(200);
        add2.setY(1150);
        add3.setX(1700);
        add3.setY(200);
        add4.setX(1700);
        add4.setY(650);
        add5.setX(1700);
        add5.setY(1150);
        paramsShop.height = 300;
        shopContainer.setLayoutParams(paramsShop);
        Game.dimension = 3;

    }

    /*
    metodo che rende visibili gli elementi view posizionati precedentemente
    e imposta, in base alla dimensione scelta, la posizione dei bottoni di upgrade
    dell'inceneritore e dell'unita di riciclo vetro
    */
    public void avanti(View view){
        add1.setVisibility(View.VISIBLE);
        add2.setVisibility(View.VISIBLE);
        add3.setVisibility(View.VISIBLE);
        add4.setVisibility(View.VISIBLE);
        add5.setVisibility(View.VISIBLE);
        missionBtn.setVisibility(View.VISIBLE);
        menuBtn.setVisibility(View.VISIBLE);
        findViewById(R.id.containerResize).setVisibility(View.GONE);
        if(Game.dimension == 1){
            upgradeVetro.setX(300);
            upgradeVetro.setY(90);
            upgradeInceneritore.setX(1090);
            upgradeInceneritore.setY(110);
        }else if(Game.dimension == 2){
            upgradeVetro.setX(350);
            upgradeVetro.setY(90);
            upgradeInceneritore.setX(1180);
            upgradeInceneritore.setY(110);
        }else{
            upgradeVetro.setX(400);
            upgradeVetro.setY(200);
            upgradeInceneritore.setX(1080);
            upgradeInceneritore.setY(160);
        }
        upgradeVetro.setVisibility(View.VISIBLE);
        upgradeInceneritore.setVisibility(View.VISIBLE);
        Game.resize = false;

    }
    }

