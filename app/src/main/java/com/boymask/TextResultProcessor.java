package com.boymask;

public class TextResultProcessor {

    public static String process( String text ){
        String addon = "Se hai dei dubbi su qualche termine usato in questa risposta, " + //
                "puoi fare riferimento al Glossario che trovi nelle opzioni della App, nel menu in alto a destra";

        return text+ "\n\n"+addon;
    }
}
