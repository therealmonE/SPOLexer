package ru.therealmone.SPOParser;


public class Parser implements Visitor {
    @Override
    public void visit(String type, String value) {
        System.out.println("Got token: " + type + " --> " + value);
    }
}