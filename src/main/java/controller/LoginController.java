package controller;

import DAO.UserDAO;
import com.mysql.cj.Session;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        

        String action = req.getParameter("action");
        if(action == null || action.isEmpty()) {

            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
            rd.forward(req, resp);
        } else {
            action = action.toUpperCase();
            switch(action) {
                case "CHECK": {
                    System.out.println("check login");
                    String email = req.getParameter("email");
                    String password = req.getParameter("password");
                    password = User.hashPassword(password);
                    User u = UserDAO.getInstance().checkLogin(email, password);
                    if(u==null) { // khong thanh cong
                        System.out.println("login failed");
                        String html = renderHtml("WRONGPASSWORD","");
                        resp.getWriter().write(html);
                    } else { // thanh cong
                        System.out.println("login success");
                        String page = req.getParameter("page");
                        String html;
                        session.setAttribute("userLogging",u);
                        System.out.println("page: " + page);
                        html = renderHtml("SUCCESS",page);
                        if(u.getRoles().length!=0) { //admin
                            System.out.println("confirm admin");
                            html = callFunction("forward(\"adminmenu?action=init\");");
                        }
                        resp.getWriter().write(html);
                    }
                    break;
                }
                case "REQUIRE" : {
                    System.out.println("Require");
                    String page = req.getParameter("page");
                    System.out.println("page:" + page);
                    String pageAction = req.getParameter("pageAction");
                    String pageProductID = req.getParameter("pageProductID");
                    String pageUrl=null;
                    if (pageAction.equalsIgnoreCase("forward")) {
                        pageUrl = page;
                    } else {
                        pageUrl = page + "?action=" + pageAction + "&id=" + pageProductID;
                    }
                    System.out.println("url:" + pageUrl);

                    if(pageUrl!=null) {
                        req.setAttribute("page",pageUrl);
                    }
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
                    rd.forward(req, resp);
                    break;
                }
                case "LOGOUT" :{
                    session.removeAttribute("userLogging");
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/index");
                    rd.forward(req, resp);
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

    public String renderHtml(String req, String page) {
        String html = "";
        if(req.equals("WRONGPASSWORD")) {
            html = "   <script>\n" +
                    "      tellWrongPassword();\n" +
                    " </script>";
        } else if(req.equals("SUCCESS")) {
            if(!page.equals("")) {
                html = "   <script>\n" +
                        "      forward(\""+page+"\");\n" +
                        " </script>";
            }
            else  {
                html = "   <script>\n" +
                        "      forward(\"product?action=init&&category=smartphone\");\n" +
                        " </script>";
            }
            System.out.println(html);

        }
        return html;
    }

    public String callFunction(String function) {
        return "   <script>\n" +
                function +
                " </script>";

    }
}
