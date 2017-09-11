package com.example.diego.practica22;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListaLoginActivity extends AppCompatActivity {

    private TextView aliaslogin,emaillogin;
    private ImageView imagenlogin;
    private AdaptadorListView adaptador;
    private ListView listalogins;
    private ArrayList<String> aplicacioneslogin =new ArrayList<String>();
    private ArrayList<String> usuarioslogin =new ArrayList<String>();
    private ArrayList<String> passwordlogin =new ArrayList<String>();
    private ArrayList<Integer> imagenpordefecto = new ArrayList<>();
    private final ArrayList<Integer> identificador = new ArrayList<>();
    private String aliasusuario,emailusuario,imagenusuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_login);
        aliaslogin = (TextView) findViewById(R.id.aliaslogin);
        emaillogin = (TextView) findViewById(R.id.emaillogin);
        imagenlogin = (ImageView) findViewById(R.id.imglogin);
        listalogins = (ListView) findViewById(R.id.listaLogins);
        //Ponemos el titulo de la actividad
        setTitle(getResources().getText(R.string.tituloactividadlistalogin));
        //Guardamos en esta variable el alias obtenida de la actividad anterior
        aliasusuario = getIntent().getExtras().getString("aliasusu");
        emailusuario = getIntent().getExtras().getString("emailusu");
        imagenusuario= getIntent().getExtras().getString("imagenusu");

        //Pondremos el alias,email e imagen del usuario cargado.
        aliaslogin.setText(aliasusuario);
        emaillogin.setText(emailusuario);
        Uri miuri = Uri.parse(imagenusuario);
        imagenlogin.setImageURI(miuri);

        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        miSQLiteHelper usdbh = new miSQLiteHelper(this,"BDLogins.db", null, 1);
        final SQLiteDatabase db = usdbh.getWritableDatabase();
        //Hacemos el Select para el alias que hemos obtenido al aceptar el login.
        cargardatos();

        //Para cuando hagamos una pulsuacion leve ejecutara el siguiente codigo
        listalogins.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Si el usuario hace una pulsacion corta volvera al menu de creacion de login
                 con los datos guardados para ese login dandole la posibilidad de hacer una modificacion en la tabla de la BD*/
                //Creamos un intent que hara que pasemos de la actividad Lista login a la actividad "NuevoLogin"
                Intent modificarlogin = new Intent(ListaLoginActivity.this, NuevoLogin.class);
                //Con el intent creado anteriormente le mandamos
                // la informacion a la siguiente actividad
                int ident =identificador.get(position);
                modificarlogin.putExtra("identificadorusuario", ident);
                String alias=aliaslogin.getText().toString();
                modificarlogin.putExtra("aliasusuario", alias);
                String ModificarLogin ="Modificar";
                modificarlogin.putExtra("Comprobar",ModificarLogin);
                modificarlogin.putExtra("aliasusu",aliasusuario );
                modificarlogin.putExtra("emailusu",emailusuario);
                modificarlogin.putExtra("imagenusu",imagenusuario);
                //Iniciamos la actividad con el intent creado en este caso "añadirlogin"
                startActivity(modificarlogin);
                finish();
            }
        });
        //Para cuando hagamos una pulsacion larga se ejecutara el siguiente codigo
        listalogins.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //Si el usuario hace una pulsacion larga le aparecera un mensaje de confirmacion
                // de borrado del elemento de la BD.Por lo que tendremos que hacer un alert dialog.
                final AlertDialog.Builder alertaBorrartabla = new AlertDialog.Builder(ListaLoginActivity.this);
                alertaBorrartabla.setTitle(getString(R.string.BorrarLogin));
                alertaBorrartabla.setMessage(getString(R.string.MensajeAlerta));
                alertaBorrartabla.setPositiveButton(getString(R.string.Aceptarborrado), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       //Si el usuario aceptar se ejecutara el siguiente codigo en el que borraremos el login indicado.
                       db.execSQL("DELETE FROM Logins WHERE _id=" + identificador.get(position));
                        //Volvemos a crear la actividad
                        Toast.makeText(ListaLoginActivity.this,getString(R.string.loginborrado),Toast.LENGTH_SHORT).show();
                        reiniciarActivity(ListaLoginActivity.this);
                    }
                });
                alertaBorrartabla.setNegativeButton(getString(R.string.Cancelarborrado), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //No haremos nada y se volvera automaticamente a la actividad
                    }
                });
                //Utilizamos el metodo setCancelable para que al pulsar en la pantalla no
                // nos saque del alert, es decir debemos de responder al alert forzadamente.
                alertaBorrartabla.setCancelable(false);
                //Creamos la alerta y la mostramos
                alertaBorrartabla.create();
                alertaBorrartabla.show();
                return false;
            }
        });
    }
    //Creamos el menu por defecto
    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.menu2, m);
        return true; ///Siempre debe devolver true
    }
    public void cargardatos(){
        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        miSQLiteHelper usdbh = new miSQLiteHelper(this,"BDLogins.db", null, 1);
        final SQLiteDatabase db = usdbh.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
            //Dentro de la lista deberan de aparecer los logins para cargarlos deberemos de pasar como valor
            // de referencia el alias guardado en las tablas que sera el alias del usuario.
            //Por lo que cuando un usuario entre se cargaran todas las columnas de la tabla logins en las que coincidan los alias.
            Cursor c = db.rawQuery("select nameOfApp,user,password,_id from Logins where alias='"+ aliaslogin.getText()+"'", null);
            //Si existe al menos un registro en el cursor se ejecutara el siguiente codigo
            if (c.moveToFirst())
            {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    //Añadimos los string a los arrays
                    aplicacioneslogin.add(c.getString(0));
                    usuarioslogin.add(c.getString(1));
                    passwordlogin.add(c.getString(2));
                    identificador.add(c.getInt(3));
                    imagenpordefecto.add(R.drawable.imagenpordefecto);
                } while (c.moveToNext());
            }
            else {
                Toast.makeText(this,getString(R.string.Noregistrados),Toast.LENGTH_SHORT).show();
            }
            db.close();
            c.close();
        }
        //Creamos un objeto de la clase LisView a la que le pasaremos los arrays
        // en donde guardaremos la informacion obtenida de la tabla login
        adaptador = new AdaptadorListView(this,aplicacioneslogin,usuarioslogin,passwordlogin,imagenpordefecto);
        listalogins.setAdapter(adaptador);
    }
    // Para cuando seleccionemos una opcion del menu, en este caso solo vamos a tener
    // 1 pero serviria si tubieramos mas de una opcion.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.añadirlogin:
                //Creamos un intent que hara que pasemos de la actividad Lista login a la actividad "NuevoLogin"
                Intent añadirlogin = new Intent(ListaLoginActivity.this, NuevoLogin.class);
                //Con el intent creado anteriormente le mandamos
                // la informacion a la siguiente actividad
                añadirlogin.putExtra("aliasusu",aliasusuario );
                añadirlogin.putExtra("emailusu",emailusuario);
                añadirlogin.putExtra("imagenusu",imagenusuario);
                //Iniciamos la actividad con el intent creado en este caso "añadirlogin"
                startActivity(añadirlogin);
                finish();
                }
        return true;
    }
    //reinicia una Activity
    public void reiniciarActivity(Activity actividad){
        Intent volverlistalogin =new Intent();
        volverlistalogin.setClass(actividad, actividad.getClass());
        //llamamos a la actividad
        volverlistalogin.putExtra("aliasusu", aliasusuario);
        volverlistalogin.putExtra("emailusu", emailusuario);
        volverlistalogin.putExtra("imagenusu",imagenusuario);
        actividad.startActivity(volverlistalogin);
        //finalizamos la actividad actual
        actividad.finish();
    }
    //Para cuando el usuario quiera volver al menuprincipal se volvera a crear la mainactivity y finalizara ListaLogin
    public void onBackPressed() {
        //Creamos un intent que hara que pasemos de la actividad Lista login a la actividad "NuevoLogin"
        Intent volverpantalladeinicio = new Intent(ListaLoginActivity.this, MainActivity.class);
        //Iniciamos la actividad con el intent creado en este caso "volverpantalladeinicio"
        startActivity(volverpantalladeinicio);
        finish();
    }

}
