package cs2030.simulator;

/**
 * This class represents the behaviour of a greedy customer
 */
class GreedyCustomer extends Customer {

    GreedyCustomer(double arrivalTime) {
        super(arrivalTime);
    }

    @Override
    public boolean isGreedy() {
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(this.getID()) + "(greedy)";
    }

}