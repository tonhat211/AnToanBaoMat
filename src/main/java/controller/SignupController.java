package controller;

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
import java.time.LocalDate;

@WebServlet("/signup")
public class SignupController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();

        String action = req.getParameter("action");
        if(action == null || action.isEmpty()) {

            RequestDispatcher rd = getServletContext().getRequestDispatcher("/signup.jsp");
            rd.forward(req, resp);
        } else {
            action = action.toUpperCase();
            switch(action) {
                case "SIGNUP": {
                    System.out.println("check signup");
                    String email = req.getParameter("email");
//                    kiem tra xem email da duoc dang ky chua
                    if(UserDAO.getInstance().checkEmail(email)) {// da duoc dang ky
                        System.out.println("email da ton tai");
                        String html = renderHtml("USEDEMAIL");
                        resp.getWriter().write(html);
                    } else { // email co the dang ky
                        System.out.println("insert user into database");
                        String name = req.getParameter("name");
                        String password = req.getParameter("password");
                        String hashPassword = User.hashPassword(password);
                        String today = LocalDate.now().toString();
                        String info = "{\"dateIn\":\""+today+"\",\"phone\":\"null\",\"gender\":\"null\",\"birthday\":\"null\",\"position\":\"null\",\"area\":\"null\"}";
                        User user = new User(name,email,hashPassword,info);
                        int re=UserDAO.getInstance().insert(user);
                        if(re!=0) { // insert thanh cong

                            // khoi tao otp va insert vao database
                            VerifyCode verifyCode = new VerifyCode(email);
                            String code = VerifyCodeDAO.getInstance().insertNewCode(verifyCode);

                            // hien tai dang bi loi phan gui mail


                            String html = renderHtml(email);
                            resp.getWriter().write(html);
                        } else {
                            System.out.println("insert failed, do database");
                            String html = renderHtml(email); // de tam, mot nho sua thanh truong hop khong thanh cong
                            resp.getWriter().write(html);
                        }
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
        if(req.equals("USEDEMAIL")) {
            html = "   <script>\n" +
                    "      tellUsedEmail();\n" +
                    " </script>";
        } else if(req.equals("SUCCESS")) {
            html = "   <script>\n" +
                    "      forward(\"verify?email=\");\n" +
                    " </script>";
        } else {
            html = "   <script>\n" +
                    "      forward(\"verify?action=signup&&email="+req+"\");\n" +
                    " </script>";
        }
        return html;
    }
}
