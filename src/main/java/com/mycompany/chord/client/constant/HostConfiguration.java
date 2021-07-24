/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chord.client.constant;

import java.util.ArrayList;

public class HostConfiguration
{
//    public static String MY_IP = "127.0.0.1";

//    public static int MY_PORT = 500;

    public static String MY_NAME = "S";
    public static boolean isSuper = false;
    public static boolean isWebService=false;
    

    public static String BOOTSTRAP_IP = "127.0.0.1";

    public static int noOfNodes = 1;
    public static int myNodeNumber = 0;
    
    public static  int BOOTSTRAP_PORT =  5555;
    public static  int INDEX_PORT =  55555;
    public static ArrayList<String> availableFiles = new ArrayList<>(); 
   
    public static int TTL = 10;
    public static int noOfPeersPreset = 8;
    
    public void addNewFile(String fileName){
        availableFiles.add(fileName); 
    }
}
