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
    private LocalDate admision;
    private LocalDate departamento;
    private final int habitacion; 
    private final int costePorDia;
    private final int id; 
    private final static String SEPARADOR  = ":";
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * @param nombre nombre del paciente.
     * @param admision fecha de ingreso.
     */
    public Paciente(int id, String nombre, String admision, int habitacion, int costePorDia) {
        this.id = id;
        this.admision = LocalDate.parse(admision, formatter);
        this.nombre = nombre;
        this.habitacion = habitacion;
        this.costePorDia = costePorDia;
    }

    /**
     * Devuelve el nombre de la persona.
     * @return el nombre de la persona.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve la fecha de ingreso en formato dd-mm-aaaa.
     * @return la fecha de ingreso en formato dd-mm-aaaa.
     */
    public String getIngreso() {
        return formatter.format(admision);
    }

    /**
     * @return  fecha de alta en formato dd-mm-aaaa.
     */
    public String getDepartamento() {
        String outPut = "";
        if (departamento != null) {
            outPut = formatter.format(departamento);
        }
        return outPut;
    }

    public void setNombre(String nombre) {
        if (nombre != null) {
            this.nombre = nombre;
        }
    }

    
    public void admision(String admision) {
        if (admision != null) {
            this.admision = LocalDate.parse(admision, formatter);
        }
    }

    
    public void setDepartamento(String departamento) {
        if (admision != null) {
            this.departamento = LocalDate.parse(departamento, formatter);
        } else {
            this.departamento = null;
        }
    }
    
    
    public int getDiasHospitalizado() {
        LocalDate now = LocalDate.now();
        Period period;
        if (departamento != null) {
            period = Period.between(admision, departamento);
        } else {
            period = Period.between(admision, now);
        }
        return period.getDays();
    }
    
    public int getCosteHospitalizacion() {
        return (getDiasHospitalizado()) * costePorDia;
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

