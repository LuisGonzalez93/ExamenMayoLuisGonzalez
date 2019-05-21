/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proexamenhospital;

/**
 *
 * @author Alumno
 */
public class Bebe extends Paciente {
    private final static int COSTE_POR_DIA = 70;

    public Bebe(int id, String nombre, String nacimiento, int habitacion) {
        super(id, nombre, nacimiento, habitacion,  COSTE_POR_DIA);
    }

}

