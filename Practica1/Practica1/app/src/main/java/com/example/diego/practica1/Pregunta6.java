package com.example.diego.practica1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Pregunta6 extends AppCompatActivity {

    // Creamos las variables
    private String nombreUsuario,respuesta1,respuesta2,respuesta3,respuesta4,respuesta5;
    private TextView nombredelusuario;
    private ProgressBar paso6;
    private Button siguiente,anterior;
    private CheckBox checkbox1,checkbox2,checkbox3,checkbox4,checkbox5;
    private ArrayList<String> preguntayrespuesta = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta6);
        nombredelusuario = (TextView) findViewById(R.id.txtnomusuario);
        paso6 = (ProgressBar) findViewById(R.id.Barraprogreso1);
        siguiente = (Button) findViewById(R.id.Siguiente1);
        anterior = (Button) findViewById(R.id.Anterior);
        checkbox1 = (CheckBox) findViewById(R.id.checkBox);
        checkbox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkbox3 = (CheckBox) findViewById(R.id.checkBox3);
        checkbox4 = (CheckBox) findViewById(R.id.checkBox4);
        checkbox5 = (CheckBox) findViewById(R.id.checkBox5);

        //Vamos a establecer el progreso de la barra para la pregunta 6 en 60 puntos
        //este valor lo iremos aumentando para cada nueva pregunta en 10 puntos.

        paso6.setProgress(60);

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


                //Dependiendo del checkbox que este seleccionado le daremos valores a las
                // diferentes respuestas que luego pasaremos al array.
                        if (checkbox1.isChecked())
                        {
                            respuesta1 = getString(R.string.respuesta6a);
                        }
                        else{
                            respuesta1="";
                        }

                         if (checkbox2.isChecked())
                             {
                             respuesta2 = getString(R.string.respuesta6b);
                          }

                        else
                        {
                            respuesta2="";
                        }

                        if (checkbox3.isChecked())
                        {
                            respuesta3 = getString(R.string.respuesta6c);
                        }

                        else
                        {
                            respuesta3="";
                        }
                        if (checkbox4.isChecked())
                        {
                            respuesta4 = getString(R.string.respuesta6d);
                        }
                        else
                        {
                            respuesta4="";
                        }
                        if (checkbox5.isChecked())
                        {
                            respuesta5 = getString(R.string.respuesta6e);
                        }
                        else
                        {
                            respuesta5="";
                        }

                //Guardamos las posibles respuestas del usuario haciendo un espacio entre cada una de ella que
                // luego deberemos de tener en cuanto a la hora de comprobar el resultado.

                String respuestaFinal= respuesta1+respuesta2+respuesta3+respuesta4+respuesta5;
                preguntayrespuesta.add(respuestaFinal);
                Toast.makeText(Pregunta6.this, "Array: " + preguntayrespuesta, Toast.LENGTH_SHORT).show();
                //Creamos un intent que hara que pasemos de la actividad pregunta6
                // a la actividad "Pregunta7" mediante el evento de pulsar en Siguiente
                Intent siguiente = new Intent(Pregunta6.this, Pregunta7.class);
                //Con el intent creado anteriormente le mandamos
                // la informacion a la siguiente actividad
                siguiente.putExtra("nombreusuario", nombreUsuario);
                siguiente.putExtra("Array1", preguntayrespuesta);
                //Iniciamos la actividad con el intent creado en este caso "siguiente"
                startActivity(siguiente);
            }

        });

        // Evento para cuando se haga click en anterior
        anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos un intent que hara que pasemos de la actividad "pregunta6"
                // a la actividad "Pregunta5" mediante el evento de pulsar en Siguiente
                Intent anterior = new Intent(Pregunta6.this, Pregunta5.class);
                //Con el intent creado anteriormente le mandamos
                // la informacion a la siguiente actividad
                anterior.putExtra("nombreusuario", nombreUsuario);
                //Se remueve el ultimo string introducido en el array y se vuelve atras.
                preguntayrespuesta.remove(4);
                anterior.putExtra("Array1", preguntayrespuesta);
                //Iniciamos la actividad con el intent creado en este caso "anterior"
                startActivity(anterior);


            }

        });
    }
}
