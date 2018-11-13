package com.qz.zframe.material.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.qz.zframe.common.util.BaseJsonView;

import io.swagger.annotations.ApiModelProperty;

public class InInventory implements Serializable{
   
	private static final long serialVersionUID = 4390798687448254343L;
	
	public interface ListView extends BaseJsonView {};

	@ApiModelProperty(name="inInventoryId",value="入库id",required=true)
	@JsonView({ ListView.class })
	private String inInventoryId;

	@ApiModelProperty(name="inInventoryOrder",value="入库单号",required=true)
	@JsonView({ ListView.class })
    private String inInventoryOrder;

	@ApiModelProperty(name="goodsDescribed",value="到货描述",required=false)
	@JsonView({ ListView.class })
    private String goodsDescribed;

	@ApiModelProperty(name="stockAddId",value="库存地点id",required=true)
	@JsonView({ ListView.class })
    private String stockAddId;
	
	@ApiModelProperty(name="stockAddName",value="库存地点名称",required=false)
	@JsonView({ ListView.class })
    private String stockAddName;

	
	@ApiModelProperty(name="goodsTypeId",value="入库类型id",required=false)
	@JsonView({ ListView.class })
    private String goodsTypeId;
	
	@ApiModelProperty(name="goodsTypeName",value="入库类型名称",required=false)
	@JsonView({ ListView.class })
    private String goodsTypeName;

	@ApiModelProperty(name="inInventoryTime",value="入库日期,展示用",required=false)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	@JsonView({ ListView.class })
    private Date inInventoryTime;
	
	@ApiModelProperty(name="inInventoryDate",value="入库日期，新增或编辑时使用",required=true)
    private String inInventoryDate;

	public String getInInventoryDate() {
		return inInventoryDate;
	}

	public void setInInventoryDate(String inInventoryDate) {
		this.inInventoryDate = inInventoryDate;
	}

	@ApiModelProperty(name="windId",value="风电场id",required=true)
	@JsonView({ ListView.class })
    private String windId;
	
	@ApiModelProperty(name="windName",value="风电场名称",required=false)
	@JsonView({ ListView.class })
    private String windName;

	@ApiModelProperty(name="contractNumber",value="合同编号",required=false)
	@JsonView({ ListView.class })
    private String contractNumber;

	@ApiModelProperty(name="companyId",value="公司id",required=false)
	@JsonView({ ListView.class })
    private String companyId;

	@ApiModelProperty(name="inInventoryType",value="入库分类，01普通入库，02期初入库",required=true)
    private String inInventoryType;

	@ApiModelProperty(name="isDelete",value="删除状态，01正常，02删除",required=false)
    private String isDelete;

	@ApiModelProperty(name="creater",value="创建人id",required=false)
    private String creater;
	
	
	@ApiModelProperty(name="createName",value="创建人姓名",required=false)
	@JsonView({ ListView.class })
    private String createName;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@ApiModelProperty(name="createTime",value="创建时间",required=false)
    private Date createTime;

	@ApiModelProperty(name="status",value="状态",required=false)
	@JsonView({ ListView.class })
    private String status;
	
	@ApiModelProperty(name="supplierId",value="供应商id",required=false)
	@JsonView({ ListView.class })
    private String supplierId;
	
	@ApiModelProperty(name="supplierName",value="供应商名称",required=false)
	@JsonView({ ListView.class })
    private String supplierName;
	
	@ApiModelProperty(name="inInventoryDetails",value="物资明细列表",required=false)
	private List<InInventoryDetail> inInventoryDetails;

    public String getInInventoryId() {
        return inInventoryId;
    }

    public void setInInventoryId(String inInventoryId) {
        this.inInventoryId = inInventoryId == null ? null : inInventoryId.trim();
    }

    public String getInInventoryOrder() {
        return inInventoryOrder;
    }

    public void setInInventoryOrder(String inInventoryOrder) {
        this.inInventoryOrder = inInventoryOrder == null ? null : inInventoryOrder.trim();
    }

    public String getGoodsDescribed() {
        return goodsDescribed;
    }

    public void setGoodsDescribed(String goodsDescribed) {
        this.goodsDescribed = goodsDescribed == null ? null : goodsDescribed.trim();
    }

    public String getStockAddId() {
        return stockAddId;
    }

    public void setStockAddId(String stockAddId) {
        this.stockAddId = stockAddId == null ? null : stockAddId.trim();
    }


    public String getGoodsTypeId() {
		return goodsTypeId;
	}

	public void setGoodsTypeId(String goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}

	public String getGoodsTypeName() {
		return goodsTypeName;
	}

	public void setGoodsTypeName(String goodsTypeName) {
		this.goodsTypeName = goodsTypeName;
	}

	public Date getInInventoryTime() {
        return inInventoryTime;
    }

    public void setInInventoryTime(Date inInventoryTime) {
        this.inInventoryTime = inInventoryTime;
    }


    public String getWindId() {
		return windId;
	}

	public void setWindId(String windId) {
		this.windId = windId;
	}

	public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber == null ? null : contractNumber.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getInInventoryType() {
        return inInventoryType;
    }

    public void setInInventoryType(String inInventoryType) {
        this.inInventoryType = inInventoryType == null ? null : inInventoryType.trim();
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete == null ? null : isDelete.trim();
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

	public String getStockAddName() {
		return stockAddName;
	}

	public void setStockAddName(String stockAddName) {
		this.stockAddName = stockAddName;
	}

	public String getWindName() {
		return windName;
	}

	public void setWindName(String windName) {
		this.windName = windName;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public List<InInventoryDetail> getInInventoryDetails() {
		return inInventoryDetails;
	}

	public void setInInventoryDetails(List<InInventoryDetail> inInventoryDetails) {
		this.inInventoryDetails = inInventoryDetails;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
  
}