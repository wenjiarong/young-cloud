package org.springyoung.common.response;

import java.io.Serializable;

public interface IResultCode extends Serializable {

    String getMessage();

    int getCode();

}