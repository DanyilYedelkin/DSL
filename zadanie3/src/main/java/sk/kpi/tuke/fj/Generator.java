package sk.kpi.tuke.fj;

import java.io.IOException;
import java.io.Writer;

public class Generator {
    private final StateMachineDefinition stateMachine;
    private final Writer writer;

    public Generator(StateMachineDefinition stateMachine, Writer writer) {
        this.stateMachine = stateMachine;
        this.writer = writer;
    }

    private void stateUsage() {
        stateMachine.getStates().forEach((number, action) -> {
            try {
                writer.write("void state_" + number + "();\n");
            } catch (IOException e) {
                throw new StateMachineException("Error in get states!");
            }
        });
    }

    public void generate() throws IOException {
        // TODO your code goes here

        writer.write("#include \"common.h\"\n\n");
        stateUsage();
        writer.write("\n");
        stateMachine.getStates().forEach((K, V) -> {
            try {
                writer.write("void state_" + K + "() {\n");

                // Actions
                V.getActions().forEach(sd -> {
                    try {

                        Character command = stateMachine.getCommands().get(sd);

                        writer.write("\tsend_command('" + command + "');\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });


                if (!V.getTransitions().isEmpty() || !stateMachine.getResetEvents().isEmpty()) {
                    writer.write("\tchar ev;\n\twhile (ev = read_event()) {\n");
                    writer.write("\t\tswitch (ev) {\n");
                }

                // Transactions
                V.getTransitions().forEach(t -> {
                    try {

                        Character event = stateMachine.getEvents().get(t.eventName());

                        writer.write("\t\tcase '" + event  + "':\n");
                        writer.write("\t\t\treturn state_" + t.targetName() + "();\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                // Reset events
                stateMachine.getResetEvents().forEach(r -> {
                    try {
                        writer.write("\t\tcase '" + stateMachine.getEvents().get(r) + "':\n");
                        writer.write("\t\t\treturn state_" + stateMachine.getInitialStateName() + "();\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                if (!V.getTransitions().isEmpty() || !stateMachine.getResetEvents().isEmpty()) {
                    writer.write("\t\t}\n\t}\n");
                }

                writer.write("}\n\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        //Main
        writer.write("void main() {\n");
        if (stateMachine.getInitialStateName() != null) {
            writer.write("\tstate_" + stateMachine.getInitialStateName() + "();\n");
        }
        writer.write("}");
    }

    private void writeState(String name, StateDefinition state) throws IOException {
        // TODO your code goes here
    }
}
