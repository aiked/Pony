/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Pony;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author jit
 */
public class StopWords {
    
    private String language;
    private String path;
    private HashSet<String> words;
    
    public StopWords(){
        this.language   = null;
        this.path       = null;
        this.words      = new HashSet<String>();
    }
    
    public StopWords( String path, String language ) throws IOException{
        
        this.words = new HashSet<>();
        this.language   = language;
        this.path       = path;
        
        try{
            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(path), "UTF-8"))) {

                String line;
                
                while ((line = br.readLine()) != null) {
                    this.words.add(line);
                }
            }
        }catch ( IOException e){ System.err.println("Error: "+e.getMessage()); }
    }
    
    public String   getLanguage() {
        return language;
    }
    public void     setLanguage(String language) {
        this.language = language;
    }
    
    public String   getPath() {
        return path;
    }
    public void     setPath(String path) {
        this.path = path;
    }

    public Boolean  findWord( String word){
        return this.words.contains(word);
    }
    
    public Boolean ReadFromFile( String path ) throws IOException{
        try{
            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(path), "UTF-8"))) {

                String line;
                
                while ((line = br.readLine()) != null) {
                    this.words.add(line);
                }
            }
            return true;
        }catch ( IOException e){ System.err.println("Error: "+e.getMessage()); }
        
        return false;
    }
    
    public void PrintWords(){
        System.out.println("\nFilepath: "+this.path);
        System.out.println("Language: "+this.language);
        System.out.println("Size: "+this.words.size());
        for ( String s : this.words){
            System.out.println(s);
        }
    }
    
    
}
