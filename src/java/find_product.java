
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class find_product extends HttpServlet {

    HttpSession session;
    static Connection con = null;
    static Statement st = null;
    static PreparedStatement stmt = null;
    static String url = "jdbc:mysql://localhost:3306/mydb";
    static String user = "root";
    static String password = "root";
    List<String> list1 = new ArrayList<>();
    List<String> list2 = new ArrayList<>();
    List<String> list3 = new ArrayList<>();
    List<String> list4 = new ArrayList<>();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        session = request.getSession(false);
        String User = null;
        String pass = null;
        User = (String)session.getAttribute("username");
        pass = (String)session.getAttribute("password");
        if(User == null && pass == null){
            //session.invalidate();
            out.println("<div style='background-color: gray;'><h3 style='font-family: verdana; padding : 6px;'>" + "Your session has been expired !" + "</h3></div>");
            out.println("<a href='index.html'><b style='color : green; font-size: 18px'>" + "Click here to log in again" + "</b></a>");
            out.println("<br><br><hr>");
        }else{
            String name = request.getParameter("pname");
            try{
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(url, user, password);
                System.out.println("Connection successful ! ! !");
                String sql = "SELECT name, price, discount, quantity FROM product_info";
                try{
                    st = con.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    list1.clear();
                    list2.clear();
                    list3.clear();
                    list4.clear();
                    while(rs.next()){
                        String search = rs.getString("name"); 
                        if(search.contains(name)){
                            list1.add(rs.getString("name"));
                            list2.add(Float.toString(rs.getFloat("price")));
                            list3.add(Float.toString(rs.getFloat("discount")));
                            list4.add(Integer.toString(rs.getInt("quantity")));
                        }
                    }
                    session.setAttribute("arr1", list1);
                    session.setAttribute("arr2", list2);
                    session.setAttribute("arr3", list3);
                    session.setAttribute("arr4", list4);
                    RequestDispatcher rd = request.getRequestDispatcher("user_access");
                    rd.forward(request, response);
                }catch(Exception e){
                    System.out.println(e);
                }
                con.close();
            }catch(Exception e){
                out.println(e);
            }
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
