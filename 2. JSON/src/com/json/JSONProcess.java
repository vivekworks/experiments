package com.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class JSONProcess {

    BufferedReader reader;
    PrintWriter writer;
    File directory;
    String jsonFileName;
    HashMap<String, String> userDataMap;

    public void begin() {
        prepareUserData();
        initializeWriter();
        writeJSON();
        initializeReader();
        readJSON();
    }

    public void prepareUserData() {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("----Enter the user data----");
        System.out.print("Name : ");
        String name = inputScanner.nextLine();
        System.out.print("Age : ");
        int age = inputScanner.nextInt();
        inputScanner.nextLine();
        System.out.print("DOB (dd-mon-yyyy) : ");
        String dob = inputScanner.nextLine();
        System.out.println("Address");
        System.out.print("Block No. : ");
        String blockNo = inputScanner.nextLine();
        System.out.print("Street : ");
        String street = inputScanner.nextLine();
        System.out.print("Location : ");
        String location = inputScanner.nextLine();
        System.out.print("City : ");
        String city = inputScanner.nextLine();
        System.out.print("PIN : ");
        String pin = inputScanner.nextLine();
        System.out.println("Phone Numbers");
        System.out.print("Home : ");
        String homePhone = inputScanner.nextLine();
        System.out.print("Personal : ");
        String personalPhone = inputScanner.nextLine();
        System.out.print("JSON FileName : ");
        jsonFileName = inputScanner.nextLine();
        createMap(name, age, dob, blockNo, street, location, city, pin, homePhone, personalPhone);
    }

    public void createMap(String name, int age, String dob, String blockNo, String street, String location, String city, String pin, String homePhone, String personalPhone) {
        userDataMap = new HashMap<>();
        userDataMap.put("name", name);
        userDataMap.put("age", String.valueOf(age));
        userDataMap.put("dob", dob);
        userDataMap.put("blockNo", blockNo);
        userDataMap.put("street", street);
        userDataMap.put("location", location);
        userDataMap.put("city", city);
        userDataMap.put("pin", pin);
        userDataMap.put("homePhone", homePhone);
        userDataMap.put("personalPhone", personalPhone);
    }

    public void writeJSON() {
        JSONObject json = new JSONObject();
        String message = null;
        try {
            json.put("name", userDataMap.get("name"));
            json.put("age", userDataMap.get("age"));
            json.put("dob", userDataMap.get("dob"));
            JSONObject jsonAddress = new JSONObject();
            jsonAddress.put("blockNo", userDataMap.get("blockNo"));
            jsonAddress.put("street", userDataMap.get("street"));
            jsonAddress.put("location", userDataMap.get("location"));
            jsonAddress.put("city", userDataMap.get("city"));
            jsonAddress.put("pin", userDataMap.get("pin"));
            json.put("address", jsonAddress);
            JSONArray jsonPhoneArray = new JSONArray();
            JSONObject jsonPhone = new JSONObject();
            jsonPhone.put("type", "home");
            jsonPhone.put("number", userDataMap.get("homePhone"));
            jsonPhoneArray.put(jsonPhone);
            jsonPhone = new JSONObject();
            jsonPhone.put("type", "personal");
            jsonPhone.put("number", userDataMap.get("personalPhone"));
            jsonPhoneArray.put(jsonPhone);
            json.put("phoneNumbers", jsonPhoneArray);
            message = json.toString();
            System.out.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer.println(message);
        writer.flush();
        if (!writer.checkError())
            writer.close();
    }

    public void readJSON() {
        StringBuilder msgBuilder = new StringBuilder();
        String line = "";
        try {
            while ((line = reader.readLine()) != null){
                msgBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String message = String.valueOf(msgBuilder);
        JSONObject json;
        try {
            json = new JSONObject(message);
            System.out.println("Name : "+json.getString("name"));
            System.out.println("Age : "+json.getString("age"));
            System.out.println("DOB : "+json.getString("dob"));
            JSONObject jsonAddress = json.getJSONObject("address");
            System.out.println("Address");
            System.out.println("Block No : "+jsonAddress.getString("blockNo"));
            System.out.println("Street : "+jsonAddress.getString("street"));
            System.out.println("Location : "+jsonAddress.getString("location"));
            System.out.println("City : "+jsonAddress.getString("city"));
            System.out.println("PIN : "+jsonAddress.getString("pin"));
            System.out.println("Phone Numbers");
            JSONArray jsonArray = json.getJSONArray("phoneNumbers");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject phoneNumber = jsonArray.getJSONObject(i);
                System.out.println(phoneNumber.get("type")+" : "+phoneNumber.get("number"));
            }
        } catch (org.json.JSONException ojj){
            ojj.printStackTrace();
        }
    }

    public void initializeReader() {
        File file = new File(directory + "/" + jsonFileName + ".txt");
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
    }

    public void initializeWriter() {
        directory = new File(Paths.get("").toAbsolutePath() + "\\out\\file\\");
        if (!directory.exists())
            directory.mkdir();
        File file = new File(directory + "/" + jsonFileName + ".txt");
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
