package controller;

import DAO.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/product")
public class ProductController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("userLogging");

        String action = req.getParameter("action");
        String category = req.getParameter("category");
        if(action == null || action.isEmpty()) action="INIT";
        action = action.toUpperCase();
        switch(action) {
            case "INIT" : {
                System.out.println("init product");
                if(category == null || category.isEmpty()) {
                    category = "smartphone";
                }
                category = category.toUpperCase();
                switch (category) {
                    case "SMARTPHONE": {
                        ArrayList<Brand> brands = BrandDAO.getInstance().selectByCategory(Constant.SMARTPHONE_CATEGORY);
                        ArrayList<ProductUnit> productUnits = ProductUnitDAO.getInstance().selectByCategory(Constant.SMARTPHONE_CATEGORY,0,20);
                        req.setAttribute("category", category);
                        req.setAttribute("brands", brands);
                        req.setAttribute("productUnits", productUnits);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/product.jsp");
                        rd.forward(req, resp);
                    }
                }
                break;
            }
            case "DETAIL": {
                System.out.println("detail product");
                int id = Integer.parseInt(req.getParameter("id"));
                ProductUnit pu = ProductUnitDAO.getInstance().selectById(id);
                ArrayList<Image> images = ImageDAO.getInstance().selectByParentID(id);
                SaleProgram saleProgram = SaleProgramDAO.getInstance().selectByObjectId(id);
                ArrayList<Comment> comments = CommentDAO.getInstance().selectObjectID(id);
                // them cac phien ban
                ArrayList<ProductDetail> details = ProductDetailDAO.getInstance().selectByProductID(id);
                pu.setDetails(details);


                // lay danh sach san pham mua kem
                ArrayList<ProductUnit> crossSells = ProductUnitDAO.getInstance().selectCrossSells(id,0,10);

                req.setAttribute("pu",pu);
                req.setAttribute("images",images);
                req.setAttribute("saleProgram",saleProgram);
                req.setAttribute("comments",comments);
                req.setAttribute("crossSells",crossSells);

                RequestDispatcher rd = getServletContext().getRequestDispatcher("/productDetail.jsp");
                rd.forward(req, resp);
                break;
            } case "BYBRAND": {
                String condition = req.getParameter("condition");
                int orderBy = Integer.parseInt(req.getParameter("orderby"));
                int offset = Integer.parseInt(req.getParameter("offset"));

                if(condition==null) {
                    System.out.println("condition is null");
                } else {
                    ArrayList<Integer> brandIDs = new ArrayList<>();
                    JsonArray jsonArray = JsonParser.parseString(condition).getAsJsonArray();
                    for(JsonElement jsonElement : jsonArray) {
                        brandIDs.add(jsonElement.getAsInt());
                    }
                    ArrayList<ProductUnit> productUnits = ProductUnitDAO.getInstance().selectByBrands(brandIDs,orderBy,offset,20);
                    String html = renderProductUnitList(productUnits);
                    resp.setContentType("text/html");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(html);
                }
                break;
            }
            case "FILTER": {
                System.out.println("filter");
                String condition = req.getParameter("condition");
                int orderBy = Integer.parseInt(req.getParameter("orderby"));
                int offset = Integer.parseInt(req.getParameter("offset"));
                if(condition==null) {
                    System.out.println("condition is null");
                } else {
                    ArrayList<String> osList = new ArrayList<>();
                    ArrayList<Integer> priceRange = new ArrayList<>();
                    JsonElement jsonElement = JsonParser.parseString(condition);
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    JsonArray osListJson = jsonObject.getAsJsonArray("osList");
                    for(JsonElement jsonElement1 : osListJson) {
                        osList.add(jsonElement1.getAsString());
                    }
                    JsonArray priceRangeJson = jsonObject.getAsJsonArray("priceRange");
                    priceRange.add(Integer.parseInt(priceRangeJson.get(0).getAsInt()+"000000"));
                    priceRange.add(Integer.parseInt(priceRangeJson.get(1).getAsInt()+"000000"));
                    System.out.println("Os list");
                    for(String s: osList) {
                        System.out.println(s);
                    }
                    System.out.println("price range");
                    for (Integer i : priceRange) {
                        System.out.println(i);
                    }

                    ArrayList<ProductUnit> productUnits = ProductUnitDAO.getInstance().selectByFilters(osList,priceRange,orderBy,offset,20);
                    String html = renderProductUnitList(productUnits);
                    resp.setContentType("text/html");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(html);
                }
                break;
            }
            case "CHECKVERSION" : {
                int id = Integer.parseInt(req.getParameter("productID"));
                String kind = req.getParameter("kind");
                String version = req.getParameter("version");
                String other = req.getParameter("other");
                ArrayList<ProductDetail> details = new ArrayList<>();
                if(kind.equalsIgnoreCase("color")) {
                    String tokens[] = other.split("-");
                    String ram="";
                    String rom="";
                    if(tokens.length<2) {
                        rom = tokens[0];
                        rom = rom.substring(0,rom.indexOf("GB")).trim();
                        other = rom;
                    } else {
                        ram = tokens[0];
                        ram = ram.substring(0,ram.indexOf("GB")).trim();
                        rom = tokens[1];
                        rom = rom.substring(0,rom.indexOf("GB")).trim();
                        other = ram+"=="+rom;
                    }
                    details = ProductDetailDAO.getInstance().selectByVersion(id,kind,version,other);
                    String exhaustedItems="";
                    double price=0;
                    for(ProductDetail d : details) {
                       if(d.getQty()<1) {
                           String s = d.getRam() + "GB - " + d.getRom()+"GB";
                           exhaustedItems+=s+"==";
                       } else {
                           if(d.getColor().equalsIgnoreCase(version) && d.getRom() == Integer.parseInt(rom)) {
                               price = d.getPrice();
                           }
                       }

                    }
                    String html = renderVersion(kind,exhaustedItems,price);
                    resp.setContentType("text/html");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(html);
                } else {
                    String tokens[] = version.split("-");
                    String ram="";
                    String rom="";
                    if(tokens.length<2) {
                        rom = tokens[0];
                        rom = rom.substring(0,rom.indexOf("GB")).trim();
                        version = rom;
                    } else {
                        ram = tokens[0];
                        ram = ram.substring(0,ram.indexOf("GB")).trim();
                        rom = tokens[1];
                        rom = rom.substring(0,rom.indexOf("GB")).trim();
                        version = ram+"=="+rom;
                    }
                    details = ProductDetailDAO.getInstance().selectByVersion(id,kind,version,other);

                    String exhaustedItems=""; // kind la ram rom nen day la color
                    double price=0;
                    for(ProductDetail d : details) {
                        if(d.getQty()<1) {
                            exhaustedItems+=d.getColor()+"==";
                        } else {
                            if(d.getColor().equalsIgnoreCase(other) && d.getRom() == Integer.parseInt(rom)) {
                                price = d.getPrice();
                            }
                        }

                    }


                    String html = renderVersion(kind,exhaustedItems,price);
                    resp.setContentType("text/html");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(html);
                }
                break;
            }
            case "ADDTOCART": {
                if(user==null) {
                    System.out.println("ko the them do chua dang nhap");
                    String html =
                            "<script>" +
                                "openModal('.modal-filter-details')" +
                            "</script>";
                    resp.setContentType("text/html");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(html);
                } else {
                    int productID = Integer.parseInt(req.getParameter("productID"));
                    String color= req.getParameter("color");
                    String version = req.getParameter("version");
                    int ram=0;
                    int rom=0;
                    String tokens[] = version.split("-");
                    if(tokens.length<2) {
                        String romTemp = tokens[0];
                        romTemp = romTemp.substring(0,romTemp.indexOf("GB")).trim();
                        rom = Integer.parseInt(romTemp);
                    } else {
                        String ramTemp = tokens[0];
                        ramTemp = ramTemp.substring(0,ramTemp.indexOf("GB")).trim();
                        String romTemp = tokens[1];
                        romTemp = romTemp.substring(0,romTemp.indexOf("GB")).trim();
                        ram = Integer.parseInt(ramTemp);
                        rom = Integer.parseInt(romTemp);
                    }
                    int productDetailID = ProductDetailDAO.getInstance().selectIDByVersion(productID,color,ram,rom);
                    Cart cart = new Cart(user.getId(),productDetailID,1);
                    int re=0;
                    int isExistID = CartUnitDAO.getInstance().checkExistCart(cart);
                    if(isExistID!=0) {
                        CartUnitDAO.getInstance().updateQty(isExistID,"plus");
                    } else {
                        re = CartUnitDAO.getInstance().insert(cart);
                    }
                    if(re==1) {
                        String html = renderNotify("Thêm vào giỏ hàng thành công");
                        resp.setContentType("text/html");
                        resp.setCharacterEncoding("UTF-8");
                        resp.getWriter().write(html);
                    }
                }
                break;
            }
            case "SEARCH": {
                String searchInput = req.getParameter("search-input");
                ArrayList<ProductUnit> productUnits = ProductUnitDAO.getInstance().selectBySearch(searchInput,0,20);
                int cateID = Constant.SMARTPHONE_CATEGORY;
                if(!productUnits.isEmpty()) cateID = productUnits.get(0).getProductID();
                ArrayList<Brand> brands = BrandDAO.getInstance().selectByCategory(cateID);
                switch (cateID) {
                    case Constant.SMARTPHONE_CATEGORY : category = "SMARTPHONE"; break;
                    case Constant.TABLET_CATEGORY: category = "TABLET"; break;
                    case Constant.LAPTOP_CATEGORY: category = "LAPTOP"; break;
                    default: category = "SMARTPHONE";
                }
                req.setAttribute("category", category);
                req.setAttribute("brands", brands);
                req.setAttribute("productUnits", productUnits);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/product.jsp");
                rd.forward(req, resp);
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

    public String renderProductUnitList(ArrayList<ProductUnit> productUnits) {
        String re="";
        for(ProductUnit p : productUnits) {
            re+="<div class=\"grid-col-2_4 product-item-container\">\n" +
                    "                        <div class=\"product-item\">\n" +
                    "                            <a href=\""+p.getUrlToDetail()+"\">\n" +
                    "                                <div class=\"info flex-roww status\">\n" +
                                                        p.getStatusItem() +
                    "                                </div>\n" +
                    "                                <div class=\"img-container flex-roww\" style=\"justify-content: center;\">\n" +
                    "                                    <img class=\"img-product\" src=\"./assets/img/thumbnail/"+p.getThumbnail()+"\" alt=\"\">\n" +
                    "                                </div>\n" +
                    "                                <div class=\"info promotion\">\n" +
                    "                                    <p class=\"promotion-item\">"+p.getSaleProgramItem()+"</p>\n" +
                    "                                </div>\n" +
                    "                                <div class=\"info name\">\n" +
                    "                                    <p>"+p.getFullName()+"</p>\n" +
                    "                                </div>\n" +
                    "\n" +
                    "                                <div class=\"info features flex-roww\">\n" +
                                                            p.getFeatureItems() +
                    "                                </div>\n" +
                    "                                <div class=\"version flex-roww group\">\n" +
                                                            p.getStorageVersionItems() +
                    "                                </div>\n" +
                    "                                <div class=\"price\">\n" +
                    "                                    <div><p class=\"cur-price\">"+p.getCurrentPrice()+" <span>VND</span></p> </div>\n" +
                    "\n" +
                    "                                    <div class=\"flex-roww\" style=\"justify-content: space-between;\">\n" +
                    "                                        <p class=\"init-price\">"+p.getInitialPrice()+" <span>VND</span></p>\n" +
                    "                                        <div class=\"info rate\">\n" +
                    "                                            <p><i class=\"bi bi-star-fill\"></i><span class=\"rate-figure\">"+p.getRate()+"</span>(<span class=\"total-estimate\">"+p.getTotalComment()+"</span>)</p>\n" +
                    "                                        </div>\n" +
                    "                                    </div>\n" +
                    "                                </div>\n" +
                    "                            </a>\n" +
                    "\n" +
                    "\n" +
                    "                        </div>\n" +
                    "\n" +
                    "                    </div>";
        }

        return re;
    }
    public String renderVersion(String kind, String exhaustedItems, double price) {
        String re="";
        if(kind.equalsIgnoreCase("color")) {
            re+= "<script>" +
                    "disableVersion('.option-selector .option-item', '"+exhaustedItems+"');" +
                "</script>";
        }
        else {
            re+= "<script>" +
                    "disableVersion('.color-selector .option-item', '"+exhaustedItems+"');" +
                "</script>";
        }
        re+="<script>" +
                "updatePrice('"+ProductUnit.formatPrice(price)+"')"+
            "</script>";
        return re;
    }

    public String renderNotify(String notify) {
        String re="";
        re= "<script>" +
                "showSuccessToast('"+notify+"');" +
            "</script>";

        return re;
    }

}
