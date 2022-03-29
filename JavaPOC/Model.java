import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    followRedirects(): Return final URL after following all redirects (302, META Refresh, ETC)
    */
    public URL followRedirects() throws IOException {
        String content = "";
        // HTTP (Initial connection)
        if (urlProtocol.equals("http") && this.redirectCount == 0){
            conHttps.getHeaderFields();
            redirect = conHttp.getURL();
        }
        // HTTPS (Initial connection)
        else if (urlProtocol.equals("https") && this.redirectCount == 0){
            conHttps.getHeaderFields();
            redirect = conHttps.getURL();
        }
        
        // read page content of conHttp to string and print it; then scan for meta refresh
        else if (redirectProtocol.equals("http") && this.redirectCount > 0){
            // Create connection to redirect URL
            HttpURLConnection redirHttp = (HttpURLConnection) redirect.openConnection();
            redirHttp.setRequestMethod("GET");
            redirHttp.setRequestProperty("User-Agent", "Mozilla/5.0");
            redirHttp.setUseCaches(false);
            redirHttp.setInstanceFollowRedirects(false);
    
            // Read page response to pageResponse
            InputStream is = redirHttp.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line).append('\n');
            br.close();
            redirHttp.disconnect();

            content = sb.toString();
            System.out.println(content);
        }
        // read page content of conHttps to string and print it; then scan for meta refresh
        else if (redirectProtocol.equals("https") && this.redirectCount > 0){
            // Create connection to redirect URL
            HttpsURLConnection redirHttps = (HttpsURLConnection) redirect.openConnection();
            redirHttps.setRequestMethod("GET");
            redirHttps.setRequestProperty("User-Agent", "Mozilla/5.0");
            redirHttps.setUseCaches(false);
            redirHttps.setInstanceFollowRedirects(false);
    
            // Read page response to pageResponse
            InputStream is = redirHttps.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line).append('\n');
            br.close();
            redirHttps.disconnect();

            content = sb.toString();
            System.out.println(content);

        }

        // TODO: Create scan for meta refresh method
        // TODO: Create get page content method
        // TODO determine http -> https redirects


        redirectProtocol = redirect.getProtocol();

        // Re-run until no more redirects
        if (!redirect.equals(finalURL) && (redirectCount < 10)){ 
            this.redirectCount++;
            System.out.println("Redirect #" + this.redirectCount + ": " + redirect.toString());
            finalURL = redirect;
            this.followRedirects();
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