package com.qz.zframe.material.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.servlet.handler.MappedInterceptor;

import com.github.pagehelper.Page;
import com.qz.zframe.common.util.ErrorCode;
import com.qz.zframe.common.util.PageResultEntity;
import com.qz.zframe.common.util.ResultEntity;
import com.qz.zframe.material.dao.MaterialMapper;
import com.qz.zframe.material.entity.Material;
import com.qz.zframe.material.entity.MaterialExample;
import com.qz.zframe.material.enums.IsDeleteEnum;
import com.qz.zframe.material.service.MaterialService;

@Service
@Transactional(rollbackFor = Exception.class)
public class MaterialServiceImpl implements MaterialService {

	private static Logger logger = LoggerFactory.getLogger(MaterialServiceImpl.class);

	@Autowired
	private MaterialMapper materialMapperMapper;

	@Override
	public List<Material> listMaterial(MaterialExample materialExample) {

		return materialMapperMapper.selectByExample(materialExample);
	}

	@Override
	public ResultEntity saveMaterial(Material material) {
		ResultEntity resultEntity = new ResultEntity();
		if (checkMaterialCodeOrNameIsExit(material)) {
			resultEntity.setCode(ErrorCode.ERROR);
			resultEntity.setMsg("物资名称或物资编码已存在");
			return resultEntity;
		}

		int count = materialMapperMapper.insertSelective(material);
		if (count == 0) {
			resultEntity.setCode(ErrorCode.ERROR);
			resultEntity.setMsg("新增失败");
		} else {
			resultEntity.setCode(ErrorCode.SUCCESS);
		}
		return resultEntity;
	}

	/**
	 * 判断物资分类或物资名称是否已存在
	 */
	private boolean checkMaterialCodeOrNameIsExit(Material material) {
		MaterialExample example = new MaterialExample();
		example.createCriteria().andIsDeleteEqualTo(IsDeleteEnum.DELETE_NO.getCode()).andMaterialNameEqualTo(material.getMaterialName());
		example.or().andMaterialCodeEqualTo(material.getMaterialCode());
		int count = materialMapperMapper.countByExample(example);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public PageResultEntity getMaterialList(MaterialExample materialExample) {
		PageResultEntity resultEntity = new PageResultEntity();
		int count = materialMapperMapper.countByExample(materialExample);
		resultEntity.setTotal(count);
		List<Material> list;
		if (count == 0) {
			list = Collections.emptyList();
		} else {
			list = materialMapperMapper.selectByExample(materialExample);
		}
		resultEntity.setCode(0);
		resultEntity.setRows(list);
		return resultEntity;
	}

	@Override
	public ResultEntity updateMaterial(Material material) {
		ResultEntity resultEntity = new ResultEntity();
		Material materialInfo = materialMapperMapper.selectByPrimaryKey(material.getMaterialId());
		if (materialInfo != null) {
			if (!materialInfo.getMaterialName().equals(material.getMaterialName())
					|| !materialInfo.getMaterialCode().equals(material.getMaterialCode())) {
				if (checkMaterialCodeOrNameIsExit(material)) {
					resultEntity.setCode(ErrorCode.ERROR);
					resultEntity.setMsg("物资名称或物资编码已存在");
					return resultEntity;
				}
			}
		}
		int count = materialMapperMapper.updateByPrimaryKeySelective(material);
		if (count == 0) {
			resultEntity.setCode(ErrorCode.ERROR);
			resultEntity.setMsg("编辑失败");
		} else {
			resultEntity.setCode(ErrorCode.SUCCESS);
		}
		return resultEntity;
	}

//	@Transactional(rollbackFor = Exception.class)
//	@Override
//	public ResultEntity delMaterial(List<String> materialIds) {
//		ResultEntity resultEntity = new ResultEntity();
//		try {
//			// materialMapperMapper.delMaterial(materialIds);
//			resultEntity.setCode(0);
//			resultEntity.setMsg("删除成功");
//		} catch (Exception e) {
//			e.printStackTrace();
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//			resultEntity.setCode(-1);
//			resultEntity.setMsg("操作异常");
//		}
//		return resultEntity;
//	}
	
	@Override
	public ResultEntity delMaterial(List<String> materialIds) {
		ResultEntity resultEntity = new ResultEntity();
		Map<String, Object> params = new HashMap<>(2);
		params.put("isDelete", IsDeleteEnum.DELETE_YES.getCode());
		params.put("materialIds", materialIds);
		materialMapperMapper.updateIsdete(params);
		resultEntity.setCode(ErrorCode.SUCCESS);
		return resultEntity;
	}

	@Override
	public ResultEntity detailMaterial(String materialId) {
		ResultEntity resultEntity = new ResultEntity();
		Material material = materialMapperMapper.selectByPrimaryKey(materialId);
		if (material == null) {
			resultEntity.setCode(-1);
			resultEntity.setMsg("物资Id不存在");
			logger.debug("错误的materialId{}" + materialId);
		} else {
			resultEntity.setCode(0);
			resultEntity.setData(material);
		}

		return resultEntity;
	}

}
