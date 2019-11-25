package com.example.myfirstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import java.security.Key;

public class Resultado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        TextView puntosLabel = (TextView) findViewById(R.id.puntosLabel);
        TextView maximoPuntajeLabel = (TextView) findViewById(R.id.maximoPuntajeLabel);
        int puntaje = getIntent().getIntExtra("Puntaje", 0);
        puntosLabel.setText(puntaje + "");

        SharedPreferences configuracion = getSharedPreferences("GAME DATA", Context.MODE_PRIVATE);
        int maximoPuntaje = configuracion.getInt("MAXIMO PUNTAJE", 0);

        if (puntaje > maximoPuntaje) {
            maximoPuntajeLabel.setText("Maximo Puntaje : " + puntaje);

            //Guardar

            SharedPreferences.Editor editor = configuracion.edit();
            editor.putInt("Hight Score", puntaje);
            editor.commit();
        } else {
            maximoPuntajeLabel.setText("Maximo Puntaje : " + maximoPuntaje);
        }
    }

    public void reanudar(View view) {
        startActivity(new Intent(getApplicationContext(), Inicio.class));
    }

    //desactivar boton de retorno movil

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

}
