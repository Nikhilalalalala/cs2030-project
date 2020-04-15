import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.Optional;

/** 
 * This class manages the behaviour of the entire collection of serves available
 * 
 */
class GroupServers {
    private List<Server> groupOfServers;
    private static int totalLeaves;
    private static int totalServed;
    private static double totalWaitingTime;

    /**
     * Constructor of GroupServers where a group of servers is being modelled
     * @param numOfServers the total number of servers
     */
    GroupServers(int numOfServers) {
        List<Server> group = new ArrayList<>(numOfServers);
        IntStream.range(0, numOfServers)
            .forEach(id -> {
                Server creation = new Server();
                group.add(creation);
            });
        this.groupOfServers = group;
    }

    /**
     * Finds a server with no customer it is serving at the time given
     * If there are more than 1 vacant server, the one with the 
     * smaller ID is returned
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
     * Finds a server with no customer waiting for it
     * If there are more than 1 server with no waiting customer, 
     * the one with the smaller ID is returned
     * @return the server with no waiting customer if there is
     */
    public Optional<Server> findNextAvailableServerToWait() {
        for (Server server : groupOfServers) {
            if (server.canWait()) {
                if (!server.getHaveWaitingCustomer()) {
                    return Optional.of(server);
                }
            }
        }        
        return Optional.empty();
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
     * Increments the total waiting time accumulated by the group of servers
     * by the time supplied
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
     * @return average waiting time for customers who have been served
     */
    public static double getAverageWaitingTime() {
        return GroupServers.getTotalWaitingTime() / GroupServers.getTotalServed();
    }
}