package com.example.diego.practica1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class Pregunta9 extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    // Creamos las variables
    private String nombreUsuario,respuestaFinal;
    private TextView nombredelusuario;
    private ProgressBar paso9;
    private int puntuacion;
    private Button siguiente,anterior;
    private ToggleButton Verdaderofalso;
    private ArrayList<String> preguntayrespuesta = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta9);
        nombredelusuario = (TextView) findViewById(R.id.txtnomusuario);
        paso9 = (ProgressBar) findViewById(R.id.Barraprogreso1);
        siguiente = (Button) findViewById(R.id.Siguiente1);
        anterior = (Button) findViewById(R.id.Anterior);
        Verdaderofalso = (ToggleButton) findViewById(R.id.Verdaderofalso);

        Verdaderofalso.setOnCheckedChangeListener(this);

        //Vamos a establecer el progreso de la barra para la pregunta 9 en 90 puntos
        //este valor lo iremos aumentando para cada nueva pregunta en 10 puntos.

        paso9.setProgress(90);

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
            public void onClick(View v)
            {
                //Si elegimos verdadero sumara 1 punto a la puntuacion total.
                if(Verdaderofalso.isChecked())
                {
                    respuestaFinal=getString(R.string.respuesta9a);
                    Toast.makeText(Pregunta9.this, "Ha elegido verdadero", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    respuestaFinal=getString(R.string.respuesta9b);
                    Toast.makeText(Pregunta9.this, "Ha elegido falso", Toast.LENGTH_SHORT).show();
                }
                preguntayrespuesta.add(respuestaFinal);
                Toast.makeText(Pregunta9.this, "Array: " + preguntayrespuesta, Toast.LENGTH_SHORT).show();
                //Creamos un intent que hara que pasemos de la actividad pregunta9
                // a la actividad "Pregunta10" mediante el evento de pulsar en Siguiente
                Intent siguiente = new Intent(Pregunta9.this, Pregunta10.class);
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
                //Creamos un intent que hara que pasemos de la actividad "pregunta9"
                // a la actividad "Pregunta8" mediante el evento de pulsar en Siguiente
                Intent anterior = new Intent(Pregunta9.this,Pregunta8.class);
                //Con el intent creado anteriormente le mandamos
                // la informacion a la siguiente actividad
                anterior.putExtra("nombreusuario",nombreUsuario);
                //Se remueve el ultimo string introducido en el array y se vuelve atras.
                preguntayrespuesta.remove(7);
                anterior.putExtra("Array1", preguntayrespuesta);
                //Iniciamos la actividad con el intent creado en este caso "anterior"
                startActivity(anterior);


            }

        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        //Si cambia de Off a On ejecutara un codigo

        if(isChecked)
        {

        }

        //Si cambia de On a Off ejecutara un codigo distinto

        else
        {

        }


    }
}
