package io.github.therealmone.spoParser;

import io.github.therealmone.translatorAPI.Node;
import io.github.therealmone.translatorAPI.Token;

import java.util.ArrayList;

class TreeNode extends Node {
    private ArrayList<TreeNode> childes;
    private Token token;

    TreeNode(String value) {
        super(value);
        this.childes = new ArrayList<>();
    }

    TreeNode(String value, TreeNode parent) {
        super(value, parent);
        this.childes = new ArrayList<>();
    }

    void addChild(TreeNode child) {
        this.childes.add(child);
    }

    Token getToken() {
        return this.token;
    }

    void setToken(Token token) {
        this.token = token;
    }

    ArrayList<TreeNode> getChildes() {
        return this.childes;
    }
}