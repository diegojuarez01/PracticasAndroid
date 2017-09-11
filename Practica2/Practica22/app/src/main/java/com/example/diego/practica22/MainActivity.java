package com.example.diego.practica22;

import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Creamos las variables
    private String usuario, password;
    private EditText introducirusuario, introducirpassword;
    private Button Aceptar;
    private GestureLibrary libreriagestos;
    private Gesture mGesture;
    private Usuario usuAux = new Usuario();
    //Nombre del archivo donde guardaremos los usuarios en la memoria interna
    private static final String Documentousuarios = "usuarios.txt";
    //Tamaño minimo del gesto
    private static final float LENGTH_THRESHOLD = 120.0f;
    private int indice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Referenciamos
        introducirusuario = (EditText) findViewById(R.id.IntroducirUsuario);
        introducirpassword = (EditText) findViewById(R.id.IntroducirPassword);
        Aceptar = (Button) findViewById(R.id.Aceptar);
        //Le referenciamos a la vista del gesto  el R.id.gestureoverlayview
        final GestureOverlayView overlay = (GestureOverlayView) findViewById(R.id.gestureOverlayView);
        overlay.addOnGestureListener(new ProcesadorDeGestos());
        //Ponemos el titulo de la actividad
        setTitle(getResources().getText(R.string.titulomain));
        final ArrayList<Usuario> arrayusuarios = new ArrayList<>();

        Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) // Evento para cuando se haga click en Aceptar
            {
                //obtenemos los datos introducidos por el usuario y los guardamos pasados a String para comparar
                usuario = introducirusuario.getText().toString();
                password = introducirpassword.getText().toString();

                if (usuario.isEmpty()) {
                    Toast.makeText(MainActivity.this, getString(R.string.faltausuario), Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(MainActivity.this, getString(R.string.faltapassword), Toast.LENGTH_SHORT).show();
                } else if (password.length() < 8) {
                    Toast.makeText(MainActivity.this, getString(R.string.minimopassword), Toast.LENGTH_SHORT).show();
                }
                else if (mGesture==null){
                    Toast.makeText(MainActivity.this, getString(R.string.faltagesto), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Comprobamos si el usuario, la contraseña y el gesto coinciden con algun usuario guardado en nuestro archivo de usuarios.
                    //Primero vamos a leer el archivo donde se encuentran todos los usuarios guardados.
                    try {
                        BufferedReader fin = new BufferedReader(new InputStreamReader((openFileInput(Documentousuarios))));
                        String texto = fin.readLine();
                        Gson gson = new Gson();
                        //Separamos los objetos Usuario y los guardamos en un array String
                        String[] parts = texto.split("-SEPARADOR-");
                       //Recorremos el array donde hemos guardado el texto de cada objeto Usuario
                        for(indice=0;indice<parts.length;indice++) {
                            //Creamos un usuario con la el texto deserializado para cada usuario
                            usuAux = gson.fromJson(parts[indice], Usuario.class);
                            //añadimos a el arrayusuarios los objetos devueltos al deserializar
                            arrayusuarios.add(usuAux);
                        }
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), getString(R.string.errorleyendofichero), Toast.LENGTH_SHORT).show();
                    }
                    //hacemos un bucle recorriendo el array de usuarios donde tenemos todos los usuarios guardados
                        for(indice=0;indice<arrayusuarios.size();indice++) {
                        //Cargamos el fichero de gestos
                        libreriagestos = GestureLibraries.fromRawResource(MainActivity.this, R.raw.libreriagestos);
                        //Asignamos el gesto en cuestión
                        libreriagestos.addGesture(arrayusuarios.get(indice).getAlias(), arrayusuarios.get(indice).getGestousuario());
                        libreriagestos.load();
                            //Si el alias de usuario que tenemos registrado es igual al introducido en el EditText comprobaremos el password
                            if(arrayusuarios.get(indice).getAlias().contentEquals(usuario))
                            {
                                //Si ademas del usuario el password del usuario que tenemos registrado es igual al introducido en el EditText comprobaremos el gesto
                                if(arrayusuarios.get(indice).getContraseña().contentEquals(password))
                                {
                                    //Si no se carga la libreriagestos terminara la actividad
                                    if (!libreriagestos.load()) {
                                        Toast.makeText(getApplicationContext(), getString(R.string.libreriacomprobada), Toast.LENGTH_LONG).show();
                                    }
                                    if (gestureCorrect(mGesture))
                                    {
                                        Toast.makeText(getApplicationContext(), getString(R.string.usuOkgestoOk), Toast.LENGTH_LONG).show();
                                        //Una vez comprobado el usuario y el password si el gesto se corresponde
                                        // con el gesto guardado para ese usuario y password pasaremos a la nueva actividad
                                        Intent intent = new Intent(getApplicationContext(), ListaLoginActivity.class);
                                        //A esta nueva actividad le pasaremos el valor del usuario identificado el alias,el email y la foto
                                        intent.putExtra("aliasusu", arrayusuarios.get(indice).getAlias());
                                        intent.putExtra("emailusu",arrayusuarios.get(indice).getEmail());
                                        intent.putExtra("imagenusu",arrayusuarios.get(indice).getFotousuario());
                                        startActivity(intent);
                                        //No se comprobaran mas usuarios
                                        finish();
                                        return;
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), getString(R.string.usuOkgestoNo), Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                }
                                //Si no se cumple el usuario o la password pasara al siguiente usuario a comprobar
                            }
                        }
                    //Si recorre el buqle y no encuentra un usuario con la misma password y gesto se imprimira un mensaje por pantalla
                    Toast.makeText(getApplicationContext(), getString(R.string.usuNOgestoNo), Toast.LENGTH_LONG).show();
                    return;
                }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.añadirusuario:
                //Creamos un intent que hara que pasemos de la actividad principal a la actividad "NuevoUsuario"
                Intent añadirusuario = new Intent(MainActivity.this, NuevoUsuario.class);
                //Iniciamos la actividad con el intent creado en este caso "añadirusuario"
                startActivity(añadirusuario);
        }
        return true;
    }

    //Metodo para comprobar si el gesto coincide
    private boolean gestureCorrect(Gesture gesture) {
        boolean found = false;
        ArrayList<Prediction> predictions = libreriagestos.recognize(gesture);
        //Si a encontradoalgun resultado
        if (predictions.size() > 0) {
            //En este caso solos nos interesa el gesto que más se parezca
            Prediction prediccion = predictions.get(0);
            //Miramos que tengo un parecido mínimo
            if (prediccion.score > 1.5) {
                //Decimos lo que ha escrito
                Toast.makeText(this, prediccion.name, Toast.LENGTH_SHORT).show();
                found = true;
            }
        }
        return found;
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mGesture != null) {
            outState.putParcelable("gesture", mGesture);
        }
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mGesture = savedInstanceState.getParcelable("gesture");
        if (mGesture != null) {
            final GestureOverlayView overlay =
                    (GestureOverlayView) findViewById(R.id.gestureOverlayView);
            overlay.post(new Runnable() {
                public void run() {
                    overlay.setGesture(mGesture);
                }
            });
            Aceptar.setEnabled(true);
        }
    }

    private class ProcesadorDeGestos implements GestureOverlayView.OnGestureListener {
        @Override
        public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
            //Al inicio del gesto, el gesto debe de ser nulo para que reinicie el valor
            // con cada nuevo gesto introducido por el usuario
            mGesture = null;
        }
        @Override
        public void onGesture(GestureOverlayView overlay, MotionEvent event) {

        }
        @Override
        public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
            mGesture = overlay.getGesture();
            if (mGesture.getLength() < LENGTH_THRESHOLD) {
                overlay.clear(false);
            }
        }
        @Override
        public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
        }
    }
}



