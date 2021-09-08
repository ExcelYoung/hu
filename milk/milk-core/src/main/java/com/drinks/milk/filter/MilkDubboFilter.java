package com.drinks.milk.filter;


import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.fastjson.JSON;
import com.drinks.milk.enums.ErrorEnum;
import com.drinks.milk.exception.BaseException;
import com.drinks.milk.exception.BusException;
import com.drinks.milk.enums.HttpStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * <p>Title: ChaosDubboFilter</p>
 *
 * @author hupenglong
 * @date 2020-08-19 14:22
 */
@Slf4j
@Activate(group = Constants.PROVIDER)
public class MilkDubboFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcResult rpcResult = null;
        long start = System.currentTimeMillis();
        Object[] arguments = invocation.getArguments();
        if (arguments.length > 0) {
            log.info("{}#{}#before:{}", invoker.getInterface(), invocation.getMethodName(), JSON.toJSONString(arguments[0]));
        }
        try {
            rpcResult = (RpcResult) invoker.invoke(invocation);
            if (rpcResult.hasException()) {
                if (rpcResult.getException() instanceof BusException) {
                    rpcResult = buildBusExceptionResult(rpcResult.getException());
                }else if (rpcResult.getException() instanceof BaseException) {
                    rpcResult = buildBaseExceptionResult(rpcResult.getException());
                } else {
                    log.info("{}#{}#exception:{}", invoker.getInterface(), invocation.getMethodName(), rpcResult.getException().getMessage());
                    rpcResult = buildExceptionResult();
                }
            }
        } catch (Exception e) {
            log.error("远程接口执行失败", e);
            rpcResult = buildExceptionResult();
        }
        log.info("{}#{}#time:{}ms#after:{}", invoker.getInterface(), invocation.getMethodName(), System.currentTimeMillis() - start, rpcResult == null ? "" : JSON.toJSONString(rpcResult.getValue()));
        return rpcResult;
    }

    /**
     * 构建业务异常返回结果
     *
     * @param exception
     * @return
     */
    private RpcResult buildBusExceptionResult(Throwable exception) {
        BusException e = (BusException) exception;
        RpcResult rpcResult = new RpcResult();
        if (e.getCode() == HttpStatusCode.SUCCESS.getCode()) {
            rpcResult.setValue(com.drinks.milk.entity.Result.newSuccess());
        } else {
            if (StringUtils.isEmpty(e.getMsg())) {
                rpcResult.setValue(com.drinks.milk.entity.Result.newFailure(HttpStatusCode.getName(e.getCode())));
            } else {
                rpcResult.setValue(com.drinks.milk.entity.Result.newFailure(e.getMsg()));
            }
        }
        return rpcResult;
    }

    /**
     * 构建base异常返回结果
     *
     * @param exception
     * @return
     */
    private RpcResult buildBaseExceptionResult(Throwable exception) {
        BaseException e = (BaseException) exception;
        RpcResult rpcResult = new RpcResult();
        if (StringUtils.isEmpty(e.getMessage())) {
            rpcResult.setValue(com.drinks.milk.entity.Result.newFailure(ErrorEnum.EXCEPTION));
        } else {
            rpcResult.setValue(com.drinks.milk.entity.Result.newFailure(e.getMessage()));
        }
        return rpcResult;
    }

    /**
     * 构建未知异常返回结果
     *
     * @return
     */
    private RpcResult buildExceptionResult() {
        RpcResult rpcResult = new RpcResult();
        rpcResult.setValue(com.drinks.milk.entity.Result.newFailure(ErrorEnum.EXCEPTION));
        return rpcResult;
    }
}
