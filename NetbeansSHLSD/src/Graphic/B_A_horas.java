/*
 * To change this license <heade></heade>r, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Clase horas
 * @author johndelgado
 */
public class B_A_horas {
  private String carnet;
  private Hora hora_fin, hora_inicio;
  private String fecha;
  private String observaciones;
  private Double tiempo_total;

  //constructor de horas
  public B_A_horas(String carnet, Hora hora_fin, Hora hora_inicio, String fecha, String observaciones, Double tiempo_total) {
    this.carnet = carnet;
    this.hora_fin = hora_fin;
    this.hora_inicio = hora_inicio;
    this.fecha = fecha;
    this.observaciones = observaciones;
    this.tiempo_total = tiempo_total;
  }

  //getters & setters
  public String getFecha() {
    return fecha;
  }

  public void setFecha(String fecha) {
    this.fecha = fecha;
  }


  public Double getTiempo_total() {
    return tiempo_total;
  }

  public void setTiempo_total(Double tiempo_total) {
    this.tiempo_total = tiempo_total;
  }

  public String getCarnet() {
    return carnet;
  }

  public void setCarnet(String carnet) {
    this.carnet = carnet;
  }

  public Hora getHora_inicio() {
    return hora_inicio;
  }

  public void setHora_inicio(Hora hora_inicio) {
    this.hora_inicio = hora_inicio;
  }

  public Hora getHora_fin() {
    return hora_fin;
  }

  public void setHora_fin(Hora hora_fin) {
    this.hora_fin = hora_fin;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }

  @Override
    //metodo para convertir una hora en un string
    public String toString() {
      return "Carnet: " + carnet + ", hora inicio: " + hora_inicio.toString() + ", hora fin: " + hora_fin.toString() + ", tiempo_total: " + tiempo_total + ", fecha: " + fecha + ", observaciones: " + observaciones ;
    }

  //metodo que permite registrar una labor en la base de datos
  public Boolean registrarLabor(){
    Boolean meta = false;
    try (Connection conn = Conexion.obtenerConn()) {
      Statement st;
      st = conn.createStatement();
      meta = st.execute("INSERT INTO LABOR values ('" + this.getHora_inicio().getCadena() + "', '"
          + this.getHora_fin().getCadena() + "', '"  
          + this.getObservaciones() + "', '" 
          + this.getFecha() + "', '" + this.getCarnet()+ "', '" 
          + this.getTiempo_total()+"');");
      st.close();
      conn.close();
    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    }
    return meta;
  }

  //metodo que permite consultar todas las labores asociadas a un estudiante
  public static ArrayList<B_A_horas> consultarLabores(String carnet){
    ArrayList<B_A_horas> labores = new ArrayList<>();
    try (Connection conn = Conexion.obtenerConn()) {
      Statement st;
      st = conn.createStatement();

      ResultSet rs = st.executeQuery("SELECT * FROM LABOR WHERE CARNET_LABOR = '" + String.valueOf(carnet) + "';");
      
        while (rs.next()) {
          Hora hInicio = new Hora(rs.getString(1));
          Hora hFin = new Hora(rs.getString(2));
          B_A_horas labor = new B_A_horas(carnet, hFin,hInicio,rs.getString(4),rs.getString(3),rs.getDouble(6));
          labores.add(labor);
        }

        rs.close();          
      conn.close();

    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    }
    return labores;

  }
  
   public static B_A_horas consultarLabor(String carnet, String fecha, Hora hora_inicio){
      B_A_horas labor = null;
      try (Connection conn = Conexion.obtenerConn()) {
         Statement st;
         st = conn.createStatement();
         String[] auxiliarFecha = fecha.trim().split("-");
         fecha =auxiliarFecha[2]+"-"+auxiliarFecha[1]+"-"+auxiliarFecha[0];
         ResultSet rs = st.executeQuery("SELECT * FROM LABOR WHERE CARNET_LABOR = '" + carnet + "' AND FECHA = '"+ fecha + "' AND HORA_INICIO = '"+ hora_inicio.getCadena() + "';");  
         rs.next();
         Hora hInicio = new Hora(rs.getString(1));
         Hora hFin = new Hora(rs.getString(2));
         labor = new B_A_horas(carnet, hFin,hInicio,rs.getString(4),rs.getString(3),rs.getDouble(6));
       

        rs.close();          
        conn.close();

    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    }
    return labor;

  }

  //interfaz para la consulta de labores
  public static String[] consultarLaboresStrings(String carnet){
    Object[] labores = (B_A_horas.consultarLabores(carnet).toArray());
    String[] cadenas = new String[labores.length];
    for(int iterador = 0; iterador < labores.length; iterador++)
      cadenas[iterador] = ((String)labores[iterador].toString());
    return cadenas;
  }

  //intefaz para la consulta de labores
  public static String consultarTotalHoras(String carnet) throws ParseException{
    ArrayList<B_A_horas> labores = (B_A_horas.consultarLabores(carnet));
    long total = 0;
    java.text.DateFormat df = new java.text.SimpleDateFormat("MM-DD-YYYY HH:mm");
    for(int iterador = 0; iterador < labores.size(); iterador++){
      java.util.Date inicio = df.parse(labores.get(iterador).getFecha() +" "+labores.get(iterador).getHora_inicio());
      java.util.Date fin = df.parse(labores.get(iterador).getFecha() +" "+labores.get(iterador).getHora_fin());
      total = total + Math.abs(fin.getTime() - inicio.getTime());
    }   
    String retorno = String.valueOf((total/60000)/60) + ":" + String.valueOf((total/60000) % 60);
    return retorno;
  }
  
    //metodo que permite modificar una labor en la base de datos
  public void modificarLabor(String new_carnet, String new_fecha, String new_observaciones) {
    try (Connection conn = Conexion.obtenerConn()) {
        Statement st;
        st = conn.createStatement();
        st.executeUpdate(
        "UPDATE LABOR SET "
            + "OBSERVACION ='"+new_observaciones+"',"
            + "FECHA ='"+new_fecha+"',"
            + "CARNET_LABOR ='"+new_carnet+"'"
        + " WHERE"
            + " HORA_INICIO ='"+this.hora_inicio +"' AND"
            + " FECHA ='"+this.fecha+"' AND"
            + " CARNET_LABOR ='"+this.carnet + "';");
        
        st.close();
        conn.close();
    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    }
  }
  
  //metodo que permite eliminar una labor en la base de datos
  public void eliminarLabor() {
    try (Connection conn = Conexion.obtenerConn()) {
      Statement st;
      st = conn.createStatement();
              
      st.executeUpdate("DELETE FROM LABOR WHERE HORA_INICIO ='"
          +this.hora_inicio +"'AND FECHA ='"+this.fecha+
          "'AND CARNET_LABOR ='"+this.carnet + "';");
      
      st.close();
      conn.close();
    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    }
  }

  public static String[] listarLaboresDeLista(ArrayList<String> carnets) throws ParseException{
      String[] pares = new String[carnets.size()];
      String carnet;
      for (int iterador = 0; iterador < carnets.size(); iterador++) {
          carnet = carnets.get(iterador);
          pares[iterador] = (String) (carnet + "Acumulado: " + B_A_horas.consultarTotalHoras(carnet));
      }
      
      return pares;
  }
  
    public static int consultarCantidadLabores(String carnet){
      int cantidad = 0;
      try (Connection conn = Conexion.obtenerConn()) {
         Statement st;
         st = conn.createStatement();
         ResultSet rs = st.executeQuery("SELECT count(*) FROM LABOR WHERE CARNET_LABOR = '" + carnet +"';");  
         rs.next();
         cantidad = rs.getInt(1);
         rs.close();          
         conn.close();

    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    }
    return cantidad;

  }
}
