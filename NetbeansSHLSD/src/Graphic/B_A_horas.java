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
import java.text.DateFormat;
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
      return "Carnet: " + carnet + ", hora fin: " + hora_fin.toString() + ", horainicio: " + hora_inicio.toString() + ", observaciones: " + observaciones + ", tiempo_total: " + tiempo_total + ", fecha: " + fecha ;
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
      rs.next();
      Hora hInicio = new Hora(rs.getString(1));
      Hora hFin = new Hora(rs.getString(2));
      B_A_horas labor = new B_A_horas(carnet, hFin,hInicio,rs.getString(4),rs.getString(3),rs.getDouble(6));
      labores.add(labor);
      if (rs.next()) {
        labor = new B_A_horas(carnet, hFin,hInicio,rs.getString(4),rs.getString(3),rs.getDouble(6));
        labores.add(labor);

        while (rs.next()) {
          hInicio = new Hora(rs.getString(1));
          hFin = new Hora(rs.getString(2));
          labor = new B_A_horas(carnet, hFin,hInicio,rs.getString(4),rs.getString(3),rs.getDouble(6));
          labores.add(labor);
        }

        rs.close();          
      }                    
      conn.close();

    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    }
    return labores;

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
  public static String consultarTotalHoras(String carnet){
    ArrayList<B_A_horas> labores = (B_A_horas.consultarLabores(carnet));
    Double total = 0.0;
    Double auxiliar;
    Integer horas;
    Integer minutos;
    for(int iterador = 0; iterador < labores.size(); iterador++){
      total = total + labores.get(iterador).getTiempo_total();
      horas = Integer.parseInt(total.toString().subSequence(0, total.toString().indexOf(".")).toString());
      minutos = Integer.parseInt(total.toString().subSequence(total.toString().indexOf(".")+1, total.toString().length()).toString());

      if (minutos > 59){
        minutos = minutos - 60;
        auxiliar = Double.parseDouble("0."+ minutos.toString());
        total = total  + auxiliar;
      }

    }   
    return total.toString();
  }

}
