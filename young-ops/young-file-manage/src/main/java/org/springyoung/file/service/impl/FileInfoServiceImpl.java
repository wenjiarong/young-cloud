package org.springyoung.file.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springyoung.file.entity.FileInfo;
import org.springyoung.file.mapper.FileInfoMapper;
import org.springyoung.file.service.IFileInfoService;

/**
 * 文件信息表 服务实现类
 */
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements IFileInfoService {

}
