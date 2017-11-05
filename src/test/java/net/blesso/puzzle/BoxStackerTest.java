package net.blesso.puzzle;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import net.blesso.puzzle.BoxStacker.Box;

public class BoxStackerTest {
	
	private List<Box> boxes;
	
	@Before
	public void setup(){
		boxes = new ArrayList<>();
	}

	private void addBox(int height, int width, int length){
		boxes.add(new Box(height, width, length));
	}
	
	@Test
	public void whenAllSameSize() {
		addBox(5, 5, 5);
		addBox(5, 5, 5);
		addBox(5, 5, 5);
		addBox(5, 5, 5);
		assertThat(new BoxStacker().maxStackBox(boxes).bestStackHeight, CoreMatchers.equalTo(5.0));
	}
	
	@Test
	public void whenAllInOrder() {
		addBox(1, 1, 1);
		addBox(2, 2, 2);
		addBox(3, 3, 3);
		addBox(4, 4, 4);
		assertThat(new BoxStacker().maxStackBox(boxes).bestStackHeight, CoreMatchers.equalTo(10.0));
	}
	
	@Test
	public void skipOneDimension() {
		addBox(1, 1, 1);
		addBox(3, 3, 100);
		addBox(4, 4, 4);
		assertThat(new BoxStacker().maxStackBox(boxes).bestStackHeight, CoreMatchers.equalTo(5.0));
	}
	
	@Test
	public void whenNoneCanStackThenPickTallest() {
		addBox(1, 100, 1);
		addBox(17, 2, 2);
		addBox(3, 3, 100);
		addBox(4, 4, 4);
		assertThat(new BoxStacker().maxStackBox(boxes).bestStackHeight, CoreMatchers.equalTo(17.0));
	}

}
