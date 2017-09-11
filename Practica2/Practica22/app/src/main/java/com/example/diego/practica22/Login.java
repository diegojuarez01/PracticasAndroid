package com.example.diego.practica22;

/**
 * Created by Diego on 13/05/2016.
 */
public class Login
{
        private String AplicacionLogin;
        private String TipoDeAplicacion;
        private String FechaAlta;
        private String UrlApp;
        private String URLimagen;
        private String usuarioLogin;
        private String password;
        private String alias;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }


    public String getAplicacionLogin() {
        return AplicacionLogin;
    }

    public void setAplicacionLogin(String aplicacionLogin) {
        AplicacionLogin = aplicacionLogin;
    }

    public String getTipoDeAplicacion() {
        return TipoDeAplicacion;
    }

    public void setTipoDeAplicacion(String tipoDeAplicacion) {
        TipoDeAplicacion = tipoDeAplicacion;
    }

    public String getFechaAlta() {
        return FechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        FechaAlta = fechaAlta;
    }

    public String getUrlApp() {
        return UrlApp;
    }

    public void setUrlApp(String urlApp) {
        UrlApp = urlApp;
    }

    public String getURLimagen() {
        return URLimagen;
    }

    public void setURLimagen(String URLimagen) {
        this.URLimagen = URLimagen;
    }

    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
