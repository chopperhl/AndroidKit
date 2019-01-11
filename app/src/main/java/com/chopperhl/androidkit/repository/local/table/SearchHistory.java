package com.chopperhl.androidkit.repository.local.table;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * Description:
 * Author chopperhl
 * Date 11/3/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
@Table("search_history")
public class SearchHistory implements Serializable {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    public long id;
    public String keyWords;
    public long createTime;
    public long updateTime;
}
