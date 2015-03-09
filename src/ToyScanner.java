
import java_cup.runtime.*;

import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.util.regex.*;

public class ToyScanner implements java_cup.runtime.Scanner{

    private Trie trie;
    private boolean isMultLineComment;
    private Stack<Symbol> out;
    private final int DEFAULT_MAX_SYMBOLS = 1500;
    private boolean firstDone = false;

    public ToyScanner(int maxSymbols, String fileName) throws FileNotFoundException {
        isMultLineComment = false;
        out = new Stack<Symbol>();
        trie = new Trie(ToyLangUtil.reserved, maxSymbols);
        scanFile(fileName, false);
    }

    public ToyScanner(String fileName) throws FileNotFoundException {
        isMultLineComment = false;
        out = new Stack<Symbol>();
        trie = new Trie(ToyLangUtil.reserved, DEFAULT_MAX_SYMBOLS);
        scanFile(fileName, false);
    }

    //Scan any given file to the lexical analyzer
    public void scanFile (String fileName, boolean verbose) throws FileNotFoundException {
        Scanner scFile = new Scanner(new File(fileName));
        while (scFile.hasNext()) {
            String tempLine = scFile.nextLine().trim();
            scanLine(tempLine);
        }
        Stack<Symbol> temp = new Stack<Symbol>();
        temp.push(new Symbol(sym.EOF));
        while(!out.empty()) {
            temp.push(out.pop());
        }
        out = temp;
        
    }

    //The method that scans each line into words for the trie
    //Reserved flag for denoting if a line contains reserved words or not
    //Verbosity flag for debugging and output reasons
    private void scanLine (String line) {
        //Empty lines are ignored
        if (line.isEmpty()) {
            return;
        }
        //Find all tokens in the line that fit the language accordingly
        Pattern p = Pattern.compile(ToyLangUtil.totRegex);
        Matcher m = p.matcher(line);
        ArrayList<String> tokens = new ArrayList<String>();
        while (m.find()) {
            tokens.add(line.substring(m.start(), m.end()));
        }
        String[] strTokens = new String[tokens.toArray().length];
        strTokens = tokens.toArray(strTokens);
        //Parse each token individually
        for (int i = 0; i < strTokens.length; i++) {
            //Skip whitespace
            if (ToyLangUtil.isWhitespace(strTokens[i])) {
                continue;
            }
            //Skip the rest of the line once a "//" token appears
            else if (strTokens[i].equals("//")) {
                break;
                //Mark if we are in a multi line comment or not as indicated by appearance of markers
            } else if (strTokens[i].equals("/*")) {
                //We can put anything inside a multiple line comment, including starting another one
                isMultLineComment = true;
            } else if (strTokens[i].equals("*/")) {
                //We cannot have nested comments though, so if we aren't in a multiple line comment and an ending marker
                //appears, we parse it as an invalid token
                if (isMultLineComment) {
                    isMultLineComment = false;
                }
            }
            //We need to not be in a multi line comment to parse the word
            else if (!isMultLineComment) {
                if(ToyLangUtil.isIdentifier(strTokens[i])) {
                    trie.scanWord(strTokens[i], false);
                }
                //Add the scanned token to a list of output, for use later
                out.push(new Symbol(ToyLangUtil.stringToToken(strTokens[i])));
            }
        }
    }

    @Override
    public Symbol next_token () throws Exception {
        if(out.empty()) {
            return null;
        }
        Symbol s = out.pop();
        if(firstDone) {
            System.out.print("[shift]\n" + ToyLangUtil.tokenToString(s) + " ");
        } else {
            System.out.print(ToyLangUtil.tokenToString(s) + " ");
        }
        firstDone = true;
        return s;
    }
}
