package com.qzl.criminalintent.db;

/**
 * 描述数据表的内部类
 * Created by Qzl on 2016-09-11.
 */
public class CrimeDbSchema {

    public static final class CrimeTable{
        public static final String NAME = "crime";//表名

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String SUSPECT = "suspect";//联系人字段
        }
    }
}
