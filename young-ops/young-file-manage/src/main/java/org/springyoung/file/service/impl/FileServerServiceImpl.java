package org.springyoung.file.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springyoung.file.entity.FileServer;
import org.springyoung.file.mapper.FileServerMapper;
import org.springyoung.file.service.IFileServerService;

/**
 * 文件服务器信息表 服务实现类
 */
@Service
public class FileServerServiceImpl extends ServiceImpl<FileServerMapper, FileServer> implements IFileServerService {

}
