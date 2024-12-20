package controller;

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
import model.VerifyCode;

import java.io.IOException;
import java.util.Random;

@WebServlet("/verify")
public class VerifyCodeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        System.out.println("verify hello");
        String action = req.getParameter("a");

        if(action==null) {
            String email = req.getParameter("email");
            String forAction = req.getParameter("fa");
            Random rand = new Random();
            String otp ="";
            for(int i=0;i<5; i++) {
                otp+= String.valueOf(rand.nextInt(9)+1);
            }
//                otp="12345";
            VerifyCode verifyCode = new VerifyCode(otp, email);
            VerifyCodeDAO.getInstance().insert(verifyCode);

            EmailService emailService = new EmailService();
            if(forAction.equalsIgnoreCase("SIGNUP")) {
                emailService.send(email,"Xác thực tài khoản","Bạn đã tạo tài khoản mới bằng email\n"
                        +"\nHãy dùng mã OTP " +otp+ " để xác thực tài khoản." +
                        "\nMã OTP sẽ hết hạn trong vòng 5p");
            } else if(forAction.equalsIgnoreCase("reportKey")) {
                emailService.send(email,"Lộ private key","Bạn đã báo cáo bị lộ private key\n"
                        +"\nHãy dùng mã OTP " +otp+ " để có thể thiết lập bộ key mới." +
                        "\nMã OTP sẽ hết hạn trong vòng 5p");
            }

            req.setAttribute("email", email);
            req.setAttribute("action", forAction);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/verifyCode.jsp");
            rd.forward(req, resp);
            return;
        }
        action = action.toUpperCase();
        switch(action) {
            case "SIGNUP": {
//                System.out.println("verify code signup");
//                req.setAttribute("email", email);
//                req.setAttribute("action", action);
//                RequestDispatcher rd = getServletContext().getRequestDispatcher("/verifyCode.jsp");
//                rd.forward(req, resp);
//                break;
            }
            case "VERIFY": {
                System.out.println("verify");
                String email = req.getParameter("email");
                String forAction = req.getParameter("action");
                String otp = req.getParameter("otp");
                VerifyCode code = VerifyCodeDAO.getInstance().selectTheLast(email);
                boolean re=true;
                if(code.getIsVerify()==Constant.UNACTIVE) re=false;
                if(!otp.equalsIgnoreCase(code.getCode())) re=false;
                if(!code.isWithinFiveMinutes()) re=false;
                if(re) {
//                    VerifyCodeDAO.getInstance().disableCode(otp);
                    if(forAction.equalsIgnoreCase("SIGNUP")) {
                        String html = Constant.callFunction("openModal2('.verify-success-modal');");
                        resp.getWriter().write(html);
                    } else if(forAction.equalsIgnoreCase("reportKey")) {
                        String html = Constant.callFunction("submitReportKey();");
                        resp.getWriter().write(html);
                    }

                } else {
                    String html = Constant.callFunction("wrongOTP('#otp-form','Mã OTP sai');");
                    resp.getWriter().write(html);
                }
                break;
            }
            case "RESEND": {
//                String email = req.getParameter("email");
//                String forAction = req.getParameter("action");
//                Random rand = new Random();
//                String otp ="";
//                for(int i=0;i<5; i++) {
//                    otp+= String.valueOf(rand.nextInt(9)+1);
//                }
//                //                otp="12345";
//                VerifyCode verifyCode = new VerifyCode(otp, email);
//                VerifyCodeDAO.getInstance().insert(verifyCode);
//                EmailService emailService = new EmailService();
//                String mailContent = MessageValues.getOTP_VERIFY_ACCOUNT_MESSAGE(otp);
//                emailService.sendHTML(email,MessageValues.WEB_NAME, mailContent);
//
//                req.setAttribute("email", email);
//                req.setAttribute("action", forAction);
//                RequestDispatcher rd = getServletContext().getRequestDispatcher("/verifyCode.jsp");
//                rd.forward(req, resp);
//                break;
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
