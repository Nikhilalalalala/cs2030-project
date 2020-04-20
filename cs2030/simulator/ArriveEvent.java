package cs2030.simulator;

import java.util.Optional;

/**
 * This class describes the behaviour of the event when a customer arrives
 */
public class ArriveEvent extends Event {
    /**
     * Constructor of ArriveEvent The time associated with this event is the arrival
     * time of the customer
     * 
     * @param customer The customer that arrives
     */
    ArriveEvent(Customer customer) {
        super(customer, Optional.empty());
        this.time = this.getCustomerInvolved().getArrivalTime();
        this.isDiscarded = false; // no arriveevents are ever discarded
    }

    /**
     * If there are any vacant server, the a new ServeEvent would be created with
     * this customer and the available server, else it goes on to find any server
     * that has no waiting people and would create a WaitEvent accordingly. If no
     * server is found, it would create a LeaveEvent
     * 
     * @param group the group of Servers handling the event
     * @return the next event depending if the customer waits, is served, or leaves
     */
    @Override
    public Optional<Event> happenEvent(GroupServers group) {
        System.out.println(this);

        Event newEvent;
        double arrivalTime = this.getCustomerInvolved().getArrivalTime();
        Optional<Server> server = group.findNextAvailableServerToServe(arrivalTime);

        if (server.isPresent()) {

            newEvent = new ServedEvent(this.getCustomerInvolved(), server, arrivalTime, false);

        } else {
            // find server for customer to wait at
            Optional<Server> waitAt;
            if (customerInvolved.isGreedy()) {
                waitAt = group.findShortestQueueServer();
            } else {
                waitAt = group.findNextAvailableServerToWait();
            }

            // allocate the next event
            if (waitAt.isPresent()) {
                newEvent = new WaitEvent(this.getCustomerInvolved(), waitAt, arrivalTime);
            } else {
                newEvent = new LeavesEvent(this.getCustomerInvolved());
            }
        }

        return Optional.of(newEvent);
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + this.getCustomerInvolved() + " arrives";
    }

}