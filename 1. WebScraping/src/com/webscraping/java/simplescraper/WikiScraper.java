package com.webscraping.java.simplescraper;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.Proxy;
import java.net.InetSocketAddress;
//https://en.wikipedia.org/wiki/Python
public class WikiScraper {
    public static void scrapeTopic(String url){
        String html = getUrl("https://en.wikipedia.org/"+url);
        //System.out.println(html);
        Document doc = Jsoup.parse(html);
        Elements context = doc.select(".mw-parser-output > ul");
        for(Element val :context){
            System.out.println(val.text());
        }
        System.out.println(context);
    }

    public static String getUrl(String url){
        System.out.println("URL - "+url);
        URL urlObj = null;
        try{
            urlObj = new URL(url);//URL object constructor accepts the string url
        } catch (MalformedURLException mue){
            System.out.println("URL is malformed");
            return "";
        }
        URLConnection urlCon = null;
        BufferedReader in = null;
        String outputText = "";
        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.177.38.133", 80));
            urlCon = urlObj.openConnection(proxy);//Opening connection to get data over web
            in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));//Providing input stream to the reader
            String line="";
            while((line = in.readLine()) != null)
                outputText += line;
            in.close();
        } catch(IOException ioe){
            System.out.println("Error connecting the url");
            return "";
        }
        return outputText;
    }
}
