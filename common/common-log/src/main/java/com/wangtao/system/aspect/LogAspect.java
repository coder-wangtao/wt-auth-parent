package com.wangtao.system.aspect;
import com.alibaba.fastjson.JSON;
import com.wangtao.common.helper.JwtHelper;
import com.wangtao.model.system.SysOperLog;
import com.wangtao.system.annotation.Log;
import com.wangtao.system.service.OperLogService;
import io.jsonwebtoken.JwtHandler;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

@Aspect
@Component
public class LogAspect {
    @Autowired
    OperLogService operLogService;


    //joinPoint 连接点对象 获取目标方法的信息
    //result 目标方法的返回值
//    @AfterReturning(pointcut = "execution(com.wangtao.system.controller.*(..))")
    @AfterReturning(pointcut = "@annotation(controllerLog)",returning = "result")
    public void afterReturning(JoinPoint joinPoint, Log controllerLog, Object result) {
        handleLog(joinPoint,controllerLog,result,null);
    }



    @AfterThrowing(pointcut = "@annotation(controllerLog)",throwing = "e")
    public void afterReturning(JoinPoint joinPoint, Log controllerLog, Throwable e) {
        handleLog(joinPoint,controllerLog,null,e);
    }

    private void handleLog(JoinPoint joinPoint, Log controllerLog, Object result, Throwable e) {
        SysOperLog sysOperLog = new SysOperLog();
        //title
        sysOperLog.setTitle(controllerLog.title());
        sysOperLog.setBusinessType(controllerLog.businessType().name());
        sysOperLog.setOperatorType(controllerLog.operatorType().name());
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        sysOperLog.setMethod(className + "." + methodName + "()");

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String requestMethod = request.getMethod();
        sysOperLog.setRequestMethod(requestMethod);

        String token = request.getHeader("token");
        String username = JwtHelper.getUsername(token);
        sysOperLog.setOperName(username);

        //请求url
        String requestURI = request.getRequestURI();
        sysOperLog.setOperUrl(requestURI);

        //ip地址
        String localAddr = request.getLocalAddr();
        sysOperLog.setOperIp(localAddr);

        //请求参数
        if(controllerLog.isSaveRequestData()){
            setRequestData(joinPoint,sysOperLog);
        }
        //返回参数
        if(controllerLog.isSaveResponseData()){
            sysOperLog.setJsonResult(JSON.toJSONString(result));
        }
        //status
        if(e != null){
            sysOperLog.setStatus(0);
            sysOperLog.setErrorMsg(e.getMessage());
        }else {
            sysOperLog.setStatus(1);
        }

        sysOperLog.setOperTime(new Date());

        operLogService.saveOperLog(sysOperLog);
    }




    /**
     * 设置请求数据的方法
     *
     * @param joinPoint
     * @param sysOperLog
     */
    private void setRequestData(JoinPoint joinPoint, SysOperLog sysOperLog) {
        String requestMethod = sysOperLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            sysOperLog.setOperParam(params);
        }
    }

    /**
     * 参数拼装的方法
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (!StringUtils.isEmpty(o) && !isFilterObject(o)) {
                    try {
                        Object jsonObj = JSON.toJSON(o);
                        params += jsonObj.toString() + " ";
                    } catch (Exception e) {
                    }
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }
}
