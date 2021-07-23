/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chord.client.state;

/**
 *
 * @author Charith.S
 */
public class UserConfig {
    private static String username = "charith";
    private static String ip = "127.0.0.1";
    private static int port = 8000;

    public static String getUsername() {
            return username;
    }

    public static void setUsername(String username) {
            UserConfig.username = username;
    }

    public static String getIp() {
            return ip;
    }

    public static void setIp(String ip) {
            UserConfig.ip = ip;
    }

    public static int getPort() {
            return port;
    }

    public static void setPort(int port) {
            UserConfig.port = port;
    }
}

