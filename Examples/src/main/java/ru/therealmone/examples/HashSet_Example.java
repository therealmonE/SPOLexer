package ru.therealmone.examples;

import ru.therealmone.applicationAPI.Translator;

import java.io.FileInputStream;
import java.io.IOException;

public class HashSet_Example {
    public static void main(String[] args) throws IOException {
        Translator translator = Translator.create();

        translator.translate(
                Translator.loadProgram(new FileInputStream("examples/src/main/resources/hashset_example.txt"))
        );
    }
}
