package controller;

import DAO.BrandDAO;
import DAO.ImageDAO;
import DAO.OrderDAO;
import DAO.ProductUnitDAO;
import com.google.gson.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@MultipartConfig
@WebServlet("/adminproduct")
public class AdminProductController extends HttpServlet {
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
                String idin = req.getParameter("search");
                ArrayList<ProductUnit> productUnits = ProductUnitDAO.getInstance().searchForAdmin(idin);
                req.setAttribute("numOfPages", 1);
                req.setAttribute("productUnits", productUnits);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminProduct.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            case "QUERYBY": {
                System.out.println("admin product: query by cate");
                int cateID = Integer.parseInt(req.getParameter("cateID"));
                ArrayList<ProductUnit> productUnits = ProductUnitDAO.getInstance().selectByCategoryForAdmin(cateID,0,Constant.NUM_OF_ITEMS_A_PAGE);
                int numOfProducts = ProductUnitDAO.getInstance().selectCountByCategory(cateID);
                Integer numOfPages = numOfProducts / Constant.NUM_OF_ITEMS_A_PAGE;
                if(numOfProducts % Constant.NUM_OF_ITEMS_A_PAGE != 0) numOfPages++;

                req.setAttribute("numOfPages", numOfPages);
                req.setAttribute("productUnits", productUnits);
                req.setAttribute("cateID", cateID);

                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminProduct.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            case "QUERY": {
                System.out.println("product query");
//                int page = req.getParameter("page")!=null?Integer.parseInt(req.getParameter("page")):0;
//                int byStatus = req.getParameter("byStatus")!=null?Integer.parseInt(req.getParameter("byStatus")):-1;
//                int offset = (page-1)*Constant.NUM_OF_ITEMS_A_PAGE;
//                System.out.println("page: " + page);
//                System.out.println("byStatus: " + byStatus);
//                System.out.println("offset: " + offset);
//                ArrayList<OrderUnit> orderunits = OrderDAO.getInstance().selectOrderUnitByStatus(byStatus,offset,Constant.NUM_OF_ITEMS_A_PAGE);
//                String html = renderOrderList(orderunits);
//                resp.getWriter().write(html);
                break;
            }
            case "PREPAREUPDATE" : {
                String idString = req.getParameter("id");
                System.out.println("prepare update id: "+idString);
                int id = Integer.parseInt(idString);
                ProductUnit p = ProductUnitDAO.getInstance().selectForUpdateProduct(id);
                ArrayList<Brand> brands = BrandDAO.getInstance().selectByCategory(p.cateID);
                ArrayList<Image> imgs = ImageDAO.getInstance().selectByParentID(p.getProductID());
                String html = renderUpdateForm(p,brands, imgs) ;
                System.out.println("html:" + html);
                resp.getWriter().write(html);
                break;
            }
            case "UPDATE": {
                System.out.println("admin product : update info");
                int id = Integer.parseInt(req.getParameter("id"));
                String name = req.getParameter("name");
                int brandID = Integer.parseInt(req.getParameter("brandID"));
                int cateID = Integer.parseInt(req.getParameter("cateID"));
                String firstSale = req.getParameter("firstSale").replace(".", "");
                String config = req.getParameter("config");
                int prominence = Integer.parseInt(req.getParameter("prominence"));
                String version = req.getParameter("version");
                String des = req.getParameter("des");
                String imgUrls = req.getParameter("imgUrls");
                Brand brand = new Brand(brandID);
                ArrayList<String> imgUrlList = new ArrayList<>();

                if(imgUrls!=null || !imgUrls.isEmpty()) {
                    String temps[] = imgUrls.split("==");
                    for(String s : temps) {
                        imgUrlList.add(s);
                    }

                }
                // upload img
                String uploadThumbnailDir = getServletContext().getRealPath("/") + Constant.THUMBNAIL_DIR;
                String uploadImagesDir = getServletContext().getRealPath("/") + Constant.PRODUCT_DETAIL_IMG_DIR;

                File thumbnailDir = new File(uploadThumbnailDir);
                if (!thumbnailDir.exists()) {
                    thumbnailDir.mkdirs();  // Create the thumbnails directory
                }
                File imagesDir = new File(uploadImagesDir);
                if (!imagesDir.exists()) {
                    imagesDir.mkdirs();  // Create the images directory
                }
                Part thumbnailPart = req.getPart("thumbnail"); // For the thumbnail (single file)

                String thumbnailFileName="";

                // For the images (multiple files)
                // Handle the thumbnail upload
                if (thumbnailPart != null && thumbnailPart.getSize() > 0) {
                    thumbnailFileName = getFileName(thumbnailPart);
                    String thumbnailPath = uploadThumbnailDir + File.separator + thumbnailFileName;
                    File file = new File(thumbnailPath);
                    if (!file.exists()) thumbnailPart.write(thumbnailPath);
                    // Write the file to the server
                    ProductUnitDAO.getInstance().updateThumbnail(id,thumbnailFileName);
                }
                // Handle the image upload


                Collection<Part> parts = req.getParts();
                for (Part part : parts) {
                    if (part.getName().equals("imgs") && part.getSize() > 0) {
                        String imageFileName = getFileName(part);
                        String imagePath = uploadImagesDir + File.separator + imageFileName;
                        File file = new File(imagePath);
                        if (!file.exists()) part.write(imagePath);
                        // Write the image file to the server
                    }
                }

                ProductUnit p = new ProductUnit(id, name, version, brand, cateID, config, thumbnailFileName, firstSale, prominence,des);

                int re = ProductUnitDAO.getInstance().updateProduct(p);


                if(!imgUrlList.isEmpty()) ProductUnitDAO.getInstance().updateImgs(id,imgUrlList);
                if(re==1) {
                    System.out.println("update thanh cong");
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminmenu?action=adminproduct&message=updateSuccess_"+id);
                    dispatcher.forward(req, resp);
//                    resp.sendRedirect("/adminmenu?action=adminproduct&message=updateSuccess_"+id);
                }
//                resp.getWriter().println("Files uploaded successfully!");
                break;
            }
            case "LOCK": {
                System.out.println("lock product");
                int id = Integer.parseInt(req.getParameter("id"));
                int re = ProductUnitDAO.getInstance().lockProduct(id);
                String html="";
                if(re==1) {
                    html = htmlSuccessToast("khóa sản phẩm id: " + id +" thành công!");

                } else {
                    html = htmlErrorToast("Khóa sản phẩm id: " + id +" thất bại!");
                }
                resp.getWriter().write(html);
                break;
            }
            case "ACTIVE": {
                System.out.println("active product");
                int id = Integer.parseInt(req.getParameter("id"));
                int re = ProductUnitDAO.getInstance().activeProduct(id);
                String html="";
                if(re==1) {
                    html = htmlSuccessToast("Mở khóa sản phẩm id: " + id +" thành công!");

                } else {
                    html = htmlErrorToast("Mở khóa sản phẩm id: " + id +" thất bại!");
                }
                resp.getWriter().write(html);
                break;
            }
            case "DELETE": {
                System.out.println("delete product");
                int id = Integer.parseInt(req.getParameter("id"));
                int re = ProductUnitDAO.getInstance().deleteProduct(id);
                String html="";
                if(re==1) {
                    System.out.println("update thanh cong");
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminmenu?action=adminproduct&message=deleteSuccess_"+id);
                    dispatcher.forward(req, resp);
//                    resp.sendRedirect("/adminmenu?action=adminproduct&message=updateSuccess_"+id);
                }
                break;
            }
            case "SHOWDETAIL": {
                int id = Integer.parseInt(req.getParameter("id"));
                ArrayList<ProductDetail> details = ProductUnitDAO.getInstance().selectDetails(id);
                String html = renderDetails(details) ;
                System.out.println("html:" + html);
                resp.getWriter().write(html);
                break;
            }
            case "UPDATEDETAIL": {
                int id = Integer.parseInt(req.getParameter("id"));
                String color = req.getParameter("color");
                int ram = Integer.parseInt(req.getParameter("ram"));
                int rom = Integer.parseInt(req.getParameter("rom"));
                double price = Double.parseDouble(req.getParameter("price"));
                int qty = Integer.parseInt(req.getParameter("qty"));
                int stt= Integer.parseInt(req.getParameter("stt"));
                ProductDetail detail = new ProductDetail(id,color,ram,rom,price,qty,1);
                int re = ProductUnitDAO.getInstance().updateDetail(detail);
                if(re==1) detail=ProductUnitDAO.getInstance().selectDetail(id);
                String html = renderDetail(detail,stt);
                resp.getWriter().write(html);
                break;

            }
            case "LOCKDETAIL": {
                System.out.println("lock detail product");
                int id = Integer.parseInt(req.getParameter("id"));
                int re = ProductUnitDAO.getInstance().lockDetail(id);
                String html="";
                if(re==1) {
                    html = htmlSuccessToast("khóa id: " + id +" thành công!");

                } else {
                    html = htmlErrorToast("Khóa id: " + id +" thất bại!");
                }
                resp.getWriter().write(html);
                break;
            }
            case "ACTIVEDETAIL": {
                System.out.println("active detail product");
                int id = Integer.parseInt(req.getParameter("id"));
                int re = ProductUnitDAO.getInstance().activeDetail(id);
                String html="";
                if(re==1) {
                    html = htmlSuccessToast("Mở khóa id: " + id +" thành công!");

                } else {
                    html = htmlErrorToast("Mở khóa id: " + id +" thất bại!");
                }
                resp.getWriter().write(html);
                break;
            }
            case "DELETEDETAIL": {
                System.out.println("delete detail product");
                int id = Integer.parseInt(req.getParameter("id"));
                int re = ProductUnitDAO.getInstance().deleteDetail(id);
                String html="";
                if(re==1) {
                    System.out.println("update thanh cong");
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminmenu?action=adminproduct&message=deleteSuccess_"+id);
                    dispatcher.forward(req, resp);
//                    resp.sendRedirect("/adminmenu?action=adminproduct&message=updateSuccess_"+id);
                }
                break;
            }
            case "ADD": {
                System.out.println("admin product : add product");

                String name = req.getParameter("name");
                int brandID = Integer.parseInt(req.getParameter("brandID"));
                int cateID = Integer.parseInt(req.getParameter("cateID"));
                String firstSale = req.getParameter("firstSale").replace(".", "");
                String config = req.getParameter("config");
                int prominence = Integer.parseInt(req.getParameter("prominence"));
                String version = req.getParameter("version");
                String des = req.getParameter("des");
                String imgUrls = req.getParameter("imgUrls");
                Brand brand = new Brand(brandID);
                ArrayList<String> imgUrlList = new ArrayList<>();
                String versions = req.getParameter("versions");

                ArrayList<ProductDetail> details = new ArrayList<>();
                JsonObject root = JsonParser.parseString(versions).getAsJsonObject();
                JsonArray versionArr = root.getAsJsonArray("versions");
                for (JsonElement element : versionArr) {
                    JsonObject versionItem = element.getAsJsonObject();
                    String color = versionItem.get("color").getAsString();
                    int ram = versionItem.get("ram").getAsInt();
                    int rom = versionItem.get("rom").getAsInt();
                    double price = versionItem.get("price").getAsDouble();
                    int qty = versionItem.get("qty").getAsInt();
                    details.add(new ProductDetail(color,ram,rom,price,qty));
                }


                if(imgUrls!=null || !imgUrls.isEmpty()) {
                    String temps[] = imgUrls.split("==");
                    for(String s : temps) {
                        imgUrlList.add(s);
                    }

                }
                // upload img
                String uploadThumbnailDir = getServletContext().getRealPath("/") + Constant.THUMBNAIL_DIR;
                String uploadImagesDir = getServletContext().getRealPath("/") + Constant.PRODUCT_DETAIL_IMG_DIR;

                File thumbnailDir = new File(uploadThumbnailDir);
                if (!thumbnailDir.exists()) {
                    thumbnailDir.mkdirs();  // Create the thumbnails directory
                }
                File imagesDir = new File(uploadImagesDir);
                if (!imagesDir.exists()) {
                    imagesDir.mkdirs();  // Create the images directory
                }
                Part thumbnailPart = req.getPart("thumbnail"); // For the thumbnail (single file)

                String thumbnailFileName="";

                // For the images (multiple files)
                // Handle the thumbnail upload
                if (thumbnailPart != null && thumbnailPart.getSize() > 0) {
                    thumbnailFileName = getFileName(thumbnailPart);
                    String thumbnailPath = uploadThumbnailDir + File.separator + thumbnailFileName;
                    File file = new File(thumbnailPath);
                    if (!file.exists()) thumbnailPart.write(thumbnailPath);
                    // Write the file to the server
//                    ProductUnitDAO.getInstance().updateThumbnail(id,thumbnailFileName);
                }
                // Handle the image upload


                Collection<Part> parts = req.getParts();
                for (Part part : parts) {
                    if (part.getName().equals("imgs") && part.getSize() > 0) {
                        String imageFileName = getFileName(part);
                        String imagePath = uploadImagesDir + File.separator + imageFileName;
                        File file = new File(imagePath);
                        if (!file.exists()) part.write(imagePath);
                        // Write the image file to the server
                    }
                }

                ProductUnit product = new ProductUnit(name, version, brand, cateID, config, thumbnailFileName, firstSale, des, prominence);
                int id = ProductUnitDAO.getInstance().insertProduct(product);
                if(id!=0) {
                    ProductUnitDAO.getInstance().insertDetails(id,details);
                    if(!imgUrlList.isEmpty()) ProductUnitDAO.getInstance().updateImgs(id,imgUrlList);

                }
                System.out.println("add product thanh cong");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminmenu?action=adminproduct&message=addSuccess_"+id);
                dispatcher.forward(req, resp);

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

    public String renderUpdateForm(ProductUnit p,ArrayList<Brand> brands, ArrayList<Image> imgs) {
        String re="";
//        re = "                    <form action=\"adminproduct\" method=\"POST\" id=\"updateInfoForm\" enctype=\"multipart/form-data\">\n" +
        re =
                "                        <h4 class=\"confirm-content\" style=\"text-align: center\">Cập nhật thông tin sản phẩm</h4>\n" +
                        "                        <div class=\"flex-roww\" style=\"justify-content: space-between; margin-top: 10px;\">\n" +
                        "                            <div class=\"form-group grid-col-4\">\n" +
                        "                                <label for=\"name\">Tên</label>\n" +
                        "                                <input type=\"text\" class=\"form-control\" id=\"name\" name=\"name\" value=\""+p.getName()+"\" aria-describedby=\"emailHelp\" placeholder=\"Nhập tên sản phẩm\" required>\n" +
                        "                            </div>\n" +
                        "                            <div class=\"form-group flex-roww grid-col-4\">\n" +
                        "                                <label for=\"id\">ID:</label>\n" +
                        "                                <input type=\"text\" class=\"form-control\" id=\"id\" name=\"id\" aria-describedby=\"emailHelp\" placeholder=\"ID\" value=\""+p.getProductID()+"\" readonly>\n" +
                        "                            </div>\n" +
                        "                        </div>\n" +
                        "                        <div class=\"flex-roww\" style=\"justify-content: space-between; margin-top: 10px;\">\n" +
                        "                        <div class=\"form-group grid-col-4\">\n" +
                        "                            <label for=\"version\">Phiên bản</label>\n" +
                        "                            <input type=\"text\" class=\"form-control\" id=\"version\" name=\"version\" value=\""+p.getVersion()+"\" aria-describedby=\"emailHelp\" placeholder=\"Nhập phiên bản\" required>\n" +
                        "                        </div>\n" +
                        "                            <div class=\"form-group flex-roww grid-col-4\">\n" +

                        "                            </div>\n" +
                        "                        </div>\n" +

                        "                        <div class=\"flex-roww\" style=\"justify-content: space-between; margin-top: 15px;\">\n" +
                        "                            <div class=\"form-group grid-col-4\">\n" +
                        "                                <label>Thương hiệu</label>\n" +
                        getBrandSelect(p.brand.id,brands) +
                        "                            </div>\n" +
                        "                            <div class=\"form-group grid-col-4\">\n" +
                        "                                <label>Phân loại</label>\n" +
                        getCateSelect(p.cateID)+
                        "                            </div>\n" +
                        "                        </div>\n" +
                        "                        <div class=\"flex-roww\" style=\"justify-content: space-between; margin-top: 15px;\">\n" +
                        "                            <div class=\"form-group grid-col-4\">\n" +
                        "                                <label for=\"saleDate\">Ngày mở bán</label>\n" +
                        "                                <input type=\"date\" class=\"form-control\" id=\"saleDate\" name=\"saleDate\" value=\""+p.getSaleDate()+"\" placeholder=\"Nhập ngày mở bán\" required>\n" +
                        "                            </div>\n" +
                        "                            <div class=\"form-group grid-col-4\">\n" +
                        "                                <label for=\"initial-price\">Giá mở bán</label>\n" +
                        "                                <input type=\"text\" class=\"form-control\" id=\"initial-price\" name=\"initialPrice\" value=\""+p.getInitialPrice()+"\" placeholder=\"Nhập giá mở bán\" required>\n" +
                        "                            </div>\n" +
                        "                            <input type=\"text\" name=\"firstSale\"  value='"+p.firstSale+"' hidden>\n" +
                        "                        </div>\n" +
                        "                        <div class=\"flex-roww\" style=\"margin-top: 15px;\" >\n" +
                        "                            <div class=\"form-group grid-col-4\">\n" +
                        "                                <label>Cấu hình</label>\n" +
                        "                                <table id=\"configTable\" class=\"table\">\n" +
                        "                                    <thead>\n" +
                        "                                    <tr>\n" +
                        "                                        <th>Tên</th>\n" +
                        "                                        <th>Giá trị</th>\n" +
                        "                                    </tr>\n" +
                        "                                    </thead>\n" +
                        "                                    <tbody class=\"group\">\n" +
                        getConfigurationTable(p.config)+
                        "                                    </tbody>\n" +
                        "                                </table>\n" +
                        "                                <input type=\"text\" name=\"config\" hidden>\n" +
                        "                            </div>\n" +
                        "                            <div class=\"form-group grid-col-4\">\n" +
                        "                            </div>\n" +
                        "                        </div>\n" +
                        "                        <div class=\"flex-roww\" style=\"margin-top: 15px;\" >\n" +
                        "                            <div class=\"form-group grid-col-4\">\n" +
                        "                                <label>Nổi bật</label>\n" +
                        "                                <input type=\"text\" name=\"feature\" value=\""+getFeatureValue(p.config)+"\" placeholder=\"Nhập thuộc tính nổi bật\">\n" +
                        "                            </div>\n" +
                        "                            <div class=\"form-group grid-col-4\">\n" +
                        "                            </div>\n" +
                        "                        </div>\n" +
                        "                        <div class=\"flex-roww\" style=\"margin-top: 15px;\" >\n" +
                        "                            <div class=\"form-group grid-col-4\">\n" +
                        "                                <label for=\"prominence\">Độ nổi bật</label>\n" +
                        "                                <input type=\"number\" class=\"form-control\" id=\"prominence\" name=\"prominence\" value=\""+p.prominence+"\" placeholder=\"Nhập độ nổi bật\" required>\n" +
                        "                            </div>\n" +
                        "                            <div class=\"form-group grid-col-4\">\n" +
                        "                            </div>\n" +
                        "\n" +
                        "                        </div>\n" +
                        "                        <div class=\"flex-roww\" style=\"margin-top: 20px;\" >\n" +
                        "                            <div class=\"form-group grid-col-4\">\n" +
                        "                                <p>Ảnh thumbnail</p>\n" +
                        "                                <input id=\"thumbnailInput\" type=\"file\" name=\"thumbnail\" accept=\".jpg, .jpeg, .png\"  onchange=\"previewImage()\" />\n" +
                        "                                <div class=\"thumbnail-img-container grid-col-6\" style=\"margin-top: 15px;\">\n" +
                        "                                    <img src=\"./assets/img/thumbnail/"    +p.thumbnail+"\" alt=\"\" id=\"thumbnailPreview\" style=\"width:100%\">\n" +
//                "                                     <input type=\"text\" value=\""+p.thumbnail+"\" name=\"thumbnailInput\">\n\n" +
                        "                                </div>\n" +
                        "                            </div>\n" +
                        "                            <div class=\"form-group grid-col-4\">\n" +
                        "                            </div>\n" +

                        "\n" +
                        "                        </div>\n" +
                        "                        <div class=\"flex-roww\" style=\"margin-top: 20px;\" >\n" +
                        "                            <div class=\"form-group\">\n" +
                        "                                <p>Ảnh chi tiết</p>\n" +
                        "                                <!--                new -->\n" +
                        "                                <div class=\"upload__box group\">\n" +
                        "                                    <div class=\"upload__btn-box\">\n" +
                        "                                        <label class=\"upload__btn\">\n" +
                        "                                            <input type=\"file\" multiple data-max_length=\"20\" name=\"imgs\" class=\"upload__inputfile\" accept=\".jpg, .jpeg, .png\" onchange=\"previewImages(event);\">\n" +
                        "                                        </label>\n" +
                        "                                    </div>\n" +
                        "                                    <div class=\"upload__img-wrap\">" +
                        getImgs(imgs) +
                        "                                   </div>\n" +
                        "                                   <input type=\"text\" name=\"imgUrls\" value=\""+getImgUrls(imgs)+"\" hidden>\n" +
                        "                                </div>\n" +

                        "                            </div>\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "                        </div>\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "                        <p>Mô tả</p>\n" +
                        "                        <div class=\"toolbar\">\n" +
                        "                            <button onclick=\"document.execCommand('bold')\"><b>B</b></button>\n" +
                        "                            <button onclick=\"document.execCommand('italic')\"><i>I</i></button>\n" +
                        "                            <button onclick=\"document.execCommand('underline')\"><u>U</u></button>\n" +
                        "                        </div>\n" +
                        "\n" +
                        "                        <textarea contenteditable=\"true\" id=\"editor\" class=\"editor\" name=\"description\" placeholder=\"Bắt đầu soạn thảo văn bản ở đây...\"></textarea>\n" +
                        "                        <br>\n" +
                        "                        <div class=\"flex-roww\" style=\"margin-top:20px; justify-content: space-around\">\n" +
                        "                            <input type=\"text\" name=\"action\" value=\"update\" hidden>\n" +
                        "                            <button class=\"btn  btn-fourth btn-cancel\" type=\"button\" onclick=\"closeModal(event);\">Hủy</button>\n" +
                        "                            <button class=\"btn btn-primary btn-confirm\" onclick=\"\" type=\"submit\">Lưu</button>\n" +
                        "                        </div>\n";
//                "                    </form>\n";
        return re;
    }
    public String getFileName(Part part) {
        String contentDisposition = part.getHeader("Content-Disposition");
        for (String content : contentDisposition.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return null;
    }


    public String getConfigurationTable(String config) {
        String re="";
        JsonObject jsonObject = JsonParser.parseString(config).getAsJsonObject();
        for (String key : jsonObject.keySet()) {
            JsonElement value = jsonObject.get(key);
            if(value.isJsonArray()) continue;
            re+=    "<tr>\n" +
                    "    <td><input type=\"text\" value=\""+key+"\" onkeydown=\"handleKeyDown(event, 1)\"></td>\n" +
                    "    <td><input type=\"text\" value=\""+value.getAsString()+"\" onkeydown=\"handleKeyDown(event, 2)\"></td>\n" +
                    "</tr>\n";

        }
        return re;
    }

    public String getBrandSelect(int selected,ArrayList<Brand> brands) {
        String re = "";
        re = "<select name=\"brandID\" data-default=\""+selected+"\">\n";
        for(Brand b : brands) {
            re+= "<option value=\""+b.getId()+"\" "+(b.getId()==selected?"selected":"") +">"+b.getName()+"</option>\n";
        }
        re+="</select>\n";
        return re;
    }

    public String getCateSelect(int selected) {
        String re = "";
        re = "<select name=\"cateID\" data-default=\""+selected+"\">\n";
        for(int i=1;i<=3;i++) {
            re+= "<option value=\""+i+"\" "+(i==selected?"selected":"") +">"+Constant.getCategoryName(i)+"</option>\n";
        }
        re+="</select>\n";
        return re;
    }

    public String getFeatureValue(String config) {
        String re="";
        JsonElement jsonElement = JsonParser.parseString(config);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray features = jsonObject.getAsJsonArray("features");
        for(JsonElement f : features) {
            re+=f.getAsString()+",";
        }
        re= re.substring(0, re.length()-1);
        return re;

    }

    public String getImgs(ArrayList<Image> images) {
        String re="";
        for(Image image : images) {
            re+="<div class=\"upload__img-box\">\n" +
                    "<div style=\"background-image: url('./assets/img/product/"+image.getUrl()+"')\"  data-file=\""+image.getUrl()+"\" class=\"img-bg\">\n" +
                        "<div class=\"upload__img-close\" onclick=\"removeImg(event)\"></div>\n" +
                    "</div>\n" +
               "</div>";
        }
        return  re;

    }

    public String getImgUrls(ArrayList<Image> images) {
        StringBuilder temp = new StringBuilder();

        for(Image image : images) {
            temp.append(image.getUrl()+"==");
        }
        String re="";
        if(temp.toString().length()>4) {
            re= temp.toString();
            re=re.substring(0, re.length()-2);
        }
        return  re;

    }

    public String htmlSuccessToast(String message) {
        return "<script>showSuccessToast('"+message+"');</script>";
    }

    public String htmlErrorToast(String message) {
        return "<script>showErrorToast('"+message+"');</script>";
    }

    public String renderDetails(ArrayList<ProductDetail> details) {
        StringBuilder re=new StringBuilder();
        int stt=1;
        for(ProductDetail d: details) {
            re.append(" <tr class=\"group "+(d.isActive()?"":"locked")+"\" id=\"detail"+d.getId()+"\">\n" +
                    "            <th scope=\"row\" class=\"grid-col-0_5 text-center\" >\n" +
                    "                <span class=\"stt\">"+stt+"</span>\n" +
                    "            </th>\n" +
                    "            <td class=\"grid-col-0_5 text-center\">\n" +
                    "                <span class=\"id\">"+d.getId()+"</span>\n" +
                    "            </td>\n" +
                    "            <td class=\"grid-col-1\">\n" +
                    "                <input class=\"info-input\" type=\"text\" name=\"color\" value=\""+d.getColor()+"\" readonly>\n" +
                    "            </td>\n" +
                    "            <td class=\"grid-col-1 text-center\">\n" +
                    "                <input class=\"info-input text-center\" type=\"number\" name=\"ram\" value=\""+d.getRam()+"\" readonly>\n" +
                    "            </td>\n" +
                    "            <td class=\"grid-col-1 text-center\">\n" +
                    "                <input class=\"info-input text-center\" type=\"number\" name=\"rom\" value=\""+d.getRom()+"\" readonly>\n" +
                    "            </td>\n" +
                    "            <td class=\"grid-col-1 text-center\">\n" +
                    "                <input class=\"info-input text-center\" type=\"number\" name=\"price\" value=\""+d.getPrice()+"\" readonly>\n" +
                    "            </td>\n" +
                    "            <td class=\"grid-col-1\">\n" +
                    "                <input class=\"info-input text-center\" type=\"number\" name=\"qty\" value=\""+d.getQty()+"\" readonly>\n" +
                    "            </td>\n" +
                    "            <td class=\"grid-col-1\">\n" +
                    "                <select name=\"action\" onchange=\"handleChange(event);\" data-default=\"none\">\n" +
                    "                    <option value=\"none\">...</option>\n" +
                    "                    <option value=\"updatedetail\">Cập nhật</option>\n" +
                    "                    <option class=\"lock-option\" value=\"lockdetail\">Khóa</option>\n" +
                    "                    <option class=\"unlock-option\" value=\"unlockdetail\">Mở khóa</option>\n" +
                    "                    <option value=\"deletedetail\">Xóa</option>\n" +
                    "                </select>\n" +
                    "                <button class=\"update-detail-btn\" onclick=\"updateDetail(event);\"><i class=\"bi bi-floppy\"></i></button>\n" +
                    "\n" +
                    "            </td>\n" +
                    "\n" +
                    "        </tr>");
            stt++;
        }

        return re.toString();
    }

    public String renderDetail(ProductDetail d,int stt) {
        String re="";
            re=" <tr class=\"group "+(d.isActive()?"":"locked")+"\" id=\"detail"+d.getId()+"\">\n" +
                    "            <th scope=\"row\" class=\"grid-col-0_5 text-center\" >\n" +
                    "                <span class=\"stt\">"+stt+"</span>\n" +
                    "            </th>\n" +
                    "            <td class=\"grid-col-0_5 text-center\">\n" +
                    "                <span class=\"id\">"+d.getId()+"</span>\n" +
                    "            </td>\n" +
                    "            <td class=\"grid-col-1\">\n" +
                    "                <input class=\"info-input\" type=\"text\" name=\"color\" value=\""+d.getColor()+"\" readonly>\n" +
                    "            </td>\n" +
                    "            <td class=\"grid-col-1 text-center\">\n" +
                    "                <input class=\"info-input text-center\" type=\"number\" name=\"ram\" value=\""+d.getRam()+"\" readonly>\n" +
                    "            </td>\n" +
                    "            <td class=\"grid-col-1 text-center\">\n" +
                    "                <input class=\"info-input text-center\" type=\"number\" name=\"rom\" value=\""+d.getRom()+"\" readonly>\n" +
                    "            </td>\n" +
                    "            <td class=\"grid-col-1 text-center\">\n" +
                    "                <input class=\"info-input text-center\" type=\"number\" name=\"price\" value=\""+d.getPrice()+"\" readonly>\n" +
                    "            </td>\n" +
                    "            <td class=\"grid-col-1\">\n" +
                    "                <input class=\"info-input text-center\" type=\"number\" name=\"qty\" value=\""+d.getQty()+"\" readonly>\n" +
                    "            </td>\n" +
                    "            <td class=\"grid-col-1\">\n" +
                    "                <select name=\"action\" onchange=\"handleChange(event);\" data-default=\"none\">\n" +
                    "                    <option value=\"none\">...</option>\n" +
                    "                    <option value=\"updatedetail\">Cập nhật</option>\n" +
                    "                    <option class=\"lock-option\" value=\"lockdetail\">Khóa</option>\n" +
                    "                    <option class=\"unlock-option\" value=\"unlockdetail\">Mở khóa</option>\n" +
                    "                    <option value=\"deletedetail\">Xóa</option>\n" +
                    "                </select>\n" +
                    "                <button class=\"update-detail-btn\" onclick=\"updateDetail(event);\"><i class=\"bi bi-floppy\"></i></button>\n" +
                    "\n" +
                    "            </td>\n" +
                    "\n" +
                    "        </tr>";
        return re;
    }



}
