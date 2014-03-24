/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphic;

/**
 *
 * @author johndelgado
 */
class Hora {
    String cadena;
    Integer hora;
    Integer minutos;

    public Hora(String cadena, Integer hora, Integer minutos) {
        this.cadena = cadena;
        this.hora = hora;
        this.minutos = minutos;
    }
    
    public Hora(String cadena){
        String literal1 = cadena.substring(0, 2);
        String literal2 = cadena.substring(3, 5);
        if (cadena.contains(":")){
            this.cadena = cadena;
            this.hora = Integer.parseInt(literal1);
            this.minutos = Integer.parseInt(literal2);
        }

    }
    
    public Hora(Integer hora, Integer minutos) {
        this.cadena = "Hora calculada";
        this.hora = hora;
        this.minutos = minutos;
    }
    
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
    public String toString() {
        return "Hora{" + "cadena=" + cadena + ", hora=" + hora + ", minutos=" + minutos + '}';
    }
    
    public Double toDouble() {
        Double resultado = this.hora + ((this.minutos) /100.0);
        return resultado;
    }
    
    public Integer esMayor(Hora inicio, Hora fin){        
        return  inicio.hora < fin.hora ? 1 :0;
    }
    
    public Boolean esValida(Hora hora){
        return (hora.hora < 24 && hora.hora > 0 && hora.minutos < 60 && hora.minutos > 0);
    }
}
