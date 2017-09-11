package com.example.diego.practica1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Pregunta2 extends AppCompatActivity
{

    // Creamos las variables
private String nombreUsuario,respuesta;
private TextView nombredelusuario,añomun;
private ProgressBar paso2;
private Button siguiente,anterior;

private ArrayList<String> preguntayrespuesta = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta2);
        nombredelusuario = (TextView) findViewById(R.id.txtnomusuario);
        paso2 = (ProgressBar) findViewById(R.id.Barraprogreso1);
        siguiente = (Button) findViewById(R.id.Siguiente1);
        anterior = (Button) findViewById(R.id.Anterior);
        añomun = (TextView) findViewById(R.id.añomun);

        //Vamos a establecer el progreso de la barra para la pregunta 2 en 20 puntos
        //este valor lo iremos aumentando para cada nueva pregunta en 10 puntos.

        paso2.setProgress(20);

        //Guardaremos en nombreUsuario el valor del string introducido
        // por el usuario en la actividad anterior donde la key sera "nombreusuario"
        //que hemos establecido en esta linea "siguiente.putExtra("nombreusuario",nombreUsuario);"

        nombreUsuario = getIntent().getExtras().getString("nombreusuario");

        //Guardamos las respuestas en un array.
        preguntayrespuesta = getIntent().getExtras().getStringArrayList("Array1");


        //Vamos a establecer como valor de texto al TextView el nombre
        // introducido por el usuario en la actividad anterior.
        nombredelusuario.setText(nombreUsuario);

        // Evento para cuando se haga click en Siguiente
        siguiente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Convertimos el textview en un String
                respuesta = añomun.getText().toString();
                //Para cuando no se introduzca nada en el textView aparecera un mensaje
                // para que el usuario introduzca una fecha.
                if (respuesta.isEmpty())
                {
                    Toast.makeText(Pregunta2.this, "Debe introducir un año", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    preguntayrespuesta.add(respuesta);
                    //Creamos un intent que hara que pasemos de la actividad pregunta2
                    // a la actividad "Pregunta3" mediante el evento de pulsar en Siguiente
                    Intent siguiente = new Intent(Pregunta2.this, Pregunta3.class);
                    //Con el intent creado anteriormente le mandamos
                    // la informacion a la siguiente actividad
                    siguiente.putExtra("nombreusuario", nombreUsuario);
                    siguiente.putExtra("Array1",preguntayrespuesta);
                    Toast.makeText(Pregunta2.this, "Array: "+preguntayrespuesta, Toast.LENGTH_SHORT).show();
                    //Iniciamos la actividad con el intent creado en este caso "siguiente"
                    startActivity(siguiente);
                }

            }

        });

        // Evento para cuando se haga click en anterior
        anterior.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Creamos un intent que hara que pasemos de la actividad "pregunta2"
                // a la actividad "Pregunta1" mediante el evento de pulsar en Siguiente
                Intent anterior = new Intent(Pregunta2.this,Pregunta1.class);
                //Con el intent creado anteriormente le mandamos
                // la informacion a la siguiente actividad
                anterior.putExtra("nombreusuario",nombreUsuario);
                //Iniciamos la actividad con el intent creado en este caso "anterior"
                startActivity(anterior);


            }

        });
    }
}
