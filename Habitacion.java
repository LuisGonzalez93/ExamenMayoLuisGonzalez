/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proexamenhospital;

import java.io.Serializable;

/**
 *
 * @author Alumno
 */
public class Habitacion implements Serializable {
    private int habitacionNumero;
    private Madre madre; 

    public Habitacion(int habitacionNumero) {
        this.habitacionNumero = habitacionNumero;
    }
    
   
    public boolean isEmpty() {
        return madre == null;
    }

    public int getHabitacionNumero() {
        return habitacionNumero;
    }

    public Madre getMadre() {
        return madre;
    }

    public void setHabitacionNumero(int habitacionNumero) {
        this.habitacionNumero = habitacionNumero;
    }

    public void setMadre(Madre madre) {
        this.madre = madre;
    }
    
    public int bebes() {
        int result = 0;
        if (madre != null) {
            result = madre.bebes();
        }
        return result;
    }
        
}

