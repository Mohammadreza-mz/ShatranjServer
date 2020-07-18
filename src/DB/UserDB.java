package DB;

import dataTypes.User;
import queries.*;
import java.util.HashMap;
import java.util.LinkedList;

public class UserDB {
    static HashMap<String, User> usernameMap= new HashMap<>();
    static HashMap<User, Boolean> isLoggedIn= new HashMap<>();
    static LinkedList<User> users= new LinkedList<>();
    static LinkedList<User> onlineUsers= new LinkedList<>();


    public static LoginResult login(LoginRequest loginRequest){
        if(!usernameMap.containsKey(loginRequest.username))
            return new LoginResult(null,"The username or pass you entered is incorrect");
        if(!usernameMap.get(loginRequest.username).checkPassword(loginRequest.password))
            return new LoginResult(null,"The username or pass you entered is incorrect");
        if(isLoggedIn.get(usernameMap.get(loginRequest.username)))
            return new LoginResult(null,"This account is logged in somewhere else! please logout and try again");

        onlineUsers.add(usernameMap.get(loginRequest.username));
        isLoggedIn.put(usernameMap.get(loginRequest.username),true);
        return new LoginResult(usernameMap.get(loginRequest.username),"Welcome!");
    }


    public static SignUpResult signUp(SignUpRequest signUpRequest){
        if(usernameMap.containsKey(signUpRequest.user.username))
            return new SignUpResult(false);

        users.add(signUpRequest.user);
        usernameMap.put(signUpRequest.user.username,signUpRequest.user);
        onlineUsers.add(signUpRequest.user);
        isLoggedIn.put(signUpRequest.user,true);
        return new SignUpResult(true);
    }

    public static void logout(Logout logout){
        isLoggedIn.put(usernameMap.get(logout.username),false);
        onlineUsers.remove(usernameMap.get(logout.username));
    }

}
