
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class cod extends HttpServlet {

    static Connection con = null;
    static Statement st = null;
    static PreparedStatement stmt = null;
    static PreparedStatement stmt1 = null;
    String url = "jdbc:mysql://localhost:3306/mydb";
    String user = "root";
    String password = "root";
    HttpSession session;
    List<String> p = null;
    List<String> q = null;
    List<String> c = null;
    List<String> s = null;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<title>Final Page</title>");
        out.println("<div style='background-color: gray;'><h3 style='font-family: verdana; padding : 6px;'>" + "Your order(s) will be delivered to your shipping adddres." + "</div>");
        String sql = "SELECT bill FROM bill_info";
        String sql1;
        int bill_no = 0;
        session = request.getSession(false);
        Object obj1 = session.getAttribute("product");
        Object obj2 = session.getAttribute("quantity");
        Object obj3 = session.getAttribute("price");
        Object obj4 = session.getAttribute("instock");
        if(obj1 != null && obj2 != null && obj3 != null){
            p = (List<String>)obj1;
            q = (List<String>)obj2;
            c = (List<String>)obj3;
            s = (List<String>)obj4;
        }
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Connection successful ! ! !");
            try{
                st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                while(rs.next()){
                    bill_no = rs.getInt("bill");
                }
                if(bill_no == 0){
                    bill_no = 10000;
                }else{
                    bill_no = bill_no + 1;
                }
                out.println("<div style='background-color: #77ee77; text-align: center;'><h3 style='background-color: #77ee77; color: green;'>Bill No : " + bill_no + "</h3></div>");
                out.println("<table style='font-family: arial, sans-serif; border-collapse: collapse; width: 30%;'><tr>");
                out.println("<th style='border: 1px solid #dddddd; text-align: left; padding: 8px'>Order Name</th>" + "<th style='border: 1px solid #dddddd; text-align: left; padding: 8px'>Cost</th>");
                out.println("</tr>");
                for (int i = 0; i < p.size(); i++) {
                    sql = "INSERT INTO bill_info (user, product, quantity, amount, bill) VALUES (?, ?, ?, ?, ?)";
                    
                    try{
                        stmt = con.prepareStatement(sql);
                        stmt.setString(1, (String)session.getAttribute("username"));
                        stmt.setString(2, p.get(i));
                        stmt.setInt(3, Integer.parseInt(q.get(i)));
                        stmt.setFloat(4, Float.parseFloat(c.get(i)));
                        stmt.setInt(5, bill_no);
                        stmt.execute();
                        
                    }catch(Exception e){
                        System.out.println(e);
                    }
                    sql1 = "UPDATE product_info SET quantity = ? WHERE name = ?";
                    try{
                        stmt1 = con.prepareStatement(sql1);
                        stmt1.setInt(1, Integer.parseInt(s.get(i)) - Integer.parseInt(q.get(i)));
                        stmt1.setString(2, p.get(i));
                        stmt1.executeUpdate();
                    }catch(Exception e){
                        out.println(e);
                    }
                    if(i%2 == 0){
                            out.println("<tr style='background-color: #dddddd;'>");
                    }else{
                        out.println("<tr>");
                    }
                    out.println("<td style='border: 1px solid #dddddd; text-align: left; padding: 8px'>" + p.get(i) + "</td>" + "<td style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>"
                            + c.get(i) + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
                con.close();
                session.setAttribute("payment", 1);
                session.removeAttribute("arr1");
                session.removeAttribute("arr2");
                session.removeAttribute("arr3");
                session.removeAttribute("arr4");
                out.println("<div style='background-color: #77ee77; text-align: center;'><h3 style='background-color: #77ee77; color: green;'>" + "Your order(s) will be delivered soon" + "</h3></div>");
                out.println("<button style='padding: 4px; padding-left: 8px; padding-right: 8px; background-color: whitesmoke'><a href='user_access?done=" + "yes" + "'" + "style='text-decoration: none; color: black'><b>Go Back To Home Page</b></a></button>");
            }catch(Exception e){
                    
            }
        }catch(Exception e){
            System.out.println(e);
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
