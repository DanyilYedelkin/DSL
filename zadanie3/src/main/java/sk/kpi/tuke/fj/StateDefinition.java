package sk.kpi.tuke.fj;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StateDefinition {
    private final List<String> actions = new ArrayList<>();
    private final List<TransitionDefinition> transitions = new ArrayList<>();

    public void addAction(String name) {
        actions.add(name);
    }

    public void addTransition(TransitionDefinition transition) {

        transitions.add(transition);
        Set<TransitionDefinition> t = new HashSet<>(transitions);

        if (transitions.size() != t.size()) {
            throw new StateMachineException("Duplicate name in state definition! Duplicate name: " + transition.eventName());
        }
    }

    public List<String> getActions() {
        return actions;
    }

    public List<TransitionDefinition> getTransitions() {
        return transitions;
    }

    @Override
    public String toString() {
        return "StateDefinition{" +
                "actions=" + actions +
                ", transitions=" + transitions +
                '}';
    }
}
