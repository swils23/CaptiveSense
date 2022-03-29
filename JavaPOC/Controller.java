import java.net.*;

public class Controller {
    public static void main(String[] args) {
        // Declare var of type URL with data "http://www.gstatic.com/generate_204"
        URL gstaticURL = null, testURL = null;
        try {
            gstaticURL = new URL("http://www.gstatic.com/generate_204");
            testURL = new URL("https://youtu.be");
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // construct objects of type Model
        Model gstatic = new Model(gstaticURL);
        Model test = new Model(testURL);



        
        // Whether or not client has internet connection
        Boolean connectivity = gstatic.checkResponseCode(204);
        System.out.println("Connectivity: " + connectivity); // DEBUG
        System.out.println("Redirect: " + test.checkRedirect()); // DEBUG

        // if connectivity is true, but expected page content is not received or redirects to another page
        System.out.println("Final URL: " + test.followRedirect()); // DEBUG

        

        

        
    
    
    


    }    
}
