/*
 * Author: Santiago Ontañón Villar (Brain Games)
 */
package test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import parser.Tokenizer;

/**
 *
 * @author santi
 */
public class TokenizerTest {

    @Test public void test1() {
        Assert.assertArrayEquals(new String[]{"ld","a",",","2"}, tokenize("ld a,2"));
    }
    @Test public void test2() {
        Assert.assertArrayEquals(new String[]{"ex","af",",","af'"}, tokenize("ex af,af'"));
    }
    @Test public void test3() {
        Assert.assertArrayEquals(new String[]{"ex","af",",","AF'"}, tokenize("ex af,AF'"));
    }
    @Test public void test4() {
        Assert.assertArrayEquals(new String[]{"variable","<<","2"}, tokenize("variable<<2"));
    }
    @Test public void test5() {
        Assert.assertArrayEquals(
                new String[]{"ds","(","(","$","+","1","-","1",")",">>","8",")","!=","(","$",">>","8",")","&&","(","100H","-","(","$","&","0FFH",")",")","||","0"},
                tokenize("ds (($ + 1 - 1) >> 8) != ($ >> 8) && (100H - ($ & 0FFH)) || 0"));
    }
    @Test public void test6() {
        Assert.assertArrayEquals(new String[]{".include"}, tokenize(".include"));
    }
    @Test public void test7() {
        Assert.assertArrayEquals(new String[]{"ld","a",",","(","hl",")"}, tokenize("ld a,(hl)"));
    }
    @Test public void test8() {
        Assert.assertArrayEquals(new String[]{"ld","a",",","[","hl","]"}, tokenize("ld a,[hl]"));
    }
    @Test public void test9() {
        Assert.assertArrayEquals(new String[]{"@@my_local_label",":"}, tokenize("@@my_local_label:"));
    }

    private static String[] tokenize(String line)
    {
        List<String> tokens = Tokenizer.tokenize(line);
        return tokens != null
                ? tokens.toArray(new String[0])
                : null;
    }
}