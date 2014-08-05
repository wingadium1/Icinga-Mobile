package mhst.dreamteam.SessionMng;

import android.util.Log;

import java.security.InvalidParameterException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import mhst.dreamteam.Const;

/**
 * Manage session
 * @author MinhNN
 */
public class Session {
    private static int loginResult = Const.SESSION_LOGGED_OUT;
    private static String Cookie = null;

    /**
     * Checks if user logged into system or not.
     * @return  true - user logged in<br />
     *          false - user has not logged in yet
     */
    public static boolean isLogin() {
        return (loginResult == Const.SESSION_LOGGED_IN);
    }

    /**
     * Return current session cookie
     * @return cookie string
     */
    public static String getCookie() {
        return Cookie;
    }

    /**
     * Logs user into server
     * @param loginInfo                     loginInfo[0] = Server Address<br />
     * &emsp;&emsp;&emsp;&emsp;&emsp;&emsp; loginInfo[1] = Username<br />
     * &emsp;&emsp;&emsp;&emsp;&emsp;&emsp; loginInfo[2] = Password<br />
     * @throws InvalidParameterException
     */
    public static int doLogin(String... loginInfo)
            throws InvalidParameterException {
        // Check input data
        if (loginInfo.length == 0) {
            throw new InvalidParameterException();
        } else if (loginInfo.length != 3) {
            throw new InvalidParameterException();
        }
        // Check if input string is null or empty
        for (String str : loginInfo) {
            if (str == null || str.isEmpty()) {
                throw new InvalidParameterException();
            }
        }

        // Parse server address
        loginInfo[0] = "http://" + loginInfo[0];

        // Response data
        Map<String, Object> result;
        // Do log in and return result code
        try {
            result = new Login().execute(loginInfo).get();
            if (result.containsKey("Code")) {
                // Login result
                loginResult = (Integer) result.get("Code");
            }
            if (result.containsKey("Cookie")) {
                // Current session cookie
                Cookie = (String) result.get("Cookie");
            }
            Log.i("Session login", "Login result = " + loginResult);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return loginResult;
    }

    public static int doLogout() {
        try {
            // if not logged in yet, just quit and return previous status
            if (!isLogin()) return loginResult;
            // Then logout
            loginResult = new Logout().execute().get();
            if (loginResult != Const.SESSION_LOGGED_IN) {
                // Empty cookie when logout
                Cookie = null;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return loginResult;
    }
}
