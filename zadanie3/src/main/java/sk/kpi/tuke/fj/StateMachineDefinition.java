package sk.kpi.tuke.fj;

import java.util.*;

public class StateMachineDefinition {
    private final Map<String, Character> events = new HashMap<>();
    private final Map<String, Character> commands = new HashMap<>();
    private final List<String> resetEvents = new ArrayList<>();
    private final Map<String, StateDefinition> states = new LinkedHashMap<>();
    private String initialStateName = null;

    public void addEvent(String name, char value) {
        if (events.containsKey(name)) {
            throw new StateMachineException("Duplicate parameters in event key value! Error: " + name);
        }

        if (events.containsValue(value)) {
            throw new StateMachineException("Duplicate value in events! Duplicate value: " + value);
        }

        events.put(name, value);
    }

    public void addCommand(String name, char value) {
        if (commands.containsKey(name)) {
            throw new StateMachineException("Duplicate parameters in command key value! Error: " + name);
        }

        commands.put(name, value);
    }

    public void addState(String name, StateDefinition state) {

        if (states.containsKey(name)) {
            throw new StateMachineException("Duplicate parameters in state key value! Error: " + name);
        } else if (states.containsValue(state)) {
            throw new StateMachineException("Duplicate value in states! Duplicate value: " + state.toString());
        }

        states.put(name, state);
        if (initialStateName == null) {
            initialStateName = name;
        }
    }

    public void addResetEvent(String name) {
        if (resetEvents.contains(name)) {
            throw new StateMachineException("Duplicate parameters in reset state key value! Error: " + name);
        }

        resetEvents.add(name);
    }

    public String getInitialStateName() {
        return initialStateName;
    }

    public Map<String, Character> getEvents() {
        return events;
    }

    public Map<String, Character> getCommands() {
        return commands;
    }

    public List<String> getResetEvents() {
        return resetEvents;
    }

    public Map<String, StateDefinition> getStates() {
        return states;
    }
}
