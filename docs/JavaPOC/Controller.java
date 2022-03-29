import java.net.*;

public class Controller {
    public static void main(String[] args) {
        // Declare var of type URL with data "http://www.gstatic.com/generate_204"
        URL gstaticURL = null;
        try {
            gstaticURL = new URL("http://www.gstatic.com/generate_204");
            // url = new URL("https://www.google.com");
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // construct a Model, "pt", with URL gstaticURL
        Model gstatic = new Model(gstaticURL);

        
        // Whether or not client has internet connection
        Boolean connectivity = gstatic.check204() && true;

        

        
    
    
    


    }    
}
