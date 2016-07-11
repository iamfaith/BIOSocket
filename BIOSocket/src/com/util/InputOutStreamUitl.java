package com.util;

import java.io.*;

/**
 * Created by faith on 16/7/11.
 */
public class InputOutStreamUitl {

    public static String readLine(InputStream inputStream) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        return bufferedReader.readLine();

    }

    public static void writeSocket(OutputStream os, String msg) throws Exception{
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(os);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        bufferedWriter.write(msg);
        os.flush();
        bufferedWriter.flush();
    }

    public static void writeSocketLine(OutputStream os, String msg) throws Exception{
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(os);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        bufferedWriter.write(msg);
        bufferedWriter.write(System.lineSeparator());
        os.flush();
        bufferedWriter.flush();
    }
}
