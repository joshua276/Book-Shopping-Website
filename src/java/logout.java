
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class logout extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session;
        session = request.getSession(false);
        session.removeAttribute("username");
        session.removeAttribute("password");
        session.removeAttribute("arr1");
        session.removeAttribute("arr2");
        session.removeAttribute("arr3");
        session.removeAttribute("arr4");
        session.setAttribute("logout", "yes");
        //out.println("<div style='background-color: gray;'><h3 style='font-family: verdana; padding : 6px;'>" + "Your session has been expired !" + "</h3></div>");
        //out.println("<a href='index.html'><b style='color : green; font-size: 18px'>" + "Click here to log in again" + "</b></a>");
        //out.println("<br><br><hr>");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("max-age", 0);
        response.setDateHeader("Expires", 0);
        response.sendRedirect("index.html");
        //RequestDispatcher rd = request.getRequestDispatcher("index.html");
        //rd.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
