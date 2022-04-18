package Project4;

import java.io.*;
import java.net.*;
import javafx.application.Application;
import static javafx.application.Platform.exit;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.Vector;




public class Client extends Application{
    
    DataOutputStream toServer = null;
    DataInputStream fromServer = null;
  

    String output = "";
    String input = "";
    
    
TextArea txtArea = new TextArea();





  @Override // Override the start method in the Application class
  public void start(Stage stage) throws IOException {
    
    
    Button sendBtn = new Button();
    Button receiveBtn = new Button();
    TextField sendField = new TextField();
    BorderPane txtPane = new BorderPane();
    
    AnchorPane backG = new AnchorPane(txtArea, sendBtn, receiveBtn, sendField, txtPane);
    backG.setPrefSize(459, 545);
    backG.setStyle("-fx-background-color: grey");
    
    sendField.setLayoutX(22);
    sendField.setLayoutY(468);
    sendField.setPrefSize(345, 31);
    //sendField.setAlignment(Pos.BOTTOM_LEFT);
    sendBtn.setText("Send");
    Font myFont = new Font("System", 15);
    sendBtn.setFont(myFont);
    sendBtn.setLayoutX(383);
    sendBtn.setLayoutY(468);
    receiveBtn.setText("Recieve");
    receiveBtn.setFont(myFont);
    receiveBtn.setLayoutX(375);
    receiveBtn.setLayoutY(506);
    
    
    //sendBtn.setAlignment(Pos.BOTTOM_RIGHT);
    txtArea.setPrefSize(415, 429);
    txtArea.setEditable(false);
    txtPane.setCenter(new ScrollPane(txtArea));
    txtPane.setLayoutX(22);
    txtPane.setLayoutY(26);
    
    
    Scene scene = new Scene(backG);
    stage.setTitle("Client Messanger");
    stage.setScene(scene);
    stage.show();
    
   
    receiveBtn.setOnAction(e -> {
        
        String dsp;
        
        try{
            
            
            
            dsp = "";
            dsp += fromServer.readUTF() + "\n";
            
            input = "S: ";
            
            input += dsp;
            txtArea.appendText(input);
            
            
           } catch(IOException ex){
               System.err.println("No reply yet");
           }
        
    });
    
    sendBtn.setOnAction(e -> {
       
        String sendText;
        String display;
        
        try {
            
            sendText = sendField.getText();
            
            String arr[] = sendText.split(" ", -1);
            
            display = "C: ";
            display += sendText + "\n";
            output = display;
            txtArea.appendText(display);
            System.out.println(display);
            sendField.setText("");

            toServer.writeUTF(sendText);        //Sending to server
            toServer.flush();
            
            if(sendText.equals("LIST")){    //LIST client-side commands
                
                String tmp = fromServer.readUTF();
                String data[] = tmp.split(" ", -1);
                txtArea.appendText("S: " + data[0] + "\n    ");
                System.out.println("S: " + data[0] + "\n    ");
                for(int i = 1; i < data.length; i++){
                    if(data[i].equals("done")){
                        txtArea.appendText("\n    ");
                        System.out.println("\n    ");
                    } else {
                        txtArea.appendText(" " + data[i]);
                        System.out.println(" " + data[i]);
                    }
                }
                        
            } else if(sendText.equals("LIST -all")) {   //LIST -all client-side commands
                String tmp = fromServer.readUTF();
                String data[] = tmp.split(" ", -1);
                txtArea.appendText("S: " + data[0] + "\n    ");
                System.out.println("S: " + data[0] + "\n    ");
                for(int i = 1; i < data.length; i++){
                    if(data[i].equals("john") || data[i].equals("sally") || data[i].equals("qiang")){
                        txtArea.appendText(data[i]+ "\n    ");
                        System.out.println(data[i] + "\n    ");
                    } else if(data[i].equals("done")){
                        if(data[i+1].equals("john") || data[i+1].equals("sally") || data[i+1].equals("qiang")){
                            txtArea.appendText("\n");
                            System.out.println("\n");
                        } else {
                            txtArea.appendText("\n      ");
                            System.out.println("\n      ");
                        }
                    }
                    else {
                        txtArea.appendText(" " + data[i]);
                        System.out.println(" " + data[i]);
                    }
                }
            } else if(sendText.equals("LOGOUT")){   //LOGOUT client-side commands
                display = "S: ";
                display += fromServer.readUTF();
                System.out.println(display);
                txtArea.appendText(display);
                fromServer.close();
                toServer.close();
                exit();
                
            } 
            else{       //Regular outputs
            
                display = "S: ";
                display += fromServer.readUTF() + "\n";
            
                System.out.println(display);
                txtArea.appendText(display);
            
            
          
            
            
       
        }
        }
        catch(IOException ex){
            System.err.println(ex);
        }
        
    });
    
   
    
    
    
    try {
     
      
      Socket socket = new Socket("localhost", 8000);
      
      fromServer = new DataInputStream(socket.getInputStream());

      
      toServer = new DataOutputStream(socket.getOutputStream());
      
      
     
         
      
    }
    
    catch (IOException ex) {
        txtArea.appendText(ex.toString() + "\n");
    }
    
    
   
  }
  
  
  
  
  

 
  public static void main(String[] args) {
    launch(args);
  }

    

}
