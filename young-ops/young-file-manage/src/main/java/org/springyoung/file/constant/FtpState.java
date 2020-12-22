package org.springyoung.file.constant;

public interface FtpState {

	int FS_WAIT_LOGIN = 0;    //等待输入用户名状态
	int FS_WAIT_PASS = 1;    //等待输入密码状态
	int FS_LOGIN = 2;        //已经登陆状态
	int FTYPE_ASCII = 0;
	int FTYPE_IMAGE = 1;
	int FMODE_STREAM = 0;
	int FMODE_COMPRESSED = 1;
	int FSTRU_FILE = 0;
	int FSTRU_PAGE = 1;

}
