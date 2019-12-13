package bgu.spl.mics;

import java.util.concurrent.ArrayBlockingQueue;

public class Producer implements Runnable {
    private final ArrayBlockingQueue<Integer> queue;
    private final int id;
    private final int amount; //Number of products to produce
    private final int PERIOD = 700; //Time period required for producing one product
    Producer(int id, int amount, ArrayBlockingQueue<Integer> q) {
        this.id = id;
        this.amount = amount;
        queue =q;
    }
    public void run() {
        try {
            for (int i =0; i < amount; i++){
                Thread.sleep(PERIOD);
                queue.put(produce(id,i));
            }

        } catch (InterruptedException ignored) { }
    }

    /**
     * produce an Integer product composed of producer's id and current iteration
     * @param id - producer id
     * @param iteration - current iteration
     * @return Integer product
     */
    private Integer produce(int id, int iteration) {
        int product = id * amount + iteration;
        System.out.println("Producing: " + product);
        return product;

    }
}
