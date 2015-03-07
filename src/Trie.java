
public class Trie {
    //~ is unused, * is reserved word ending, @ is other ending
    private char[] symbolArray;
    //-1 is unused
    private int[] nextArray, switchArray;
    private int freeSpacePointer;

    //The alphabet, for reserved keywords and identifiers
    private static final char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'
            , 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V'
            , 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'
            , 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v'
            , 'w', 'x', 'y', 'z'};

    //Initializes the trie structure with a set of reserved words and a max size
    public Trie (String[] reservedWords, int maxSymbols) {
        switchArray = new int[52];
        for (int i = 0; i < switchArray.length; i++) {
            switchArray[i] = -1;
        }
        symbolArray = new char[maxSymbols];
        nextArray = new int[maxSymbols];
        for (int i = 0; i < symbolArray.length; i++) {
            symbolArray[i] = '~';
            nextArray[i] = -1;
        }
        freeSpacePointer = 0;
        for (int i = 0; i < reservedWords.length; i++) {
            scanWord(reservedWords[i], true);
        }
    }

    //The method that parses each word for the trie
    //Reserved flag for denoting if a word is reserved or not
    public void scanWord (String word, boolean reserved) {
        if (word.length() == 0) {
            return;
        }
        int wordPtr = 0;
        char symbol = word.charAt(wordPtr);
        int switchIndex = getIntChar(symbol);
        int pointer = switchArray[switchIndex];
        //If the first character in this word has never been read before, then
        //parse the rest of the word into the array; otherwise, continue to the next
        //character in the word
        if (pointer == -1) {
            //Get the location of the first available spot in the array of words
            pointer = freeSpacePointer;
            //Set the first character check array to point to this location
            switchArray[switchIndex] = pointer;
            //Go to the next character and parse in the rest of the word
            for (wordPtr = 1; wordPtr < word.length(); wordPtr++) {
                symbolArray[pointer] = word.charAt(wordPtr);
                pointer++;
            }
            if (reserved) {
                symbolArray[pointer] = '*';
            } else {
                symbolArray[pointer] = '@';
            }
            freeSpacePointer = ++pointer;
        } else {
            //Go to the next character and parse in the rest of the word
            //If the word is one character long, keep the previous one
            if (++wordPtr < word.length()) {
                symbol = word.charAt(wordPtr);
            } else {
                if (reserved) {
                    symbol = '*';
                } else {
                    symbol = '@';
                }
            }
            //While we still have characters in the word left to read
            boolean exit = false;
            while (!exit) {
                //If the next character is in the next position, i.e. has the same
                //prefix as a word before it, and the next character is not the
                //endmarker, advance past it; otherwise, we have read a word we
                //have seen before
                if (symbolArray[pointer] == symbol || (symbolArray[pointer] == '*' && symbol == '@') || (symbolArray[pointer] == '@' && symbol == '*')) {
                    if (symbol != '*' && symbol != '@') {
                        pointer++;
                        if (++wordPtr < word.length()) {
                            symbol = word.charAt(wordPtr);
                        } else {
                            if (reserved) {
                                symbol = '*';
                            } else {
                                symbol = '@';
                            }
                        }
                    } else {
                        //We are done with the word, so we break out of the loop
                        exit = true;
                    }
                //If there is a pointer at this word, follow it
                } else if (nextArray[pointer] != -1) {
                    pointer = nextArray[pointer];
                //Otherwise, we have read up to this prefix and the rest of the word is new
                } else {
                    //Set the pointer at the end of the prefix to point to the location of the rest of the word
                    nextArray[pointer] = freeSpacePointer;
                    //Move the pointer to this new location
                    pointer = freeSpacePointer;
                    //Put the rest of the word in
                    for (; wordPtr < word.length(); wordPtr++) {
                        symbolArray[pointer] = word.charAt(wordPtr);
                        pointer++;
                    }
                    if (reserved) {
                        symbolArray[pointer] = '*';
                    } else {
                        symbolArray[pointer] = '@';
                    }
                    freeSpacePointer = ++pointer;
                    exit = true;
                }
            }
        }
    }

    public int getIntChar (char c) {
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == c) {
                return i;
            }
        }
        return -1;
    }
}
