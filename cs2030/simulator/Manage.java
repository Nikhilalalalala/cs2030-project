package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Optional;

/**
 * Manages all events.
 */
public class Manage {

    /** Runs the simulation and prints the necessary output.
     * 
     * @param rngSeed the seed for the random obect
     * @param numOfServers number of non-self-check servers
     * @param numOfSelfCheckServers number of self-check servers
     * @param maxQueueLength maximum number of people in a queue
     * @param numOfCustomers number of customer to simulate
     * @param arrivalrate the rate of arrival of customers
     * @param servicerate the rate of duration of service of servers
     * @param restingRate the rate at whch servers rest after a service
     * @param restingProbability the probability that a server rests after a service
     * @param greedyProbability the probability that a custiner is greedy
     */
    public static void run(int rngSeed, int numOfServers, int numOfSelfCheckServers, 
            int maxQueueLength, int numOfCustomers, double arrivalrate, double servicerate, 
            double restingRate, double restingProbability, double greedyProbability) {

        List<Customer> listOfCustomer = new ArrayList<>();

        RandomGenerator rng = new RandomGenerator(rngSeed, arrivalrate, servicerate, restingRate);

        GroupServers groupServers = new GroupServers(numOfServers, numOfSelfCheckServers, 
            maxQueueLength,rng, restingProbability);

        double now = 0;
        while (numOfCustomers > 0) {

            if (rng.genCustomerType() < greedyProbability) {
                listOfCustomer.add(new GreedyCustomer(now));
            } else {
                listOfCustomer.add(new Customer(now));
            }
            now = now + rng.genInterArrivalTime();

            numOfCustomers--;
        }

        PriorityQueue<Event> pq = new PriorityQueue<>();

        for (Customer cust : listOfCustomer) {
            pq.add(new ArriveEvent(cust));
        }

        while (!pq.isEmpty()) {
            Event curr = pq.poll();
            Optional<Event> newEvent = curr.happenEvent(groupServers);
            Optional<Event> proceedingEvent = newEvent.filter(x -> x.validEvent());
            proceedingEvent.ifPresent(x -> pq.add(x));

        }

        System.out.println("[" + String.format("%.3f", GroupServers.getAverageWaitingTime()) + " "
                + GroupServers.getTotalServed() + " " + GroupServers.getTotalLeaves() + "]");
    }
}
