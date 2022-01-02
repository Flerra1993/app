package it.uniba.sms2122.thehillreloded;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }


    public void send(View view){

        if(Game.cleanScreen || Game.pauseMission) {
            Game.cleanScreen = false;
            Game.resize = false;

            for (int i = 0; i < 7; i++) {
                Game.unitaRicicloArrayList.get(i).setPresent(false);
                Game.timeBarArrayList.get(i).setPresent(false);
                Game.unitaRicicloArrayList.get(i).setPuntiRiciclaggio(0);
            }

            Game.bloccoArrayList.removeIf(Objects::nonNull);
            Game.dimension = (int) getIntent().getExtras().get("dimension");
            Game.puntiSole = 0;
            Game.minute = 0;
            Game.second = 0;
            Game.gameover = false;
            Game.pauseShop = false;
            Game.pauseUpgrade = false;
            Game.pauseMission = false;
            Game.isPositionated = false;

        }
        Intent intent = new Intent(MainActivity2.this,MainActivity.class);
        startActivity(intent);

    }
}