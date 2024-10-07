package gruoppo.test.service;

import gruoppo.test.Application.User;
import gruoppo.test.Application.UserInfo;
import gruoppo.test.database.dbuser;
import gruoppo.test.database.Dbimpl;

import java.sql.SQLException;

public class UserService {
    private final Dbimpl dbManager;

    public UserService() {
        this.dbManager = new Dbimpl();
    }

    public UserInfo registerUser(String username, String password) throws SQLException {
        User user = dbuser.register(dbManager.getConnection(), username, password);
        return new UserInfo(user.getId(), user.getUsername());
    }

    public boolean loginUser(String username, String password) throws SQLException {
        return dbuser.login(dbManager.getConnection(), username, password);
    }

    public UserInfo getUserInfoByUsername(String username) throws SQLException {
        int userId = dbuser.getUserId(dbManager.getConnection(), username);
        return new UserInfo(userId, username);
    }
}
