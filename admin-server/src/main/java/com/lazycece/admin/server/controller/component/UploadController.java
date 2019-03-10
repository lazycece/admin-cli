package com.lazycece.admin.server.controller.component;


import com.lazycece.admin.common.reponse.ResponseData;
import com.lazycece.admin.common.utils.StringUtils;
import com.lazycece.admin.common.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author lazycece
 */
@RestController
@Slf4j
public class UploadController {

    @Value("${upload-file.file-path}")
    private String filePath;

    @Value("${upload-file.root-url}")
    private String rootUrl;

    /**
     * upload file
     *
     * @param file   file, see ${@link MultipartFile}
     * @param dir    file's dictionary
     * @param attach attach dictionary path
     * @return response
     */
    @PostMapping("/upload/file")
    public ResponseData upload(@RequestParam MultipartFile file, @RequestParam String dir, String attach) {
        try {
            long fileSize = 1024 * 1024 * 2;
            if (file.getSize() > fileSize) {
                return ResponseData.fail("文件超过2M");
            }

            if (StringUtils.isNotBlank(attach)) {
                dir += "/" + attach;
            }
            String originalFileName = file.getOriginalFilename();
            String newFileName = UUIDUtils.uuid() + originalFileName.substring(originalFileName.lastIndexOf(".")).trim();
            String path = dir + "/" + newFileName;

            File uploadFile = new File(filePath + path);
            if (!uploadFile.getParentFile().exists()) {
                uploadFile.getParentFile().mkdirs();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), uploadFile);

            String url = rootUrl + path;
            return ResponseData.success(url);
        } catch (Exception e) {
            log.error("upload file error: {}", e);
            return ResponseData.fail();
        }
    }
}
