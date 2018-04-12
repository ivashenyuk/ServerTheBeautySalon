package com.main;

public class Setting {
    private static final String ipConnection = "127.0.0.1";
    private static final int portConnection = 1356;
    private static final int portLogin = 1357;
    private static final int portRegistration = 1358;
    private static final int portGetWorkers = 1359;
    private static final int portGetProfit = 1360;
    private static final int portGetTimeWork = 1361;
    private static final int portGetStatistic = 1362;
    private static final int portGetSchedule = 1363;

    public static int getPortConnection() {
        return portConnection;
    }
    public static String getIpConnection() {
        return ipConnection;
    }
    public static int getPortLogin() {
        return portLogin;
    }
    public static int getPortRegistration() {
        return portRegistration;
    }
    public static int getPortGetWorkers() {
        return portGetWorkers;
    }
    public static int getPortGetProfit() {
        return portGetProfit;
    }
    public static int getPortGetTimeWork() {
        return portGetTimeWork;
    }
    public static int getPortGetStatistic() {
        return portGetStatistic;
    }
    public static int getPortGetSchedule() {
        return portGetSchedule;
    }
}
