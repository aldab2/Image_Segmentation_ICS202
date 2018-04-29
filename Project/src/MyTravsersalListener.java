import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListenerAdapter;
import org.jgrapht.event.VertexTraversalEvent;

public class MyTravsersalListener<V, E> extends TraversalListenerAdapter<V, E> {

	public int  startOfNewTree = 0;
	public int tmp= startOfNewTree;
	public boolean  isStartOfNewTree = false;
	@Override
	public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
	isStartOfNewTree = false;
	
		super.connectedComponentStarted(e);
	}
/*	@Override
	public void edgeTraversed(EdgeTraversalEvent<E> e) {
	
		if( tmp == startOfNewTree)
			isStartOfNewTree = false;
		else 
			isStartOfNewTree = true;
		super.edgeTraversed(e);
	}*/
/*	@Override
	public void vertexTraversed(VertexTraversalEvent<V> e) {
		if( tmp == startOfNewTree)
			isStartOfNewTree = false;
		else 
			isStartOfNewTree = true;
		super.vertexTraversed(e);
	}
	
	@Override
	public void vertexFinished(VertexTraversalEvent<V> e) {
	

		if( tmp == startOfNewTree)
			isStartOfNewTree = false;
		else 
			isStartOfNewTree = true;
		
		super.vertexFinished(e);
	}*/

	@Override
	public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
		startOfNewTree +=10;
		/*
		if( tmp == startOfNewTree)
			isStartOfNewTree = false;
		else 
			isStartOfNewTree = true;*/
	isStartOfNewTree = true;
		super.connectedComponentFinished(e);
	}
	
	
}
