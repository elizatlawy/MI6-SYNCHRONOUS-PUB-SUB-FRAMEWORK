package bgu.spl.mics.application.passiveObjects;

import bgu.spl.mics.Printer;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Passive object representing the diary where all reports are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private fields and methods to this class as you see fit.
 */
public class Diary {
	private List<Report> reports;
	private AtomicInteger total;

	private static class DiaryHolder{
		private static Diary instance = new Diary();
	}

	private Diary() {
		reports = new LinkedList<>();
		total = new AtomicInteger();
		total.set(0);
	}
	/**
	 * Retrieves the single instance of this class.
	 */
	public static Diary getInstance() {
		return DiaryHolder.instance;
	}

	public List<Report> getReports() {
		return reports;
	}

	/**
	 * adds a report to the diary
	 * @param reportToAdd - the report to add
	 */
	public void addReport(Report reportToAdd){
		synchronized (reports){
			reports.add(reportToAdd);
		}
	}

	/**
	 *
	 * <p>
	 * Prints to a file name @filename a serialized object List<Report> which is a
	 * List of all the reports in the diary.
	 * This method is called by the main method in order to generate the output.
	 */
	public void printToFile(String filename){
		// print number of received missions (total field)
		Printer.Print(total,filename);
		// print reports
		LinkedList<Report> reportsToPrint = new LinkedList<>(reports);
		Printer.Print(reportsToPrint,filename);


	}

	/**
	 * Gets the total number of received missions (executed / aborted) be all the M-instances.
	 * @return the total number of received missions (executed / aborted) be all the M-instances.
	 */
	public int getTotal(){
		return total.intValue();
	}


	/**
	 * Increments the total number of received missions by 1
	 */
	public void incrementTotal() {
		total.compareAndSet(total.intValue(),total.intValue()+1);
	}
	// TODO: delete - not allowed public methods
	@Override
	public String toString() {
		return "Diary{" +
				"reports= " + reports.toString() + '\n' +
				", total= " + total +
				'}';
	}
}
