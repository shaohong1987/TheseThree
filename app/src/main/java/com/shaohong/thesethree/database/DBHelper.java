package com.shaohong.thesethree.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shaohong on 2017/5/18.
 */

public class DBHelper extends SQLiteOpenHelper {
    //数据库名称
    static final String DB_NAME = "TheseThreeDB";
    static final int VERSION = 1;

    //试卷表
    static final String TABLE_NAME_TEST = "table_test";
    static final String TEST_ID = "id";
    static final String TEST_COL_ID = "test_id";
    static final String TEST_COL_NAME = "test_name";
    static final String TEST_COL_SCROE = "test_score";
    static final String TEST_COL_JIGE = "test_jige_score";

    //试卷数据
    static final String TABLE_NAME_TEST_LIBRARY = "table_test_library";
    static final String TEST_LIBRARY_COL_ID = "test_id";
    static final String TEST_LIBRARY_COL_TEST_ID = "id";
    static final String TEST_LIBRARY_QUESTION_ANLI = "anli";
    static final String TEST_LIBRARY_QUESTION_ANSWER = "answer";
    static final String TEST_LIBRARY_QUESTION_DIFFICULTY = "difficulty";
    static final String TEST_LIBRARY_QUESTION_EXERCISETYPE = "exerciseType";
    static final String TEST_LIBRARY_QUESTION_ISRIGHT = "isright";
    static final String TEST_LIBRARY_QUESTION_ITEMNUM = "itemNum";
    static final String TEST_LIBRARY_QUESTION_LABEL = "label";
    static final String TEST_LIBRARY_QUESTION_PAPERID ="paperid";
    static final String TEST_LIBRARY_QUESTION_PARTID ="partid";
    static final String TEST_LIBRARY_QUESTION_QUESTION ="question";
    static final String TEST_LIBRARY_QUESTION_QUESTIONID ="questionid";
    static final String TEST_LIBRARY_QUESTION_REMARK ="remark";
    static final String TEST_LIBRARY_QUESTION_SCORE ="score";
    static final String TEST_LIBRARY_QUESTION_SECTION ="section";
    static final String TEST_LIBRARY_QUESTION_SEQ ="seq";
    static final String TEST_LIBRARY_QUESTION_USERANSWER ="userAnswer";
    static final String TEST_LIBRARY_QUESTION_OPTION_A = "itemA";
    static final String TEST_LIBRARY_QUESTION_OPTION_B = "itemB";
    static final String TEST_LIBRARY_QUESTION_OPTION_C = "itemC";
    static final String TEST_LIBRARY_QUESTION_OPTION_D = "itemD";
    static final String TEST_LIBRARY_QUESTION_OPTION_E = "itemE";
    static final String TEST_LIBRARY_QUESTION_OPTION_F = "itemF";
    static final String TEST_LIBRARY_QUESTION_OPTION_G = "itemG";
    static final String TEST_LIBRARY_QUESTION_OPTION_H = "itemH";
    static final String TEST_LIBRARY_QUESTION_OPTION_I = "itemI";
    static final String TEST_LIBRARY_QUESTION_OPTION_J = "itemJ";

    //通知
    static final String TABLE_NAME_NOTICE = "table_notice";
    static final String NOTICE_COL_ID = "id";
    static final String NOTICE_COL_TYPE = "type";
    static final String NOTICE_COL_CONTENT = "content";
    static final String NOTICE_COL_EDUCODE = "educode";
    static final String NOTICE_COL_GROUPID = "groupid";
    static final String NOTICE_COL_HOSPITALCODE = "hospitalcode";
    static final String NOTICE_COL_SENDTIME = "sendtime";
    static final String NOTICE_COL_TESTCODE = "testcode";
    static final String NOTICE_COL_SHOW= "isShow";
    static final String NOTICE_COL_STATUS= "status";
    static final String NOTICE_COL_ISVALUED= "isvalued";

    /**
     * 构造方法
     *
     * @param context
     */
    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable_testLibrary = "CREATE TABLE "
                + TABLE_NAME_TEST_LIBRARY + " ("
                + TEST_LIBRARY_COL_ID + " TEXT,"
                + TEST_LIBRARY_COL_TEST_ID + " TEXT,"
                + TEST_LIBRARY_QUESTION_QUESTIONID + " TEXT,"
                + TEST_LIBRARY_QUESTION_ANLI + " TEXT,"
                + TEST_LIBRARY_QUESTION_ANSWER + " TEXT,"
                + TEST_LIBRARY_QUESTION_DIFFICULTY + " TEXT,"
                + TEST_LIBRARY_QUESTION_EXERCISETYPE + " TEXT,"
                + TEST_LIBRARY_QUESTION_ISRIGHT + " TEXT,"
                + TEST_LIBRARY_QUESTION_ITEMNUM + " TEXT,"
                + TEST_LIBRARY_QUESTION_LABEL + " TEXT,"
                + TEST_LIBRARY_QUESTION_PAPERID + " TEXT,"
                + TEST_LIBRARY_QUESTION_PARTID + " TEXT,"
                + TEST_LIBRARY_QUESTION_QUESTION + " TEXT,"
                + TEST_LIBRARY_QUESTION_REMARK + " TEXT,"
                + TEST_LIBRARY_QUESTION_SCORE + " TEXT,"
                + TEST_LIBRARY_QUESTION_SECTION + " TEXT,"
                + TEST_LIBRARY_QUESTION_SEQ + " TEXT,"
                + TEST_LIBRARY_QUESTION_USERANSWER + " TEXT,"
                + TEST_LIBRARY_QUESTION_OPTION_A + " TEXT,"
                + TEST_LIBRARY_QUESTION_OPTION_B + " TEXT,"
                + TEST_LIBRARY_QUESTION_OPTION_C + " TEXT,"
                + TEST_LIBRARY_QUESTION_OPTION_D + " TEXT,"
                + TEST_LIBRARY_QUESTION_OPTION_E + " TEXT,"
                + TEST_LIBRARY_QUESTION_OPTION_F + " TEXT,"
                + TEST_LIBRARY_QUESTION_OPTION_G + " TEXT,"
                + TEST_LIBRARY_QUESTION_OPTION_H + " TEXT,"
                + TEST_LIBRARY_QUESTION_OPTION_I + " TEXT,"
                + TEST_LIBRARY_QUESTION_OPTION_J + " TEXT)";
        db.execSQL(createTable_testLibrary);
        DbUpdateV2(db);
        DbUpdateV3(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i = oldVersion; i < newVersion; i++) {
            switch (i) {
                case 2:
                    DbUpdateV2(db);
                    break;
                case 3:
                    DbUpdateV3(db);
                    break;
                default:
                    break;
            }
        }
    }

    private void DbUpdateV2(SQLiteDatabase db){
        String createTable_notice="CREATE TABLE "
                + TABLE_NAME_NOTICE + " ("
                + NOTICE_COL_ID + " INTEGER,"
                + NOTICE_COL_TYPE + " TEXT,"
                + NOTICE_COL_CONTENT + " TEXT,"
                + NOTICE_COL_EDUCODE + " TEXT,"
                + NOTICE_COL_GROUPID + " TEXT,"
                + NOTICE_COL_HOSPITALCODE + " TEXT,"
                + NOTICE_COL_SENDTIME + " TEXT,"
                + NOTICE_COL_TESTCODE + " TEXT,"
                + NOTICE_COL_STATUS + " TEXT,"
                + NOTICE_COL_ISVALUED + " TEXT,"
                + NOTICE_COL_SHOW + " INTEGER)";
        db.execSQL(createTable_notice);
    }

    private void DbUpdateV3(SQLiteDatabase db) {
        String createTable_test = "CREATE TABLE "
                + TABLE_NAME_TEST + " ("
                + TEST_ID + " INTEGER PRIMARY KEY autoincrement,"
                + TEST_COL_ID + " INTEGER,"
                + TEST_COL_NAME + " TEXT,"
                + TEST_COL_SCROE + " TEXT,"
                + TEST_COL_JIGE + " TEXT)";
        db.execSQL(createTable_test);
    }
}
