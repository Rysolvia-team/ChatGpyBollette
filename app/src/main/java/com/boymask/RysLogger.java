package com.boymask;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class RysLogger {

    private static List<String> msgs = new ArrayList<>();

    public static void init() {
        msgs.clear();
    }

    public static void add(String s) {
        msgs.add(s);
    }

    public static void add(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        String sStackTrace = sw.toString();

        msgs.add(sStackTrace);
    }

    public static List<String> getMsgs() {
        return msgs;
    }
}
