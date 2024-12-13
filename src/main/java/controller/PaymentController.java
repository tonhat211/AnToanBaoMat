package controller;

import DAO.AddressDAO;
import DAO.CartUnitDAO;
import DAO.OrderDAO;
import DAO.ProductDetailDAO;
import DAO.SaleProgramDAO;
import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/payment")
public class PaymentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User userLogging = (User) session.getAttribute("userLogging");

        String action = req.getParameter("action");
        if(action == null || action.isEmpty()) {

            ArrayList<CartUnit> carts = CartUnitDAO.getInstance().selectByUserID(userLogging.getId());
            req.setAttribute("carts", carts);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/cart.jsp");
            rd.forward(req, resp);
        } else {
            action = action.toUpperCase();
            switch(action) {
                case "BUY" : {
                    System.out.println("buy: hello");
                    String[] idArr = req.getParameterValues("cart-check");
                    ArrayList<Integer> ids = new ArrayList<>();
                    for(String id : idArr) {
                        ids.add(Integer.parseInt(id));
                    }

                    ArrayList<CartUnit> carts = CartUnitDAO.getInstance().selectByIDs(ids);
                    ArrayList<Address> addresses = AddressDAO.getInstance().selectByUserID(userLogging.getId());
                    ArrayList<Integer> productDetailIDs = new ArrayList<>();
                    for(CartUnit cart : carts) {
                        productDetailIDs.add(cart.getProductDetailID());
                    }

                    SaleProgram saleProgram = SaleProgramDAO.getInstance().selectOfObjectIds(productDetailIDs);
                    if(saleProgram.getId()==0) {
                        saleProgram=new SaleProgram(0,"Không có chương trình",0);
                    }
                    req.setAttribute("carts", carts);
                    req.setAttribute("addresses", addresses);
                    req.setAttribute("saleProgram", saleProgram);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/payment.jsp");
                    rd.forward(req, resp);
                    break;
                }
                case "ORDER" : {
                    String idsJson = req.getParameter("idsJson");
                    Gson gson = new Gson();
                    String[] idArr = gson.fromJson(idsJson, String[].class);
                    ArrayList<Integer> ids = new ArrayList<>();
                    for(String id : idArr) {
                        ids.add(Integer.parseInt(id));
                    }

                    ArrayList<CartUnit> carts = CartUnitDAO.getInstance().selectByIDs(ids);
                    String address = req.getParameter("address");
                    String totalMoney = req.getParameter("totalMoney");

                    Order order = new Order(Double.parseDouble(totalMoney), userLogging.getId(), address);
                    int orderID = OrderDAO.getInstance().insert(order);

                    int re = OrderDAO.getInstance().insertOrderDetail(orderID,carts);

//                  xoa khoi gio hang
                    CartUnitDAO.getInstance().deleteOrderedCarts(ids);
                    if(re!=0) {
                        String html = callFuntion("setupSignOrderModal("+orderID+");");
                        resp.getWriter().write(html);
                    }
                    break;
                }
                case "SIGN": {
                    int orderID = Integer.parseInt(req.getParameter("orderID"));
                    String signature = req.getParameter("digital-signature");
                    int re = OrderDAO.getInstance().updateSignature(orderID,signature);

                    //  giam so luong trong kho
                    OrderUnit orderUnit = OrderDAO.getInstance().selectByID(orderID);
                    ArrayList<OrderDetail> details = orderUnit.getDetails();
                    ProductDetailDAO.getInstance().updateSaledQty(details);
                    resp.sendRedirect("order");
//                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/order");
//                    rd.forward(req, resp);
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

    public String callFuntion(String function) {
        return "<script>" + function + "</script>";
    }


}
