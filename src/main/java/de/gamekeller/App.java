package de.gamekeller;

import de.gamekeller.DataTypes.Client;
import de.gamekeller.Parser.DatabaseParser;

public class App {

    public static void main(String[] args){

        //DatabaseParser.writeMongo();
        DatabaseParser parser = new DatabaseParser("mongodb://localhost:27017", "Ts", "TS");
        Client client = parser.readClientFromUUID("3");
        System.out.println("fertsch");

    }


}
