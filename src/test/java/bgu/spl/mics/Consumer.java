package bgu.spl.mics;

import java.util.concurrent.ArrayBlockingQueue;

public class Consumer extends Subscriber {
    private final ArrayBlockingQueue<Integer> queue;
    private final int amount; //Number of products to consume
    private final int PERIOD = 2000;  //Time period required for consuming one product
    Consumer(String id, int amount, ArrayBlockingQueue<Integer> q) {
        super(id);
        this.amount = amount;
        queue =q;
    }

    @Override
    protected void initialize() {

    }
    /**
     * consume an Integer product
     * @param prod - the product to be consumed
     */
    private void consume(Integer prod){
        System.out.println("Consumed:  " + prod);
    }
}
