package bgu.spl.mics;

import bgu.spl.mics.application.messages.TerminateBroadcast;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * The {@link MessageBrokerImpl class is the implementation of the MessageBroker interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBrokerImpl implements MessageBroker {
    private ConcurrentHashMap<Class<? extends Message>, ConcurrentLinkedQueue<Subscriber>> mapOfSubscribers = new ConcurrentHashMap<>();

    private ConcurrentHashMap<Subscriber, LinkedBlockingDeque<Message>> mapOfToDoMessages = new ConcurrentHashMap<>();

    private ConcurrentHashMap<Event, Future> mapOfevents = new ConcurrentHashMap<>();

    private static class MessageBrokerHolder {
        private static MessageBroker instance = new MessageBrokerImpl();
    }

    /**
     * Retrieves the single instance of this class.
     */
    public static MessageBroker getInstance() {
        return MessageBrokerHolder.instance;
    }

    @Override
    public <T> void subscribeEvent(Class<? extends Event<T>> type, Subscriber m) {
        subscribe(type, m);

    }

    @Override
    public void subscribeBroadcast(Class<? extends Broadcast> type, Subscriber m) {
        subscribe(type, m);

    }

    private void subscribe(Class<? extends Message> type, Subscriber m) {
        synchronized (type) {
            mapOfSubscribers.putIfAbsent(type, new ConcurrentLinkedQueue<>());
            ConcurrentLinkedQueue<Subscriber> toAddQ = mapOfSubscribers.get(type);
            if (!toAddQ.contains(m))
                toAddQ.add(m);
        }
    }

    @Override
    public <T> void complete(Event<T> e, T result) {
        Future<T> future = mapOfevents.get(e);
        if (future != null)
            future.resolve(result);
    }

    @Override
    public void sendBroadcast(Broadcast b) {
        if (mapOfSubscribers.containsKey(b.getClass())) {
            synchronized (mapOfSubscribers.get(b.getClass())) {
                ConcurrentLinkedQueue<Subscriber> subscribersOfCurrBroadcast = mapOfSubscribers.get(b.getClass());
                if(subscribersOfCurrBroadcast != null && !subscribersOfCurrBroadcast.isEmpty()){
                    for (RunnableSubPub currSubscriber : subscribersOfCurrBroadcast)
                        if(b instanceof TerminateBroadcast){
                           mapOfToDoMessages.get (currSubscriber).addFirst(b);
                        }
                        else
                            mapOfToDoMessages.get(currSubscriber).add(b);
                }
            }
        }
    }

    @Override
    public <T> Future<T> sendEvent(Event<T> e) {
        Future future;
        Subscriber currSubscriber = null;
        boolean problem;
        do{
            problem = false;
            if(mapOfSubscribers.containsKey(e.getClass())){
                future = new Future();
                mapOfevents.put(e, future);
                synchronized (mapOfSubscribers.get(e.getClass())) { // lock the queue of this event class
                    ConcurrentLinkedQueue<Subscriber> subscribersOfCurrEvent = mapOfSubscribers.get(e.getClass());
                    if (subscribersOfCurrEvent != null && !subscribersOfCurrEvent.isEmpty()) {
                        currSubscriber = subscribersOfCurrEvent.poll(); // return the head of the list & removes it
                        subscribersOfCurrEvent.add(currSubscriber); // add the currSubscriber to the end of the list (round-robin)
                    }
                } // end of sync
                if (currSubscriber != null) {
                    synchronized (currSubscriber) {
                        BlockingQueue<Message> todoQ = mapOfToDoMessages.get(currSubscriber);
                        if (todoQ != null) {
                            todoQ.add(e);
                        } else { // the currSubscriber unregistered himself
                            problem = true;
                        }
                    }
                }
                else {
                    future = null;
                }
            }
            else{
                future = null;
            }
        }
        while(problem);
        return future;
    }

    @Override
    public void register(Subscriber m) {
        mapOfToDoMessages.putIfAbsent(m, new LinkedBlockingDeque<>());
    }

    @Override
    public void unregister(Subscriber m) {
        LinkedBlockingDeque<Message> toDeleteQ = mapOfToDoMessages.get(m);
        // resolve all m toDoMessages to null
        if(toDeleteQ != null && !toDeleteQ.isEmpty()){
            for(Message currMessage : toDeleteQ)
                if( currMessage  instanceof Event)
                    complete((Event)currMessage, null);
        }
        // remove the queue of m from the toDoMessages
        mapOfToDoMessages.remove(m);
        // unsubscribe m for all the events & broadcasts he was subscribed to
        for(Class<? extends Message> currMessage : mapOfSubscribers.keySet()){
            ConcurrentLinkedQueue<Subscriber> currMessageQ = mapOfSubscribers.get(currMessage);
            synchronized (currMessageQ){
                currMessageQ.remove(m);
            }
        }
    }

    @Override
    public Message awaitMessage(Subscriber m) throws InterruptedException {
        LinkedBlockingDeque<Message> toPullFromQ = mapOfToDoMessages.get(m);
        return toPullFromQ.take(); // take() is a blocking method that will wait until Q is not empty
    }
}
