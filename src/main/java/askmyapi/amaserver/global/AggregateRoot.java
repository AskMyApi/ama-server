package askmyapi.amaserver.global;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AggregateRoot {
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    /**
     * Registers a domain event to the aggregate root.
     */
    protected  void registerEvent(DomainEvent event) {
        domainEvents.add(event);
    }

    /**
     * Returns an unmodifiable list of domain events.
     */
    public List<DomainEvent> consumeDomainEvents() {
        var events = List.copyOf(domainEvents);
        domainEvents.clear(); // Clear the events after consuming them
        return events;
    }
}
