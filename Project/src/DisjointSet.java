import java.awt.List;
import java.util.ArrayList;
import java.util.Set;

public class  DisjointSet{
	private Integer[] set;		//the disjoint set as an array

	public Integer[] getSet(){		//mostly debugging method to print array
		return set;
	}

	public DisjointSet(ArrayList<Integer> vertices) {		//constructor creates singleton sets
		set = new Integer [vertices.size()];
		for(Integer i = 0; i < set.length; i++){		//initialize to -1 so the trees have nothing in them
			set[i] = vertices.get(i);
		}
	}

	
	public void union(Integer root1, Integer root2) {
		if(set[root2] < set[root1]){		// root2 is deeper
			set[root1] = root2;		// Make root2 new root
		}
		else {
			if(set[root1] == set[root2]){
				set[root1]--;			// Update height if same
			}
			set[root2] = root1;		// Make root1 new root
		}
	}

	
/*	public Integer find(Integer x) {
		if(set[x] < 0){		//If tree is a root, return its index
			return x;
		}
		Integer next = x;		
		while(set[next] > 0){		//Loop until we find a root
			next=set[next];
		}
		return next;
	}*/
	public Integer find(int i) {

	    int p = set[i];
	    if (i == p) {
	      return i;
	    }
	    return set[i] = find(p);

	  }
}