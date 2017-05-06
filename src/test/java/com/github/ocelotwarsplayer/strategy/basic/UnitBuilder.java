package com.github.ocelotwarsplayer.strategy.basic;

import com.github.ocelotwarsplayer.playground.api.Unit;

public class UnitBuilder {

	private int row;
	private int column;
	private int id;
	private String playerName;

	public static UnitBuilder aUnit() {
		return new UnitBuilder();
	}

	public UnitBuilder withPosition(int row, int column) {
		this.row = row;
		this.column = column;
		return this;
	}

	public UnitBuilder withId(int id) {
		this.id = id;
		return this;
	}

	public UnitBuilder withPlayer(String playerName) {
		this.playerName = playerName;
		return this;
	}

	public Unit create() {
		com.github.ocelotwarsplayer.playground.api.Unit unit = new com.github.ocelotwarsplayer.playground.api.Unit();
		unit.setId(id);
		unit.setPlayer(playerName);
		return unit;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}

}
