package com.example.diego.practica1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Pregunta7 extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    // Creamos las variables
    private String nombreUsuario,respuestaFinal;
    private TextView nombredelusuario,cantidad;
    private ProgressBar paso7;
    private int puntuacion;
    private Button siguiente,anterior;
    private SeekBar Seekbar1;
    private ArrayList<String> preguntayrespuesta = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta7);
        nombredelusuario = (TextView) findViewById(R.id.txtnomusuario);
        paso7 = (ProgressBar) findViewById(R.id.Barraprogreso1);
        siguiente = (Button) findViewById(R.id.Siguiente1);
        anterior = (Button) findViewById(R.id.Anterior);
        cantidad = (TextView) findViewById(R.id.Cantidad);
        Seekbar1= (SeekBar) findViewById(R.id.Seekbar1);

        Seekbar1.setOnSeekBarChangeListener(this);
        //Vamos a establecer el progreso de la barra para la pregunta 7 en 70 puntos
        //este valor lo iremos aumentando para cada nueva pregunta en 10 puntos.

        paso7.setProgress(70);

        //Guardaremos en nombreUsuario el valor del string introducido
        // por el usuario en la actividad anterior donde la key sera "nombreusuario"
        //que hemos establecido en esta linea "siguiente.putExtra("nombreusuario",nombreUsuario);"

        nombreUsuario = getIntent().getExtras().getString("nombreusuario");
        preguntayrespuesta = getIntent().getExtras().getStringArrayList("Array1");

        //Vamos a establecer como valor de texto al TextView el nombre
        // introducido por el usuario en la actividad anterior.
        nombredelusuario.setText(nombreUsuario);

        // Evento para cuando se haga click en Siguiente
        siguiente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Pasamos el textview donde se encuentra la cantidad de jugadores a String
                // para luego poder comprobar el resultado.
                respuestaFinal = cantidad.getText().toString();

                preguntayrespuesta.add(respuestaFinal);
                Toast.makeText(Pregunta7.this, "Array: " + preguntayrespuesta, Toast.LENGTH_SHORT).show();

                //Creamos un intent que hara que pasemos de la actividad pregunta7
                // a la actividad "Pregunta8" mediante el evento de pulsar en Siguiente
                Intent siguiente = new Intent(Pregunta7.this, Pregunta8.class);
                //Con el intent creado anteriormente le mandamos
                // la informacion a la siguiente actividad
                siguiente.putExtra("nombreusuario", nombreUsuario);
                siguiente.putExtra("Array1", preguntayrespuesta);
                //Iniciamos la actividad con el intent creado en este caso "siguiente"
                startActivity(siguiente);
            }

        });

        // Evento para cuando se haga click en anterior
        anterior.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Creamos un intent que hara que pasemos de la actividad "pregunta7"
                // a la actividad "Pregunta6" mediante el evento de pulsar en Siguiente
                Intent anterior = new Intent(Pregunta7.this,Pregunta6.class);
                //Con el intent creado anteriormente le mandamos
                // la informacion a la siguiente actividad
                anterior.putExtra("nombreusuario",nombreUsuario);
                //Se remueve el ultimo string introducido en el array y se vuelve atras.
                preguntayrespuesta.remove(5);
                anterior.putExtra("Array1", preguntayrespuesta);
                //Iniciamos la actividad con el intent creado en este caso "anterior"
                startActivity(anterior);


            }

        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        //La cantidad ira subiendo conforme avancemos la barra.
    cantidad.setText(""+progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
