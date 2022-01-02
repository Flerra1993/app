package it.uniba.sms2122.thehillreloded;


import android.graphics.Bitmap;
import android.graphics.Rect;

/*
Classe che rappresenta il blocco da riciclare generico
 */
public class Blocco {


    private final static double MAX_VELOCITA = 5; //spostamento verso il basso
    private final static double STOP = 0;
    private double posizioneX;
    private double posizioneY;
    private final double id;//identificativo univoco del blocco
    private boolean isColliding = false;//indica se il blocco sta collidendo
    private boolean isTimeToPick = false;// indica se il blocco deve essere lasciato
    private boolean isPick= false; //indica se il blocco è preso dall'utente
    private boolean actionDown = false;//indica se l'utente ha cliccato sullo schermo
    private int counterPick = 0;//conta i secondi che l'utente muove il blocco
    private boolean isGameOver = false;//indica se il blocco può comportare il gameover (per evitare che la collisione di un blocco in discesa imposti il gameover)



    public Blocco(double posizioneX, double posizioneY) {
        this.posizioneX = posizioneX;
        this.posizioneY = posizioneY;
        id = Math.random() * 10000000; //id del blocco creato randomicamente per distinguere ogni blocco
    }

    /*
    metodo che, in base alle bitmap in input e alle loro coordinate,crea due rettangoli invisibili intorno
    gli oggetti e determina se i due oggetti entrano in contatto
     */
    public static boolean isCollisionDetected(Bitmap bitmap1, int x1, int y1,
                                              Bitmap bitmap2, int x2, int y2) {

        Rect bounds1 = new Rect(x1, y1, x1+bitmap1.getWidth(), y1+bitmap1.getHeight());//rettangolo invisibile per la prima bitmap
        Rect bounds2 = new Rect(x2, y2, x2+bitmap2.getWidth(), y2+bitmap2.getHeight());//rettangolo invisibile per la seconda bitmap

        //se i due rettangoli si intersecano -> true
        return Rect.intersects(bounds1, bounds2);

    }

    /*
    metodo che aggiorna il pattern del blocco
    */
    public void update(int limiteBottom, int centerX, int centerY) {

        if(isTimeToPick){//se l'oggetto deve essere lasciato
            this.posizioneX = centerX;//aggiorno la posizione x
            this.posizioneY = centerY;//aggiorno la posizione y

            this.isTimeToPick = false;//l'oggetto non deve più essere riposizionato
            this.isPick = false;//non è preso dall'utente
            this.isColliding = false;//non collide con nessun oggetto

        }


            if (posizioneY <= limiteBottom && !isColliding && !isPick) { //se l'oggetto non collide,non arriva al pavimento e non è preso dall'utente
                posizioneY = posizioneY + MAX_VELOCITA;//spostati verso il basso
            } else {//altrimenti
                posizioneY = posizioneY + STOP;//fermati
            }


    }
       /*
       imposta la posizione dell'oggetto
        */
        public void setPosizione(double posizioneX, double posizioneY) {
        double x;
        double y;
        if(Game.dimension == 3){// se la dimesione è tablet
            x = 60;//valore x che serve per centrare la posizionecon il centro della bitmap
            y = 60;//valore y che serve per centrare la posizionecon il centro della bitmap

        }else//altrimenti se la dimesione è per telefono
        {
            x = 55;//valore x che serve per centrare la posizionecon il centro della bitmap
            y = 55;//valore y che serve per centrare la posizionecon il centro della bitmap
        }
        this.posizioneX = posizioneX - x;//aggiorno la posizione x
        this.posizioneY = posizioneY - y;//aggiorno la posizione y
    }

    /*
    metodo che al momento del touch determina se l'oggetto è toccato
     */
    public boolean isTouched(float x, float y) {
        double x_comp;//compensazione affinchè al momento del touch venga rilevato sull'intera bitmap

        if(Game.dimension == 3){//se la dimensione è tablet
            x_comp = 110;
        }
        else//altrimenti
        {
            x_comp = 105;
        }
        boolean xInside = x > this.posizioneX  && x< this.posizioneX + x_comp;
        boolean yInsidde = y > this.posizioneY  && y< this.posizioneY + x_comp;

        //se il tocco avviene sull'oggetto (ovvero la bitmap) -> true
        if (xInside) return yInsidde;
        //altrimenti
        return false;
    }

    //------------------------------------------------
    //metodi setter e getter
    //------------------------------------------------
    public void setTimeToPick(boolean timeToPick) {
        isTimeToPick = timeToPick;
    }

    public double getPosizioneX() {
        return posizioneX;
    }

    public double getPosizioneY() {
        return posizioneY;
    }

    public double getId() {
        return id;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }


    public void setColliding(boolean colliding) {
        isColliding = colliding;
    }

    public void setPick(boolean picked) {
        isPick = picked;
    }

    public void setActionDown(boolean actionDown) {
        this.actionDown = actionDown;
    }

    public boolean isActionDown() {
        return actionDown;
    }

    public boolean isPick() {
        return isPick;
    }

    public int getCounterPick() {
        return counterPick;
    }

    public void setCounterPick(int counterPick) {
        this.counterPick = counterPick;
    }

}
