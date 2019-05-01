package com.hiveofthoughts.connector;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.config.Database;
import com.hiveofthoughts.mc.server.ServerBalance;
import com.hiveofthoughts.mc.server.ServerInfo;
import com.hiveofthoughts.mc.server.ServerType;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Connector {

    private static List<ConnectorAction > m_queue;

    private static Thread m_connectorThread;
    private static Thread m_serverCheckThread;

    private static boolean m_threadRunning = true;

    public static void main(String[] args){
        System.out.println("Starting up com.hiveofthoughts.connector.Connector");

        m_queue = new ArrayList<>();
        Scanner t_inputScanner = new Scanner(System.in);

        // ensure database is setup and ready to use.
        Database.getInstance();

        // Confirm there is an update list for the connector.
        try{
            Database.getInstance().getDocument(Database.Table_NetworkConfig, Database.Field_Name, getConnectorListName()).get(Database.Field_Value);
        }catch (Exception e){
            System.out.println("Suspected update list does not exist.");
            e.printStackTrace();
        }


        try {
            m_connectorThread = new Thread(){
                public void run(){
                    while(m_threadRunning){
                        getUpdates();
                        performUpdates();
                        try {
                            sleep(500);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            };
            m_connectorThread.start();

            m_serverCheckThread = new Thread(){
                public void run(){
                    while(m_threadRunning){
                        checkServerStatus();
                        try {
                            sleep(1000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            };
            m_serverCheckThread.start();
        }catch(Exception e){
            System.out.println("Could not start connector update and server check thread.");
            e.printStackTrace();
        }

        System.out.println("Ready to receive input.");
        String t_input = "";
        while (!(t_input = t_inputScanner.nextLine()).equalsIgnoreCase(ConnectorActionType.QUIT.getAction())){
            // Start receiving input.
            ConnectorAction t_ca = convertStringToAction(t_input);
            if(submitAction(t_ca))
                System.out.println("Submitted action.");

        }
        m_threadRunning = false;
    }


    private static void checkServerStatus(){
        String[] t_serverList = Config.ServerPorts.keySet().toArray(new String[Config.ServerPorts.size()]);

        for(String t_serverName : t_serverList) {
            boolean t_serverUp = true;
            boolean t_versionUpToDate = true;
            int t_portNum = Config.ServerPorts.get(t_serverName);


            try {
                // Socket t_s = new Socket(Config.ServerHostName, Config.ServerPorts.get(t_d));
                InetAddress t_addr = InetAddress.getByName(Config.ServerHostName);
                SocketAddress t_sockAddr = new InetSocketAddress(t_addr, t_portNum);
                Socket t_sock = new Socket();
                t_sock.connect(t_sockAddr, Config.ServerPingTimeout);

                if(!((String)Database.getInstance().getDocument(Database.Table_ServerInfo, Database.Field_Port, t_portNum + "").get(Database.Field_ServerVersion)).equalsIgnoreCase(ServerType.getFromFullName(t_serverName).getVersion())){
                    System.out.println("Server : " + t_serverName + " found to be out of date. Sending restart command. ");
                    t_versionUpToDate = false;
                    submitAction(new ActionRestartServer(t_serverName.substring(0, t_serverName.indexOf(Config.ServerNameMiddle)), Integer.parseInt(t_serverName.substring(t_serverName.indexOf(Config.ServerNameMiddle) + 1))));
                }
            } catch (IOException e) {
                t_serverUp = false;
            } catch (Exception e) {
            }
            if(!t_serverUp){
                // Make sure that the database has the document removed.
                try{
                    // if(((String)Database.getInstance().getDocument(Database.Table_ServerInfo, Database.Field_Port, t_portNum + "").get(Database.Field_ServerStatus)).equals(Config.StatusInProgress))
                    Database.getInstance().getDocument(Database.Table_ServerInfo, Database.Field_Port, t_portNum + "");
                    System.out.println("Server : " + t_serverName + " was not found to be up, but exists in database. Removing...");
                    Database.getInstance().removeDocument(Database.Table_ServerInfo, Database.Field_Port, t_portNum + "");

                    submitAction(new ActionStopServer(t_serverName.substring(0, t_serverName.indexOf(Config.ServerNameMiddle)), Integer.parseInt(t_serverName.substring(t_serverName.indexOf(Config.ServerNameMiddle) + 1))));
                }catch(Exception e) { }
                // If there is an update list, remove it
                try{
                    Database.getInstance().removeDocument(Database.Table_ServerInfo, Database.Field_Name, getUpdateListName(t_serverName.substring(0, t_serverName.indexOf(Config.ServerNameMiddle)), Integer.parseInt(t_serverName.substring(t_serverName.indexOf(Config.ServerNameMiddle) + 1))));
                } catch(Exception e){ e.printStackTrace();}
            }
        }
    }

    private static void performUpdates(){
        // Run the actions.
        for(int t_i = 0; t_i < m_queue.size(); t_i ++){
            m_queue.get(t_i).run();
        }
        // Clear the queue.
        m_queue.clear();
    }

    private static void getUpdates(){
        // Update the m_queue to have the actions from the connector_update in networkConfig collection.
        try {
            List<String> t_updates = (ArrayList<String>) Database.getInstance().getDocument(Database.Table_NetworkConfig, Database.Field_Name, getConnectorListName()).get(Database.Field_Value);
            Database.getInstance().updateDocument(Database.Table_NetworkConfig, Database.Field_Name, getConnectorListName(), Database.Field_Value, new ArrayList<String>());

            for(String t_update : t_updates){
                m_queue.add(convertStringToAction(t_update));
            }
        } catch (Exception e){
            System.out.println("Could not get updates.");
            e.printStackTrace();
        }
    }

    public static boolean submitAction(ConnectorAction a_action){
        try {
            if(Database.getInstance().fieldExists(Database.Table_NetworkConfig, Database.Field_Name, getConnectorListName(), Database.Field_Value) == false){
               Database.getInstance().updateDocument(Database.Table_NetworkConfig, Database.Field_Name, getConnectorListName(), Database.Field_Value, new ArrayList<String>());
            }

            List<String> t_updates = (ArrayList<String>) Database.getInstance().getDocument(Database.Table_NetworkConfig, Database.Field_Name, getConnectorListName()).get(Database.Field_Value);
            if(t_updates != null)
                t_updates.add(a_action.toString());
            else {
                t_updates = new ArrayList<>();
                t_updates.add(a_action.toString());
            }
            Database.getInstance().updateDocument(Database.Table_NetworkConfig, Database.Field_Name, getConnectorListName(), Database.Field_Value, t_updates);
        } catch (Exception e){
            System.out.println("Could not submit action.");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getConnectorListName(){
        return "connector_update";
    }

    public static String getUpdateListName(String a_serverName, int a_serverNumber){
        return "connector_" + a_serverName + "-" + a_serverNumber;
    }

    public static List<String> getServerActions(){
        return getServerActions(ServerInfo.getInstance().getServerName(), ServerInfo.getInstance().getServerNumber());
    }

    // Get a servers actions, and remove the document in database.
    public static List<String> getServerActions(String a_serverName, int a_serverNumber){
        a_serverName = a_serverName.toLowerCase();
        List<String> r_currentActions;
        try{
            r_currentActions = (List<String>) Database.getInstance().getDocument(Database.Table_ServerInfo, Database.Field_Name, getUpdateListName(a_serverName, a_serverNumber)).get(Database.Field_Value);
            Database.getInstance().removeDocument(Database.Table_ServerInfo, Database.Field_Name, getUpdateListName(a_serverName, a_serverNumber));
        } catch(Exception e){
            r_currentActions = new ArrayList<>();
        }
        return r_currentActions;
    }

    public static boolean issueAllServerAction(ConnectorAction a_action){
        String[] t_serverList = ServerInfo.getInstance().getServerCompleteOnlineList();

        for(String t_serverName : t_serverList) {
            String t_start = null;
            int t_number = -1;
            try{
                t_start = t_serverName.substring(0, t_serverName.indexOf(Config.ServerNameMiddle));
                t_number = Integer.parseInt(t_serverName.substring(t_serverName.indexOf(Config.ServerNameMiddle) + 1));
            } catch(Exception e){
                System.out.println("Error issuing server action for: " + t_serverName);
            }
            if(t_start != null && t_number != -1) {
                issueServerAction(a_action, t_start, t_number);
            }
        }
        return true;
    }

    public static boolean issueServerAction(ConnectorAction a_action, String a_serverName, int a_serverNumber) {
        try{
            boolean t_fieldExists = true;

            List<String> t_currentActions;
            try{
                t_currentActions = (List<String>) Database.getInstance().getDocument(Database.Table_ServerInfo, Database.Field_Name, getUpdateListName(a_serverName, a_serverNumber)).get(Database.Field_Value);
            } catch(Exception e){
                Database.getInstance().insertDocument(Database.Table_ServerInfo, new Document(Database.Field_Name, getUpdateListName(a_serverName, a_serverNumber)));
                t_currentActions = new ArrayList<>();
                t_fieldExists = false;
            }
            t_currentActions.add(a_action.toString());
            if(t_fieldExists)
                Database.getInstance().updateDocument(Database.Table_ServerInfo, Database.Field_Name, getUpdateListName(a_serverName, a_serverNumber), Database.Field_Value, t_currentActions);
            else
                Database.getInstance().insertDocument(Database.Table_ServerInfo, new Document(Database.Field_Name, getUpdateListName(a_serverName, a_serverNumber)).append(Database.Field_Value, t_currentActions));
        } catch(Exception e){
            System.out.println("Could not issue server action..");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static ConnectorAction convertStringToAction(String[] a_args){
        if(a_args.length < 1)
            return new ActionNothing();

        ConnectorAction r_ret = new ActionNothing();

        if(a_args[0].equalsIgnoreCase(ConnectorActionType.START_SERVER.getAction())) {
            try {
                r_ret = new ActionStartServer(a_args[1], Integer.parseInt(a_args[2]));
            }catch (Exception e){
                System.out.println("Unable to parse START_SERVER action");
                e.printStackTrace();
            }
        } else if(a_args[0].equalsIgnoreCase(ConnectorActionType.STOP_SERVER.getAction())){
            try{
                r_ret = new ActionStopServer(a_args[1], Integer.parseInt(a_args[2]));
            }catch(Exception e){
                System.out.println("Unable to parse STOP_SERVER action");
                e.printStackTrace();
            }
        } else if(a_args[0].equalsIgnoreCase(ConnectorActionType.RESTART_SERVER.getAction())) {
            try{
                r_ret = new ActionRestartServer(a_args[1], Integer.parseInt(a_args[2]));
            }catch(Exception e){
                System.out.println("Unable to parse RESTART_SERVER action");
                e.printStackTrace();
            }
        } else if(a_args[0].equalsIgnoreCase(ConnectorActionType.HELP.getAction())) {
            r_ret = new ActionHelp();
        }

        return r_ret;
    }

    public static ConnectorAction convertStringToAction(String a_args){
        return convertStringToAction(a_args.split(" "));
    }


    public enum ConnectorActionType{
        QUIT("QUIT"),
        HELP("HELP"),
        NOTHING("NOTHING"),
        START_SERVER("START_SERVER"),
        STOP_SERVER("STOP_SERVER"),
        RESTART_SERVER("RESTART_SERVER");


        private String m_action = "";

        ConnectorActionType(String a_name){
            m_action = a_name;
        }

        public String getAction(){
            return m_action;
        }
    }

    public static class ActionHelp extends ConnectorAction {
        public ActionHelp(){
            super(ConnectorActionType.HELP);
        }

        public void run(){
            System.out.println("LIST OF ACTION TYPES");
            for(int t_i=0;t_i<ConnectorActionType.values().length;t_i++){
                System.out.println(t_i + " - " + ConnectorActionType.values()[t_i].getAction());
            }
        }

        public String toString(){
            return getTypeString();
        }
    }

    public static class ActionRestartServer extends ConnectorAction {
        private String m_serverType;
        private int m_serverNumber;

        public ActionRestartServer(String a_type, int a_number){
            super(ConnectorActionType.RESTART_SERVER);

            m_serverType = a_type.toLowerCase();
            m_serverNumber = a_number;
        }

        public void run(){
            m_queue.add(new ActionStopServer(m_serverType, m_serverNumber));
            m_queue.add(new ActionStartServer(m_serverType, m_serverNumber));
        }

        public String toString(){
            return "RESTART_SERVER " + m_serverType + " " + m_serverNumber;
        }
    }

    public static class ActionStopServer extends ConnectorAction {

        private String m_serverType;
        private int m_serverNumber;

        public ActionStopServer(String a_type, int a_number){
            super(ConnectorActionType.STOP_SERVER);

            m_serverType = a_type.toLowerCase();
            m_serverNumber = a_number;
        }

        @Override
        public void runServer(){
            ServerBalance.stopServer("Connector says so.");
        }

        public void run(){
            System.out.println("Stopping server: " + m_serverType + "-" + m_serverNumber);
            try {
                // Tell the database to stop server.
                issueServerAction(this, m_serverType, m_serverNumber);

                ProcessBuilder builder = new ProcessBuilder();

                builder.command("docker", "stop", "-t", "30", m_serverType + "-" + m_serverNumber);
                Process t_p = builder.start();

                /**
                 ProcessBuilder t_pb = new ProcessBuilder(t_commands);
                 Process t_p = t_pb.start();
                 */
                BufferedReader br = new BufferedReader(new InputStreamReader(t_p.getInputStream()));

                t_p.waitFor();

                System.out.println("Output of running is: ");
                String t_line;
                while ((t_line = br.readLine()) != null) {
                    System.out.println(t_line);
                }
            }catch(Exception e){
                System.out.println("Could not stop server: ");
                e.printStackTrace();
            }
            // If the server is not permanent, remove.
            if(!ServerType.getFromName(m_serverType).isPermanent()) {
                try {
                    ProcessBuilder builder = new ProcessBuilder();

                    builder.command("docker", "rm", m_serverType + Config.ServerNameMiddle + m_serverNumber);
                    Process t_p = builder.start();

                    /**
                     ProcessBuilder t_pb = new ProcessBuilder(t_commands);
                     Process t_p = t_pb.start();
                     */
                    BufferedReader br = new BufferedReader(new InputStreamReader(t_p.getInputStream()));

                    t_p.waitFor();

                    System.out.println("Output of running is: ");
                    String t_line;
                    while ((t_line = br.readLine()) != null) {
                        System.out.println(t_line);
                    }
                    System.out.println("Server removed.");
                } catch (Exception e) {
                    System.out.println("Could not remove server: ");
                    e.printStackTrace();
                }
            }
            System.out.println("Server stopped successfully.");
        }

        public String toString(){
            return getTypeString() + " " + m_serverType + " " + m_serverNumber;
        }
    }

    public static class ActionNothing extends ConnectorAction {
        public ActionNothing(){
            super(ConnectorActionType.NOTHING);
        }

        public void run(){
            System.out.println("Nothing action performed.");
        }

        public String toString(){
            return "NOTHING";
        }
    }

    public static class ActionStartServer extends ConnectorAction {

        private String m_serverType;
        private int m_serverNumber;

        public ActionStartServer(String a_type, int a_number){
            super(ConnectorActionType.START_SERVER);

            m_serverType = a_type;
            m_serverNumber = a_number;
        }

        public void run() {
            System.out.println("Starting server: " + m_serverType + "-" + m_serverNumber);
            // If the server is permanent, first try to start it.
            if (ServerType.getFromName(m_serverType).isPermanent()) {
                try {
                    ProcessBuilder builder = new ProcessBuilder();

                    builder.command("docker", "start", m_serverType + "-" + m_serverNumber);
                    System.out.println(builder.command().toArray().toString());
                    Process t_p = builder.start();

                    /**
                     ProcessBuilder t_pb = new ProcessBuilder(t_commands);
                     Process t_p = t_pb.start();
                     */
                    BufferedReader br = new BufferedReader(new InputStreamReader(t_p.getInputStream()));

                    t_p.waitFor();

                    System.out.println("Output of running is: ");
                    String t_line;
                    while ((t_line = br.readLine()) != null) {
                        System.out.println(t_line);
                    }
                } catch (Exception e) {
                    System.out.println("Could not start server: ");
                    e.printStackTrace();
                }
            }
            try {
                ProcessBuilder builder = new ProcessBuilder();

                builder.command("docker", "run", "-dit", "--name", m_serverType + "-" + m_serverNumber, "-p", Config.ServerPorts.get(m_serverType + "-" + m_serverNumber) + ":25565", "-e", "SERVER_TYPE=" + m_serverType, "-e", "SERVER_NUMBER=" + m_serverNumber, m_serverType.toLowerCase());
                System.out.println(builder.command().toArray().toString());
                Process t_p = builder.start();

                /**
                 ProcessBuilder t_pb = new ProcessBuilder(t_commands);
                 Process t_p = t_pb.start();
                 */
                BufferedReader br = new BufferedReader(new InputStreamReader(t_p.getInputStream()));

                t_p.waitFor();

                System.out.println("Output of running is: ");
                String t_line;
                while ((t_line = br.readLine()) != null) {
                    System.out.println(t_line);
                }
            }catch(Exception e){
                System.out.println("Could not start server: ");
                e.printStackTrace();
            }
            System.out.println("Server started successfully.");
        }

        public String toString(){
            return getTypeString() + " " + m_serverType + " " + m_serverNumber;
        }
    }

    public static abstract class ConnectorAction {

        private ConnectorActionType m_type;

        public ConnectorAction(ConnectorActionType a_type){
            m_type = a_type;
        }

        public ConnectorActionType getType(){
            return m_type;
        }

        public String getTypeString(){
            return m_type.getAction();
        }

        public abstract void run();

        public abstract String toString();

        // Method for handling if the command is to be executed on server.
        public void runServer(){}
    }
}
