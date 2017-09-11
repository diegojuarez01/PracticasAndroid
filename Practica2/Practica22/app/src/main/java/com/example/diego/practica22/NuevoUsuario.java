package com.example.diego.practica22;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

public class NuevoUsuario extends AppCompatActivity {


    private Button Galeria,Camara,Aceptar,Cancelar;
    private ImageView Fotousuario;
    private Intent Seleccionarimagen;
    private final static int Seleccionar_foto = 1;
    private final static int Capturar_foto = 2;
    private Bitmap bmp;
    private EditText aliasusuario,email,contraseña;
    private Uri ficheroSalidaUri; //ruta para almacenar la imagen
    private File Directorio;
    private Gesture gestousuario;

    //Nombre del archivo donde guardaremos los usuarios en la memoria interna
    private static final String Documentousuarios = "usuarios.txt";
    //Tamaño minimo del gesto
    private static final float LENGTH_THRESHOLD = 120.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_usuario);
        Galeria = (Button) findViewById(R.id.button);
        Camara = (Button)findViewById(R.id.button2);
        Aceptar = (Button)findViewById(R.id.button3);
        Cancelar = (Button)findViewById(R.id.button4);
        Fotousuario =(ImageView)findViewById(R.id.imagenusuario);
        aliasusuario = (EditText)findViewById(R.id.alias);
        email = (EditText)findViewById(R.id.email);
        contraseña = (EditText)findViewById(R.id.password);

        //Le referenciamos a la vista del gesto  el R.id.gestureoverlayview
        final GestureOverlayView overlay = (GestureOverlayView) findViewById(R.id.gestoUsuario);
        overlay.addOnGestureListener(new ProcesadorDeGestos());

        //Establecemos el titulo de la actividad
        setTitle(getResources().getText(R.string.tituloNuevoUsuario));

        //Para cuando el usuario decide seleccionar su imagen de login desde la galeria
        Galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seleccionarimagen = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                /*para que aparezcan imagenes de cualquier extension */
                Seleccionarimagen.setType("image/*");
                Directorio = new File(Environment.getExternalStorageDirectory(), "");
                ficheroSalidaUri = Uri.fromFile(Directorio); /*Convertimos el fichero en URI*/
                Seleccionarimagen.putExtra(MediaStore.EXTRA_OUTPUT, ficheroSalidaUri);
                startActivityForResult(Seleccionarimagen, Seleccionar_foto);
            }
        });

        //Para cuando el usuario decide seleccionar su imagen de login desde la camara
        Camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Seleccionarimagen =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Seleccionarimagen.putExtra(MediaStore.EXTRA_OUTPUT, ficheroSalidaUri);
                startActivityForResult(Seleccionarimagen,Capturar_foto);
            }
        });
        //Para cuando el usuario decida aceptar el formulario de creacion de usuario
        Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Creamos un objeto de la clase usuario donde guardaremos mas adelante
                // los datos introducidos por el usuario para crear un nuevo usuario
                Usuario nuevousuario = new Usuario();
                //Serializamos para extraer el documento JSON a partir de sus datos
                final Gson gson = new Gson();
                //Si alguno de los campos del formulario esta vacio apareceran mensajes
                // que impediran que el usuario cree un nuevo usuario, en esos mensajes le indicaremos
                // al usuario que dato falta por rellenar
                if(aliasusuario.getText().toString().isEmpty())
                {
                    Toast.makeText(NuevoUsuario.this, getString(R.string.faltaalias), Toast.LENGTH_SHORT).show();
                }
                else if(email.getText().toString().isEmpty())
                {
                    Toast.makeText(NuevoUsuario.this, getString(R.string.faltaemail), Toast.LENGTH_SHORT).show();
                }
                else if(contraseña.getText().toString().isEmpty())
                {
                    Toast.makeText(NuevoUsuario.this, getString(R.string.faltapassword), Toast.LENGTH_SHORT).show();
                }
                else if(contraseña.getText().toString().length()<8)
                {
                    Toast.makeText(NuevoUsuario.this, getString(R.string.minimopassword), Toast.LENGTH_SHORT).show();
                }
                else if(bmp==null)
                {
                    Toast.makeText(NuevoUsuario.this, getString(R.string.faltaimagenusuario), Toast.LENGTH_SHORT).show();
                }
                else if(gestousuario==null)
                {
                    Toast.makeText(NuevoUsuario.this, getString(R.string.faltagesto), Toast.LENGTH_SHORT).show();
                }
                //Cuando los campos del formulario esten rellenos con datos se ejecutara este bloque de codigo
                else
                {
                    //Dependiendo del valor introducido por el usuario en el edittext de aliasusuario
                    // guardaremos en el objeto nuevousario.setalias el valor introducido.
                    nuevousuario.setAlias(aliasusuario.getText().toString());
                    //Dependiendo del valor introducido por el usuario en el edittext de contraseña
                    // guardaremos en el objeto nuevousario.setContraseña el valor introducido.
                    nuevousuario.setContraseña(contraseña.getText().toString());
                    //Dependiendo del valor introducido por el usuario en el edittext de email
                    // guardaremos en el objeto nuevousario.setEmail el valor introducido.
                    nuevousuario.setEmail(email.getText().toString());
                    nuevousuario.setGestousuario(gestousuario);
                    //Pasamos el uri a string para añadirlo a nuevousuario
                    String foto = ficheroSalidaUri.toString();
                    nuevousuario.setFotousuario(foto);
                    //Serializamos nuevousuario una vez introducidos todos los valores
                    String objJSONSerialized=gson.toJson(nuevousuario);
                    //Creamos el fichero donde guardaremos los usuarios de forma persistente en la memoria interna
                    // si todos los campos del formulario estan rellenados correctamente.
                    try
                    {
                        //Mode_private para crear el archivo o sobreescribir uno existe, para añadir
                        // datos a uno creado seria con mode_append
                        OutputStreamWriter fout=new OutputStreamWriter(openFileOutput(Documentousuarios, Context.MODE_APPEND));
                        //Escribimos dentro del archivo creado el usuario introducido en el formulario serializado en JSON
                        fout.write(objJSONSerialized+"-SEPARADOR-");
                        fout.close();
                    }catch (IOException e)
                    {
                        Toast.makeText(getApplicationContext(),getString(R.string.errormeminterna), Toast.LENGTH_SHORT).show();
                    }
                    //Se imprimira un mensaje por pantalla indicando que se han guardado los datos
                    Toast.makeText(NuevoUsuario.this, getString(R.string.datosguardados), Toast.LENGTH_SHORT).show();
                    //Se termina esta actividad y volvemos a la mainactivity una vez creado el usuario
                    finish();
                }
            }
        });

        //Para cuando el usuario decida cancelar el formulario de creacion de usuario (Se vaciara)
        Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                aliasusuario.setText(null);
                contraseña.setText(null);
                email.setText(null);
                Fotousuario.setImageBitmap(null);
            }
        });
}
    //Sobreescribimos el metodo onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //Caso para cuando queremos obtener una foto desde la camara
            case Capturar_foto:
            if (resultCode == Activity.RESULT_OK)
            {
                ficheroSalidaUri = data.getData();
                Bundle extra = data.getExtras();
                //guardamos la foto en el Bitmap bmp
                bmp = (Bitmap) extra.get("data");
                //Establecemos en el imageView Fotousuario la foto guardado en el Bitmap
                Fotousuario.setImageBitmap(bmp);
            }
                //caso para cuando queremos seleccionar una foto de la galeria
            case Seleccionar_foto:
             if(resultCode == Activity.RESULT_OK)
             {
                 /*Guardamos la imagen de galeria en el uri ficheroSalidaUri*/
                 ficheroSalidaUri = data.getData();
                 InputStream is;
                 try {
                     is = getContentResolver().openInputStream(ficheroSalidaUri);
                     BufferedInputStream bis = new BufferedInputStream(is);
                     bmp = BitmapFactory.decodeStream(bis);
                     Fotousuario.setImageBitmap(bmp);
                 } catch (FileNotFoundException e) {}
             }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (gestousuario != null) {
            outState.putParcelable("gesture", gestousuario);
        }
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        gestousuario = savedInstanceState.getParcelable("gesture");
        if (gestousuario != null) {
            final GestureOverlayView overlay =
                    (GestureOverlayView) findViewById(R.id.gestoUsuario);
            overlay.post(new Runnable() {
                public void run() {
                    overlay.setGesture(gestousuario);
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
            gestousuario = null;
        }
        @Override
        public void onGesture(GestureOverlayView overlay, MotionEvent event) {

        }
        @Override
        public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
            gestousuario = overlay.getGesture();
            if (gestousuario.getLength() < LENGTH_THRESHOLD) {
                overlay.clear(false);
            }
        }
        @Override
        public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
        }
    }
}



