package cs2030.simulator;

import java.util.Optional;

/**
 * This class describes the behaviour of the event when a customer is served by
 * a server
 */
class ServedEvent extends Event {
    private boolean wasWaiting;
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
        if (wasWaiting)
            this.time = this.server.get().getNextServiceTime();
        else
            this.time = this.arrivalTime;
    }

    ServedEvent(Customer customer, Optional<Server> server, double time, double arrivalTime, boolean wasWaiting) {
        super(customer, server);
        this.arrivalTime = arrivalTime;
        this.wasWaiting = wasWaiting;
        if (wasWaiting)
            this.time = this.server.get().getNextServiceTime();
        else
            this.time = this.arrivalTime;
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
            Event newEvent;
            if (this.server.filter(x -> x.isSelfCheck()).isPresent()) {
                Optional<Server> earliestServer = group.findEarliestSelfCheckServer(this.server.get());
                newEvent = new ServedEvent(this.getCustomerInvolved(), earliestServer, this.time, arrivalTime, wasWaiting);
            } else {
                newEvent = new ServedEvent(this.getCustomerInvolved(), this.getServer(), this.time, arrivalTime, wasWaiting);
            }
            return Optional.of(newEvent);
        }

        System.out.println(this);

        if (this.wasWaiting) {

            this.server.get().removeCustomerFromQueue();
            double waitingTimeIncurred = this.time - this.arrivalTime;
            GroupServers.addTotalWaitingTime(waitingTimeIncurred);
        }

        double timeDone = this.time + group.createServiceDuration();
        double timetoAdd = timeDone - this.getServer().get().getNextServiceTime();
        this.server.ifPresent(x -> x.setNextServiceTime(timetoAdd));
        Event newEvent = new DoneEvent(this.getCustomerInvolved(), this.getServer(), timeDone);

        return Optional.of(newEvent);
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + this.getCustomerInvolved().getID() + " served by "
                + this.getServer().get().toString();
    }

}