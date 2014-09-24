package com.kharkiv.board.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

@MultipartConfig
@WebServlet("/registration")
public class RegistrationController extends AbstractAutowiringServlet {

    private static final long serialVersionUID = 1L;
    
    @Value(value = "${avatar.storage.folder}")
    private String AVATAR_DIR;
    
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // gets absolute path of the web application
        String appPath = req.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String savePath = appPath + File.separator + AVATAR_DIR + File.separator;
        
        // creates the save directory if it does not exists
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        
        String file = "";
        
        for (Part part : req.getParts()) {
            String fileName = extractFileName(part);
            if (StringUtils.isNotBlank(fileName)) {
                file = fileName;
                part.write(savePath + fileName);
            }
        }
        
        System.out.println(req.getParameter("login"));
        System.out.println(req.getParameter("password"));
        
        PrintWriter writer = resp.getWriter();
        writer.write("success; filename = " + file);
    }
    
    /**
     * Extracts file name from HTTP header content-disposition
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
}
