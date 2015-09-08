package elacier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Restaurant_Receiver extends Thread{
	private Socket s;
	private Restaurant shop;
	public Restaurant_Receiver(String name,Socket s, Restaurant shop){
        super(name);
        this.s = s;
        this.shop = shop;
    }
    
    public void run(){
       String line = null; 
       int start = 0;
       long token = -1;
       try  
       { 
    	   PrintWriter wtr = new PrintWriter(s.getOutputStream());  
    	   BufferedReader rdr = new BufferedReader(new InputStreamReader(s.getInputStream()));  
    	   line = rdr.readLine();
           while(true){
        	   //wtr.println("你好，服务器已经收到您的信息！'" + line + "'\n");  
        	   //wtr.flush();  
        	   start = line.indexOf(":");
        	   if(start <1 ){   /*error received, but treat it as heart beat*/	
        		   
        	   }else{
        		  token =Long.parseLong(line.substring(0,start-1));
        		  String result = line.substring(start+1);
        		  if(token<1000){
        			/* heart beat*/
        			  
        			  
        		  }else{
        			  if(result.equalsIgnoreCase("OK")){
        				  restaurant_query_reponse(token); 
        			  }else{
        				/*restaurant is not available*/  
        			  }
        		  }
        	   }
        	   line = rdr.readLine();  
           }
      }catch (IOException e) {   
          e.printStackTrace();  
          /*release socekt db*/
      }
       
    }
    
    void beat_heart(PrintWriter wr){
    	wr.println("beat heart" + "'\n"); 
    	wr.flush();
    }
    
    void restaurant_query_reponse(long token_id){
    	Transaction_Info trans_info = ElacierService.getInstance().getCommuMsg(token_id);
    	ElacierService.getInstance().removeSpecifidCommuMsg(token_id, trans_info);
    	
    	if(trans_info == null){
    		System.out.println(shop.getName()+"do not get this transaction");
    	}else{
    		synchronized(trans_info.lock){
    			if(trans_info.bingo!=null){
    				System.out.println(shop.getName()+"do not get this transaction");
    				return;
    			}else{
    				trans_info.bingo = shop;
    			}
    		}
    		
    		trans_info.trans_id = System.currentTimeMillis() +"-"+ ElacierService.PC_NUM;
    		String url = ElacierService.LUNCH_SERVELT_URL + trans_info.trans_id;
    		ElacierService.getInstance().putFormMsg(trans_info.trans_id, trans_info);
    		/*send back url to user*/
    		
    		
    	}
    	
    }
    
    
}
