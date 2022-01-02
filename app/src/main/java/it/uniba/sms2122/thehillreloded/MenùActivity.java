package it.uniba.sms2122.thehillreloded;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MenùActivity extends AppCompatActivity {






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        BottomNavigationView bottomnav= findViewById(R.id.bottom_navigation);//Definisco navbar e la collego alla activty xml
        bottomnav.setOnNavigationItemSelectedListener(navListner);//è collegata alla classe BottomNavigationview




        //Faccio partire la schermata iniziale dal fragment Home
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();






















    }

    //classe per creare la nav bar
    private BottomNavigationView.OnNavigationItemSelectedListener navListner = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment seleziona= null;
            switch (item.getItemId()){
                //BOTTONE GIOCA
                case R.id.Gioca:
                    seleziona = new HomeFragment();
                    break;
                //BOTTONE IMPOSTAZIONI
                case R.id.Impostazioni:
                    seleziona = new SettingFragment();
                    break;
                //BOTTONE PROFILO
                case R.id.Profilo:
                    seleziona = new ProfiloFragment();
                    break;
            }
            //ATTENZIONE IN ".setCustomAnimations" faccio partire l'animazion che è nella cartella "anim" presente in "res"
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.e_destra_sx,R.anim.q_destra_sx,R.anim.e_sinistra_dx,R.anim.q_sinistra_dx).replace(R.id.fragment_container, seleziona).commit();
            return true;
        }
    };






}