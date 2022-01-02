package it.uniba.sms2122.thehillreloded;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class Fragment2 extends Fragment {
    //fragment di prova per aprire altre activity
    //Questo fragment è quello che dovrà aprire il gioco in base alla difficoltà scelta

    private Button bFacile,bMedio,bDifficile;

    //bottone2=view.findViewById(R.id.button3);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.customlayout, container, false);//"customlayout" contiente i bottoni delle difficoltà
        bFacile=view.findViewById(R.id.button3);//FACILE
        bMedio=view.findViewById(R.id.button4);//MEDIO
        bDifficile=view.findViewById(R.id.button5);//DIFFICILE


        bFacile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getActivity(),Animazione.class);//SERVE PER APRIRE UNA QUALSIASI ACTIVITY
                startActivity(in);
            }
        });

        bMedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getActivity(),MainActivity2.class);//SERVE PER APRIRE UNA QUALSIASI ACTIVITY
                startActivity(in);
            }
        });


        bDifficile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getActivity(),Animazione.class);//SERVE PER APRIRE UNA QUALSIASI ACTIVITY
                startActivity(in);
            }
        });


        return view;
    }



}