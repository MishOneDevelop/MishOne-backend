package com.mishone.mishone.config;

import org.hibernate.community.dialect.SQLiteDialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;

public class SQLiteCustomDialect extends SQLiteDialect {

    public SQLiteCustomDialect() {
        super();
    }

    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new IdentityColumnSupportImpl() {
            @Override
            public boolean supportsInsertSelectIdentity() {
                return true;
            }

            @Override
            public String getIdentitySelectString(String table, String column, int type) {
                return "select last_insert_rowid()"; // para SQLite
            }
        };
    }
}
