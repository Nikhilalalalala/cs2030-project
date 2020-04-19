package cs2030.simulator;

import java.util.Optional;

/**
 * This class describes the behaviour of the event when a customer is served by
 * a server
 */
class ServedEvent extends Event {
    private boolean wasWaiting;
    private double endTimeOfThisService;
    private double arrivalTime;
    

    /**
     * Constructor of ServeEvent where the customer is modelled to be served by the
     * server
     * 
     * @param customer The customer that is being served
     * @param server   the server serving the customer
     * @param time     the time the customer starts being served
     */
    ServedEvent(Customer customer, Optional<Server> server, double arrivaltime, boolean wasWaiting) {
        
        super(customer, server);
        this.arrivalTime = arrivaltime;
        this.wasWaiting = wasWaiting;

        if (wasWaiting) this.time = this.server.get().getNextServiceTime();
        else this.time = this.arrivalTime;
    }

    /**
     * The customer is served
     * 
     * @param group the group of Servers handling the event
     * @return the Done Event where the customer is done serving
     */
    public Optional<Event> happenEvent(GroupServers group) {

        if (this.time < this.server.get().getNextServiceTime()) {
            this.isDiscarded = true;
            // System.out.println("new serve event created (time less than next service time) " + this);
            return Optional.of( new ServedEvent(this.getCustomerInvolved(), this.server, this.arrivalTime, wasWaiting));
        }

        System.out.println(this);

        if (this.wasWaiting) {
            // the customer getting served is someone who has waited;
            this.getServer().get().removeCustomerFromQueue();
            double finishedPreviousServiceAt = server.get().getNextServiceTime(); // also the time the server starts new service 
            
            double waitingTimeIncurred = finishedPreviousServiceAt - this.arrivalTime;
            GroupServers.addTotalWaitingTime(waitingTimeIncurred);

            double durationOfService = group.createServiceDuration();
            double timeDone = this.time + durationOfService;
            this.server.ifPresent(x -> x.setNextServiceTime(durationOfService));
            
            
            Event newEvent = new DoneEvent(this.getCustomerInvolved(), this.getServer(), timeDone);

            return Optional.of(newEvent);

        } else {

            double timeDone = this.time + group.createServiceDuration();
            double timetoAdd = timeDone - this.getServer().get().getNextServiceTime();
            this.server.ifPresent(x -> x.setNextServiceTime(timetoAdd));
            Event newEvent = new DoneEvent(this.getCustomerInvolved(), this.getServer(), timeDone);
            return Optional.of(newEvent);
        }
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + this.getCustomerInvolved().getID() + " served by "
                + this.getServer().get().toString();
    }

}