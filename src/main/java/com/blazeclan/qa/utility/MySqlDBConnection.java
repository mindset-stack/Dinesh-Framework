package com.blazeclan.qa.utility;

import com.blazeclan.qa.base.CommonFunctions;
import com.blazeclan.qa.constants.IConstants;

import java.sql.*;
import java.sql.DriverManager;
import java.util.Properties;

public class MySqlDBConnection {

    Properties prop = CommonFunctions.init_properties(IConstants.MYSQL_DB_CONNECTION_CONFIG_PATH);
    public Connection connection;
    public  Statement statement;
    private void databaseSetup() {
        try {
            //created a database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            //get connection to database
            connection = DriverManager.getConnection(prop.getProperty("MySqlUrl"), prop.getProperty("UserName"),
                    prop.getProperty("Password"));
            //statement object is created to send request to database
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet retrieveData(String sqlQuery) {
        databaseSetup();
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            databaseTearDown();
        }
        return resultSet;
    }

        private void databaseTearDown() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
