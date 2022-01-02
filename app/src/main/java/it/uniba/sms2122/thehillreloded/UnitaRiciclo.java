package it.uniba.sms2122.thehillreloded;

/*
Classe che rappresenta un'unita di riciclo generica
 */
public class UnitaRiciclo {

    private double posizioneX;//posizione x dell'unita di riciclo
    private double posizioneY;//posizione y dell'unita di riciclo
    private boolean isOccupated;//indica se l'unita è occupata o è libera di riciclare
    private int counterOccupated = 0;//indica, nel momento in cui l'unita è occupata, il tempo trascorso dall'inizio della lavorazione
    private int lvlUpgrade = 1;//lvl unita
    private boolean isPresent;//indica se l'unita è presente, ovvero è stata acquistata
    private int totOggettiRiciclati = 0;//numero complessivo di oggetti riciclati
    private int lvlUsura = 0;//indica il livello usura dell'unita (solo in caso di lvl > 1)
    private int puntiRiciclaggio = 30;//punti riciclaggio dell'unita ottenuti con il riciclaggio dei blocchi
    private int oggettoRiciclo1 = 0;//numero oggetti prodotti di lvl 1
    private int oggettoRiciclo2 = 0;//numero oggetti prodotti di lvl 2
    private int oggettoRiciclo3 = 0;//numero oggetti prodotti di lvl 3
    private int posizioneSchermo = 0;//posizione sullo schermo (in base ai bottoni add1,...,add5) 1,2,3,4,5


    public UnitaRiciclo(double posizioneX, double posizioneY) {
        this.posizioneX = posizioneX;
        this.posizioneY = posizioneY;
    }


    //---------------------------------------
    //metodi getter e setter
    //---------------------------------------
    public double getPosizioneX() {
        return posizioneX;
    }

    public double getPosizioneY() {
        return posizioneY;
    }

    public boolean isOccupated() {
        return isOccupated;
    }

    public void setOccupated(boolean occupated) {
        isOccupated = occupated;
    }

    public int getCounterOccupated() {
        return counterOccupated;
    }

    public void setCounterOccupated(int counterOccupated) {
        this.counterOccupated = counterOccupated;
    }

    public int getLvlUpgrade() {
        return lvlUpgrade;
    }

    public void setLvlUpgrade(int lvlUpgrade) {
        this.lvlUpgrade = lvlUpgrade;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public int getTotOggettiRiciclati() {
        return totOggettiRiciclati;
    }

    public void setTotOggettiRiciclati(int totOggettiRiciclati) {
        this.totOggettiRiciclati = totOggettiRiciclati;
    }

    public int getLvlUsura() {
        return lvlUsura;
    }

    public void setLvlUsura(int lvlUsura) {
        this.lvlUsura = lvlUsura;
    }

    public int getPuntiRiciclaggio() {
        return puntiRiciclaggio;
    }

    public void setPuntiRiciclaggio(int puntiRiciclaggio) {
        this.puntiRiciclaggio = puntiRiciclaggio;
    }

    public int getOggettoRiciclo1() {
        return oggettoRiciclo1;
    }

    public void setOggettoRiciclo1(int oggettoRiciclo1) {
        this.oggettoRiciclo1 = oggettoRiciclo1;
    }

    public int getOggettoRiciclo2() {
        return oggettoRiciclo2;
    }

    public void setOggettoRiciclo2(int oggettoRiciclo2) {
        this.oggettoRiciclo2 = oggettoRiciclo2;
    }

    public int getOggettoRiciclo3() {
        return oggettoRiciclo3;
    }

    public void setOggettoRiciclo3(int oggettoRiciclo3) {
        this.oggettoRiciclo3 = oggettoRiciclo3;
    }

    public void setPosizioneX(double posizioneX) {
        this.posizioneX = posizioneX;
    }

    public void setPosizioneY(double posizioneY) {
        this.posizioneY = posizioneY;
    }

    public int getPosizioneSchermo() {
        return posizioneSchermo;
    }

    public void setPosizioneSchermo(int posizioneSchermo) {
        this.posizioneSchermo = posizioneSchermo;
    }
}
