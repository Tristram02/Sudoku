/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.firstproject;


public class FirstProject {

    public static void main(String[] args) {
        com.mycompany.firstproject.SudokuBoard gra = new SudokuBoard();
        if (gra.fillBoard(gra.getBoard())) {
            gra.printBoard();
        } else {
            System.out.println("Nie da sie rozwiazac tej planszy");
        }
    }
}
