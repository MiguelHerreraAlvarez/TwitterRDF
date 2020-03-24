package back;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import twitter4j.Status;

public class RDFManager {
    private final Model model;
    private final String tweetURI = "http://twitterlibrary.com/tweetresource/";
    private final String userURI = "http://twitterlibrary.com/userresource/";
    private final String languageURI = "http://twitterlibrary.com/languageresource/";
    private final String replyURI = "http://twitterlibrary.com/replyresource/";
    private final String themeURI = "http://twitterlibrary.com/athemeresource/";
    private final String searchURI = "http://twitterlibrary.com/themeresource/";
    private final String textTheme;
    private final String searchString;
    
    //Tweet
    Property textProperty;
    Property tweetIdProperty;
    Property dateProperty;
    Property replyProperty;
    Property userProperty;
    Property languageProperty;
    Property searchProperty;
    
    //Author
    Property userNameProperty;
    Property userLocationProperty;
    
    //Language
    Property languageIdProperty;
    Property languageLabelProperty;
    
    //A theme
    Property themeProperty;
    
    //Theme
    Property themeLabelProperty;
    
    public RDFManager(String textTheme, String searchString) {
        model = ModelFactory.createDefaultModel();
        this.textTheme = textTheme;
        this.searchString = searchString;
        
        textProperty = model.createProperty(tweetURI, "texto");
        dateProperty = model.createProperty(tweetURI, "fecha_creacion");
        tweetIdProperty = model.createProperty(tweetURI, "id_tweet");
        replyProperty = model.createProperty(tweetURI, "respuesta_a");
        userProperty = model.createProperty(tweetURI, "autor");
        languageProperty = model.createProperty(tweetURI, "idioma");
        searchProperty = model.createProperty(tweetURI, "relacionado_con");
        
        userNameProperty = model.createProperty(userURI, "nombre");
        userLocationProperty = model.createProperty(userURI, "ubicacion");
        
        languageIdProperty = model.createProperty(languageURI, "id_idioma");
        languageLabelProperty = model.createProperty(languageURI, "etiqueta");
        
        themeProperty = model.createProperty(searchURI, "tipo");
        
        themeLabelProperty = model.createProperty(themeURI, "etiqueta");
    }
    
    public Resource createGraph(Status status){
        return createGraph(status, true);
    }
    
    public Resource createGraph(Status status, boolean checkReply){
        Data data = new Data(status);
        
        Resource tweetResource = createTweetResource(data);
        Resource userResource = createUserResource(data);
        Resource languageResource = createLanguageResource(data);
        Resource searchResource = createSearchResource();
        Resource themeResource = createThemeResource();
        
        searchResource.addProperty(RDF.type, themeResource);
        tweetResource.addProperty(this.searchProperty, searchResource);
        tweetResource.addProperty(this.userProperty, userResource);
        tweetResource.addProperty(this.languageProperty, languageResource);
        
        if (checkReply && data.isReply()){
            tweetResource.addProperty(replyProperty, createGraph(TwitterWrapper.getStatus(data.getReplyTo()), false));
        }
        
        return tweetResource;
    }

    private Resource createTweetResource(Data data) {
        Resource tweet = model.createResource(tweetURI + data.getTweetId());
        tweet.addProperty(tweetIdProperty, data.getTweetId() + "");
        tweet.addProperty(textProperty, data.getText());
        tweet.addProperty(dateProperty, data.getDate());
        
        return tweet;
    }

    private Resource createUserResource(Data data) {
        Resource user = model.createResource(userURI + data.getUserId());
        user.addProperty(userNameProperty, data.getUserName());
        user.addProperty(userLocationProperty, data.getUserLocation());
        
        return user;
    }

    private Resource createLanguageResource(Data data) {
        Resource language = model.createResource(userURI + data.getLanguageId());
        language.addProperty(languageIdProperty, data.getLanguageId());
        language.addProperty(languageLabelProperty, data.getLanguageLabel());
        
        return language;
    }    

    private Resource createThemeResource() {
        Resource theme = model.createResource(themeURI + textTheme);
        theme.addProperty(RDF.type, RDFS.Class);
        theme.addProperty(RDFS.label, textTheme);
        
        return theme;
    }
    
    private Resource createSearchResource(){
        Resource search = model.createResource(searchURI + searchString);
        return search;
    }

    public Model getModel() {
        return model;
    }

}
