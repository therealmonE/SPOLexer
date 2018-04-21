package ru.therealmone.SPOLexer;

import org.junit.Test;
import static org.junit.Assert.*;

public class lexerTest {

    private void addLexemes(Lexer lexer) {
        lexer.addLexeme("VAR", "^[a-z]+$", 4);
        lexer.addLexeme("DIGIT", "^0|([1-9][0-9]*)",3);
        lexer.addLexeme("ASSIGN_OP", "^=$", 2);
        lexer.addLexeme("OP", "^[\\+\\-\\/\\*]$", 1);
    }

    @Test
    public void testGenerate() {
        Lexer lexer = new Lexer();
        addLexemes(lexer);

        //Success tests
        lexer.generateTokens("value$");
        assertEquals(1, lexer.tokens.size());
        assertEquals("VAR", lexer.tokens.get(0).getType());

        lexer.generateTokens("0$");
        assertEquals(1, lexer.tokens.size());
        assertEquals("DIGIT", lexer.tokens.get(0).getType());

        lexer.generateTokens("=$");
        assertEquals(1, lexer.tokens.size());
        assertEquals("ASSIGN_OP", lexer.tokens.get(0).getType());

        lexer.generateTokens("+$");
        assertEquals(1, lexer.tokens.size());
        assertEquals("OP", lexer.tokens.get(0).getType());

        lexer.generateTokens("value = 15 + 0$");
        assertEquals(5, lexer.tokens.size());
        assertEquals("VAR", lexer.tokens.get(0).getType());
        assertEquals("ASSIGN_OP", lexer.tokens.get(1).getType());
        assertEquals("DIGIT", lexer.tokens.get(2).getType());
        assertEquals("OP", lexer.tokens.get(3).getType());
        assertEquals("DIGIT", lexer.tokens.get(4).getType());

        lexer.generateTokens("15 = value - 10$");
        assertEquals(5, lexer.tokens.size());
        assertEquals("DIGIT", lexer.tokens.get(0).getType());
        assertEquals("ASSIGN_OP", lexer.tokens.get(1).getType());
        assertEquals("VAR", lexer.tokens.get(2).getType());
        assertEquals("OP", lexer.tokens.get(3).getType());
        assertEquals("DIGIT", lexer.tokens.get(4).getType());

        lexer.generateTokens("= value / 0$");
        assertEquals(4, lexer.tokens.size());
        assertEquals("ASSIGN_OP", lexer.tokens.get(0).getType());
        assertEquals("VAR", lexer.tokens.get(1).getType());
        assertEquals("OP", lexer.tokens.get(2).getType());
        assertEquals("DIGIT", lexer.tokens.get(3).getType());

        lexer.generateTokens("* = value - 100000$");
        assertEquals(5, lexer.tokens.size());
        assertEquals("OP", lexer.tokens.get(0).getType());
        assertEquals("ASSIGN_OP", lexer.tokens.get(1).getType());
        assertEquals("VAR", lexer.tokens.get(2).getType());
        assertEquals("OP", lexer.tokens.get(3).getType());
        assertEquals("DIGIT", lexer.tokens.get(4).getType());

        lexer.generateTokens("value = a + b - c / 0 * 10045645 = value1 = value2 = - * + /$");
        assertEquals(22, lexer.tokens.size());
        assertEquals("VAR", lexer.tokens.get(0).getType());
        assertEquals("ASSIGN_OP", lexer.tokens.get(1).getType());
        assertEquals("VAR", lexer.tokens.get(2).getType());
        assertEquals("OP", lexer.tokens.get(3).getType());
        assertEquals("VAR", lexer.tokens.get(4).getType());
        assertEquals("OP", lexer.tokens.get(5).getType());
        assertEquals("VAR", lexer.tokens.get(6).getType());
        assertEquals("OP", lexer.tokens.get(7).getType());
        assertEquals("DIGIT", lexer.tokens.get(8).getType());
        assertEquals("OP", lexer.tokens.get(9).getType());
        assertEquals("DIGIT", lexer.tokens.get(10).getType());
        assertEquals("ASSIGN_OP", lexer.tokens.get(11).getType());
        assertEquals("VAR", lexer.tokens.get(12).getType());
        assertEquals("DIGIT", lexer.tokens.get(13).getType());
        assertEquals("ASSIGN_OP", lexer.tokens.get(14).getType());
        assertEquals("VAR", lexer.tokens.get(15).getType());
        assertEquals("DIGIT", lexer.tokens.get(16).getType());
        assertEquals("ASSIGN_OP", lexer.tokens.get(17).getType());
        assertEquals("OP", lexer.tokens.get(18).getType());
        assertEquals("OP", lexer.tokens.get(19).getType());
        assertEquals("OP", lexer.tokens.get(20).getType());
        assertEquals("OP", lexer.tokens.get(21).getType());

        lexer.generateTokens("= - * + / value1 = value2 = a + b * 0 - 9999 = 10000 - 10000 * = value$");
        assertEquals(25, lexer.tokens.size());
        assertEquals("ASSIGN_OP", lexer.tokens.get(0).getType());
        assertEquals("OP", lexer.tokens.get(1).getType());
        assertEquals("OP", lexer.tokens.get(2).getType());
        assertEquals("OP", lexer.tokens.get(3).getType());
        assertEquals("OP", lexer.tokens.get(4).getType());
        assertEquals("VAR", lexer.tokens.get(5).getType());
        assertEquals("DIGIT", lexer.tokens.get(6).getType());
        assertEquals("ASSIGN_OP", lexer.tokens.get(7).getType());
        assertEquals("VAR", lexer.tokens.get(8).getType());
        assertEquals("DIGIT", lexer.tokens.get(9).getType());
        assertEquals("ASSIGN_OP", lexer.tokens.get(10).getType());
        assertEquals("VAR", lexer.tokens.get(11).getType());
        assertEquals("OP", lexer.tokens.get(12).getType());
        assertEquals("VAR", lexer.tokens.get(13).getType());
        assertEquals("OP", lexer.tokens.get(14).getType());
        assertEquals("DIGIT", lexer.tokens.get(15).getType());
        assertEquals("OP", lexer.tokens.get(16).getType());
        assertEquals("DIGIT", lexer.tokens.get(17).getType());
        assertEquals("ASSIGN_OP", lexer.tokens.get(18).getType());
        assertEquals("DIGIT", lexer.tokens.get(19).getType());
        assertEquals("OP", lexer.tokens.get(20).getType());
        assertEquals("DIGIT", lexer.tokens.get(21).getType());
        assertEquals("OP", lexer.tokens.get(22).getType());
        assertEquals("ASSIGN_OP", lexer.tokens.get(23).getType());
        assertEquals("VAR", lexer.tokens.get(24).getType());

        lexer.generateTokens("= = = = = = = = = = = 10000 100 10 1 0 v a l u e * * * * - - - - / / /$");
        assertEquals(32, lexer.tokens.size());
        assertEquals("ASSIGN_OP", lexer.tokens.get(0).getType());
        assertEquals("ASSIGN_OP", lexer.tokens.get(1).getType());
        assertEquals("ASSIGN_OP", lexer.tokens.get(2).getType());
        assertEquals("ASSIGN_OP", lexer.tokens.get(3).getType());
        assertEquals("ASSIGN_OP", lexer.tokens.get(4).getType());
        assertEquals("ASSIGN_OP", lexer.tokens.get(5).getType());
        assertEquals("ASSIGN_OP", lexer.tokens.get(6).getType());
        assertEquals("ASSIGN_OP", lexer.tokens.get(7).getType());
        assertEquals("ASSIGN_OP", lexer.tokens.get(8).getType());
        assertEquals("ASSIGN_OP", lexer.tokens.get(9).getType());
        assertEquals("ASSIGN_OP", lexer.tokens.get(10).getType());
        assertEquals("DIGIT", lexer.tokens.get(11).getType());
        assertEquals("DIGIT", lexer.tokens.get(12).getType());
        assertEquals("DIGIT", lexer.tokens.get(13).getType());
        assertEquals("DIGIT", lexer.tokens.get(14).getType());
        assertEquals("DIGIT", lexer.tokens.get(15).getType());
        assertEquals("VAR", lexer.tokens.get(16).getType());
        assertEquals("VAR", lexer.tokens.get(17).getType());
        assertEquals("VAR", lexer.tokens.get(18).getType());
        assertEquals("VAR", lexer.tokens.get(19).getType());
        assertEquals("VAR", lexer.tokens.get(20).getType());
        assertEquals("OP", lexer.tokens.get(21).getType());
        assertEquals("OP", lexer.tokens.get(22).getType());
        assertEquals("OP", lexer.tokens.get(23).getType());
        assertEquals("OP", lexer.tokens.get(24).getType());
        assertEquals("OP", lexer.tokens.get(25).getType());
        assertEquals("OP", lexer.tokens.get(26).getType());
        assertEquals("OP", lexer.tokens.get(27).getType());
        assertEquals("OP", lexer.tokens.get(28).getType());
        assertEquals("OP", lexer.tokens.get(29).getType());
        assertEquals("OP", lexer.tokens.get(30).getType());
        assertEquals("OP", lexer.tokens.get(31).getType());


        lexer.generateTokens("$");
        assertEquals(0, lexer.tokens.size());
    }

    @Test
    public void testLexemes() {
        Lexer lexer = new Lexer();
        addLexemes(lexer);

        assertEquals(4, lexer.lexemes.size());
    }

    @Test
    public void testCompile() {
        Lexer lexer = new Lexer();
        addLexemes(lexer);

        //Success tests
        assertTrue(lexer.compile("VAR", "value"));
        assertTrue(lexer.compile("VAR", "v"));
        assertTrue(lexer.compile("VAR", "z"));
        assertTrue(lexer.compile("VAR", "va"));
        assertTrue(lexer.compile("VAR", "valuevaluevalue"));

        assertTrue(lexer.compile("DIGIT", "0"));
        assertTrue(lexer.compile("DIGIT", "1"));
        assertTrue(lexer.compile("DIGIT", "9"));
        assertTrue(lexer.compile("DIGIT", "100000"));
        assertTrue(lexer.compile("DIGIT", "100009"));
        assertTrue(lexer.compile("DIGIT", "19"));

        assertTrue(lexer.compile("ASSIGN_OP", "="));

        assertTrue(lexer.compile("OP", "+"));
        assertTrue(lexer.compile("OP", "-"));
        assertTrue(lexer.compile("OP", "/"));
        assertTrue(lexer.compile("OP", "*"));

        //Failure tests
        assertFalse(lexer.compile("VAR", "A"));
        assertFalse(lexer.compile("VAR", "Z"));
        assertFalse(lexer.compile("VAR", "value "));
        assertFalse(lexer.compile("VAR", " value"));
        assertFalse(lexer.compile("VAR", " value "));
        assertFalse(lexer.compile("VAR", " a "));
        assertFalse(lexer.compile("VAR", "valuE"));
        assertFalse(lexer.compile("VAR", "Value"));
        assertFalse(lexer.compile("VAR", "vaLue"));
        assertFalse(lexer.compile("VAR", "valuevalue value"));

        assertFalse(lexer.compile("DIGIT", "09"));
        assertFalse(lexer.compile("DIGIT", "000000"));

        assertFalse(lexer.compile("ASSIGN_OP", "=="));
        assertFalse(lexer.compile("ASSIGN_OP", ":="));
        assertFalse(lexer.compile("ASSIGN_OP", "!="));

        assertFalse(lexer.compile("OP", "+-"));
        assertFalse(lexer.compile("OP", "/*"));
        assertFalse(lexer.compile("OP", "+*"));
        assertFalse(lexer.compile("OP", "="));
        assertFalse(lexer.compile("OP", "=="));
    }

    @Test
    public void testPriority() {
        Lexer lexer = new Lexer();
        lexer.addLexeme("L1", "^[a-z]+", 4);
        lexer.addLexeme("L2", "^[a-z]+", 3);
        lexer.addLexeme("L3", "^[a-z]*[0-9]+", 2);
        lexer.addLexeme("L4", "^[0-9]+", 1);

        lexer.generateTokens("value$"); // L1 - L2
        assertEquals("L1", lexer.tokens.get(0).getType());

        lexer.generateTokens("099$"); // L3 - L4
        assertEquals("L3", lexer.tokens.get(0).getType());
    }
}
