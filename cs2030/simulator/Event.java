package cs2030.simulator;

import java.util.Optional;

/** Event class models the behaviour of events events have a customer, server, the time.
 */
public abstract class Event implements Comparable<Event> {
    protected Customer customerInvolved;
    protected Optional<Server> server;
    protected double time;
    protected boolean isDiscarded;

    /** Constructor of Event where it minimally takes in customer, server.
     * 
     * @param customer   customer being served
     * @param server     server serving the customer
     */
    Event(Customer customer, Optional<Server> server) {
        this.customerInvolved = customer;
        this.server = server;
    }

    /** Determines if the event was valid or not.
     * 
     * @return boolean value of whether the event is valid or not
     */
    public boolean validEvent() {
        return !this.isDiscarded;
    }

    /**
     * This method describes what happens to the various attributes involved in the event.
     * 
     * @param group the group of servers employed
     * @return the event that follows this event
     */
    abstract Optional<Event> happenEvent(GroupServers group);

    public Customer getCustomerInvolved() {
        return this.customerInvolved;
    }

    public Optional<Server> getServer() {
        return this.server;
    }

    /** Compares two events first based on the time of the event and then by the customers.
     * 
     */
    @Override
    public int compareTo(Event e) {
        double difference = this.time - e.time;
        if (difference == 0) {
            return this.getCustomerInvolved().compareTo(e.getCustomerInvolved());
        } else {
            if (difference > 0) {
                return 1;
            } else if (difference < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    }

}