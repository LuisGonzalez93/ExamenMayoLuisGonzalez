/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proexamenhospital;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Alumno
 */
public abstract class Paciente implements Serializable{
    private String nombre; 
    private LocalDate admission; 
    private LocalDate departamento; 
    private final int habitacion; 
    private final int costePorDia;
    private final int id; 
    private final static String SEPARADOR  = ":";
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Construye un paciente especificando su nombre y la fecha de ingreso en formato dd-mm-aaaa.
     * @param nombre nombre del paciente.
     * @param admission fecha de ingreso.
     */
    public Paciente(int id, String nombre, String admission, int habitacion, int costePorDia) {
        this.id = id;
        this.admission = LocalDate.parse(admission, formatter);
        this.nombre = nombre;
        this.habitacion = habitacion;
        this.costePorDia = costePorDia;
    }

    /**
     * Devuelve el nombre del paciente.
     * @return el nombre del paciente.
     */
    public String getName() {
        return nombre;
    }

    /**
     * Devuelve la fecha de ingreso en formato dd-mm-aaaa.
     * @return la fecha de ingreso en formato dd-mm-aaaa.
     */
    public String getAdmission() {
        return formatter.format(admission);
    }

    /**
     * Devuelve la fecha de alta en formato dd-mm-aaaa.
     * @return la fecha de alta en formato dd-mm-aaaa.
     */
    public String getDeparture() {
        String result = "";
        if (departamento != null) {
            result = formatter.format(departamento);
        }
        return result;
    }

    public void setName(String nombre) {
        if (nombre != null) {
            this.nombre = nombre;
        }
    }

    public void setAdmission(String admission) {
        if (admission != null) {
            this.admission = LocalDate.parse(admission, formatter);
        }
    }

    
    public void setDepartamento(String departamento) {
        if (admission != null) {
            this.departamento = LocalDate.parse(departamento, formatter);
        } else {
            this.departamento = null;
        }
    }
    
    /**
     * getDiasHospitalizados nos devuelve el numero de dias que haya estado
     * el paciente en el hospital
     * @return el numero de dias que lleva hospitalizado el paciente.
     */
    public int getDiasHospitalizados() {
        LocalDate now = LocalDate.now();
        Period period;
        if (departamento != null) {
            period = Period.between(admission, departamento);
        } else {
            period = Period.between(admission, now);
        }
        return period.getDays();
    }
    
    public int getCosteHospitalizacion() {
        return (this.getDiasHospitalizados()) * costePorDia;
    }
    
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(nombre).append(SEPARADOR).append(id).append(SEPARADOR).append(habitacion + 100);
        return str.toString();
    }

    public int getHabitacion() {
        return habitacion;
    }

    public int getId() {
        return id;
    }
    
    
    
    
}


