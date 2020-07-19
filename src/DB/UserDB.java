package DB;

import commons.dataTypes.SearchDetails;
import commons.dataTypes.User;
import commons.queries.*;
import java.util.HashMap;
import java.util.LinkedList;

public class UserDB {
    static HashMap<String, User> usernameMap= new HashMap<>();
    static HashMap<User, Boolean> isLoggedIn= new HashMap<>();
    static LinkedList<User> users= new LinkedList<>();
    static LinkedList<User> onlineUsers= new LinkedList<>();


    public synchronized static LoginResult login(LoginRequest loginRequest){
        if(!usernameMap.containsKey(loginRequest.username))
            return new LoginResult(null,"The username or password you entered is incorrect");
        if(usernameMap.get(loginRequest.username)== null)
            return new LoginResult(null,"The account has been deleted!");
        if(!usernameMap.get(loginRequest.username).checkPassword(loginRequest.password))
            return new LoginResult(null,"The username or password you entered is incorrect");
        if(isLoggedIn.get(usernameMap.get(loginRequest.username)))
            return new LoginResult(null,"This account is logged in somewhere else! please logout and try again");

        onlineUsers.add(usernameMap.get(loginRequest.username));
        isLoggedIn.put(usernameMap.get(loginRequest.username),true);
        return new LoginResult(usernameMap.get(loginRequest.username),"Welcome!");
    }


    public synchronized static SignUpResult signUp(SignUpRequest signUpRequest){
        if(usernameMap.containsKey(signUpRequest.user.username))
            return new SignUpResult(null);

        users.add(signUpRequest.user);
        usernameMap.put(signUpRequest.user.username,signUpRequest.user);
        onlineUsers.add(signUpRequest.user);
        isLoggedIn.put(signUpRequest.user,true);
        return new SignUpResult(signUpRequest.user);
    }

    public synchronized static void logout(Logout logout){
        isLoggedIn.put(usernameMap.get(logout.username),false);
        onlineUsers.remove(usernameMap.get(logout.username));
    }

    public synchronized static void deleteAccount(DeleteAccount deleteAccount){
        User user= usernameMap.get(deleteAccount.username);
        isLoggedIn.put(user,false);
        onlineUsers.remove(user);
        users.remove(user);
        usernameMap.put(deleteAccount.username, null);
    }

    public synchronized static void changePassword(ChangePassword changePassword){
        usernameMap.get(changePassword.username).setPassword(changePassword.password);
    }

    public synchronized static SearchResult search(SearchRequest searchRequest){
        LinkedList<SearchDetails> ret= usernameMap.get(searchRequest.username).lastOpponent();
        for(User u: onlineUsers){
            if(!ret.contains(u.details()))
                ret.add(u.details());
        }
        return new SearchResult(ret);
    }

}
