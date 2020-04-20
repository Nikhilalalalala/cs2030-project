package cs2030.simulator;

/** This class represents the behaviour of a greedy customer.
 */
class GreedyCustomer extends Customer {

    /** Creates a greedy customer with a unique ID and the time of arrival.
     * 
     * @param arrivalTime the time the customer arrives
     */
    GreedyCustomer(double arrivalTime) {
        super(arrivalTime);
    }

    /** Returns true for a greedy customer.
     */
    @Override
    public boolean isGreedy() {
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(this.getID()) + "(greedy)";
    }

}