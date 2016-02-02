package com.baseballstandings.logify;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Scott on 1/21/2016.
 */
public class JsonParser {

    private String jsonString;

    public JsonParser(String json){
        setJsonString(json);
    }

    public List<League> parseLeagueHeirachyFile() throws JSONException {

        List<League> leagues = new ArrayList<>();

        JSONArray leagueArray = new JSONObject(getJsonString()).getJSONObject("league").getJSONObject("season").getJSONArray("leagues");

        for(int i = 0; i < leagueArray.length();i++){
            JSONObject leagueObject = leagueArray.getJSONObject(i);
            League league = new League();
            league.setName(leagueObject.getString("name"));

            System.out.println(league.getName());

            JSONArray divisionArray = leagueObject.getJSONArray("divisions");
            for(int d = 0; d < divisionArray.length();d++){
                Division division = new Division();
                JSONObject divisionObject = divisionArray.getJSONObject(d);
                division.setName(divisionObject.getString("name"));

                System.out.println(division.getName());

                JSONArray teamArray = divisionObject.getJSONArray("teams");
                for(int t = 0;t < teamArray.length();t++){
                    Team team = new Team();
                    JSONObject teamObject = teamArray.getJSONObject(t);
                    team.setName(teamObject.getString("market") + " " + teamObject.getString("name"));
                    System.out.println(team.getName());
                    division.getTeams().add(team);
                }

                league.getDivision().add(division);

            }

            leagues.add(league);
        }

        return leagues;
    }

    /**
     * @return Standings Object contains divisions and
     * teams with wins and losses for each team
     * @throws JSONException
     */
    public Standings parseStandingsFile() throws JSONException {

        Standings standings = new Standings();

        JSONArray leagueArray = new JSONObject(getJsonString()).getJSONObject("league").getJSONObject("season").getJSONArray("leagues");

        for(int i = 0; i < leagueArray.length();i++){

            League league = new League();
            JSONObject leagueObject = leagueArray.getJSONObject(i);

            league.setName(leagueObject.getString("name"));

            JSONArray divisionArray = leagueObject.getJSONArray("divisions");
            for(int d = 0; d < divisionArray.length();d++){
                Division division = new Division();
                JSONObject divisionObject = divisionArray.getJSONObject(d);
                division.setName(divisionObject.getString("name"));

                JSONArray teamArray = divisionObject.getJSONArray("teams");
                for(int t = 0;t < teamArray.length();t++){
                    Team team = new Team();
                    JSONObject teamObject = teamArray.getJSONObject(t);
                    team.setName(teamObject.getString("market") + " " + teamObject.getString("name"));
                    team.setGame_back((int)Double.parseDouble(teamObject.getString("games_back")) + "");
                    team.setWin_pct(teamObject.getString("win_p"));
                    team.setWins(teamObject.getString("win"));
                    team.setLosses(teamObject.getString("loss"));
                    division.getTeams().add(team);
                }

                league.getDivision().add(division);

            }

            standings.getLeagues().add(league);

            /*
            Collections.sort(teams, new Comparator<Team>() {
                @Override
                public int compare(Team lhs, Team rhs) {
                    return lhs.getName().compareTo(rhs.getName());
                }
            });

            */

        }

        return standings;

    }

    public List<Game> parseTeamSchedule(String name) throws JSONException {

        List<Game> games = new ArrayList<>();

        JSONObject seasonObject = new JSONObject(getJsonString());


        JSONArray gamesArray = seasonObject.getJSONObject("league").getJSONObject("season").getJSONArray("games");

        for(int i = 0; i < gamesArray.length();i++){
            //loop through each game object and check if game contains the team sent in as ARG
            JSONObject gameObj = gamesArray.getJSONObject(i);

            String awayTeam, homeTeam;
            awayTeam = gameObj.getJSONObject("away").getString("market") + " " + gameObj.getJSONObject("away").getString("name");
            homeTeam = gameObj.getJSONObject("home").getString("market") + " " + gameObj.getJSONObject("home").getString("name");

            if(awayTeam.equals(name) || homeTeam.equals(name)){

                //do not add games if they have already been played!
                if(new Date().compareTo(new FormatGameStartTime().formatGameDateToDateObject(gameObj.getString("scheduled"))) == -1 ||
                        new Date().compareTo(new FormatGameStartTime().formatGameDateToDateObject(gameObj.getString("scheduled"))) == 0){

                    //build the game object for the team and add the game object to the listing
                    Game game = new Game();
                    game.setId(gameObj.getString("id"));
                    game.setScheduled(gameObj.getString("scheduled"));
                    game.setDay_night(gameObj.getString("day_night"));
                    game.setAway_team(gameObj.getJSONObject("away").getString("name"));
                    game.setAway_market(gameObj.getJSONObject("away").getString("market"));
                    game.setHome_team(gameObj.getJSONObject("home").getString("name"));
                    game.setHome_market(gameObj.getJSONObject("home").getString("market"));
                    game.setDateOfGame();
                    game.setTimeOfGame();
                    game.setGame_date(new FormatGameStartTime().formatGameDateToDateObject(game.getScheduled()));

                    games.add(game);
                }else{
                    continue;
                }

            }else{
                continue;
            }
        }

        System.out.println("TEAM: " + name + " # OF GAMES---> " + games.size());

        return games;
    }

    /**
     * parses the entire mlb schedule file creating a Game Object for each game played
     * the method only creates games that are playing the the future
     * @return list of all games for the season
     * @throws JSONException
     */
    public List<Game> parseTeamSchedule() throws JSONException {

        List<Game> games = new ArrayList<>();

        JSONObject seasonObject = new JSONObject(getJsonString());


        JSONArray gamesArray = seasonObject.getJSONObject("league").getJSONObject("season").getJSONArray("games");

        for(int i = 0; i < gamesArray.length();i++){
            //loop through each game object and check if game contains the team sent in as ARG
            JSONObject gameObj = gamesArray.getJSONObject(i);

                //do not add games if they have already been played!
                if(new Date().compareTo(new FormatGameStartTime().formatGameDateToDateObject(gameObj.getString("scheduled"))) == -1 ||
                        new Date().compareTo(new FormatGameStartTime().formatGameDateToDateObject(gameObj.getString("scheduled"))) == 0){

                    //build the game object for the team and add the game object to the listing
                    Game game = new Game();
                    game.setId(gameObj.getString("id"));
                    game.setScheduled(gameObj.getString("scheduled"));
                    game.setDay_night(gameObj.getString("day_night"));
                    game.setAway_team(gameObj.getJSONObject("away").getString("name"));
                    game.setAway_market(gameObj.getJSONObject("away").getString("market"));
                    game.setHome_team(gameObj.getJSONObject("home").getString("name"));
                    game.setHome_market(gameObj.getJSONObject("home").getString("market"));
                    game.setDateOfGame();
                    game.setTimeOfGame();
                    game.setGame_date(new FormatGameStartTime().formatGameDateToDateObject(game.getScheduled()));

                    games.add(game);
                }else{
                    continue;
                }

        }

        return games;
    }


    public List<Game> findGamesPlayingToday() throws JSONException {

        List<Game> games = new ArrayList<>();

        JSONObject seasonObject = new JSONObject(getJsonString());
        String today = "04/03/2016";
        Log.d(this.getClass().getSimpleName(), "Searching For " + today);
        JSONArray gamesArray = seasonObject.getJSONObject("league").getJSONObject("season").getJSONArray("games");

        for(int i = 0; i < gamesArray.length();i++){
            //loop through each game object and check if game contains the team sent in as ARG
            JSONObject gameObj = gamesArray.getJSONObject(i);

            String gameDate = new FormatGameStartTime().getDateOfGame(gameObj.getString("scheduled"));

            //Log.d(this.getClass().getSimpleName(),"CURRENT GAME DATE " + gameDate);

            if(gameDate.equals(today)){
                //build the game object for the team and add the game object to the listing
                Game game = new Game();
                game.setId(gameObj.getString("id"));
                game.setScheduled(gameObj.getString("scheduled"));
                game.setDay_night(gameObj.getString("day_night"));
                game.setAway_team(gameObj.getJSONObject("away").getString("name"));
                game.setAway_market(gameObj.getJSONObject("away").getString("market"));
                game.setHome_team(gameObj.getJSONObject("home").getString("name"));
                game.setHome_market(gameObj.getJSONObject("home").getString("market"));

                game.setDateOfGame();
                game.setTimeOfGame();

                games.add(game);


            }
        }

        return games;

    }

    public List<Game> findGamesPlayingToday(String date,GameFinderInterface gameFinderInterface) throws JSONException {

        List<Game> games = new ArrayList<>();

        JSONObject seasonObject = new JSONObject(getJsonString());
        String today = date;
        Log.d(this.getClass().getSimpleName(), "Searching For " + today);
        JSONArray gamesArray = seasonObject.getJSONObject("league").getJSONObject("season").getJSONArray("games");

        int j = 0;

        for(int i = 0; i < gamesArray.length();i++){
            //loop through each game object and check if game contains the team sent in as ARG
            JSONObject gameObj = gamesArray.getJSONObject(i);

            String gameDate = new FormatGameStartTime().getDateOfGame(gameObj.getString("scheduled"));

            //Log.d(this.getClass().getSimpleName(),"CURRENT GAME DATE " + gameDate);

            if(gameDate.equals(today)){
                //build the game object for the team and add the game object to the listing
                Game game = new Game();
                game.setId(gameObj.getString("id"));
                game.setScheduled(gameObj.getString("scheduled"));
                game.setDay_night(gameObj.getString("day_night"));
                game.setAway_team(gameObj.getJSONObject("away").getString("name"));
                game.setAway_market(gameObj.getJSONObject("away").getString("market"));
                game.setHome_team(gameObj.getJSONObject("home").getString("name"));
                game.setHome_market(gameObj.getJSONObject("home").getString("market"));

                game.setDateOfGame();
                game.setTimeOfGame();

                games.add(game);

                gameFinderInterface.gameFound(++j);
            }
        }

        return games;

    }
    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }
}
