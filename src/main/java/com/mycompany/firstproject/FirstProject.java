/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.firstproject;


public class FirstProject {

    public static void main(String[] args) {
            SudokuSolver solver = new BacktrackingSudokuSolver();
            SudokuBoard gra = new SudokuBoard(solver);
            gra.solveGame();
            gra.printBoard();
            gra.getBox(1,7);
        }
    }
