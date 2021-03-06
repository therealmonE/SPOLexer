package org.tdf4j.example.jtrAPI;

import org.tdf4j.example.jtrAPI.utils.RPNOptimizer;
import io.github.therealmone.tdf4j.commons.Stream;
import io.github.therealmone.tdf4j.generator.impl.LexerGenerator;
import io.github.therealmone.tdf4j.generator.impl.ParserGenerator;
import io.github.therealmone.tdf4j.model.Token;
import io.github.therealmone.tdf4j.lexer.Lexer;
import io.github.therealmone.tdf4j.tdfparser.TdfParser;
import io.github.therealmone.tdf4j.tdfparser.TdfParserGenerator;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;


import java.io.IOException;
import java.util.List;

public class ParserTest {
    private static TdfParser tdfParser;

    @BeforeClass
    public static void beforeClass() {
        try {
            tdfParser = new TdfParserGenerator(Thread.currentThread().getContextClassLoader().getResourceAsStream("grammar.tdf")).generate();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void parserTests() {
        final Lexer lexer = new LexerGenerator(tdfParser.getLexerModule()).generate();
        final Parser parser = new ParserGenerator(tdfParser.getParserModule()).generate(Parser.class);

        //while tests
        {
            final Stream<Token> stream = lexer.stream("while(a > b) {}$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("while((a > b) & (c < d)) {}$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("while((((((a > b))))) & (((((c < d)))))) {}$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("while((a == b) & (c < d * (5 + 1)) | ((10 *(s + 150) <= 300))){}$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("while(((((((((((c < d))))))))))) {}$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("while(a < b & c > d) {}$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("while(a == b & c < d * (5 + 1) | 10 * (s + 150) <= 300){}$");
            assertTrue(check(stream, parser));
        }

        {
            //if tests
            final Stream<Token> stream = lexer.stream("if(a > b) {}$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("if((a > b) & (c < d)) {}$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("if((((((a > b))))) & (((((c < d)))))) {}$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("if((a == b) & (c < d * (5 + 1)) | ((10 *(s + 150) <= 300))){}$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("if(((((((((((c < d))))))))))) {}$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("if(a < b & c > d) {}$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("if(a == b & c < d * (5 + 1) | 10 * (s + 150) <= 300){}$");
            assertTrue(check(stream, parser));
        }

        {
            //for tests
            final Stream<Token> stream = lexer.stream("for(i = 0; i < 100; i = i + 1) {}$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("for(i = (((((a + b))))) * (((((c + d))))); i > 100; i = i + 1){}$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("for(i = (((1 + 1) * (1 + 2)) * (1 + 1)) * (100 - 10); i > 100; i = i + 1){}$");
            assertTrue(check(stream, parser));
        }

        {
            //do-while tests
            final Stream<Token> stream = lexer.stream("do{}while(a > b)$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("do{}while((a > b) & (c < d))$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("do{}while((((((a > b))))) & (((((c < d))))))$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("do{}while((a == b) & (c < d * (5 + 1)) | ((10 *(s + 150) <= 300)))$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("do{}while(((((((((((c < d)))))))))))$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("do{}while(a < b & c > d)$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("do{}while(a == b & c < d * (5 + 1) | 10 * (s + 150) <= 300)$");
            assertTrue(check(stream, parser));
        }

        {
            //print tests
            final Stream<Token> stream = lexer.stream("print(0);$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("new a = 100; print(a);$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("new a typeof arraylist; put(a, 100); print(get(a, 0));$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("new a typeof hashset; new i = 0; put(a, i); print(get(a, i));$");
            assertTrue(check(stream, parser));
        }

        {
            //get tests
            final Stream<Token> stream = lexer.stream("new a typeof hashset; new i = 100; put(a, i); i = get(a, i);$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("new a typeof arraylist; put(a, 100); i = get(a, 0);$");
            assertTrue(check(stream, parser));
        }

        {
            //remove tests
            final Stream<Token> stream = lexer.stream("new a typeof hashset; new i = 100; put(a, i); remove(a, i);$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("new a typeof arraylist; put(a, 100); remove(a, 0);$");
            assertTrue(check(stream, parser));
        }

        {
            //Combinations
            //TODO: Написать больше комбинаций
            final Stream<Token> stream = lexer.stream("while(a > b) {if (a < d) {do{i = i;}while(a < b)} else {for(i = i; i < 100; i = i + 1){i = i;}}}$");
            assertTrue(check(stream, parser));
        }

        {
            final Stream<Token> stream = lexer.stream("a = b; c = a; a = a;$");
            assertTrue(check(stream, parser));
        }
    }

    @Test
    public void converterTests() {
        final Lexer lexer = new LexerGenerator(tdfParser.getLexerModule()).generate();
        final Parser parser = new ParserGenerator(tdfParser.getParserModule()).generate(Parser.class);

        //while tests
        parser.parse(lexer.stream("while(a < b) {a = a + 1;}$"));
        assertRPN(parser.getRPN(), "%a", "%b", "<", "!F@10", "#a", "%a", "1", "+", "=", "!@0", "$");

        parser.parse(lexer.stream("while(a < b & c > d) {}$"));
        assertRPN(parser.getRPN(), "%a", "%b", "<", "%c", "%d", ">", "&", "!F@9", "!@0", "$");

        parser.parse(lexer.stream("while(((a < b) & (c > d)) | ((a > c) & (b < d))) {}$"));
        assertRPN(parser.getRPN(), "%a", "%b", "<", "%c", "%d", ">", "&", "%a", "%c", ">", "%b", "%d", "<", "&", "|", "!F@17", "!@0", "$");

        parser.parse(lexer.stream("while((a < b) & (c > d) | (a > c) & (b < d)) {}$"));
        assertRPN(parser.getRPN(), "%a", "%b", "<", "%c", "%d", ">", "&", "%a", "%c", ">", "%b", "%d", "<", "&", "|", "!F@17", "!@0", "$");

        parser.parse(lexer.stream("while(a < b) {if (a < b) {}}$"));
        assertRPN(parser.getRPN(), "%a", "%b", "<", "!F@10", "%a", "%b", "<", "!F@9", "!@9", "!@0", "$");

        parser.parse(lexer.stream("while(a < b) {do{} while(a < b)}$"));
        assertRPN(parser.getRPN(), "%a", "%b", "<", "!F@9", "%a", "%b", "<", "!T@4", "!@0", "$");

        parser.parse(lexer.stream("while(a < b) {for(i = 1; i < 100; i = i + 1) {}}$"));
        assertRPN(parser.getRPN(), "%a", "%b", "<", "!F@18", "#i", "1", "=", "%i", "100", "<", "!F@17", "#i", "%i", "1", "+", "=", "!@7", "!@0", "$");

        parser.parse(lexer.stream("while(a < b) {new a typeof hashset; new i = 1; put(a, i);}$"));
        assertRPN(parser.getRPN(), "%a", "%b", "<", "!F@16", "#a", "new", "#hashset", "typeof", "#i", "new", "1", "=", "#a", "#i", "put", "!@0", "$");

        //for tests
        parser.parse(lexer.stream("for(i = 1; (i < n) & (n > i); i = i + 1) {}$"));
        assertRPN(parser.getRPN(), "#i", "1", "=", "%i", "%n", "<", "%n", "%i", ">", "&", "!F@17", "#i", "%i", "1", "+", "=", "!@3", "$");

    }

    private static void assertRPN(final List<String> rpn, final String ... commands) {
        assertEquals(commands.length, rpn.size());
        for (int i = 0; i < commands.length; i++) {
            assertEquals(commands[i], rpn.get(i));
        }
    }

    @Test
    public void testOptimizer() {
        final Lexer lexer = new LexerGenerator(tdfParser.getLexerModule()).generate();
        final Parser parser = new ParserGenerator(tdfParser.getParserModule()).generate(Parser.class);
        final RPNOptimizer optimizer = new RPNOptimizer();

        parser.parse(lexer.stream("print(100 / (25 + 25));$"));
        assertRPN(optimizer.optimize(parser.getRPN()), "2.0", "print", "$");

        parser.parse(lexer.stream("print(1 / (100 * (50 - (1 / 0.16))));$"));
        assertRPN(optimizer.optimize(parser.getRPN()),"2.2857142857142857E-4", "print", "$");

        parser.parse(lexer.stream("print(100 / (25 + 25 - a));$"));
        assertRPN(optimizer.optimize(parser.getRPN()), "100", "50.0", "%a", "-", "/", "print", "$");

        parser.parse(lexer.stream("print(1 / (100 * (50 - (1 / 0.16 - a))));$"));
        assertRPN(optimizer.optimize(parser.getRPN()), "1", "100", "50", "6.25", "%a", "-", "-", "*", "/", "print", "$");
    }


    private boolean check(final Stream<Token> tokens, final Parser parser) {
        assertNotNull(parser.parse(tokens));
        return true;
    }
}
