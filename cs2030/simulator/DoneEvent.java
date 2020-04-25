package cs2030.simulator;

import java.util.Optional;

/**
 * This class describes the behaviour of the event when a customer is done being served.
 */
class DoneEvent extends Event {

    /**
     * Constructor of DoneEvent where the server is modelled to be done serving the customer.
     * 
     * @param customer the customer that is done being served
     * @param server   the server that finished serving the customer
     * @param time     the time which the service is done
     */
    DoneEvent(Customer customer, Optional<Server> server, double time) {
        super(customer, server);
        this.time = time;
        this.isDiscarded = false; // no DoneEvents are ever discarded
    }

    /** The customer leaves after a service and the decision is made if a server gets to rest.
     * 
     * @param group the group of Servers handling the event
     * @return an empty event
     */
    @Override
    public Optional<Event> happenEvent(GroupServers group) {

        System.out.println(this);

        Statistics.addTotalServed();
        // determining if the server needs rest
        if (!server.get().isSelfCheck() && group.deservesRest()) {
            double restingDuration = group.createRestingDuration();
            server.ifPresent(x -> x.setNextServiceTime(restingDuration));
        }

        return Optional.empty();
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + this.getCustomerInvolved() + 
            " done serving by " + this.getServer().get().toString();
    }
}