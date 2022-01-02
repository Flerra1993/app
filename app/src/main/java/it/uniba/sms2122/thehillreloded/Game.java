package it.uniba.sms2122.thehillreloded;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

// classe che gestisce il gioco, è una View
// istanziata in MainActivity dove viene aggiunta al contentView e parallelizzata in un thread
@SuppressLint("ViewConstructor")
public class Game extends View{

    private final static int CONSTANT_TO_CONVERT_IN_SECONDS = 25;//ogni update avviene ogni 40 millisecondi per cui ogni 25 abbiamo un secondo
    private final Paint paint;// il canvas su cui è disegnata la grafica di schermo
    public static boolean resize = true,isPositionated = false; //indicano, rispettivamente, se bisogna effettuare il ridimensionamento schermo e se gli elementi a schermo sono posizionati
    public static int dimension;// indica la dimesione dello schermo 1 -> telefono 5", 2 -> telefono 5"+, 3 -> tablet 7"+
    private long counterTime;//conteggio del tempo in base agli update
    public static long second = 0;//secondi di gioco
    public static long minute = 0;//minuti di gioco
    public static boolean gameover = false,cleanScreen = false;//indicano, rispettivamente, se il gioco è teminato e se bisogna pulire lo schermo
    private Bitmap pericoloUsura, puntiRiciclaggio, sole, standard,limiteCollina;
    private Bitmap bloccoAcciaio, bloccoVetro, bloccoPlastica, bloccoAlluminio,bloccoPericoloso,bloccoTecnologico,bloccoCarta;
    private Bitmap unitaAcciaio, unitaAlluminio, unitaPlastica,unitaCarta,inceneritore, unitaVetro,unitaTecnologica;
    private int counter= 0;//conteggio per spaw blocchi di rifiuti
    public static ArrayList<Blocco> bloccoArrayList = new ArrayList<>();//contiene i blocchi di rifiuti
    public static ArrayList<UnitaRiciclo> unitaRicicloArrayList = new ArrayList<>();//contiene le unita di riciclo
    public static ArrayList<TimeBar> timeBarArrayList = new ArrayList<>();// contine le barre del tempo per ogni unita di riciclo
    public static boolean pauseShop = false,pauseUpgrade = false, pauseMission = false;//indicano le possibile pause all'interno del gioco(upgrade delle unita, shop delle unita, missioni e menu di pausa per uscire dal gioco)
    private int limiteBottom,limiteDx,limiteSx;//limiti del gioco, dove possono sostare i blocchi di rifiuti
    private int centerX,centerY;//posizione del blocco in caso di riposzionamento(per tempo o per posizione fuori dai limiti)
    public static double posizioneUnitaVetroX,posizioneUnitaInceneritoreX,posizioneUnitaInceneritoreY,posizioneUnitaVetroY,posizioneUnita1X,posizioneUnita3X,posizioneUnita2X,posizioneUnita4X,posizioneUnita5X,posizioneUnita1Y,posizioneUnita3Y,posizioneUnita2Y,posizioneUnita4Y,posizioneUnita5Y; //posizioni delle unita in base alla dimesione dell schermo
    public static int puntiSole = 330;//punti di gioco che si ottengono mediante riciclo dei blocchi
    private Bitmap limiteCollinaInf;
    private MediaPlayer audio = new MediaPlayer(); //Gestisce la riproduzione dei suoni
    private MediaPlayer colonnaSonora; // Gestisce la riproduzione della colonna sonora
    private boolean musicOn = true, soundOn = true;//indicano, rispettivamente, se la musica deve essere attiva e se i suoni di gioco devono essere attivi


    public Game(Context context) {
        super(context);
        paint = new Paint();
        setCanvasGame();//imposta le texture di ogni oggetto di gioco
        initializeUnit();//iniziallizza l'array delle unita
        initializeUnitTimeBar(getContext());//inizializza l'array delle time bar
        colonnaSonora = MediaPlayer.create(getContext(),R.raw.colonna_sonora);//inizializza la colonna sonora del gioco
    }

    /*
    metodo che assegna ad ogni bitmap la propria texture
     */
    private void setCanvasGame(){
        bloccoAcciaio = BitmapFactory.decodeResource(getResources(),R.drawable.acciaio48);
        bloccoAlluminio = BitmapFactory.decodeResource(getResources(),R.drawable.lattina48);
        bloccoPlastica = BitmapFactory.decodeResource(getResources(),R.drawable.plastica48);
        bloccoPericoloso = BitmapFactory.decodeResource(getResources(),R.drawable.pericolosi48);
        bloccoVetro = BitmapFactory.decodeResource(getResources(),R.drawable.vetro48);
        bloccoTecnologico = BitmapFactory.decodeResource(getResources(),R.drawable.tecnologico48);
        bloccoCarta = BitmapFactory.decodeResource(getResources(),R.drawable.carta48);
        unitaAcciaio = BitmapFactory.decodeResource(getResources(),R.drawable.industria_acciaio_96);
        unitaAlluminio = BitmapFactory.decodeResource(getResources(),R.drawable.industria_alluminio96);
        unitaCarta = BitmapFactory.decodeResource(getResources(),R.drawable.industria_carta_96);
        unitaPlastica = BitmapFactory.decodeResource(getResources(),R.drawable.industria_plastica96);
        unitaTecnologica = BitmapFactory.decodeResource(getResources(),R.drawable.industria_tecnologica96);
        unitaVetro = BitmapFactory.decodeResource(getResources(),R.drawable.industria_vetro96);
        inceneritore = BitmapFactory.decodeResource(getResources(),R.drawable.inceneritore96);
        pericoloUsura = BitmapFactory.decodeResource(getResources(),R.drawable.pericolo_usura24);
        puntiRiciclaggio = BitmapFactory.decodeResource(getResources(),R.drawable.pala18x18);
        sole = BitmapFactory.decodeResource(getResources(),R.drawable.puntisole24);
        standard = BitmapFactory.decodeResource(getResources(),R.drawable.standard_bitmap_collisioni);
        limiteCollina = BitmapFactory.decodeResource(getResources(),R.drawable.limite_collina);
        limiteCollinaInf = BitmapFactory.decodeResource(getResources(),R.drawable.limite_collina_inf);
    }
    /*
    metodo che inserisce tutte le possibili unita di riciclo all'interno dell'array
    */
    private void initializeUnit(){
        unitaRicicloArrayList.add(0,new Inceneritore(0,0));
        unitaRicicloArrayList.add(1,new UnitaRicicloVetro(0,0));
        unitaRicicloArrayList.add(2,new UnitaRicicloCarta(0,0));
        unitaRicicloArrayList.add(3,new UnitaRicicloAcciaio(0,0));
        unitaRicicloArrayList.add(4,new UnitaRicicloAlluminio(0,0));
        unitaRicicloArrayList.add(5,new UnitaRicicloPlastica(0,0));
        unitaRicicloArrayList.add(6,new UnitaRicicloTecnologia(0,0));

    }

    /*
    metodo che inserisce le timebar associate ad ogni unita all'interno dell'array
     */
    private void initializeUnitTimeBar(Context context){
        timeBarArrayList.add(0,new TimeBar(context,unitaRicicloArrayList.get(0),120,90,80,true));
        timeBarArrayList.add(1,new TimeBar(context,unitaRicicloArrayList.get(1),120,90,80,false));
        timeBarArrayList.add(2,new TimeBar(context,unitaRicicloArrayList.get(2),120,90,80,false));
        timeBarArrayList.add(3,new TimeBar(context,unitaRicicloArrayList.get(3),120,90,80,false));
        timeBarArrayList.add(4,new TimeBar(context,unitaRicicloArrayList.get(4),120,90,80,false));
        timeBarArrayList.add(5,new TimeBar(context,unitaRicicloArrayList.get(5),120,90,80,false));
        timeBarArrayList.add(6,new TimeBar(context,unitaRicicloArrayList.get(6),120,90,80,false));

    }


    /*
    metodo che definisce la posizione delle unita di riciclo in base alla dimesione dello schermo
    selezionata
    */
    private void setPositions(int dimension) {
        if (dimension == 1) {//5"

            posizioneUnitaInceneritoreX = 910;
            posizioneUnitaInceneritoreY = 15;
            posizioneUnitaVetroX = 50;
            posizioneUnitaVetroY = 100;
            posizioneUnita1X = 50;
            posizioneUnita1Y = 400;
            posizioneUnita4X = 1550;
            posizioneUnita4Y = 400;
            posizioneUnita2X = 50;
            posizioneUnita2Y = 700;
            posizioneUnita3X = 1550;
            posizioneUnita3Y = 100;
            posizioneUnita5X = 1550;
            posizioneUnita5Y = 700;
            limiteBottom = 950;
            limiteSx = 450;
            limiteDx = 1450;
            centerX = 460;
            centerY = 20;
        } else if(dimension == 2){//5"+

            posizioneUnitaInceneritoreX = 990;
            posizioneUnitaInceneritoreY = 15;
            posizioneUnitaVetroX = 100;
            posizioneUnitaVetroY = 100;
            posizioneUnita1X = 100;
            posizioneUnita1Y = 400;
            posizioneUnita4X = 1700;
            posizioneUnita4Y = 400;
            posizioneUnita2X = 100;
            posizioneUnita2Y = 700;
            posizioneUnita3X = 1700;
            posizioneUnita3Y = 100;
            posizioneUnita5X = 1700;
            posizioneUnita5Y = 700;
            limiteBottom = 950;
            limiteSx = 500;
            limiteDx = 1600;
            centerX = 510;
            centerY = 20;

        }else if(dimension == 3)
        {//7"+
            posizioneUnitaInceneritoreX = 900;
            posizioneUnitaInceneritoreY = 50;
            posizioneUnitaVetroX = 150;
            posizioneUnitaVetroY = 200;
            posizioneUnita1X = 150;
            posizioneUnita1Y = 600;
            posizioneUnita4X = 1600;
            posizioneUnita4Y = 600;
            posizioneUnita2X = 150;
            posizioneUnita2Y = 1100;
            posizioneUnita3X = 1600;
            posizioneUnita3Y = 200;
            posizioneUnita5X = 1600;
            posizioneUnita5Y = 1100;
            limiteBottom = 1000;
            limiteSx = 560;
            limiteDx = 1480;
            centerX = 570;
            centerY = 20;
        }

        //posiziona le unita che sono presenti dall'inizio del gioco (Vetro e Inceneritore)
        unitaRicicloArrayList.get(1).setPosizioneX(posizioneUnitaVetroX);
        unitaRicicloArrayList.get(1).setPosizioneY(posizioneUnitaVetroY);
        unitaRicicloArrayList.get(0).setPosizioneY(posizioneUnitaInceneritoreY);
        unitaRicicloArrayList.get(0).setPosizioneX(posizioneUnitaInceneritoreX);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        boolean touch = false;//indica se il tocco sullo schermo è un evento touch o click di un bottone
        if(!pauseShop && !pauseUpgrade && !pauseMission && !gameover) {// se il gioco non è in pausa o terminato

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN://tocco sullo schermo

                    for (Blocco blocco : bloccoArrayList) {

                        if (blocco.isTouched(event.getX(), event.getY()) && !blocco.isActionDown() && !pauseShop) {//se il blocco è toccato e il gioco non è in pausa
                            touch = true;//l'evento è touch
                            blocco.setActionDown(true);
                            blocco.setPick(true);//il blocco è preso

                        }
                    }

                    break;
                case MotionEvent.ACTION_MOVE://il dito si sposta sulllo schermo
                    for (Blocco blocco : bloccoArrayList) {
                        if (blocco.isActionDown()) {
                            blocco.setPosizione(event.getX(), event.getY());//aggiorna la posizione del blocco
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP://il dito non tocca più lo schermo
                    for (Blocco blocco : bloccoArrayList) {
                        if (blocco.isActionDown()) {
                            if (statoCollisione(blocco)) {//se il blocco collide
                                blocco.setPosizione(centerX, centerY);//aggiorna posizione
                                blocco.setColliding(false);//resetta la collisione
                                blocco.setGameOver(false);//il blocco non può scatenare il gameover collidendo con il limite superiore della collina

                            }
                            if(event.getX() >= limiteDx +20 || event.getX() <= limiteSx - 20 || event.getY() >= limiteBottom + 100){// se il blocco supera i limiti di gioco
                                blocco.setTimeToPick(true);//imposta che il blocco deve essere riposizionato
                                blocco.setGameOver(false);//il blocco non può scatenare il gameover collidendo con il limite superiore della collina

                            }
                            blocco.setGameOver(false);//il blocco non può scatenare il gameover collidendo con il limite superiore della collina
                            blocco.setActionDown(false);//termina l'evento actionDown del blocco
                            blocco.setPick(false);// non è più spostato dall'utente
                            blocco.setCounterPick(0);//azzera il contatore del tempo di movimento del blocco effettuato dall'utente
                        }
                    }
                    break;

            }
        }
    return  touch; //true-> evento touch
                   //false-> evento click
    }

    /*
    metodo che imposta l'effetto sonoro del riciclaggio
     */
    private void industrieSound(){
        if(soundOn) {
            audio = MediaPlayer.create(getContext(), R.raw.industrie_sound);
            audio.start();
        }
    }

    /*
    metodo che imposta la colonna sonora del gioco
     */
    private void music(){
        if(musicOn){//se la musica è richiesta
            if(!colonnaSonora.isLooping()) {//se non è in loop
                colonnaSonora.setLooping(true);//imposta il media player in modalita loop
                colonnaSonora.start();//avvia il media player
                }

            }else //altrimenti
        {
            colonnaSonora.stop();
        }
        }

        /*
        metodo che, in base al blocco e all'unita di riciclo, effettua o meno il riciclo del blocco
         */
    private boolean setRicicloBlocco(UnitaRiciclo unitaRiciclo, Blocco blocco){

            //se il blocco e l'unita corrispondono e il blocco è preso dall'utente
            if(unitaRiciclo instanceof UnitaRicicloAcciaio && blocco instanceof BloccoAcciaio && blocco.isPick()) {
                if (Blocco.isCollisionDetected(bloccoAcciaio, (int) blocco.getPosizioneX(), (int) blocco.getPosizioneY(), unitaAcciaio, (int) unitaRiciclo.getPosizioneX(), (int) unitaRiciclo.getPosizioneY())) {//se il blocco collide con l'unita di riciclo
                    unitaRiciclo.setOccupated(true);//l'unita è occupata nel riciclo
                    industrieSound();//riproduce l'audio dell'industria in lavorazione
                    return true;
                }
            }
        //se il blocco e l'unita corrispondono e il blocco è preso dall'utente
        if(unitaRiciclo instanceof UnitaRicicloAlluminio && blocco instanceof BloccoAlluminio && blocco.isPick()) {
            if (Blocco.isCollisionDetected(bloccoAlluminio, (int) blocco.getPosizioneX(), (int) blocco.getPosizioneY(), unitaAlluminio, (int) unitaRiciclo.getPosizioneX(), (int) unitaRiciclo.getPosizioneY())) {//se il blocco collide con l'unita di riciclo
                unitaRiciclo.setOccupated(true);//l'unita è occupata nel riciclo
                industrieSound();//riproduce l'audio dell'industria in lavorazione
                return true;
            }
        }
        //se il blocco e l'unita corrispondono e il blocco è preso dall'utente
        if(unitaRiciclo instanceof UnitaRicicloCarta && blocco instanceof BloccoCarta && blocco.isPick()) {
            if (Blocco.isCollisionDetected(bloccoCarta, (int) blocco.getPosizioneX(), (int) blocco.getPosizioneY(), unitaCarta, (int) unitaRiciclo.getPosizioneX(), (int) unitaRiciclo.getPosizioneY())) {//se il blocco collide con l'unita di riciclo
                unitaRiciclo.setOccupated(true);//l'unita è occupata nel riciclo
                industrieSound();//riproduce l'audio dell'industria in lavorazione
                return true;
            }
        }
        //se il blocco e l'unita corrispondono e il blocco è preso dall'utente
        if(unitaRiciclo instanceof UnitaRicicloPlastica && blocco instanceof BloccoPlastica && blocco.isPick()) {
            if (Blocco.isCollisionDetected(bloccoPlastica, (int) blocco.getPosizioneX(), (int) blocco.getPosizioneY(), unitaPlastica, (int) unitaRiciclo.getPosizioneX(), (int) unitaRiciclo.getPosizioneY())) {//se il blocco collide con l'unita di riciclo
                unitaRiciclo.setOccupated(true);//l'unita è occupata nel riciclo
                industrieSound();//riproduce l'audio dell'industria in lavorazione
                return true;
            }
        }
        //se il blocco e l'unita corrispondono e il blocco è preso dall'utente
        if(unitaRiciclo instanceof UnitaRicicloTecnologia && blocco instanceof BloccoTecnologica && blocco.isPick()) {
            if (Blocco.isCollisionDetected(bloccoTecnologico, (int) blocco.getPosizioneX(), (int) blocco.getPosizioneY(), unitaTecnologica, (int) unitaRiciclo.getPosizioneX(), (int) unitaRiciclo.getPosizioneY())) {//se il blocco collide con l'unita di riciclo
                unitaRiciclo.setOccupated(true);//l'unita è occupata nel riciclo
                industrieSound();//riproduce l'audio dell'industria in lavorazione
                return true;
            }
        }
        //se il blocco e l'unita corrispondono e il blocco è preso dall'utente
        if(unitaRiciclo instanceof UnitaRicicloVetro && blocco instanceof BloccoVetro && blocco.isPick()) {
            if (Blocco.isCollisionDetected(bloccoVetro, (int) blocco.getPosizioneX(), (int) blocco.getPosizioneY(), unitaVetro, (int) unitaRiciclo.getPosizioneX(), (int) unitaRiciclo.getPosizioneY())) {//se il blocco collide con l'unita di riciclo
                unitaRiciclo.setOccupated(true);//l'unita è occupata nel riciclo
                industrieSound();//riproduce l'audio dell'industria in lavorazione
                return true;
            }
        }
        //se l'unita è l'inceneritore e il blocco è preso dall'utente
        if(unitaRiciclo instanceof Inceneritore  && blocco.isPick()) {
            if (Blocco.isCollisionDetected(bloccoPericoloso, (int) blocco.getPosizioneX(), (int) blocco.getPosizioneY(), inceneritore, (int) unitaRiciclo.getPosizioneX(), (int) unitaRiciclo.getPosizioneY()) && puntiSole > 2) {//se il blocco collide con l'unita di riciclo e si hanno almeno 2 punti sole
                unitaRiciclo.setOccupated(true);//l'unita è occupata nel riciclo
                industrieSound();//riproduce l'audio dell'industria in lavorazione
                puntiSole = puntiSole - 2; //toglie due punti sole da quelli presenti
                return true;
            }
        }

        return false;

    }

    /*
      metodo che disegna a schermo il tempo di gioco
     */
    private void drawTime(Canvas canvas){
        String minuteString;//stringa che contiene i minuti
        String secondString;//stringa che contiene i secondi
        int color = ContextCompat.getColor(getContext(), R.color.white);//intero che indica il colore del testo
        paint.setColor(color);//imposta il colore

        if(minute < 10){// se i minuti sono meno di 10
            minuteString = "0" + minute + ":"; //aggiunge uno 0 prima del numero per avere il formato 00m
        }else//altrimenti
        {
            minuteString = minute + ":";
        }
        if(second < 10){// se i secondi sono minori di 10
            secondString = "0" + second; //aggiunge uno 0 prima del numero per avere il formato 00s
        }else//altrimenti
        {
            secondString = second + "";
        }

        if(dimension == 3){//se la dimensione è tablet
            paint.setTextSize(55);//imposta la dimensione
            canvas.drawText("Tempo: " + minuteString + secondString,350,66,paint);//disegna il tempo

        }else{//altrimenti
            paint.setTextSize(50);//imposta la dimensione
            canvas.drawText("Tempo: " + minuteString + secondString,350,50,paint);//disegna il tempo
        }

    }

    /*
    metodo che aggiorna il contatore del tempo in cui l'utente sposta il blocco
     */
    private void isTimeToPick(Blocco blocco){
        if(blocco.isPick()){//se l'utente prende l'oggetto
            blocco.setCounterPick(blocco.getCounterPick()+1);//aggiorna il counter
            if(blocco.getCounterPick() / CONSTANT_TO_CONVERT_IN_SECONDS == 3){//se il tempo trascorso è 3 secondi
                blocco.setGameOver(false);//il blocco non può causare il gameover con la collisione con il limite della collina
                blocco.setActionDown(false);//il dito non tocca più lo schermo dove è presente l'oggetto
                blocco.setTimeToPick(true);//l'oggetto deve essere riposizionato
                blocco.setPick(false);//l'oggetto non è più spostato dall'utente
                blocco.setCounterPick(0);//azzero il contatore del tempo di spostamento da parte dell'utente
            }

        }
    }

    /*
    metodo che disegna a schermo i punti sole posseduti dal giocatore
     */
    private void drawPuntiSole(Canvas canvas) {

        String puntiSoleS = Integer.toString(puntiSole);//converte i punti sole in stringa
        int color = ContextCompat.getColor(getContext(), R.color.white);//intero che indica il colore del testo
        paint.setColor(color);//imposta il colore del testo
        if(dimension == 3){//se la dimensione è tablet
            paint.setTextSize(55);//imposta la dimensione del testo
            canvas.drawBitmap(sole, 100, 0, paint);//disegna il simbolo dei punti sole
            canvas.drawText(" : " + puntiSoleS, 175, 66, paint);//disegna il numero di punti sole

        }else {//altrimenti

            paint.setTextSize(50);//imposta la dimesione del testo
            canvas.drawBitmap(sole, 100, 0, paint);//disegna il simbolo dei punti sole
            canvas.drawText(" : " + puntiSoleS, 160, 50, paint);//disegna il numero di punti sole

        }



    }
    /*
       metodo che determina se un blocco collide con un altro
    */
    private boolean statoCollisione(Blocco blocco){
            //cicla la lista dei blocchi presenti
            for (Blocco presente : bloccoArrayList) {
                //se i blocchi sono diversi e il numero di oggetti è almeno 2
                if (blocco.getId() != presente.getId() && bloccoArrayList.size() > 1) {
                        //se il blocco collide con altri e non è spostato dall'utente
                        //NOTA: standard è un'immagine di un rettangolo, poiché a causa di troppi controlli, per ogni possibile bitmap, il gioco aveva parecchi cali di frame
                        if(Blocco.isCollisionDetected(standard,(int) blocco.getPosizioneX(),(int) blocco.getPosizioneY(),standard,(int) presente.getPosizioneX(),(int) presente.getPosizioneY()) && !presente.isPick() ){
                            blocco.setGameOver(true);//il blocco può causare il gameover in caso di collisione con il limite della collina
                            return true;
                        }
                    }
                }


        return false;
    }

    /*
    metodo che disegna il simbolo di pericolo usura sull'unita riciclo quando
    il l'unita riciclo è vicina al downgrade
     */
    private void drawPericoloUsura(Canvas canvas, UnitaRiciclo unitaRiciclo){
        //se l'unita riciclo è nella posizione coperta dal menu di acquisto unita riciclo (parte bassa dello schermo) e il gioco è in pausa per l'upgrade o
        //per l'acquisto di un' unita
        if(!((unitaRiciclo.getPosizioneSchermo() == 2 || unitaRiciclo.getPosizioneSchermo() == 5) && (pauseUpgrade || pauseShop))){
            //se l'unita riciclo ha riciclato piu di 39 oggetti
            if(unitaRiciclo.getLvlUsura() >= 40) {
                canvas.drawBitmap(pericoloUsura, (float) unitaRiciclo.getPosizioneX() + 85, (float) unitaRiciclo.getPosizioneY() + 100, paint);//disegna il simbolo pericolo usura
            }
        }
    }

    /*
    metodo che disegna i punti riciclaggio per ogni unita di riciclo (escluso l'inceneritore)
     */
    private void drawPuntiRiciclaggio(Canvas canvas, UnitaRiciclo unitaRiciclo){
        int color = ContextCompat.getColor(getContext(), R.color.white);//intero che indica il colore del testo
        paint.setColor(color);//imposta il colore del testo
        if(dimension == 3){//se dimesione tablet
            paint.setTextSize(40);//imposta grandezza del testo
        }else {//altrimenti
            paint.setTextSize(35);//imposta gradezza del testo
        }
        paint.setFakeBoldText(true);

        //se l'unita riciclo è nella posizione coperta dal menu di acquisto unita riciclo (parte bassa dello schermo) e il gioco è in pausa per l'upgrade o
        //per l'acquisto di un' unita
        if(!((unitaRiciclo.getPosizioneSchermo() == 2 || unitaRiciclo.getPosizioneSchermo() == 5) && (pauseUpgrade || pauseShop))){
            //se l'unita è presente sullo schermo e non è l'inceneritore
            if(unitaRiciclo.isPresent() && !(unitaRiciclo instanceof Inceneritore)) {
                canvas.drawBitmap(puntiRiciclaggio, (float) unitaRiciclo.getPosizioneX() + 200, (float) unitaRiciclo.getPosizioneY() + 105, paint);//disegna il simbolo del punto riciclo
                canvas.drawText( ":", (float)unitaRiciclo.getPosizioneX() + 232, (float) unitaRiciclo.getPosizioneY() + 130, paint);//disegna ":"
                canvas.drawText( " " + unitaRiciclo.getPuntiRiciclaggio(), (float)unitaRiciclo.getPosizioneX() + 239, (float) unitaRiciclo.getPosizioneY() + 133, paint);//disegna il numero di punti riclo

            }
        }
    }


    /*
    metodo che determina quando il gioco termina
     */
    private void gameOver(Blocco blocco){
        if(dimension == 1) {// se la dimansione è 5"
            //se il blocco collide con il limite della collina, il blocco non è preso dall'utente ed è adatto a scatenare il gameover (deve collidere con un altro blocco e collidere con la linea)
            if (Blocco.isCollisionDetected(standard, (int) blocco.getPosizioneX(),(int) blocco.getPosizioneY(), limiteCollina, (int) unitaRicicloArrayList.get(1).getPosizioneX() + 300, (int) unitaRicicloArrayList.get(1).getPosizioneY() + 200) && !blocco.isPick() && blocco.isGameOver()){
                gameover= true;

            }
        }else if(dimension == 2){//se la dimensione è 5"+
            //se il blocco collide con il limite della collina, il blocco non è preso dall'utente ed è adatto a scatenare il gameover (deve collidere con un altro blocco e collidere con la linea)
            if (Blocco.isCollisionDetected(standard, (int) blocco.getPosizioneX(),(int) blocco.getPosizioneY(), limiteCollina, (int) unitaRicicloArrayList.get(1).getPosizioneX() + 350, (int) unitaRicicloArrayList.get(1).getPosizioneY() + 200) && !blocco.isPick() && blocco.isGameOver()){
                gameover = true;
            }
        }else{//se la dimensione è 7"+
            //se il blocco collide con il limite della collina, il blocco non è preso dall'utente ed è adatto a scatenare il gameover (deve collidere con un altro blocco e collidere con la linea)
            if (Blocco.isCollisionDetected(standard, (int) blocco.getPosizioneX(),(int) blocco.getPosizioneY(), limiteCollina, (int) unitaRicicloArrayList.get(1).getPosizioneX() + 320, (int) unitaRicicloArrayList.get(1).getPosizioneY() + 180)&& !blocco.isPick() && blocco.isGameOver()){
                gameover = true;
            }
        }
    }

    /*
    metodo che disegna la scritta gameover sullo schermo
     */
    private void drawGameOver(Canvas canvas){
        int color = ContextCompat.getColor(getContext(),R.color.timeTimeBaar);
        paint.setColor(color);//imposta il colore
        paint.setTextSize(150);//imposta la dimesione del testo
        if(dimension == 1) {//dimesione 5"
            canvas.drawText("GameOver", 600, 500, paint);//disegna testo
        }else if(dimension == 3){ //dimesione 7"+
            canvas.drawText("GameOver", 600, 800, paint);//disegna testo
        }else //dimensione 5"+
        {
            canvas.drawText("GameOver", 700, 500, paint);//disegna testo
        }
    }

    public  void onDraw(Canvas canvas){
        if(!resize) {//se non si sta effettuando il ridimensionamento schermo

            for (UnitaRiciclo unitaRiciclo : unitaRicicloArrayList) {

                if (unitaRiciclo instanceof Inceneritore && unitaRiciclo.isPresent()) {//se l'inceneritore è presente
                    //se il gioco non è in pausa per il menu missione,pausa o lo schermo deve essere pulito per gameover e la dimesione dello schermo è 7"+
                    if (!((pauseMission || cleanScreen) && dimension != 3))
                        canvas.drawBitmap(inceneritore, (float) unitaRiciclo.getPosizioneX(), (float) unitaRiciclo.getPosizioneY(), paint);//disegna unita riciclo
                }

                if (unitaRiciclo instanceof UnitaRicicloAcciaio && unitaRiciclo.isPresent()) {//se l'unita riciclo acciaio è presente
                    //se l'unita è presente nella posizione 2 o 5 e il gioco è in pausa per lo shop o l'upgrade non disegnare, altrimenti disegna
                    if (!((unitaRiciclo.getPosizioneSchermo() == 2 || unitaRiciclo.getPosizioneSchermo() == 5) && (pauseUpgrade || pauseShop))) {
                        canvas.drawBitmap(unitaAcciaio, (float) unitaRiciclo.getPosizioneX(), (float) unitaRiciclo.getPosizioneY(), paint);//disegna unita riciclo
                    }
                }
                if (unitaRiciclo instanceof UnitaRicicloAlluminio && unitaRiciclo.isPresent()) {//se l'unita riciclo alluminio è presente
                    //se l'unita è presente nella posizione 2 o 5 e il gioco è in pausa per lo shop o l'upgrade non disegnare, altrimenti disegna
                    if (!((unitaRiciclo.getPosizioneSchermo() == 2 || unitaRiciclo.getPosizioneSchermo() == 5) && (pauseUpgrade || pauseShop))) {
                        canvas.drawBitmap(unitaAlluminio, (float) unitaRiciclo.getPosizioneX(), (float) unitaRiciclo.getPosizioneY(), paint);//disegna unita riciclo
                    }
                }
                if (unitaRiciclo instanceof UnitaRicicloVetro && unitaRiciclo.isPresent()) {//se l'unita riciclo vetro è presente
                    canvas.drawBitmap(unitaVetro, (float) unitaRiciclo.getPosizioneX(), (float) unitaRiciclo.getPosizioneY(), paint);//disegna unita riciclo
                }
                if (unitaRiciclo instanceof UnitaRicicloCarta && unitaRiciclo.isPresent()) {//se l'unita riciclo carta è presente
                    //se l'unita è presente nella posizione 2 o 5 e il gioco è in pausa per lo shop o l'upgrade non disegnare, altrimenti disegna
                    if (!((unitaRiciclo.getPosizioneSchermo() == 2 || unitaRiciclo.getPosizioneSchermo() == 5) && (pauseUpgrade || pauseShop))) {
                        canvas.drawBitmap(unitaCarta, (float) unitaRiciclo.getPosizioneX(), (float) unitaRiciclo.getPosizioneY(), paint);//disegna unita riciclo
                    }
                }
                if (unitaRiciclo instanceof UnitaRicicloTecnologia && unitaRiciclo.isPresent()) {//se l'unita riciclo tecnologia è presente
                    //se l'unita è presente nella posizione 2 o 5 e il gioco è in pausa per lo shop o l'upgrade non disegnare, altrimenti disegna
                    if (!((unitaRiciclo.getPosizioneSchermo() == 2 || unitaRiciclo.getPosizioneSchermo() == 5) && (pauseUpgrade || pauseShop))) {
                        canvas.drawBitmap(unitaTecnologica, (float) unitaRiciclo.getPosizioneX(), (float) unitaRiciclo.getPosizioneY(), paint);//disegna unita riciclo
                    }
                }
                if (unitaRiciclo instanceof UnitaRicicloPlastica && unitaRiciclo.isPresent()) {//se l'unita riciclo plastica è presente
                    //se l'unita è presente nella posizione 2 o 5 e il gioco è in pausa per lo shop o l'upgrade non disegnare, altrimenti disegna
                    if (!((unitaRiciclo.getPosizioneSchermo() == 2 || unitaRiciclo.getPosizioneSchermo() == 5) && (pauseUpgrade || pauseShop))) {
                        canvas.drawBitmap(unitaPlastica, (float) unitaRiciclo.getPosizioneX(), (float) unitaRiciclo.getPosizioneY(), paint);//disegna unita riciclo
                    }
                }
                drawPericoloUsura(canvas, unitaRiciclo);//disegna pericolo usura
                drawPuntiRiciclaggio(canvas, unitaRiciclo);//disegna punti riciclaggio
            }

            for (TimeBar timeBar : timeBarArrayList) {
                if (timeBar.isPresent()) {
                    //(se la timebar è assegnata all'unita in posizione 5 o 2 e il gioco è in pausa per lo shop o l'upgrade)->false, altrimenti -> true
                    if (!((timeBar.getPosizioneSchermo() == 2 || timeBar.getPosizioneSchermo() == 5) && (pauseUpgrade || pauseShop)))
                        //(se la timebar appartiene all'inceneritore e il gioco è in pausa per menu missioni o per gameover e la dimensione è 5" o 5"+)-> false, altrimenti -> true
                        if (!(timeBar.getUnitaRiciclo() instanceof Inceneritore && (pauseMission || cleanScreen) && dimension != 3)) {
                            timeBar.draw(canvas);//disegna timebar
                        }
                }
            }

            if (!pauseMission && !cleanScreen) {//se il gioco non è in pausa per il menu delle missioni o per il menu di pausa
                if (dimension == 2) {//se la dimensione è 5"+
                    canvas.drawBitmap(limiteCollina, (float) unitaRicicloArrayList.get(1).getPosizioneX() + 350, (float) unitaRicicloArrayList.get(1).getPosizioneY() + 200, paint);//disegna limite collina superiore
                } else if (dimension == 1) {//se la dimensione è 5"
                    canvas.drawBitmap(limiteCollina, (float) unitaRicicloArrayList.get(1).getPosizioneX() + 300, (float) unitaRicicloArrayList.get(1).getPosizioneY() + 200, paint);//disegna limite collina superiore

                } else {// se la dimensione è 7"+
                    canvas.drawBitmap(limiteCollina, (float) unitaRicicloArrayList.get(1).getPosizioneX() + 320, (float) unitaRicicloArrayList.get(1).getPosizioneY() + 180, paint);//disegna limite collina superiore
                    canvas.drawBitmap(limiteCollinaInf,(float) unitaRicicloArrayList.get(1).getPosizioneX() + 320, (float) unitaRicicloArrayList.get(1).getPosizioneY() + 905, paint);//disegna limite collina inferiore
                }
            }

            if (!pauseShop && !pauseUpgrade && !pauseMission && !cleanScreen) {// se il gioco non è in pausa
                for (Blocco blocco : bloccoArrayList) {//disegna i blocchi

                    if (blocco instanceof BloccoAcciaio) {
                        canvas.drawBitmap(bloccoAcciaio, (float) blocco.getPosizioneX(), (float) blocco.getPosizioneY(), paint);

                    } else if (blocco instanceof BloccoVetro) {

                        canvas.drawBitmap(bloccoVetro, (float) blocco.getPosizioneX(), (float) blocco.getPosizioneY(), paint);


                    } else if (blocco instanceof BloccoCarta) {

                        canvas.drawBitmap(bloccoCarta, (float) blocco.getPosizioneX(), (float) blocco.getPosizioneY(), paint);


                    } else if (blocco instanceof BloccoAlluminio) {

                        canvas.drawBitmap(bloccoAlluminio, (float) blocco.getPosizioneX(), (float) blocco.getPosizioneY(), paint);


                    } else if (blocco instanceof BloccoPlastica) {

                        canvas.drawBitmap(bloccoPlastica, (float) blocco.getPosizioneX(), (float) blocco.getPosizioneY(), paint);


                    } else if (blocco instanceof BloccoTecnologica) {

                        canvas.drawBitmap(bloccoTecnologico, (float) blocco.getPosizioneX(), (float) blocco.getPosizioneY(), paint);


                    } else if (blocco instanceof BloccoPericoloso) {

                        canvas.drawBitmap(bloccoPericoloso, (float) blocco.getPosizioneX(), (float) blocco.getPosizioneY(), paint);
                    }


                }
            }

            drawPuntiSole(canvas);//disegna punti sole
            drawTime(canvas);//disegna tempo di gioco
            if (gameover && !cleanScreen) {//se il gioco è terminato e lo schermo non deve ancora essere pulito
                drawGameOver(canvas);//disegna il gameover
            }
        }
        }


        /*
            metodo che definisce il tempo di lavorazione dell'unita, ovvero il
            tempo in cui è occupata
         */
    private void occupatedUnit(UnitaRiciclo unitaRiciclo){
        int time;

            //se l'unita è al livello 1
            if(unitaRiciclo.isOccupated() && unitaRiciclo.getLvlUpgrade() == 1){
                if(unitaRiciclo instanceof Inceneritore){
                    time = 10;
                } else
                {
                    time = 5;
                }

                //aumenta il contatore dell'unita che indica il tempo di occupazione
                unitaRiciclo.setCounterOccupated(unitaRiciclo.getCounterOccupated()+1);
                //se il contatore convertito in secondi è maggiore del di 5s
                if(unitaRiciclo.getCounterOccupated() / CONSTANT_TO_CONVERT_IN_SECONDS >= time){
                    unitaRiciclo.setPuntiRiciclaggio(unitaRiciclo.getPuntiRiciclaggio() + 1);//aggiorno i punti riciclaggio
                    unitaRiciclo.setTotOggettiRiciclati(unitaRiciclo.getTotOggettiRiciclati() + 1);//aggiorno il totale oggetti riciclati
                    unitaRiciclo.setOccupated(false);//imposta l'unita nuovamente libero
                    audio.stop();//interrompe l'audio
                    unitaRiciclo.setCounterOccupated(0);//azzera il countatore del tempo di occupazione
                }
                //se l'unita è al livello 2
            }else if(unitaRiciclo.isOccupated() && unitaRiciclo.getLvlUpgrade() == 2){
                if(unitaRiciclo instanceof Inceneritore){
                    time = 10;
                } else
                {
                    time = 3;
                }
                //aumenta il contatore dell'unita che indica il tempo di occupazione
                unitaRiciclo.setCounterOccupated(unitaRiciclo.getCounterOccupated()+1);
                //se il contatore convertito in secondi è maggiore del di 3s
                if(unitaRiciclo.getCounterOccupated() / CONSTANT_TO_CONVERT_IN_SECONDS >= time){
                    unitaRiciclo.setLvlUsura(unitaRiciclo.getLvlUsura() + 1);//aumenta il livello di usura dell'unita
                    unitaRiciclo.setPuntiRiciclaggio(unitaRiciclo.getPuntiRiciclaggio() + 1);//aggiorna punti riciclaggio
                    unitaRiciclo.setTotOggettiRiciclati(unitaRiciclo.getTotOggettiRiciclati() + 1);//aggiorna totale oggetti riciclati
                    unitaRiciclo.setOccupated(false);//imposta l'unita nuovamente libera
                    audio.stop();//interrompe l'audio
                    unitaRiciclo.setCounterOccupated(0);//azzera il contatore del tempo di occupazione
                }
            }else if(unitaRiciclo.isOccupated() && unitaRiciclo.getLvlUpgrade() == 3)
            {
                if(unitaRiciclo instanceof Inceneritore){
                    time = 10;
                } else
                {
                    time = 2;
                }

                //aumenta il contatore dell'unita che indica il tempo di occupazione
                unitaRiciclo.setCounterOccupated(unitaRiciclo.getCounterOccupated()+1);
                //se il contatore convertito in secondi è maggiore del di 2s
                if(unitaRiciclo.getCounterOccupated() / CONSTANT_TO_CONVERT_IN_SECONDS >= time){
                    unitaRiciclo.setLvlUsura(unitaRiciclo.getLvlUsura() + 1);//aumenta il livello di usura dell'unita
                    unitaRiciclo.setPuntiRiciclaggio(unitaRiciclo.getPuntiRiciclaggio() + 1);//aggiorna punti riciclaggio
                    unitaRiciclo.setTotOggettiRiciclati(unitaRiciclo.getTotOggettiRiciclati() + 1);//aggiorna totale oggetti riciclati
                    unitaRiciclo.setOccupated(false);//imposta l'unita nuovamente libera
                    audio.stop();//interrompe l'audio
                    unitaRiciclo.setCounterOccupated(0);//azzera il contatore del tempo di occupazione
                }
            }

    }

    /*
    metodo che in base al tempo di gioco imposta decide la probabilità di spawn dei blocchi
     */
    private void setBlocco(){

        int probabilitaVetro,probabilitaCarta,probabilitaAcciaio,probabilitaAlluminio,probabilitaPericoloso,probabilitaPlastica,probabilitaTecnologia;

        if(minute <= 2){
            probabilitaVetro = 600;
            probabilitaCarta = 750;
            probabilitaAlluminio = 900;
            probabilitaAcciaio = 970;
            probabilitaPlastica = 980;
            probabilitaTecnologia = 990;
            probabilitaPericoloso = 1000;
        }else if(minute == 3){
            probabilitaVetro = 450;
            probabilitaCarta = 700;
            probabilitaAlluminio = 850;
            probabilitaAcciaio = 950;
            probabilitaPlastica = 970;
            probabilitaTecnologia = 990;
            probabilitaPericoloso = 1000;
        }else if(minute == 4){
            probabilitaVetro = 400;
            probabilitaCarta = 600;
            probabilitaAlluminio = 800;
            probabilitaAcciaio = 900;
            probabilitaPlastica = 950;
            probabilitaTecnologia = 970;
            probabilitaPericoloso = 1000;
        }else if(minute == 5){
            probabilitaVetro = 350;
            probabilitaCarta = 500;
            probabilitaAlluminio = 650;
            probabilitaAcciaio = 850;
            probabilitaPlastica = 950;
            probabilitaTecnologia = 980;
            probabilitaPericoloso = 1000;
        }else if(minute == 7 || minute == 6 || minute == 8 || minute == 9){
            probabilitaVetro = 350;
            probabilitaCarta = 450;
            probabilitaAlluminio = 600;
            probabilitaAcciaio = 740;
            probabilitaPlastica = 850;
            probabilitaTecnologia = 950;
            probabilitaPericoloso = 1000;
        }else if(minute == 10 || minute == 11 || minute == 12){
            probabilitaVetro = 350;
            probabilitaCarta = 500;
            probabilitaAcciaio = 650;
            probabilitaAlluminio = 800;
            probabilitaPlastica = 900;
            probabilitaTecnologia = 990;
            probabilitaPericoloso = 1000;
        }else{
            probabilitaVetro = 150;
            probabilitaCarta = 300;
            probabilitaAcciaio = 450;
            probabilitaAlluminio = 600;
            probabilitaPlastica = 750;
            probabilitaTecnologia = 900;
            probabilitaPericoloso = 1000;
        }

        int x = (int)(Math.random()*1000);

        if(x < probabilitaVetro){
            bloccoArrayList.add(new BloccoVetro(Math.random() * ((limiteDx - 200) - (limiteSx + 200)) + (limiteSx + 200), 150));
        }else if( x > probabilitaVetro && x <= probabilitaCarta ){
            bloccoArrayList.add(new BloccoCarta(Math.random() * ((limiteDx - 200) - (limiteSx + 200)) + (limiteSx + 200), 150));
        } else if(x > probabilitaCarta && x < probabilitaAlluminio){
            bloccoArrayList.add(new BloccoAlluminio(Math.random() * ((limiteDx - 200) - (limiteSx + 200)) + (limiteSx + 200), 150));
        } else if( x >= probabilitaAlluminio && x <= probabilitaAcciaio ){
            bloccoArrayList.add(new BloccoAcciaio(Math.random() * ((limiteDx - 200) - (limiteSx + 200)) + (limiteSx + 200), 150));
        } else if(x > probabilitaAcciaio && x < probabilitaPlastica){
            bloccoArrayList.add(new BloccoPlastica(Math.random() * ((limiteDx - 200) - (limiteSx + 200)) + (limiteSx + 200), 150));
        } else if (x >= probabilitaPlastica && x < probabilitaTecnologia){
            bloccoArrayList.add(new BloccoTecnologica(Math.random() * ((limiteDx - 200) - (limiteSx + 200)) + (limiteSx + 200), 150));
        } else if (x >= probabilitaTecnologia && x <= probabilitaPericoloso) {
            bloccoArrayList.add(new BloccoPericoloso(Math.random() * ((limiteDx - 200) - (limiteSx + 200)) + (limiteSx + 200), 150));
        }
    }

    /*
    metodo che effettua il downgrade dell'unita di riciclo
     */
    private void downgradeUnit(UnitaRiciclo unitaRiciclo){
        if (unitaRiciclo.getLvlUpgrade() > 1) {// se l'unita riciclo è di livello maggiore di 1
            if(unitaRiciclo.getLvlUsura() == 48) {// se l'unita riciclo ha effettuato il riciclo di 48 blocchi
                unitaRiciclo.setLvlUpgrade(unitaRiciclo.getLvlUpgrade() - 1);//livello precedente
                unitaRiciclo.setLvlUsura(0);//imposta il livello usura a 0
            }
        }

    }

    /*
    metodo che calcola il tempo di gioco
     */
    private void setTime(){
        counterTime++;
        second = counterTime/CONSTANT_TO_CONVERT_IN_SECONDS;
        if(second >= 60){
            second = 0;
            counterTime = 0;
            minute++;
        }
    }

    /*
        metodo che aggiorna la logica di gioco
     */
    public void update(){
    if(!resize) {// se lo schermo non deve essere ridimensionato

        if(!isPositionated){// se le unita non sono posizionate
            setPositions(dimension);//inizializza le unita in base alla dimensione
            isPositionated = true;//unita posizionate
        }

        //se le unita vetro e inceneritore non sono presenti, rendile presenti
        if (!unitaRicicloArrayList.get(0).isPresent() && !unitaRicicloArrayList.get(1).isPresent() && !timeBarArrayList.get(0).isPresent() && !timeBarArrayList.get(1).isPresent()) {
            unitaRicicloArrayList.get(0).setPresent(true);
            unitaRicicloArrayList.get(1).setPresent(true);
            timeBarArrayList.get(0).setPresent(true);
            timeBarArrayList.get(1).setPresent(true);
        }


        //se il gioco non è in pausa
        if (!pauseShop && !pauseUpgrade && !pauseMission && !gameover) {
            music();//avvia musica
            setTime();//avvia tempo di gioco
            counter++;
            if (counter / CONSTANT_TO_CONVERT_IN_SECONDS == 5) {// ogni 5 secondi
                counter = 0;
                setBlocco();//spawn blocco
            }

            for (Blocco blocco : bloccoArrayList) {
                blocco.update(limiteBottom, centerX, centerY);//aggiorna posizione blocco

            }

            for (Blocco blocco : bloccoArrayList) {
                gameOver(blocco);
                blocco.setColliding(statoCollisione(blocco));
                isTimeToPick(blocco);
            }

            for (UnitaRiciclo unitaRiciclo : unitaRicicloArrayList) {

                if (unitaRiciclo.isPresent()) {//se l'unita è presente
                    downgradeUnit(unitaRiciclo);
                    occupatedUnit(unitaRiciclo);
                    if (!unitaRiciclo.isOccupated()) {//se l'unita è libera
                        bloccoArrayList.removeIf(blocco -> setRicicloBlocco(unitaRiciclo, blocco));//rimuovi il blocco se il blocco se collide con l'unita di riciclo adatta
                    }
                }
            }

        }
    }
    }

}
