/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.firstproject;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;


public class GreeterTest {
    Greeter g;
   @Test
    public void testGreeter() {
       g = new Greeter();
       ByteArrayOutputStream outContent = new ByteArrayOutputStream();
       System.setOut(new PrintStream(outContent));
       g.greeter();

       String expectedOutput = "Pozdrawiam";

       assertEquals(expectedOutput, outContent.toString());
    }
    
}
