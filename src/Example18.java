import com.jaunt.*;
import com.jaunt.component.*;
import java.util.*;

public class Example18{
  public static void main(String[] args){
    String url="https://www.sis.hawaii.edu/uhdad/avail.classes?i=MAN&t=201430&s=ICS";
    JauntObj jaunt = new JauntObj(url);
    
    ArrayList<JauntRowItem> results = JauntObj.getResults();
    
    for (JauntRowItem item : results) {
      System.out.println(item.getCrn());
    }
    
    
    
  }
}