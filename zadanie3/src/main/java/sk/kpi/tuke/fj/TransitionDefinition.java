package sk.kpi.tuke.fj;

public record TransitionDefinition(String eventName, String targetName) {

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof TransitionDefinition t)) {
            return false;
        }

        return this.eventName.equals(t.eventName);
    }

    @Override
    public int hashCode() {
        return eventName.hashCode();
    }
}