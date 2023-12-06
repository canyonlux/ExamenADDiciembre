package org.example;

public class Cliente {
    private int clientid;
    private String dni;
    private String nombre;
    private String apellidos;
    private String nacionalidad;
    private String telefono;
    private String email;
    private String contrasenya;

    // Constructor por defecto
    public Cliente() {
    }

    // Constructor con todos los atributos
    public Cliente(int clientid, String dni, String nombre, String apellidos, String nacionalidad, String telefono, String email, String contrasenya) {
        this.clientid = clientid;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nacionalidad = nacionalidad;
        this.telefono = telefono;
        this.email = email;
        this.contrasenya = contrasenya;
    }

    // Getters y Setters
    public int getClientid() {
        return clientid;
    }

    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "clientid=" + clientid +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", contrasenya='" + contrasenya + '\'' +
                '}';
    }
}
