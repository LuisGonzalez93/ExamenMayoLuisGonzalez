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

    private final static int HABITACIONES_POR_PLANTA = 30;
    private ArrayList<Habitacion> habitaciones;
    private int pacientes;

    public Hospital() {
        habitaciones = new ArrayList<>();
        for (int i = 1; i < HABITACIONES_POR_PLANTA + 1; i++) {
            habitaciones.add(new Habitacion(i));
        }
        pacientes = 1;
    }

    
    public void ingresoMadre(String nombre, String fecha) {
        Collections.sort(habitaciones, Comparator.comparing(Habitacion::getHabitacionNumero));
        boolean flag = false;
        int i = 0;
        while (!flag && i < habitaciones.size()) {
            Habitacion habitacion = habitaciones.get(i);
            if (habitacion.isEmpty()) {
                Madre madre = new Madre(pacientes, nombre, fecha, i + 1);
                habitacion.setPaciente(madre);
                pacientes++;
                flag = true;
            }
            i++;
        }
        if (!flag) {
            System.out.println("No hay habitaciones disponibles");
        }
    }

    public void nacimientoBebe(String babyName, int roomNumber, String fecha) {
        boolean flag = false;
        Iterator<Habitacion> it = habitaciones.iterator();
        while (!flag && it.hasNext()) {
            Habitacion habitacion = it.next();
            if (!habitacion.isEmpty()) {
                if (habitacion.getMadre().getId() == roomNumber) {
                    Bebe bebe = new Bebe(pacientes, babyName, fecha, habitacion.getMadre().getHabitacion());
                    habitacion.getMadre().addBebe(bebe);
                    pacientes++;
                    flag = true;
                }
            }
        }
    }

   
    public int altaFamilia(int id, String fecha) {
        int result = -1;
        boolean flag = false;
        int i = 0;
        while (!flag && i < habitaciones.size()) {
            Habitacion habitacion = habitaciones.get(i);
            if (!habitacion.isEmpty() && habitacion.getMadre().getId() == id) {
                habitacion.getMadre().setDepartamento(fecha);
                result = habitacion.getMadre().getCosteHospitalizacion();
                habitacion.vaciarHabitacion();
                flag = true;
            }
            i++;
        }
        return result;
    }

    public String getListadoHabitacionesOrdenNumeroBebes() {
        Collections.sort(habitaciones, Comparator.comparing(Habitacion::bebes).reversed().thenComparing(Habitacion::getHabitacionNumero));
        int habitacionesOcupadas = 0;
        StringBuilder str = new StringBuilder();
        for (Habitacion habitacion : habitaciones) {
            if (!habitacion.isEmpty()) {
                habitacionesOcupadas++;
                str.append(habitacion.getMadre().toString());
            }
        }
        str.append(habitacionesOcupadas).append(" habitaciones ocupadas");
        System.out.println(str);
        return str.toString();
    }

    public String getListadoHabitacionesOrdenNumeroBebes(String fecha) {
        Collections.sort(habitaciones, Comparator.comparing(Habitacion::bebes).reversed().thenComparing(Habitacion::getHabitacionNumero));
        int habitacionesOcupadas = 0;
        StringBuilder str = new StringBuilder();
        ArrayList numeroHabitaciones=new ArrayList<>();
        for (Habitacion habitacion : habitaciones) {
            if (!habitacion.isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate searched = LocalDate.parse(fecha, formatter);
                LocalDate admission = LocalDate.parse(habitacion.getMadre().getAdmission(), formatter);
                Period period = Period.between(admission, searched);
                if (period.getDays() >= 0) {
                    habitacionesOcupadas++;
                    numeroHabitaciones.add(habitacion.getHabitacionNumero() + 100);
                }
            }
        }
        str.append(habitacionesOcupadas).append(" habitaciones ocupadas el ").append(fecha);
        if (numeroHabitaciones.size() > 0) {
            str.append(": ").append(numeroHabitaciones.toString().substring(0,numeroHabitaciones.toString().length()-1));
        }
        return str.toString();
    }

    public int getMediaNumeroBebes() {
        int result = -1;
        int habitacionesOcupadas = 0;
        int nenos = 0;
        for (Habitacion habitacion : habitaciones) {
            if (!habitacion.isEmpty()) {
                habitacionesOcupadas++;
                nenos += habitacion.bebes();
            }
        }
        if (habitacionesOcupadas > 0) {
            if (nenos == 0) {
                result = 0;
            } else {
                result = (int) Math.ceil(nenos / habitacionesOcupadas);
            }
        }
        return result;
    }

    public String getInfoFamiliaDelBebe(String babyName) {
        StringBuilder str = new StringBuilder();
        for (Habitacion habitacion : habitaciones) {
            if (!habitacion.isEmpty()) {
                if (habitacion.getMadre().tieneBebe(babyName)) {
                    str.append(habitacion.getMadre().toString());
                }
            }
        }
        return str.toString();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Habitacion habitacion : habitaciones) {
            if (!habitacion.isEmpty()) {
                str.append(habitacion.getMadre().toString());
            }
        }
        return str.toString();
    }

    public static void save(Hospital hospital) {
        try {
            FileOutputStream fos = new FileOutputStream("hospital.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(hospital);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Hospital restore() {
        Hospital hospital = null;
        try {
            FileInputStream fis = new FileInputStream("hospital.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            hospital = (Hospital) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            return hospital;
        }
    }

}
