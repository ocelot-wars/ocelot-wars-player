package com.github.ocelotwarsplayer.strategy.basic;

import java.util.ArrayList;
import java.util.List;

import com.github.ocelotwarsplayer.playground.api.Playground;
import com.github.ocelotwarsplayer.playground.api.Tile;
import com.github.ocelotwarsplayer.playground.api.Unit;

public class PlaygroundBuilder {

	private static List<List<Tile>> tiles;


	public static PlaygroundBuilder aPlayground() {
		return new PlaygroundBuilder();
	}
	
	private PlaygroundBuilder() {
		tiles = createEmptyTiles();
	}
	
	
	private static List<List<Tile>> createEmptyTiles() {
		List<List<Tile>> tiles = new ArrayList<>(new ArrayList<>());

		for (int j = 0; j < 10; j++) {
			List<Tile> tilesRow = new ArrayList<Tile>();
			for (int i = 0; i < 10; i++) {
				tilesRow.add(new Tile());
			}
			tiles.add(tilesRow);
		}
		return tiles;
	}

	public Playground create() {
		Playground playground = new Playground();
		playground.setTiles(tiles);
		return playground;
	}


	public PlaygroundBuilder withUnit(UnitBuilder unitBuilder) {
		Unit unit = unitBuilder.create();
		tiles.get(unitBuilder.getRow()).get(unitBuilder.getColumn()).getAssets().add(unit);
		return this;
	}

}
