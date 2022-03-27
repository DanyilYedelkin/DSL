package calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Tests
{
    @Test
    public void test1()
    {
        int result = (int) new Parser("-1     +   5 -2").parse();
        assertEquals(2, result);
    }

    @Test
    public void test2()
    {
        int result = (int) new Parser("1+    3 - 10").parse();
        assertEquals(-6, result);
    }

    @Test
    public void test3()
    {
        int result = (int) new Parser(" ").parse();
        assertEquals(0, result);
    }

    @Test
    public void test4()
    {
        int result = (int) new Parser("-1+1").parse();
        assertEquals(0, result);
    }

    @Test
    public void test18()
    {
        int result = (int) new Parser("-1+1").parse();
        assertEquals(0, result);
    }

    @Test
    public void test17()
    {
        int result = (int) new Parser("(-1)+1").parse();
        assertEquals(0, result);
    }

    @Test
    public void test16()
    {
        int result = (int) new Parser("-2+1").parse();
        assertEquals(-1, result);
    }

    @Test
    public void test15()
    {
        int result = (int) new Parser("(       (16      /(4    *(3-1    )))*30 -(17*(30+10*40))/17)*10/2").parse();
        assertEquals(-1850, result);
    }

    @Test
    public void test5()
    {
        int result = (int) new Parser("-1 + 3 * 5 - 2 * 4").parse();
        assertEquals(6, result);
    }

    @Test
    public void test6()
    {
        int result = (int) new Parser("3/3*(3*3)*3/3 + 3").parse();
        assertEquals(12, result);
    }

    @Test
    public void test7()
    {
        int result = (int) new Parser("(-1 +                                      1)").parse();
        assertEquals(0, result);
    }

    @Test
    public void test8()
    {
        int result = (int) new Parser("(-0 +                                      1)").parse();
        assertEquals(1, result);
    }

    @Test
    public void test9()
    {
        int result = (int) new Parser("(0 *                                      1)").parse();
        assertEquals(0, result);
    }

    @Test
    public void test10()
    {
        int result = (int) new Parser("(5)*(0)-      1").parse();
        assertEquals(-1, result);
    }

    @Test
    public void test11()
    {
        int result = (int) new Parser("(5)*(-1)-      1").parse();
        assertEquals(-6, result);
    }

    @Test
    public void test12()
    {
        int result = (int) new Parser("(5)*(-1)*(-1)").parse();
        assertEquals(5, result);
    }

    @Test
    public void test13()
    {
        int result = (int) new Parser("(5)*(-1)*(-1)*(-1)").parse();
        assertEquals(-5, result);
    }

    @Test
    public void test14()
    {
        int result = (int) new Parser("(5)*(-1)*(-1)*(-1)*(0)*(0) + 55555555").parse();
        assertEquals(55555555, result);
    }
}