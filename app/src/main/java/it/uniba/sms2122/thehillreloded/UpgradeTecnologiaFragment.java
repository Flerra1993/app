package it.uniba.sms2122.thehillreloded;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/*
Fragment che costituisce il menu di upgrade dell'unita di riciclo Tecnologia
 */
public class UpgradeTecnologiaFragment extends Fragment implements View.OnClickListener {

    private TextView upgradeTxt;
    private Button btnUpgradeUnit;
    private String upgrade;//stringa per settare il valore di upgradeTxt
    private TextView usuraTxt;
    private String usura;//stringa per settare il valore di usuraTxt
    private ImageButton oggetto1;
    private String numeroOggetto1;//stringa per settare il valore di numeroOggetto1Txt
    private ImageButton oggetto2;
    private String numeroOggetto2;//stringa per settare il valore di numeroOggetto2Txt
    private ImageButton oggetto3;
    private String numeroOggetto3;//stringa per settare il valore di numeroOggetto3Txt
    private TextView numeroOggetto1Txt;
    private TextView numeroOggetto2Txt;
    private TextView numeroOggetto3Txt;
    private TextView ricavoOggetto2Txt;
    private TextView ricavoOggetto3Txt;
    private TextView costoOggetto2Txt;
    private TextView costoOggetto3Txt;
    private ImageView puntiRiciclaggioOggetto2Imm;
    private ImageView puntiRiciclaggioOggetto3Imm;
    private ImageView puntiSoleOggetto2Imm;
    private ImageView puntiSoleOggetto3Imm;
    private ImageButton infOggetto2;
    private ImageButton infOggetto3;
    private TextView tempoLavorazioneTxt;

    public UpgradeTecnologiaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upgrade_tecnologia, container, false);
        TextView textViewOggettiRiciclati = view.findViewById(R.id.totRiciclatiTecnologiaNumeroTxt);
        String tot = Game.unitaRicicloArrayList.get(6).getTotOggettiRiciclati() + " ";
        textViewOggettiRiciclati.setText(tot);

        //inizializzo le stringhe con i valori presi dall'unita di riciclo Tecnologia e setto le textView associate
        upgradeTxt = view.findViewById(R.id.lvlUpgradeTecnologiaNumeroTxt);
        usuraTxt = view.findViewById(R.id.lvlUsuraTecnologiaNumeroTxt);
        usura = Game.unitaRicicloArrayList.get(6).getLvlUsura()/2 + " ";
        usuraTxt.setText(usura);
        if(Game.unitaRicicloArrayList.get(6).getLvlUpgrade() > 2){
            upgrade = "MAX";
        }else {
            upgrade = Game.unitaRicicloArrayList.get(6).getLvlUpgrade()+ " ";
        }
        upgradeTxt.setText(upgrade);
        btnUpgradeUnit = view.findViewById(R.id.aumentaLvlUnitaTecnologiaBtn);
        if(Game.unitaRicicloArrayList.get(6).getLvlUpgrade()> 2 || Game.unitaRicicloArrayList.get(6).getPuntiRiciclaggio() < 4){
            btnUpgradeUnit.setClickable(false);
            btnUpgradeUnit.setAlpha((float)0.20);
        }


        oggetto1 = view.findViewById(R.id.ricicloTecnologia1Btn);
        oggetto2 = view.findViewById(R.id.ricicloTecnologia2Btn);
        oggetto3 = view.findViewById(R.id.ricicloTecnologia3Btn);
        numeroOggetto1Txt = view.findViewById(R.id.numeroOggettiCreatiTecnologia1Txt);
        numeroOggetto2Txt = view.findViewById(R.id.numeroOggettiCreatiTecnologia2Txt);
        numeroOggetto3Txt = view.findViewById(R.id.numeroOggettiCreatiTecnologia3Txt);
        ricavoOggetto2Txt = view.findViewById(R.id.ricavoRicicloOggettoTecnologia2Txt);
        ricavoOggetto3Txt = view.findViewById(R.id.ricavoRicicloOggettoTecnologia3Txt);
        costoOggetto2Txt = view.findViewById(R.id.costoOggettoTecnologia2Txt);
        costoOggetto3Txt = view.findViewById(R.id.costoOggettoTecnologia3Txt);
        puntiRiciclaggioOggetto2Imm = view.findViewById(R.id.puntiRiciclaggiTecnologiaImm2);
        puntiRiciclaggioOggetto3Imm = view.findViewById(R.id.puntiRiciclaggiTecnologiaImm4);
        puntiSoleOggetto2Imm = view.findViewById(R.id.puntiSoleRicavoTecnologia2Imm);
        puntiSoleOggetto3Imm = view.findViewById(R.id.puntiSoleRicavoTecnologia3Imm);
        infOggetto2 = view.findViewById(R.id.informazioniCreazioneOggettoTecnologia2Btn);
        infOggetto3 = view.findViewById(R.id.informazioniCreazioneOggettoTecnologia3Btn);
        tempoLavorazioneTxt = view.findViewById(R.id.tempoRiciclaggioTecnologiaTxt);

        if(Game.dimension == 1){//se la dimensione è 5" cambio le dimensioni del testo
            TextView tipoUnitatxt = view.findViewById(R.id.tipoUnitaRicicloTecnologiaTxt);
            TextView lvlUpgradeTxt = view.findViewById(R.id.lvlUpgradeTecnologiaTxt);
            TextView lvlUsuraTxt = view.findViewById(R.id.lvlUsuraTecnologiaTxt);
            TextView lvlTotOggetti = view.findViewById(R.id.totRiciclatiTecnologiaTxt);
            TextView lvlMaxUsura = view.findViewById(R.id.lvlUsuraMaxTecnologiaTxt);
            tipoUnitatxt.setTextSize(10);
            lvlTotOggetti.setTextSize(10);
            lvlUpgradeTxt.setTextSize(10);
            lvlUsuraTxt.setTextSize(10);
            lvlMaxUsura.setTextSize(10);
            usuraTxt.setTextSize(10);
            upgradeTxt.setTextSize(10);
            tempoLavorazioneTxt.setTextSize(10);
            textViewOggettiRiciclati.setTextSize(10);

        }

        if(Game.dimension == 3){//se la dimensione è 7+" cambio le dimensioni del testo e bottoni
            TextView tipoUnitatxt = view.findViewById(R.id.tipoUnitaRicicloTecnologiaTxt);
            TextView lvlUpgradeTxt = view.findViewById(R.id.lvlUpgradeTecnologiaTxt);
            TextView lvlUsuraTxt = view.findViewById(R.id.lvlUsuraTecnologiaTxt);
            TextView lvlTotOggetti = view.findViewById(R.id.totRiciclatiTecnologiaTxt);
            TextView lvlMaxUsura = view.findViewById(R.id.lvlUsuraMaxTecnologiaTxt);
            tipoUnitatxt.setTextSize(20);
            lvlTotOggetti.setTextSize(20);
            lvlUpgradeTxt.setTextSize(20);
            lvlUsuraTxt.setTextSize(20);
            lvlMaxUsura.setTextSize(20);
            usuraTxt.setTextSize(20);
            upgradeTxt.setTextSize(20);
            tempoLavorazioneTxt.setTextSize(20);
            textViewOggettiRiciclati.setTextSize(20);
            ViewGroup.LayoutParams ricicloBtn1Params = oggetto1.getLayoutParams();
            ViewGroup.LayoutParams ricicloBtn2Params = oggetto2.getLayoutParams();
            ViewGroup.LayoutParams ricicloBtn3Params = oggetto3.getLayoutParams();
            ricicloBtn1Params.width = 160;
            ricicloBtn1Params.height = 180;
            ricicloBtn2Params.width = 160;
            ricicloBtn2Params.height = 180;
            ricicloBtn3Params.width = 160;
            ricicloBtn3Params.height = 180;
            oggetto1.setLayoutParams(ricicloBtn1Params);
            oggetto2.setLayoutParams(ricicloBtn2Params);
            oggetto3.setLayoutParams(ricicloBtn3Params);
            ViewGroup.LayoutParams paramsBtnLevel = btnUpgradeUnit.getLayoutParams();
            paramsBtnLevel.width = 330;
            paramsBtnLevel.height = 120;
            btnUpgradeUnit.setLayoutParams(paramsBtnLevel);

        }
        checkUnitUpgrade();

        //inizializza il nummero di oggetti prodotti di lvl 1 e li passa alla textView associata
        numeroOggetto1 = "n: " + Game.unitaRicicloArrayList.get(6).getOggettoRiciclo1();
        numeroOggetto1Txt.setText(numeroOggetto1);

        //rende trasparente e non cliccabile il bottone di produzione nuovo oggetto di lvl 1
        // se non si hanno i punti necessari
        if(Game.unitaRicicloArrayList.get(6).getPuntiRiciclaggio() < 2){
            oggetto1.setClickable(false);
            oggetto1.setAlpha((float)0.20);
        }

        //inizializza il nummero di oggetti prodotti di lvl 2 e li passa alla textView associata
        numeroOggetto2 = "n: " + Game.unitaRicicloArrayList.get(6).getOggettoRiciclo2();
        numeroOggetto2Txt.setText(numeroOggetto2);

        //rende trasparente e non cliccabile il bottone di produzione nuovo oggetto di lvl 2
        // se non si hanno i punti necessari
        if(Game.unitaRicicloArrayList.get(6).getPuntiRiciclaggio() < 4){
            oggetto2.setClickable(false);
            oggetto2.setAlpha((float)0.20);
        }

        //inizializza il nummero di oggetti prodotti di lvl 3 e li passa alla textView associata
        numeroOggetto3 = "n: " + Game.unitaRicicloArrayList.get(6).getOggettoRiciclo3();
        numeroOggetto3Txt.setText(numeroOggetto3);

        //rende trasparente e non cliccabile il bottone di produzione nuovo oggetto di lvl 3
        // se non si hanno i punti necessari
        if(Game.unitaRicicloArrayList.get(6).getPuntiRiciclaggio() < 7){
            oggetto3.setClickable(false);
            oggetto3.setAlpha((float)0.20);
        }

        setTempoLavorazioneTxt();

        //imposto i listner per il click dei bottoni
        btnUpgradeUnit.setOnClickListener(this);
        oggetto1.setOnClickListener(this);
        oggetto2.setOnClickListener(this);
        oggetto3.setOnClickListener(this);

        return view;
    }

    /*
   metodo che formatta il tempo di lavorazione in 00m 00s e lo
   passa alla textView associata
    */
    private void setTempoLavorazioneTxt(){
        String tempoLavorazione;
        if(Game.unitaRicicloArrayList.get(6).getLvlUpgrade() == 1){
            tempoLavorazione = "Tempo lavorazione:5s";
        }else if(Game.unitaRicicloArrayList.get(6).getLvlUpgrade() == 2){
            tempoLavorazione = "Tempo lavorazione:3s";
        }else
        {
            tempoLavorazione = "Tempo lavorazione:2s";
        }
        tempoLavorazioneTxt.setText(tempoLavorazione);
    }

    private void checkButtonNewOggetto(){

        //se non si possiedono i punti necessari per la produzione di un oggetto di lvl 1
        if(Game.unitaRicicloArrayList.get(6).getPuntiRiciclaggio() < 2){
            //il bottone per la produzione dell'oggetto di lvl 1 diventa non cliccabile e trasparente
            oggetto1.setClickable(false);
            oggetto1.setAlpha((float)0.20);
        }
        //se non si possiedono i punti necessari per la produzione di un oggetto di lvl 2
        if(Game.unitaRicicloArrayList.get(6).getPuntiRiciclaggio() < 4){
            //il bottone per la produzione dell'oggetto di lvl 1 diventa non cliccabile e trasparente
            oggetto2.setClickable(false);
            oggetto2.setAlpha((float)0.20);
        }
        //se non si possiedono i punti necessari per la produzione di un oggetto di lvl 3
        if(Game.unitaRicicloArrayList.get(6).getPuntiRiciclaggio() < 7){
            //il bottone per la produzione dell'oggetto di lvl 1 diventa non cliccabile e trasparente
            oggetto3.setClickable(false);
            oggetto3.setAlpha((float)0.20);
        }
    }

    private void checkUnitUpgrade(){

        //se l'unita è di livello maggiore di 2
        if(Game.unitaRicicloArrayList.get(6).getLvlUpgrade() >= 2){
            //rende visibile il bottone di produzione oggetto di lvl 2
            oggetto2.setVisibility(View.VISIBLE);
            numeroOggetto2Txt.setVisibility(View.VISIBLE);
            costoOggetto2Txt.setVisibility(View.VISIBLE);
            ricavoOggetto2Txt.setVisibility(View.VISIBLE);
            puntiSoleOggetto2Imm.setVisibility(View.VISIBLE);
            puntiRiciclaggioOggetto2Imm.setVisibility(View.VISIBLE);
            infOggetto2.setVisibility(View.VISIBLE);
        }else//altrimenti
        {
            //rende gone il bottone di produzione oggetto di lvl 2
            oggetto2.setVisibility(View.GONE);
            numeroOggetto2Txt.setVisibility(View.GONE);
            costoOggetto2Txt.setVisibility(View.GONE);
            ricavoOggetto2Txt.setVisibility(View.GONE);
            puntiSoleOggetto2Imm.setVisibility(View.GONE);
            puntiRiciclaggioOggetto2Imm.setVisibility(View.GONE);
            infOggetto2.setVisibility(View.GONE);
        }
        //se l'unita è di livello maggiore di 3 (MAX)
        if(Game.unitaRicicloArrayList.get(6).getLvlUpgrade() >= 3){
            //rende visibile il bottone di produzione oggetto di lvl 3
            oggetto3.setVisibility(View.VISIBLE);
            numeroOggetto3Txt.setVisibility(View.VISIBLE);
            costoOggetto3Txt.setVisibility(View.VISIBLE);
            ricavoOggetto3Txt.setVisibility(View.VISIBLE);
            puntiSoleOggetto3Imm.setVisibility(View.VISIBLE);
            puntiRiciclaggioOggetto3Imm.setVisibility(View.VISIBLE);
            infOggetto3.setVisibility(View.VISIBLE);
        }else//altrimenti
        {
            //rende gone il bottone di produzione oggetto di lvl 3
            oggetto3.setVisibility(View.GONE);
            numeroOggetto3Txt.setVisibility(View.GONE);
            costoOggetto3Txt.setVisibility(View.GONE);
            ricavoOggetto3Txt.setVisibility(View.GONE);
            puntiSoleOggetto3Imm.setVisibility(View.GONE);
            puntiRiciclaggioOggetto3Imm.setVisibility(View.GONE);
            infOggetto3.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.aumentaLvlUnitaTecnologiaBtn:
                if(Game.unitaRicicloArrayList.get(6).getPuntiRiciclaggio() >= 4) {// se si hanno i punti necessari
                    Game.unitaRicicloArrayList.get(6).setPuntiRiciclaggio(Game.unitaRicicloArrayList.get(6).getPuntiRiciclaggio() - 4);//sottrai prezzo pagato
                    Game.unitaRicicloArrayList.get(6).setLvlUpgrade(Game.unitaRicicloArrayList.get(6).getLvlUpgrade() + 1);//aumenta livello unita
                    //aggiorna informazioni unita
                    upgrade = Game.unitaRicicloArrayList.get(6).getLvlUpgrade() + " ";
                    Game.unitaRicicloArrayList.get(6).setLvlUsura(0);
                    usura = Game.unitaRicicloArrayList.get(6).getLvlUsura() + " ";
                    usuraTxt.setText(usura);
                    //se l'unita è al livello massimo
                    if (Game.unitaRicicloArrayList.get(6).getLvlUpgrade() > 2) {
                        //rende il bottone non cliccabile e trasparente
                        btnUpgradeUnit.setClickable(false);
                        btnUpgradeUnit.setAlpha((float) 0.20);
                        upgrade = "MAX";
                    }
                    //altrimenti se i punti non sono sufficienti
                    else if (Game.unitaRicicloArrayList.get(6).getPuntiRiciclaggio() < 4) {
                        //rende il bottone non cliccabile e trasparente
                        btnUpgradeUnit.setClickable(false);
                        btnUpgradeUnit.setAlpha((float) 0.20);
                    }

                    //aggiorna la textView
                    upgradeTxt.setText(upgrade);
                    setTempoLavorazioneTxt();
                }

                checkUnitUpgrade();

              checkButtonNewOggetto();
                break;
            case R.id.ricicloTecnologia1Btn:
                //se si hanno i punti necessari alla produzione di un oggetto di lvl 1
                if(Game.unitaRicicloArrayList.get(6).getPuntiRiciclaggio() >= 2){
                    Game.puntiSole = Game.puntiSole + 2;
                    //paga 2 punti riciclaggio
                    Game.unitaRicicloArrayList.get(6).setPuntiRiciclaggio(Game.unitaRicicloArrayList.get(6).getPuntiRiciclaggio() - 2);
                    //aggiorna conteggio oggetti di lvl1 prodotti
                    Game.unitaRicicloArrayList.get(6).setOggettoRiciclo1(Game.unitaRicicloArrayList.get(6).getOggettoRiciclo1() + 1);
                    numeroOggetto1 = "n: " + Game.unitaRicicloArrayList.get(6).getOggettoRiciclo1();
                    numeroOggetto1Txt.setText(numeroOggetto1);

                }
              checkButtonNewOggetto();
                break;

            case R.id.ricicloTecnologia2Btn:
                //se si hanno i punti necessari alla produzione di un oggetto di lvl 2
                if(Game.unitaRicicloArrayList.get(6).getPuntiRiciclaggio() >= 4){
                    //guadagna 4 punti sole
                    Game.puntiSole = Game.puntiSole + 4;
                    //paga 4 punti riciclaggio
                    Game.unitaRicicloArrayList.get(6).setPuntiRiciclaggio(Game.unitaRicicloArrayList.get(6).getPuntiRiciclaggio() - 4);
                    //aggiorna conteggio oggetti di lvl2 prodotti
                    Game.unitaRicicloArrayList.get(6).setOggettoRiciclo2(Game.unitaRicicloArrayList.get(6).getOggettoRiciclo2() + 1);
                    numeroOggetto2 = "n: " + Game.unitaRicicloArrayList.get(6).getOggettoRiciclo2();
                    numeroOggetto2Txt.setText(numeroOggetto2);

                }
              checkButtonNewOggetto();
                break;

            case R.id.ricicloTecnologia3Btn:
                //se si hanno i punti necessari alla produzione di un oggetto di lvl 3
                if(Game.unitaRicicloArrayList.get(6).getPuntiRiciclaggio() >= 7){
                    //guadagna 7 punti sole
                    Game.puntiSole = Game.puntiSole + 7;
                    //paga 7 punti riciclaggio
                    Game.unitaRicicloArrayList.get(6).setPuntiRiciclaggio(Game.unitaRicicloArrayList.get(6).getPuntiRiciclaggio() - 7);
                    //aggiorna conteggio oggetti di lvl3 prodotti
                    Game.unitaRicicloArrayList.get(6).setOggettoRiciclo3(Game.unitaRicicloArrayList.get(6).getOggettoRiciclo3() + 1);
                    numeroOggetto3 = "n: " + Game.unitaRicicloArrayList.get(6).getOggettoRiciclo3();
                    numeroOggetto3Txt.setText(numeroOggetto3);
                }

              checkButtonNewOggetto();
                break;

        }

    }


}