package com.example.diego.practica1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Pregunta8 extends AppCompatActivity {

    // Creamos las variables
    private String nombreUsuario,respuestaFinal;
    private TextView nombredelusuario;
    private ProgressBar paso8;
    private Button siguiente,anterior;
    private Spinner listadesplegable;
    private String [] preguntas8;
    private ArrayList<String> preguntayrespuesta = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta8);
        nombredelusuario = (TextView) findViewById(R.id.txtnomusuario);
        paso8 = (ProgressBar) findViewById(R.id.Barraprogreso1);
        siguiente = (Button) findViewById(R.id.Siguiente1);
        anterior = (Button) findViewById(R.id.Anterior);
        listadesplegable = (Spinner) findViewById(R.id.MenuDes8);

        //Asignamos el valor dentro del array a el array preguntas8
        preguntas8 = getResources().getStringArray(R.array.respuestas8);

        // Creamos un array de cadenas
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,preguntas8);
        //Establecemos en el spinner el adaptador
        listadesplegable.setAdapter(adaptador);
        listadesplegable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            //Para el item que esta siendo seleccionado
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Dependiendo de la posicion en la que se encuentre el selector actura de una forma u otra
                switch (position) {
                    case 0:
                        respuestaFinal = getString(R.string.respuesta8a);
                        break;
                    case 1:
                        respuestaFinal = getString(R.string.respuesta8b);
                        break;
                    case 2:
                        respuestaFinal = getString(R.string.respuesta8c);
                        break;
                    case 3:
                        respuestaFinal = getString(R.string.respuesta8d);
                        break;
                    case 4:
                        respuestaFinal = getString(R.string.respuesta8e);
                        break;
                    case 5:
                        respuestaFinal = getString(R.string.respuesta8f);
                        break;
                    case 6:
                        respuestaFinal = getString(R.string.respuesta8g);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Vamos a establecer el progreso de la barra para la pregunta 6 en 60 puntos
        //este valor lo iremos aumentando para cada nueva pregunta en 10 puntos.

        paso8.setProgress(80);

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
                preguntayrespuesta.add(respuestaFinal);
                Toast.makeText(Pregunta8.this, "Array: " + preguntayrespuesta, Toast.LENGTH_SHORT).show();
                //Creamos un intent que hara que pasemos de la actividad pregunta8
                // a la actividad "Pregunta9" mediante el evento de pulsar en Siguiente
                Intent siguiente = new Intent(Pregunta8.this, Pregunta9.class);
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
                //Creamos un intent que hara que pasemos de la actividad "pregunta8"
                // a la actividad "Pregunta7" mediante el evento de pulsar en Siguiente
                Intent anterior = new Intent(Pregunta8.this,Pregunta7.class);
                //Con el intent creado anteriormente le mandamos
                // la informacion a la siguiente actividad
                anterior.putExtra("nombreusuario",nombreUsuario);
                //Se remueve el ultimo string introducido en el array y se vuelve atras.
                preguntayrespuesta.remove(6);
                anterior.putExtra("Array1", preguntayrespuesta);
                //Iniciamos la actividad con el intent creado en este caso "anterior"
                startActivity(anterior);


            }

        });
    }
}
