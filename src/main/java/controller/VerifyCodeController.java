package controller;

import DAO.VerifyCodeDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Constant;

import java.io.IOException;

@WebServlet("/verify")
public class VerifyCodeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        System.out.println("verify hello");
        String action = req.getParameter("action");
        String email = req.getParameter("email");

        if(action == null || action.isEmpty()) {
            req.setAttribute("email", email);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/verifyCode.jsp");
            rd.forward(req, resp);
        } else {
            action = action.toUpperCase();
            switch(action) {
                case "SIGNUP": {
                    System.out.println("verify code signup");
                    req.setAttribute("email", email);
                    req.setAttribute("action", action);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/verifyCode.jsp");
                    rd.forward(req, resp);
                    break;
                }
                case "VERIFY" : {
                    System.out.println("verifying code");
                    email = "2003tonhat@gmail.com"; //test
                    String code = req.getParameter("code");
                    int re=VerifyCodeDAO.getInstance().verifyCode(code,email);
                    if(re== Constant.EXPIRED_CODE) {// code het han
                        String html = renderHtml("EXPIREDCODE");
                        resp.getWriter().write(html);
                    } else if(re== Constant.USED_CODE){ //code thanh cong
                        String html = renderHtml("SUCCESS");
                        resp.getWriter().write(html);
                    } else { //sai code
                        String html = renderHtml("WRONGCODE");
                        resp.getWriter().write(html);
                    }
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

    public String renderHtml(String req) {
        String html = "";
        if(req.equals("EXPIREDCODE")) {
            html = "   <script>\n" +
                    "      tellExpiredCode();\n" +
                    " </script>";
        } else if(req.equals("WRONGCODE")) {
            html = "   <script>\n" +
                    "      tellWrongCode();\n" +
                    " </script>";
        } else { // thanh cong
            html = "   <script>\n" +
                    "      tellVerifySuccessful();\n" +
                    " </script>";
        }

        return html;

    }
}
