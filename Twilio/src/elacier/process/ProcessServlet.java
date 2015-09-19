package elacier.process;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import elacier.provider.JsonMessageTransformer;
import elacier.provider.JsonTransformImpl;
import elacier.provider.msg.BaseMessage;

public class ProcessServlet extends HttpServlet {
	
	
	private JsonMessageTransformer transform;
	
	private MessageHandler	messageHandler;

	@Override
	public void destroy() {
		super.destroy();
		transform = null;
		messageHandler = null;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		transform = new JsonTransformImpl();
		messageHandler = new MessageHandler();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		handle(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		handle(req, resp);
	}
	
	private void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
		String outContent = null;
		String json = req.getParameter("data");
		BaseMessage bm = parseJson(json);
		if (bm != null) {
			BaseMessage retrunMessage = messageHandler.handleMessage(bm);
			
			if (retrunMessage != null) {
				JSONObject jsonobject = transform.transform(retrunMessage);
				outContent = jsonobject.toString();
			} else {
				//ADD error Message
			}
			
		} else {
			// add error message
		}
		
		
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		out.print(outContent);
		out.flush();
	}
	
	
	private BaseMessage parseJson(String json) {
		return transform.transform(json);
	}
	

}
