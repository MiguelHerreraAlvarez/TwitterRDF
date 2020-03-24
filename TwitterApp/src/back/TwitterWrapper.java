package back;

import java.util.ArrayList;
import java.util.List;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;


public class TwitterWrapper {
    
    static Twitter setConfiguration(){
        Twitter twitter = TwitterFactory.getSingleton();
        return twitter;
    }
    
    public static List<Status> searchTweets(String term,int count) throws TwitterException{
        Twitter twitter = setConfiguration();
        List<Status> res = new ArrayList();
         int remainingTweets = count;
        Query query = new Query(term);
        try{
            while(remainingTweets > 0){
              int queryCount =  remainingTweets > 100 ? 100 : remainingTweets;
              query.count(queryCount);
              QueryResult result = twitter.search(query);
              res.addAll(result.getTweets());
              if (result.hasNext()){
                  query = result.nextQuery();
              }
              remainingTweets -= queryCount;
            }
        } catch(TwitterException e){
        }
        return res;
    }
    
    public static Status getStatus(long tweetId){
        Twitter twitter = setConfiguration();
        try {
            return twitter.showStatus(tweetId);
        } catch(TwitterException e){
        }
        return null;
    }
}
