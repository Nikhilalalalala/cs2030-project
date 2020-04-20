package cs2030.simulator;

/**
 * This class simulates the beahviour of a self-check server with a common queue.
 */
class SelfCheckServer extends Server {

    private static int sharedQueueMax;
    private static int numOfPeopleInSharedQueue;

    /**
     * A Server has a unique ID and only 1 waiting customer atmost at any given time
     * @param maxQueueCapacity the maximum queue capacity shared among all self-check servers
     */
    SelfCheckServer(int maxQueueCapacity) {
        super(maxQueueCapacity);
        sharedQueueMax = maxQueueCapacity;
        numOfPeopleInQueue = 0;
    }

    /**
     * Returns true if the server is self-check
     */
    @Override
    public boolean isSelfCheck() {
        return true;
    }

    @Override
    public String toString() {
        return "self-check " + this.serverID;
    }
    
    /**
     * Returns true if there the maximum queue capacity has not been reached
     */
    @Override
    public boolean canWait() {
        return numOfPeopleInSharedQueue < sharedQueueMax;
    }

    /**
     * Increments the shared queue by one
     */
    @Override
    public void addCustomerToQueue() {
        numOfPeopleInSharedQueue++;
    }

    /**
     * Decrements the shared queue by one
     */
    @Override
    public void removeCustomerFromQueue() {
        numOfPeopleInSharedQueue--;
    }

    /**
     * Gives the number of people in the queue 
     * @return the number of people in the queue
     */
    @Override
    public int getNumOfPeopleInQueue() {
        return SelfCheckServer.numOfPeopleInSharedQueue;
    }

}