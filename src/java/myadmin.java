
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

public class myadmin extends HttpServlet {
    
    HttpSession session;
    static Connection con = null;
    static Statement st = null;
    static PreparedStatement stmt = null;
    static String url = "jdbc:mysql://localhost:3306/mydb";
    static String user = "root";
    static String password = "root";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String name = request.getParameter("user");
        String pass = request.getParameter("password");
        PrintWriter out = response.getWriter();
        out.println("<title>Error!</title>");
            try{
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(url, user, password);
                System.out.println("Connection successful ! ! !");
                String sql = "SELECT admin_password FROM admin_info WHERE admin in (?)";
                try{
                    stmt = con.prepareStatement(sql);
                    stmt.setString(1, name);
                    ResultSet rs = stmt.executeQuery();
                    while(rs.next()){
                        if(rs.getString("admin_password").equals(pass)){
                            session = request.getSession(true);
                            session.setAttribute("username", name);
                            session.setAttribute("password", pass);
                            //session.setMaxInactiveInterval(10);
                            RequestDispatcher rd = request.getRequestDispatcher("admin_access");
                            rd.forward(request, response);
                        }else{
                            out.println("<div style='background-color: gray;'><h3 style='font-family: verdana; padding : 6px;'>" + "Information is not correct !" + "</h3></div>");
                            out.println("<a href='admin.html'><b style='color : green; font-size: 18px'>" + "Click here to go back" + "</b></a>");
                            out.println("<br><br><hr>");
                        }
                    }
                }catch(Exception e){
                    System.out.println(e);
                }
            }catch(Exception e){
                out.println(e);
            }
        
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
