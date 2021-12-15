package org.dgf.network;

import java.io.Closeable;
import java.io.IOException;

public class Utility {
    public static String filterURL(String url){
        StringBuilder sb = new StringBuilder();
        for (char c : url.toCharArray()) {
            if (c == ';' || c == ':' || c == '/') {
                sb.append('_');
            }
            else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static void close(Closeable connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
