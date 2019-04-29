package com.hiveofthoughts.connector;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.config.Database;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Connector {

    private static List<ConnectorAction > m_queue;

    private static Thread m_connectorThread;

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
        }catch(Exception e){
            System.out.println("Could not start connector update thread.");
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
        STOP_SERVER("STOP_SERVER");


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

    public static class ActionStopServer extends ConnectorAction {

        private String m_serverType;
        private int m_serverNumber;

        public ActionStopServer(String a_type, int a_number){
            super(ConnectorActionType.STOP_SERVER);

            m_serverType = a_type;
            m_serverNumber = a_number;
        }

        public void run(){
            System.out.println("Stopping server: " + m_serverType + "-" + m_serverNumber);
            try {
                ProcessBuilder builder = new ProcessBuilder();

                builder.command("docker", "stop", m_serverType + "-" + m_serverNumber);
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
            try {
                ProcessBuilder builder = new ProcessBuilder();

                builder.command("docker", "rm", m_serverType + "-" + m_serverNumber);
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
                System.out.println("Could not remove server: ");
                e.printStackTrace();
            }
            System.out.println("Server stopped and removed successfully.");
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

        public void run(){
            System.out.println("Starting server: " + m_serverType + "-" + m_serverNumber);
            try {
                ProcessBuilder builder = new ProcessBuilder();

                builder.command("docker", "run", "-dit", "--name", m_serverType + "-" + m_serverNumber, "-p", Config.ServerPorts.get(m_serverType + "-" + m_serverNumber) + ":25565", "-e", "SERVER_TYPE=" + m_serverType, "-e", "SERVER_NUMBER=" + m_serverNumber, "main");
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
    }
}
