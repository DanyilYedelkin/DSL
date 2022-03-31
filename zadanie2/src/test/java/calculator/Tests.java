package calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class Tests
{
    @Test
    public void testException1()
    {
        int testResult = 1;

        try {
            new Parser("--1 + 5").parse();
        } catch (Exception exception)
        {
            testResult = 0;
        }

        assertEquals(0, testResult);
    }

    @Test
    public void testException2()
    {
        int testResult = 1;

        try {
            new Parser("1++5").parse();
        } catch (Exception exception)
        {
            testResult = 0;
        }

        assertEquals(0, testResult);
    }

    @Test
    public void testException3()
    {
        int testResult = 1;

        try {
            new Parser("1 +- 5").parse();
        } catch (Exception exception)
        {
            testResult = 0;
        }

        assertEquals(0, testResult);
    }

    @Test
    public void testException4()
    {
        int testResult = 1;

        try {
            new Parser("(1 + 5").parse();
        } catch (Exception exception)
        {
            testResult = 0;
        }

        assertEquals(0, testResult);
    }

    @Test
    public void testException5()
    {
        int testResult = 1;

        try {
            new Parser("1 + 5)").parse();
        } catch (Exception exception)
        {
            testResult = 0;
        }

        assertEquals(0, testResult);
    }

    @Test
    public void testException6()
    {
        int testResult = 1;

        try {
            new Parser("5/0").parse();
        } catch (Exception exception)
        {
            testResult = 0;
        }

        assertEquals(0, testResult);
    }

    @Test
    public void test1()
    {
        int result = new Parser("-1     +   5 -2").parse();
        assertEquals(2, result);
    }

    @Test
    public void test2()
    {
        int result = new Parser("1+    3 - 10").parse();
        assertEquals(-6, result);
    }

    @Test
    public void test3()
    {
        int result = new Parser(" ").parse();
        assertEquals(0, result);
    }

    @Test
    public void test4()
    {
        int result = new Parser("-1+1").parse();
        assertEquals(0, result);
    }

    @Test
    public void test18()
    {
        int result = new Parser("-1+1").parse();
        assertEquals(0, result);
    }

    @Test
    public void test17()
    {
        int result = new Parser("(-1)+1").parse();
        assertEquals(0, result);
    }

    @Test
    public void test16()
    {
        int result = new Parser("-2+1").parse();
        assertEquals(-1, result);
    }

    @Test
    public void test15()
    {
        int result = new Parser("(       (16      /(4    *(3-1    )))*30 -(17*(30+10*40))/17)*10/2").parse();
        assertEquals(-1850, result);
    }

    @Test
    public void test5()
    {
        int result = new Parser("-1 + 3 * 5 - 2 * 4").parse();
        assertEquals(6, result);
    }

    @Test
    public void test6()
    {
        int result = new Parser("3/3*(3*3)*3/3 + 3").parse();
        assertEquals(12, result);
    }

    @Test
    public void test7()
    {
        int result = new Parser("(-1 +                                      1)").parse();
        assertEquals(0, result);
    }

    @Test
    public void test8()
    {
        int result = new Parser("(-0 +                                      1)").parse();
        assertEquals(1, result);
    }

    @Test
    public void test9()
    {
        int result = new Parser("(0 *                                      1)").parse();
        assertEquals(0, result);
    }

    @Test
    public void test10()
    {
        int result = new Parser("(5)*(0)-      1").parse();
        assertEquals(-1, result);
    }

    @Test
    public void test11()
    {
        int result = new Parser("(5)*(-1)-      1").parse();
        assertEquals(-6, result);
    }

    @Test
    public void test12()
    {
        int result = new Parser("(5)*(-1)*(-1)").parse();
        assertEquals(5, result);
    }

    @Test
    public void test13()
    {
        int result = new Parser("(5)*(-1)*(-1)*(-1)").parse();
        assertEquals(-5, result);
    }

    @Test
    public void test14()
    {
        int result = new Parser("(5)*(-1)*(-1)*(-1)*(0)*(0) + 55555555").parse();
        assertEquals(55555555, result);
    }
}
