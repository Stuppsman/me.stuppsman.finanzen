package me.stuppsman.finanzen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class FileManager {

	public RandomAccessFile raf;
	
	public File file = new File("plugins/Finanzen/Transaktionen.log");
	
	public void createFile() {
		try{            
			if (!file.exists()) {
	    		file.createNewFile();
	    	
	          System.out.println("File wurde erfolgreich erstellt!");
	        }        
	               
	    }    
	    catch(Exception e)    
	    {    
	        e.printStackTrace();    
	    }
	}
	
	public void addToLog(String index, String content){
	    try {        
	    	
	        raf = new RandomAccessFile(file , "rws"); //Neues RandomAccessFile im Modus 'rws'
	        raf.writeBytes(getContentAsOneLine().toString()); //Vorherigen Inhalt reinschreiben (Die Methode ist hier drunter ;) )
	        raf.writeBytes(index + ": " + content); //Neuen Inhalt hinzufügen
	        raf.close();  //RandomAccessFile closen    
	    } catch (Exception e) {    
	        e.printStackTrace();    
	    }
	}
    public StringBuilder getContentAsOneLine()
    {
    	StringBuilder sb = new StringBuilder(""); //Stringbuilder zum returnen
    	String buffer = ""; //Unser 'zwischenspeicher'

    	try {
    		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                    //Neuer BufferedReader
    		while((buffer = br.readLine()) != null) //Zeilen Auslesen
    		{
    			sb.append(buffer + "\n"); //Jetzt wird die gespeicherte Zeile eingetragen. <!!> Das \n ist für den Zeilenumbruch!!
    		}
    			
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    		
    	return sb;
    }
}
