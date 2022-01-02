package it.uniba.sms2122.thehillreloded;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

/*
Classe che rappresenta la barra di tempo associata ad ogni unita di riciclo.
La timebar evidenzia il tempo di lavorazione di un blocco.
 */
public class TimeBar {
    private final UnitaRiciclo unitaRiciclo;//unita di riciclo associata
    private final Paint borderPaint;// il canvas su cui è disegnata la grafica del bordo della barra
    private final Paint timePaint;// il canvas su cui è disegnata la grafica del contenuto della barra
    private final static int CONSTANT_TIME_UNIT = 180;// costante per adattare il contenuto della barra al bordo (se il livello dell'unita è 1)
    private final static int CONSTANT_TIME_UNIT_LVL2 = 108;// costante per adattare il contenuto della barra al bordo (se il livello dell'unita è 2)
    private final static int CONSTANT_TIME_UNIT_LVL3 = 72;// costante per adattare il contenuto della barra al bordo (se il livello dell'unita è 3)
    private final static int CONSTANT_TIME_INCENERITORE = 360;// costante per adattare il contenuto della barra al bordo in caso di inceneritore
    private final int width;//larghezza
    private final int height;//profondita
    private final int margin; // pixel value
    private final int distanceToRight;//distanza da destra
    private final int distanceToUnit;//distanza dall'unita associata
    private final int distanceToLeft;//distanza da sinistra
    private final boolean inceneritore;//indica se l'unita è un inceneritore o meno
    private boolean isPresent = false;//indica se è presente, ovvero se l'unita a cui è associata è presente
    private int posizioneSchermo = 0;//posizione (1,2,3,4,5) in base al bottone di aggiunta (add1,...,add5) con cui si è aperto lo shop
    private final int timeColorUp1;//colore della barra per il lvl 1
    private final int timeColorUp2;//colore della barra per il lvl 2
    private final int timeColorUp3;//colore della barra per il lvl 3

    public TimeBar(Context context, UnitaRiciclo unitaRiciclo,int distanceToRight, int distanceToUnit, int distanceToLeft, boolean inceneritore) {
        this.unitaRiciclo = unitaRiciclo;//unita associata
        this.width = 150;
        this.height = 20;
        this.margin = 2;
        this.distanceToRight = distanceToRight;
        this.distanceToUnit = distanceToUnit;
        this.distanceToLeft = distanceToLeft;
        this.borderPaint = new Paint();
        int borderColor = ContextCompat.getColor(context, R.color.borderTimeBar);
        borderPaint.setColor(borderColor);
        this.inceneritore = inceneritore;
        this.timePaint = new Paint();

        timeColorUp1 = ContextCompat.getColor(context,R.color.timeTimeBaar);
        timeColorUp2 = ContextCompat.getColor(context,R.color.timeTimeBaarUp2);
        timeColorUp3 = ContextCompat.getColor(context,R.color.timeTimeBaarUp3);
        timePaint.setColor(timeColorUp1);

    }

    public void draw(Canvas canvas) {
        float x = (float) unitaRiciclo.getPosizioneX();//posizione x unita di riciclo
        float y = (float) unitaRiciclo.getPosizioneY();//posizione y unita di riciclo
        float timePercentage = 0;//percentuale completamento barra
        if(inceneritore){//se inceneritore
            timePercentage = (float) unitaRiciclo.getCounterOccupated()/CONSTANT_TIME_INCENERITORE;//dividi il contatore per la costante dell'inceneritore
        }else if(unitaRiciclo.getLvlUpgrade() == 1) {//altrimenti se l'unita di riciclo è di lvl 1
            timePaint.setColor(timeColorUp1);//imposta il colore adatto
                timePercentage = (float) unitaRiciclo.getCounterOccupated() / CONSTANT_TIME_UNIT;
            }
        else if(unitaRiciclo.getLvlUpgrade() == 2){//altrimenti se l'unita di riciclo è di lvl 2
            timePaint.setColor(timeColorUp2);
                timePercentage = (float) unitaRiciclo.getCounterOccupated() / CONSTANT_TIME_UNIT_LVL2;
            }
        else if(unitaRiciclo.getLvlUpgrade() == 3) {//altrimenti se l'unita di riciclo è di lvl 3
            timePaint.setColor(timeColorUp3);
                timePercentage = (float) unitaRiciclo.getCounterOccupated() / CONSTANT_TIME_UNIT_LVL3;
            }

        // Disegna il bordo della barra
        float borderLeft, borderTop, borderRight, borderBottom;
        borderLeft = x + width - (float) distanceToRight/2 + distanceToLeft;
        borderRight = x + width + distanceToRight;
        borderBottom = y + distanceToUnit;
        borderTop = borderBottom - height;
        canvas.drawRect(borderLeft, borderTop, borderRight, borderBottom, borderPaint);

        // Disegna il tempo corrente sotto forma di barra
        float timeLeft, timeTop, timeRight, timeBottom, timeWidth, timeHeight;
        timeWidth = width - 2*margin;
        timeHeight = height - 2*margin;
        timeLeft = borderLeft + margin;
        timeRight = timeLeft + timeWidth*timePercentage;
        timeBottom = borderBottom - margin;
        timeTop = timeBottom - timeHeight;
        canvas.drawRect(timeLeft, timeTop, timeRight, timeBottom, timePaint);
    }

    //----------------------------------------
    //metodi getter e setter
    //----------------------------------------
    public UnitaRiciclo getUnitaRiciclo() {
        return unitaRiciclo;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public int getPosizioneSchermo() {
        return posizioneSchermo;
    }

    public void setPosizioneSchermo(int posizioneSchermo) {
        this.posizioneSchermo = posizioneSchermo;
    }
}
