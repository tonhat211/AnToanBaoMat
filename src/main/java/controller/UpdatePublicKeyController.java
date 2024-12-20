package controller;

import DAO.OrderDAO;
import DAO.UserDAO;
import DAO.VerifyCodeDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Constant;
import model.User;
import model.VerifyCode;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/updatePublicKey")
public class UpdatePublicKeyController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User userLogging = (User) session.getAttribute("userLogging");
        if(userLogging == null) { return;}
        System.out.println("update key");
        String action = req.getParameter("action");
        if(action == null) {
            String otp = req.getParameter("otp");
            if(otp==null || userLogging==null) {
                String script = Constant.callFunction("changeToProductUrl();" +
                        "showErrorToast2('OTP hết hạn!','none');");

                req.setAttribute("script", script);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/product");
                dispatcher.forward(req, resp);
                return;
            }
            String email = userLogging.getEmail();

            VerifyCode code = VerifyCodeDAO.getInstance().selectTheLast(email);
            boolean re=true;
            if(code.getIsVerify()== Constant.UNACTIVE) re=false;
            if(!otp.equalsIgnoreCase(code.getCode())) re=false;
            if(!code.isWithinFiveMinutes()) re=false;
            if(re) {
                VerifyCodeDAO.getInstance().disableCode(otp); // huy otp
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/updatePublicKey.jsp");
                rd.forward(req, resp);
            } else {
                String script = Constant.callFunction("changeToProductUrl();" +
                        "showErrorToast2('OTP hết hạn!','none');");

                req.setAttribute("script", script);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/product");
                dispatcher.forward(req, resp);
                return;
            }
            return;
        }
        action = action.toUpperCase();
        switch (action) {
            case "UPDATE": {
                String time = req.getParameter("time");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String formattedDateTime = dateTime.format(outputFormatter);
                time = formattedDateTime + ":00";

                String publicKey = req.getParameter("public-key");
                int re = UserDAO.getInstance().updatePublicKey(userLogging.getId(), publicKey);
                if(re==1) {
                    OrderDAO.getInstance().removeSignatureOf(userLogging.getId(), time);
                    String redirectUrl = req.getContextPath() + "/resignOrder";
                    resp.sendRedirect(redirectUrl);
                }
                break;
            }
            case "DELAY": {
                String time = req.getParameter("time");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String formattedDateTime = dateTime.format(outputFormatter);
                time = formattedDateTime + ":00";
                int re = UserDAO.getInstance().updatePublicKey(userLogging.getId(), null);
                if(re==1) {
                    OrderDAO.getInstance().removeSignatureOf(userLogging.getId(), time);
                    resp.sendRedirect("product");
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
