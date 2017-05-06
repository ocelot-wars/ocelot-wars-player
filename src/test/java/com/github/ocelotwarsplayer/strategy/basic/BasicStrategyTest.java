package com.github.ocelotwarsplayer.strategy.basic;

import static com.github.ocelotwarsplayer.strategy.basic.PlaygroundBuilder.aPlayground;
import static com.github.ocelotwarsplayer.strategy.basic.UnitBuilder.aUnit;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.github.ocelotwarsplayer.PlayerName;
import com.github.ocelotwarsplayer.playground.api.Playground;

public class BasicStrategyTest {
	
	private static final String PLAYER = "player";
	private BasicStrategy strategy = new BasicStrategy(new PlayerName(PLAYER));

	@Test
	public void testGetUnits_ForPlaygroundWithoutUnits() throws Exception {

		Playground playground = aPlayground().create();
		
		List<Unit> result = strategy.getUnits(playground);
		
		assertThat(result, is(empty()));
	}
	
	@Test
	public void testGetUnits_ForPlaygroundWithOneUnit() throws Exception {
		
		Playground playground = aPlayground()
			.withUnit(aUnit()
				.withPosition(3, 7)
				.withId(42)
				.withPlayer(PLAYER))
			.create();
		
		List<Unit> result = strategy.getUnits(playground);
		
		assertThat(result, hasSize(1));
	}

	@Test
	public void testGetUnits_OnlyOwnUnits() throws Exception {

		Playground playground = aPlayground()
			.withUnit(aUnit()
				.withPosition(3, 7)
				.withId(42)
				.withPlayer(PLAYER))
			.withUnit(aUnit()
				.withPosition(1,  1)
				.withId(21)
				.withPlayer("otherPlayer"))
			.create();
		
		List<Unit> result = strategy.getUnits(playground);
		
		assertThat(result, hasSize(1));
	}	

}
