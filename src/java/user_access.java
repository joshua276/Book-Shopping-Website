
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class user_access extends HttpServlet {

    HttpSession session;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        session = request.getSession(false);
        PrintWriter out = response.getWriter();
        String user = null;
        String pass = null;
        user = (String)session.getAttribute("username");
        pass = (String)session.getAttribute("password");
        out.println("<title>Home</title>");
        if(user == null && pass == null){
            //session.invalidate();
            out.println("<div style='background-color: gray;'><h3 style='font-family: verdana; padding : 6px;'>" + "Your session has been expired !" + "</h3></div>");
            out.println("<a href='index.html'><b style='color : green; font-size: 18px'>" + "Click here to log in again" + "</b></a>");
            out.println("<br><br><hr>");
        }
        else{
            
            out.println("<div style='background-color: gray;'><h3 style='font-family: verdana; padding : 6px;'>" + "Welcome, " + user + "</div>");
            out.println("<a href='user_access' style='color: white; background-color: #ddaa22; text-decoration: none; padding: 4px;'>" + "Our Products" + "</a>" + " | " +
                    "<a href='cart' style='background-color: wheat; color: white; text-decoration: none; padding:4px;'>" + "Your cart" + "</a>" + " | " + 
                    "<a href='logout?lout=1' style='background-color: wheat; color: white; text-decoration: none; padding:4px;'>" + "Log Out" + "</a>" + "<br>" + "<div style=\"height: 7px;\"></div>" + "<hr>");
            out.println("<form action='find_product'>" + "<div style=\"height: 7px;\"></div>");
            out.println("<input type='text' name='pname' />" + "&nbsp;<button type='submit' name='fbutton' style='color:maroon; background-color:whitesmoke'>" + "<b>Search Product</b>" + "</button>" + "</form>" + "<hr>");
            if(request.getParameter("done") != null && request.getParameter("done").equals("yes")){
                session.setAttribute("payment", 0);
            }else if(session.getAttribute("payment") == null){
                session.setAttribute("payment", 0);
            }else{
            try{
                Object obj1 = session.getAttribute("arr1");
                Object obj2 = session.getAttribute("arr2");
                Object obj3 = session.getAttribute("arr3");
                Object obj4 = session.getAttribute("arr4");
                
                if(obj1 != null){
                    List<String> name = (List<String>)obj1;
                    List<String> price = (List<String>)obj2;
                    List<String> discount = (List<String>)obj3;
                    List<String> quantity = (List<String>)obj4;
                    out.println("<h3 style='padding: 8px; background-color: #ccffaa; color:green'>" + "Products matching your search : " + "</h3>");
                    out.println("<form action='cart'>");
                    out.println("<table style='font-family: arial, sans-serif; border-collapse: collapse; width: 50%;'><tr>");
                    out.println("<th style='border: 1px solid #dddddd; text-align: left; padding: 8px'>Product Name</th><th style='border: 1px solid #dddddd; text-align: left; padding: 8px'>Price</th><th style='border: 1px solid #dddddd; text-align: left; padding: 8px'>Discount</th>"
                            + "<th style='border: 1px solid #dddddd; text-align: left; padding: 8px'>In Stock</th>" + "<th style='border: 1px solid #dddddd; text-align: left; padding: 8px'>Buy</th>");
                    out.println("</tr>");
                    for (int i = 0; i < name.size(); i++) {
                        if(i%2 == 0){
                            out.println("<tr style='background-color: #dddddd;'>");
                        }else{
                            out.println("<tr>");
                        }
                        out.println("<td style='border: 1px solid #dddddd; text-align: left; padding: 8px'>" + name.get(i) + "</td>" + "<td style='border: 1px solid #dddddd; text-align: left; padding: 8px'>" + price.get(i) + "</td>" + "<td style='border: 1px solid #dddddd; text-align: left; padding: 8px'>" + discount.get(i) + "%" + "</td>" + "<td style='border: 1px solid #dddddd; text-align: left; padding: 8px'>" + quantity.get(i) + "</td>" + 
                                "<td style='border: 1px solid #dddddd; text-align: left; padding: 8px'><input type='checkbox' name='prod' value='" + name.get(i) + "'" + "</input>");
                        out.println("</tr>");
                    }
                    out.println("</table><br>");
                    out.println("<button type='submit' name='sbutton' style='background-color: green; border:none; padding : 4px; padding-right: 8px; padding-left: 8px;color: white'>Add To Cart</button>");
                    out.println("</form>");
                }
                
            }catch(Exception e){
                
            }
            }
        }
         out.println("<hr>");   
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
