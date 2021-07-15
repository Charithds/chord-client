/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chord.client.others;

import com.mycompany.chord.client.constant.MessageType;
import com.mycompany.chord.client.service.ChordOperation;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Message {
    private String message;
    
    public Message(MessageType type, String ip, int port, String name){

        switch(type){
            case REG:message = appendLength(ChordOperation.REG + " " + ip + " " + port + " " + name);
                break;
            case UNREG:message=appendLength(ChordOperation.UNREG + " " + ip + " " + port + " " + name);
                break;
            case JOIN:message=appendLength(ChordOperation.JOIN + " " + ip + " " + port + " " + name);
                break;
            case JOINOK: message=appendLength(ChordOperation.JOINOK + " " + ip + " " + port + " " + 0);
                break;
            case STORE: message=appendLength(ChordOperation.STORE + " " + ip + " " + port + " " + name);
                break;
            case FILES:
                message = appendLength(ChordOperation.FILES + " " + ip + " " + port + " " + name);
                break;
            case SER:
            {
                String fileName = name;
                message=appendLength(ChordOperation.SER + " " + ip + " " + port + " " + fileName);
                break;
            }
            case INQUIRE: message= appendLength(ChordOperation.INQUIRE + " " + ip + " " + port);
                break; 
            case INQUIREOK: message= appendLength(ChordOperation.INQUIREOK + " " + ip + " " + port);
                break;
            case LEAVE:
                String peerIpPort = name;
                if(peerIpPort!=null){
                    message=appendLength(ChordOperation.LEAVE + " " + ip + " " + port + " " + name);
                }else{
                    message=appendLength(ChordOperation.LEAVE + " " + ip + " " + port + " " + "CHILD-LEAVING");
                }
                break;
        }
    }
    
    static public String customFormat(String pattern, double value ) {
      DecimalFormat myFormatter = new DecimalFormat(pattern);
      String output = myFormatter.format(value);
      return output;
   }
    
    //public Message(MessageType type,String searchKey){
    public Message(MessageType type,String searchKey,String intermediateIp,int intermediatePort){
        switch(type){
            //case SEROK: message=appendLength("SEROK"+" "+"0"+" "+searchKey);
            case SEROK: message=appendLength(
              ChordOperation.SEROK + " " + "0" + " " + searchKey + " " + intermediateIp + " " + intermediatePort);
                break;        
        }
    }
    
  //  public Message(MessageType type, int noOfFiles, String fileDestinationIp, int fileDestinationPort, int hops, ArrayList<String> files, String fileKey){
        public Message(MessageType type, int noOfFiles, String fileDestinationIp, int fileDestinationPort, int hops, ArrayList<String> files, String fileKey,String intermediateIp,int intermediatePort){

        switch(type){
        
            case SEROK: 
            {
                String filesString=fileKey;
                for (String file : files) {
                    filesString = filesString +" "+ file;
                }
                //message=appendLength("SEROK"+" "+noOfFiles+" "+fileDestinationIp+" "+fileDestinationPort+" "+hops+" "+filesString);
                message=appendLength(
                  ChordOperation.SEROK + " " + noOfFiles + " " + fileDestinationIp + " " + fileDestinationPort + " " + hops + " " + filesString + " " + intermediateIp + " " + intermediatePort);
                break;
        
            }
        }
    }
    
    private String appendLength(String message){
         int messageLength = message.length()+4+1;
        String messageLengthString = Integer.toString(messageLength);
        String prefix="";
        switch(messageLengthString.length()){
            case 1: prefix="000"+messageLengthString+" ";
                break;
            case 2:prefix = "00"+messageLengthString+" ";
                break;
            case 3:prefix="0"+messageLengthString+" ";
                break;
            case 4: prefix=messageLengthString+" ";
                break;
        }
        message=prefix+message;
        
        return message;
    }

    public String getMessage(){
        return message;
    }
    
    
}

