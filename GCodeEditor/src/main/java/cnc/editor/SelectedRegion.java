package cnc.editor;


/**
 * measured in view coordinates: pixel
 */
public class SelectedRegion {
	
	private boolean selectionStarted = false;
	private int startX;
	private int startY;
	private int endX;
	private int endY;
	
	public boolean isSelectionStarted() {
		return selectionStarted;
	}

	public void startSelection(int x, int y){
		startX = x;
		startY = y;
		endX = x;
		endY = y;
		selectionStarted = true;
	}
	
	public void setEndOfSelection(int x, int y){
		
		if(!selectionStarted){
			throw new RuntimeException("selection was not started");
		}
		endX = x;
		endY = y;
	}
	
	public int getStartX() {
		return startX;
	}
	public int getStartY() {
		return startY;
	}
	public int getEndX() {
		return endX;
	}
	public int getEndY() {
		return endY;
	}

	public void clear() {
		startX = 0;
		startY = 0;
		endX = 0;
		endY = 0;
		selectionStarted = false;
	}
}
