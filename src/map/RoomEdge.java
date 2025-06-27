/**
 * Defines the properties of the RoomEdge
 * @author Tony Krystofiak
 * @Date 12/12/2023
 */
package map;

/** 
 * An Edge represents a relationship between two vertices
 */
public class RoomEdge implements Comparable<RoomEdge>{
  
  // Data Fields
  /** The source vertex */
  private int source;
  
  /** The destination vertex */
  private int dest;
  
  /** The side of the source vertex the path extends from */
  private String sourceSide;
  
  /** The side of the dest vertex the path extends to */
  private String destSide;
  
  // Constructor
  /** Construct an Edge with a source of from and a destination of to. 
    * extends from source side, extends to dest side
    * @param source - The source vertex
    * @param dest - The destination vertex
    * @param sourceSide - The side of the source vertex
    * @param destSide - The side of the destination vertex
    */
  public RoomEdge(int source, int dest, String sourceSide, String destSide) {
    this.source = source;
    this.dest = dest;
    this.sourceSide = sourceSide;
    this.destSide = destSide;
  }
  
  // Methods
  /** Get the source
    * @return The value of source
    */
  public int getSource() {
    return this.source;
  }
  
  /** Get the destination
    * @return The value of dest
    */
  public int getDest() {
    return this.dest;
  }
  /** Get the source side
   * @return The value of source side
   */
  public String getSourceSide() {
	return sourceSide;
  }
  /** Get the dest side
   * @return The value of dest side
   */
  public String getDestSide() {
	return destSide;
  }
	
  
  /** Return a String representation of the edge
    * @return A String representation of the edge
    */
  public String toString() {
    return "[(Source: " + this.getSource() + " | Source Side: " + this.getSourceSide() + 
    		"), Dest: " + this.getDest() + " | Dest Side: " + this.getDestSide() + ")]";
  }

  /** Return a hash code for an edge. The hash code is the source shifted left 16 
    * bits exclusive OR with the dest
    * @return a hash code for an edge
    */
  public int hashCode() {
    return (this.source << 16) ^ this.dest;
  }

@Override
public int compareTo(RoomEdge o) {
	// TODO Auto-generated method stub
	return 0;
}

}
 

