package com.dbms.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;

@Service
public class AppealService {

    @Autowired
    private DataSource dataSource;

    public boolean addAppeal(String driverID, String citationNumber, String details, boolean status) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affectedRows = -1;
        boolean isValidOperation = true;
        try {
            System.out.println("Getting connection");
            connection = dataSource.getConnection();
            System.out.println("Data Recieved"+ driverID);
            String sql = "INSERT INTO Appeals(CitationNumber, DriverID, details, status) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, citationNumber);
            preparedStatement.setString(2, driverID);
            preparedStatement.setString(3, details);
            preparedStatement.setBoolean(4, status);

            affectedRows = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + affectedRows);

        } catch (SQLException e) {
            e.printStackTrace();
            isValidOperation = false;
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Connection closed");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return isValidOperation;
    }

    // Function to delete appeals data
    public boolean deleteAppealById(String driverID, String citationNumber) {
        Connection connection = null;
        PreparedStatement deleteStatement = null;
        boolean isValidOperation = true;
        try {
            System.out.println("Getting connection");
            connection = dataSource.getConnection();
            String deleteSql = "DELETE FROM Appeals WHERE DriverID = ? AND CitationNumber = ?";
            deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setString(1, driverID);
            deleteStatement.setString(2, citationNumber);
            int affectedRows = deleteStatement.executeUpdate();
            System.out.println("Rows deleted: " + affectedRows);
            if(affectedRows == 0){
                System.out.println("Data not found");
                isValidOperation = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            isValidOperation = false;
        } finally {
            if (deleteStatement != null) {
                try {
                    deleteStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Connection closed");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return isValidOperation;
    }

    public boolean updateAppeal(String driverID, String citationNumber, String details, String status) {
        Connection connection = null;
        PreparedStatement checkStatement = null;
        PreparedStatement upsertStatement = null;
        ResultSet resultSet = null;
        boolean isValid = true;
        try {

            System.out.println("Getting connection");
            connection = dataSource.getConnection();
            String checkSql = "SELECT * FROM Appeals WHERE CitationNumber = ? AND DriverID = ?";
            checkStatement = connection.prepareStatement(checkSql);
            checkStatement.setString(1, citationNumber);
            checkStatement.setString(2, driverID);

            resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {

                String updateSql = "UPDATE Appeals SET details = ?, status = ? WHERE CitationNumber = ? AND DriverID=?";
                upsertStatement = connection.prepareStatement(updateSql);
                upsertStatement.setString(1, details != null ? details : resultSet.getString("details"));
                upsertStatement.setBoolean(2, status != null ? Boolean.parseBoolean(status) : resultSet.getBoolean("status"));
                upsertStatement.setString(3, citationNumber);
                upsertStatement.setString(4, driverID);


                int affectedRows = upsertStatement.executeUpdate();
                System.out.println("Rows updated: " + affectedRows);
            } else {
                System.out.println("The record does not exist");
                isValid = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            isValid = false;
        } finally {

            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (checkStatement != null) {
                try {
                    checkStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (upsertStatement != null) {
                try {
                    upsertStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {

                    connection.close();
                    System.out.println("Connection closed");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return isValid;
    }
}

