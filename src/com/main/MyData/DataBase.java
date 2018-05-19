package com.main.MyData;

import com.main.OthersServer.ServerLogin;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {
    private static Connection con = null;

    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/TheBeautySalon";
    private static final String user = "root";
    private static final String password = "";

//    private static String URL = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;database=TheBeautySalon;integratedSecurity=true;";
    private static Statement st = null;
    private ArrayList<DataOrderLine> listOrders = new ArrayList<DataOrderLine>();
    private ArrayList<DataProfitLine> listProfit = new ArrayList<DataProfitLine>();
    private ArrayList<DataWorker> dataWorkers = new ArrayList<DataWorker>();
    private ArrayList<DataScheduleLine> listSchedule = new ArrayList<DataScheduleLine>();

    public DataBase() {
        try {

            if (con == null)
                con = DriverManager.getConnection(url, user, password);

            if (con != null) System.out.println("Connection Successful!\n");
            if (con == null) return;

            st = con.createStatement();
        }  catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }

    public boolean CheckEmailAndPassword(String email, String password) {
        boolean ret = false;
        try {
            String queryGetNumberOfTimes =
                    "SELECT * FROM Users_table WHERE Users_table.Email='" + email +
                            "' AND Users_table.Password='" + password + "'";
            ResultSet rs = st.executeQuery(queryGetNumberOfTimes);

            while (rs.next()) {
                int idFromDB = rs.getInt(1);
                String nameFromDB = rs.getString(2);
                String emailFromDB = rs.getString(3);
                String passwordFromDB = rs.getString(4);
                String secretCodeFromDB = rs.getString(5);
                String statusFromDB = rs.getString(6);

                ServerLogin.dataUser = new DataUser(idFromDB, nameFromDB, emailFromDB, statusFromDB, passwordFromDB, secretCodeFromDB);
                ret = true;
            }

            if (rs != null) rs.close();
            //if (st != null) st.close();
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
        return !ret;
    }

    public void addUser(String nameUser, String email, String password, String secretCode, String status) {
        try {
            String query = "INSERT INTO Users_table (Name ,Email ,Password ,SecretCode ,Status)" +
                    "VALUES ('" + nameUser + "','" + email + "','" + password + "','" + secretCode + "','" + status + "')";
            st.execute(query);

            //if (rs != null) rs.close();
            //if (st != null) st.close();

        } catch (SQLException e) {
            System.out.println("Connection failed!");
            //e.printStackTrace();
        }
    }

    public boolean GetUserForEmail(String email) {
        boolean ret = false;
        try {
            String queryGetNumberOfTimes =
                    "SELECT * FROM Users_table WHERE Users_table.Email='" + email + "'";
            ResultSet rs = st.executeQuery(queryGetNumberOfTimes);

            while (rs.next()) {

                String emailFromDB = rs.getString(3);

                System.out.println(emailFromDB);
                ret = true;
            }

            if (rs != null) rs.close();
            //if (st != null) st.close();
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
        return !ret;
    }

    public boolean CheckEmailAndSecretCode(String secretCode, String email) {
        boolean ret = false;
        System.out.println(email);
        System.out.println(secretCode);
        try {
            String queryGetNumberOfTimes =
                    "SELECT * FROM Users_table WHERE Users_table.Email='" + email +
                            "' AND Users_table.SecretCode='" + secretCode + "'";
            ResultSet rs = st.executeQuery(queryGetNumberOfTimes);

            while (rs.next()) {
                int idFromDB = rs.getInt(1);
                String nameFromDB = rs.getString(2);
                String emailFromDB = rs.getString(3);
                String passwordFromDB = rs.getString(4);
                String secretCodeFromDB = rs.getString(5);
                String statusFromDB = rs.getString(6);

                ServerLogin.dataUser = new DataUser(idFromDB, nameFromDB, emailFromDB, statusFromDB, passwordFromDB, secretCodeFromDB);

                ret = true;
            }

            if (rs != null) rs.close();
            //if (st != null) st.close();
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
        return !ret;
    }

    public void SetChagePassword(String password, DataUser user) {
        try {
//            String query = "INSERT INTO [dbo].[Users_table] ([Name] ,[Email] ,[Password] ,[SecretCode] ,[Status])" +
//                    "VALUES ('" + user.getNameUser() + "','" + user.getEmailUser() + "','" + password + "','" + user.getSecretCode() + "','" +
//                    user.getStatusUser() + "')";


            String query = "UPDATE Users_table SET Password = '" + password + "'" +
                    "WHERE Users_table.Email='" + user.getEmailUser() + "' AND Users_table.SecretCode='" + user.getSecretCode() + "'";
            st.execute(query);

            //if (rs != null) rs.close();
            //if (st != null) st.close();

        } catch (SQLException e) {
            System.out.println("Connection failed!");
            //e.printStackTrace();
        }
    }

    public void addOrder(String price, String performer, String customer, String date, String serveice, String email) {
        try {
            int priceInt = Integer.parseInt(removeLastChar(price));
            String query = "INSERT INTO [dbo].[Orders_table] ([Name],[Serveice],[Price],[Email],[Date],[Performer],[Discount])" +
                    "VALUES ('" + customer + "','" + serveice + "','" + priceInt + "','" + email + "','" + date + "','" + performer + "',0)";
            st.execute(query);

            query = "SELECT * FROM [dbo].[Profit_table] WHERE [dbo].[Profit_table].[Serveice]='" + serveice + "'";
            ResultSet rs = st.executeQuery(query);

            if (rs.next()) {
                int profit = Integer.parseInt(rs.getString(3)) + priceInt;
                int netprofit = profit / 100 * 60;
                query = "UPDATE [dbo].[Profit_table] SET [Profit] = '" + profit + "',[Netprofit] = '" + netprofit + "' WHERE [Serveice]='" + serveice + "'";
                st.execute(query);
            } else {
                query = "INSERT INTO [dbo].[Profit_table] ([Serveice],[Profit],[Netprofit]) VALUES ('" + serveice + "','" + priceInt + "','" + priceInt / 100 * 60 + "')";
                st.execute(query);
            }

            if (rs != null) rs.close();

        } catch (SQLException e) {
            System.out.println("Connection failed!");
            //e.printStackTrace();
        }
    }

    public boolean CheckDate(String date) {
        boolean ret = false;
        try {
            String queryGetNumberOfTimes =
                    "SELECT * FROM [dbo].[Orders_table] WHERE [dbo].[Orders_table].[Date]='" + date + "'";
            //"SELECT * FROM [dbo].[Users_table] WHERE [dbo].[Orders_table].[Date]='" + date+"'";
            ResultSet rs = st.executeQuery(queryGetNumberOfTimes);

            while (rs.next()) {

                String dateFromDB = rs.getString(3);
                System.out.println(dateFromDB);

                ret = true;
            }

            if (rs != null) rs.close();
            //if (st != null) st.close();
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
        return !ret;
    }

    private String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    public ArrayList<DataOrderLine> GetOrder() {
        try {
            String queryGetNumberOfTimes =
                    "SELECT * FROM Orders_table";
            ResultSet rs = st.executeQuery(queryGetNumberOfTimes);

            while (rs.next()) {
                String price = "";
                price += rs.getInt(4);
                String discount = "";
                discount += rs.getInt(8);
                listOrders.add(new DataOrderLine(rs.getInt(1), rs.getString(2), rs.getString(3), price,
                        rs.getString(5), rs.getString(6), rs.getString(7), discount));
            }

            if (rs != null) rs.close();
            //if (st != null) st.close();
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
        return listOrders;
    }

    public ArrayList<DataProfitLine> GetProfit() {
        try {
            String queryGetNumberOfTimes =
                    "SELECT * FROM Profit_table";
            ResultSet rs = st.executeQuery(queryGetNumberOfTimes);

            while (rs.next()) {
                listProfit.add(new DataProfitLine(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }

            if (rs != null) rs.close();
            //if (st != null) st.close();
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
        return listProfit;
    }

    public ArrayList<DataWorker> GetWorkers() {
        try {
            String queryGetNumberOfTimes =
                    "SELECT * FROM Performers_table";
            ResultSet rs = st.executeQuery(queryGetNumberOfTimes);

            while (rs.next()) {
                String s1 = rs.getString(2).trim();
                String s2 = rs.getString(3).trim();
                String s3 = rs.getString(4).trim();
                int s4 = rs.getInt(1);
                String s5 = rs.getString(5).trim();

                dataWorkers.add(new DataWorker(s1, s2, s3, s4, s5));
            }
            if (rs != null) rs.close();
            //if (st != null) st.close();
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
        return dataWorkers;
    }
    public ArrayList<DataScheduleLine> GetSchedule() {
        try {
            String queryGetNumberOfTimes =
                    "SELECT * FROM Schedule_table";
            ResultSet rs = st.executeQuery(queryGetNumberOfTimes);

            while (rs.next()) {
                int s1 = rs.getInt(1);
                String s2 = rs.getString(2).trim();
                String s3 = rs.getString(3).trim();
                String s4 = rs.getString(4).trim();

                listSchedule.add(new DataScheduleLine(s1, s2, s3, s4));
            }
            if (rs != null) rs.close();
            //if (st != null) st.close();
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
        return listSchedule;
    }

}
