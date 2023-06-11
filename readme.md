Description of xpconfig:
{
    "xp": 
    {
        "levelXpKey": [1500, 3000, 5000, 7000, 9000, 10000, 11000, 12000, 13000, 14000],    <-- xp needed at each stage to reach next level
        "nextXpKey": 5,                                                                     <-- levels needed to reach next stage
        "nextRank": [{                                                                       
            "stammuser": {                                                                  <-- gast, user, stammuser...
                "levelNeeded": 56                                                           <-- level needed to reach this rank
            }
        }],
        "gains": {
            "tickLength": 1,                                                                <-- duration in seconds until a user gets xp
            "boostDuration": 120,                                                           <-- duration in minutes that a user gets an xp boost each day
            "eachTick": 20,                                                                 <-- amount of xp a user gets every tick
            "eachTickBoosted": 100                                                          <-- amount of xp a user gets every tick during xp boost
        }
    }
}


Description of Database: 
{
   "teamspeakConnections":[                                                                 
         {                                                                                     
            "_id":{                                                                         <-- unique userid from ts3 database
               "$oid":"5c72cb5c4856570536f901ee"
            },
            "clientId":14,                                                                  <-- current client id
            "nickname":"Juri",                                                              <-- current nickname
            "connectedAt":1551012544079,                                                    <-- start of this Connection
            "currentChannel":{                                              
               "name":"Autismo Risto",                                                      <-- name and id of channel the user is currently in 
               "id":138840
           }
        }
      ],
      "teamspeakOnline":true,                                                               <-- is the user currently online
      "rank":9900,                                                                          <-- rank of user
      "xp":{
         "current":11300,                                                                   <-- xp the user has collected in his current level
         "total":6507060,                                                                   <-- total xp the user has collected
         "boostMinutesRemaining":14,                                                        <-- remaining duration of this users boost time
         "lastPromotionAt":1431284838056                                                    <-- last time the user has ranked up (User --> Stammuser)
      },
      "level":468,                                                                          <-- current level of user
      "consent":true,                                                                       <-- consent to being seen by other users 
      "teamspeakTracking":{
        "activeTime":4112430000,                                                            <-- time the user was active   
        "onlineTime":9241135000,                                                            <-- time the user has been online
        "lastSeen":1551027032229                                                            <-- last time the user was online
      }
}