package com.github.caryyu.springjtaatomikos;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

public class AtomikosJtaPlatform extends AbstractJtaPlatform {

    @Override
    protected TransactionManager locateTransactionManager() {
        return new UserTransactionManager();
    }

    @Override
    protected UserTransaction locateUserTransaction() {
        return new UserTransactionImp();
    }
}
