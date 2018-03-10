import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class VncRedirect extends HttpServlet {
public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
String target = "http://piccadilly:580";
target = target + req.getParameter("port");
res.sendRedirect(target);
}
}
