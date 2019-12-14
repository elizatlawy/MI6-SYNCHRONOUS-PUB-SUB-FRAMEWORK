package bgu.spl.mics;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The {@link MessageBrokerImpl class is the implementation of the MessageBroker interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBrokerImpl implements MessageBroker {
    private ConcurrentHashMap<Class<? extends Message>, ConcurrentLinkedQueue<RunnableSubPub>> mapOfSubscribers = new ConcurrentHashMap<>();

    private ConcurrentHashMap<RunnableSubPub, BlockingQueue<Message>> mapOfToDoMessages = new ConcurrentHashMap<>();

    private ConcurrentHashMap<Event, Future> events = new ConcurrentHashMap<>();

    public MessageBrokerImpl() {
    }


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
            ConcurrentLinkedQueue<RunnableSubPub> toAddQ = mapOfSubscribers.get(type);
            if (!toAddQ.contains(m))
                toAddQ.add(m);
        }
    }

    @Override
    public <T> void complete(Event<T> e, T result) {
        Future<T> f = events.get(e);
        if (f != null)
            f.resolve(result);
    }

    @Override
    public void sendBroadcast(Broadcast b) {
        if (mapOfSubscribers.containsKey(b.getClass())) {
            synchronized (mapOfSubscribers.get(b.getClass())) {
                ConcurrentLinkedQueue<RunnableSubPub> subscribersOfBroadcast = mapOfSubscribers.get(b.getClass());
                for (RunnableSubPub currSubscriber : subscribersOfBroadcast)
                    mapOfToDoMessages.get(currSubscriber).add(b);
            }
        }
    }


    @Override
    public <T> Future<T> sendEvent(Event<T> e) {
        Future future = new Future();
        RunnableSubPub currSubscriber = null;
		synchronized (e.getClass()) {
			ConcurrentLinkedQueue<RunnableSubPub> subscribersOfEvent = mapOfSubscribers.get(e.getClass());
			if (subscribersOfEvent != null && !subscribersOfEvent.isEmpty()) {
				currSubscriber = subscribersOfEvent.poll();
				subscribersOfEvent.add(currSubscriber);
			}
		}

		if (currSubscriber != null) {
			events.put(e, future);
			synchronized (currSubscriber) {
				BlockingQueue<Message> todoQ = mapOfToDoMessages.get(currSubscriber);
				if (todoQ != null) {
					todoQ.add(e);
				} else {
					future.resolve(null);
				}
			}
		}
		else {
			future.resolve(null);
		}

        return future;
    }

    @Override
    public void register(Subscriber m) {
        // TODO Auto-generated method stub

    }

    @Override
    public void unregister(Subscriber m) {
        // TODO Auto-generated method stub

    }

    @Override
    public Message awaitMessage(Subscriber m) throws InterruptedException {
        // TODO Auto-generated method stub
        return null;
    }


}
