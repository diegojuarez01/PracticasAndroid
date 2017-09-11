package com.example.diego.practica1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Pregunta1 extends AppCompatActivity  {

    // Creamos las variables
    private String nombreUsuario,respuestafinal;
    private TextView nombredelusuario;
    private ProgressBar paso1;
    private Button siguiente;
    private int respuesta1;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioGroup radioGroup1;
    ArrayList<String> preguntayrespuesta = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta1);

        nombredelusuario = (TextView) findViewById(R.id.txtnomusuario);
        paso1 = (ProgressBar) findViewById(R.id.Barraprogreso1);
        siguiente = (Button) findViewById(R.id.Siguiente1);

        radioGroup1 = (RadioGroup) findViewById(R.id.grupo1);

        radioButton1 = (RadioButton) findViewById(R.id.radio1a);
        radioButton2 = (RadioButton) findViewById(R.id.radio1b);
        radioButton3 = (RadioButton) findViewById(R.id.radio1c);


                //Vamos a establecer el progreso de la barra para la pregunta 1 en 10 puntos
                //este valor lo iremos aumentando para cada nueva pregunta en 10 puntos.

                paso1.setProgress(10);

        //Guardaremos en nombreUsuario el valor del string introducido
        // por el usuario en la actividad anterior donde la key sera "nombreusuario"
        //que hemos establecido en esta linea "siguiente.putExtra("nombreusuario",usuario);"

        nombreUsuario = getIntent().getExtras().getString("nombreusuario");

        //Vamos a establecer como valor de texto al TextView el nombre
        // introducido por el usuario en la actividad anterior.
        nombredelusuario.setText(nombreUsuario);

        siguiente.setOnClickListener(new View.OnClickListener() // Evento para cuando se haga click en Siguiente
        {

            @Override
            public void onClick(View v)
            {

            //Guardamos el id que esta seleccionado al pulsar siguiente en la variable respuesta
            respuesta1= radioGroup1.getCheckedRadioButtonId();

            //Dependiendo de la id de respuesta se ejecutara un bloque de codigo distinto.
             switch (respuesta1)
             {
                 case R.id.radio1a:
                     respuestafinal=getString(R.string.respuesta1a);
                     break;
                 //Para cuando la opcion es incorrecta no hara nada,le podriamos poner un toast
                 //avisando de que la opcion es incorrecta pero en esta ocasion no lo vamos a hacer asi
                 // ya que nuestra aplicacion permite volver a la pregunta anterior.
                 case R.id.radio1b:
                     respuestafinal=getString(R.string.respuesta1b);
                    break;
                 case R.id.radio1c:
                     respuestafinal=getString(R.string.respuesta1c);
                     break;
             }


                //Solo pasara a la siguiente actividad cuando haya algun elemento de los radiobuttons checkeado

                if(radioButton1.isChecked()||radioButton2.isChecked()||radioButton3.isChecked())
                {
                    //Creamos un intent que hara que pasemos de la actividad pregunta1
                    // a la actividad "Pregunta2" mediante el evento de pulsar en Siguiente
                    Intent siguiente = new Intent(Pregunta1.this,Pregunta2.class);
                    //Añadimos la respuesta definitiva dentro del array
                    preguntayrespuesta.add(respuestafinal);

                    //Con el intent creado anteriormente le mandamos
                    // la informacion a la siguiente actividad
                    siguiente.putExtra("nombreusuario",nombreUsuario);
                    siguiente.putExtra("Array1",preguntayrespuesta);

                    Toast.makeText(Pregunta1.this, "Array: "+preguntayrespuesta, Toast.LENGTH_SHORT).show();
                    //Iniciamos la actividad con el intent creado en este caso "siguiente"
                    startActivity(siguiente);
                }

                // Si no hay ninguna opcion seleccionada le aparecera
                // un mensaje al usuario para que introduzca o seleccione una opcion
               else
                {
                    Toast.makeText(Pregunta1.this, "Por favor seleccione una opción", Toast.LENGTH_SHORT).show();
                }


            }
        });





    }


}
