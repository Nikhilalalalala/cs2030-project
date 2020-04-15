import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import cs2030.simulator.*;

class Main {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numOfServers = sc.nextInt();
        GroupServers groupServers = new GroupServers(numOfServers);
    
        List<Customer> listOfCustomer = new ArrayList<>();
        while(sc.hasNext()) {
            listOfCustomer.add(new Customer(sc.nextDouble()));
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
        
        sc.close();
        
    }
}