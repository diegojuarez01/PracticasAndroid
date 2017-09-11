package com.example.diego.practica22;
import android.gesture.Gesture;

public class Usuario
{
    private String alias;
    private String contraseña;
    private String email;
    private String fotousuario;
    private Gesture gestousuario;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;

    }

    public String getFotousuario() {
        return fotousuario;
    }

    public void setFotousuario(String fotousuario) {
        this.fotousuario = fotousuario;
    }

    public Gesture getGestousuario() {
        return gestousuario;
    }

    public void setGestousuario(Gesture gestousuario) {
        this.gestousuario = gestousuario;
    }

}
