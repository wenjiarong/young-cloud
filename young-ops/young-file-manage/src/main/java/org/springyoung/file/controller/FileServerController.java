package org.springyoung.file.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springyoung.core.boot.ctrl.YoungController;
import org.springyoung.core.mp.support.Condition;
import org.springyoung.core.mp.support.Query;
import org.springyoung.core.tool.api.R;
import org.springyoung.core.tool.utils.Func;
import org.springyoung.file.entity.FileServer;
import org.springyoung.file.service.IFileServerService;
import org.springyoung.file.vo.FileServerVO;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文件服务器信息表 控制器
 */
@RestController
@AllArgsConstructor
@RequestMapping("/fileserver")
@Api(value = "文件服务器信息表", tags = "文件服务器信息表接口")
public class FileServerController extends YoungController {

    private final IFileServerService fileServerService;

    public final static String HTTP_SERVER_IP_ID = "1336946487971840002";

    /**
     * 分页 文件服务器信息表
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "分页", notes = "传入fileServer")
    public R<IPage<FileServer>> list(@ApiIgnore @RequestParam Map<String, Object> fileServer, Query query) {
        IPage<FileServer> pages = fileServerService.page(Condition.getPage(query));
        return R.data(pages);
    }

    /**
     * 新增或修改 文件服务器信息表
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "新增或修改", notes = "传入fileServer")
    public R submit(@Valid @RequestBody FileServer fileServer) {
        return R.status(fileServerService.saveOrUpdate(fileServer));
    }


    /**
     * 删除 文件服务器信息表
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "逻辑删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(fileServerService.removeByIds(Func.toLongList(ids)));
    }

    /**
     * 文件服务器信息表
     */
    @GetMapping("/listNoPage")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "不分页", notes = "")
    public R listNoPage() {
        List<FileServerVO> list = fileServerService.list()
                .stream()
                .map(e -> {
                    FileServerVO vo = new FileServerVO();
                    vo.setId(e.getId());
                    vo.setName(e.getName());
                    vo.setProtocol(e.getProtocol());
                    return vo;
                }).collect(Collectors.toList());
        return R.data(list);
    }

}
