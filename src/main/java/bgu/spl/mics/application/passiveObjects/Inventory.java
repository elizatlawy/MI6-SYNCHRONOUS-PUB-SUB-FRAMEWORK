package bgu.spl.mics.application.passiveObjects;

import bgu.spl.mics.Printer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * That's where Q holds his gadget (e.g. an explosive pen was used in GoldenEye, a geiger counter in Dr. No, etc).
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private fields and methods to this class as you see fit.
 */
public class Inventory {

    private List<String> gadgets;

    private static class InventoryHolder {
        private static Inventory instance = new Inventory();
    }

    private Inventory() {
        gadgets = new LinkedList<>();
    }

    /**
     * Retrieves the single instance of this class.
     */
    public static Inventory getInstance() {
        return InventoryHolder.instance;
    }

    /**
     * Initializes the inventory. This method adds all the items given to the gadget
     * inventory.
     * <p>
     *
     * @param inventory Data structure containing all data necessary for initialization
     *                  of the inventory.
     */
    public void load(String[] inventory) {
        this.gadgets.addAll(Arrays.asList(inventory));
    }

    /**
     * acquires a gadget and returns 'true' if it exists.
     * <p>
     *
     * @param gadget Name of the gadget to check if available
     * @return ‘false’ if the gadget is missing, and ‘true’ otherwise
     */
    public boolean getItem(String gadget) {
        synchronized (gadgets) {
            if (gadgets.contains(gadget)) {
                gadgets.remove(gadget);
                return true;
            }
            return false;
        }
    }

    /**
     * <p>
     * Prints to a file name @filename a serialized object List<String> which is a
     * list of all the of the gadgeds.
     * This method is called by the main method in order to generate the output.
     */
    public void printToFile(String filename) {
        //TODO: Implement this
        LinkedList<String> gadgetsToPrint = new LinkedList<>(gadgets);
        Printer.Print(gadgetsToPrint,filename);

    }

    @Override
    public String toString() {
        String output = "[";
        for (String currGadget : gadgets)
            output = output + "\"" + currGadget + "\"" + ",";
        output = output.substring(0, output.length() - 1) + "]";
        return output;
    }
}
