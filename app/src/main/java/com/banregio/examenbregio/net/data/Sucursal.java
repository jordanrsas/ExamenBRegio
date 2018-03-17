package com.banregio.examenbregio.net.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jordan on 16/03/2018.
 */

public class Sucursal {

    @SerializedName("ID")
    String id;

    String tipo;

    @SerializedName("NOMBRE")
    String nombre;

    @SerializedName("DOMICILIO")
    String domicilio;

    @SerializedName("HORARIO")
    String horario;

    @SerializedName("TELEFONO_PORTAL")
    String telefonoPortal;

    @SerializedName("TELEFONO_APP")
    String telefonoApp;

    @SerializedName("24_HORAS")
    String fullTime;

    @SerializedName("SABADOS")
    String sabados;

    @SerializedName("CIUDAD")
    String ciudad;

    @SerializedName("ESTADO")
    String estado;

    @SerializedName("Latitud")
    String latitude;

    @SerializedName("Longitud")
    String longitude;

    @SerializedName("Correo_Gerente")
    String emailGerente;

    @SerializedName("URL_FOTO")
    String urlPhoto;

    @SerializedName("Suc_Estado_Prioridad")
    String sucursalEstadoPrioridad;

    @SerializedName("Suc_Ciudad_Prioridad")
    String sucursalCiudadPrioridad;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getTelefonoPortal() {
        return telefonoPortal;
    }

    public void setTelefonoPortal(String telefonoPortal) {
        this.telefonoPortal = telefonoPortal;
    }

    public String getTelefonoApp() {
        return telefonoApp;
    }

    public void setTelefonoApp(String telefonoApp) {
        this.telefonoApp = telefonoApp;
    }

    public String getFullTime() {
        return fullTime;
    }

    public void setFullTime(String fullTime) {
        this.fullTime = fullTime;
    }

    public String getSabados() {
        return sabados;
    }

    public void setSabados(String sabados) {
        this.sabados = sabados;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getEmailGerente() {
        return emailGerente;
    }

    public void setEmailGerente(String emailGerente) {
        this.emailGerente = emailGerente;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getSucursalEstadoPrioridad() {
        return sucursalEstadoPrioridad;
    }

    public void setSucursalEstadoPrioridad(String sucursalEstadoPrioridad) {
        this.sucursalEstadoPrioridad = sucursalEstadoPrioridad;
    }

    public String getSucursalCiudadPrioridad() {
        return sucursalCiudadPrioridad;
    }

    public void setSucursalCiudadPrioridad(String sucursalCiudadPrioridad) {
        this.sucursalCiudadPrioridad = sucursalCiudadPrioridad;
    }
}
