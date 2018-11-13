package com.qz.zframe.tickets.mapper;

import com.qz.zframe.tickets.entity.OperateTicket;
import com.qz.zframe.tickets.entity.OperateTicketExample;
import com.qz.zframe.tickets.vo.OperateTicketVo;
import com.qz.zframe.tickets.vo.TicketStatisticsRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OperateTicketMapper {
    int countByExample(OperateTicketExample example);

    int deleteByExample(OperateTicketExample example);

    int deleteByPrimaryKey(String ticketId);

    int insert(OperateTicket record);

    int insertSelective(OperateTicket record);
    //批量删除`
    int batchDelete(@Param("array") String[] ids);
    //详情查询
    OperateTicketVo getOperateTicketDetail(@Param("ticketId") String ticketId);
    //列表查询
    List<OperateTicketVo> getOperateTicketList(@Param("map") Map<String, String> pageAndCondition);
    //查询总记录数
    int getTotal(@Param("map") Map<String, String> pageAndCondition);
    //操作票统计
    List<TicketStatisticsRes> getOperateTicketStatisticsList(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<OperateTicket> selectByExample(OperateTicketExample example);

    OperateTicket selectByPrimaryKey(String ticketId);

    int updateByExampleSelective(@Param("record") OperateTicket record, @Param("example") OperateTicketExample example);

    int updateByExample(@Param("record") OperateTicket record, @Param("example") OperateTicketExample example);

    int updateByPrimaryKeySelective(OperateTicket record);

    int updateByPrimaryKey(OperateTicket record);
}