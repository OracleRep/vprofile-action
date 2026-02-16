package com.visualpathit.account.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.visualpathit.account.service.UserService;

@Controller
public final class FileUploadController {

    /**
     * User service for retrieving and saving user profile data.
     */
    @Autowired
    private UserService userService;

    /**
     * Logger instance.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(FileUploadController.class);

    /**
     * Displays the upload page.
     *
     * @param model the UI model
     * @return view name for the upload page
     */
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String upload(final Model model) {
        return "upload";
    }

    /**
     * Handles upload of a single file and updates the user profile image fields.
     *
     * @param name the image name (used as filename prefix)
     * @param userName the username of the user being updated
     * @param file the multipart file to upload
     * @return upload result message
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public @ResponseBody String uploadFileHandler(
            @RequestParam("name") final String name,
            @RequestParam("userName") final String userName,
            @RequestParam("file") final MultipartFile file) {

        System.out.println("Called the upload file :::");

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                String rootPath = System.getProperty("catalina.home");
                System.out.println("Path ::::" + rootPath);

                File dir = new File(rootPath + File.separator + "tmpFiles");
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File serverFile = new File(
                        dir.getAbsolutePath()
                                + File.separator
                                + name
                                + ".png"
                );

                com.visualpathit.account.model.User user =
                        userService.findByUsername(userName);

                user.setProfileImg(name + ".png");
                user.setProfileImgPath(serverFile.getAbsolutePath());
                userService.save(user);

                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile)
                );
                stream.write(bytes);
                stream.close();

                LOGGER.info(
                        "Server File Location= " + serverFile.getAbsolutePath()
                );

                return "You successfully uploaded file= " + name + ".png";
            } catch (Exception e) {
                return "You failed to upload " + name + ".png => "
                        + e.getMessage();
            }
        }

        return "You failed to upload " + name + ".png because the file was empty.";
    }
}
