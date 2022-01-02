package it.uniba.sms2122.thehillreloded;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Objects;

/*
Fragment che costituisce il menu di upgrade dell'unita di riciclo Inceneritore
 */
public class UpgradeInceneritoreFragment extends Fragment implements View.OnClickListener{

    private Button powerUp;
    public UpgradeInceneritoreFragment() {
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
        View view = inflater.inflate(R.layout.fragment_upgrade_inceneritore, container, false);
        TextView usatoTxt = view.findViewById(R.id.usatoNumeroTxt);
        String usato = " " + Game.unitaRicicloArrayList.get(0).getTotOggettiRiciclati();
        powerUp = view.findViewById(R.id.buttonPowerUpInceneritore);
        if(Game.puntiSole < 5 || Game.bloccoArrayList.size() < 1){//se non si posseggono almeno 5 soli o non sono presenti almeno 2 blocchi
           //bottone non utilizzabile
            powerUp.setClickable(false);
            powerUp.setAlpha((float) 0.20);
        }

        usatoTxt.setText(usato);
        powerUp.setOnClickListener(this);//imposta il listner per il click del bottone
        return view;
    }

    @Override
    public void onClick(View view) {
        //se si hanno i punti sole necessari e almeno un blocco è presente
        if(Game.puntiSole >= 5 && Game.bloccoArrayList.size() >= 1) {
            Game.puntiSole = Game.puntiSole -5;//paga
            if (Game.bloccoArrayList.size() <= 5) {//se gli oggetti sono al più 5
                Game.bloccoArrayList.removeIf(Objects::nonNull);//elimina tutti gli oggetti
            } else {//altrimenti
                int counter = 0;
                Iterator<Blocco> i = Game.bloccoArrayList.iterator();//iteratore
                while (i.hasNext()) {//finché esistono blocchi
                    i.next();//prossimo blocco
                    if (counter < 5) {//se non sono ancora 5 oggetti eliminati
                        i.remove();//rimuovi il blocco
                    }
                    counter++;//aumenta il contatore dei blocchi eliminati
                }
            }
        }
        if(Game.puntiSole < 5 || Game.bloccoArrayList.size() < 1){//se non si può usare il powerUp
            //trasparente e non cliccabile
            powerUp.setClickable(false);
            powerUp.setAlpha((float) 0.20);
        }
    }
}