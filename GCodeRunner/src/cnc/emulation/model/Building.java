package cnc.emulation.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Building {

	private List<Brick> bricks = Collections.synchronizedList(new LinkedList<Brick>());
	
	public List<Brick> getBricks() {
		return bricks;
	}

	public void putTheBrick(Brick brick) {
		
		if(bricks == null)	{
			bricks = Collections.synchronizedList(new LinkedList<Brick>());
		}
		bricks.add(brick);
	}	
	
	public String toString() {
		
		StringBuilder res = new StringBuilder();
		if(bricks != null) {
			for(Brick b : bricks) {
				res.append(b.toString() + "\r\n");
			}
		}
		return res.toString();
	}
}
