package database;
import config.ConfigReader;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DBConnection {

    private static Connection conn = null;

    private static Connection getConnection(){

        if(conn == null) {

            try {
                Class.forName(ConfigReader.DB_DRIVER);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            try {
                conn = DriverManager
                                 .getConnection
                                    (ConfigReader.DB_URL, ConfigReader.DB_USERNAME, ConfigReader.DB_PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return conn;
    }

    public static ArrayList<Map<String,Object>> executeQuery(String sql, ArrayList<Object> params)
    {
        ResultSet result = null;
        ArrayList<Map<String,Object>> returnResult = new ArrayList<>();

        try {

            PreparedStatement statement = getConnection().prepareStatement(sql);

            for(int i=0;i<params.size();i++)
            {
                if(params.get(i) instanceof Integer)
                    statement.setInt(i + 1,(Integer)params.get(i));
                if(params.get(i) instanceof String)
                    statement.setString(i + 1,(String)params.get(i));
                if(params.get(i) instanceof Date)
                    statement.setDate(i + 1, (Date) params.get(i));
                if(params.get(i) instanceof Long)
                    statement.setLong(i + 1, (Long) params.get(i));
            }

            result = statement.executeQuery();
            ResultSetMetaData metaData = result.getMetaData();

            while (result.next()) {

                Map<String ,Object> record = new HashMap<>();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    record.put(metaData.getColumnName(i),result.getObject(i));
                }
                returnResult.add(record);
            }

            statement.close();
            result.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnResult;
    }

    public static int executeUpdate(String sql, ArrayList<Object> params)
    {
        int result = -1;

        try {

            PreparedStatement statement = getConnection().prepareStatement(sql);


            for(int i=0;i<params.size();i++)
            {
                if(params.get(i) instanceof Integer)
                    statement.setInt(i + 1,(Integer)params.get(i));
                if(params.get(i) instanceof String)
                    statement.setString(i + 1,(String)params.get(i));
                if(params.get(i) instanceof Date)
                    statement.setDate(i + 1, (Date) params.get(i));
                if(params.get(i) instanceof Long)
                    statement.setLong(i + 1, (Long) params.get(i));
            }

            result = statement.executeUpdate();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}