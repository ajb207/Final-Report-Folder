package dealwisCode;



/* This class is for storing the data gathered in other classes
 * the Bond class stores the atoms that form a bond as well as information
 * about the bond. Getter and Setter methods allow for the information to be changed.
 */

public class Bond {
  
	//the type of interaction this bond is, Hydrogen, van der Waals, etc
  private String type;
  
  //the first atom in the bond
  private Atom atom1;
  
  //the second atom in the bond
  private Atom atom2;
  
  //the distance between the atoms
  private double distance;
  
 
  //The getter setter methods for each value
  public void setType(String type){
    this.type = type;
  }
  
  public String getType(){
    return type;
  }
  
  public Atom getAtom1(){
    return atom1;
  }
  
  public Atom getAtom2(){
    return atom2;
  }
  
  public int getAtom1Number(){
    return atom1.getMolNumber();
  }
  
  public int getAtom2Number(){
    return atom2.getMolNumber();
  }
  
  public double getDistance(){
    return distance;
  }
  
  //The constructor method to form the bond
  public Bond(Atom atom1, Atom atom2, double distance){
    this.atom1 = atom1;
    this.atom2 = atom2;
    this.distance = distance;
  }
  
}
  
 

  
  
