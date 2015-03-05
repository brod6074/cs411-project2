import java.io.*;
import java_cup.runtime.*;

/****
 *
 * Test program for CSC 330 Assignment 3.  See comment for EJayParserTest for
 * further information.
 *
 */
public class PascalParserTest {

    /**
     * See the class comment for documentation.
     */
    public static void main(String[] args) {
        TreeNode t;
        try {
            PascalParser parser = new PascalParser(
                new PascalLexer(new FileReader(args[0])));
            t = (TreeNode) parser.parse().value;
            System.out.println(t);
        }
        catch (Exception e) {
            System.out.println("Exception ");
	    e.printStackTrace();
        }
    }
}