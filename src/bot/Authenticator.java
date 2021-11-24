package bot;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;




public class Authenticator {
	public void authenticate(Twitter twitter) {
		//pull the key and secet from preferences
		Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
		try {
			prefs.clear();
		} catch (BackingStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//If user hasn't been authenticated then key & secret will be null.
		String key = prefs.get("key", null);
		String secret = prefs.get("secret", null);
		AccessToken accessToken = null;
		twitter.setOAuthConsumer("AxLv4DFZmDBbPdYE88QXRjFdC", 
				"fek3cBG5RXwBAkt9bUPOfdN3pKIfwoZ0uSmRsEhhMbUhkezAla"); //programs key and secret
		if(key!=null && secret != null) {
			//User has already been authenticated
			accessToken = new AccessToken(key, secret);
			twitter.setOAuthAccessToken(accessToken);
		}

		/*
		 * By now we either have a completed OAuth sequence and null AccessToken OR
		 * we have a valid non-null AccessToken
		 */
		try {
			//If request token is already avaiable then an IllegalStateException will be thrown
			RequestToken requestToken = twitter.getOAuthRequestToken();
			if(accessToken == null) {
				accessToken = getAccessToken(twitter, requestToken);
			}
		}
		catch(IllegalStateException e) {
			// access token is already available, or consumer key/secret is not set.
            if (!twitter.getAuthorization().isEnabled()) {
                System.out.println("OAuth consumer key/secret is not set.");
                System.exit(-1);
            }

		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prefs.put("key", accessToken.getToken());
		prefs.put("secret", accessToken.getTokenSecret());
		System.out.println("You're in!");
	}
	
	/*
	 * This method only gets called if the user does not already have an access token
	 */
	private AccessToken getAccessToken(Twitter twitter, RequestToken requestToken) {
		AccessToken accessToken = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(accessToken == null) {
			
			System.out.println(requestToken.getAuthorizationURL());
			System.out.println("Enter the PIN (if available) and hit enter after you have granted access. [PIN] ");
			try {
				Desktop.getDesktop().browse(new URI(requestToken.getAuthenticationURL()));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				String PIN = br.readLine();
				if(PIN.length()>0) {
					accessToken = twitter.getOAuthAccessToken(requestToken, PIN);
				}
				else {
					accessToken = twitter.getOAuthAccessToken(requestToken);
				}
				return accessToken;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Failed to read PIN...");
			} catch (TwitterException te) {
				if (401 == te.getStatusCode()) {
                    System.out.println("Unable to get the access token.");
                } else {
                    te.printStackTrace();
                }
			}
		}
		return null;
	}
}
