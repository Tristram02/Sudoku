package com.mycompany.firstproject;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class FirstProjectTest {

    FirstProject m;

    @Test
    public void testMain() {
        m = new FirstProject();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        FirstProject.main();
        assertNotEquals("", outContent.toString());
    }

}