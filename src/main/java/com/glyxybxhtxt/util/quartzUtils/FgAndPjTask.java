package com.glyxybxhtxt.util.quartzUtils;

import com.glyxybxhtxt.dataObject.Bxd;
import com.glyxybxhtxt.service.impl.BxdServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @Author wzh
 * @Date 2021/8/28 11:05 下午
 * @Description
 */
@Slf4j
@Component
public class FgAndPjTask {
    @Autowired
    private BxdServiceImpl bs;


    public void executeFgAndPjTask() {
        log.info("返工天数减一以及15天后默认好评 定时任务开启");
        reduceFgts();
        setPjnr();
        log.info("返工天数减一以及15天后默认好评 定时任务结束");
    }

    public void reduceFgts() {
        // 等价于 Map<String, Object> params = new HashMap<...>(3/1.75F);
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        // states：1已派单，2已维修，3撤销单，4已验收，5不通过验收
        params.put("states", Lists.newArrayList(2, 4));
        // taskFgts：返工天数-1
        params.put("taskFgts", "-1");
        // 获取返工天数大于0的已维修和已验收的保修单
        List<Bxd> bxds = bs.selBxdByMap(params);
        // 获取到需要减-1的保修单id
        List<Integer> reduceFgtsIds = bxds.stream().map(Bxd::getId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(reduceFgtsIds)) {
            return;
        }
        log.info("需要减少返工天数的报修单id:{}",reduceFgtsIds);
        params.put("ids", reduceFgtsIds);
        // 返工天数减1
        bs.updateBatch(params);
    }

    public void setPjnr(){
        // 同理，参考上方注释
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(4);
        // 注意这里，4代表不通过验收，即天数满15天，但是没手动验收的，就能获取到这些订单。然后这里自动给订单好评
        params.put("states", Lists.newArrayList(4));
        // taskPjnr：评价内容
        params.put("taskPjnr", "null");
        List<Bxd> bxds = bs.selBxdByMap(params);
        List<Integer> setPjnrIds = bxds.stream().map(Bxd::getId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(setPjnrIds)) {
            return;
        }
        log.info("需要设置默认评价内容的报修单id:{}",setPjnrIds);
        params.put("taskPjnr", "默认五星好评");
        params.put("ids", setPjnrIds);
        bs.updateBatch(params);
    }

}
