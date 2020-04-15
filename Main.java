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
    
        List<Double> listOfCustomer = new ArrayList<>();
        
        while(sc.hasNext()) {
            listOfCustomer.add(sc.nextDouble());
        }

        Manage.run(numOfServers, listOfCustomer);
        
        sc.close();
        
    }
}