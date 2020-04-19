import java.util.Scanner;
import cs2030.simulator.Manage;

class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int rngSeed = sc.nextInt();
        int numOfServers = sc.nextInt();
        int maxQueueLength = sc.nextInt();
        int numOfCustomers = sc.nextInt();
        double arrivalrate = sc.nextDouble();
        double servicerate = sc.nextDouble();
        double restingrate = sc.nextDouble();
        double restingProbability = sc.nextDouble();

        Manage.run(rngSeed, numOfServers, maxQueueLength, numOfCustomers, arrivalrate, servicerate, restingrate, restingProbability);

        sc.close();

    }
}