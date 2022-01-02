package it.uniba.sms2122.thehillreloded;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
Fragment che costituisce il menu di pausa avente le impostazioni,il salvataggio e il
ritorno al menu
 */
public class MenuPausaFragment extends Fragment {

    public MenuPausaFragment() {
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
        return inflater.inflate(R.layout.fragment_menu_pausa, container, false);
    }
}