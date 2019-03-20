package com.svn;

public class Sites implements Runnable {
    private String URL;
    private String site;
    public Sites(String url, String siteName){
        this.URL = url;
        this.site = siteName;
    }

    @Override
    public void run() {
        System.out.println(site+" thread running begins");
        new Access().launch(URL,site);
        System.out.println(site+" thread running ends");
    }
}
