package cs2030.simulator;
/**
 * This Server class aids in serving of customers as the customers arrive in
 * their order 
 */
class Server implements Comparable<Server>{

    private final int ServerID;
    private static int countOfServers;
    private double nextServiceTime;
    public boolean haveWaitingCustomer;

    public int totalWaits;
    public double totalWaitingTime;
	public int totalServed;
    
    /** A Server has a unique ID and only 1 waiting customer 
     * atmost at any given time
     * 
     */
    Server() {
        Server.countOfServers ++;
        this.ServerID = Server.countOfServers;
        this.nextServiceTime = 0;
        this.haveWaitingCustomer = false;
    }
        
    /**
     * To control whether the server has a waiting customer
     * @param wait the new state of server as to whether it has 
     * a waiting customer or not
     */
    public void setWaitingCustomer(boolean wait) {
        this.haveWaitingCustomer = wait;
    }
    
    /**
     * To control the next service time the server is able to serve
     * @param nextServiceTime the new next service time
     */
    public void setNextServiceTime(double additionalTime) {
        this.nextServiceTime = this.nextServiceTime + additionalTime;
    }

    /**
     * Determines if the server is available to serve at the given time
     * @param time the time to check
     * @return whether the server can serve at that time
     */
    public boolean canServe(double time) {
         return time >= this.nextServiceTime;  
    }

    public boolean canWait() {
        return !this.haveWaitingCustomer;
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

    public boolean getHaveWaitingCustomer() {
        return this.haveWaitingCustomer;
    }

    public int getServerID() {
        return this.ServerID;
    }

    public double getNextServiceTime() {
		return this.nextServiceTime;
    }
    
	public int getTotalServed() {
		return this.totalServed;
	}
    
    public double getTotalWaitingTime() {
        return this.totalWaitingTime;
    }
}
    

