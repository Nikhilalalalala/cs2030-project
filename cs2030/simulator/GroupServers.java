package cs2030.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.Optional;

/**
 * This class manages the behaviour of the entire collection of serves available
 * 
 */
public class GroupServers {
    private List<Server> groupOfServers;
    private static int totalLeaves;
    private static int totalServed;
    private static double totalWaitingTime;
    private RandomGenerator randomGenerator;
    private double restingProbability;
    private int numOfHumanServers;
    private int numOfSelfCheckServers;

    /**
     * Constructor of GroupServers where a group of servers is being modelled
     * 
     * @param numOfServers the total number of servers
     */

    /*
     * public GroupServers(int numOfServers) { List<Server> group = new
     * ArrayList<>(numOfServers); IntStream.range(0, numOfServers) .forEach(id -> {
     * Server creation = new Server(); group.add(creation); }); this.groupOfServers
     * = group; }
     */

    GroupServers(int numOfServers, int numOfSelfCheckServers, int maxQueueInServer, RandomGenerator rng,
            double restingProbability) {
        List<Server> group = new ArrayList<>(numOfServers);
        IntStream.range(0, numOfServers).forEach(id -> {
            Server creation = new Server(maxQueueInServer);
            group.add(creation);
        });
        IntStream.range(0, numOfSelfCheckServers).forEach(id -> {
            SelfCheckServer creation = new SelfCheckServer(maxQueueInServer);
            // SelfCheckServer.addToCommonQueue(creation);
            group.add(creation);
        });
        this.numOfHumanServers = numOfServers;
        this.numOfSelfCheckServers = numOfSelfCheckServers;
        this.groupOfServers = group;
        this.randomGenerator = rng;
        this.restingProbability = restingProbability;
    }

    /**
     * Finds a server with no customer it is serving at the time given If there are
     * more than 1 vacant server, the one with the smaller ID is returned
     * 
     * @param timeToServe the time to check
     * @return the vacant server if there is
     */
    public Optional<Server> findNextAvailableServerToServe(double timeToServe) {
        for (Server server : this.groupOfServers) {
            if (server.canServe(timeToServe)) {
                return Optional.of(server);
            }
        }
        return Optional.empty();
    }

    /**
     * Finds a server with no customer waiting for it If there are more than 1
     * server with no waiting customer, the one with the smaller ID is returned
     * 
     * @return the server with no waiting customer if there is
     */
    public Optional<Server> findNextAvailableServerToWait() {
        for (Server server : this.groupOfServers) {
            if (server.canWait()) {
                return Optional.of(server);
            }
        }
        return Optional.empty();
    }

    public Optional<Server> findEarliestSelfCheckServer(Server curr) {
        Server earliest = curr;
        for (int i = numOfHumanServers; i < groupOfServers.size(); i++) {
            if (groupOfServers.get(i).getNextServiceTime() < earliest.getNextServiceTime()) {
                earliest = groupOfServers.get(i);
            }
        }
        return Optional.of(earliest);
    }

    /**
     * Increments the number of customers left
     */
    public static void addTotalLeaves() {
        GroupServers.totalLeaves++;
    }

    public static int getTotalLeaves() {
        return GroupServers.totalLeaves;
    }

    public double createServiceDuration() {
        return this.randomGenerator.genServiceTime();
    }

    public boolean deservesRest() {
        return this.randomGenerator.genRandomRest() < this.restingProbability;
    }

    public double createRestingDuration() {
        return this.randomGenerator.genRestPeriod();
    }

    /**
     * Increments the number of customers served
     */
    public static void addTotalServed() {
        GroupServers.totalServed++;
    }

    public static int getTotalServed() {
        return GroupServers.totalServed;
    }

    /**
     * Increments the total waiting time accumulated by the group of servers by the
     * time supplied
     * 
     * @param time the additional waiting time incurred
     */
    public static void addTotalWaitingTime(double time) {
        GroupServers.totalWaitingTime += time;
    }

    public static double getTotalWaitingTime() {
        return GroupServers.totalWaitingTime;
    }

    /**
     * Gets the average waiting time for customers who have been served
     * 
     * @return average waiting time for customers who have been served
     */
    public static double getAverageWaitingTime() {
        if (GroupServers.getTotalWaitingTime() == 0 || GroupServers.getTotalServed() == 0)
            return 0;
        else
            return GroupServers.getTotalWaitingTime() / GroupServers.getTotalServed();
    }
}