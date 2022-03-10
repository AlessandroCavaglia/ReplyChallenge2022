package org.example;


import java.io.FileNotFoundException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws FileNotFoundException {
        ReadFile rf=new ReadFile("file.txt");
        String read=rf.read();
        System.out.println(read);
        WriteFile wf=new WriteFile("file.txt");
        wf.write(read+read.length());
    }
}
