package com.example.diego.practica1;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.Principal;

public class MainActivity extends AppCompatActivity {

// Creamos las variables
    private String usuario;
    private EditText Nombre;
    private Button Comenzar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Referenciamos
        Nombre= (EditText)findViewById(R.id.txtnombre);
        Comenzar = (Button) findViewById(R.id.comenzar);
        Comenzar.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) // Evento para cuando se haga click en Comenzar
            {

                //Creamos un intent que hara que pasemos de la actividad principal
                // a la actividad "Pregunta1" mediante el evento de pulsar en Comenzar
                Intent siguiente = new Intent(MainActivity.this,Pregunta1.class);
                //Vamos a guardar el usuario que introduciran para
                // que con un key se pueda acceder a el desde otras actividades
                usuario = Nombre.getText().toString();
                //Creamos un toast para indicar que empezamos con el cuestionario
                Toast.makeText(MainActivity.this,getString(R.string.Cuestionario)+" "+usuario.toUpperCase()+"!",Toast.LENGTH_SHORT).show();
                //Con el intent creado anteriormente le mandamos
                // la informacion a la siguiente actividad
                siguiente.putExtra("nombreusuario",usuario);
                //Iniciamos la actividad con el intent creado en este caso "siguiente"
                startActivity(siguiente);
            }
        });


    }

    //Creamos el menu por defecto
    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.menu, m);
        return true; ///Siempre debe devolver true
    }

    // Para cuando seleccionemos una opcion del menu, en este caso solo vamos a tener
    // 1 pero serviria si tubieramos mas de una opcion.
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()) {
            case R.id.acercade:
            //creamos el toast para que muestre el mensaje guardado en string.Diseñadorapp y luego lo mostramos con.show
                Toast.makeText(MainActivity.this, getString(R.string.Diseñadorapp), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}
