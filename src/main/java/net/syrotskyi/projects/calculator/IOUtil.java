package net.syrotskyi.projects.calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOUtil {

    public static String selectMode() throws IOException {
        printToConsole("Please, select mode: ");
        printToConsole("s - standard ( + - * / ), e - engineering (+ - * / ^ root sin cos tan): ");
        return getInputString();
    }

    public static String getInputString() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    public static void printToConsole(String message) {
        System.out.println(message);
    }
}
