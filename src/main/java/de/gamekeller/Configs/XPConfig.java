package de.gamekeller.Configs;

import java.util.ArrayList;
import java.util.List;

import de.gamekeller.DataTypes.Rank;

public class XPConfig {
    public List<Integer> levelXpKey;
    public List<Rank> nextRank;
    public int nextXpKey;
    public int tickLength;
    public int boostDuration;
    public int eachTick;
    public int eachTickBoosted;

    public XPConfig(List<Integer> levelXpKey, int nextXpKey, int tickLength, int boostDuration, int eachTick, int eachTickBoosted){
        this.levelXpKey = levelXpKey;
        this.nextXpKey = nextXpKey;
        this.tickLength = tickLength;
        this.boostDuration = boostDuration;
        this.eachTick = eachTick;
        this.eachTickBoosted = eachTickBoosted;
    }

    public XPConfig(){
        this.levelXpKey = new ArrayList<>();
        this.nextRank = new ArrayList<>();
    }

}
