package com.github.ocelotwarsplayer;

import java.util.List;

public class Tile {

    private int resources;
    private List<Asset> assets;
   
    public Tile(int resources, List<Asset> assets) {
        this.resources = resources;
        this.assets = assets;
    }
    
    public int getResources() {
        return resources;
    }
    
    public List<Asset> getAssets() {
        return assets;
    }
}
