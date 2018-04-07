package com.github.ocelotwarsplayer;

public class Unit extends Asset {

    private int id;
    private int load;

    public Unit(int id, int load) {
        this.id = id;
        this.load = load;
    }

    public int getId() {
        return id;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    public int getLoad() {
        return load;
    }

}
