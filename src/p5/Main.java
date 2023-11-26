package p5;

import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static final String urlName = "http://pravo.gov.ru/";

    public static void main(String[] args) throws Exception {
        URL url =new URI(urlName).toURL();
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();

        Map<String, List<String>> headers = urlConnection.getHeaderFields();
        for(Map.Entry<String, List<String>> entry: headers.entrySet()){
            String key = entry.getKey();
            for (String value: entry.getValue()){
                System.out.println(key + ":" + value);
            }
        }

        System.out.println("---------------------------");
        System.out.println("ContentType: " + urlConnection.getContentType());
        System.out.println("ContentLength: " + urlConnection.getContentLength());
        System.out.println("ContentEncoding:" + urlConnection.getContentEncoding());
        System.out.println("Date: " + urlConnection.getDate());
        System.out.println("Expiration: " + urlConnection.getExpiration());
        System.out.println("LastModified: " + urlConnection.getLastModified());
        System.out.println("---------------------------");

        Scanner scanner = new Scanner(urlConnection.getInputStream());

        for(int i = 1; scanner.hasNextLine() && i <=20; i++){
            System.out.println(scanner.nextLine());
            //if (scanner.hasNextLine()) System.out.println("....");
        }




    }
}
