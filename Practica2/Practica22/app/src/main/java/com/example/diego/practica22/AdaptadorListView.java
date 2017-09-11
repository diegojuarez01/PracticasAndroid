package com.example.diego.practica22;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class AdaptadorListView extends BaseAdapter {

    //Variables
    Context context;
    ArrayList<String> Aplicaciones;
    ArrayList<String> usuarioslogin;
    ArrayList<String> passwordslogin;
    ArrayList<Integer> imagepordefecto;

    LayoutInflater inflater;

    public AdaptadorListView(Context context, ArrayList<String> Aplicaciones, ArrayList<String> usuarioslogin,ArrayList<String> passwordslogin,ArrayList<Integer> imagepordefecto) {
        this.context = context;
        this.Aplicaciones = Aplicaciones;
        this.usuarioslogin = usuarioslogin;
        this.passwordslogin = passwordslogin;
        this.imagepordefecto = imagepordefecto;
    }

    @Override
    public int getCount() {
        //Sustituir por Aplicaciones.size(); cuando me haga el select correctamente
        return Aplicaciones.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        // Declaramos variables de los items de la lista
        TextView Aplicacion,usuarioslog,passwordslog;
        ImageView imagenpordefecto;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Lo enlazamos con nuesta vista listalogs
        View itemView = inflater.inflate(R.layout.listalogs, parent, false);
        //Referenciamos las variables
        Aplicacion = (TextView) itemView.findViewById(R.id.aplicacionlog);
        usuarioslog = (TextView) itemView.findViewById(R.id.usuariologin);
        passwordslog = (TextView) itemView.findViewById(R.id.PasswordLogin);
        imagenpordefecto = (ImageView) itemView.findViewById(R.id.imagenlogin);

        // Capturamos la posicion en el array y la guardamos para cada momento
        Aplicacion.setText(Aplicaciones.get(position));
        usuarioslog.setText(usuarioslogin.get(position));
        passwordslog.setText(passwordslogin.get(position));
        imagenpordefecto.setImageResource(imagepordefecto.get(position));

        return itemView;
    }
}