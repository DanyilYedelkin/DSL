package sk.kpi.tuke.fj;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {

        // TODO your code goes here
        // produce stm.c file that is able to simulate simple state machine

        try{
            String path = new File(".").getCanonicalPath() + "/src/main/resources/";
            FileReader r = new FileReader(path + "dsl.txt");
            BufferedReader reader = new BufferedReader(r);
            Lexer lexer = new Lexer(reader);
            Parser parser = new Parser(lexer);
            PrintWriter writer = new PrintWriter(path + "out/stm.c");
            Generator generator = new Generator(parser.stateMachine(), writer);
            generator.generate();
            writer.close();
            reader.close();
            r.close();
        } catch(StateMachineException e) {
            e.printStackTrace();
        }

    }
}