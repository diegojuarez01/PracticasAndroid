package com.example.diego.practica22;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NuevoLogin extends AppCompatActivity {

        private Spinner listadesplegable;
        private String [] TiposDeAplicaciones;
        private EditText aplicacion,fechaalta,Urlapp,imagenURL,usuario,password;
        private Calendar calendar;
        private Button Aceptar,Fecha;
        private int idusuario;
        //Utilizaremos este string para guardar la opcion seleccionada por el usuario en el spinner
        //y aliasusuario para obtener el alias de la actividad anterior
        private String RespuestaSpinner,aliasusuario,comprobacionparamodificar,emailusuario,imagenusuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_login);
        listadesplegable = (Spinner) findViewById(R.id.spinnerTipoaplicacion);
        aplicacion = (EditText) findViewById(R.id.eTextaplicacionnuevologin);
        fechaalta = (EditText) findViewById(R.id.eTextFecha);
        Urlapp = (EditText) findViewById(R.id.eTexturlapp);
        imagenURL = (EditText) findViewById(R.id.eTextimgURL);
        usuario = (EditText) findViewById(R.id.eTextUsuario);
        password = (EditText) findViewById(R.id.eTextPasword);
        Fecha = (Button) findViewById(R.id.fecha);
        Aceptar = (Button) findViewById(R.id.aceptarnuevologin);

        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        miSQLiteHelper usdbh = new miSQLiteHelper(this, "BDLogins.db", null, 1);
        final SQLiteDatabase db = usdbh.getWritableDatabase();
        //Guardamos en esta variable el alias obtenida de la actividad anterior
        aliasusuario = getIntent().getExtras().getString("aliasusu");
        emailusuario = getIntent().getExtras().getString("emailusu");
        imagenusuario= getIntent().getExtras().getString("imagenusu");
        idusuario = getIntent().getExtras().getInt("identificadorusuario");
        comprobacionparamodificar = getIntent().getExtras().getString("Comprobar");
        //Ponemos el titulo de la actividad
        setTitle(getResources().getText(R.string.añadirlog));
        //Asignamos el valor dentro del array a el array TiposDeAplicaciones
        TiposDeAplicaciones = getResources().getStringArray(R.array.TiposDeAplicaciones);
        // Creamos un array de cadenas
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,TiposDeAplicaciones);
        //Establecemos en el spinner el adaptador
        listadesplegable.setAdapter(adaptador);
        listadesplegable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            //Para el item que esta siendo seleccionado
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Dependiendo de la posicion en la que se encuentre el selector actura de una forma u otra
                switch (position) {
                    case 0:
                        RespuestaSpinner = getString(R.string.Aplicacionweb);
                        break;
                    case 1:
                        RespuestaSpinner = getString(R.string.Aplicacionmovil);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(comprobacionparamodificar==null){
        }
        else {
            //Si el usuario viene de hacer una pulsacion para modificar uno de los logins se ejecutara este codigo
            if (comprobacionparamodificar.contentEquals("Modificar")) {
                if (db != null) {
                    //Por lo que cuando un usuario entre se cargaran todas las columnas de la tabla logins en las que coincidan los alias.
                    Cursor c = db.rawQuery("select nameOfApp,user,password,loginType,urlApp,imageURL,firstdate from Logins where _id=" + idusuario, null);
                    //Si existe al menos un registro en el cursor se ejecutara el siguiente codigo
                    if (c.moveToFirst()) {
                        //Recorremos el cursor hasta que no haya más registros
                        do {
                            Login log = new Login();
                            //Añadimos los string al objeto log
                            log.setAplicacionLogin(c.getString(0));
                            log.setUsuarioLogin(c.getString(1));
                            log.setPassword(c.getString(2));
                            log.setTipoDeAplicacion(c.getString(3));
                            log.setUrlApp(c.getString(4));
                            log.setURLimagen(c.getString(5));
                            log.setFechaAlta(c.getString(6));
                            //Ponemos los valores obtenidos en los campos del formulario
                            aplicacion.setText(log.getAplicacionLogin());
                            usuario.setText(log.getUsuarioLogin());
                            password.setText(log.getPassword());
                            //Si el tipo de aplicacion es igual a R.strin.AplicacionWEB
                            if (log.getTipoDeAplicacion().contentEquals(getString(R.string.Aplicacionweb))) {
                                //Pondremos de valor por defecto R.string.Aplicacionweb
                                listadesplegable.setSelection(0);
                            } else {
                                //Pondremos de valor por defecto R.string.Aplicacionmovil
                                listadesplegable.setSelection(1);
                            }
                            Urlapp.setText(log.getUrlApp());
                            imagenURL.setText(log.getURLimagen());
                            fechaalta.setText(log.getFechaAlta());
                        } while (c.moveToNext());
                    } else {
                        Toast.makeText(this, getString(R.string.ErrorBD), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        //Cuando pulsamos en el boton FECHA obtendra la fecha actual
        Fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) // Evento para cuando se haga click en FECHA
            {
                calendar = Calendar.getInstance();
                //Establecemos de formato de fecha  "Dia/mes/año" como nos indica en la practica
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                //Obtenemos la fecha actual con el formato que le hemos puesto
                String currentDate = sdf.format(calendar.getTime());
                //Establecemos la fecha actual en el edittext fechaalta
                fechaalta.setText(currentDate);
            }
        });
        //Cuando pulsamos en el boton Aceptar Creara un nuevo login
        Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) // Evento para cuando se haga click en Aceptar
            {
                //Habra que comprobar que no hay ningun campo vacio excepto imgURL/UrlApp y fechaalta que no es obligatorio.
                if(aplicacion.getText().toString().isEmpty())
                {
                    Toast.makeText(NuevoLogin.this, getString(R.string.faltaaplicacion), Toast.LENGTH_SHORT).show();
                }
                else if(password.getText().toString().isEmpty())
                {
                    Toast.makeText(NuevoLogin.this, getString(R.string.faltapassword), Toast.LENGTH_SHORT).show();
                }
                else if(usuario.getText().toString().isEmpty())
                {
                    Toast.makeText(NuevoLogin.this, getString(R.string.faltausuario), Toast.LENGTH_SHORT).show();
                }
                //Cuando los campos del formulario esten rellenos con datos se ejecutara este bloque de codigo
                else {
                    //Si comprobacionparamodificar==null  es que hemos entrado para añadir un nuevoLogin
                    if (comprobacionparamodificar==null)
                    {
                        //Si hemos abierto correctamente la base de datos cuando le demos aceptar crearemos un nuevo login
                        if (db != null) {
                    /*Por lo que vamos a hacer un insert en la BD cada vez que le demos a Aceptar
                     Por lo que hacemos añadimos a la tabla Logins, no hara falta poner un valor
                    para la columna de ID ya que de forma automatica se sumara 1 tras cada insercion*/
                            db.execSQL("INSERT INTO Logins (alias,loginType,nameOfApp,urlApp,imageURL,user,password,firstdate) " +
                                    "VALUES ('" + aliasusuario + "', '" + RespuestaSpinner + "','" + aplicacion.getText().toString() +
                                    "','" + Urlapp.getText().toString() + "','" + imagenURL.getText().toString() + "','" + usuario.getText().toString() +
                                    "','" + password.getText().toString() + "','" + fechaalta.getText().toString() + "')");
                            //Mostramos el mensaje para saber que ha salido bien y hemos creado un nuevo login
                            Toast.makeText(getApplicationContext(), getString(R.string.nuevologin), Toast.LENGTH_SHORT).show();
                        }
                        //Creamos un intent que hara que pasemos de la actividad Nuevologin a la actividad "ListaLoginActivity"
                        Intent volverlistalogin = new Intent(NuevoLogin.this, ListaLoginActivity.class);
                        volverlistalogin.putExtra("aliasusu",aliasusuario );
                        volverlistalogin.putExtra("emailusu",emailusuario);
                        volverlistalogin.putExtra("imagenusu",imagenusuario);
                        //Iniciamos la actividad con el intent creado en este caso "volverlistalogin"
                        startActivity(volverlistalogin);
                        //Una vez añadido el login la actividad terminara
                        finish();
                    }
                    else {
                        //Si venimos para modificar un Login
                         {
                            //Aqui hacemos la moficacion final sobre la tabla para el idlogin seleccionado
                             //Si hemos abierto correctamente la base de datos cuando le demos aceptar crearemos un nuevo login
                             if (db != null)
                             {
                                 db.execSQL("UPDATE Logins SET alias='" + aliasusuario + "',loginType='" + RespuestaSpinner + "',nameOfApp='" + aplicacion.getText().toString() +
                                         "',urlApp='" + Urlapp.getText().toString() + "',imageURL='" + imagenURL.getText().toString() + "',user='" + usuario.getText().toString() +
                                         "',password='" + password.getText().toString() + "',firstdate='" + fechaalta.getText().toString() + "'WHERE _id=" + idusuario);
                                 //Mostramos el mensaje para saber que ha salido bien y hemos modificado el login
                                 Toast.makeText(getApplicationContext(), getString(R.string.modificadologin), Toast.LENGTH_SHORT).show();
                                 //Creamos un intent que hara que pasemos de la actividad Nuevologin a la actividad "ListaLoginActivity"
                                 Intent volverlistalogin = new Intent(NuevoLogin.this, ListaLoginActivity.class);
                                 volverlistalogin.putExtra("aliasusu", aliasusuario);
                                 volverlistalogin.putExtra("emailusu", emailusuario);
                                 volverlistalogin.putExtra("imagenusu", imagenusuario);
                                 //Iniciamos la actividad con el intent creado en este caso "volverlistalogin"
                                 startActivity(volverlistalogin);
                                 //Una vez añadido el login la actividad terminara
                                 finish();
                             }
                        }
                    }
                }
            }
        });
        }
    //Para cuando el usuario quiera volver atras se volvera a crear la actividad anterior ListaLogin guardando el alias el email y la foto
    public void onBackPressed() {
        //Creamos un intent que hara que pasemos de la actividad Nuevologin a la actividad "ListaLoginActivity"
        Intent volverlistalogin = new Intent(NuevoLogin.this, ListaLoginActivity.class);
        volverlistalogin.putExtra("aliasusu",aliasusuario );
        volverlistalogin.putExtra("emailusu",emailusuario);
        volverlistalogin.putExtra("imagenusu",imagenusuario);
        startActivity(volverlistalogin);
        finish();
    }
    public void onStart(){
        super.onStart();
        fechaalta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {
                    Datepicker dialog = new Datepicker(view);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");
                }
            }

        });
    }
}
