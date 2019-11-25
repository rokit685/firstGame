package com.example.myfirstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {



    private TextView puntajeLabel;
    private TextView inicioLabel;
    private ImageView carrito;
    private ImageView adulto;
    private ImageView nino;
    private ImageView perro;


    //Tamaño
    private int altopantalla;
    private int tamañocarro;
    private int anchopantalla;



    //Posicion

    private int carritoY;
    private int adultoX;
    private int adultoY;
    private int ninoX;
    private int ninoY;
    private int perroX;
    private int perroY;

    private int puntaje = 0;

    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private Sounds sound;

    //Validar estado
    private boolean action_flg = false;
    private boolean inicio_flg = false;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sound = new Sounds(this);


        puntajeLabel = (TextView) findViewById(R.id.puntajeLabel);
        inicioLabel = (TextView) findViewById(R.id.inicioLabel);
        carrito = (ImageView) findViewById(R.id.carrito);
        adulto = (ImageView) findViewById(R.id.adulto);
        nino = (ImageView) findViewById(R.id.nino);
        perro = (ImageView) findViewById(R.id.perro);

        //Obtener tamaño pantalla
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point tamaño = new Point();
        disp.getSize(tamaño);

        anchopantalla = tamaño.x;
        altopantalla = tamaño.y;


        // Mover fuera de la pantalla
        adulto.setX(-80);
        adulto.setY(-80);
        nino.setX(-80);
        nino.setY(-80);
        perro.setX(-80);
        perro.setY(-80);

        puntajeLabel.setText("Puntaje : 0");

    }

    public void cambioPos(){

        validarPunto();
        //adulto
        adultoX -= 12;
        if(adultoX<0){
            adultoX = anchopantalla + 20;
            adultoY = (int) Math.floor(Math.random()*(altopantalla - adulto.getHeight()));
        }
        adulto.setX(adultoX);
        adulto.setY(adultoY);

        //niño
        ninoX -= 16;
        if(ninoX<0){
            ninoX = anchopantalla + 10;
            ninoY = (int) Math.floor(Math.random()*(altopantalla - nino.getHeight()));
        }
        nino.setX(ninoX);
        nino.setY(ninoY);

        //Perro
        perroX -= 20;
        if(perroX<0){
            perroX = anchopantalla + 5000;
            perroY = (int) Math.floor(Math.random()*(altopantalla - perro.getHeight()));
        }
        perro.setX(perroX);
        perro.setY(perroY);



        //Mover carrito
        if(action_flg == true){
            //tocando
            carritoY -=20;
        } else {
            //Actualizar
            carritoY +=20;

        }
        // validar posicion del carro
        if(carritoY < 0 ) carritoY = 0;

        if(carritoY > altopantalla - tamañocarro) carritoY = altopantalla - tamañocarro;

        carrito.setY(carritoY);

        puntajeLabel.setText("Puntaje : " + puntaje);

    }

    public void validarPunto(){

        // si el centro de los emojis es el de carrito se cuenta un punto

        //Adulto

        int adultoCenterX = adultoX + adulto.getWidth()/2;
        int adultoCenterY = adultoY + adulto.getHeight()/2;

        if (0<= adultoCenterX && adultoCenterX <= tamañocarro && carritoY <= adultoCenterY && adultoCenterY <= carritoY + tamañocarro ){

            puntaje += 10;
            adultoX = -10;

            sound.playSonidoAcierto();

        }

        //Niño

        int ninoCenterX = ninoX + nino.getWidth()/2;
        int ninoCenterY = ninoY + nino.getHeight()/2;

        if (0<= ninoCenterX && ninoCenterX <= tamañocarro && carritoY <= ninoCenterY && ninoCenterY <= carritoY + tamañocarro ){

            puntaje -= 5;
            ninoX = -10;

            sound.playSonidoAcierto();





        }

        //Perro

        int perroCenterX = perroX + perro.getWidth()/2;
        int perroCenterY = perroY + perro.getHeight()/2;

        if (0<= perroCenterX && perroCenterX <= tamañocarro && carritoY <= perroCenterY && perroCenterY <= carritoY + tamañocarro ){

            // para tiempo
            timer.cancel();
            timer = null;

            sound.playSonidoDesacierto();

            //Mostrar resultados

            Intent intent = new Intent(getApplicationContext(),Resultado.class);
            intent.putExtra("Puntaje",puntaje);
            startActivity(intent);



        }

    }

    public boolean onTouchEvent(MotionEvent me){

        if(inicio_flg == false){

            inicio_flg = true;

            FrameLayout pantalla = (FrameLayout) findViewById(R.id.frame);
            altopantalla = pantalla.getHeight();
            carritoY = (int)carrito.getY();

            tamañocarro = carrito.getHeight();




            inicioLabel.setVisibility(View.GONE);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            cambioPos();
                        }
                    });

                }
            },0,20);

        } else {

            if (me.getAction() == MotionEvent.ACTION_DOWN){
                action_flg =true;
            }else if (me.getAction() == MotionEvent.ACTION_UP){
                action_flg = false;
            }
        }

        return true;
    }

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
