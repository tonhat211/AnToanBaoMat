package controller;

import DAO.CartUnitDAO;
import DAO.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.CartUnit;
import model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/cart")
public class CartController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("userLogging");

        String action = req.getParameter("action");
        if(action == null || action.isEmpty()) {

            ArrayList<CartUnit> carts = CartUnitDAO.getInstance().selectByUserID(user.getId());
            req.setAttribute("carts", carts);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/cart.jsp");
            rd.forward(req, resp);
        } else {
            action = action.toUpperCase();
            switch(action) {
                case "UPDATEQTY": {
                    int cartID = Integer.parseInt(req.getParameter("cartID"));
                    String operation = req.getParameter("operation");
                    int newQty = CartUnitDAO.getInstance().updateQty(cartID, operation);
                    if(newQty==0) {
                        Cart cart = new Cart(cartID);
                        CartUnitDAO.getInstance().delete(cart);
                    }
                    resp.setContentType("text/plain");
                    resp.setCharacterEncoding("UTF-8");
                    System.out.println("newqty: " + newQty);
                    PrintWriter out = resp.getWriter();
                    out.print(newQty);
                    out.flush();
                    break;
                }


            }

        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        doGet(req, resp);
    }

    public String renderHtml(String req, String page) {
        String html = "";
        if(req.equals("WRONGPASSWORD")) {
            html = "   <script>\n" +
                    "      tellWrongPassword();\n" +
                    " </script>";
        } else if(req.equals("SUCCESS")) {
            if(page.equalsIgnoreCase("cart")) {
                html = "   <script>\n" +
                        "      forward(\"cart?action=init\");\n" +
                        " </script>";
            } else {
                html = "   <script>\n" +
                        "      forward(\"product?action=init&&category=smartphone\");\n" +
                        " </script>";
            }

        }
        return html;
    }
}
