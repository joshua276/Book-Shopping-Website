
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class add_product extends HttpServlet {
    
    static Connection con = null;
    static Statement st = null;
    static PreparedStatement stmt = null;
    static String url = "jdbc:mysql://localhost:3306/mydb";
    static String user = "root";
    static String password = "root";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String pname = request.getParameter("prod");
        float amount = Float.parseFloat(request.getParameter("price"));
        float discount = Float.parseFloat(request.getParameter("discount"));
        try{
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(url, user, password);
                System.out.println("Connection successful ! ! !");
                String sql = "SELECT name FROM product_info WHERE name in (?)";
                try{
                    stmt = con.prepareStatement(sql);
                    stmt.setString(1, pname);
                    ResultSet rs = stmt.executeQuery();
                    if(rs.next()){
                        sql = "UPDATE product_info SET price = ?, discount = ? WHERE name in (?)";
                        try{
                            stmt = con.prepareStatement(sql);
                            stmt.setFloat(1, amount);
                            stmt.setFloat(2, discount);
                            stmt.execute();
                        }catch(Exception e){
                            System.out.println(e);
                        }
                    }else{
                        sql = "INSERT INTO product_info (name, price, discount) VALUES (?, ?, ?)";
                        try{
                            stmt = con.prepareStatement(sql);
                            stmt.setString(1, pname);
                            stmt.setFloat(2, amount);
                            stmt.setFloat(3, discount);
                            stmt.execute();
                        }catch(Exception e){
                            System.out.println(e);
                        }
                    }
                }catch(Exception e){
                    System.out.println(e);
                }
            }catch(Exception e){
                out.println(e);
            }
        RequestDispatcher rd = request.getRequestDispatcher("admin_access");
        rd.forward(request, response);
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
