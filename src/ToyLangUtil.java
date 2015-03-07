import java.util.regex.Pattern;

import java_cup.runtime.Symbol;

public class ToyLangUtil {
    //Reserved words in Toy
    public static final String[] reserved = {"boolean", "break", "class", "double", "else"
            , "extends", "false", "for", "if", "implements", "int", "interface"
            , "newarray", "println", "readln", "return", "string", "true", "void", "while"};
    //Regular expressions for identifiers, integers, doubles, strings, operations, punctuation, and whitespace
    //Booleans are covered under identifiers and reserved words
    private static final String idRegex = "[A-Za-z_][A-Za-z0-9_]*";
    private static final String intRegex = "0[xX][0-9a-fA-F]+|\\d+";
    private static final String doubleRegex = "\\d+\\.\\d*[Ee][+-]\\d+|\\d+\\.\\d*[Ee]\\d+|\\d+\\.\\d*";
    //Recognize ASCII or Unicode parentheses
    private static final String stringRegex = "\".*?\"|“.*?”";
    private static final String opPuncRegex = "//|/\\*|\\*/|\\+|\\-|\\*|/|%|<=|>=|<|>|==|!=|&&|\\|\\||!|=|;|,|\\.|\\(|\\)|\\[|\\]|\\{|\\}|\"|“|”";
    private static final String whitespaceRegex = "[ \n\t]";
    //A combined regular expression for any possible combination of the above
    //Will work even when there are no whitespace characters in the input
    public static final String totRegex = stringRegex + '|' + idRegex + '|'
            + doubleRegex + '|' + intRegex + '|' + opPuncRegex + '|' + whitespaceRegex;

    public static boolean isIdentifier (String s) {
        return Pattern.matches(idRegex, s);
    }

    public static boolean isReserved (String s) {
        for (int i = 0; i < reserved.length; i++) {
            if (reserved[i].equals(s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isIntConst (String s) {
        return Pattern.matches(intRegex, s);
    }

    public static boolean isDoubleConst (String s) {
        return Pattern.matches(doubleRegex, s);
    }

    public static boolean isStringConst (String s) {
        return Pattern.matches(stringRegex, s);
    }

    public static boolean isOperatorOrPunctuation (String s) {
        return Pattern.matches(opPuncRegex, s);
    }

    public static boolean isWhitespace (String s) {
        return Pattern.matches(whitespaceRegex, s);
    }

    public static int stringToToken (String s) {
        if (isReserved(s)) {
            if (s.equals("boolean")) {
                return sym._boolean;
            } else if (s.equals("break")) {
                return sym._break;
            } else if (s.equals("class")) {
                return sym._class;
            } else if (s.equals("double")) {
                return sym._double;
            } else if (s.equals("else")) {
                return sym._else;
            } else if (s.equals("extends")) {
                return sym._extends;
            } else if (s.equals("false")) {
                return sym._booleanconstant;
            } else if (s.equals("for")) {
                return sym._for;
            } else if (s.equals("if")) {
                return sym._if;
            } else if (s.equals("implements")) {
                return sym._implements;
            } else if (s.equals("int")) {
                return sym._int;
            } else if (s.equals("interface")) {
                return sym._interface;
            } else if (s.equals("newarray")) {
                return sym._newarray;
            } else if (s.equals("println")) {
                return sym._println;
            } else if (s.equals("readln")) {
                return sym._readln;
            } else if (s.equals("return")) {
                return sym._return;
            } else if (s.equals("string")) {
                return sym._string;
            } else if (s.equals("true")) {
                return sym._booleanconstant;
            } else if (s.equals("void")) {
                return sym._void;
            } else if (s.equals("while")) {
                return sym._while;
            } else {
                //Should never happen
                return sym.error;
            }
        } else if (isStringConst(s)) {
            return sym._stringconstant;
        } else if (isIdentifier(s)) {
            return sym._id;
        } else if (isDoubleConst(s)) {
            return sym._doubleconstant;
        } else if (isIntConst(s)) {
            return sym._intconstant;
        } else if (isOperatorOrPunctuation(s)) {
            if (s.equals("+")) {
                return sym._plus;
            } else if (s.equals("-")) {
                return sym._minus;
            } else if (s.equals("*")) {
                return sym._multiplication;
            } else if (s.equals("/")) {
                return sym._division;
            } else if (s.equals("%")) {
                return sym._mod;
            } else if (s.equals("<")) {
                return sym._less;
            } else if (s.equals("<=")) {
                return sym._lessequal;
            } else if (s.equals(">")) {
                return sym._greater;
            } else if (s.equals(">=")) {
                return sym._greaterequal;
            } else if (s.equals("==")) {
                return sym._equal;
            } else if (s.equals("!=")) {
                return sym._notequal;
            } else if (s.equals("&&")) {
                return sym._and;
            } else if (s.equals("||")) {
                return sym._or;
            } else if (s.equals("!")) {
                return sym._not;
            } else if (s.equals("=")) {
                return sym._assignop;
            } else if (s.equals(";")) {
                return sym._semicolon;
            } else if (s.equals(",")) {
                return sym._comma;
            } else if (s.equals(".")) {
                return sym._period;
            } else if (s.equals("(")) {
                return sym._leftparen;
            } else if (s.equals(")")) {
                return sym._rightparen;
            } else if (s.equals("[")) {
                return sym._leftbracket;
            } else if (s.equals("]")) {
                return sym._rightbracket;
            } else if (s.equals("{")) {
                return sym._leftbrace;
            } else if (s.equals("}")) {
                return sym._rightbrace;
            } else {
                //Includes multiline comment ends and double quotes
                return sym.error;
            }
        } else {
            //Includes whitespace
            return sym.error;
        }
    }

    public static String tokenToString (Symbol s) {
        if(s.sym == 0 || s.sym == 1) {
            return sym.terminalNames[s.sym];
        } else if (s.sym > 1 && s.sym < sym.terminalNames.length) {
            return sym.terminalNames[s.sym].substring(1, sym.terminalNames[s.sym].length());
        } else {
            return "NOT_A_TOKEN";
        }
    }
}
