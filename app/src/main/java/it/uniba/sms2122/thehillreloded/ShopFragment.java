package it.uniba.sms2122.thehillreloded;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
Fragment che costituisce il menu di acquisto unita di riciclo
 */
public class ShopFragment extends Fragment {


    public ShopFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        if(Game.unitaRicicloArrayList.get(2).isPresent() || (Game.puntiSole < 12 && !Game.unitaRicicloArrayList.get(2).isPresent())) {
            view.findViewById(R.id.buyCartaBtn).setClickable(false);
            view.findViewById(R.id.buyCartaBtn).setAlpha((float) 0.20);
        }

        if(Game.unitaRicicloArrayList.get(5).isPresent() || (Game.puntiSole < 35 && !Game.unitaRicicloArrayList.get(5).isPresent())) {
            view.findViewById(R.id.buyPlasticaBtn).setClickable(false);
            view.findViewById(R.id.buyPlasticaBtn).setAlpha((float) 0.20);
        }

        if(Game.unitaRicicloArrayList.get(4).isPresent() || (Game.puntiSole < 25 && !Game.unitaRicicloArrayList.get(4).isPresent())) {
            view.findViewById(R.id.buyAlluminioBtn).setClickable(false);
            view.findViewById(R.id.buyAlluminioBtn).setAlpha((float) 0.20);
        }

        if(Game.unitaRicicloArrayList.get(3).isPresent() || (Game.puntiSole < 30 && !Game.unitaRicicloArrayList.get(3).isPresent())) {
            view.findViewById(R.id.buyAcciaioBtn).setClickable(false);
            view.findViewById(R.id.buyAcciaioBtn).setAlpha((float) 0.20);
        }

        if(Game.unitaRicicloArrayList.get(6).isPresent() || (Game.puntiSole < 40 && !Game.unitaRicicloArrayList.get(6).isPresent())) {
            view.findViewById(R.id.buyTecnologiaBtm).setClickable(false);
            view.findViewById(R.id.buyTecnologiaBtm).setAlpha((float) 0.20);
        }

        // Inflate the layout for this fragment
        return view;
    }

}