package com.example.amplero;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ngram.NGramTokenizer;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;


public class Scrambler {

  public static final String not = " is not a scramble of ";
  public static final String poor = " is a poor scramble of ";
  public static final String fair = " is a fair scramble of ";
  public static final String hard = " is a hard scramble of ";

  // pre-sorted arrays
  public static String[] allowed = {"AI","AY","BB","BL","BR","CC","CH","CK","CL","CR","DD","DR","EA","EE","EO","FF","FL"
    ,"FR","GG","GH","GL","GR","HH","IO","JJ","KK","KL","KR","KW","LL","MM","NN","OA","OO","OY","PF","PL","PP","PR","QQ",
    "RR","SC","SCH","SCR","SH","SHR","SK","SL","SM","SN","SP","SQ","SS","ST","SW","TH","THR","TR","TT","TW","VV","WH",
    "WR","WW","XX","YA","YO","YU","ZZ"};

  public static char[] vowels = {'A', 'E', 'I', 'O', 'U', 'Y'};

  public static void main(String[] args) {
    if (args.length != 2 || args[0].length() != args[1].length() || args[0].equals("")) {
      usage();
      throw new IllegalArgumentException("Please provide only two Strings of the same length");
    }

    char[] scramble = args[0].toUpperCase().toCharArray();
    char[] word = args[1].toUpperCase().toCharArray();

    if (!isLetterString(scramble) || !isLetterString(word)) {
      usage();
      throw new IllegalArgumentException("Please provide only Strings with letters");
    }

    if (!sameChars(scramble, word)) {
      usage();
      throw new IllegalArgumentException("Please provide two Strings with the same characters");
    }

    if (isNot(args[0], args[1])) {
      System.out.println(buildExitString("not", args[0], args[1]));
      System.exit(0);
    }

    if (isPoor(scramble, word)) {
      System.out.println(buildExitString("poor", args[0], args[1]));
      System.exit(0);
    }

    if (isHard(scramble, word)) {
      System.out.println(buildExitString("hard", args[0], args[1]));
      System.exit(0);
    }

    System.out.println(buildExitString("fair", args[0], args[1]));
    System.exit(0);
  }

  /**
    Returns a boolean based on whether the character arrays contain exactly the same characters
   @param c1 a character array
   @param c2 a character array
   @return returns true if both arrays contain exactly the same characters.
   */

  static boolean sameChars(char[] c1, char[] c2) {
    char[] copy1 = Arrays.copyOf(c1, c1.length);
    char[] copy2 = Arrays.copyOf(c2, c2.length);
    Arrays.sort(copy1);
    Arrays.sort(copy2);
    return (Arrays.equals(copy1, copy2));
  }

  static boolean isNot(String s1, String s2) {
    return (s1.equals(s2));
  }

  static boolean isPoor(char[] c1, char[] c2) {
    if (c1[0] == c2[0] && !looksReal(c1)) {
      return true;
    }
    boolean found = false;
    for (int i = 0; i < c1.length - 1; i++) {
      char c1Place = c1[i];
      char c2Place = c2[i];
      char c1next = c1[i + 1];
      char c2next = c2[i + 1];
      if (c1Place == c2Place && c1next == c2next && !looksReal(c1)) {
        return true;
      }
    }
    return false;
  }

  static boolean isHard(char[] c1, char[] c2) {
    for (int i = 0; i < c1.length; i++) {
      char c1Place = c1[i];
      char c2Place = c2[i];
      if (c1Place == c2Place) {
        return false;
      }
    }
    if (looksReal(c1)) return true;
    else return false;
  }

  static String buildExitString(String exit, String scramble, String word) {
    String buildMe = "";
    switch (exit) {
      case "not":
        buildMe = scramble + not + word;
        break;
      case "poor":
        buildMe = scramble + poor + word;
        break;
      case "fair":
        buildMe = scramble + fair + word;
        break;
      case "hard":
        buildMe = scramble + hard + word;
        break;
    }
    if (buildMe.equals("")) throw new IllegalStateException("exit String must be: not / poor / fair / hard");
    return buildMe;
  }

  /**
    Returns true if the given character is a vowel
    @param vowels a character array of vowels
    @param c the character in question
    @return True if the character c is in the supplied character array, false if otherwise
   */

  static boolean isVowel(char[] vowels, char c) {
    if (Arrays.binarySearch(vowels, c) >= 0) {
      return true;
    } else {
      return false;
    }
  }

  /**
  This method contains a loop used to generate "bi-gram" and "tri-gram" strings and check them against the the allowed
   String array, via Arrays.binarySearch . If a bi-gram or tri-gram is found, we increment the loop counter, named
   "offset" to consider the next character in the array.

  The next code block underneath the String generation section tests for alternating vowel and consonants between
  characters, starting at the current "offset". If we recall above, the "offset" was incremented additionally inside the
   loop for "bi-grams" and "tri-grams".

   @param scramble the scrambled word as a character array
   @return true if the word "looks real" , false if otherwise
   */

  static boolean looksReal(char[] scramble) {
    for (int offset = 0; offset < scramble.length - 1; offset++) {
      int remainder = scramble.length - offset;
      String biGram = new String(scramble, offset, 2);
      if (2 <= remainder && Arrays.binarySearch(allowed, biGram) >= 0) {
        offset = offset + 1;
        if (3 <= remainder) {
          String triGram = new String(scramble, offset - 1, 3);
          if (Arrays.binarySearch(allowed, triGram) >= 0) {
            offset = offset + 1;
          }
        }
      }

      if (scramble.length - offset >= 2) {
        char place = scramble[offset];
        char next = scramble[offset + 1];
        boolean placeVowel = isVowel(vowels, place);
        boolean nextVowel = isVowel(vowels, next);
        if (placeVowel && nextVowel || !placeVowel && !nextVowel) {
          return false;
        }
      }
    }
    return true;
  }

  static void usage() {
    System.out.println("java Scrambler 'scrambled_word' 'word'");
  }

  static boolean isLetterString(char[] c1) {
    for (char c : c1) {
      if (!Character.isLetter(c)) {
        return false;
      }
    }
    return true;
  }

  static boolean ngramLooksReal(String scrambled) {
    //StringReader reader = new StringReader(scrambled);
    Analyzer analyzer = new Analyzer() {
      protected TokenStreamComponents createComponents (String str){
        NGramTokenizer ngt = new NGramTokenizer(2, 3);
        return new TokenStreamComponents(ngt);
      }
    };
    TokenStream tost = analyzer.tokenStream("defField",scrambled);
    OffsetAttribute offsetAtt = tost.addAttribute(OffsetAttribute.class);

    try {
      tost.reset();
      while (tost.incrementToken()) {
        System.out.println("token: " + tost.reflectAsString(true));
        System.out.println("token start offset: " + offsetAtt.startOffset());
        System.out.println(" token end offset: " + offsetAtt.endOffset());
      }
      tost.end();
    }
    catch(IOException ioe ){System.out.println("io exception:"+ioe.getMessage());}
    finally {
      // example not with try / catch block in finally
      try {
        tost.close();
      }
      catch(IOException ioe ){System.out.println("io exception:"+ioe.getMessage());}
    }
    return true;
  }
}
