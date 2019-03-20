package com.main;

import com.svn.Sites;

public class Main {
    public static void main(String[] args){
        String[] siteList = {};
        String URL = "";
        for(String site :siteList) {
            new Thread(new Sites(URL, site)).start();
        }
    }
}
