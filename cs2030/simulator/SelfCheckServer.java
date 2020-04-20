package cs2030.simulator;

import java.util.PriorityQueue;

class SelfCheckServer extends Server {

    private static int sharedQueueMax;
    private static int numOfPeopleInSharedQueue;

    SelfCheckServer(int m) {
        super(m);
        sharedQueueMax = m;
        numOfPeopleInQueue = 0;
    }

    // public static void addToCommonQueue(SelfCheckServer s) {
    // if (queue == null) queue = new PriorityQueue<>();
    // SelfCheckServer.queue.add(s);
    // }
    // public static Server getEarliest() {
    // return queue.poll();
    // }

    @Override
    public int compareTo(Server s) {
        double difference = this.getNextServiceTime() - s.getNextServiceTime();
        if (difference == 0)
            return this.getServerID() - s.getServerID();
        else if (difference < 0)
            return -1;
        else
            return 1;
    }

    @Override
    public boolean isSelfCheck() {
        return true;
    }

    @Override
    public String toString() {
        return "self-check " + this.serverID;
    }

    @Override
    public boolean canWait() {
        return numOfPeopleInSharedQueue < sharedQueueMax;
    }

    @Override
    public void addCustomerToQueue() {
        numOfPeopleInSharedQueue++;
    }

    @Override
    public void removeCustomerFromQueue() {
        numOfPeopleInSharedQueue--;
    }

}