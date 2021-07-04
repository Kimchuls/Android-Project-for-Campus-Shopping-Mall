package com.example.supermarket_1_0.work_activity.entity;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.$Gson$Types;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AddItem {
    private String id;
    private String WorkerID;
    private int TaskCode;
    private String StartTime;
    private String TaskName;
    private String TaskDetails;

    String[] TaskNameList = {"商品进货", "打包商品"};

    public AddItem() {
    }

    public AddItem(String id, String WorkerID, int TaskCode, String StartTime, String TaskDetails) {
        this.id = id;
        this.WorkerID = WorkerID;
        this.TaskCode = TaskCode;
        this.StartTime = StartTime;
        this.TaskName = TaskNameList[TaskCode];
        this.TaskDetails = TaskDetails;
    }

    @Override
    public String toString() {
        return "AddItem{" +
                "id=" + id +
                ", WorkerID=" + WorkerID +
                ", TaskCode=" + TaskCode +
                ", TaskName='" + TaskName + '\'' +
                ", TaskDetails='" + TaskDetails + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkerID() {
        return WorkerID;
    }

    public void setWorkerID(String WorkerID) {
        this.WorkerID = WorkerID;
    }

    public int getTaskCode() {
        return TaskCode;
    }

    public void setTaskCode(int TaskCode) {
        this.TaskCode = TaskCode;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }

    public String getTaskName() {
        return TaskName;
    }

    public String getTaskDetails() {
        return TaskDetails;
    }

    public List<String> getTaskDetailEncode() throws JSONException {
        List<String> choiceList = new ArrayList<>();
        if(TaskCode==0){
            String[] str=TaskDetails.split("-");
            String data = String.format("商品编号：%s，商品名称：%s，进货数目：%s",str[0],str[1],str[2]);
            choiceList.add(data);
        }
        else if (TaskCode==1){
            String[] items=TaskDetails.split(",");
            for(int i=1;i<items.length;i++){
                String[] str=items[i].split("-");
                String data = String.format("商品编号：%s，商品名称：%s，需要数目：%s",str[0],str[1],str[2]);
                choiceList.add(data);
            }
        }
        return choiceList;
    }

    public void setTaskDetails(String TaskDetails) {
        this.TaskDetails = TaskDetails;
    }
}
