package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Optional;

/**
 * Manages all events.
 */
public class Manage {

    /**
     * Runs the simulation and prints the necessary output.
     * 
     * @param numOfServers         total number of servers
     * @param customerArrivalTimes timings each customer arrives
     */

    public static void run(
            int rngSeed, 
            int numOfServers, 
            int numOfSelfCheckServers, 
            int maxQueueLength,
            int numOfCustomers, 
            double arrivalrate, 
            double servicerate, 
            double restingRate, 
            double restingProbability,
            double greedyProbability) {

        List<Customer> listOfCustomer = new ArrayList<>();

        RandomGenerator rng = new RandomGenerator(rngSeed, arrivalrate, servicerate, restingRate);

        GroupServers groupServers = new GroupServers(numOfServers, numOfSelfCheckServers, maxQueueLength, rng,
                restingProbability);

        double now = 0;
        while (numOfCustomers > 0) {
            listOfCustomer.add(new Customer(now));
            now = now + rng.genInterArrivalTime();
            numOfCustomers--;
        }

        PriorityQueue<Event> pq = new PriorityQueue<>();

        for (Customer cust : listOfCustomer) {
            pq.add(new ArriveEvent(cust));
        }

        while (!pq.isEmpty()) {
            Event curr = pq.poll();
            // System.out.println(curr);
            Optional<Event> newEvent = curr.happenEvent(groupServers);
            Optional<Event> proceedingEvent = newEvent.filter(x -> x.validEvent());
            proceedingEvent.ifPresent(x -> pq.add(x));

        }

        System.out.println("[" + String.format("%.3f", GroupServers.getAverageWaitingTime()) + " "
                + GroupServers.getTotalServed() + " " + GroupServers.getTotalLeaves() + "]");
    }
}
