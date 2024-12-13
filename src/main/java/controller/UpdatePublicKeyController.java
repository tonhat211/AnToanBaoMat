package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/updatePublicKey")
public class UpdatePublicKeyController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String can = (String) session.getAttribute("can");
        System.out.println("can:" + can);
        if(can.equalsIgnoreCase("yes")) {
            session.removeAttribute("can");
            String time = req.getParameter("t");
            System.out.println("time: " +time);
            req.setAttribute("time",time);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/updatePublicKey.jsp");
            rd.forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
