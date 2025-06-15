package com.ucb.paulo.projetofinal.model;

/// Usando somente Title e Abstract para busca de Strings

public class Article {
    private String title;
    private String abstractText;

    public Article(String title, String abstractText) {
        this.title = title;
        this.abstractText = abstractText;
    }

    public String getTitle() {
        return title;
    }

    public String getAbstractText() {
        return abstractText;
    }

    @Override
    public String toString() {

        String resumoCurto = abstractText.length() > 150 ? abstractText.substring(0, 150) + "...": abstractText; // encurtando o resumo
        return "Título: " + title + " \nAbstract: " + resumoCurto + "\n";
    }
}
