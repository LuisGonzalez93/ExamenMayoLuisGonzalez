/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proexamenhospital;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Alumno
 */
public class Madre extends Paciente {
    private final static int COSTE_POR_DIA = 100;
    private ArrayList<Bebe> bebes; 

  
    public Madre(int id, String nombre, String admission, int room) {
        super(id, nombre, admission, room, COSTE_POR_DIA);
        bebes = new ArrayList<>();
    }
    
    @Override
    public int getCosteHospitalizacion() {
        int outPut = super.getCosteHospitalizacion();
        for (Bebe bebe : bebes) {
            outPut += bebe.getCosteHospitalizacion();
        }
        return outPut;
    }
    
    public void addBebe(Bebe bebe) {
        bebes.add(bebe);
    }
    
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(super.toString());
        if (bebes.size() > 0) {
            str.append(">");
        }
        Iterator<Bebe> it = bebes.iterator();
        while(it.hasNext()) {
            Bebe bebe = it.next();
            str.append(bebe.toString());
            if (it.hasNext()) {
                str.append("+");
            }
        }
        str.append("\n");
        return str.toString();
    }
    
    @Override
    public void setDepartamento(String departamento) {
        super.setDepartamento(departamento);
        for (Bebe bebe : bebes) {
            bebe.setDepartamento(departamento);
        }
    }
    
    public int bebes() {
        return bebes.size();
    }
 
    public boolean tieneBebe(String bebeNombre) {
        int i = 0;
        boolean tiene = false;
        while(!tiene && i < bebes.size()) {
            Bebe bebe = bebes.get(i);
            if (bebe.getNombre().toLowerCase().contains(bebeNombre.toLowerCase())) {
                tiene = true;
            }
            i++;
        }
        return tiene;
    }
}

