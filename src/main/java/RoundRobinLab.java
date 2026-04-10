import java.util.*;

public class RoundRobinLab {

    static class Process {
        int id;
        int arrivalTime;
        int burstTime;
        int remainingTime;
        int completionTime;
        int turnaroundTime;
        int waitingTime;

        public Process(int id, int arrivalTime, int burstTime) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.remainingTime = burstTime;
        }
    }

    /**
     * 1, 2, 3: Implement Round Robin Scheduling
     *
     * This method simulates Round Robin scheduling with the given time quantum.
     *
     * 1: Create the ready queue and scheduling loop
     *   - Create an ArrayList to hold the ready queue
     *   - Add all processes to the ready queue initially
     *   - Create a loop that continues while the queue is not empty
     *
     * 2: Process execution logic
     *   - Remove the first process from the queue
     *   - Calculate how much time this process will run (minimum of quantum and remaining time)
     *   - Update currentTime by adding the execution time
     *   - Subtract execution time from the process's remainingTime
     *   - If remainingTime > 0, add process back to the end of the queue
     *   - If remainingTime == 0, set the process completionTime to currentTime
     *
     * 3: Calculate metrics after all processes complete
     *   - Loop through all processes
     *   - For each process: turnaroundTime = completionTime - arrivalTime
     *   - For each process: waitingTime = turnaroundTime - burstTime
     */
     
    public static void scheduleRoundRobin(List<Process> processes, int timeQuantum) {
        int currentTime = 0;

        // 1. Create ready queue and add all processes 
        //    -> "the waiting line at the bakery" 
        ArrayList <Process> readyQueue = new ArrayList <> ();
        for (Process p : processes) {
            readyQueue.add(p); // customers get on line in the order they arrived
        }

        // 2: Scheduling loop
        //      -> "as long as there's a line at the bakery, keep serving the customers"
        /**
         * while (queue is not empty) {
         *
         *     - Remove first process 		-> 		"take the customer at the front of the line"
         *     - Calculate execution time (min of quantum and remaining time) 		-> 	"decide how long their order takes: they either get the Time Quantum or remaining time left
         *     - Update current time 		-> 	"move the clock forward by the time spent with that customer at the counter"
         *     - Decrease remaining time 	-> 	"subtract the time the customer just used from their total order"
         *     - If not done, add back to queue 	-> 	"if they still need more time, they have to go to the back of the line"
         *     - If done, set completion time 	-> 	"(else) if they are finished, record the exact time they left the bakery"
         *
         * }
         */ 
        while (!readyQueue.isEmpty()) {
        
        Process current = readyQueue.remove(0);
        int executeTime = Math.min(timeQuantum, current.remainingTime);
        currentTime += executeTime;
        current.remainingTime -= executeTime;
        if (current.remainingTime > 0) {
                readyQueue.add(current);
            } 
            else  
                current.completionTime = currentTime;
            }
            
            
         /** 3: Calculate turnaround and waiting times for each process		-> 		"seeing how long the community (of customers) had to wait"
         *     turnaroundTime = completionTime - arrivalTime             -> 		turnaround = the total 'door-to-door' time they spent at the bakery
         *     waitingTime = turnaroundTime - burstTime			->		waiting time = the turnaround minus the time they spent actually getting their order
         */ 
          for (Process p : processes) {
            p.turnaroundTime = p.completionTime - p.arrivalTime;
            p.waitingTime = p.turnaroundTime - p.burstTime;
        }
         
    }

   

    /**
     * Calculate and display metrics (FULLY PROVIDED)
     */
    public static void calculateMetrics(List<Process> processes, int timeQuantum) {
        System.out.println("========================================");
        System.out.println("Round Robin Scheduling Simulator");
        System.out.println("========================================\n");
        System.out.println("Time Quantum: " + timeQuantum + "ms");
        System.out.println("----------------------------------------");
        System.out.println("Process | Arrival | Burst | Completion | Turnaround | Waiting");

        double totalTurnaround = 0;
        double totalWaiting = 0;

        for (Process p : processes) {
            System.out.printf("   %d    |    %d    |   %d   |     %d     |     %d     |    %d\n",
                    p.id, p.arrivalTime, p.burstTime, p.completionTime,
                    p.turnaroundTime, p.waitingTime);
            totalTurnaround += p.turnaroundTime;
            totalWaiting += p.waitingTime;
        }

        System.out.println();
        System.out.printf("Average Turnaround Time: %.2fms\n", totalTurnaround / processes.size());
        System.out.printf("Average Waiting Time: %.2fms\n", totalWaiting / processes.size());
        System.out.println("========================================\n\n");
    }

    /**
     * Main method (FULLY PROVIDED)
     */
    public static void main(String[] args) {
        List<Process> processes1 = new ArrayList<>();
        processes1.add(new Process(1, 0, 7));
        processes1.add(new Process(2, 0, 4));
        processes1.add(new Process(3, 0, 2));

        scheduleRoundRobin(processes1, 3);
        calculateMetrics(processes1, 3);

        List<Process> processes2 = new ArrayList<>();
        processes2.add(new Process(1, 0, 7));
        processes2.add(new Process(2, 0, 4));
        processes2.add(new Process(3, 0, 2));

        scheduleRoundRobin(processes2, 5);
        calculateMetrics(processes2, 5);
    }
}
