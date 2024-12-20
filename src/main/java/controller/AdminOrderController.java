package controller;

import DAO.OrderDAO;
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

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/adminorder")
public class AdminOrderController extends HttpServlet {
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
            case "SEARCH": {
                String key = req.getParameter("key");
                int byStatus = Integer.parseInt(req.getParameter("byStatus"));

                ArrayList<OrderUnit> orderUnits =  new ArrayList<>();
                if(key.contains(":") || key.contains("-")) {
                    orderUnits = OrderDAO.getInstance().selectOrderUnitByTime(byStatus,key,0,100);
                } else {
                    orderUnits = OrderDAO.getInstance().selectOrderUnitByID(byStatus,key,0,100);
                }
                int numOfOrders = orderUnits.size();
                Integer numOfPages = numOfOrders / Constant.NUM_OF_ITEMS_A_PAGE;
                if(numOfOrders % Constant.NUM_OF_ITEMS_A_PAGE != 0) numOfPages++;
                req.setAttribute("numOfPages", numOfPages);
                req.setAttribute("orderUnits", orderUnits);
                req.setAttribute("byStatus", byStatus);

                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminOrder.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            case "BYSTATUS": {
                int byStatus = Integer.parseInt(req.getParameter("byStatus"));
                System.out.println("By status:" +  byStatus);

                ArrayList<OrderUnit> orderUnits =  OrderDAO.getInstance().selectOrderUnitByStatus(byStatus,0,Constant.NUM_OF_ITEMS_A_PAGE);
                int numOfOrders = OrderDAO.getInstance().selectCountOrderUnitBy(byStatus);
                Integer numOfPages = numOfOrders / Constant.NUM_OF_ITEMS_A_PAGE;
                if(numOfOrders % Constant.NUM_OF_ITEMS_A_PAGE != 0) numOfPages++;
                req.setAttribute("numOfPages", numOfPages);
                req.setAttribute("orderUnits", orderUnits);
                req.setAttribute("byStatus", byStatus);

                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminOrder.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            case "ORDEROF": {
                int id = Integer.parseInt(req.getParameter("id"));
                System.out.println("order of");

                ArrayList<OrderUnit> orderUnits =  OrderDAO.getInstance().selectOrderUnitOf(id);
//                int numOfOrders = OrderDAO.getInstance().selectCountOrderUnitBy(byStatus);
//                Integer numOfPages = numOfOrders / Constant.NUM_OF_ITEMS_A_PAGE;
//                if(numOfOrders % Constant.NUM_OF_ITEMS_A_PAGE != 0) numOfPages++;
//                req.setAttribute("numOfPages", numOfPages);
                req.setAttribute("orderUnits", orderUnits);
                req.setAttribute("title","Đơn hàng của " + id);
//                req.setAttribute("byStatus", byStatus);

                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminOrder.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            case "QUERY": {
                System.out.println("order query");
                int page = req.getParameter("page")!=null?Integer.parseInt(req.getParameter("page")):0;
                int byStatus = req.getParameter("byStatus")!=null?Integer.parseInt(req.getParameter("byStatus")):-1;
                int offset = (page-1)*Constant.NUM_OF_ITEMS_A_PAGE;
                System.out.println("page: " + page);
                System.out.println("byStatus: " + byStatus);
                System.out.println("offset: " + offset);
                ArrayList<OrderUnit> orderunits = OrderDAO.getInstance().selectOrderUnitByStatus(byStatus,offset,Constant.NUM_OF_ITEMS_A_PAGE);
                String html = renderOrderList(orderunits);
                resp.getWriter().write(html);
                break;
            }
            case "UPDATE": {
                System.out.println("admin order : update status");
                int byStatus = Integer.parseInt(req.getParameter("byStatus") != null ? req.getParameter("byStatus") : "-1");
                int status = Integer.parseInt(req.getParameter("status"));
                int id = Integer.parseInt(req.getParameter("id"));
                String userEmail = OrderDAO.getUserEmailByOrderID(id); // Lấy email người dùng từ order ID

                // Kiểm tra trạng thái chữ ký
                int signStatus = OrderDAO.checkSign(id); // Lấy trạng thái chữ ký từ DB

                // Kiểm tra trạng thái mới
                int currentStatus = OrderDAO.getInstance().selectStatus(id);
                if ((status <= currentStatus) || status < 0) { // Không hợp lệ
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    resp.setContentType("text/html");
                    resp.setCharacterEncoding("UTF-8");
                    String html = "<script>\n" +
                            "      showErrorToast2(\"Cập nhật trạng thái cho " + id + " thất bại. Đơn hàng đã " + Constant.getStatusString(currentStatus) + " trước đó.\");\n" +
                            "    </script>";
                    resp.getWriter().write(html);
                } else { // Hợp lệ
                    int page = Integer.parseInt(req.getParameter("page"));
                    int offset = (page - 1) * Constant.NUM_OF_ITEMS_A_PAGE;
                    int re = OrderDAO.getInstance().updateStatus(id, status);
                    if (re == 1) {
                        // Gửi email thông báo
                        EmailService emailService = new EmailService();
                        String subject = "";
                        String content = "";

                        if (status == Constant.CONFIRM) { // Xác nhận đơn hàng
                            subject = "Xác nhận đơn hàng";
                            content = "Đơn hàng #" + id + " của bạn đã được xác nhận. Cảm ơn bạn đã sử dụng dịch vụ!";
                        } else if (status == Constant.CANCEL) { // Hủy đơn hàng
                            subject = "Hủy đơn hàng";
                            if (signStatus == 0) { // Chưa ký
                                content = "Đơn hàng #" + id + " đã bị hủy do chữ ký chưa được cung cấp.";
                            } else if (signStatus == -1) { // Chữ ký sai
                                content = "Đơn hàng #" + id + " đã bị hủy do chữ ký không hợp lệ.";
                            } else { // Chữ ký hợp lệ
                                content = "Đơn hàng #" + id + " đã bị hủy theo yêu cầu.";
                            }
                        }

                        if (!subject.isEmpty() && !content.isEmpty()) {
                            emailService.send(userEmail, subject, content);
                        }

                        // Cập nhật giao diện
                        ArrayList<OrderUnit> orderUnits = OrderDAO.getInstance().selectOrderUnitByStatus(byStatus, offset, Constant.NUM_OF_ITEMS_A_PAGE);
                        String html = renderOrderList(orderUnits);
                        html += "<script>\n" +
                                "      showSuccessToast2(\"Cập nhật trạng thái cho " + id + " thành công!\",'none');\n" +
                                "</script>";
                        resp.getWriter().write(html);
                    } else {
                        String html = "<script>\n" +
                                "      showErrorToast2(\"Cập nhật trạng thái cho " + id + " thất bại. Lỗi: " + Constant.UNDEFINED + "\");\n" +
                                "    </script>";
                        resp.getWriter().write(html);
                    }
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


    public String renderOrderList(ArrayList<OrderUnit> orderUnits) {
        StringBuilder re = new StringBuilder();
        for (OrderUnit o : orderUnits) {
            int orderId = o.getOrderID(); // Lấy orderId từ đối tượng OrderUnit
            int signStatus = OrderDAO.checkSign(orderId); // Kiểm tra chữ ký

            // Xác định trạng thái chữ ký để hiển thị
            String signStatusHTML;
            if (signStatus == 1) {
                signStatusHTML = "<span class=\"status-box signed\">Đã ký</span>";
            } else if (signStatus == -1) {
                signStatusHTML = "<span class=\"status-box invalid-sign\">Chữ ký sai</span>";
            } else {
                signStatusHTML = "<span class=\"status-box unsigned\">Chưa ký</span>";
            }

            // Render HTML
            String temp = "<tr class=\"group\">\n" +
                    "    <th scope=\"row\" class=\"grid-col-0_5 text-center id\" style=\"height: fit-content;\">" + o.getOrderID() + "</th>\n" +
                    "    <td class=\"grid-col-1 text-center\">\n" +
                    "        <span class=\"time\">" + o.getDateSet() + "</span>\n" +
                    "    </td>\n" +
                    "    <td class=\"grid-col-3_5\">\n" +
                    "        " + o.getProductList() + "\n" +
                    "    </td>\n" +
                    "    <td class=\"grid-col-1_5\">" + o.getTotalMoney() + " VND</td>\n" +
                    "    <td class=\"grid-col-1\">" + signStatusHTML + "</td>\n" +
                    "    <td class=\"grid-col-1\"><span class=\"status-" + o.getStatus() + "\">" + o.getStatusString() + "</span></td>\n" +
                    "    <td class=\"grid-col-1 text-center\"><span class=\"updateTime time\">" + o.getUpdateTime() + "</span></td>\n" +
                    "    <td class=\"grid-col-1\">\n" +
                    "        <button class=\"btn btn-status-" + o.getNextStatus() + "\" type=\"button\" onclick=\"updateStatus('" + o.getOrderID() + "','" + o.getNextStatus() + "')\">" + o.getNextStatusString() + "</button>\n" +
                    "        <select name=\"action\" onchange=\"handleChange(event);\" data-default=\"none\" style=\"margin-bottom: 15px;\">\n" +
                    "            <option value=\"none\">...</option>\n" +
                    "            <option value=\"" + Constant.CONFIRM + "\">Xác nhận</option>\n" +
                    "            <option value=\"" + Constant.DELIVERY + "\">Bàn giao</option>\n" +
                    "            <option value=\"" + Constant.COMPLETE + "\">Thành công</option>\n" +
                    "            <option value=\"" + Constant.CANCEL + "\">Hủy</option>\n" +
                    "            <option value=\"detail\">Chi tiết</option>\n" +
                    "        </select>\n" +
                    "        <a class=\"address-detail-btn\" href=\"\" onclick=\"event.preventDefault()\">Địa chỉ</a>\n" +
                    "        <div style=\"position: relative;\">\n" +
                    "            <div class=\"address-container sub-content\">\n" +
                    "                <p>" + o.getReceiver() + "</p>\n" +
                    "                <p>" + o.getAddress() + "</p>\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "    </td>\n" +
                    "</tr>";
            re.append(temp);
        }
        return re.toString();
    }


}
