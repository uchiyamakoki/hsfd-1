package com.qz.zframe.tally.dao;

import com.qz.zframe.tally.dto.PostDto;
import com.qz.zframe.tally.dto.TallyRouterDto;
import com.qz.zframe.tally.dto.UserDto;
import com.qz.zframe.tally.entity.*;
import com.qz.zframe.tally.vo.TallyPlanVO;
import com.qz.zframe.tally.vo.TallyRouterVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface TallyRouterDao {
    /**
     * 查巡检导航信息
     * @return
     */
    List<TallyRoute> findAllTallyRouter(@Param("windId") String windId, @Param("routeCode") String routeCode, @Param("routeName") String routeName);
    //没用的接口
    int sal();

    /**
     * 通过路线id查出周期名称
     * @param routeId 路线id
     * @return
     */
    List<String> findCycleNameByRouteId(String routeId);

    /**
     * 通过路线id查出用户id
     * @param routeId 路线id
     * @return
     */
    List<String> findUserIdsByRouteId(String routeId);

    /**
     * 通过用户id查名称
     * @param userId 用户id
     * @return
     */
    String findUserNameByUserId(String userId);

    /**
     * 通过路线id数组删除周期
     * @param routeIds
     */
    void deleteByRouteId(@Param("routeIds") String[] routeIds);

    /**
     *通过路线id数组删除路线人员关联
     * @param routeIds
     */
    void deleteRouteUserByRouteId(@Param("routeIds") String[] routeIds);

    /**
     * 通过路线id数组删除路线标准关联
     * @param routeIds
     */
    void deleteTallyRouteStandardByRouteId(@Param("routeIds") String[] routeIds);

    /**
     * 通过路线id数组删除点检路线
     * @param routeIds
     */
    void deleteTallyRouteByRouteId(@Param("routeIds") String[] routeIds);

    /**
     * 查找目前最大流水号
     * @return 目前最大流水号
     */
    int findserialNum();

    /**
     * 新增巡检路线
     * @param tallyRoute
     */
    void addTallyRoute(TallyRoute tallyRoute);

    /**
     * 新增周期
     * @param cycle
     */
    void addCycle(Cycle cycle);

    /**
     * 新增时段
     * @param periodTime
     */
    void addPeriodTime(PeriodTime periodTime);

    /**
     * 新增周期时间关联表
     * @param cyclePeriodTime
     */
    void addCyclePeriodTime(CyclePeriodTime cyclePeriodTime);

    /**
     * 根据userName获取userID
     * @param userName
     * @return
     */
    String findUserIdByUserName(String userName);

    /**
     * \新增路线人员关联
     * @param routeUser
     */
    void addRouteUser(RouteUser routeUser);

    /**
     * 判断路线id是否已经存在
     * @param routeId
     * @return
     */
    int checkRouteId(String routeId);

    /**
     * 查询所有用户名称
     * @return
     */
    List<String> choicePeople();

    //todo
    /**
     * 查询所有风场
     * @return
     */
    List<String> findAllWind();

    /**
     * 查Dto
     * @param windId
     * @param routeCode
     * @param routeName
     * @return
     */
    List<TallyRouterDto> findAllTallyRouterDto(@Param("windId") String windId, @Param("routeCode") String routeCode, @Param("routeName") String routeName);


    /**
     * 查出所有路线编码
     * @return
     */
    List<String> findAllRouteCode();

    /**
     * 根据路线编码查所属风场
     * @param routeCode
     * @return
     */
    String findWindIdByRouteCode(String routeCode);

    /**
     * 查分页用总数
     * @param windId
     * @param routeCode
     * @param routeName
     * @return
     */
    int countTallyRoute(@Param("windId") String windId, @Param("routeCode") String routeCode, @Param("routeName") String routeName);


    /**
     * 根据用户名查相关职务
     * @param userName
     * @return
     */
    PostDto findPostByUserName(String userName);

    /**
     * 查询路线编码相关
     * @param routeCode
     * @return
     */
    TallyRoute findRouteCodeRelevant(String routeCode);

    /**
     * 查询周期相关信息
     * @param routeId
     * @param cycleName
     * @return
     */
    Cycle findCycleByCycleNameAndRouteId(@Param("routeId")String routeId,@Param("cycleName")String cycleName);
    /**
     * 查时间段相关
     * @param cycleId
     * @return
     */
    List<PeriodTime> findPeriodTimeByCycleId(String cycleId);

    /**
     * 更新巡检路线信息
     * @param tallyRoute
     */
    void updateTallyRoute(TallyRoute tallyRoute);

    /**
     * 更新周期信息
     * @param cycle
     */
    void updateCycle(Cycle cycle);

    /**
     * 查时间段id
     * @param cycleId
     * @param startTime
     * @param endTime
     * @return
     */
    String findPeriodTimeIdByCycleIdStartTimeAndEndTime(@Param("cycleId")String cycleId,@Param("startTime")Date startTime,@Param("endTime")Date endTime);

    /**
     * 更新时间段表
     * @param periodTime
     */
    void updatePeriodTime(PeriodTime periodTime);

    /**
     * 根据路线id和用户id查关联表id
     * @param routeId
     * @param userId
     * @return
     */
    String findIdByRouteIdAndUserId(@Param("routeId")String routeId,@Param("userId")String userId);

    /**
     * 更新用户路线关系表
     * @param routeUser
     */
    void updateRouteUser(RouteUser routeUser);

    /**
     * 根据周期名称 开始时间 结束时间删除时间表
     * @param cycleName
     * @param startTime
     * @param endTime
     */
    void deletePeriodTimeByCycleNameAndStartTimeAndEndTime(@Param("cycleName")String cycleName,@Param("startTime")Date startTime,@Param("endTime")Date endTime);

    /**
     * 查时间段id
     * @param cycleId
     * @param startTime
     * @param endTime
     * @return
     */
    int findPeriodTimeByCycleIdStartTimeAndEndTime(@Param("cycleId")String cycleId,@Param("startTime")Date startTime,@Param("endTime")Date endTime);
    //**********************************************************

    /**
     * 点检计划的条件查询
     * @param routeName
     * @param maintenancer
     * @param windId
     * @return
     */
     List<TallyRoute> findTallyRouteByPlan(@Param("routeName")String routeName, @Param("maintenancer") String maintenancer,
                                           @Param("windId") String windId, @Param("startTime")Date startTime, @Param("endTime")Date endTime);


    /**
     * 查显示的东西 巡检计划的
     * @param routeName
     * @param maintenancer
     * @param windId
     * @param startTime
     * @param endTime
     * @return
     */
     List<TallyPlanVO> findTallyPlanVOByPlan(@Param("routeName")String routeName, @Param("maintenancer") String maintenancer,
                                             @Param("windId") String windId, @Param("startTime")Date startTime, @Param("endTime")Date endTime);


    /**
     * 根据路线名称和风电场名称查巡检路线
     * @param routeName
     * @param windId
     * @return
     */
     List<TallyRoute> findTallyRouteByWindIdAndRouteName(@Param("routeName")String routeName,@Param("windId") String windId);

    /**
     * 根据路线名称和风电场id计算
     * @param routeName
     * @param windId
     * @return
     */
    int countByWindIdAndRouteName(@Param("routeName")String routeName,@Param("windId") String windId);

    /**
     * 查询路线id最大值
     * @return
     */
    String findRouteId();

    /**
     * 查询周期id最大值
     * @return
     */
    String findCycleId();

    String findDepartmentByUserName(String userName);


    /**
     * 查巡检路线添加的用户信息相关
     * @return
     */
    List<UserDto> findAllUserDto();





}
