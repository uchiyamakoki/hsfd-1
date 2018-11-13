package com.qz.zframe.tally.controller;

import com.github.pagehelper.PageHelper;
import com.qz.zframe.authentication.CurrentUserService;
import com.qz.zframe.common.util.ErrorCode;
import com.qz.zframe.common.util.PageBean;

import com.qz.zframe.common.util.ResultEntity;
import com.qz.zframe.tally.dto.QDto;
import com.qz.zframe.tally.dto.TallyRouterDto;
import com.qz.zframe.tally.entity.*;
import com.qz.zframe.tally.service.PeriodTimeService;
import com.qz.zframe.tally.service.TallyRouterService;
import com.qz.zframe.tally.service.TallyStandardService;
import com.qz.zframe.tally.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("api/support/tallyRouter")
@Api(
        tags = {"api-support-tallyRouter"},
        description = "巡检-巡检路线配置"
)
@Transactional(rollbackFor = Exception.class)
public class InspectionController {

    @Autowired
    CurrentUserService currentUserService;
    @Autowired
    TallyRouterService tallyRouterService;
    @Autowired
    TallyStandardService tallyStandardService;
    @Autowired
    PeriodTimeService periodTimeService;



  /*  @ApiOperation(value="查询巡检线路", notes="可以根据无参，路线编码，路线名称，当前页，每页显示条数查询" ,httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "windId", value = "风电场", required = false),
            @ApiImplicitParam(name = "routeName", value = "路线名称", required = false),
    })
    @RequestMapping("list")
    @ResponseBody
    public ResultEntity list(String windId,
                                   String routeCode,
                                   String routeName,
                                   @RequestParam(value = "currentPage",defaultValue = "1")int currentPage,
                                   @RequestParam(value = "pageSize",defaultValue = "10")  int pageSize) {



        int n=tallyRouterService.countTallyRoute(windId, routeCode, routeName);
        ResultEntity resultEntity=new ResultEntity();
        PageHelper.startPage(currentPage,pageSize);


        List<TallyRouterDto> tallyRouterDtoList=tallyRouterService.findAllTallyRouterDto(windId, routeCode, routeName);
        List<TallyRouterVO> tallyRouterVOList=new ArrayList<TallyRouterVO>();
        for (TallyRouterDto tallyRouterDto:tallyRouterDtoList){
            TallyRouterVO tallyRouterVO=new TallyRouterVO();
            tallyRouterVO.setCycleName(tallyRouterDto.getCycleName());
            tallyRouterVO.setRouteCode(tallyRouterDto.getRouteCode());
            tallyRouterVO.setRouteId(tallyRouterDto.getRouteId());
            tallyRouterVO.setRouteName(tallyRouterDto.getRouteName());
            tallyRouterVO.setWindId(tallyRouterDto.getWindId());

            List<String> userNames=new ArrayList<String>();
            List<String> userIds=tallyRouterService.findUserIdsByRouteId(tallyRouterDto.getRouteId());
            for (String userId:userIds){
                userNames.add(tallyRouterService.findUserNameByUserId(userId));
            }
            tallyRouterVO.setUserNames(userNames);
            tallyRouterVOList.add(tallyRouterVO);
        }



        PageBean<TallyRouterVO> pageData=new PageBean<TallyRouterVO>(currentPage,pageSize,n);
        pageData.setItems(tallyRouterVOList);

        resultEntity.setCode(ErrorCode.SUCCESS);
        resultEntity.setMsg("获取分页信息成功");
        resultEntity.setData(pageData);
        return resultEntity;
    }*/

    /**
     * 点击巡检和页面相关查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    @ApiOperation(value="查询巡检线路", notes="可以根据无参，风电场，路线名称，当前页，每页显示条数查询" ,httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "windId", value = "风电场", required = false),
            @ApiImplicitParam(name = "routeName", value = "路线名称", required = false),
    })
    @RequestMapping("list")
    @ResponseBody
    public ResultEntity list(String windId,
                             String routeName,
                             @RequestParam(value = "currentPage",defaultValue = "1")int currentPage,
                             @RequestParam(value = "pageSize",defaultValue = "10")  int pageSize) {

        int n=tallyRouterService.countByWindIdAndRouteName(routeName, windId);

        ResultEntity resultEntity=new ResultEntity();
        PageHelper.startPage(currentPage,pageSize);


        List<TallyRoute> tallyRouteList=tallyRouterService.findTallyRouteByWindIdAndRouteName(routeName, windId);

        PageBean<TallyRoute> pageData=new PageBean<TallyRoute>(currentPage,pageSize,n);
        //System.out.println(currentPage+"***"+pageSize+"***"+tallyRouterVOList.size());
        pageData.setItems(tallyRouteList);

        resultEntity.setCode(ErrorCode.SUCCESS);
        resultEntity.setMsg("获取分页信息成功");
        resultEntity.setData(pageData);
        return resultEntity;
    }

    @ApiOperation(value="删除巡检线路", notes="根据传入的路线id数组进行删除" ,httpMethod="DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "routeIds", value = "路线ID", required = false),
    })
    @RequestMapping("delete")
    @ResponseBody
    public ResultEntity tallyDelete(String[] routeIds) {
        ResultEntity resultEntity=new ResultEntity();

        if (routeIds==null){
            resultEntity.setCode(ErrorCode.ERROR);
            resultEntity.setMsg("请选择要删除的路线!");

            return resultEntity;
        }
        //1.删除周期表记录
        tallyRouterService.deleteByRouteId(routeIds);

        //2.删除路线人员关联表
        tallyRouterService.deleteRouteUserByRouteId(routeIds);
        //3.删除路线标准关联
        //tallyRouterService.deleteTallyRouteStandardByRouteId(routeIds);
        //4.删除点检路线表
        tallyRouterService.deleteTallyRouteByRouteId(routeIds);

        resultEntity.setCode(ErrorCode.SUCCESS);
        resultEntity.setMsg("删除成功!");

        return resultEntity;
    }

    @ApiOperation(value="获取流水号，当前日期，申请人", notes="获取巡检路线添加基础信息接口" ,httpMethod="GET")
    @RequestMapping("user")
    @ResponseBody
    public ResultEntity tallyUser() {
        ResultEntity resultEntity=new ResultEntity();
        //查流水号,tallyRoute查最大id
        int serialNum=tallyRouterService.findserialNum()+1;
        //查申请日期
        Date currentDate=new Date();
        //查当前用户
        String userName=currentUserService.getUsername();



        TallyRouterUserVO tallyRouterUserVO=new TallyRouterUserVO();
        tallyRouterUserVO.setSerialNum(serialNum);
        tallyRouterUserVO.setCurrentDate(currentDate);
        tallyRouterUserVO.setUserName(userName);
        //tallyRouterUserVO.setRouteId(UUID.randomUUID()+"");

        resultEntity.setCode(ErrorCode.SUCCESS);
        resultEntity.setMsg("获取成功!");
        resultEntity.setData(tallyRouterUserVO);
        return resultEntity;
    }





    //todo 具体校验和前端对接
    @ApiOperation(value="新增巡检路线", notes="根据风电场，周期名称，基准日期，路线id,周期单位，周期，人员等信息添加" ,httpMethod="POST")
    @RequestMapping("post")
    @ResponseBody
    public ResultEntity tallyPost(@RequestBody TallyPostVO tallyPostVO) {
        ResultEntity resultEntity=new ResultEntity();

        if (StringUtils.isBlank(tallyPostVO.getWindId()) || StringUtils.isBlank(tallyPostVO.getCycleName())
                || StringUtils.isBlank(tallyPostVO.getBenchmarkDate().toString())
                ||StringUtils.isBlank(tallyPostVO.getCycleUnit())
                ||StringUtils.isBlank(tallyPostVO.getCycle())
                ||StringUtils.isBlank(tallyPostVO.getUserNames())
                ||StringUtils.isBlank(tallyPostVO.getRouteCode())
                ||StringUtils.isBlank(tallyPostVO.getRouteName())
                ||StringUtils.isBlank(tallyPostVO.getStartTimes().get(0).toString())
                ||StringUtils.isBlank(tallyPostVO.getEndTimes().get(0).toString())) {
            resultEntity.setCode(ErrorCode.ERROR);
            resultEntity.setMsg("缺少必填字段!");
            return resultEntity;
        }

        TallyRoute tallyRoute=new TallyRoute();
        tallyRoute.setRouteCode(tallyPostVO.getRouteCode());//路线编码 匹配和流水号一致 和前端商量一下
        tallyRoute.setWindId(tallyPostVO.getWindId());//所属风场
        tallyRoute.setRouteName(tallyPostVO.getRouteName());//路线的名称
        tallyRoute.setStatus(tallyPostVO.getStatus());//状态
        tallyRoute.setRemark(tallyPostVO.getTallyRouteRemark());//备注
        tallyRoute.setSerialNum(tallyPostVO.getSerialNum());//流水号 查出来
        tallyRoute.setMaintenancer(tallyPostVO.getMaintenancer());//维护人 查出来，最近修改的人
        tallyRoute.setMaintenanceTime(tallyPostVO.getMaintenanceTime());//维护日期 查出来
        tallyRoute.setRouteId(tallyRouterService.findRouteId());//路线id  根流水号差不多来吧

        tallyRouterService.addTallyRoute(tallyRoute);//完成巡检路线表

        Cycle cycle=new Cycle();
        cycle.setCycleId(tallyRouterService.findCycleId()); //还是看具体要求*************不过id基本能匹配名称，所以名称最好不同喽
        cycle.setRouteId(tallyRoute.getRouteId());
        cycle.setCycleName(tallyPostVO.getCycleName());
        cycle.setCycleUnit(tallyPostVO.getCycleUnit());
        cycle.setBenchmarkDate(tallyPostVO.getBenchmarkDate());
        cycle.setCycle(tallyPostVO.getCycle());

        tallyRouterService.addCycle(cycle);

        int len=tallyPostVO.getStartTimes().size();

        for (int i=0;i<len;i++){
            //时间段
            PeriodTime periodTime=new PeriodTime();
            periodTime.setPeriodTimeId(""+ UUID.randomUUID());
            periodTime.setStartTime(tallyPostVO.getStartTimes().get(i));
            periodTime.setEndTime(tallyPostVO.getEndTimes().get(i));

            tallyRouterService.addPeriodTime(periodTime);
            //时间段周期关联
            CyclePeriodTime cyclePeriodTime=new CyclePeriodTime();

            cyclePeriodTime.setId(UUID.randomUUID()+"");
            cyclePeriodTime.setCycleId(cycle.getCycleId());
            cyclePeriodTime.setPeriodTimeId(periodTime.getPeriodTimeId());

            tallyRouterService.addCyclePeriodTime(cyclePeriodTime);

        }



           RouteUser routeUser=new RouteUser();
           routeUser.setId(cycle.getCycleId());
           routeUser.setRouteId(tallyRoute.getRouteId());

           //根据username获取userid
            routeUser.setUserId(tallyPostVO.getUserId());
           //routeUser.setUserId();
          // 0 点检执行者 1 路线管理者 2 漏检负责人
           String identity=tallyPostVO.getIdentity();
           if (identity.equals("0")){
               routeUser.setIsPractitioners("是");//看情况
               routeUser.setIsHead("否");//看情况
               routeUser.setIsManagers("否");//看情况
           }else if (identity.equals("1")){
               routeUser.setIsPractitioners("否");//看情况
               routeUser.setIsHead("否");//看情况
               routeUser.setIsManagers("是");//看情况
           }else if (identity.equals("2")){
               routeUser.setIsPractitioners("否");//看情况
               routeUser.setIsHead("是");//看情况
               routeUser.setIsManagers("否");//看情况
           }else {
               routeUser.setIsPractitioners("否");//看情况
               routeUser.setIsHead("否");//看情况
               routeUser.setIsManagers("否");//看情况
           }

           tallyRouterService.addRouteUser(routeUser);

        resultEntity.setCode(ErrorCode.SUCCESS);
        resultEntity.setMsg("添加巡检路线相关信息成功!");

        return resultEntity;
    }

    @ApiOperation(value="获取选择人员id和人员所属部门", notes="点击选择后提供人员信息" ,httpMethod="GET")
    @RequestMapping("choice")
    @ResponseBody
    public ResultEntity choice() {
        ResultEntity resultEntity=new ResultEntity();

        resultEntity.setCode(ErrorCode.SUCCESS);
        resultEntity.setMsg("获取人员信息成功!");
        resultEntity.setData(tallyRouterService.findAllUserDto());
        return resultEntity;
    }

    //todo
    @ApiOperation(value="获取风电场下拉项", notes="点击下拉后获取风电场下拉信息" ,httpMethod="GET")
    @RequestMapping("winds")
    @ResponseBody
    public ResultEntity winds() {
        ResultEntity resultEntity=new ResultEntity();
        resultEntity.setCode(ErrorCode.SUCCESS);
        resultEntity.setMsg("获取风电场下拉列表信息成功!");
        resultEntity.setData(tallyRouterService.choicePeople());
        return resultEntity;
    }


    //todo 专门已有的路线编码库查询
    @ApiOperation(value="获取路线编码", notes="点击下拉后获取路线编码信息" ,httpMethod="GET")
    @RequestMapping("routeCode")
    @ResponseBody
    public ResultEntity routeCode() {
        ResultEntity resultEntity=new ResultEntity();



        resultEntity.setCode(ErrorCode.SUCCESS);
        resultEntity.setMsg("获取路线编码下拉列表信息成功!");
        resultEntity.setData(tallyRouterService.findAllRouteCode());
        return resultEntity;
    }

    @ApiOperation(value="获取路线编码后匹配风电场", notes="选定路线编码后根据路线编码匹配风电场" ,httpMethod="GET")
    @RequestMapping("routeCodeWindId")
    @ResponseBody
    public ResultEntity routeCodeWindId(String routeCode) {
        ResultEntity resultEntity=new ResultEntity();



        resultEntity.setCode(ErrorCode.SUCCESS);
        resultEntity.setMsg("匹配风电场信息成功!");
        resultEntity.setData(tallyRouterService.findWindIdByRouteCode(routeCode));
        return resultEntity;
    }



    @ApiOperation(value="修改巡检路线", notes="根据点检路线所需信息修改" ,httpMethod="PUT")
    @RequestMapping("update")
    @ResponseBody
    public ResultEntity updateRouter(@RequestBody TallyPostVO tallyPostVO) {
        ResultEntity resultEntity=new ResultEntity();

        if (StringUtils.isBlank(tallyPostVO.getWindId()) || StringUtils.isBlank(tallyPostVO.getCycleName())
                || StringUtils.isBlank(tallyPostVO.getBenchmarkDate().toString())
                ||StringUtils.isBlank(tallyPostVO.getCycleUnit())
                ||StringUtils.isBlank(tallyPostVO.getCycle())
                ||StringUtils.isBlank(tallyPostVO.getUserNames())
                ||StringUtils.isBlank(tallyPostVO.getRouteCode())
                ||StringUtils.isBlank(tallyPostVO.getRouteName())
                ||StringUtils.isBlank(tallyPostVO.getStartTimes().get(0).toString())
                ||StringUtils.isBlank(tallyPostVO.getEndTimes().get(0).toString())) {
            resultEntity.setCode(ErrorCode.ERROR);
            resultEntity.setMsg("缺少必填字段!");
            return resultEntity;
        }

        TallyRoute tallyRoute=new TallyRoute();
        tallyRoute.setRouteCode(tallyPostVO.getRouteCode());//路线编码 匹配和流水号一致 和前端商量一下
        tallyRoute.setWindId(tallyPostVO.getWindId());//所属风场
        tallyRoute.setRouteName(tallyPostVO.getRouteName());//路线的名称
        tallyRoute.setStatus(tallyPostVO.getStatus());//强制次序
        tallyRoute.setRemark(tallyPostVO.getTallyRouteRemark());//备注
        tallyRoute.setSerialNum(tallyPostVO.getSerialNum());//流水号 查出来
        tallyRoute.setMaintenancer(tallyPostVO.getMaintenancer());//维护人 查出来，最近修改的人
        tallyRoute.setMaintenanceTime(tallyPostVO.getMaintenanceTime());//维护日期 查出来
       // tallyRoute.setRouteId(tallyPostVO.getRouteId());//路线id  自己写一个UUID接口 要自己生成 有检验接口，所以一定不同

        tallyRouterService.updateTallyRoute(tallyRoute);//完成点检路线表更新

        Cycle cycle=new Cycle();
       // cycle.setCycleId(tallyRouterService.findCycleByCycleNameAndRouteId(tallyPostVO.getRouteId(),tallyPostVO.getCycleName()).getCycleId());
        //cycle.setRouteId(tallyPostVO.getRouteId());
        cycle.setCycleName(tallyPostVO.getCycleName());
        cycle.setCycleUnit(tallyPostVO.getCycleUnit());
        cycle.setBenchmarkDate(tallyPostVO.getBenchmarkDate());
        cycle.setCycle(tallyPostVO.getCycle());

        tallyRouterService.updateCycle(cycle);


        //可能还要写个删除的
        int len=tallyPostVO.getStartTimes().size();

        for (int i=0;i<len;i++){

            //时间段
            Date start=tallyPostVO.getStartTimes().get(i);
            Date end=tallyPostVO.getEndTimes().get(i);

            int n=tallyRouterService.findPeriodTimeByCycleIdStartTimeAndEndTime(cycle.getCycleId(),start,end);

            if (n==0){
                PeriodTime periodTime=new PeriodTime();
                periodTime.setPeriodTimeId(UUID.randomUUID()+"");
                periodTime.setStartTime(start);
                periodTime.setEndTime(end);

                //tallyRouterService.updatePeriodTime(periodTime);
                tallyRouterService.addPeriodTime(periodTime);
            }


        }

        String userId=tallyRouterService.findUserIdByUserName(tallyPostVO.getUserNames());
        RouteUser routeUser=new RouteUser();
        routeUser.setId(tallyRouterService.findIdByRouteIdAndUserId(tallyRoute.getRouteId(),userId));
      //  routeUser.setRouteId(tallyPostVO.getRouteId());

        //根据username获取userid
        routeUser.setUserId(userId);
        //routeUser.setUserId();
        //todo 根据身份的传值判断
        // 0 点检执行者 1 路线管理者 2 漏检负责人
        String identity=tallyPostVO.getIdentity();
        if (identity.equals("0")){
            routeUser.setIsPractitioners("0");//看情况
            routeUser.setIsHead("1");//看情况
            routeUser.setIsManagers("1");//看情况
        }else if (identity.equals("1")){
            routeUser.setIsPractitioners("1");//看情况
            routeUser.setIsHead("1");//看情况
            routeUser.setIsManagers("0");//看情况
        }else if (identity.equals("2")){
            routeUser.setIsPractitioners("1");//看情况
            routeUser.setIsHead("0");//看情况
            routeUser.setIsManagers("1");//看情况
        }else {
            routeUser.setIsPractitioners("1");//看情况
            routeUser.setIsHead("1");//看情况
            routeUser.setIsManagers("1");//看情况
        }
        //routeUser.setRemark(tallyPostVO.getIscUserRemark());
        tallyRouterService.updateRouteUser(routeUser);
        resultEntity.setCode(ErrorCode.SUCCESS);
        resultEntity.setMsg("更新点检路线相关信息成功!");

        return resultEntity;
    }


    @ApiOperation(value="获取人员匹配职务", notes="选定人员后判断是否有职务(0 是；1 不是)" ,httpMethod="GET")
    @RequestMapping("isGanHuo")
    @ResponseBody
    public ResultEntity isGanHuo(String userName) {
        ResultEntity resultEntity=new ResultEntity();

        //tallyRouterService.findPostByUserName(userName);

        resultEntity.setCode(ErrorCode.SUCCESS);
        resultEntity.setMsg("匹配人员职务成功!");
        resultEntity.setData(tallyRouterService.findPostByUserName(userName));
        return resultEntity;
    }


    @ApiOperation(value="获取路线编码后匹配风电场,路线id等信息", notes="选定路线编码后根据路线编码匹配风电场等" ,httpMethod="GET")
    @RequestMapping("routeCodeRelevant")
    @ResponseBody
    public ResultEntity routeCodeRelevant(String routeCode) {
        ResultEntity resultEntity=new ResultEntity();


        resultEntity.setCode(ErrorCode.SUCCESS);
        resultEntity.setMsg("匹配风电场信息成功!");
        resultEntity.setData(tallyRouterService.findRouteCodeRelevant(routeCode));
        return resultEntity;
    }

    @ApiOperation(value="获取路线id和周期名称后匹配周期相关信息", notes="根据路线id和周期名称获取周期相关信息" ,httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "routeId", value = "路线ID", required = false),
            @ApiImplicitParam(name = "cycleName", value = "周期名称", required = false),
    })
    @RequestMapping("cycleRelevant")
    @ResponseBody
    public ResultEntity cycleRelevant(String routeId,String cycleName) {
        ResultEntity resultEntity=new ResultEntity();
        CycleVO cycleVO=new CycleVO();

        Cycle cycle=tallyRouterService.findCycleByCycleNameAndRouteId(routeId, cycleName);

        List<PeriodTime> periodTimeList=tallyRouterService.findPeriodTimeByCycleId(cycle.getCycleId());

        cycleVO.setBenchmarkDate(cycle.getBenchmarkDate());
        cycleVO.setCycle(cycle.getCycle());
        cycleVO.setCycleId(cycle.getCycleId());
        cycleVO.setCycleName(cycle.getCycleName());
        cycleVO.setCycleUnit(cycle.getCycleUnit());
        cycleVO.setPeriodTimeList(periodTimeList);
        cycleVO.setRouteId(cycle.getRouteId());

        resultEntity.setCode(ErrorCode.SUCCESS);
        resultEntity.setMsg("匹配周期和时间段信息成功!");
        resultEntity.setData(cycleVO);
        return resultEntity;
    }

    @ApiOperation(value="删除时间段", notes="根据周期名称和开始结束时间删除时间段" ,httpMethod="DELETE")
    @RequestMapping("deletePeriodTime")
    @ResponseBody
    public ResultEntity deletePeriodTime(@RequestBody QDto qDto) {
        ResultEntity resultEntity=new ResultEntity();
        //删除时间段
        tallyRouterService.deletePeriodTimeByCycleNameAndStartTimeAndEndTime(qDto.getCycleName(), qDto.getStartTime(), qDto.getEndTime());

        //删除周期时间段关联


        resultEntity.setCode(ErrorCode.SUCCESS);
        resultEntity.setMsg("删除时间段成功!");
        return resultEntity;
    }


}
