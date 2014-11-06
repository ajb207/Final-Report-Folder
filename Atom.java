package dealwisCode;


/*This class is used to store information on each atom that
 * is pulled form the PDB file. Getter and Setter methods allow
 * values to be pulled and changed when needed.
 */

public class Atom {
  
  //determines the number of the atom in the chain of the molecule 
  private int molNumber;
  
  //determines on which chain the atom is located
  private String chain;
  
  //determines what atom is here by atomic number
  private int atomicNumber;
  
  //the X coordinate of the atom
  private double xCoord;
  
  //the Y coordinate of the atom
  private double yCoord;
  
  //the Z coordinate of the atom
  private double zCoord;
  
  //the valence of the atom. This isn't used now, but might be later
  private String valence;
  
  //the full atomic symbol of the group the atom belongs too
  private String fullSymbol;
  
  //the atomic symbol of the atom
  private String symbol;
  
  //the amino acid or ligand residue the atom is located.
  private String residue;
  
  //the number of the ligand or residue in the overall strucutre.
  private int residueNumber;
  
  //this constructor creates a blank Atom
  //that must be built by the program classes
  public Atom(){
  }
  
  //The getter setter methods for each value
  public void setSymbol(String symbol){
    this.symbol = symbol;
  }
  
  public String getSymbol(){
    return symbol;
  }
  
  public void setFullSymbol(String fullSymbol){
    this.fullSymbol = fullSymbol;
  }
  
  public String getFullSymbol(){
    return fullSymbol;
  }
  
  public void setMolNumber(int molNumber){
    this.molNumber = molNumber;
  }

  public int getMolNumber(){
    return molNumber;
  }
  
  public void setChain(String chain){
    this.chain = chain;
  }
  
  public String getChain(){
    return chain;
  }
  
  public void setResidue(String residue){
    this.residue = residue;
  }
  
  public String getResidue(){
    return residue;
  }
  
  public void setResidueNumber(int residueNumber){
    this.residueNumber = residueNumber;
  }
  
  public int getResidueNumber(){
    return residueNumber;
  }
  
  public void setAtomicNumber(int atomicNumber){
    this.atomicNumber = atomicNumber;
  }
  
  public int getAtomicNumber(){
    return atomicNumber;
  }
  
  public void setXCoord(double xCoord){
    this.xCoord = xCoord;
  }
  
  public double getXCoord(){
    return xCoord;
  }
  
  public void setYCoord(double yCoord){
    this.yCoord = yCoord;
  }
  
  public double getYCoord(){
    return yCoord;
  }
  
  public void setZCoord(double zCoord){
    this.zCoord = zCoord;
  }
  
  public double getZCoord(){
    return zCoord;
  }
}
