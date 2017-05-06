package com.github.ocelotwarsplayer.strategy.basic;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.ocelotwarsplayer.PlayerName;
import com.github.ocelotwarsplayer.playground.api.Asset;
import com.github.ocelotwarsplayer.playground.api.Playground;

public class BasicStrategy {

	private PlayerName playerName;

	public BasicStrategy(PlayerName playerName) {
		this.playerName = playerName;
	}

	public void calculateMoves(Playground playground) {

		List<Unit> units = getUnits(playground);

	}

	List<Unit> getUnits(Playground playground) {
		// TODO this selects the player's units, but information about their
		// position and other information about resources, headquarters etc. is
		// lost. Better domain Model is needed see #1
		Stream<Asset> assets = playground.getTiles().stream()
			.flatMap(l -> l.stream())
			.map(t -> t.getAssets())
			.flatMap(l -> l.stream());
		List<Unit> units = assets
			.filter(a -> playerName.getName().equals(a.getPlayer()))
			.filter(a -> a instanceof com.github.ocelotwarsplayer.playground.api.Unit)
			.map(u -> new Unit((com.github.ocelotwarsplayer.playground.api.Unit) u))
			.collect(Collectors.toList());
		return units;
	}

}
