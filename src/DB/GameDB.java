package DB;

import commons.dataTypes.ScoreboardComparator;
import commons.dataTypes.SearchDetails;
import commons.dataTypes.User;
import commons.queries.ScoreboardRequest;
import commons.queries.ScoreboardResult;

import java.util.Collections;
import java.util.LinkedList;

public class GameDB {


    public static ScoreboardResult Scoreboard(ScoreboardRequest scoreboardRequest){
        LinkedList<SearchDetails> tmp=new LinkedList<>();
        for(User u:UserDB.users)
            tmp.add(u.details());
        Collections.sort(tmp,new ScoreboardComparator());
        LinkedList<String> ret=new LinkedList<>();
        for(SearchDetails u:tmp){
            if(u.username.equals(scoreboardRequest.username)){
                ret.add("---->"+u.toString().substring(5));
            }
            else
                ret.add(u.toString());
        }
        return new ScoreboardResult(ret);
    }
}
