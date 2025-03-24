package com.tseste.personal_finance_analytics;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Utils {
    public static String ExceptionToString(Exception  e) {
        StringWriter stingWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stingWriter);
        e.printStackTrace(printWriter);
        return stingWriter.toString();
    }
}
