/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author johndelgado
 */
public class B_A_estudiante {
    private String nombre, apellido, direccion, email, estado, celular;
    private String carnet;

    public B_A_estudiante() {
    }

    public B_A_estudiante(String nombre, String apellido, String carnet, String direccion, String email, String estado, String celular) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.email = email;
        this.estado = estado;
        this.celular = celular;
        this.carnet = carnet;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    @Override
    public String toString() {
        return "B_A_estudiante{" + "nombre=" + nombre + ", apellido=" + apellido + ", direccion=" + direccion + ", email=" + email + ", estado=" + estado + ", celular=" + celular + ", carnet=" + carnet + "}'";
    }
    
    public void registrarEstudiante(){
        try (Connection conn = Conexion.obtenerConn()) {
            Statement st;
            st = conn.createStatement();
            
            st.execute("INSERT INTO ESTUDIANTE values ('" + this.getNombre() + "', '"
                    + this.getApellido() + "', '" + this.getCarnet() + "', '" 
                    + this.getDireccion() + "', '" + this.getEmail() + "', '" 
                    + this.getEstado() + "', '" + this.getCelular() + "');");

            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public static B_A_estudiante consultarEstudiante(String carnet){
        B_A_estudiante estudiante = null;
        try (Connection conn = Conexion.obtenerConn()) {
            Statement st;
            st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM ESTUDIANTE WHERE CARNET = '" + carnet + "';");
            rs.next();
            estudiante = new B_A_estudiante(rs.getString(1),rs.getString(2),(rs.getString(3)),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
         return estudiante;

    }
    
    public void modificarEstudiante() {
         try (Connection conn = Conexion.obtenerConn()) {
             Statement st;
             st = conn.createStatement();
             st.executeUpdate("UPDATE ESTUDIANTE SET nombre ='"
                    +this.nombre +"', apellido ='"+this.apellido+
                     "', email ='"+this.email+
                     "', celular ='"+this.celular+
                     "', direccion ='"+this.direccion+
                     "', estado ='"+this.estado+"'"
                     + " WHERE carnet ='"+this.carnet+"';");
          } catch (SQLException ex) {
              System.err.println(ex.getMessage());
          }
        
    }

         
}