package com.ingic.tanfit.entities;

/**
 * Created on 11/28/2017.
 */

public class TimingTypeEnt {
    private int ImageGreyRes;
    private int ImageWhiteRes;
    private int FrameGreenRes;
    private int FrameTransparentRes;
    private int GreenFontColor;
    private int BlackFontColor;
    private boolean isSelected;
    private String Type;
    private String classImage;
    private int classId;

    public TimingTypeEnt(int imageGreyRes, int imageWhiteRes,boolean isSelected, String type,String ClassImage,int classId) {
        ImageGreyRes = imageGreyRes;
        ImageWhiteRes = imageWhiteRes;
        this.isSelected = isSelected;
        Type = type;
        this.classImage=ClassImage;
        this.classId=classId;
    }

    public int getImageGreyRes() {
        return ImageGreyRes;
    }

    public void setImageGreyRes(int imageGreyRes) {
        ImageGreyRes = imageGreyRes;
    }

    public int getImageWhiteRes() {
        return ImageWhiteRes;
    }

    public void setImageWhiteRes(int imageWhiteRes) {
        ImageWhiteRes = imageWhiteRes;
    }

    public int getFrameGreenRes() {
        return FrameGreenRes;
    }

    public void setFrameGreenRes(int frameGreenRes) {
        FrameGreenRes = frameGreenRes;
    }

    public int getFrameTransparentRes() {
        return FrameTransparentRes;
    }

    public void setFrameTransparentRes(int frameTransparentRes) {
        FrameTransparentRes = frameTransparentRes;
    }

    public int getGreenFontColor() {
        return GreenFontColor;
    }

    public void setGreenFontColor(int greenFontColor) {
        GreenFontColor = greenFontColor;
    }

    public int getBlackFontColor() {
        return BlackFontColor;
    }

    public void setBlackFontColor(int blackFontColor) {
        BlackFontColor = blackFontColor;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getClassImage() {
        return classImage;
    }

    public void setClassImage(String classImage) {
        this.classImage = classImage;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }
}

