package controller;

import DAO.OrderDAO;
import DAO.PublicKeyDAO;
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
import model.PublicKey;
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
        String action = req.getParameter("a");
        if(action == null) {
            System.out.println("Action null");
            String otp = req.getParameter("otp");
            System.out.println("otp: " + otp );
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
            System.out.println("otp end time: " + code.getTime());
            if(re) {
                System.out.println("otp correct");
                VerifyCodeDAO.getInstance().disableCode(otp); // huy otp
                String forAction = req.getParameter("fa");
                req.setAttribute("fa",forAction);
                System.out.println("forAction: " + forAction);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/updatePublicKey.jsp");
                rd.forward(req, resp);
            } else {
                System.out.println("otp incorrect");

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
            case "ADD": {
                String publicKeyContent = req.getParameter("public-key");
                PublicKey publicKey = new PublicKey(0,userLogging.getId(), publicKeyContent);
                int re = PublicKeyDAO.getInstance().insert(publicKey);
                User freshUser = UserDAO.getInstance().selectById(userLogging.getId());
                session.setAttribute("userLogging", freshUser);
                String script = Constant.callFunction("changeToProductUrl();" +
                        "showSuccessToast2('Thêm Public Key thành công!','none');");
                req.setAttribute("script", script);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/product");
                dispatcher.forward(req, resp);
                break;
            }
            case "UPDATE": {
                System.out.println("update");
                String time = req.getParameter("time");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String formattedDateTime = dateTime.format(outputFormatter);
                time = formattedDateTime + ":00";

                String publicKeyContent = req.getParameter("public-key");
                System.out.println("userloging key: " + userLogging.publicKey.id);
                PublicKey publicKey = userLogging.publicKey;
                PublicKeyDAO.getInstance().disablePublicKey(publicKey,time);
                publicKey = new PublicKey(0, userLogging.getId(), publicKeyContent);
                int re =PublicKeyDAO.getInstance().insert(publicKey);
                if(re==1) {
                    User freshUser = UserDAO.getInstance().selectById(userLogging.getId());
                    session.setAttribute("userLogging", freshUser);
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
