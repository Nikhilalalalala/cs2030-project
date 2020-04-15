package cs2030.simulator;
import java.util.Optional;

/**
 * This class describes the behaviour of the event when a customer is served by a server
 */
class ServedEvent extends Event {
    
    /**
     * Constructor of ServeEvent where the customer is modelled to be 
     * served by the server 
     * @param customer The customer that is being served
     * @param server the server serving the customer
     * @param time the time the customer starts being served
     */
    ServedEvent(Customer customer, Optional<Server> server, double time) {
        super(customer, server, Event.SERVED);
        this.time = time;
    }

    /**
     * The customer is served 
     * @param group the group of Servers handling the event 
     * @return the Done Event where the customer is done serving
     */
    public Optional<Event> happenEvent(GroupServers group) {
        if (this.getServer().get().getHaveWaitingCustomer()) {
            //the customer getting served is someone who has waited;
            this.getServer().get().setWaitingCustomer(false); 
        } 
        double timeDone = this.time + Event.DURATION;
        double timetoAdd = timeDone - this.getServer().get().getNextServiceTime();
        this.server.ifPresent( x -> x.setNextServiceTime(timetoAdd));
        Event newEvent = new DoneEvent(this.getCustomerInvolved(), this.getServer(), timeDone);
        return Optional.of(newEvent);
    }


    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + 
        this.getCustomerInvolved().getID() + " served by " +
        this.getServer().get().getServerID();
    }


}