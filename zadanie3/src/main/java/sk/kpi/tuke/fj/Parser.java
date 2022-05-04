package sk.kpi.tuke.fj;

import java.io.IOException;
import java.util.Set;

import static sk.kpi.tuke.fj.TokenType.*;

/**
 * Parser for the state machine language
 *
 * StateMachine -> { Statement }
 * Statement    -> Events | ResetEvents | Commands | State
 * Events       -> "events" "{" { NAME CHAR } "}"
 * Commands     -> "commands" "{" { NAME CHAR } "}"
 * ResetEvents  -> "resetEvents" "{" { NAME } "}"
 * State        -> "state" "{" [Actions] { Transition } "}"
 * Actions      -> "actions" "{" { NAME } "}"
 * Transition   -> NAME "->" NAME
 */
public class Parser {
    private final Lexer lexer;
    private Token symbol;
    private StateMachineDefinition definition;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }


    public StateMachineDefinition stateMachine() {
        definition = new StateMachineDefinition();
        var first = Set.of(EVENTS, COMMANDS, RESET_EVENTS, STATE);
        consume();
        while (first.contains(symbol.type())) {
            switch (symbol.type()) {
                case EVENTS -> events();
                case COMMANDS -> commands();
                case RESET_EVENTS -> resetEvents();
                case STATE -> state();
            }
        }

        definition.getStates().forEach((K, V) -> {
            V.getActions().forEach(sd -> {
                Character command = definition.getCommands().get(sd);

                if (command == null) {
                    throw new StateMachineException("Command \"" + sd + "\" is not defined in \"commands\" scope!");
                }
            });

            V.getTransitions().forEach(t -> {
                Character event = definition.getEvents().get(t.eventName());

                if (event == null) {
                    throw new StateMachineException("Event \"" + t.eventName() + "\" is not defined in \"events\" scope!");
                }
            });
        });

        definition.getResetEvents().forEach(r -> {
            if (!definition.getEvents().containsKey(r)) {
                throw new StateMachineException("Reset event \"" + r + "\" is not defined in \"events\" scope!");
            }
        });

        return definition;
    }

    private void events() {
        match(EVENTS);
        match(LBRACE);
        while (symbol.type() == NAME) {
            var name = symbol.attribute();
            consume();
            var value = symbol.attribute();
            match(CHAR);
            definition.addEvent(name, value.charAt(0));
        }
        match(RBRACE);
    }

    private void commands() {
        // TODO your code goes here

        match(COMMANDS);
        match(LBRACE);
        while (symbol.type() == NAME) {
            var name = symbol.attribute();
            consume();
            var value = symbol.attribute();
            match(CHAR);
            definition.addCommand(name, value.charAt(0));
        }
        match(RBRACE);
    }

    private void resetEvents() {
        // TODO your code goes here

        match(RESET_EVENTS);
        match(LBRACE);
        while (symbol.type() == NAME) {
            var name = symbol.attribute();
            consume();
            definition.addResetEvent(name);
        }
        match(RBRACE);
    }

    private void state() {
        // TODO your code goes here
        StateDefinition newDefinition = new StateDefinition();
        match(STATE);

        String newStateName = symbol.attribute();
        consume();
        match(LBRACE);

        if (symbol.type() == ACTIONS) {
            checkingSymbols(newDefinition);
        }

        addTransitionForDefinition(newDefinition);

        match(RBRACE);
        definition.addState(newStateName, newDefinition);
    }

    private void checkingSymbols(StateDefinition newDefinition) {
        consume();
        match(LBRACE);
        while (symbol.type() == NAME) {
            String name = symbol.attribute();
            newDefinition.addAction(name);
            consume();
        }
        match(RBRACE);
    }
    private void addTransitionForDefinition(StateDefinition newDefinition) {
        while (symbol.type() == NAME) {
            newDefinition.addTransition(transition());
        }
    }

    private TransitionDefinition transition() {
        // TODO your code goes here
        String eventName = symbol.attribute();
        match(NAME);
        match(ARROW);

        String targetName = symbol.attribute();
        match(NAME);

        return new TransitionDefinition(eventName, targetName);
    }

    private void match(TokenType expectedSymbol) {
        // TODO your code goes here
        if (symbol.type().equals(expectedSymbol)) {
            consume();
        } else {
            throw new StateMachineException("Match error in token: " + symbol.type() + ", but excepted: " + expectedSymbol);
        }
    }

    private void consume() {
        // TODO your code goes here

        symbol = lexer.nextToken();
    }
}