package com.daniel.dao;

import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.daniel.model.User;
import com.daniel.util.DbUtil;

public class UserDao {

	private Connection connection;

	public UserDao() {
		connection = DbUtil.getConnection();
	}

	public boolean addUser(User user) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("insert into users values (?, ?, ?, ? )");
			// Parameters start with 1
			preparedStatement.setInt(1, new Random().nextInt(1000));
			preparedStatement.setString(2, user.getFirstName());
			preparedStatement.setString(3, user.getLastName());
			preparedStatement.setString(4, user.getCnp());
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void deleteUser(int userId) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("delete from users where userid=?");
			// Parameters start with 1
			preparedStatement.setInt(1, userId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean updateUser(User user) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("update users set firstname=?, lastname=?, cnp=?" +
							"where userid=?");
			// Parameters start with 1
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			//preparedStatement.setDate(3, new java.sql.Date(user.getDob().getTime()));
			preparedStatement.setString(3, user.getCnp());
			preparedStatement.setInt(4, user.getUserid());
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from users");
			while (rs.next()) {
				User user = new User();
				user.setUserid(rs.getInt("userid"));
				user.setFirstName(rs.getString("firstname"));
				user.setLastName(rs.getString("lastname"));
				//user.setDob(rs.getDate("dob"));
				user.setCnp(rs.getString("cnp"));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}
	
	public User getUserById(int userId) {
		User user = new User();
		try {
			PreparedStatement preparedStatement = connection.
					prepareStatement("select * from users where userid=?");
			preparedStatement.setInt(1, userId);
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				user.setUserid(rs.getInt("userid"));
				user.setFirstName(rs.getString("firstname"));
				user.setLastName(rs.getString("lastname"));
				//user.setDob(rs.getDate("dob"));
				user.setCnp(rs.getString("cnp"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}
	
	public User getUserByCnp(String userCnp) {
		User user = new User();
		try {
			PreparedStatement preparedStatement = connection.
					prepareStatement("select * from users where cnp=?");
			preparedStatement.setString(1, userCnp);
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				user.setUserid(rs.getInt("userid"));
				user.setFirstName(rs.getString("firstname"));
				user.setLastName(rs.getString("lastname"));
				user.setCnp(rs.getString("cnp"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}
}
