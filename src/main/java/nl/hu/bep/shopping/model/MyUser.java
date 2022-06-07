package nl.hu.bep.shopping.model;

import javax.management.relation.Role;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

public class MyUser implements Principal {
    private String name;
    private String username;
    private String role;
    private String password;
    private static List<MyUser> allUsers = new ArrayList<>();

    public MyUser(String nm, String unm, String rl, String ww) {
        this.name=nm;
        this.username = unm;
        this.password = ww;
        this.role = rl;
        if (!allUsers.contains(this)) allUsers.add(this);
    }

    public static String validateLogin(String username, String password) {
        for (MyUser user : allUsers) {
            if (username.equals(user.username) && password.equals(user.password)) {
                return user.role;
            }
        }return null;
    }



    @Override
    public String getName() {
        return name;
    }
    public static MyUser getUserByName(String name){
        for (MyUser user : allUsers){
            if (name.equals(user.username)){
                return user;
            }
        }return null;
    }

    public String getRole() {
        return role;
    }
}
