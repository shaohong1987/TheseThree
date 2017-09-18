package com.shaohong.thesethree.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.shaohong.thesethree.bean.HistoryListItemObject;
import com.shaohong.thesethree.bean.Paper;
import com.shaohong.thesethree.bean.SimpleExam;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by shaohong on 2017/5/18.
 */

public class DbManager {
    private Context context;
    private SQLiteDatabase database;

    public DbManager(Context context) {
        this.context = context;
    }

    public void openDB() {
        DBHelper dbHelper = new DBHelper(context);
        if (database == null || !database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }
    }

    public void closeDB() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    public long deleteLibraryAllData(int testid) {
        return database.delete(DBHelper.TABLE_NAME_TEST_LIBRARY, "test_id=" + testid, null);
    }

    public long insertLibrary(Paper paper) {
        ContentValues newValues = new ContentValues();
        newValues.put(DBHelper.TEST_LIBRARY_COL_ID, paper.getTestId());
        newValues.put(DBHelper.TEST_LIBRARY_COL_TEST_ID, paper.getId());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_ANLI, paper.getAnli());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_ANSWER, paper.getAnswer());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_DIFFICULTY, paper.getDifficulty());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_EXERCISETYPE, paper.getExerciseType());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_ISRIGHT, paper.getIsright());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_ITEMNUM, paper.getItemNum());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_LABEL, paper.getLabel());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_PAPERID, paper.getPaperid());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_PARTID, paper.getPartid());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_QUESTION, paper.getQuestion());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_QUESTIONID, paper.getQuestionId());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_REMARK, paper.getRemark());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_SCORE, paper.getScroe());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_SECTION, paper.getSetcion());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_SEQ, paper.getSeq());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_USERANSWER, paper.getUserAnswer());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_OPTION_A, paper.getItemA());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_OPTION_B, paper.getItemB());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_OPTION_C, paper.getItemC());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_OPTION_D, paper.getItemD());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_OPTION_E, paper.getItemE());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_OPTION_F, paper.getItemF());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_OPTION_G, paper.getItemG());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_OPTION_H, paper.getItemH());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_OPTION_I, paper.getItemI());
        newValues.put(DBHelper.TEST_LIBRARY_QUESTION_OPTION_J, paper.getItemJ());
        return database.insert(DBHelper.TABLE_NAME_TEST_LIBRARY, null,
                newValues);
    }

    public Paper[] queryAllData() {
        Cursor result = database.query(DBHelper.TABLE_NAME_TEST_LIBRARY,
                null, null, null, null,
                null, null);
        return ConvertToPaper(result);
    }

    public List<Paper> getMistakeBook(int id) {
        List<Paper> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select distinct * from table_test_library where isright=0 and test_id=" +
                id + ";", null);
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        for (int i = 0; i < resultCounts; i++) {
            Paper paper = new Paper();
            paper.setTestId(cursor.getInt(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_COL_ID)));
            paper.setAnli(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_ANLI)));
            paper.setAnswer(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_ANSWER)));
            paper.setDifficulty(cursor.getInt(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_DIFFICULTY)));
            paper.setExerciseType(cursor.getInt(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_EXERCISETYPE)));
            paper.setIsright(cursor.getInt(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_ISRIGHT)));
            paper.setItemNum(cursor.getInt(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_ITEMNUM)));
            paper.setLabel(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_LABEL)));
            paper.setPaperid(cursor.getInt(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_PAPERID)));
            paper.setPartid(cursor.getInt(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_PARTID)));
            paper.setQuestion(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_QUESTION)));
            paper.setQuestionId(cursor.getInt(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_QUESTIONID)));
            paper.setRemark(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_REMARK)));
            paper.setScroe(cursor.getInt(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_SCORE)));
            paper.setSetcion(cursor.getInt(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_SECTION)));
            paper.setSeq(cursor.getInt(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_SEQ)));
            paper.setUserAnswer(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_USERANSWER)));
            paper.setItemA(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_OPTION_A)));
            paper.setItemB(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_OPTION_B)));
            paper.setItemC(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_OPTION_C)));
            paper.setItemD(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_OPTION_D)));
            paper.setItemE(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_OPTION_E)));
            paper.setItemF(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_OPTION_F)));
            paper.setItemG(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_OPTION_G)));
            paper.setItemH(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_OPTION_H)));
            paper.setItemI(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_OPTION_I)));
            paper.setItemJ(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_OPTION_J)));
            if (!paper.getAnswer().equals(paper.getUserAnswer()))
                list.add(paper);
            cursor.moveToNext();
        }
        return list;
    }

    public long insertNotice(HistoryListItemObject obj) {
        database.beginTransaction();
        try {
            database.execSQL("delete from table_notice where id=" + obj.getId() + ";");
            database.execSQL("insert into table_notice(id,type,content,testcode,educode,groupid," +
                    "hospitalcode,sendtime,isShow,status,isvalued) values(" + obj.getId() + ",'" + obj.getType() +
                    "','" + obj.getTitle()
                    + "','" + obj.getTestcode() + "','" + obj.getEducode() + "','" + obj.getGroupid() + "','" + obj
                    .getHospitalcode()
                    + "','" + obj.getDt() + "','1','" + obj.getStatus() + "','" + obj.getIsvalued() + "');");
            database.setTransactionSuccessful();
        } catch (Exception ex) {
            return -1L;
        } finally {
            database.endTransaction();
        }
        return 1l;
    }

    public long insertTest(SimpleExam exam) {
        database.beginTransaction();
        try {
            database.execSQL("delete from table_test where test_id=" + exam.getId() + ";");
            database.execSQL("insert into table_test(test_id,test_name,test_score,test_jige_score) " +
                    "values(" + exam.getId() + ",'" + exam.getExamName() + "'," + exam.getScore() + "," + exam
                    .getJigeScore() + ");");
            database.setTransactionSuccessful();
        } catch (Exception ex) {
            return -1L;
        } finally {
            database.endTransaction();
        }
        return 1l;
    }

    public List<SimpleExam> getExams() {
        Cursor result = database.query(DBHelper.TABLE_NAME_TEST,
                null, null, null, null,
                null, null);
        return ConvertToExam(result);
    }

    @Nullable
    private List<SimpleExam> ConvertToExam(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<SimpleExam> list = new ArrayList<>();
        for (int i = 0; i < resultCounts; i++) {
            SimpleExam obj = new SimpleExam();
            obj.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.TEST_COL_ID)));
            obj.setExamName(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_COL_NAME)));
            obj.setScore(cursor.getInt(cursor.getColumnIndex(DBHelper.TEST_COL_SCROE)));
            obj.setJigeScore(cursor.getInt(cursor.getColumnIndex(DBHelper.TEST_COL_JIGE)));
            list.add(obj);
        }
        return list;
    }

    public int getMaxNoticeId() {
        Cursor cr;
        cr = database.rawQuery("select max(id) from table_notice", null);
        if (cr.moveToFirst()) {
            return cr.getInt(0);
        }
        return 0;
    }

    public long updateNoticeStatus(int id, String status) {
        ContentValues newValues = new ContentValues();
        newValues.put(DBHelper.NOTICE_COL_STATUS, status);
        String[] arg = {String.valueOf(id)};
        return database.update(DBHelper.TABLE_NAME_NOTICE, newValues, "id=?", arg);
    }

    public long updateNoticeShow(int id) {
        ContentValues newValues = new ContentValues();
        newValues.put(DBHelper.NOTICE_COL_SHOW, 0);
        String[] arg = {String.valueOf(id)};
        return database.update(DBHelper.TABLE_NAME_NOTICE, newValues, "id=?", arg);
    }

    public List<HistoryListItemObject> queryNotices() {
        Cursor result = database.query(DBHelper.TABLE_NAME_NOTICE,
                null, null, null, null,
                null, null);
        return ConvertToNotice(result);
    }

    @Nullable
    private List<HistoryListItemObject> ConvertToNotice(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<HistoryListItemObject> list = new ArrayList<>();
        for (int i = 0; i < resultCounts; i++) {
            HistoryListItemObject obj = new HistoryListItemObject();
            obj.setIsvalued(cursor.getString(cursor.getColumnIndex(DBHelper.NOTICE_COL_ISVALUED)));
            obj.setHospitalcode(cursor.getString(cursor.getColumnIndex(DBHelper.NOTICE_COL_HOSPITALCODE)));
            obj.setGroupid(cursor.getString(cursor.getColumnIndex(DBHelper.NOTICE_COL_GROUPID)));
            obj.setTestcode(cursor.getString(cursor.getColumnIndex(DBHelper.NOTICE_COL_TESTCODE)));
            obj.setDt(cursor.getString(cursor.getColumnIndex(DBHelper.NOTICE_COL_SENDTIME)));
            obj.setEducode(cursor.getString(cursor.getColumnIndex(DBHelper.NOTICE_COL_EDUCODE)));
            obj.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.NOTICE_COL_ID)));
            obj.setStatus(cursor.getString(cursor.getColumnIndex(DBHelper.NOTICE_COL_STATUS)));
            obj.setTitle(cursor.getString(cursor.getColumnIndex(DBHelper.NOTICE_COL_CONTENT)));
            obj.setType(cursor.getString(cursor.getColumnIndex(DBHelper.NOTICE_COL_TYPE)));
            list.add(obj);
        }
        return list;
    }

    @Nullable
    private Paper[] ConvertToPaper(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        Paper[] papers = new Paper[resultCounts];
        for (int i = 0; i < resultCounts; i++) {
            papers[i] = new Paper();
            papers[i].setTestId(cursor.getInt(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_COL_ID)));
            papers[i].setAnli(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_ANLI)));
            papers[i].setAnswer(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_ANLI)));
            papers[i].setDifficulty(cursor.getInt(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_DIFFICULTY)));
            papers[i].setExerciseType(cursor.getInt(cursor.getColumnIndex(DBHelper
                    .TEST_LIBRARY_QUESTION_EXERCISETYPE)));
            papers[i].setIsright(cursor.getInt(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_ISRIGHT)));
            papers[i].setItemNum(cursor.getInt(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_ITEMNUM)));
            papers[i].setLabel(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_LABEL)));
            papers[i].setPaperid(cursor.getInt(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_PAPERID)));
            papers[i].setPartid(cursor.getInt(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_PARTID)));
            papers[i].setQuestion(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_QUESTION)));
            papers[i].setQuestionId(cursor.getInt(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_QUESTIONID)));
            papers[i].setRemark(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_REMARK)));
            papers[i].setScroe(cursor.getInt(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_SCORE)));
            papers[i].setSetcion(cursor.getInt(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_SECTION)));
            papers[i].setSeq(cursor.getInt(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_SEQ)));
            papers[i].setUserAnswer(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_USERANSWER)));
            papers[i].setItemA(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_OPTION_A)));
            papers[i].setItemB(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_OPTION_B)));
            papers[i].setItemC(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_OPTION_C)));
            papers[i].setItemD(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_OPTION_D)));
            papers[i].setItemE(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_OPTION_E)));
            papers[i].setItemF(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_OPTION_F)));
            papers[i].setItemG(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_OPTION_G)));
            papers[i].setItemH(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_OPTION_H)));
            papers[i].setItemI(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_OPTION_I)));
            papers[i].setItemJ(cursor.getString(cursor.getColumnIndex(DBHelper.TEST_LIBRARY_QUESTION_OPTION_J)));
            cursor.moveToNext();
        }
        return papers;
    }
}
