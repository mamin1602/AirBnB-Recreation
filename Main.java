package application;
	
import java.io.FileNotFoundException;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.*;
import java.io.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;




public class Main extends Application 
{	// Universal for Clock
	TextArea clock;
	
	public void start(Stage stage) throws FileNotFoundException 
	{
		stage.setTitle("Airbnb.com");
		stage.setWidth(1200);
		stage.setHeight(1000);
		
        clock = new TextArea();
        clock.setEditable(false);
        clock.setPrefHeight(30);   
        clock.setPrefWidth(90);
        // Auto refresh
	    refreshClock();
	    
	    Button home = new Button("Home");
	    home.setFont(new Font("Calibri", 12));
        home.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override public void handle(ActionEvent e)
            { 	
            	System.exit(0);
            }
        });

	    Button chat = new Button("Chat");
	    chat.setFont(new Font("Calibri", 12));
        chat.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override public void handle(ActionEvent e)
            { 	
            	Platform.runLater(new Runnable()
            	{		
            		public void run()
            		{
            			Alert alert = new Alert(Alert.AlertType.INFORMATION);
            			alert.setTitle("CHAT");
            			alert.setHeaderText("SEND A MESSAGE TO CURRENT BOOKING HOST:");
            			alert.setHeight(400);
            			alert.setWidth(900);
            			alert.setResizable(true);
            			
            			String a = "Lexie A. : \r \n" +
            			
            						"John D. :  \r \n";
   
 
            			
            			alert.setContentText(a);
            			alert.showAndWait();
            		}
            	});
            }
        });
	    	    
	    Button fav = new Button("Favorites");
	    fav.setFont(new Font("Calibri", 12));
        fav.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override public void handle(ActionEvent e)
            { 	
            	Platform.runLater(new Runnable()
            	{		
            		public void run()
            		{
            			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            			alert.setTitle("FAVORITES");
            			alert.setHeaderText("SAVED BOOKINGS");
            			alert.setHeight(400);
            			alert.setWidth(900);
            			alert.setResizable(true);
            			
            			String a = "420 LEXINGTON AVE: CONDO \r" +
            						"MANHATTAN, NY \r" +
            					   "3 BEDROOMS, 2 BATHROOMS\r \n" +
            						
            						"793 LOWLAND DRIVE: MANSION\r" +
            						"BEVERLY HILLS, CA \r" +
         					   		"8 BEDROOMS, 11 BATHROOMS\r \n";
 
            			
            			alert.setContentText(a);
            			alert.showAndWait();
            		}
            	});
            }
        });
	    
	    Button help = new Button("Help");
	    help.setFont(new Font("Calibri", 12));
        help.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override public void handle(ActionEvent e)
            { 	
            	Platform.runLater(new Runnable()
            	{		
            		public void run()
            		{
            			Alert alert = new Alert(Alert.AlertType.INFORMATION);
            			alert.setTitle("AIRBNB HELP WINDOW");
            			alert.setHeaderText("Help Screen");
            			alert.setHeight(400);
            			alert.setWidth(900);
            			alert.setResizable(true);
            			
            			String h = "ENTER A CITY \r \n" +
            					   "ENTER THE AMOUNT OF GUESTS \r \n" +
            					    "BOOK A BNB! \r \n" +
            					   "IF THIS IS AN EMERGENCY, HANG UP AND DIAL 911 ! \r \n";
            			
            			alert.setContentText(h);
            			alert.showAndWait();
            		}
            	});
            	
            }
        });
        
	    Button nyc = new Button("NEW YORK CITY");
	    chat.setFont(new Font("Calibri", 20));
        chat.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override public void handle(ActionEvent e)
            { 	
            	Platform.runLater(new Runnable()
            	{		
            		public void run()
            		{
            			Alert alert = new Alert(Alert.AlertType.INFORMATION);
            			alert.setTitle("NEW YORK CITY");
            			alert.setHeaderText("AVAILABLE: ");
            			alert.setHeight(400);
            			alert.setWidth(900);
            			alert.setResizable(true);
            			
            			alert.showAndWait();
            		}
            	});
            }
        });
        
        Button submitButton    = new Button("SUBMIT");     
        submitButton.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override public void handle(ActionEvent e)
            {
            	Platform.runLater(new Runnable() 
				 {
				        public void run() 
				        {
				        	String rs=null;
				            socketUtils su = new socketUtils();
				            
				            if (su.socketConnect() == true) //this always seems to be false for whatever reason
				            {
				            	String strDouble = String.format("%.2f");
				            	String msg = "Transaction>kiosk#001" + "," + strDouble;
            	                su.sendMessage(msg);				            	
            	                String ackOrNack = su.recvMessage();
            	                
            	                
            	                msg = "quit";
            	                su.sendMessage(msg);
            	                rs = su.recvMessage();
            	                
            	                
            	                //
            	                // close the socket connection
            	                //
            	                su.closeSocket();
            	                
            	                // 
            	                // write to transaction log
            	                //
            	                msg = "CLIENT : Transaction>kiosk#001" + "," + "," + strDouble;
            	                fileIO trans = new fileIO();
            	                trans.wrTransactionData(msg);
            	                
      
            	                
            	                
            	                TextInputControl ta2 = null;
								if (ackOrNack.startsWith("ACK") == true)
            	                {
            	                	ta2.setText("Success!    Message was received and processed by the Socket Server!");
            	                }
            	                else
            	                {
            	                   ta2.setText("RECV : " + ackOrNack);
            	                   ta2.appendText(rs);
            	                }
				            }
				            else
				            {
				            	// 
            	                // write to transaction log
            	                //
				            	String strDouble = String.format("%.2f");
            	                String msg = "CLIENT NETWORK ERROR : Transaction>kiosk#001" + "," + strDouble;
            	                
            	                fileIO trans = new fileIO();
            	                trans.wrTransactionData(msg);
            	                
            	                
				            	Alert alert = new Alert(Alert.AlertType.ERROR);
						        alert.setTitle("--- Network Communications Error ---");
						        alert.setHeaderText("Unable to talk to Socket Server!");
						          
						        alert.showAndWait();
				            }
				        }
				    });	
            }
        });




	    
	    Button booked = new Button("Booked");
	    help.setFont(new Font("Calibri", 12));
	    help.setTextFill(Color.web("#A52A2A"));
	    TextField mybookings = new TextField();
	    
	    Label city = new Label("What City? ");
	    city.setFont(new Font("Calibri", 18));
	    city.setTextFill(Color.web("#A52A2A"));
	    TextField cityA = new TextField();
	    
	    Label guests = new Label("How many guests? ");
	    guests.setFont(new Font("Calibri", 18));
	    guests.setTextFill(Color.web("#A52A2A"));
	    TextField guestsA = new TextField();
	    
	    Image BNB = new Image("https://iconiclife.com/wp-content/uploads/2019/11/modern-farmhouse-design-front-elevation.jpg");
	    javafx.scene.layout.BackgroundImage house = new BackgroundImage(BNB, null, null, null, null);
	    
	    Image logo = new Image("https://cdn.freebiesupply.com/logos/large/2x/airbnb-2-logo-png-transparent.png");
	    ImageView BNBlogo = new ImageView(logo);
	    BNBlogo.setFitWidth(100);
	    BNBlogo.setFitHeight(100);
	    
		   GridPane gp = new GridPane();
		   gp.add(city, 0, 96, 1, 1);
		   gp.add(cityA, 1, 96, 3, 1);
		   
		   gp.add(guests, 0, 97, 1, 1);
		   gp.add(guestsA, 1, 97, 3, 1);
		   
		   
		   gp.add(home, 1, 0, 1, 1);
		   
		   gp.add(chat, 2, 0, 1, 1);

		   gp.add(submitButton, 3, 0, 1, 1);

		   gp.add(help, 4, 0, 1, 1);

		   
		 
		   
	    
	  VBox vb = new VBox();
	  vb.getChildren().addAll(BNBlogo);	    
	  
	  
	  BorderPane pane = new BorderPane();
	  
	    pane.setTop(clock);
	    pane.setBackground(new Background(house));
	    pane.setRight(BNBlogo);
	    pane.setBottom(gp);

	    //pane.setLeft(booked);
	       	     
	    // Sets the App layout
	    Scene scene = new Scene(pane);
	    stage.setScene(scene);
	    stage.show();
	    

        
	
	}
	// Clock - thread code
    private void refreshClock()
    {
    	Thread refreshClock = new Thread()
		   {  
			  public void run()
			  {	 
				while (true)
				{
					Date dte = new Date();
		
					String topMenuStr = "       " + dte.toString();		
				  
					clock.setText(topMenuStr); 
			               
				    try
				    {
					   sleep(3000L);
				    }
				    catch (InterruptedException e) 
				    {
					   // TODO Auto-generated catch block
					   e.printStackTrace();
				    }
				  
	            }  // end while ( true )
				 
		    } // end run thread
		 };

	     refreshClock.start();
    }
    
    public class fileIO 
    {
    		public void wrTransactionData(String dataStr)
    		{
    	        FileWriter fwg = null;
    	        try 
    	        {
    	        	// open the file in append write mode
    	        	fwg = new FileWriter("transactionLog.txt", true);
    	        }
    	        catch (IOException e)
    	        {
    	        	// TODO Auto-generated catch block
    	        	e.printStackTrace();
    	        }
    	   	    
    	        BufferedWriter bwg = new BufferedWriter(fwg);
    	        PrintWriter outg   = new PrintWriter(bwg);
    			
    	        String timeStamp = new SimpleDateFormat("MM-dd-yyyy HH.mm.ss").format(new Date());
    	        
    	        outg.println(timeStamp + " : " + dataStr);
    	        
    	        outg.close();
    		}
    }


	
	public static void main(String [] args)
	{
		launch (args);
	}

}








