package javafoundations;
import java.util.List;

/*******************************************************************
 *  GraphADT.java    
 *  @author CS230 Staff 
 *  @version 1.0 
 *  @date 2025.11.01
 *  API for an UNDIRECTED graph data structure.
 *******************************************************************/
public interface GraphADT<T>
{
    /** Saves the current graph into a .tgf file.
     * If it cannot write the file, a message is printed. */
    public void saveTGF(String tgf_file_name);

    /** Returns true if this graph is empty, false otherwise. */
    public boolean isEmpty();

    /** Returns the number of vertices in this graph. */
    public int getNumVertices();

    /** Returns the number of edges in this graph. */
    public int getNumEdges();

    /** Returns true iff an edge exists between the given vertices. */
    public boolean isEdge(T vertex1, T vertex2);

    /** Adds a vertex to this graph, associating the object with vertex.
     * If the vertex already exists, nothing is inserted. */
    public void addVertex(T vertex);

    /** Removes a single vertex with the given value from this graph.
     * If the vertex does not exist, it does not change the graph. */
    public void removeVertex(T vertex);

    /** Inserts an edge between two vertices of this graph.
     * If the vertices exist. Else it does not change the graph. */
    public void addEdge(T vertex1, T vertex2);

    /** Removes an edge between two vertices of this graph.
     * If the vertices exist. Else it does not change the graph. */
    public void removeEdge(T vertex1, T vertex2);

    /** Returns a string representation of the graph. */
    public String toString();

    /** Returns the list of neighbors for a vertex. */
    public List<T> getNeighbors(T vertex);
}

