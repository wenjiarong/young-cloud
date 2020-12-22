package org.springyoung.file.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springyoung.core.boot.ctrl.YoungController;
import org.springyoung.core.secure.YoungUser;
import org.springyoung.core.tool.api.R;
import org.springyoung.file.config.MyProperties;
import org.springyoung.file.config.StartService;
import org.springyoung.file.server.TftpServer;
import org.springyoung.file.utils.FileUtil;

import java.io.File;

/**
 * @ClassName TftpController
 * @Description TODO
 * @Author 小温
 * @Date 2020/12/11 17:40
 * @Version 1.0
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/tftp")
@Api(value = "tftp", tags = "tftp")
public class TftpController extends YoungController {

    private MyProperties properties;

    @PostMapping("/switchDir")
    @ApiOperationSupport(order = 1)
    public R switchDir(@RequestParam String dir, String tenantId) {
        if (StringUtils.isEmpty(tenantId)) {
            YoungUser user = getUser();
            tenantId = user.getTenantId();
        }
        TftpServer tftpServer = (TftpServer) StartService.SERVER_MAP.get(StartService.TFTP_SERVER_NAME);
        File file = new File(properties.getDir() + tenantId + FileUtil.addHead(dir));
        tftpServer.setRootDir(file);
        return R.data(true);
    }

    @GetMapping("/getDir")
    @ApiOperationSupport(order = 2)
    public R getDir() {
        TftpServer tftpServer = (TftpServer) StartService.SERVER_MAP.get(StartService.TFTP_SERVER_NAME);
        return R.data(tftpServer.getRootDir().getAbsolutePath());
    }

}
