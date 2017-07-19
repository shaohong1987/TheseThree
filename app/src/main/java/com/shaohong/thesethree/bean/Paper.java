package com.shaohong.thesethree.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shaohong on 2017/7/10.
 */

public class Paper implements Parcelable {
    private int id;
    private int questionId;
    public Paper(){}
    protected Paper(Parcel in) {
        id = in.readInt();
        questionId = in.readInt();
        testId = in.readInt();
        answer = in.readString();
        exerciseType = in.readInt();
        itemA = in.readString();
        itemB = in.readString();
        itemC = in.readString();
        itemD = in.readString();
        itemE = in.readString();
        itemF = in.readString();
        itemG = in.readString();
        itemH = in.readString();
        itemI = in.readString();
        itemJ = in.readString();
        itemNum = in.readInt();
        anli = in.readString();
        question = in.readString();
        remark = in.readString();
        partid = in.readInt();
        setcion = in.readInt();
        label = in.readString();
        userAnswer = in.readString();
        scroe = in.readInt();
        paperid = in.readInt();
        seq = in.readInt();
        isright = in.readInt();
        difficulty = in.readFloat();
    }

    public static final Creator<Paper> CREATOR = new Creator<Paper>() {
        @Override
        public Paper createFromParcel(Parcel in) {
            return new Paper(in);
        }

        @Override
        public Paper[] newArray(int size) {
            return new Paper[size];
        }
    };

    public int getQuestionId() {
        return questionId;
    }


    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    private int testId;
    private String answer;
    private int exerciseType;
    private String itemA;
    private String itemB;
    private String itemC;
    private String itemD;
    private String itemE;
    private String itemF;
    private String itemG;
    private String itemH;
    private String itemI;
    private String itemJ;
    private int itemNum;
    private String anli;
    private String question;
    private String remark;
    private int partid;
    private int setcion;
    private String label;
    private String userAnswer;
    private int scroe;
    private int paperid;
    private int seq;
    private int isright;
    private float difficulty;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(int exerciseType) {
        this.exerciseType = exerciseType;
    }

    public String getItemA() {
        return itemA;
    }

    public void setItemA(String itemA) {
        this.itemA = itemA;
    }

    public String getItemB() {
        return itemB;
    }

    public void setItemB(String itemB) {
        this.itemB = itemB;
    }

    public String getItemC() {
        return itemC;
    }

    public void setItemC(String itemC) {
        this.itemC = itemC;
    }

    public String getItemD() {
        return itemD;
    }

    public void setItemD(String itemD) {
        this.itemD = itemD;
    }

    public String getItemE() {
        return itemE;
    }

    public void setItemE(String itemE) {
        this.itemE = itemE;
    }

    public String getItemF() {
        return itemF;
    }

    public void setItemF(String itemF) {
        this.itemF = itemF;
    }

    public String getItemG() {
        return itemG;
    }

    public void setItemG(String itemG) {
        this.itemG = itemG;
    }

    public String getItemH() {
        return itemH;
    }

    public void setItemH(String itemH) {
        this.itemH = itemH;
    }

    public String getItemI() {
        return itemI;
    }

    public void setItemI(String itemI) {
        this.itemI = itemI;
    }

    public String getItemJ() {
        return itemJ;
    }

    public void setItemJ(String itemJ) {
        this.itemJ = itemJ;
    }

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    public String getAnli() {
        return anli;
    }

    public void setAnli(String anli) {
        this.anli = anli;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getPartid() {
        return partid;
    }

    public void setPartid(int partid) {
        this.partid = partid;
    }

    public int getSetcion() {
        return setcion;
    }

    public void setSetcion(int setcion) {
        this.setcion = setcion;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public int getScroe() {
        return scroe;
    }

    public void setScroe(int scroe) {
        this.scroe = scroe;
    }

    public int getPaperid() {
        return paperid;
    }

    public void setPaperid(int paperid) {
        this.paperid = paperid;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getIsright() {
        return isright;
    }

    public void setIsright(int isright) {
        this.isright = isright;
    }

    public float getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(float difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(questionId);
        dest.writeInt(testId);
        dest.writeString(answer);
        dest.writeInt(exerciseType);
        dest.writeString(itemA);
        dest.writeString(itemB);
        dest.writeString(itemC);
        dest.writeString(itemD);
        dest.writeString(itemE);
        dest.writeString(itemF);
        dest.writeString(itemG);
        dest.writeString(itemH);
        dest.writeString(itemI);
        dest.writeString(itemJ);
        dest.writeInt(itemNum);
        dest.writeString(anli);
        dest.writeString(question);
        dest.writeString(remark);
        dest.writeInt(partid);
        dest.writeInt(setcion);
        dest.writeString(label);
        dest.writeString(userAnswer);
        dest.writeInt(scroe);
        dest.writeInt(paperid);
        dest.writeInt(seq);
        dest.writeInt(isright);
        dest.writeFloat(difficulty);
    }
}

