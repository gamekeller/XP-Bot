        // // // // File file = new File("Database.json");
        // // // // try {
        // // // //     JsonNode rootNode = new ObjectMapper().readTree(file);
        // // // //     JsonNode usersNode = rootNode.path("users");
        // // // //     for (JsonNode userNode : usersNode) {
        // // // //         JsonNode connectionsNode = userNode.path("teamspeakConnections");
        // // // //         for (JsonNode connectionNode : connectionsNode) {
        // // // //             String connectionUuid = connectionNode.path("uuid").asText();
        // // // //             if (uuid.equals(connectionUuid)) {
        // // // //                 Client client = new Client();
        // // // //                 client.teamspeakConnection. = connectionNode.path("databaseID").asInt();
        // // // //                 String nickname = connectionNode.path("nickname").asText();
        // // // //                 long connectedAt = connectionNode.path("connectedAt").asLong();
        // // // //                 boolean teamspeakLinked = userNode.path("teamspeakLinked").asBoolean();
        // // // //                 boolean teamspeakOnline = userNode.path("teamspeakOnline").asBoolean();
        // // // //                 int rank = userNode.path("rank").asInt();
        // // // //                 JsonNode xpNode = userNode.path("xp");
        // // // //                 int currentXp = xpNode.path("current").asInt();
        // // // //                 int totalXp = xpNode.path("total").asInt();
        // // // //                 int boostMinutesRemaining = xpNode.path("boostMinutesRemaining").asInt();
        // // // //                 int level = userNode.path("level").asInt();
        // // // //                 boolean consent = userNode.path("consent").asBoolean();
        // // // //                 JsonNode trackingNode = userNode.path("teamspeakTracking");
        // // // //                 long activeTime = trackingNode.path("activeTime").asLong();
        // // // //                 long onlineTime = trackingNode.path("onlineTime").asLong();
        // // // //                 long lastSeen = trackingNode.path("lastSeen").asLong();

        // // // //             }
        // // // //         }
        // // // //     }
        // // // //     System.out.println("User with UUID " + uuid + " not found");
        // // // // } catch (IOException e) {
        // // // //     e.printStackTrace();
        // // // // }