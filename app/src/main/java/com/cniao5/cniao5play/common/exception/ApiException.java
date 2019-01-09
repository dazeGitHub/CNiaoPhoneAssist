package com.cniao5.cniao5play.common.exception;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.common.exception
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class ApiException extends BaseException {





    public ApiException(int code, String displayMessage) {
        super(code, displayMessage);
    }
}
