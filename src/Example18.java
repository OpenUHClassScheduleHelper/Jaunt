import com.jaunt.*;
import com.jaunt.component.*;

public class Example18{
  public static void main(String[] args){
    try{
      UserAgent userAgent = new UserAgent(); 
      userAgent.visit("https://www.sis.hawaii.edu/uhdad/avail.classes?i=MAN&t=201430&s=ICS");
      Table table = userAgent.doc.getTable("<table class= listOfClasses>");
      
      System.out.println("\nColumn having 'Time'");
      Elements elements = table.getCol("Time");
      for (Element element : elements) {
        //System.out.println();
        System.out.println(element.getText());
      }
      
    }
    catch(JauntException e){
      System.err.println(e);
    }
  }
}