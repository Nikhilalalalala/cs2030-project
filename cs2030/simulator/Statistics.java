package cs2030.simulator;

/**
 * Tracks all the necessary statistics of all servers.
 */
class Statistics {
    private static int totalLeaves;
    private static int totalServed;
    private static double totalWaitingTime;

    /** Increments the number of customers left.
     */
    public static void addTotalLeaves() {
        Statistics.totalLeaves++;
    }

    public static int getTotalLeaves() {
        return Statistics.totalLeaves;
    }

    /** Increments the number of customers served.
     */
    public static void addTotalServed() {
        Statistics.totalServed++;
    }

    public static int getTotalServed() {
        return Statistics.totalServed;
    }

    /** Increments total waiting time accumulated by the group of servers by the time supplied.
     * 
     * @param time the additional waiting time incurred
     */
    public static void addTotalWaitingTime(double time) {
        Statistics.totalWaitingTime += time;
    }

    public static double getTotalWaitingTime() {
        return Statistics.totalWaitingTime;
    }

    /** Gets the average waiting time for customers who have been served.
     * 
     * @return average waiting time for customers who have been served
     */
    public static double getAverageWaitingTime() {
        if (Statistics.getTotalWaitingTime() == 0 || Statistics.getTotalServed() == 0) {
            return 0;
        } else {
            return Statistics.getTotalWaitingTime() / Statistics.getTotalServed();
        }
    }


}