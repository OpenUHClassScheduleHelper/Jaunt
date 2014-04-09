import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Collections;

import com.jaunt.Element;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;

/**
 * A wrapper class for the Jaunt API.
 * 
 * @author Rob Namahoe
 */
public class JauntObj {

	private String url = "https://www.sis.hawaii.edu/uhdad/avail.classes?i=MAN&t=201430&s=ICS";
	private static ArrayList<JauntRowItem> rowItems = new ArrayList<>(); // initial list
	private static ArrayList<JauntRowItem> meetingTimes = new ArrayList<>(); // final list to be returned (with all times/days merged)

	public JauntObj() {
		processUrl();
	}

	/**
	 * Constructor Method.
	 * 
	 * @param url
	 *            The url to scrape.
	 */
	public JauntObj(String url) {
		this.url = url;
		processUrl();
	}

	private void processUrl() {

		String temp;

		try {
			UserAgent userAgent = new UserAgent();
			userAgent.visit(this.url);
			List<Element> trs = userAgent.doc.findFirst("<table class=listOfClasses>").getEach("<tr>").toList();
		
			for (int i = 0; i < trs.size(); i++) { // iterate through <tr>s
				Element tr = trs.get(i);

				// Only process the row if there are more than 6 columns
				// otherwise it will crash.
				if (tr.getEach("<td|th>").toList().size() > 6) {
					// Only create a new row item if it is a valid CRN.
					temp = tr.getElement(1).innerText();
					if (isCrn(temp)) {
						JauntRowItem item = new JauntRowItem();
						item.setFocus(getFocus(tr.getElement(0).innerText())); // Focus
						item.setCrn(tr.getElement(1).innerText()); // CRN
						item.setCourse(tr.getElement(2).innerText()); // Course
						item.setSection(tr.getElement(3).innerText()); // Section
						item.setTitle(getTitle(tr.getElement(4).innerText())); // Title
						item.setInstructor(tr.getElement(6).innerText()); // Instructor
						item.setDays(tr.getElement(8).innerText()); // Day
						item.setTime(tr.getElement(9).innerText()); // Time
						item.setLocation(tr.getElement(10).innerText()); // Location
						rowItems.add(item);
						meetingTimes.add(item);
					} else {
						// Else if a 2nd row exists, merge it to the previous row (if valid)
						JauntRowItem meetItem = new JauntRowItem();
						
						StringBuilder builder = new StringBuilder(); // merge days together
						builder.append(rowItems.get(rowItems.size()-1).getDays());
						builder.append(" & ");
						builder.append(tr.getElement(7).innerText());
						
						StringBuilder builder2 = new StringBuilder();
						builder2.append(rowItems.get(rowItems.size()-1).getTime());
						builder2.append(" & ");
						builder2.append(tr.getElement(8).innerText());
						
						StringBuilder builder3 = new StringBuilder();
						builder3.append(rowItems.get(rowItems.size()-1).getLocation());
						builder3.append(" & ");
						builder3.append(tr.getElement(9).innerText());

						meetItem.setCrn(rowItems.get(rowItems.size()-1).getCrn()); // CRN
						meetItem.setDays(builder.toString()); // Day
						meetItem.setTime(builder2.toString()); // Time
						meetItem.setLocation(builder3.toString()); // Location
						meetingTimes.add(meetItem);
					}
				}
			}			
		} catch (JauntException e) {
			System.err.println(e);
		}
	}

	/**
	 * Checks if a string is a 5 digit number. 
	 * @param strNum The string to test.
	 * @return True if the string is also an integer, false otherwise.
	 */
	private static boolean isCrn(String strNum) {
		boolean ret = true;
		strNum.trim();
		if (strNum.length() != 5) {
			return false;
		}
		try {
			Integer.parseInt(strNum);
		} catch (NumberFormatException e) {
			ret = false;
		}
		return ret;
	}

	/**
	 * Get the Gen Ed/focus attribute of the course, if available.
	 * 
	 * @param focus
	 *            The focus.
	 * @return The valid focus string or a zero-length string if invalid.
	 */
	private static String getFocus(String focus) {
		String ret = focus;
		if (focus.indexOf("nbsp") > 0) {
			ret = "";
		}
		return ret;
	}

	/**
	 * Gets the course title without the following string "Restriction: ".
	 * 
	 * @param title
	 *            The title of the course.
	 * @return The title of the string.
	 */
	private static String getTitle(String title) {
		String ret = title.trim();
		int startIndex = title.indexOf("Restriction: ");
		if (startIndex > 0) {
			int endIndex = title.length();
			String toBeReplaced = ret.substring(startIndex, endIndex);
			ret = ret.replace(toBeReplaced, "");
		}
		return ret;
	}

	/**
	 * Get the list of results.
	 * @return The list of results.
	 */
	public static ArrayList<JauntRowItem> getResults() {
		return rowItems;
	}

	/**
	 * Print the results.
	 */
	public static void printResults() {
		for (JauntRowItem item : rowItems) {
			System.out.println(item.getFocus() + ", " + item.getCrn() + ", "
					+ item.getCourse() + ", " + item.getSection() + ", "
					+ item.getTitle() + ", " + item.getInstructor() + ", " + item.getDays());
		}
	}

	/**
	 * Print meeting list.
	 * This is full course listing w/ merged meeting times and null rows eliminated
	 */
	public static void printMeeting() {
		// Remove duplicates
		for (int i = 0; i < meetingTimes.size()-1; i++) {
		  if (meetingTimes.get(i).getCrn().compareTo(meetingTimes.get(i+1).getCrn()) == 0) {
			  // remove it
			  meetingTimes.remove(i);
		  }
		}
		
		// ascii header for the console, can be safely removed
		System.out.println("CRN, DATE, TIME, LOCATION");
		System.out.println("-----------------------------------------------------------");
		
		// print all courses
		for (JauntRowItem item : meetingTimes) {
			 System.out.println(item.getCrn() + ", " + item.getDays() + ", "
					+ item.getTime() + ", " + item.getLocation());
		}
		System.out.println("\ntotal courses: " + meetingTimes.size());
		
		// ART 176, CRN 86905 presents a problem with appending 'a' or 'p' to the time
		// Maybe make the start times follow some sort of rule
		// i.e. always append a p unless <something>
	}
}
