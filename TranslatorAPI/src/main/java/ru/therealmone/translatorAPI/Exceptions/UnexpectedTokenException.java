package ru.therealmone.translatorAPI.Exceptions;

import ru.therealmone.translatorAPI.Token;

public class UnexpectedTokenException extends Exception implements ExceptionInterface {
    private String expected;
    private Token token;
    private String history;

    public UnexpectedTokenException(Token token, String expected, String history) {
        this.expected = expected;
        this.token = token;
        this.history = history;
    }

    @Override
    public void message() {
        System.out.println("Unexpected token " + token.getType() + " at '^' mark. \n" + history + token.getValue());
        for (int i = 0; i < history.length(); i++) {
            System.out.print(" ");
        }
        System.out.println("^");
        System.out.println("Expected: " + expected);
    }
}
