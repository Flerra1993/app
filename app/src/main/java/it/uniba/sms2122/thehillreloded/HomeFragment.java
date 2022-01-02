package it.uniba.sms2122.thehillreloded;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class HomeFragment extends Fragment {

    //CLASSE DEL TASTO HOME DELLA NAV BAR
    private Button bottone;


    public HomeFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //CHIEDO DI FAR VISUALIZZARE IL LAYOUT DI FRAGMENT HOME
        View view= inflater.inflate(R.layout.fragment_home,container,false);

        bottone =view.findViewById(R.id.button);
        bottone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container,new Fragment2());
                fr.commit();

                //AL CLICK DEL BUTTON CONTENUTO NEL "fragment_home"
                //PASSO IL "fragment_container" PRESENTE IN FRAGMENT2

            }
        });


        return view;
    }
}