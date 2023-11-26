package p4;

import java.net.URLConnection;
import java.util.Base64;

public class Main {

    public static void main(String[] args) {
        //mailto:alex@yandex.ru
        //http://yandex.ru
        //https://yandex.ru
        //ftp://
        //file://file.local
        //jar://
        //[схема]:спецчасть[#необязат часть]

        URLConnection urlConnection;

        //urlConnection.setDoOutput();

        String enc = Base64.getEncoder().encodeToString("password".getBytes());
        System.out.println(enc);
        byte[] denc = Base64.getDecoder().decode(enc);
        String dencString = new String(denc);
        System.out.println(dencString);


    }
}
