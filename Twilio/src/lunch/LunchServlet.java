package lunch;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LunchServlet
 */
@WebServlet("/LunchServlet")
public class LunchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletContext mContext; 
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LunchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(isOrderRequest(request, response))
		{
			String trans = getTransactionIdFromURL(request);
			if(trans!=null){
				if(isTransValid(request,trans)){
					System.out.println("lunch trans is valid");
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
					out.println("<HTML>");
					out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
					out.println("  <BODY>");
					out.println("  Transaction ID is valid:"+trans);
					//out.println("<a href=\"test.html\">"+menu.menu_id+"</a>");
					out.println("  </BODY>");
					out.println("</HTML>");
					out.flush();
					out.close();
				}else{
					sendErrorBack(response,HttpServletResponse.SC_NOT_FOUND);
					return;
				}
			}else{
				sendErrorBack(response,HttpServletResponse.SC_FORBIDDEN);
			}
		}else{
			sendErrorBack(response,HttpServletResponse.SC_FORBIDDEN);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		mContext = config.getServletContext();
		System.out.println("Lunch Server started");
		//System.out.println("Observation ServletConfig:");
		//System.out.println(config);
		//System.out.println("Observation ServletInfo:");
		//System.out.println(getServletInfo());
	}
	
	private boolean isOrderRequest(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("lunch"+request.getQueryString());
		if(request.getQueryString()!=null){
			return true;
		}else{
			return false;
		}
	}
	
	private String getTransactionIdFromURL(HttpServletRequest request)
	{
		String query = request.getQueryString();
		int start;
		start = query.indexOf("id=") + 3;
		String tran_id = query.substring(start);
		
		System.out.println(query);
		System.out.println(tran_id);
		return tran_id;
	}
	
	private boolean isTransValid(HttpServletRequest request,String tran_id)
	{
		if(mContext.getAttribute(tran_id)==null)
		{
			return false;
		}else{
			return true;
		}
		
	}
	
	private void sendErrorBack(HttpServletResponse response,int errCode) throws java.io.IOException
	{
		response.sendError(errCode);
		PrintWriter out = response.getWriter();
		out.flush();
		out.close();
	}

}
