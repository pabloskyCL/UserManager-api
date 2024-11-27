package com.apiRest.pabloq.UserManager.Handler;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.serverless.proxy.spring.SpringBootProxyHandlerBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.apiRest.pabloq.UserManager.UserManagerApplication;
import jakarta.ws.rs.core.Application;

public class AsynchronousLambdaHandler implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {
    private SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    public AsynchronousLambdaHandler() throws ContainerInitializationException {
        handler = (SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse>)
                new SpringBootProxyHandlerBuilder()
                        .springBootApplication(UserManagerApplication.class)
                        .asyncInit()
                        .buildAndInitialize();
    }

    @Override
    public AwsProxyResponse handleRequest(AwsProxyRequest awsProxyRequest, Context context) {
        return handler.proxy(awsProxyRequest, context);
    }
}