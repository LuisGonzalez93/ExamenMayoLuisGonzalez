/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proexamenhospital;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.StringJoiner;

/**
 *
 * @author Alumno
 */
public class Hospital implements Serializable {

    //Numero de habitaciones por planta
    private final static int NUMERO_HABITACIONES = 30;
    private ArrayList<Habitacion> habitaciones;
    private int personas;

   
    public Hospital() {
        habitaciones = new ArrayList<>();
        for (int i = 1; i < NUMERO_HABITACIONES + 1; i++) {
            habitaciones.add(new Habitacion(i));
        }
        personas = 1;
    }

    /**
     * @param nombre nombre de la madre.
     * @param fecha fecha del ingreso.
     */
    public void ingresoMadre(String nombre, String fecha) {
        Collections.sort(habitaciones, Comparator.comparing(Habitacion::getHabitacionNumero));
        boolean habitacionDisponible = true;
        int i = 0;
        while (habitacionDisponible && i < habitaciones.size()) {
            Habitacion habitacion = habitaciones.get(i);
            if (habitacion.isEmpty()) {
                Madre madre = new Madre(personas, nombre, fecha, i);
                personas++;
                habitacion.setMadre(madre);
                habitacionDisponible = false;
            }
            i++;
        }
        if (!habitacionDisponible) {
            System.out.println("No hay habitaciones disponibles");
        }
    }

    public void nacimientoBebe(String bebeNombre, int habitacionNumero, String fecha) {
        boolean habitacionesDisponibles = false;
        Iterator<Habitacion> it = habitaciones.iterator();
        while (!habitacionesDisponibles && it.hasNext()) {
            Habitacion habitacion = it.next();
            if (!habitacion.isEmpty()) {
                if (habitacion.getMadre().getId() == habitacionNumero) {
                    Bebe bebe = new Bebe(personas, bebeNombre, fecha, habitacion.getMadre().getHabitacion());
                    habitacion.getMadre().addBebe(bebe);
                    personas++;
                    habitacionesDisponibles = true;
                }
            }
        }
    }

    /**
     * altaFamilia
     * @param id numero de familia.
     * @param fecha fecha de alta.
     * @return Devuelve el coste de la atencion sanitaria o, -1, en caso de que se intente dar de
     * alta a un paciente que no exista.
     */
    public int altaFamilia(int id, String fecha) {
        int outPut = -1;
        boolean habitacionesDisponibles = false;
        int i = 0;
        while (!habitacionesDisponibles && i < habitaciones.size()) {
            Habitacion habitacion = habitaciones.get(i);
            if (!habitacion.isEmpty() && habitacion.getMadre().getId() == id) {
                habitacion.getMadre().setDepartamento(fecha);
                outPut = habitacion.getMadre().getCosteHospitalizacion();
                habitacionesDisponibles = true;
            }
            i++;
        }
        return outPut;
    }

    
    

}

