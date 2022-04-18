package Project4;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;
import javafx.application.Application;
import javafx.stage.Stage;

public class Server extends Application {
  
  private int clientNo = 0;
  public boolean twoClients = false;
  
  static Vector<HandleAClient> clients = new Vector<>();
  HandleAClient c1, c2;

  @Override 
  public void start(Stage primaryStage) {
    

    new Thread( () -> {
      try {
        // Create a server socket
        ServerSocket serverSocket = new ServerSocket(8000);
        
    
        while (true) {
          // Listen for a new connection request
          Socket socket = serverSocket.accept();
          
          DataInputStream in = new DataInputStream(socket.getInputStream());
          DataOutputStream out = new DataOutputStream(socket.getOutputStream());
    
          // Increment clientNo
          clientNo++;
          
          

            // Find the client's host name, and IP address
            InetAddress inetAddress = socket.getInetAddress();
              System.out.println(inetAddress.getHostName());
            
          
          
          // Create and start a new thread for the connection
          if(clientNo == 1){
          c1 = new HandleAClient(socket, clientNo, in, out);
          
          clients.add(c1);
          
          new Thread(c1).start();
          } else {
              c2 = new HandleAClient(socket, clientNo, in, out);
          
          clients.add(c2);
          twoClients = true;
          
          new Thread(c2).start();
          }
        }
      }
      catch(IOException ex) {
        System.err.println(ex);
      }
    }).start();
  }
  
  // Define the thread class for handling new connection
  class HandleAClient implements Runnable {
    
    private Socket socket; // A connected socket
    public int clientNo;
    final DataInputStream in;
    final DataOutputStream out;

    /** Construct a thread */
    public HandleAClient(Socket socket, int clientNo, DataInputStream in, DataOutputStream out) {
      this.socket = socket;
      this.clientNo = clientNo;
      this.in = in;
      this.out = out;
    }

    String text;
    boolean loggedIn = false;
       
        boolean root = false, john = false, sally = false, qiang = false;           //Creating all necessary variables
        boolean rootOpen = false, johnOpen = false, sallyOpen = false, qiangOpen = false;
        
        Vector<String> users = new Vector<String>();
        
        File rootSol = new File("root_solutions.txt");      //I could not get the file writer to clear the old file and append new text
        File johnSol = new File("john_solutions.txt");      //so I had to delete the files after they were "made" and this worked.
        File sallySol = new File("sally_solutions.txt");
        File qiangSol = new File("qiang_solutions.txt");
        
       
    
    
    @Override
    public void run() {
        
        try{    //Opening logins file and storing the users in a vector
            
            File logins = new File("C:\\Users\\cptn_\\JavaProjects\\CIS427 Prog 1\\build\\classes\\logins.txt");
            Scanner scnr = new Scanner(logins);
            while(scnr.hasNextLine()){
                users.add(scnr.nextLine()); 
            }
            scnr.close();
            
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        
        
        
        
        
        while(true){
            try {
                
                
                while(true){
 
                        text = this.in.readUTF();

                        System.out.println(text);
                        
                        String arr[] = text.split(" ", -1);
                            
                    
                        
                        
                            if(arr[0].equals("LOGIN")){ //For LOGINS

                                for(int i = 0; i < users.size(); i++){
                                    String name;

                                    String data[] = users.get(i).split(" ", -1);
                                    if(data[0].equals(arr[1]) && data[1].equals(arr[2])){   //Logging in the correct user
                                        loggedIn = true;
                                        out.writeUTF("SUCCESS");
                                        name = data[0];
                                        
                                        if(name.equals("root")){
                                            root = true;
                                        } else if(name.equals("john")){
                                            john = true;
                                        } else if (name.equals("sally")){
                                            sally = true;
                                        } else {
                                            qiang = true;
                                        }
                                        break;
                                    } else { loggedIn = false; }


                                } 
                                    if(loggedIn == false){
                                        out.writeUTF("FAILURE: Please provide correct username and password. Try again.");      //End LOGINS
                                    } 

                            } else if(arr[0].equals("SOLVE")){      //For SOLVE
                                if(loggedIn){
                                    if(arr[1].equals("-c")){        //For solving a circle
                                        double circum, area;
                                        int radius;
                                        if(arr.length == 3){
                                            radius = Integer.parseInt(arr[2]);    //Performing necessary calculations for a circle
                                            circum = 2 * 3.14159 * radius;
                                            circum = circum * Math.pow(10, 2);
                                            circum = Math.floor(circum);
                                            circum = circum / Math.pow(10, 2);  //This reduces the decimal points to 2 
                                            area = 3.14159 * radius *radius;
                                            area = area * Math.pow(10, 2);
                                            area = Math.floor(area);
                                            area = area / Math.pow(10, 2);
                                            
                                            String result = "Circle's circumference is " + circum + " and area is " + area;
                                            out.writeUTF(result);
                                            result += " done ";
                                            if(root){               //Opening the correct file and writing to the file
                                                try{
                                                    
                                                    if(!rootOpen){
                                                        rootSol.createNewFile();
                                                        rootOpen = true;
                                                    }
                                                    FileWriter writer = new FileWriter(rootSol, true);
                                                    writer.write(result);
                                                    writer.close();
                                                } 
                                                catch (IOException e){
                                                    e.printStackTrace();
                                                }
                                            } else if(john){
                                                try{
                                                   
                                                    if(!johnOpen){
                                                        johnSol.createNewFile();
                                                        johnOpen = true;
                                                    }
                                                    FileWriter writer = new FileWriter(johnSol, true);
                                                    writer.write(result);
                                                    writer.close();
                                                    
                                                    
                                                } 
                                                catch (IOException e){
                                                    e.printStackTrace();
                                                }
                                            }else if (sally){
                                                try{
                                               
                                                    if(!sallyOpen){
                                                        sallySol.createNewFile();
                                                        sallyOpen = true;
                                                    }
                                                    FileWriter writer = new FileWriter(sallySol, true);
                                                    writer.write(result);
                                                    writer.close();
                                                    
                                                    
                                                } 
                                                catch (IOException e){
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                try{
                                                   
                                                    if(!qiangOpen){
                                                        qiangSol.createNewFile();
                                                        qiangOpen = true;
                                                    }
                                                    FileWriter writer = new FileWriter(qiangSol, true);
                                                    writer.write(result);
                                                    writer.close();
                                                    
                                                    
                                                } 
                                                catch (IOException e){
                                                    e.printStackTrace();
                                                }
                                            }
                                        } else {
                                            out.writeUTF("Error: No radius found.");
                                        }                                                   //END circle solve
                                        
                                    } else if(arr[1].equals("-r")){     //Begin rectangle solve
                                        int side1, side2;
                                        int perim, area;
                                        String result;
                                        if(arr.length == 3){
                                            side1 = Integer.parseInt(arr[2]);
                                            area = side1 * side1;
                                            perim = side1 * 4;
                                            
                                            result = "Rectangle's perimeter is " + perim + " and area is " + area;
                                            out.writeUTF(result);
                                            result += " done ";
                                            if(root){                   //REPEATED code of printing to file
                                                try{
                                                    
                                                    if(!rootOpen){
                                                        rootSol.createNewFile();
                                                        rootOpen = true;
                                                    }
                                                    FileWriter writer = new FileWriter(rootSol, true);
                                                    writer.write(result);
                                                    writer.close();
                                                } 
                                                catch (IOException e){
                                                    e.printStackTrace();
                                                }
                                            } else if(john){
                                                try{
                                                    
                                                    if(!johnOpen){
                                                        johnSol.createNewFile();
                                                        johnOpen = true;
                                                    }
                                                    FileWriter writer = new FileWriter(johnSol, true);
                                                    writer.write(result);
                                                    writer.close();
                                                    
                                                    
                                                } 
                                                catch (IOException e){
                                                    e.printStackTrace();
                                                }
                                            }else if (sally){
                                                try{
                                                    
                                                    if(!sallyOpen){
                                                        sallySol.createNewFile();                       //Checking all booleans to see who is logged in, and if it is their first time logging in or not
                                                        sallyOpen = true;
                                                    }
                                                    FileWriter writer = new FileWriter(sallySol, true);
                                                    writer.write(result);
                                                    writer.close();
                                                    
                                                    
                                                } 
                                                catch (IOException e){
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                try{
                                                    
                                                    if(!qiangOpen){
                                                        qiangSol.createNewFile();
                                                        qiangOpen = true;
                                                    }
                                                    FileWriter writer = new FileWriter(qiangSol, true);
                                                    writer.write(result);
                                                    writer.close();
                                                    
                                                    
                                                } 
                                                catch (IOException e){
                                                    e.printStackTrace();
                                                }
                                            }
                                        } else if(arr.length == 4){
                                            side1 = Integer.parseInt(arr[2]);
                                            side2 = Integer.parseInt(arr[3]);
                                            area = side1 * side2;
                                            perim = (side1 * 2) + (side2 * 2);
                                            
                                            result = "Rectangle's perimeter is " + perim + " and area is " + area;
                                            out.writeUTF(result);
                                            result += " done ";
                                            if(root){                       //REPEATED code of printing to correct file
                                                try{
                                                    
                                                    if(!rootOpen){
                                                        rootSol.createNewFile();
                                                        rootOpen = true;
                                                    }
                                                    FileWriter writer = new FileWriter(rootSol, true);
                                                    writer.write(result);
                                                    writer.close();
                                                } 
                                                catch (IOException e){
                                                    e.printStackTrace();
                                                }
                                            } else if(john){
                                                try{
                                                   
                                                    if(!johnOpen){
                                                        johnSol.createNewFile();
                                                        johnOpen = true;
                                                    }
                                                    FileWriter writer = new FileWriter(johnSol, true);
                                                    writer.write(result);
                                                    writer.close();
                                                    
                                                    
                                                } 
                                                catch (IOException e){
                                                    e.printStackTrace();
                                                }
                                            }else if (sally){
                                                try{
                                                    
                                                    if(!sallyOpen){
                                                        sallySol.createNewFile();
                                                        sallyOpen = true;
                                                    }
                                                    FileWriter writer = new FileWriter(sallySol, true);
                                                    writer.write(result);
                                                    writer.close();
                                                    
                                                    
                                                } 
                                                catch (IOException e){
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                try{
                                                    
                                                    if(!qiangOpen){
                                                        qiangSol.createNewFile();
                                                        qiangOpen = true;
                                                    }
                                                    FileWriter writer = new FileWriter(qiangSol, true);
                                                    writer.write(result);
                                                    writer.close();
                                                    
                                                    
                                                } 
                                                catch (IOException e){
                                                    e.printStackTrace();
                                                }
                                            }
                                        } 
                                    }       //END rectangle solve
                                    
                                } else {
                                    out.writeUTF("You must be logged in to access the SOLVED command.");
                                }
                            }       //End SOLVE
                            else if(arr[0].equals("MESSAGE")){
                                if(twoClients){
                                    if(this.clientNo == 1){
                                        if(arr[1].equals("john")){
                                            String temp = "Message from John: ";
                                            if(c2.john){
                                                
                                                for(int i = 2; i < arr.length; i++){
                                                    temp += arr[i] + " ";
                                                }
                                                c2.out.writeUTF(temp);
                                                
                                                c2.out.flush();
                                                this.out.writeUTF("Message sent.");
                                            } else {
                                                this.out.writeUTF("User is not logged in.");
                                                this.out.flush();
                                            }
                                        } else if(arr[1].equals("sally")){
                                            String temp = "Message from Sally: ";
                                            if(c2.sally){
                                                
                                                for(int i = 2; i < arr.length; i++){
                                                    temp += arr[i] + " ";
                                                }
                                                c2.out.writeUTF(temp);
                                                
                                                c2.out.flush();
                                                this.out.writeUTF("Message sent.");
                                            } else {
                                                this.out.writeUTF("User is not logged in.");
                                                this.out.flush();
                                            }
                                        } else if(arr[1].equals("qiang")){
                                            String temp = "Message from Qiang: ";
                                            if(c2.qiang){
                                                
                                                for(int i = 2; i < arr.length; i++){
                                                    temp += arr[i] + " ";
                                                }
                                                c2.out.writeUTF(temp);
                                                
                                                c2.out.flush();
                                                this.out.writeUTF("Message sent.");
                                            }else {
                                                this.out.writeUTF("User is not logged in.");
                                                this.out.flush();
                                            }
                                        } else if(arr[1].equals("root")){
                                            String temp = "Message from Root: ";
                                            if(c2.root){
                                                
                                                for(int i = 2; i < arr.length; i++){
                                                    temp += arr[i] + " ";
                                                }
                                                c2.out.writeUTF(temp);
                                                
                                                c2.out.flush();
                                                this.out.writeUTF("Message sent.");
                                            }else {
                                                this.out.writeUTF("User is not logged in.");
                                                this.out.flush();
                                            }
                                        } else if(arr[1].equals("-all")){
                                            String temp = "Message from Root: ";
                                            if(this.root){
                                                
                                                for(int i = 2; i < arr.length; i++){
                                                    temp += arr[i] + " ";
                                                }
                                                c2.out.writeUTF(temp);
                                                
                                                c2.out.flush();
                                                this.out.writeUTF("Message sent.");
                                            }else {
                                                this.out.writeUTF("Error: Root must be logged in.");
                                                this.out.flush();
                                            }
                                        } else {
                                            this.out.writeUTF("User does not exist.");
                                            this.out.flush();
                                        }
                                    } else {
                                        if(arr[1].equals("john")){
                                            String temp ="Message from John: ";
                                            if(c1.john){
                                                
                                                for(int i = 2; i < arr.length; i++){
                                                    temp += arr[i] + " ";
                                                }
                                                c1.out.writeUTF(temp);
                                                c1.out.flush();
                                                this.out.writeUTF("Message sent.");
                                            } else {
                                                this.out.writeUTF("User is not logged in.");
                                                this.out.flush();
                                            }
                                        } else if(arr[1].equals("sally")){
                                            String temp ="Message from Sally: ";
                                            if(c1.sally){
                                                
                                                for(int i = 2; i < arr.length; i++){
                                                    temp += arr[i] + " ";
                                                }
                                                c1.out.writeUTF(temp);
                                                c1.out.flush();
                                                this.out.writeUTF("Message sent.");
                                            } else {
                                                this.out.writeUTF("User is not logged in.");
                                                this.out.flush();
                                            }
                                        } else if(arr[1].equals("qiang")){
                                            String temp ="Message from Qiang: ";
                                            if(c1.qiang){
                                                
                                                for(int i = 2; i < arr.length; i++){
                                                    temp += arr[i] + " ";
                                                }
                                                c1.out.writeUTF(temp);
                                                c1.out.flush();
                                                this.out.writeUTF("Message sent.");
                                            } else {
                                                this.out.writeUTF("User is not logged in.");
                                                this.out.flush();
                                            }
                                        } else if(arr[1].equals("root")){
                                            String temp ="Message from Root: ";
                                            if(c1.root){
                                                
                                                for(int i = 2; i < arr.length; i++){
                                                    temp += arr[i] + " ";
                                                }
                                                c1.out.writeUTF(temp);
                                                c1.out.flush();
                                                this.out.writeUTF("Message sent.");
                                            } else {
                                                this.out.writeUTF("User is not logged in.");
                                                this.out.flush();
                                            }
                                        } else if(arr[1].equals("-all")){
                                            String temp ="Message from Root: ";
                                            if(this.root){
                                                
                                                for(int i = 2; i < arr.length; i++){
                                                    temp += arr[i] + " ";
                                                }
                                                c1.out.writeUTF(temp);
                                                c1.out.flush();
                                                this.out.writeUTF("Message sent.");
                                            } else {
                                                this.out.writeUTF("Error: Root must be logged in.");
                                            }
                                        } else {
                                            this.out.writeUTF("User does not exist.");
                                            this.out.flush();
                                        }
                                    }
                                } else {
                                    this.out.writeUTF("Error: No other clients are open.");
                                }
                            }
                            else if(arr[0].equals("LIST")){         //BEGIN list
                                if(arr.length == 1){
                                    if(root){

                                        try {       //For regular list operation of the correct user
                                            String data = "root ";
                                            Scanner reader = new Scanner(rootSol);
                                            while(reader.hasNextLine()){
                                               data += reader.nextLine() + " ";

                                            }
                                            out.writeUTF(data);
                                            reader.close();
                                            out.flush();
                                        } catch (FileNotFoundException e){
                                            e.printStackTrace();
                                        }

                                    } else if(john){
                                        try {
                                            String data = "john ";
                                            Scanner reader = new Scanner(johnSol);
                                            while(reader.hasNextLine()){
                                               data += reader.nextLine() + " ";

                                            }
                                            out.writeUTF(data);
                                            reader.close();
                                            out.flush();
                                        } catch (FileNotFoundException e){
                                            e.printStackTrace();
                                        }
                                    } else if(sally){
                                        try {
                                            String data = "sally ";
                                            Scanner reader = new Scanner(sallySol);
                                            while(reader.hasNextLine()){
                                               data += reader.nextLine() + " ";

                                            }
                                            out.writeUTF(data);
                                            reader.close();
                                            out.flush();
                                        } catch (FileNotFoundException e){
                                            e.printStackTrace();
                                        } 
                                    } else {
                                        try {
                                            String data = "qiang ";
                                            Scanner reader = new Scanner(qiangSol);
                                            while(reader.hasNextLine()){
                                               data += reader.nextLine() + " ";

                                            }
                                            out.writeUTF(data);
                                            reader.close();
                                            out.flush();
                                        } catch (FileNotFoundException e){
                                            e.printStackTrace();
                                        }
                                    }
                                
                                } 
                                else {
                                    if(root){       //BEGIN list -all
                                        try {       //For root function listing all files
                                            String data = "root ";
                                            Scanner reader = new Scanner(rootSol);
                                            while(reader.hasNextLine()){
                                               data += reader.nextLine() + " ";
                                            }
                                            data += "john ";
                                            if(johnOpen){
                                                reader = new Scanner(johnSol);
                                                while(reader.hasNextLine()){
                                                   data += reader.nextLine() + " ";
                                                }
                                            } else { data += "No interactions yet done "; }
                                            data += "sally ";
                                            if(sallyOpen){
                                                reader = new Scanner(sallySol);
                                                while(reader.hasNextLine()){
                                                   data += reader.nextLine() + " ";
                                                }
                                            } else { data += "No interactions yet done "; }
                                            data += "qiang ";
                                            if(qiangOpen){
                                                reader = new Scanner(qiangSol);
                                                while(reader.hasNextLine()){
                                                   data += reader.nextLine() + " ";
                                                }
                                            } else { data += "No interactions yet done "; }
                                            out.writeUTF(data);
                                            reader.close();
                                            out.flush();
                                        } catch (FileNotFoundException e){
                                            e.printStackTrace();
                                        }
                                    } //END list
                                }
                            } else if(arr[0].equals("SHUTDOWN")){   //BEGIN shutdown
                                out.writeUTF("200 OK");
                                this.socket.close();
                                return;     //END shutdown
                                
                            } else if(arr[0].equals("LOGOUT")){ //BEGIN logout
                                out.writeUTF("200 OK");
                                root = false;
                                john = false;
                                sally = false;
                                qiang = false;
                            }         // END logout
                            
                            else {
                                out.writeUTF("300 Invalid Command");
                            }
                        }
                
           


            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
        
    }
  }
  
  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
