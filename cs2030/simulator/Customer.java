package cs2030.simulator;

/** This class contains the attributes and the methods of a Customer.
 * 
 */
public class Customer implements Comparable<Customer> {
    protected final int customerID;
    protected static int numOfCustomers = 0;
    protected final double arrivalTime;

    /** Creates a customer with a unique ID and the time of arrival.
     * 
     * @param arrivalTime the time the customer arrives
     */
    Customer(double arrivalTime) {
        Customer.numOfCustomers++;
        this.customerID = numOfCustomers;
        this.arrivalTime = arrivalTime;
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public int getID() {
        return this.customerID;
    }

    public int getNumOfCustomers() {
        return numOfCustomers;
    }

    /** Compares two customers first based on their arrival Time and then their ID.
     * 
     */
    @Override
    public int compareTo(Customer cust) {
        double difference = this.getArrivalTime() - cust.getArrivalTime();
        if (difference == 0) {
            return this.getID() - cust.getID();
        } else {
            if (difference > 0) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    @Override
    public String toString() {
        return String.valueOf(customerID);
    }

    /** Returns false if the customer is not greedy.
     * @return true if the customer is greedy, false otherwise
     */
    public boolean isGreedy() {
        return false;
    }
}