package io.github.therealmone.spoLexer;

import io.github.therealmone.translatorAPI.ResourceLoader;
import io.github.therealmone.translatorAPI.SavePrinter;
import io.github.therealmone.translatorAPI.Token;
import io.github.therealmone.spoLexer.exceptions.UnexpectedSymbolException;

import java.util.*;
import java.util.regex.*;

class LexerImpl implements Lexer {
    private List<Token> tokens;
    private final Map<String, Pattern> lexemes;
    private final Map<String, Integer> priority;

    private static final char END_SYMBOL = '$';

    LexerImpl() {
        tokens = new ArrayList<>();
        lexemes = ResourceLoader.getLexemes();
        lexemes.put(String.valueOf(END_SYMBOL), Pattern.compile("^\\" + END_SYMBOL + "$"));
        priority = ResourceLoader.getLexemePriority();
    }

    @Override
    public void showLexemes() {
        SavePrinter.savePrintln("\u001B[33mLEXEMES:\u001B[0m");
        lexemes.forEach((lexeme, pattern) -> SavePrinter.savePrintf("%-20s%-10s%-40s%n", lexeme, "-->", pattern));
    }

    @Override
    public void generateTokens(String input) {
        input = input.replaceAll("\\" + END_SYMBOL, "");
        input += END_SYMBOL;

        tokens.clear();
        StringBuilder tempString = new StringBuilder();

        while (input.charAt(0) != END_SYMBOL) {
            input = input.trim();
            tempString.append(input.charAt(tempString.length()));

            if (!checkLexemes(tempString.toString())) {
                if (tempString.length() > 1) {
                    tempString.deleteCharAt(tempString.length() - 1);
                    tokens.add(new Token(chooseLexeme(tempString.toString()), tempString.toString()));
                    input = input.substring(tempString.length());
                    tempString.delete(0, tempString.length());
                } else {
                    throw new UnexpectedSymbolException(input, tokens);
                }
            }
        }

        tokens.add(new Token("$", "$"));
    }

    private boolean checkLexemes(String str) {
        for (Map.Entry<String, Pattern> entry : lexemes.entrySet()) {
            if (match(entry.getKey(), str)) {
                return true;
            }
        }

        return false;
    }

    private String chooseLexeme(String str) {
        int tmpPriority = 0;
        String lexType = "";

        for (Map.Entry<String, Pattern> entry : lexemes.entrySet()) {
            if (match(entry.getKey(), str)) {
                if (priority.get(entry.getKey()) > tmpPriority) {
                    lexType = entry.getKey();
                    tmpPriority = priority.get(entry.getKey());
                }
            }
        }

        return lexType;
    }

    @Override
    public void showTokens() {
        SavePrinter.savePrintln("\u001B[33mGENERATED TOKENS:\u001B[0m");
        tokens.forEach(token -> SavePrinter.savePrintf("%-20s%-10s%-40s%n", token.getType(), "-->", token.getValue()));
    }

    boolean match(String lexeme, String string) {
        Pattern p = lexemes.get(lexeme);
        Matcher m = p.matcher(string);
        return m.matches();
    }

    @Override
    public Set<String> getTerminals() {
        return Collections.unmodifiableSet(this.lexemes.keySet());
    }

    @Override
    public List<Token> getTokens() {
        return Collections.unmodifiableList(tokens);
    }
}
