package com.alquimia.koua.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.alquimia.koua.db.KouaRecord;
import com.alquimia.koua.db.Category;

import com.alquimia.koua.db.KouaRecordDao;
import com.alquimia.koua.db.CategoryDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig kouaRecordDaoConfig;
    private final DaoConfig categoryDaoConfig;

    private final KouaRecordDao kouaRecordDao;
    private final CategoryDao categoryDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        kouaRecordDaoConfig = daoConfigMap.get(KouaRecordDao.class).clone();
        kouaRecordDaoConfig.initIdentityScope(type);

        categoryDaoConfig = daoConfigMap.get(CategoryDao.class).clone();
        categoryDaoConfig.initIdentityScope(type);

        kouaRecordDao = new KouaRecordDao(kouaRecordDaoConfig, this);
        categoryDao = new CategoryDao(categoryDaoConfig, this);

        registerDao(KouaRecord.class, kouaRecordDao);
        registerDao(Category.class, categoryDao);
    }
    
    public void clear() {
        kouaRecordDaoConfig.getIdentityScope().clear();
        categoryDaoConfig.getIdentityScope().clear();
    }

    public KouaRecordDao getKouaRecordDao() {
        return kouaRecordDao;
    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

}