package net.blesso.puzzle;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * From McDowell, chapter 8, question 8.13.
 * @author francis
 */
public class BoxStacker {
	
	
	/**
	 * Finds the maximum height of a stack of boxes. For a two boxes to be stackable, the bottom box must be
	 * strictly bigger than the top box in all dimensions.
	 * @param boxes the boxes to check
	 * @return the Box, which is the bottom of the maxiumal stack. 
	 */
	public Box maxStackBox(List<Box> boxes){
		//sorts the boxes in ascending order based on height
		Collections.sort(boxes, new HeightComparator());
		Box maxBox = null;
		for (int i=0; i < boxes.size(); i++) {
			final Box currentBox = boxes.get(i);
			final Box tallestSmallerBox = findSmallerBoxWithHighestStack(currentBox, boxes, i);
			if (tallestSmallerBox == null) {
				//nothing can stack upon this box, so the best stack height is its own height
				currentBox.bestStackHeight = currentBox.height;
			} else {
				//otherwise add the best box's height to the curr box's height
				currentBox.bestStackHeight = tallestSmallerBox.bestStackHeight + currentBox.height;
			}
			if (maxBox == null || maxBox.bestStackHeight < currentBox.bestStackHeight) {
				maxBox = currentBox;
			}
		}
		return maxBox;
		
	}
		
	private Box findSmallerBoxWithHighestStack(Box currentBox, List<Box> boxes, int currBoxIndex) {
		Box tallestStackableBox = null; 
		//search through all the boxes smaller than the current box
		for (int pos = currBoxIndex -1; pos >=0; pos--) {
			final Box smallerBox = boxes.get(pos);
			if (currentBox.canStackBeneth(smallerBox)) {
				if (tallestStackableBox == null || tallestStackableBox.bestStackHeight < smallerBox.bestStackHeight) {
					tallestStackableBox = smallerBox;
				}
			}
		}
		return tallestStackableBox;
	}

	public static class Box {
		private final int height;
		private final int width;
		private final int length;
		
		/** Height of the tallest stack of boxes that can stack upon this box */
		double bestStackHeight;
		
		public Box(int height, int width, int length){
			assert(height > 0);
			assert(width > 0);
			assert(length > 0);
			
			this.height = height;
			this.width = width;
			this.length = length;
		}
		
		boolean canStackBeneth(Box other) {
			return other.height < this.height &&
					other.width < this.width &&
					other.length < this.length;
		}
	}
	
	private static class HeightComparator implements Comparator<Box> {
		@Override
		public int compare(Box b1, Box b2) {
			if (b1.height == b2.height) {
				return 0;
			} else if (b1.height < b2.height) {
				return -1;
			} else {
				return 1;
			}
		}
	}

}
