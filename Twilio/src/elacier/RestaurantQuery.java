package elacier;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class RestaurantQuery implements IRestaurantCommunication{
	static long token_id = 1000;
	Object lock;
	
	public RestaurantQuery(){
		lock = new Object();
	}
	
	public Socket findConnectSocket(Restaurant shop){
		return new Socket();
	} 
	
	public boolean sendQueryInfo(Socket s,long token,Transaction_Info trans_info){
		OutputStream  os;
		String cmd = token + ":" + "is restaurant available for "+ trans_info.persons + "people?";
		
		try{
			os=s.getOutputStream();
			os.write(cmd.getBytes());
			os.close();
	    }catch(IOException e){
	    	return false;
	    }
		
		return true;
	}
	
	public int queryRestaurantAvailable(List<Restaurant> restaurants,Transaction_Info trans_info ){
		long token;
		Restaurant shop;
		synchronized(this.lock){
			token = token_id++;
		}
		
		if(restaurants!=null && restaurants.size()>0){
			boolean flag_send_suc = false;
			for(int i=0;i<restaurants.size();i++){
				shop = restaurants.get(i);
				/*find socket connect to the shop ios*/
				Socket s = findConnectSocket(shop);
				if(s!=null){
					if(sendQueryInfo(s,token,trans_info)){
						flag_send_suc = true;
						ElacierService.getInstance().putCommuMsg(token_id, trans_info);
					}
				}
			}
			
			if(flag_send_suc)
				return RESTAURANT_COMM_OK;
			else 
				return RESTAURANT_COMM_NOK;
			
		}else{
			return RESTAURANT_COMM_NOK;
		}
		
	}
}
