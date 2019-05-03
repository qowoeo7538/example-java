/**
 * Copyright (c) 2005-2012 springside.org.cn
 */
package org.lucas.exception;

import javax.servlet.http.HttpServletRequest;

/**
 * 关于异常的工具类.
 *
 * @author calvin
 * @version 2013-01-15
 */
public class RequestExceptions {

    /**
     * 在request中获取异常类
     *
     * @param request
     * @return
     */
    public static Throwable getThrowable(HttpServletRequest request) {
        Throwable ex = null;
        if (request.getAttribute("exception") != null) {
            ex = (Throwable) request.getAttribute("exception");
        } else if (request.getAttribute("javax.servlet.error.exception") != null) {
            ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
        }
        return ex;
    }

}
