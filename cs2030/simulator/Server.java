package cs2030.simulator;

/**
 * This Server class aids in serving of customers as the customers arrive in
 * their order
 */
class Server implements Comparable<Server> {

    private final int ServerID;
    private static int countOfServers;
    private double nextServiceTime;

    public int maxQueueCapacity, numOfPeopleInQueue;

    /**
     * A Server has a unique ID and only 1 waiting customer atmost at any given time
     * 
     */
    Server(int m) {
        Server.countOfServers++;
        this.ServerID = Server.countOfServers;
        this.nextServiceTime = 0;
        this.numOfPeopleInQueue = 0;
        this.maxQueueCapacity = m;
    }

    /**
     * To control the next service time the server is able to serve
     * 
     * @param nextServiceTime the new next service time
     */
    public void setNextServiceTime(double additionalTime) {
        this.nextServiceTime = this.nextServiceTime + additionalTime;
    }

    /**
     * Determines if the server is available to serve at the given time
     * 
     * @param time the time to check
     * @return whether the server can serve at that time
     */
    public boolean canServe(double time) {
        return time >= this.nextServiceTime;
    }

    @Override
    public int compareTo(Server s) {
        double difference = this.getNextServiceTime() - s.getNextServiceTime();
        if (difference == 0) {
            return this.getServerID() - s.getServerID();
        } else {
            if (difference > 0)
                return 1;
            else
                return -1;
        }
    }

    public boolean canWait() {
        return numOfPeopleInQueue < maxQueueCapacity;
    }

    public void addCustomerToQueue() {
        this.numOfPeopleInQueue++;
    }

    public void removeCustomerFromQueue() {
        this.numOfPeopleInQueue--;
    }

    public int getServerID() {
        return this.ServerID;
    }

    public double getNextServiceTime() {
        return this.nextServiceTime;
    }

    @Override
    public String toString() {
        return "server " + this.ServerID;
    }
}
