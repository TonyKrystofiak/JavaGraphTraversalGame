package map;

import java.util.*;
import java.io.*;

public class GraphDriver{
  
  public static void displayGraphInfo(RoomGraph g) {
    System.out.println("Graph g has " + g.getNumV() + " vertices");
    System.out.println("Is graph directed?  " + g.isDirected());
    System.out.println("Show all edges:");
    for(int v = 0; v<g.getNumV();v++){
      Iterator<RoomEdge> edges = g.getEdgeIterator(v);
      if (edges!=null){
        while(edges.hasNext())
          System.out.println(edges.next());
      }
      else{
        System.out.println("DUDE! Complete me before printing");
      }
    }
	  System.out.println("Show all rooms:");
	  Map<Integer, Room> roomMap = g.getRoomMap();
	  for(int r = 0; r<g.getNumV();r++){
		  Room curRoom = roomMap.get(r);
		  System.out.println("Room: " + r + " " + curRoom);
	  }
  }
  
  public static void main(String[] args){
	RoomGraph g;
    System.out.println("*********************************** Unweighted, Undirected ");    
    g = new GameLayout("UnweightedData.txt",false, null);
    GraphDriver.displayGraphInfo(g);
    //System.out.println(g.keySearch(4, 9));
  }
  
}
