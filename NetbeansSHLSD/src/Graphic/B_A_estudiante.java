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
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Clase estudiante
 * @author johndelgado
 */
public class B_A_estudiante {
  private String nombre, apellido, direccion, email, estado, celular;
  private String carnet;

  public B_A_estudiante() {
  }

  //construtor de estudiante
  public B_A_estudiante(String nombre, String apellido, String carnet, String direccion, String email, String estado, String celular) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.direccion = direccion;
    this.email = email;
    this.estado = estado;
    this.celular = celular;
    this.carnet = carnet;
  }
  
  public B_A_estudiante(String nombre, String apellido, String estado, String carnet) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.estado = estado;
    this.carnet = carnet;
  }

  //Getters & setters
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
    //metodo para convertir un estudiante en una cadena de caracteres
    public String toString() {
      return carnet + " " + nombre + " " + apellido + ", " + estado;
    }

  //metodo que permite registrar estudiante en la base de datos
  public Boolean registrarEstudiante(){
    Boolean meta = false;
    try (Connection conn = Conexion.obtenerConn()) {
      Statement st;
      st = conn.createStatement();
          st.execute("INSERT INTO ESTUDIANTE values ('" + this.getNombre() + "', '"
          + this.getApellido() + "', '" + this.getCarnet() + "', '" 
          + this.getDireccion() + "', '" + this.getEmail() + "', '" 
          + this.getEstado() + "', '" + this.getCelular() + "');");
 
      meta = B_A_estudiante.consultarEstudiante(this.getCarnet()).carnet.equalsIgnoreCase(this.getCarnet());
      conn.close();
      st.close();
    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    }
    return meta;
  }

  //metodo que permite consultar un estudiante en la base de datos
  public static B_A_estudiante consultarEstudiante(String carnet){
    B_A_estudiante estudiante = null;
    try (Connection conn = Conexion.obtenerConn()) {
      Statement st;
      st = conn.createStatement();
      
      ResultSet rs = st.executeQuery("SELECT * FROM ESTUDIANTE WHERE CARNET = '" + carnet + "';");
      rs.next();
      
      estudiante = new B_A_estudiante(rs.getString(1),rs.getString(2),(rs.getString(3)),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
      conn.close();
      rs.close();
    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    }
    return estudiante;

  }

  //metodo que permite modificar un estudiante en la base de datos
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
      st.close();
      conn.close();
    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    }
  }

  //metodo para validar un carnet
  public static Boolean validaCarnet(String carnet) {
    String patron = "[0-9][0-9]-[0-9][0-9][0-9][0-9][0-9]";
    Pattern comp = Pattern.compile(patron);
    return carnet.matches(patron);
  }
  
  //metodo que retorna la cantidad de estudiante con el estado que se le pasa
  @SuppressWarnings("ConvertToTryWithResources")
  public static int[] contarEstudiantes(){
      int[] cantidades = new int[3];
      try (Connection conn = Conexion.obtenerConn()) {
          Statement st;
          st = conn.createStatement();
          ResultSet rs = st.executeQuery("SELECT COUNT(carnet) FROM ESTUDIANTE WHERE ESTADO = 'ACTIVO';");
          rs.next();
          cantidades[0] = rs.getInt(1);
          rs = st.executeQuery("SELECT COUNT(carnet) FROM ESTUDIANTE WHERE ESTADO = 'INACTIVO';");
          rs.next();
          cantidades[1] = rs.getInt(1);
          rs = st.executeQuery("SELECT COUNT(carnet) FROM ESTUDIANTE;");
          rs.next();
          cantidades[2] =  rs.getInt(1);
          conn.close();
    } catch (SQLException ex) {
          System.err.println(ex.getMessage());
    }
      
      return cantidades;
  }
  
  //interfaz para la consulta de labores
  public static String[] consultarEstudiantesStrings(){
    Object[] estudiantes = (B_A_estudiante.consultarEstudiantes().toArray());
    String[] cadenas = new String[estudiantes.length];
    for(int iterador = 0; iterador < estudiantes.length; iterador++)
      cadenas[iterador] = ((String)estudiantes[iterador].toString());
    return cadenas;
  }
  
    //metodo que permite consultar todas las labores asociadas a un estudiante
  public static ArrayList<B_A_estudiante> consultarEstudiantes(){
    ArrayList<B_A_estudiante> estudiantes = new ArrayList<>();
    try (Connection conn = Conexion.obtenerConn()) {
      Statement st;
      st = conn.createStatement();

      ResultSet rs = st.executeQuery("SELECT * FROM ESTUDIANTE ORDER BY CARNET;");
      
        while (rs.next()) {
            String nombre = rs.getString(1);
            String apellido = rs.getString(2);
            String carnet = rs.getString(3);
            String direccion = rs.getString(4);
            String email = rs.getString(5);
            String estado = rs.getString(6);
            String celular = rs.getString(7);
            B_A_estudiante estudiante = new B_A_estudiante(nombre, apellido,carnet,direccion, email, estado, celular);
            estudiantes.add(estudiante);
        }

        rs.close();          
      conn.close();

    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    }
    return estudiantes;

  }
  
  //metodo que permite obtener los carnets de la base de datos
  public static ArrayList<String> listarEstudiantes(){
    ArrayList<String> carnets = new ArrayList();
    try (Connection conn = Conexion.obtenerConn()) {
      Statement st;
      st = conn.createStatement();
        try (ResultSet rs = st.executeQuery("SELECT CARNET FROM ESTUDIANTE;")) {
            while(rs.next()) {
                carnets.add(rs.getString(1));
            }
            
            conn.close();
        }
    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    }
    return carnets;
  }
  
}
