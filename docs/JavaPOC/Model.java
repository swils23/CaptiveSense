import java.io.IOException;
import java.net.*;
import javax.net.ssl.HttpsURLConnection;

public class Model{
    Boolean DBG = true; // DEBUG

    private URL url;
    private HttpURLConnection conHttp;
    private HttpsURLConnection conHttps;

    // Connection settings
    private int timeout; // in milliseconds
    private String reqMethod; // GET, POST, PUT, DELETE, etc.

    // one argument constructor that takes and initializes URL
    public Model(URL url) {
        this.url = url;
        if (DBG) System.out.println(url.getProtocol());

        // Default connection settings
        this.timeout = 5000;
        this.reqMethod = "GET";
        
    }


    /* 
    Method to open http or https connection to URL;
    Return true if connection is successful
    */
    public boolean openConnection(int timeout, String requestMethod "GET") {
        // set connection settings
        int timeout = 5000;
        String requestMethod = "GET";



        try {
            // Open HTTP Connection
            if (url.getProtocol() == "http"){
                conHttp = (HttpURLConnection) url.openConnection(); // Construct conHttp

                // Connection settings
                conHttp.setConnectTimeout(this.timeout); // Connection timeout
                conHttp.setReadTimeout(this.timeout); // Read timeout
                conHttp.setRequestMethod(this.reqMethod); // Request method

                // Connect to the URL
                conHttp.connect();

                // Return true if the connection is successful
                return true;
            }
            // Open HTTPS Connection "conHttps"
            else if (url.getProtocol() == "https"){
                conHttps = (HttpsURLConnection) url.openConnection(); // Open a connection to the URL

                // Connection settings
                conHttps.setConnectTimeout(timeout); // Connection timeout
                conHttps.setReadTimeout(timeout); // Read timeout
                conHttps.setRequestMethod(reqMethod); // Request method

                // Connect to the URL
                conHttps.connect();

                // Return true if the connection is successful
                return true;
            }
            else {
                // Return false if the connection is unsuccessful
                return false;
            }
        } catch (Exception e) {
                // Return false if the connection is unsuccessful
                return false;
            }
        }
        
    }

    /*
    Check204(): Return true if connection returns 204
    */
    public boolean check204() {
        try {
            // Return true if the response code is 204
            return conHttp.getResponseCode() == 204;
        } catch (IOException e) {
            // Return false if the response code is not 204
            return false;
        }
    }


}