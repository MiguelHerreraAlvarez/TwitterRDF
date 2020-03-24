package back;

import java.util.Date;
import java.util.Locale;
import twitter4j.Status;
import twitter4j.User;

public class Data {
    private final String text;
    private final long tweetId;
    private final String date;
    private final long replyTo;
    private final long userId;
    private final String userName;
    private final String userLocation;
    private final String languageId;
    private final String languageLabel;

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserLocation() {
        return userLocation;
    }
    
    public Data(Status tweet){
        // User
        User user = tweet.getUser();
        userLocation = user.getLocation();
        userName = user.getName();
        userId = user.getId();
               
        //Tweet
        text = tweet.getText();
        date = dateFormat(tweet.getCreatedAt());
        tweetId = tweet.getId();
        
        // Retweet
        replyTo = tweet.getInReplyToStatusId();
        
        // Lenguage
        languageId = tweet.getLang();
        languageLabel = new Locale(languageId).getDisplayLanguage();
    }
    
    private String dateFormat(Date date){
        int day = date.getDate();
        int month = date.getMonth()+1;
        int year = date.getYear()+1900;
        String res =  day+ "/" + month + "/" + year;
        
        return res;
    }
    
    public String getText() {
        return text;
    }

    public long getTweetId() {
        return tweetId;
    }

    public String getDate() {
        return date;
    }

    public long getReplyTo() {
        return replyTo;
    }

    public String getLanguageId() {
        return languageId;
    }

    public String getLanguageLabel() {
        return languageLabel;
    }
    
    public boolean isReply(){
        return replyTo != -1;
    }
}
