package twilio;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MenuHtml {

	public long menu_id=0;
	public MenuHtml(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated constructor stub
		menu_id = System.currentTimeMillis();
		
	}

}
