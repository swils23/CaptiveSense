import java.io.IOException;
import java.net.*;
import javax.net.ssl.HttpsURLConnection;

public class Model{
    Boolean DBG = true; // DEBUG

    private HttpURLConnection conHttp;
    private HttpsURLConnection conHttps;


    private URL url;
    private String urlProtocol;
    private URL redirect;
    private String redirectProtocol;
    private URL finalURL;
    private String finalURLProtocol;

    private int redirectCount = 0;

    
    // Connection settings
    private int timeout; // in milliseconds
    private String reqMethod; // GET, POST, PUT, DELETE, etc.
    private boolean followRedirects;

    // one argument constructor that takes and initializes URL
    public Model(URL url) {
        this.url = url;
        this.redirect = url;
        this.finalURL = url;
        this.urlProtocol = url.getProtocol();

        // Default connection settings
        this.timeout = 5000;
        this.reqMethod = "GET";
        this.followRedirects = true;
        
        // Open connection
        this.openConnection();
        
    }

    /*
    Set & Get methods for connection settings
    */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setReqMethod(String reqMethod) {
        this.reqMethod = reqMethod;
    }

    public void setFollowRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
    }


    /* 
    Method to set connection settings and open connection to URL;
    Return true if connection is successful
    */
    public boolean openConnection() {
        try {
            
            if (urlProtocol.equals("http")) {
                conHttp = (HttpURLConnection) url.openConnection();
                conHttp.setConnectTimeout(timeout);
                conHttp.setRequestMethod(reqMethod);
                conHttp.setInstanceFollowRedirects(followRedirects);
                
                conHttp.connect();
                return true;
            } 
            
            else if (urlProtocol.equals("https")) {
                conHttps = (HttpsURLConnection) url.openConnection();
                conHttps.setConnectTimeout(timeout);
                conHttps.setRequestMethod(reqMethod);
                conHttps.setInstanceFollowRedirects(followRedirects);
                
                conHttps.connect();
                return true;
            } 
            
            else {
                System.out.println("Invalid protocol");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
    checkResponseCode(): Return true if connection returns specified status code
    */
    public boolean checkResponseCode(int code) {
        try {
            if (urlProtocol.equals("http")) return conHttp.getResponseCode() == code;
            else if (urlProtocol.equals("https")) return conHttps.getResponseCode() == code;
            else return false;
        } catch (IOException e) {
            return false;
        }
    }

    /*
    followRedirect(): Return final URL after following all redirects (302, META Refresh, ETC)
    */
    public URL followRedirect() {
        // HTTP
        if (urlProtocol.equals("http")){
            conHttps.getHeaderFields();
            redirect = conHttp.getURL();


        }
        // HTTPS
        else if (urlProtocol.equals("https")){
            conHttps.getHeaderFields();
            redirect = conHttps.getURL();
            

        }
        // Protocol error
        else return null;

        redirectProtocol = redirect.getProtocol();

        if (!redirect.equals(finalURL)){ // if start URL is not equal to final URL
            this.redirectCount++;
            System.out.println("Redirect #" + this.redirectCount + ": " + redirect.toString());
            finalURL = redirect;
            this.followRedirect();
        }

        return finalURL;
    }

    /*
    checkRedirect(): Return true if redirect is found (start URL != final URL)
    */
    public boolean checkRedirect() {

        return !this.url.equals(this.redirect);
    }



}