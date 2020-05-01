package pl.pjm77.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.pjm77.model.User;

import javax.sql.DataSource;

import static pl.pjm77.misc.DbUtils.prepStatement;

public class RealUserDAO implements UserDAO {

    private final DataSource dataSource;

    public RealUserDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void saveUserToDB(User user) {
        try {
            if (user.getId() == 0) {
                String[] columnNames = {" ID "};
                try (PreparedStatement ps = prepStatement(dataSource.getConnection(),
                        "INSERT INTO user(username, email, password, usergroup_id) " +
                                "VALUES (?, ?, ?, ?);", columnNames, user.getName(),
                        user.getEmail(), user.getPassword(), user.getGroup_id());
                     ResultSet rs = ps.getGeneratedKeys()) {
                    ps.executeUpdate();
                    if (rs.next()) {
                        user.setId(rs.getLong(1));
                    }
                }
            } else {
                try (PreparedStatement ps = prepStatement(dataSource.getConnection(),
                        "UPDATE user SET username=?, email=?, password=?, usergroup_id=? " +
                                "WHERE id = ?;", user.getName(), user.getEmail(),
                        user.getPassword(), user.getGroup_id(), user.getId())) {
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User loadUserById(long id) {
        try (PreparedStatement ps = prepStatement(dataSource.getConnection(),
                "SELECT * FROM user WHERE id=?;", id);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return loadSingleUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteUser(User user) {
        try (PreparedStatement ps = prepStatement(dataSource.getConnection(),
                "DELETE FROM user WHERE id=?", user.getId())) {
            ps.executeUpdate();
            user.setId(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User[] loadAllUsers() {
        return executeQuery("SELECT * FROM user;");
    }

    public User[] loadAllUsersByGroupId(int usergroup_id) {
        return executeQuery("SELECT * FROM user WHERE usergroup_id=?;", usergroup_id);
    }

    /**
     * executes SQL Query with optional parameter.
     * @param sqlQuery - query to execute
     * @param param - optional parameter
     * @return user objects array
     */
    private User[] executeQuery(String sqlQuery, Object...param) {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = prepStatement(dataSource.getConnection(), sqlQuery, param);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                users.add(loadSingleUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User[] uArray = new User[users.size()];
        uArray = users.toArray(uArray);
        return uArray;
    }

    /**
     * Gets single User object from result set.
     * @param rs - ResultSet
     * @return - User object
     * @throws SQLException - in case of database problems
     */
    private User loadSingleUser(ResultSet rs) throws SQLException {
        User loadedUser = new User();
        loadedUser.setId(rs.getLong("id"));
        loadedUser.setName(rs.getString("username"));
        loadedUser.setEmail(rs.getString("email"));
        loadedUser.setPassword(rs.getString("password"));
        loadedUser.setGroup_id(rs.getInt("usergroup_id"));
        return loadedUser;
    }
}