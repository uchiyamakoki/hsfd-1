package com.qz.zframe.run.entity;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class Shift {
	
	@ApiModelProperty(value="班次id",name="shiftId")
    private String shiftId;

	@ApiModelProperty(value="班次编码",name="shiftCode",required=true)
    private String shiftCode;

	@ApiModelProperty(value="班次名称",name="shiftName",required=true)
    private String shiftName;

	@ApiModelProperty(value="开始时间",name="startTime",required=true)
    private Date startTime;

	@ApiModelProperty(value="结束时间",name="endTime",required=true)
    private Date endTime;

	@ApiModelProperty(value="状态",name="status",required=true)
    private String status;

	@ApiModelProperty(value="排序",name="sort",required=true)
    private Integer sort;

	@ApiModelProperty(value="是否抄表",name="isMeterRead",required=true)
    private String isMeterRead;

    public String getShiftId() {
        return shiftId;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId == null ? null : shiftId.trim();
    }

    public String getShiftCode() {
        return shiftCode;
    }

    public void setShiftCode(String shiftCode) {
        this.shiftCode = shiftCode == null ? null : shiftCode.trim();
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName == null ? null : shiftName.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIsMeterRead() {
        return isMeterRead;
    }

    public void setIsMeterRead(String isMeterRead) {
        this.isMeterRead = isMeterRead == null ? null : isMeterRead.trim();
    }
}