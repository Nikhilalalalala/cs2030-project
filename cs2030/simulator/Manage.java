package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Optional;

public class Manage {

    public static void run (int numOfServers, List<Double> customerArrivalTimes) {
        
        GroupServers groupServers = new GroupServers(numOfServers);
        
        List<Customer> listOfCustomer = new ArrayList<>();
        for (Double time : customerArrivalTimes) {
            listOfCustomer.add(new Customer(time));
        }

        PriorityQueue<Event> pq = new PriorityQueue<>();

        for (Customer cust : listOfCustomer) {
            pq.add(new ArriveEvent(cust));
        }

        while (pq.size() != 0) {
            Event curr = pq.poll();
            System.out.println(curr);
            Optional<Event> newEvent  = curr.happenEvent(groupServers);
            newEvent.ifPresent(x -> pq.add(x));
            
        }

        System.out.println("[" + String.format("%.3f", GroupServers.getAverageWaitingTime()) + " " +
                GroupServers.getTotalServed() + " " +
                GroupServers.getTotalLeaves() + "]");        
    }
}