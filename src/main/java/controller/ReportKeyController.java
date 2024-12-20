//package controller;
//
//import DAO.OrderDAO;
//import DAO.UserDAO;
//import DAO.VerifyCodeDAO;
//import JavaMail.EmailService;
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import model.User;
//import model.VerifyCode;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Random;
//
//@WebServlet("/reportKey")
//public class ReportKeyController extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html; charset=UTF-8");
//        req.setCharacterEncoding("UTF-8");
//        resp.setCharacterEncoding("UTF-8");
//        HttpSession session = req.getSession();
//        User userLogging = (User) session.getAttribute("userLogging");
//        String action  =req.getParameter("action");
//        action = action.toUpperCase();
//        switch (action) {
//            case "REPORTKEY": {
//                String time = req.getParameter("time");
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
//                LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
//                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//                String formattedDateTime = dateTime.format(outputFormatter);
//                time = formattedDateTime + ":00";
//                Random rand = new Random();
//                String otp ="";
//                for(int i=0;i<5; i++) {
//                    otp+= String.valueOf(rand.nextInt(9)+1);
//                }
////                otp="12345";
//                VerifyCode verifyCode = new VerifyCode(otp, userLogging.getEmail());
//                VerifyCodeDAO.getInstance().insertNewCode(verifyCode);
//
//                EmailService emailService = new EmailService();
//                emailService.send(userLogging.getEmail(),"Lộ private key","Bạn đã báo cáo bị lộ private key\nThời gian bị lộ là " +time
//                        +"\nHãy dùng mã OTP " +otp+ " để có thể thiết lập bộ key mới." +
//                        "\nMã OTP sẽ hết hạn trong vòng 5p");
//                System.out.println("chuyen huong");
//                req.setAttribute("email",userLogging.getEmail());
//                req.setAttribute("time",time);
//                RequestDispatcher rd = getServletContext().getRequestDispatcher("/reportKey.jsp");
//                rd.forward(req, resp);
//                break;
//            }
//            case "RESEND": {
//                String time = req.getParameter("time");
//                Random rand = new Random();
//                String otp ="";
//                for(int i=0;i<5; i++) {
//                    otp+= String.valueOf(rand.nextInt(9)+1);
//                }
////                otp="12345";
//                VerifyCode verifyCode = new VerifyCode(otp, userLogging.getEmail());
//                VerifyCodeDAO.getInstance().insertNewCode(verifyCode);
//
//                EmailService emailService = new EmailService();
//                emailService.send(userLogging.getEmail(),"Lộ private key","Bạn đã báo cáo bị lộ private key\nThời gian bị lộ là " +time
//                        +"\nHãy dùng mã OTP " +otp+ "để có thể thiết lập bộ key mới." +
//                        "\nMã OTP sẽ hết hạn trong vòng 5p");
//                resp.getWriter().write("");
//                break;
//            }
//            case "VERIFYOTP": {
//                System.out.println("Verify otp");
//                String otp = req.getParameter("otp");
//                int re=VerifyCodeDAO.getInstance().verifyCode(otp,userLogging.getEmail());
//                System.out.println("otp: " + otp);
//                if(re==1){
//                    System.out.println("thanh cong");
//                    String time = req.getParameter("time");
//                    session.setAttribute("can","yes");
//                    String html = callFuntion("window.location.href='updatePublicKey?t="+time+"';");
//                    resp.getWriter().write(html);
//                    break;
//                } else {
//                    System.out.println("that bai");
//                    resp.getWriter().write("wrongOTP();");
//                    break;
//                }
//            }
//            case "UPDATEPUBLICKEY": {
//                String publicKey = req.getParameter("public-key");
//                String time = req.getParameter("time");
//                int re = UserDAO.getInstance().updatePublicKey(userLogging.getId(), publicKey);
//                if(re==1) {
//                    OrderDAO.getInstance().removeSignatureOf(userLogging.getId(), time);
//                    String redirectUrl = req.getContextPath() + "/resignOrder?action=init";
//                    resp.sendRedirect(redirectUrl);
////                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/resignOrder?action=init&time="+time);
////                    rd.forward(req, resp);
//                }
//
//                break;
//            }
//        }
//
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        doGet(req, resp);
//    }
//
//    public String callFuntion(String function) {
//        return "<script>" + function + "</script>";
//    }
//}
