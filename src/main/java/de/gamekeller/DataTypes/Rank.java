package de.gamekeller.DataTypes;

public class Rank {
    public String title;
    public int requiredLevel;
    public int relativeRank;

    private static Rank[] instances = new Rank[100];

    private Rank(int rank, int nextLevel, String title){
        this.title = title;
        this.requiredLevel = nextLevel;
    }

    public static synchronized Rank getInstance(String title, int nextRank, int relativeRank) {
        if (instances[relativeRank] == null) {
            instances[relativeRank] = new Rank(relativeRank, nextRank, title);
        }
        return instances[relativeRank];
    }


    public static synchronized Rank getInstance(int relativeRank){
        if (instances[relativeRank] == null) {
            try {
                throw new Exception("Rank not found in config");
            } catch (Exception e) {}
        }
        return instances[relativeRank];
    }

}
