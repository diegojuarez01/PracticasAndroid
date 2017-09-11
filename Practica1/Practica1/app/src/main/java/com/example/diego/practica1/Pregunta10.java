package com.example.diego.practica1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class Pregunta10 extends AppCompatActivity {

    // Creamos las variables
    private String nombreUsuario,respuestaFinal;
    private TextView nombredelusuario;
    private ProgressBar paso10;
    private Button siguiente,anterior;
    private NumberPicker añopregunta10;
    private ArrayList<String> preguntayrespuesta = new ArrayList<String>();
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta10);
        nombredelusuario = (TextView) findViewById(R.id.txtnomusuario);
        paso10 = (ProgressBar) findViewById(R.id.Barraprogreso1);
        siguiente = (Button) findViewById(R.id.Siguiente1);
        anterior = (Button) findViewById(R.id.Anterior);
        añopregunta10 = (NumberPicker) findViewById(R.id.FechaPregunta10);
        relativeLayout = (RelativeLayout) findViewById(R.id.layout10);

        //Establecemos los valores maximo y minimo para el NumberPicker
        añopregunta10.setMaxValue(2016);
        añopregunta10.setMinValue(2005);

        //Vamos a establecer el progreso de la barra para la pregunta 10 en 100 puntos
        //este valor lo iremos aumentando para cada nueva pregunta en 10 puntos.

        paso10.setProgress(100);

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
                //Voy a crear una alerta para que cuando el usuario pulsa en siguiente
                //le aparezca un mensaje si esta seguro que quiere acabar el cuestionario,
                // ya que es la ultima pregunta.Si le da a que no volvera a la activity pregunta10, si le da que si
                // pasara a la activity PuntuacionFinal.
                final AlertDialog.Builder alertaFinalizar = new AlertDialog.Builder(Pregunta10.this);
                alertaFinalizar.setTitle(getString(R.string.tituloalerta));
                alertaFinalizar.setMessage(getString(R.string.preguntaalerta));

                alertaFinalizar.setPositiveButton(getString(R.string.sialerta), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        respuestaFinal = Integer.toString(añopregunta10.getValue());
                        preguntayrespuesta.add(respuestaFinal);
                        Toast.makeText(Pregunta10.this, "Array: " + preguntayrespuesta, Toast.LENGTH_SHORT).show();
                        //Creamos un intent que hara que pasemos de la actividad pregunta10
                        // a la actividad "PuntuacionFinal" mediante el evento de pulsar en Siguiente
                        Intent siguiente = new Intent(Pregunta10.this, PuntuacionFinal.class);
                        //Con el intent creado anteriormente le mandamos
                        // la informacion a la siguiente actividad
                        siguiente.putExtra("nombreusuario", nombreUsuario);
                        siguiente.putExtra("Array1", preguntayrespuesta);
                        //Iniciamos la actividad con el intent creado en este caso "siguiente"
                        startActivity(siguiente);
                    }
                });
                alertaFinalizar.setNegativeButton(getString(R.string.noalerta), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Volvemos a su estado original
                        relativeLayout.setBackgroundColor(WHITE);
                        anterior.setVisibility(0);
                        siguiente.setVisibility(0);
                        añopregunta10.setVisibility(0);
                    }
                });

               //Utilizamos el metodo setCancelable para que al pulsar en la pantalla no
               // nos saque del alert, es decir debemos de responder al alert forzadamente.
                alertaFinalizar.setCancelable(false);
                //Creamos la alerta y la mostramos
                alertaFinalizar.create();
                alertaFinalizar.show();

                //Cambiamos el fondo a negro y ocultamos los botones y el numberpick
                relativeLayout.setBackgroundColor(BLACK);
                anterior.setVisibility(v.INVISIBLE);
                siguiente.setVisibility(v.INVISIBLE);
                añopregunta10.setVisibility(v.INVISIBLE);
            }

        });

        // Evento para cuando se haga click en anterior
        anterior.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Creamos un intent que hara que pasemos de la actividad "pregunta10"
                // a la actividad "Pregunta9" mediante el evento de pulsar en Siguiente
                Intent anterior = new Intent(Pregunta10.this,Pregunta9.class);
                //Con el intent creado anteriormente le mandamos
                // la informacion a la siguiente actividad
                anterior.putExtra("nombreusuario", nombreUsuario);
                //Se remueve el ultimo string introducido en el array y se vuelve atras.
                preguntayrespuesta.remove(8);
                anterior.putExtra("Array1", preguntayrespuesta);
                //Iniciamos la actividad con el intent creado en este caso "anterior"
                startActivity(anterior);


            }

        });
    }
}
