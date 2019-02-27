package ch.schildj.postcardsender.web.core.user;

import java.util.List;

/**
 * This class holds the logged user info of the application.
 *
 */
public class UserInfo {
    private String username;
    private List<String> roles;

    protected UserInfo() {
    }

    public UserInfo(String username, List<String> roles) {
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }
}
