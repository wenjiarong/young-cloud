package org.springyoung.core.response;

import java.io.Serializable;

public interface IResultCode extends Serializable {

    String getMessage();

    int getCode();

}