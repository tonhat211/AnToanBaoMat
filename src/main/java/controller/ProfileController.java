package controller;

import DAO.AddressDAO;
import DAO.UserDAO;
import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Address;
import model.User;
import model.UserInfo;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

@WebServlet("/profile")
public class ProfileController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User userLogging = (User) session.getAttribute("userLogging");
        String action = req.getParameter("action");
        action = action.toUpperCase();
        switch (action) {
            case "ADDRESSBOOK" : {
                ArrayList<Address> addresses = AddressDAO.getInstance().selectByUserID(userLogging.getId());
                String html = renderAddressList(addresses);
                resp.setContentType("text/html");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(html);
                break;
            }
            case "UPDATEADDRESS" : {
                int id = Integer.parseInt(req.getParameter("id"));
                String receiver = req.getParameter("receiver");
                String phone = req.getParameter("phone");
                String street = req.getParameter("street");
                String village = req.getParameter("village");
                String district = req.getParameter("district");
                String province = req.getParameter("province");
                Address a = new Address(id, receiver, phone, street, village, district, province);
                int re = AddressDAO.getInstance().update(a);
                if(re==1) {
                    Address address = AddressDAO.getInstance().selectById(id);
                    String html = renderAddress(address);
                    resp.setContentType("text/html");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(html);
                }
                break;
            }
            case "DELETEADDRESS" : {
                int id = Integer.parseInt(req.getParameter("id"));
                Address a =new Address(id);
                int re = AddressDAO.getInstance().delete(a);
                if(re==1) {
                    String html = renderToast("Xóa địa chỉ thành công");
                    resp.setContentType("text/html");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(html);
                }
                break;
            }
            case "ADDADDRESS" : {
                int id = userLogging.getId();
                String receiver = req.getParameter("receiver");
                String phone = req.getParameter("phone");
                String street = req.getParameter("street");
                String village = req.getParameter("village");
                String district = req.getParameter("district");
                String province = req.getParameter("province");
                Address a = new Address(id, receiver, phone, street, village, district, province); // dung id address tam de lam id user
                int re = AddressDAO.getInstance().insert(a);
                if(re==1) {
                    ArrayList<Address> addresses = AddressDAO.getInstance().selectByUserID(userLogging.getId());
                    String html = renderAddressList(addresses);
                    resp.setContentType("text/html");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(html);

                }
                break;
            }
            case "INFO" : {
                User user = UserDAO.getInstance().selectById(userLogging.getId());
                System.out.println("info: user name:" + user.getName());
                Gson gson = new Gson();
                String userJson = gson.toJson(user);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(userJson);
                break;
            }
            case "UPDATEINFO" : {
                String name = req.getParameter("name");
                String email = req.getParameter("email");
                String gender = req.getParameter("gender");
                String phone = req.getParameter("phone");
                String birthday = req.getParameter("birthday");
                String dateIn = req.getParameter("dateIn");
                LocalDate birthdayDate = LocalDate.parse(birthday);
                LocalDate dateInDate = LocalDate.parse(dateIn);
                UserInfo info = new UserInfo(dateInDate,phone,gender,birthdayDate);
                User user = new User(userLogging.getId(),name,email,info.toJson());
                System.out.println("creat user from params: user name:" + user.getName());
                int re = UserDAO.getInstance().update(user);

                user = UserDAO.getInstance().selectById(user.getId());
                System.out.println("update info: user info:" + user.getInfo());
                Gson gson = new Gson();
                String userJson = gson.toJson(user);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(userJson);
                break;
            }
            case "UPDATEPASSWORD" : {
                String currentPassword = req.getParameter("currentPassword");
                String password = req.getParameter("password");
                password = User.hashPassword(password);
                User user = UserDAO.getInstance().checkLogin(userLogging.getEmail(),currentPassword);
                if(user!=null && user.getId()== userLogging.getId()) { // dung mat khau
                    int re = UserDAO.getInstance().updatePassword(user.getId(), password);
                    if(re==1) {
                        String html = renderToast("Đổi mật khẩu thành công");
                        html+= renderScript("removeModal('#modal-container');");
                        resp.setContentType("text/html");
                        resp.setCharacterEncoding("UTF-8");
                        resp.getWriter().write(html);
                    }
                } else { // sai mat khau
                    String html = renderScript("tellWrongCurrentPassword();");
                    resp.setContentType("text/html");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(html);
                }
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

    public String renderAddressList(ArrayList<Address> addresses) {
        String re="";
        for(Address a: addresses) {
            re+="<div class=\"address-item\">\n" +
                "      <div class=\"flex-roww\" style=\"justify-content: space-between; align-items: start\">\n" +
                "           <div class=\"address-info\">\n" +
                "                <p><span class=\"receiver\">"+a.getReceiver()+"</span> |   <span class=\"phone\">"+a.getPhone()+"</span></p>\n" +
                "                <p class=\"id\" hidden>"+a.getId()+"</p>\n" +
                "                <p style=\"margin-top: 5px\">Địa chỉ:</p>\n" +
                "                <div>\n" +
                "                     <p class=\"address\" style=\"margin-left: 10px;\"><span class=\"street\">"+a.getStreet()+"</span> - <span class=\"village\">"+a.getVillage()+"</span> - <span class=\"district\">"+a.getDistrict()+"</span> - <span class=\"province\">"+a.getProvince()+"</span></p>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"flex-coll update-action\">\n" +
                "                 <i class=\"bi bi-pencil-square btn-edit\" onclick=\"initUpdateAddress(event,'.address-item','#editAddressForm')\"></i>\n" +
                "                 <i class=\"bi bi-trash btn-delete\" onclick=\"initDeleteAddress(event,'.address-item','.modall','.address-confirm')\"></i>\n" +
                "                 <!--                                <i class=\"fa-solid fa-ellipsis editBtn\" onclick=\"showEditAddress.call(this,event)\"></i>-->\n" +
                "            </div>\n" +
                "       </div>\n" +
                "       <div class=\"seperate-horizontal-90\"></div>\n" +
                " </div>";
        }
        return re;
    }
    public String renderAddress(Address a) {
        String re="<div class=\"address-item\">\n" +
                "      <div class=\"flex-roww\" style=\"justify-content: space-between; align-items: start\">\n" +
                "           <div class=\"address-info\">\n" +
                "                <p><span class=\"receiver\">"+a.getReceiver()+"</span> |   <span class=\"phone\">"+a.getPhone()+"</span></p>\n" +
                "                <p class=\"id\" hidden>"+a.getId()+"</p>\n" +
                "                <p style=\"margin-top: 5px\">Địa chỉ:</p>\n" +
                "                <div>\n" +
                "                     <p class=\"address\" style=\"margin-left: 10px;\"><span class=\"street\">"+a.getStreet()+"</span> - <span class=\"village\">"+a.getVillage()+"</span> - <span class=\"district\">"+a.getDistrict()+"</span> - <span class=\"province\">"+a.getProvince()+"</span></p>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"flex-coll update-action\">\n" +
                "                 <i class=\"bi bi-pencil-square btn-edit\" onclick=\"initUpdateAddress(event,'.address-item','#editAddressForm')\"></i>\n" +
                "                 <i class=\"bi bi-trash btn-delete\" onclick=\"initDeleteAddress(event,'.address-item','.modall','.address-confirm')\"></i>\n" +
                "                 <!--                                <i class=\"fa-solid fa-ellipsis editBtn\" onclick=\"showEditAddress.call(this,event)\"></i>-->\n" +
                "            </div>\n" +
                "       </div>\n" +
                "       <div class=\"seperate-horizontal-90\"></div>\n" +
                " </div>";
        return re;
    }

    public String renderToast(String message) {
        String re ="  <script>\n" +
                "          showSuccessToast('"+message+"','#toast-header');\n" +
                "     </script>";
        return re;
    }

    public String renderScript(String method) {
        String re ="  <script>\n" +
                    method +
                "     </script>";
        return re;
    }
}
