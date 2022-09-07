package com.samcho.user_authentication.interceptors

import lombok.extern.slf4j.Slf4j
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Slf4j
class LogInterceptor : HandlerInterceptor {
    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        val startTime = request.getAttribute("startTime") as Long
        request.removeAttribute("startTime")
        val endTime = System.currentTimeMillis()

        logger().info(
            (("[END]  [" + request.method) + "]   [ URL is:" + request.requestURL.toString()
                    ) + "]  [Execution Time: {} miliseconds]", (endTime - startTime)
        )
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val startTime = System.currentTimeMillis()
        request.setAttribute("startTime", startTime)
        logger().info("url : {}", request.requestURI)
        logger().info(("[START]  [" + request.method) + "] [ URL is: " + request.requestURL.toString())
        return true
    }
}