
/**
 * Represents a table row extracted by the Jaunt API.
 * @author Rob Namahoe
 */
public class JauntRowItem {
    private String focus;
    private String crn;
    private String course;
    private String section;
    private String title;
    private String instructor;
    private String days;
    private String time;
    private String location;
    
    /**
     * Default Constructor Method.
     */
    public JauntRowItem() {
      // Default Constructor Method.
    }
    
    /**
     * Meeting times constructor.
     * @param crn the crn used for comparison later
     * @param days the days of the course
     * @param time the time that the course meets
     * @param location the location of the course (on the given day)
     */
    public JauntRowItem(String crn, String days, String time, String location) {
    	this.setCrn(crn);
    	this.setDays(days);
    	this.setTime(time);
    	this.setLocation(location);
    }
    
    /**
     * @param focus Gen.Ed./Focus attribute. 
     * @param crn the crn of the course.
     * @param course The course number, i.e. 'ICS 311'.
     * @param section The couse section.
     * @param title The course title, i.e. 'Algorithms'.
     * @param instructor The instructor.
     * @param days days of the course
     * @param time the time that the course meets
     */
    public JauntRowItem(String focus, String crn, String course, String section, String title, String instructor, String days, String time) {
      this.setFocus(focus);
      this.setCrn(crn);
      this.setCourse(course);
      this.setSection(section);
      this.setTitle(title);
      this.setInstructor(instructor);
      this.setDays(days);
      this.setTime(time);
    }

    /**
     * @return the focus
     */
    public String getFocus() {
      return focus;
    }

    /**
     * @param focus the focus to set
     */
    public void setFocus(String focus) {
      this.focus = focus;
    }

    /**
     * @return the crn
     */
    public String getCrn() {
      return crn;
    }

    /**
     * @param crn the crn to set
     */
    public void setCrn(String crn) {
      this.crn = crn;
    }

    /**
     * @return the course
     */
    public String getCourse() {
      return course;
    }

    /**
     * @param course the course to set
     */
    public void setCourse(String course) {
      this.course = course;
    }

    /**
     * @return the section
     */
    public String getSection() {
      return section;
    }

    /**
     * @param section the section to set
     */
    public void setSection(String section) {
      this.section = section;
    }

    /**
     * @return the title
     */
    public String getTitle() {
      return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
      this.title = title;
    }

    /**
     * @return the instructor
     */
    public String getInstructor() {
      return instructor;
    }

    /**
     * @param instructor the instructor to set
     */
    public void setInstructor(String instructor) {
      this.instructor = instructor;
    }

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
