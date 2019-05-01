package com.hiveofthoughts.connector;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.Main;
import com.hiveofthoughts.mc.config.Database;
import com.hiveofthoughts.mc.server.ServerBalance;
import com.hiveofthoughts.mc.server.ServerInfo;
import com.hiveofthoughts.mc.server.ServerType;
import org.bson.Document;
import org.bukkit.Bukkit;

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
    private static Thread m_serverLoadBalanceThread;

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
                        checkServerLoad();
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
            System.out.println("Could not start connector update and server check threads.");
            e.printStackTrace();
        }

        System.out.println("Ready to receive input.");
        String t_input = "";
        while (!(t_input = t_inputScanner.nextLine()).equalsIgnoreCase(ConnectorActionType.QUIT.getAction()) && m_threadRunning){
            // Start receiving input.
            ConnectorAction t_ca = convertStringToAction(t_input);
            if(submitAction(t_ca))
                System.out.println("Submitted action.");

        }
        // Do a final check for servers, to make sure they are closed successfully.
        System.out.println("Doing final check on servers...");
        checkServerStatus();
        getUpdates();
        performUpdates();

        m_threadRunning = false;
    }

    private static void checkServerLoad(){
        // Check to see that there is a min of each type of server
        System.out.println("Checking server loads...");
        for(int t_i = 0; t_i < ServerType.values().length; t_i ++){
            ServerType t_serverType = ServerType.values()[t_i];
            System.out.println("\t" + t_serverType.getName() + " has " + ServerInfo.getServerOnlineListOfType(t_serverType.getName().toLowerCase()).length + " servers.");
            if(ServerInfo.getServerOnlineListOfType(t_serverType.getName().toLowerCase()).length < t_serverType.getMinServers()) {
                System.out.println("\tNot enough servers of type: " + t_serverType.getName() + ". Starting one now..");
                // Use the built in functionality of ServerBalance to start a server with next available number.
                ServerBalance.startServer(t_serverType.getName().toLowerCase());
            }
        }
        System.out.println("\tDone checking server quantity.");

        // Check player counts on servers, if they exceed server full ratio, start up another one.
        //      If there are 0 players on a server, and the full ratio has not been met for all servers of the type, stop it.
        for(int t_i = 0; t_i < ServerType.values().length; t_i ++){
            ServerType t_serverType = ServerType.values()[t_i];
            if(ServerInfo.getServerOnlineListOfType(t_serverType.getName().toLowerCase()).length <= 0)
                continue;
            String[] t_servers = ServerInfo.getServerOnlineListOfType(t_serverType.getName().toLowerCase());
            int t_serverCount = t_servers.length;
            int t_totalPlayerCount = 0;
            for (String t_server : t_servers) {
                t_totalPlayerCount += ServerInfo.getPlayerCountOnServer(t_server);
            }
            boolean t_shouldServerRemove = false;
            if (t_serverCount * t_serverType.getMaxPlayers() * t_serverType.getFullRatio() > t_totalPlayerCount)
                t_shouldServerRemove = true;
            if (t_shouldServerRemove && ServerInfo.getServerOnlineListOfType(t_serverType.getName().toLowerCase()).length > t_serverType.getMinServers())
                for (String t_server : t_servers) {
                    if (ServerInfo.getPlayerCountOnServer(t_server) <= 0) {
                        System.out.println("\tToo many servers of type: " + t_serverType.getName() + ". Stopping one with no players now..");
                        submitAction(new ActionStopServer(t_serverType.getName(), Integer.parseInt(t_server.substring(t_server.indexOf(Config.ServerNameMiddle) + 1))));
                        break;
                    }
                }
            else if (ServerInfo.getServerOnlineListOfType(t_serverType.getName().toLowerCase()).length > t_serverType.getMinServers()) {
                System.out.println("\tNot enough servers of type: " + t_serverType.getName() + " for " + t_totalPlayerCount + " players." + " Starting one now..");
                // Use the built in functionality of ServerBalance to start a server with next available number.
                ServerBalance.startServer(t_serverType.getName().toLowerCase());
            }
        }
        System.out.println("Done checking server loads.");
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
                    System.out.println("\tServer : " + t_serverName + " found to be out of date. Sending restart command. ");
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
                    System.out.println("\tServer : " + t_serverName + " was not found to be up, but exists in database. Removing...");
                    Database.getInstance().removeDocument(Database.Table_ServerInfo, Database.Field_Port, t_portNum + "");

                    submitAction(new ActionStopServer(ServerBalance.getMainServer(t_serverName), ServerBalance.getServerNumber(t_serverName)));
                    submitAction(new ActionStopServer(ServerBalance.getMainServer(t_serverName), ServerBalance.getServerNumber(t_serverName), true));
                }catch(Exception e) { }
                // If there is an update list, remove it
                try{
                    getServerActions(ServerBalance.getMainServer(t_serverName), ServerBalance.getServerNumber(t_serverName));
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
            try {
                Database.getInstance().removeDocument(Database.Table_ServerInfo, Database.Field_Name, getUpdateListName(a_serverName, a_serverNumber));
            } catch(Exception e){
                e.printStackTrace();
            }
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
        System.out.println("Issuing server action: " + a_action.toString() + " for " + a_serverName + Config.ServerNameMiddle + a_serverNumber);
        // Debug purposes. (new Exception()).printStackTrace();
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
        } else if(a_args[0].equalsIgnoreCase(ConnectorActionType.QUIT.getAction())) {
            r_ret = new ActionQuit();
        } else if(a_args[0].equalsIgnoreCase(ConnectorActionType.GLOBAL_MESSAGE.getAction())) {
            String t_message = "";
            for(int t_i = 1; t_i < a_args.length; t_i++)
                t_message += a_args[t_i] + " ";
            r_ret = new ActionGlobalMessage(t_message);
        } else if(a_args[0].equalsIgnoreCase(ConnectorActionType.STOP_ALL.getAction())) {
            r_ret = new ActionStopAllServers();
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
        STOP_ALL("STOP_ALL"),
        RESTART_SERVER("RESTART_SERVER"),
        GLOBAL_MESSAGE("GC");


        private String m_action = "";

        ConnectorActionType(String a_name){
            m_action = a_name;
        }

        public String getAction(){
            return m_action;
        }
    }

    public static class ActionGlobalMessage extends ConnectorAction{

        private String m_message;

        public ActionGlobalMessage(String a_message){
            super(ConnectorActionType.GLOBAL_MESSAGE);
            m_message = a_message;
        }

        public void run(){
            issueAllServerAction(this);
        }

        @Override
        public void runServer(Main a_m){
            String[] t_players = a_m.getPlayerList().keySet().toArray(new String[a_m.getPlayerList().keySet().size()]);
            for(String t_player : t_players) {
                Bukkit.getServer().getPlayer(t_player).sendMessage(m_message);
            }
        }

        public String toString(){
            return getTypeString() + " " + m_message;
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

    public static class ActionQuit extends ConnectorAction {
        public ActionQuit(){
            super(ConnectorActionType.QUIT);
        }

        public void run(){
            m_threadRunning = false;
        }

        public String toString(){
            return getTypeString();
        }
    }

    public static class ActionStopAllServers extends ConnectorAction {
        public ActionStopAllServers(){
            super(ConnectorActionType.STOP_ALL);
        }

        public void run(){
            System.out.println("Stopping all servers.");
            String[] t_servers = Config.ServerPorts.keySet().toArray(new String[Config.ServerPorts.keySet().size()]);
            for(String t_server : t_servers) {
                submitAction(new ActionStopServer(ServerBalance.getMainServer(t_server), ServerBalance.getServerNumber(t_server)));
            }
            submitAction(new ActionQuit());
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

        private boolean m_removeOnly;

        public ActionStopServer(String a_type, int a_number){
            this(a_type, a_number, false);
        }

        public ActionStopServer(String a_type, int a_number, boolean a_removeOnly){
            super(ConnectorActionType.STOP_SERVER);

            m_serverType = a_type.toLowerCase();
            m_serverNumber = a_number;

            m_removeOnly = a_removeOnly;
        }

        @Override
        public void runServer(Main a_m){
            ServerBalance.stopServer("Connector says so.");
        }

        public void run(){
            System.out.println("Stopping server: " + m_serverType + "-" + m_serverNumber);
            if(!m_removeOnly)
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
            // Remove the update actions for the server after successful stop,
            System.out.println("Removing update document for server, " + getUpdateListName(m_serverType, m_serverNumber));
            getServerActions(m_serverType, m_serverNumber);
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
        public void runServer(Main a_m){}
    }
}
