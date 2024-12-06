package controller;

import DAO.OrderDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Constant;
import model.OrderUnit;
import model.User;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/order")
public class OrderController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User userLogging = (User) session.getAttribute("userLogging");
        String action = req.getParameter("action");
        if(action==null) {
            ArrayList<OrderUnit> orderUnits =  OrderDAO.getInstance().selectOrderUnitOfAndBy(userLogging.getId(), Constant.WAITING);
            req.setAttribute("orderUnits", orderUnits);
            req.setAttribute("status", Constant.WAITING);
            if(!orderUnits.isEmpty()) {
                OrderUnit OrderPresentation = orderUnits.get(0);
                req.setAttribute("OrderPresentation", OrderPresentation);
            }
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/order.jsp");
            rd.forward(req, resp);
            return;
        }
        action = action.toUpperCase();
        switch (action) {
            case "BYSTATUS": {
                int status = Integer.parseInt(req.getParameter("status"));
                ArrayList<OrderUnit> orderUnits =  OrderDAO.getInstance().selectOrderUnitOfAndBy(userLogging.getId(), status);
                if(!orderUnits.isEmpty()) {
                    OrderUnit OrderPresentation = orderUnits.get(0);
                    req.setAttribute("OrderPresentation", OrderPresentation);
                }
                req.setAttribute("orderUnits", orderUnits);
                req.setAttribute("status", status);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/order.jsp");
                rd.forward(req, resp);
                break;
            }
            case "CANCEL": {
                int id = Integer.parseInt(req.getParameter("id"));
                int re = OrderDAO.getInstance().updateStatus(id, Constant.CANCEL);
                ArrayList<OrderUnit> orderUnits =  OrderDAO.getInstance().selectOrderUnitOfAndBy(userLogging.getId(), Constant.CANCEL);
                req.setAttribute("orderUnits", orderUnits);
                req.setAttribute("status", Constant.CANCEL);
                req.setAttribute("message", "Đã hủy thành công!");
                if(!orderUnits.isEmpty()) {
                    OrderUnit OrderPresentation = orderUnits.get(0);
                    req.setAttribute("OrderPresentation", OrderPresentation);
                }
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/order.jsp");
                rd.forward(req, resp);
                break;
            }
            case "DETAIL": {
                int status = Integer.parseInt(req.getParameter("status"));
                int id = Integer.parseInt(req.getParameter("id"));
                ArrayList<OrderUnit> orderUnits =  OrderDAO.getInstance().selectOrderUnitOfAndBy(userLogging.getId(), status);
                req.setAttribute("orderUnits", orderUnits);
                req.setAttribute("status", status);

                for(OrderUnit o : orderUnits) {
                    if(o.getOrderID() == id) {
                        req.setAttribute("OrderPresentation", o);
                    }
                }
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/order.jsp");
                rd.forward(req, resp);
                break;
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

}
