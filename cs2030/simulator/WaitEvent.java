package cs2030.simulator;

import java.util.Optional;

/** This class describes the behaviour of the event when a customer waits at a server.
 */
class WaitEvent extends Event {

    /**
     * Constructor of WaitEvent where the customer is modelled to be waiting for the
     * server to be done with its previous customer.
     * 
     * @param customer The customer that waits
     * @param server   the server going to serve the customer
     * @param time     the time the customer starts waiting, which is also their
     *                 arrival time
     */
    WaitEvent(Customer customer, Optional<Server> server, double time) {
        super(customer, server);
        this.time = time;
        this.isDiscarded = false;
    }

    /**
     * The customer waits for the server to finish serving the previous customer And
     * is served immediately after which results in it returning a new ServeEvent.
     * 
     * @param group the group of Servers handling the event
     * @return the Serve Event where the customer is served
     */
    @Override
    public Optional<Event> happenEvent(GroupServers group) {

        System.out.println(this);
        Event newEvent;
        this.server.get().addCustomerToQueue();

        if (this.server.filter(x -> x.isSelfCheck()).isPresent()) {

            Optional<Server> earliestServer = group.findEarliestSelfCheckServer(this.server.get());
            newEvent = new ServedEvent(this.getCustomerInvolved(), earliestServer, this.time, true);
            
        } else {
            newEvent = new ServedEvent(this.getCustomerInvolved(), this.getServer(), 
                this.time, true);
        }

        return Optional.of(newEvent);
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + this.getCustomerInvolved() + 
            " waits to be served by " + this.getServer().get().toString();
    }

}