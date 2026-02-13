package com.visualpathit.account.controller;

import com.visualpathit.account.model.User;
import com.visualpathit.account.service.UserService;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller for uploading user profile images.
 */
@Controller
public class FileUploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);

    private static final String VIEW_UPLOAD = "upload";
    private static final String TMP_DIR_NAME = "tmpFiles";
    private static final String FILE_EXT = ".png";

    private final UserService userService;

    public FileUploadController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Upload single file view.
     *
     * @param model model
     * @return upload view name
     */
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String upload(final Model model) {
        return VIEW_UPLOAD;
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public @ResponseBody String uploadFileHandler(
            @RequestParam("name") final String name,
            @RequestParam("userName") final String userName,
            @RequestParam("file") final MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return "You failed to upload " + name + FILE_EXT + " because the file was empty.";
        }

        try {
            final byte[] bytes = file.getBytes();
            final String rootPath = System.getProperty("catalina.home");

            final File dir = new File(rootPath + File.separator + TMP_DIR_NAME);
            if (!dir.exists() && !dir.mkdirs()) {
                return "You failed to upload " + name + FILE_EXT + " => could not create directory.";
            }

            final File serverFile = new File(dir.getAbsolutePath() + File.separator + name + FILE_EXT);

            final User user = userService.findByUsername(userName);
            if (user != null) {
                user.setProfileImg(name + FILE_EXT);
                user.setProfileImgPath(serverFile.getAbsolutePath());
                userService.save(user);
            }

            try (BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(serverFile))) {
                stream.write(bytes);
            }

            LOGGER.info("Server file location={}", serverFile.getAbsolutePath());
            return "You successfully uploaded file=" + name + FILE_EXT;

        } catch (IOException ex) {
            LOGGER.error("Failed to upload file name={} userName={}", name, userName, ex);
            return "You failed to upload " + name + FILE_EXT + " => " + ex.getMessage();
        }
    }
} 

