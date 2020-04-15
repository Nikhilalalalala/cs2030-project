package cs2030.simulator;
import java.util.Optional;

/**
 * This class describes the behaviour of the event when a customer leaves
 */
class LeavesEvent extends Event {
    
    /**
     * Constructor of LeaveEvent 
     * The time associated with this event is the arrival time of the customer 
     * as that is the time the customer leaves as well
     * @param customer the customer that leaves
     */
    LeavesEvent(Customer customer) {
        super(customer, Optional.empty(), Event.LEAVES);
        this.time = customer.getArrivalTime();
    }

    /**
     * The customer leaves 
     * @param group the group of Servers handling the event
     * @return an empty event
     */
    @Override
    public Optional<Event> happenEvent(GroupServers group) {
        GroupServers.addTotalLeaves();
        return Optional.empty();
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + 
        this.getCustomerInvolved().getID() + " leaves";
    }
}