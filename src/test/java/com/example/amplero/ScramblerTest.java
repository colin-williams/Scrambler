package com.example.amplero;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Arrays;

/**
 * Unit test for simple App.
 */
public class ScramblerTest
  extends TestCase {
  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public ScramblerTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(ScramblerTest.class);
  }

  /**
   * Rigourous Test :-)
   */
  public void testApp() {
    assertTrue(true);
  }

  public void testSameChars() {
    char[] c1 = {'h', 't'};
    char[] c2 = {'t', 'h'};
    assertTrue(Scrambler.sameChars(c1, c2));

    char[] c3 = {'t', 'r'};
    char[] c4 = {'t', 'h'};
    assertFalse(Scrambler.sameChars(c3, c4));
  }

  public void testIsNot() {
    assertTrue(Scrambler.isNot("IRONY", "IRONY"));
    assertEquals("IRONY is not a scramble of IRONY", Scrambler.buildExitString("not", "IRONY", "IRONY"));
    assertFalse(Scrambler.isNot("IRONY", "IORNY"));
    assertNotSame("IRONY is not a scramble of IRONY", Scrambler.buildExitString("not", "IRONY", "IORNY"));
  }

  public void testLooksReal() {
    assertTrue(Scrambler.looksReal("A".toCharArray()));
    assertFalse(Scrambler.looksReal("AA".toCharArray()));
    assertTrue(Scrambler.looksReal("ZA".toCharArray()));
    assertFalse(Scrambler.looksReal("ZF".toCharArray()));
    assertTrue(Scrambler.looksReal("AIT".toCharArray()));
    assertTrue(Scrambler.looksReal("SCHOOL".toCharArray()));
    assertTrue(Scrambler.looksReal("SCHOOLY".toCharArray()));
    assertFalse(Scrambler.looksReal("SCHOOLP".toCharArray()));
    assertFalse(Scrambler.looksReal("SCHMOO".toCharArray()));
    assertTrue(Scrambler.looksReal("SCHOOLL".toCharArray()));
    assertTrue(Scrambler.looksReal("IOL".toCharArray()));
    assertFalse(Scrambler.looksReal("IOY".toCharArray()));
    assertTrue(Scrambler.looksReal("IOCKY".toCharArray()));
    assertFalse(Scrambler.looksReal("IOCKG".toCharArray()));
    assertTrue(Scrambler.looksReal("ONYRI".toCharArray()));
    assertFalse(Scrambler.looksReal("OOOO".toCharArray()));
    assertFalse(Scrambler.looksReal("OOA".toCharArray()));
    assertFalse(Scrambler.looksReal("AOO".toCharArray()));
    assertFalse(Scrambler.looksReal("OOAOO".toCharArray()));
    assertFalse(Scrambler.looksReal("LLLONG".toCharArray()));
    assertFalse(Scrambler.looksReal("DROOLLL".toCharArray()));
    assertTrue(Scrambler.looksReal("BOSCH".toCharArray()));
    assertTrue(Scrambler.looksReal("BOSCHA".toCharArray()));
    assertTrue(Scrambler.looksReal("LLA".toCharArray()));
    assertTrue(Scrambler.looksReal("ZZAP".toCharArray()));
  }

  public void testIsPoor() {
    assertTrue(Scrambler.isPoor("IOYRN".toCharArray(), "IRONY".toCharArray()));
    assertEquals("IOYRN is a poor scramble of IRONY", Scrambler.buildExitString("poor", "IOYRN", "IRONY"));
    assertTrue(Scrambler.isPoor("SCHOOLP".toCharArray(), "SCHLOOP".toCharArray()));
    assertTrue(Scrambler.isPoor("IBRD".toCharArray(), "BIRD".toCharArray()));
  }

  public void testIsHard() {
    assertEquals("ONYRI is a hard scramble of IRONY", Scrambler.buildExitString("hard", "ONYRI", "IRONY"));
    assertTrue(Scrambler.isHard("ONYRI".toCharArray(), "IRONY".toCharArray()));
  }

  public void testIsFair() {
    assertEquals("MAPS is a fair scramble of SPAM", Scrambler.buildExitString("fair", "MAPS", "SPAM"));
    assertTrue(isFair("MAPS", "SPAM"));
    assertTrue(isFair("RIONY", "IRONY"));
    assertTrue(isFair("INOYR", "IRONY"));
  }

  public boolean isFair(String s1, String s2) {
    boolean not = Scrambler.isNot(s1, s2);
    char[] c1 = s1.toCharArray();
    char[] c2 = s2.toCharArray();
    return (!not && !Scrambler.isPoor(c1, c2) && !Scrambler.isHard(c1, c2));
  }
  public void testNgramLooksReal(){
    Scrambler.ngramLooksReal("tastytaste");
  }
}
