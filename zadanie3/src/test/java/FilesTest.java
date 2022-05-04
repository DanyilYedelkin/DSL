import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sk.kpi.tuke.fj.Generator;
import sk.kpi.tuke.fj.Lexer;
import sk.kpi.tuke.fj.Parser;
import sk.kpi.tuke.fj.StateMachineException;

import java.io.*;

public class FilesTest {

    String path = "";
    String controllersPath = "";
    File out = null;

    @Before
    public void init() {
        try {
            path = new File(".").getCanonicalPath() + "/src/test/resources/";
            controllersPath = path + "dsls/";
            out = new File(path + "out/out.c");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test1() {
        int testNum = 1;
        File controller = new File(controllersPath + "dsl" + testNum +".txt");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(controller));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(out))
        ) {
            Lexer lexer = new Lexer(bufferedReader);
            new Generator(new Parser(lexer).stateMachine(), bufferedWriter).generate();
        } catch (StateMachineException | IOException e) {
            e.printStackTrace();
        }

        File testFile = new File(path + "test" + testNum + ".c");
        try {
            Assert.assertTrue(FileUtils.contentEquals(out, testFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // test if we have without reset statement
    @Test
    public void test2() {
        int testNum = 5;
        File controller = new File(controllersPath + "dsl" + testNum +".txt");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(controller));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(out))
        ) {
            Lexer l = new Lexer(bufferedReader);
            new Generator(new Parser(l).stateMachine(), bufferedWriter).generate();
        } catch (StateMachineException | IOException e) {
            e.printStackTrace();
        }

        File testFile = new File(path + "test" + testNum + ".c");
        try {
            Assert.assertTrue(FileUtils.contentEquals(out, testFile));
        } catch (StateMachineException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    // test if we have 2 arrows in state
    @Test(expected = RuntimeException.class)
    public void test3() {
        int testNum = 3;
        File controller = new File(controllersPath + "dsl" + testNum +".txt");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(controller));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(out))
        ) {
            Lexer lexer = new Lexer(bufferedReader);
            new Generator(new Parser(lexer).stateMachine(), bufferedWriter).generate();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File testFile = new File(path + "test" + testNum + ".c");
        try {
            Assert.assertTrue(FileUtils.contentEquals(out, testFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // test if we have duplicate parameters
    @Test(expected = RuntimeException.class)
    public void test4() {
        int testNum = 4;
        File controller = new File(controllersPath + "dsl" + testNum +".txt");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(controller));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(out))
        ) {
            Lexer lexer = new Lexer(bufferedReader);
            new Generator(new Parser(lexer).stateMachine(), bufferedWriter).generate();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File testFile = new File(path + "test" + testNum + ".c");
        try {
            Assert.assertTrue(FileUtils.contentEquals(out, testFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // test if we have event parameter not defined
    @Test(expected = RuntimeException.class)
    public void test5() {
        int testNum = 2;
        File controller = new File(controllersPath + "dsl" + testNum +".txt");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(controller));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(out))
        ) {
            Lexer lexer = new Lexer(bufferedReader);
            new Generator(new Parser(lexer).stateMachine(), bufferedWriter).generate();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File testFile = new File(path + "test" + testNum + ".c");
        try {
            Assert.assertTrue(FileUtils.contentEquals(out, testFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // test if we have event parameter not defined
    @Test(expected = RuntimeException.class)
    public void test6() {
        int testNum = 6;
        File controller = new File(controllersPath + "dsl" + testNum +".txt");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(controller));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(out))
        ) {
            Lexer lexer = new Lexer(bufferedReader);
            new Generator(new Parser(lexer).stateMachine(), bufferedWriter).generate();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File testFile = new File(path + "test" + testNum + ".c");
        try {
            Assert.assertTrue(FileUtils.contentEquals(out, testFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // test if we haven't anything
    @Test(expected = RuntimeException.class)
    public void test7() {
        int testNum = 7;
        File controller = new File(controllersPath + "dsl" + testNum +".txt");

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(controller));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(out))
        ) {
            Lexer lexer = new Lexer(bufferedReader);
            new Generator(new Parser(lexer).stateMachine(), bufferedWriter).generate();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File testFile = new File(path + "test" + testNum + ".c");
        try {
            Assert.assertTrue(FileUtils.contentEquals(out, testFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}


/*
if (!controller.exists()) {
            throw new RuntimeException("dsl file does not exist!");
        }
* */
