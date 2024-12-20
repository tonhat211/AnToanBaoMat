package controller;

import DAO.OrderDAO;
import DAO.VerifyCodeDAO;
import JavaMail.EmailService;
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
import model.VerifyCode;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

@WebServlet("/resignOrder")
public class ResignOrderController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User userLogging = (User) session.getAttribute("userLogging");
        String action  =req.getParameter("action");
        System.out.println("Resign order controller");
        System.out.println("Action: " + action);
        if(action==null) {
            ArrayList<OrderUnit> orderUnits =  OrderDAO.getInstance().selectResignOrderUnit(userLogging.getId());
            req.setAttribute("orderUnits", orderUnits);
            System.out.println("get order units");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/resignOrder.jsp");
            rd.forward(req, resp);
            return;
        }

        action = action.toUpperCase();
        switch (action) {
            case "INIT": {

                ArrayList<OrderUnit> orderUnits =  OrderDAO.getInstance().selectResignOrderUnit(userLogging.getId());
                req.setAttribute("orderUnits", orderUnits);
                System.out.println("get order units");
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/resignOrder.jsp");
                rd.forward(req, resp);
                break;
            }
            case "RESIGN": {
                int id = Integer.parseInt(req.getParameter("id"));
                String signature = req.getParameter("signature");
                int re = OrderDAO.getInstance().updateSignature(id, signature);
                if(re==1) {
                    resp.getWriter().write("");
                }
                break;
            }
            case "CANCEL": {
                int id = Integer.parseInt(req.getParameter("id"));
                int re = OrderDAO.getInstance().updateStatus(id, Constant.CANCEL);
                if(re==1) {
                    resp.getWriter().write("");
                }
                break;
            }
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }
}
