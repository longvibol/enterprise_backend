package com.pisethjavaschool.platform.r2dbc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
@EnableTransactionManagement
public class PlatformReactiveTransactionManagementConfig implements TransactionManagementConfigurer {

    private final TransactionManager reactiveTransactionManager;

    public PlatformReactiveTransactionManagementConfig(
            @Qualifier("connectionFactoryTransactionManager")
            TransactionManager reactiveTransactionManager
    ) {
        this.reactiveTransactionManager = reactiveTransactionManager;
    }

    @Override
    public TransactionManager annotationDrivenTransactionManager() {
        return reactiveTransactionManager;
    }
}