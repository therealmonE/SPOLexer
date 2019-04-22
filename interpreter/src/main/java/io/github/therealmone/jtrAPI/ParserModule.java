package io.github.therealmone.jtrAPI;

import io.github.therealmone.tdf4j.parser.config.AbstractParserModule;

public class ParserModule extends AbstractParserModule {
    @Override
    public void configure() {
        prod("lang")
                .then(repeat(nt("expr")))
                .then(t("$"));

        prod("expr")
                .then(oneOf(
                        nt("while_loop"),
                        nt("for_loop"),
                        nt("if_statement"),
                        nt("do_while_loop"),
                        nt("print_expr"),
                        nt("assign_expr"),
                        nt("init_expr"),
                        nt("put_expr"),
                        nt("remove_expr"),
                        nt("rewrite_expr")
                ));

        prod("while_loop")
                .is(
                        t("WHILE"),
                        t("LB"),
                        nt("condition"),
                        t("RB"),
                        t("FLB"),
                        repeat(nt("expr")),
                        t("FRB")
                );

        prod("for_loop")
                .is(
                        t("FOR"),
                        t("LB"),
                        nt("assign_expr_without_del"),
                        t("DEL"),
                        nt("condition"),
                        t("DEL"),
                        nt("assign_expr_without_del"),
                        t("RB"),
                        t("FLB"),
                        repeat(nt("expr")),
                        t("FRB")
                );

        prod("if_statement")
                .is(
                        t("IF"),
                        t("LB"),
                        nt("condition"),
                        t("RB"),
                        t("FLB"),
                        repeat(nt("expr")),
                        t("FRB"),
                        optional(nt("else_stmt"))
                );

        prod("else_stmt")
                .is(
                        t("ELSE"),
                        t("FLB"),
                        repeat(nt("expr")),
                        t("FRB")
                );

        prod("assign_expr")
                .is(
                        t("VAR"),
                        t("ASSIGN_OP"),
                        nt("value_expr"),
                        t("DEL")
                );

        prod("assign_expr_without_del")
                .is(
                        t("VAR"),
                        t("ASSIGN_OP"),
                        nt("value_expr")
                );

        prod("do_while_loop")
                .is(
                        t("DO"),
                        t("FLB"),
                        repeat(nt("expr")),
                        t("FRB"),
                        t("WHILE"),
                        t("LB"),
                        nt("condition"),
                        t("RB")
                );

        prod("print_expr")
                .is(
                        t("PRINT"),
                        t("LB"),
                        nt("print_parameters"),
                        t("RB"),
                        t("DEL")
                );

        prod("print_parameters")
                .is(
                        nt("value_expr"),
                        repeat(
                                t("CONCAT"),
                                nt("print_parameters")
                        )
                );

        prod("put_expr")
                .is(
                        t("PUT"),
                        t("LB"),
                        t("VAR"),
                        t("COMMA"),
                        nt("value"),
                        t("RB"),
                        t("DEL")
                );

        prod("remove_expr")
                .is(
                        t("REMOVE"),
                        t("LB"),
                        t("VAR"),
                        t("COMMA"),
                        nt("value"),
                        t("RB"),
                        t("DEL")
                );

        prod("rewrite_expr")
                .is(
                        t("REWRITE"),
                        t("LB"),
                        t("VAR"),
                        t("COMMA"),
                        nt("value"),
                        t("COMMA"),
                        nt("value_expr"),
                        t("RB"),
                        t("DEL")
                );

        prod("init_expr")
                .is(
                        t("NEW"),
                        t("VAR"),
                        optional(nt("inst_expr")),
                        t("DEL")
                );

        prod("inst_expr")
                .is(
                        or(
                                group(t("ASSIGN_OP"), nt("value_expr")),
                                group(t("TYPEOF"), nt("type"))
                        )
                );

        prod("type")
                .is(oneOf(
                        t("ARRAYLIST"),
                        t("HASHSET")
                ));

        prod("condition")
                .is(
                        or(
                                group(t("LB"), nt("condition"), t("RB")),
                                nt("compare_expr")
                        ),
                        repeat(
                                t("LOP"),
                                nt("condition")
                        )
                );

        prod("compare_expr")
                .is(
                        nt("value_expr"),
                        t("COP"),
                        nt("value_expr")
                );

        prod("value_expr")
                .is(
                        or(
                                group(t("LB"), nt("value_expr"),  t("RB")),
                                nt("value_expr_1")
                        ),
                        repeat(
                                t("OP"),
                                nt("value_expr")
                        )
                );

        prod("value_expr_1")
                .is(
                        oneOf(
                                nt("value"),
                                nt("get_expr"),
                                nt("size_expr")
                        ),
                        repeat(t("OP"), nt("value_expr"))
                );

        prod("value")
                .is(oneOf(
                        t("VAR"),
                        t("DIGIT"),
                        t("DOUBLE"),
                        t("STRING")
                ));

        prod("get_expr")
                .is(
                        t("GET"),
                        t("LB"),
                        t("VAR"),
                        t("COMMA"),
                        nt("value"),
                        t("RB")
                );

        prod("size_expr")
                .is(
                        t("SIZE"),
                        t("LB"),
                        t("VAR"),
                        t("RB")
                );
    }
}
