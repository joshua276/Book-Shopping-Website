
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class pay extends HttpServlet {
    
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
        PrintWriter out = response.getWriter();
        out.println("<title>Payment preperation</title>");
        HttpSession session;
        float tp = (float) 0.0;
        float price = (float) 0.0;
        float dis = (float)0.0;
        int quan = 0;
        int t_q = 0;
        float c = (float)0.0;
        List<String> pname = null;
        List<String> quant = new ArrayList<>();
        List<String> cost = new ArrayList<>();
        List<String> stock = new ArrayList<>();
        session = request.getSession(false);
        out.println("<div style='background-color: gray;'><h3 style='font-family: verdana; padding : 6px;'>" + "Payment page" + "</div>");
        Object obj1 = null;
        obj1 = session.getAttribute("product");
        if(obj1 != null){
            pname = (List<String>)obj1;
            out.println("<table style='font-family: arial, sans-serif; border-collapse: collapse; width: 30%;'><tr>");
            out.println("<th style='border: 1px solid #dddddd; text-align: left; padding: 8px'>Product Name</th>" + "<th style='border: 1px solid #dddddd; text-align: left; padding: 8px'>Quantity</th>");
            out.println("</tr>");
            for (int i = 0; i < pname.size(); i++) {
                try{
                    if(Integer.parseInt(request.getParameter(pname.get(i))) != 0){
                        if(i%2 == 0){
                                out.println("<tr style='background-color: #dddddd;'>");
                        }else{
                            out.println("<tr>");
                        }

                        try{
                            Class.forName("com.mysql.jdbc.Driver");
                            con = DriverManager.getConnection(url, user, password);
                            System.out.println("Connection successful ! ! !");
                            String sql = "SELECT price, discount, quantity FROM product_info WHERE name in (?)";
                            try{
                                try{
                                    stmt = con.prepareStatement(sql);
                                    stmt.setString(1, pname.get(i));
                                    ResultSet rs = stmt.executeQuery();
                                    if(rs.next()){
                                        price = rs.getFloat("price");
                                        dis = rs.getFloat("discount");
                                        t_q = rs.getInt("quantity");
                                    }
                                    quan = Integer.parseInt(request.getParameter(pname.get(i)));
                                    if(quan > t_q){
                                        quan = t_q;
                                    }
                                    out.println("<td style='border: 1px solid #dddddd; text-align: left; padding: 8px'>" + pname.get(i) + "</td>" + "<td style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>"
                                            + quan + "</td>");
                                    out.println("</tr>");
                                    stock.add(Integer.toString(t_q));
                                }catch(Exception e){
                                    System.out.println(e);
                                }
                            }catch(Exception e){
                                System.out.println(e);
                            }
                            con.close();
                        }catch(Exception e){
                        }
                        c = (float)((quan*price*(100-dis))/100.0);
                        cost.add(Float.toString(c));
                        tp = tp + c;
                    }
                    quant.add(Integer.toString(quan));
                    session.setAttribute("quantity", quant);
                    session.setAttribute("price", cost);
                    session.setAttribute("instock", stock);
                }catch(Exception e){
                    if(i%2 == 0){
                            out.println("<tr style='background-color: #dddddd;'>");
                    }else{
                        out.println("<tr>");
                    }
                    List<String> temp1 = null;
                    List<String> temp2 = null;
                    Object Obj1 = session.getAttribute("quantity");
                    Object Obj2 = session.getAttribute("price");
                    temp1 = (List<String>)Obj1;
                    temp2 = (List<String>)Obj2;
                    out.println("<td style='border: 1px solid #dddddd; text-align: left; padding: 8px'>" + pname.get(i) + "</td>" + "<td style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>"
                            + temp1.get(i) + "</td>");
                    out.println("</tr>");
                    tp = tp + Float.parseFloat(temp2.get(i));
                }
                
            }
            out.println("<tr style='background-color: #aaeeaa;'>");
            out.println("<td style='border: 1px solid #dddddd; text-align: left; padding: 8px; color: #116611'>" + "<b>Total Price</b>" + "</td>" + "<td style='border: 1px solid #dddddd; text-align: left; padding: 8px; color: green;'><b>"
                            + tp + "</b></td>");
            out.println("</tr>");
            out.println("</table><br>");
            out.println("<div style='background-color: gray;'><h3 style='font-family: verdana; padding : 6px;'>" + "Payment options" + "</div>");
            out.println("<button style='padding: 4px; padding-left: 8px; padding-right: 8px; background-color: eeffee;'><a href='cod?amount=" + tp + "'" + "style='text-decoration: none; color: black;'><b>Cash on delivery</b></a></button>&nbsp;");
            out.println("<button style='padding: 4px; padding-left: 8px; padding-right: 8px; background-color: eeeeff;'><a href='paytm?amount=" + tp + "'" + "style='text-decoration: none; color : black;'><b>PAYTM</b></a></button>&nbsp;");
            out.println("<button style='padding: 4px; padding-left: 8px; padding-right: 8px; background-color: ffeeee;'><a href='debitcard?amount=" + tp + "'" + "style='text-decoration: none; color : black;'><b>Debit Card</b></a></button>");
            out.println("<button style='padding: 4px; padding-left: 8px; padding-right: 8px; background-color: acbace;'><a href='cart'" + "style='text-decoration: none; color : black;'><b>Go Back</b></a></button>");
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
