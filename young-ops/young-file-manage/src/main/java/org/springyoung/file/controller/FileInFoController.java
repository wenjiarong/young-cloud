package org.springyoung.file.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springyoung.core.boot.ctrl.YoungController;
import org.springyoung.core.constant.IsDeletedEnum;
import org.springyoung.core.mp.support.Condition;
import org.springyoung.core.mp.support.Query;
import org.springyoung.core.secure.YoungUser;
import org.springyoung.core.tool.api.R;
import org.springyoung.core.tool.utils.Func;
import org.springyoung.core.tool.utils.ObjectUtil;
import org.springyoung.file.config.MyProperties;
import org.springyoung.file.entity.FileInfo;
import org.springyoung.file.service.IFileInfoService;
import org.springyoung.file.utils.FileUtil;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 文件信息表 控制器
 */
@RestController
@AllArgsConstructor
@RequestMapping("/fileInfo")
@Api(value = "文件信息表", tags = "文件信息表接口")
public class FileInFoController extends YoungController {

    private final IFileInfoService fileInfoService;
    private final MyProperties properties;

    /**
     * 分页 文件信息表
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "分页", notes = "传入fileInfo")
    public R<IPage<FileInfo>> list(@ApiIgnore @RequestParam Map<String, Object> fileInfo, Query query) {
        return R.data(fileInfoService.page(Condition.getPage(query)));
    }

    /**
     * 删除 文件信息表
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "逻辑删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(fileInfoService.removeByIds(Func.toLongList(ids)));
    }

    /**
     * 上传文件
     *
     * @param file 文件
     */
    @PostMapping("/put-file")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "上传文件", notes = "")
    public R putFile(@RequestParam String fileType, @RequestParam MultipartFile file, String tenantId) throws IOException {
        YoungUser user = getUser();
        if (StringUtils.isEmpty(tenantId)) {
            tenantId = user.getTenantId();
        }

        String uploadPath = properties.getDir() + tenantId + FileUtil.addHead(fileType);

        File dir = new File(uploadPath);
        FileUtil.initPath(dir);

        String fileName = file.getOriginalFilename();
        int fileSize = (int) file.getSize();

        // 文件保存
        File f = new File(uploadPath, fileName);
        if (f.exists()) {
            f.delete();
        }
        file.transferTo(f);

        // 返回上传文件的访问路径
        String filePath = f.getAbsolutePath();

        QueryWrapper<FileInfo> qw = new QueryWrapper<>();
        LambdaQueryWrapper<FileInfo> eq = qw.lambda()
                .eq(FileInfo::getFilePath, filePath)
                .eq(FileInfo::getIsDeleted, IsDeletedEnum.no.getStatus());
        FileInfo fileInfo = fileInfoService.getOne(eq);

        boolean isSuccess;

        if (ObjectUtil.isEmpty(fileInfo)) {
            fileInfo = new FileInfo();
            //此处服务器ID使用默认0
            fileInfo.setFileServerId(Long.valueOf(FileServerController.HTTP_SERVER_IP_ID));
            fileInfo.setFilePath(filePath);
            fileInfo.setFileName(fileName);
            fileInfo.setFileSize(fileSize);
            fileInfo.setTenantId(tenantId);
            isSuccess = fileInfoService.save(fileInfo);
        } else {
            fileInfo.setFileServerId(Long.valueOf(FileServerController.HTTP_SERVER_IP_ID));
            fileInfo.setFilePath(filePath);
            fileInfo.setFileName(fileName);
            fileInfo.setFileSize(fileSize);
            isSuccess = fileInfoService.updateById(fileInfo);
        }
        return R.data(isSuccess);
    }

    /**
     * 下载文件
     */
    @PostMapping("/get-file")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "下载文件", notes = "")
    public void getFile(@RequestParam String fileName, @RequestParam String filePath, HttpServletResponse response) throws Exception {
        if (StringUtils.isNotEmpty(fileName)) {
            //设置文件路径
            File file = new File(filePath);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));// 设置文件名

                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    bis.close();
                    fis.close();
                }
            }
        }
    }

}
