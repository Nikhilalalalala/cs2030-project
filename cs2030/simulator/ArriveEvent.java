package cs2030.simulator;
import java.util.Optional;

/**
 * This class describes the behaviour of the event when a customer arrives
 */
public class ArriveEvent extends Event {
    /**
     * Constructor of ArriveEvent 
     * The time associated with this event is the arrival time of the customer
     * @param customer The customer that arrives
     */
    ArriveEvent(Customer customer) {
        super(customer, Optional.empty(), Event.ARRIVES);
        this.time = this.getCustomerInvolved().getArrivalTime();
    }

    /**
     * If there are any vacant server, the a new ServeEvent would be created with this customer and 
     * the available server, else it goes on to find any server that has no waiting people and would 
     * create a WaitEvent accordingly. If no server is found, it would create a LeaveEvent
     * @param group the group of Servers handling the event
     * @return the next event depending if the customer waits, is served, or leaves
     */
    @Override
    public Optional<Event> happenEvent(GroupServers group) {
        Event newEvent;
        double arrivalTime = this.getCustomerInvolved().getArrivalTime();
        Optional<Server> server = group.findNextAvailableServerToServe(arrivalTime);
        if (server.isPresent()) {
           //there is an available server to serve
           server.ifPresent(x -> {
               if (x.getNextServiceTime() == 0) {
                    x.setNextServiceTime(arrivalTime);
                }
           });
           newEvent = new ServedEvent(this.getCustomerInvolved(), server, arrivalTime);

        } else {
            //find server for customer to wait at
            Optional<Server> waitAt = group.findNextAvailableServerToWait();
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
        return String.format("%.3f", this.time) + " " + 
            this.getCustomerInvolved().getID() + " arrives";
    }
    
}