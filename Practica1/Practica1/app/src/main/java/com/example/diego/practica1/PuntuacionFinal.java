package com.example.diego.practica1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PuntuacionFinal extends AppCompatActivity {

    private String respuestaFinal;
    private int puntuacionFinal=0;
    private ArrayList<String> preguntayrespuesta = new ArrayList<String>();
    private TextView resultadoFinal;
    private Button nuevoJuego;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuacion_final);
        resultadoFinal= (TextView) findViewById(R.id.ResultadoFinal);
        preguntayrespuesta = getIntent().getExtras().getStringArrayList("Array1");
        nuevoJuego = (Button) findViewById(R.id.comenzar);

        //CALCULOS PARA PREGUNTA 1
        //******************************************************************
        //Comparamos la cadena contenida en el array en la posicion 0
        // con la cadena guardada en R.string.respuestacorrecta1
        //si se cumple el usuario sumara 1 punto
        //si no se cumple seguira con los mismo puntos
        if(preguntayrespuesta.get(0).contentEquals(getString(R.string.respuestacorrecta1))){
            puntuacionFinal++;
        }
        //CALCULOS PARA PREGUNTA 2
        //******************************************************************
        if (preguntayrespuesta.get(1).contentEquals(getString(R.string.respuestacorrecta2))) {
           puntuacionFinal++;
        }
        //CALCULOS PARA PREGUNTA 3
        //******************************************************************
        if (preguntayrespuesta.get(2).contentEquals(getString(R.string.respuestacorrecta3))) {
            puntuacionFinal++;
        }
        //CALCULOS PARA PREGUNTA 4
        //******************************************************************
        if (preguntayrespuesta.get(3).contentEquals(getString(R.string.respuestacorrecta4))) {
            puntuacionFinal++;
        }
        //CALCULOS PARA PREGUNTA 5
        //******************************************************************
        if (preguntayrespuesta.get(4).contentEquals(getString(R.string.respuestacorrecta5))) {
            puntuacionFinal++;
        }
        //CALCULOS PARA PREGUNTA 6
        //******************************************************************
        if (preguntayrespuesta.get(5).contentEquals(getString(R.string.respuestacorrecta6))) {
            puntuacionFinal++;
        }
        //CALCULOS PARA PREGUNTA 7
        //******************************************************************
        if(preguntayrespuesta.get(6).contentEquals(getString(R.string.respuestacorrecta7)))
        {
            puntuacionFinal++;
        }
        //CALCULOS PARA PREGUNTA 8
        //******************************************************************
        if(preguntayrespuesta.get(7).contentEquals(getString(R.string.respuestacorrecta8)))
        {
            puntuacionFinal++;
        }
        //CALCULOS PARA PREGUNTA 9
        //******************************************************************
        if(preguntayrespuesta.get(8).contentEquals(getString(R.string.respuestacorrecta9)))
        {
            puntuacionFinal++;
        }
        //CALCULOS PARA PREGUNTA 10
        //******************************************************************
        if(preguntayrespuesta.get(9).contentEquals(getString(R.string.respuestacorrecta10)))
        {
            puntuacionFinal++;
        }

        //Mostrara el resultado final en el textview ResultadoFinal
        resultadoFinal.setText(puntuacionFinal + "/" + "10");
        resultadoFinal.setTextSize(50);

         // Evento para cuando se haga click en Comenzar de nuevo
        nuevoJuego.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creamos un intent que hara que pasemos de la actividad PuntuacionFinal
                // a la actividad "MainActivity" mediante el evento de pulsar en Comenzar de nuevo
                Intent nuevojuego = new Intent(PuntuacionFinal.this, MainActivity.class);
                startActivity(nuevojuego);
            }

        });
    }
}