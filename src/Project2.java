import java.io.FileNotFoundException;

import java_cup.parser;

public class Project2 {

    public static void main (String[] args) {
        //Prevents additional output for parsing errors
        System.err.close();
        //Parse the first file
        System.out.println("FIRST FILE PARSING OUTPUT");
        try {
        	Parser p = new Parser(new ToyScanner("SimpleToy1.toy"));
            p.parse();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to find file.");
        } catch (Exception e) {
            System.out.println("\n[reject] from catch");
        }
/*
        //Parse the second file
        System.out.println("\nSECOND FILE PARSING OUTPUT");
        try {
            parser p1 = new parser(new ToyScanner("ToyTestFile2.toy"));
            p1.parse();;
        } catch (FileNotFoundException e) {
            System.out.println("Unable to find file.");
        } catch (Exception e) {
            System.out.println("\n[reject] from catch");
        }*/
    }
}