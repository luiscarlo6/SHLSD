/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphic;

import java.util.regex.Pattern;

/**
 * Clase Hora.
 * @author johndelgado
 */
class Hora {
  String cadena;
  Integer hora;
  Integer minutos;

  //contructor de la clase
  public Hora(String cadena, Integer hora, Integer minutos) {
    this.cadena = cadena;
    this.hora = hora;
    this.minutos = minutos;
  }

  //constructor de la clase
  public Hora(String cadena){
    String literal1 = cadena.substring(0, cadena.indexOf(":"));
    String literal2 = cadena.substring(cadena.indexOf(":")+1, cadena.length());
    System.out.println(literal1);
    System.out.println(literal2);
    if (cadena.contains(":")){
      this.cadena = cadena;
      this.hora = Integer.parseInt(literal1);
      this.minutos = Integer.parseInt(literal2);
    }

  }

  //constructor de la clase
  public Hora(Integer hora, Integer minutos) {
    this.cadena = "Hora calculada";
    this.hora = hora;
    this.minutos = minutos;
  }

  //metodo para calcular la diferencia entre dos horas
  public static Hora diferencia(Hora inicio, Hora fin){
    Integer totalHoras = 0;
    Integer totalMinutos = 0;

    if (fin.minutos >= inicio.minutos ){
      totalHoras = fin.hora - inicio.hora;
      totalMinutos = fin.minutos - inicio.minutos;
    }

    if (fin.minutos < inicio.minutos ){
      if (fin.hora == inicio.hora + 1){
        totalHoras = 0;
      }else{
        totalHoras = fin.hora - inicio.hora - 1;
      }
      totalMinutos = (60 - inicio.minutos) + fin.minutos;
    }

    Hora resultado = new Hora(totalHoras,totalMinutos);

    return resultado;
  }

  //getters & setters
  public String getCadena() {
    return cadena;
  }

  public void setCadena(String cadena) {
    this.cadena = cadena;
  }

  public Integer getHora() {
    return hora;
  }

  public void setHora(Integer hora) {
    this.hora = hora;
  }

  public Integer getMinutos() {
    return minutos;
  }

  public void setMinutos(Integer minutos) {
    this.minutos = minutos;
  }

  @Override
    //metodo para convertir una hora en string
    public String toString() {
      return cadena;
    }

  //metodo para convertir una hora a Double
  public Double toDouble() {
    Double resultado = this.hora + ((this.minutos) /100.0);
    return resultado;
  }

  //metodo para ver si una hora es mayor a otra
  public static Boolean esMayor(Hora inicio, Hora fin){        
    return  inicio.hora < fin.hora ? true :false;
  }

  //metodo para validar el formato de una hora
  public static Boolean validarFormato(String formato) {
    String patron = "[0-9][0-9]:[0-9][0-9]";
    String patron2 = "[0-9]:[0-9][0-9]";
    return formato.matches(patron)||formato.matches(patron2);
  }

  //metodo para validar el formato de una fecha
  public static Boolean validarFormatoFecha(String formato) {
    Boolean valida = true;
    String patron1 = "0[1-9]-[0-2][0-9]-[0-9][0-9][0-9][0-9]";
    String patron2 = "1[0-2]-[0-2][0-9]-[0-9][0-9][0-9][0-9]";
    valida = formato.matches(patron1) || formato.matches(patron2) || valida;
    patron1 = "0[1-9]-31-[0-9][0-9][0-9][0-9]";
    patron2 = "1[0-2]-31-[0-9][0-9][0-9][0-9]";
    valida = formato.matches(patron1) || formato.matches(patron2) || valida;
    patron1 = "[1-9]-[1-9]-[0-9][0-9][0-9][0-9]";
    patron2 = "1[0-2]-[1-9]-[0-9][0-9][0-9][0-9]";
    valida = formato.matches(patron1) || formato.matches(patron2) || valida;
    patron1 = "[1-9]-31-[1-9][0-9][0-9]";
    patron2 = "1[0-2]-31-[1-9][0-9][0-9]";
    valida = formato.matches(patron1) || formato.matches(patron2) || valida;
    return  valida;
  }
}
