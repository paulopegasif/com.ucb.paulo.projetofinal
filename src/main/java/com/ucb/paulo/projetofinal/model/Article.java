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
        // limpa quebras de linha e limita tamanho
        String resumoLimpo = abstractText.replaceAll("[\\n\\r]+", " ");
        if (resumoLimpo.length() > 200) {
            resumoLimpo = resumoLimpo.substring(0, 200) + "...";
        }

        return "• " + title + " | " + resumoLimpo;
    }
}
