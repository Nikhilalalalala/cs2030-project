package cs2030.simulator;

/** This Server class aids in serving of customers as the customers arrive in their order.
 */
class Server implements Comparable<Server> {

    protected final int serverID;
    private static int countOfServers;
    protected double nextServiceTime;

    protected int maxQueueCapacity;
    protected int numOfPeopleInQueue;

    /** A Server has a unique ID and only 1 waiting customer atmost at any given time.
     * 
     * @param maxQueueCapacity the maximum queue capacity of a server
     */
    Server(int maxQueueCapacity) {
        Server.countOfServers++;
        this.serverID = Server.countOfServers;
        this.nextServiceTime = 0;
        this.numOfPeopleInQueue = 0;
        this.maxQueueCapacity = maxQueueCapacity;
    }

    /** To control the next service time the server is able to serve.
     * 
     * @param nextServiceTime the new next service time
     */
    public void setNextServiceTime(double additionalTime) {
        this.nextServiceTime = this.nextServiceTime + additionalTime;
    }

    /** Determines if the server is available to serve at the given time.
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
            if (difference > 0) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    /** Returns true if there the maximum queue capacity has not been reached.
     */
    public boolean canWait() {
        return numOfPeopleInQueue < maxQueueCapacity;
    }

    /** Increments the current queue by one.
     */
    public void addCustomerToQueue() {
        this.numOfPeopleInQueue++;
    }

    /** Decrements the current queue by one.
     */
    public void removeCustomerFromQueue() {
        this.numOfPeopleInQueue--;
    }

    public int getServerID() {
        return this.serverID;
    }

    /** Gives the next available time for server to serve.
     * 
     * @return next service time
     */
    public double getNextServiceTime() {
        return this.nextServiceTime;
    }

    @Override
    public String toString() {
        return "server " + this.serverID;
    }

    /** Returns false if the server is not self-check.
     */
    public boolean isSelfCheck() {
        return false;
    }

    /** Gives the number of people in the queue.
     * 
     * @return number of people in the queue
     */
    public int getNumOfPeopleInQueue() {
        return numOfPeopleInQueue;
    }
}
