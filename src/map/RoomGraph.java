/**
 * Interface of the Game map
 * @Date 12/12/2023
 * @author Tony Krystofiak
 */
package map;

import java.util.*;


public interface RoomGraph { 
  /** 
   * Return the number of vertices.
   * @return The number of vertices
   */
  int getNumV();
  
  /** Return the Map graph of Integer --> Room data
   * @return the map
   */
  Map<Integer, Room> getRoomMap();
  
  /** Determine whether this is a directed graph.
    * @return true if this is a directed graph
    */
  boolean isDirected();
  
  /** Return a settor to the edges connected to a given vertex.
    * @param source The source vertex
    * @return An Iterator<Edge> to the vertices connected to source
    */
  Iterator<RoomEdge> getEdgeIterator(int source);

  String getRoomData(int i);

  void getEdgeData(int i);

  void save();

  void load();

  int keySearch(int i, int j);
}
