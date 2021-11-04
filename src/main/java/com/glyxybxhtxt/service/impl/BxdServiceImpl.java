package com.glyxybxhtxt.service.impl;

import com.glyxybxhtxt.constant.GlmcConstants;
import com.glyxybxhtxt.dao.BxdMapper;
import com.glyxybxhtxt.dao.JdrMapper;
import com.glyxybxhtxt.dataObject.Bxd;
import com.glyxybxhtxt.dataObject.Jdr;
import com.glyxybxhtxt.service.BxdService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Author:wangzh
 * Date: 2020/12/3 20:17
 * Version: 1.0
 * Describe：保修单 service
 * sql 大部分都挤压在 BxdMapper，试着减少代码，如分为 SelectBxdMapper、InsertBxdMapper等等
 */
@Service
public class BxdServiceImpl implements BxdService {
    @Resource
    private BxdMapper bxdMapper;

    /**
     * 此处应该调用其他的service，不可直接调用其他的mapper
     */
    @Resource
    private JdrMapper jdrMapper;

    /**
     * 根据申报人学号，获取该申报人申报的报修单
     */
    @Override
    public List<Bxd> selforsbr(Bxd b) {
        return bxdMapper.selforsbr(b);
    }

    /**
     * 根据报修单id，获取该报修单，返回内容只要求有审核员、审核员是否同意、接单人id，二维码id的信息
     */
    @Override
    public Bxd selbxdforshyid(Integer id) {
        return bxdMapper.selbxdforshyid(id);
    }

    /**
     * 看不懂 q(≧▽≦q)
     */
    @Override
    public List<Bxd> selbxdbyadmin(Bxd b) {
        return bxdMapper.selbxdbyadmin(b);
    }

    /**
     * 根据二维码id，获取由该二维码扫描出的没有完成的报修单
     */
    @Override
    public List<Bxd> selbxdforeid(Integer eid) {
        return bxdMapper.selbxdforeid(eid);
    }

    /**
     * 根据接单人的易班id，获取由该接单人处理的报修单
     * 如果报修单的状态不为空、二维码id不为空，也加入判断中
     */
    @Override
    public List<Bxd> selbxdbyjdr(Bxd b) {
        return bxdMapper.selbxdbyjdr(b);
    }

    /**
     * 根据审核员id，获得该审核员  处理  过的报修单。报修单不包括已撤回的
     * 处理 = 审核通过 | 审核不通过
     * 如果二维码id不为空，获得的报修单是该二维码区域的
     */
    @Override
    public List<Bxd> selbxdbyshy(Bxd b) {
        return bxdMapper.selbxdbyshy(b);
    }

    /**
     * 两个方法还简写，直接疑惑 `(*>﹏<*)′
     * 根据申报人学号、报修单id，来更新该报修单
     * 其实就是 更新申报人申报的报修单
     * 其他更新条件：
     * 1.如果申报人撤销了，则将报修单状态改为已撤销
     * 2.如果申报人进行评价，则将该报修单的评价体系 加上 评价星级和内容
     * 3.如果申报人追加评价，则将该报修单的评价体系 加上 该追加评价内容
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upbxdbysbr(Bxd b) {
        bxdMapper.updateByPrimaryKeySelectivebysbr(b);
    }

    /**
     * 当审核员1处理报修单，则根据该报修单id，来进行更新处理结果
     * 其他更新条件：
     * 1.如果耗材了，则在报修单表加上耗材内容
     * 2.如果接单人消耗工时了，则在报修单表加上消耗的工时
     * 3.审核员2也处理了报修单，则在报修单表上加上处理结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upbxd1byshy(Bxd b) {
        bxdMapper.upbxd1byshy(b);
    }

    /**
     * 和上方同理
     * 只是审核员1和审核员2调换位置，即第一行注释是审核员2，第五行注释是审核员1
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upbxd2byshy(Bxd b) {
        bxdMapper.upbxd2byshy(b);
    }

    /**
     * 当审核员1或者审核员2处理报修单，则根据该报修单id，来进行更新处理结果
     * 其他更新条件：
     * 1.如果耗材了，则在报修单表加上耗材内容
     * 2.如果接单人消耗工时了，则在报修单表加上消耗的工时
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int upbxdhcbyshy(Bxd b) {
        return bxdMapper.upbxdhcbyshy(b);
    }

    /**
     * 看sql吧，只要表的任意字段不为空，则进行更新
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upbxdbyadmin(Bxd b) {
        bxdMapper.upbxdbyadmin(b);
    }

    /**
     * 更新申报时间、交单时间、验收时间
     * 其他条件：看sql，好多任意字段不为空，都更新
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void fg(Bxd b) {
        bxdMapper.fg(b);
    }

    /**
     * 通过接单人id和报修单id，来更新该报修单
     * 其他条件自己看吧
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upbxdbyjdr(Bxd b) {
        bxdMapper.upbxdbyjdr(b);
    }

    /**
     * 根绝报修单id，删除该报修单
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(Integer id) {
        bxdMapper.deleteByPrimaryKey(id);
    }

    /**
     * 插入数据到报修单表中
     * 具体有哪些字段数据，看sql
     */
    @Override
    public int newbxdbysbr(Bxd b) {
        return bxdMapper.insert(b);
    }

    /**
     * 查询不同状态的报修单总数
     * 如已派单的报修单有多少个
     * 已维修的报修单有多少个
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int selnumforstate(int b) {
        return bxdMapper.selnumforstate(b);
    }

    /**
     * 查询不同评价星级的报修单总和
     * 如 5星的报修单有多少个
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int selnumforpj(int b) {
        return bxdMapper.selnumforpj(b);
    }

    /**
     * 查询一共有多少个报修单
     * 是表里的全部报修单
     */
    @Override
    public int allcount() {
        return bxdMapper.allcount();
    }

    /**
     * 查询接单人当天 已维修和已验收 报修单的所花费的工时
     * 工时四舍五入计算，保留小数点 12 位
     */
    @Override
    public Double selgs(String jid) {
        return bxdMapper.selgs(GlmcConstants.GS, jid);
    }

    /**
     * 获取一些不同状态的报修单数量
     */
    @Override
    public String tj() {
        JSONObject json = new JSONObject();
        // 当天申报的报修单数量
        json.put("daybx", bxdMapper.daybx());
        // 当天维护的报修单数量
        json.put("daywx", bxdMapper.daywx());
        // 本月内维护的保修单数量
        json.put("monthwx", bxdMapper.monthwx());
        // 本月内申报的保修单数量
        json.put("monthbx", bxdMapper.monthbx());
        // 本年内维护的保修单数量
        json.put("yearwx", bxdMapper.yearwx());
        // 本年内申报的保修单数量
        json.put("yearbx", bxdMapper.yearbx());
        return json.toString();
    }

    /**
     * 根据申报人学号，获取他最新申报的报修单
     * 可能申报了多个，这方法只获取最新时间的那个
     */
    @Override
    public int getsbrbxdnewid(String xh) {
        return bxdMapper.getsbrbxdnewid(xh);
    }

    /**
     * 更新报修单的状态
     * 其他更新条件：
     * 1.如果验收时间不为空，则也进行更新
     */
    @Override
    public int upbxdbyysr(Bxd b) {
        return bxdMapper.upbxdbyysr(b);
    }

    /**
     * 根据报修单id，查询该报修单的耗材数据
     */
    @Override
    public String selishc(Integer id) {
        return bxdMapper.selishc(id);
    }

    /**
     * 根据报修单id，查询该报修单的全部信息
     */
    @Override
    public Bxd selonebxd(Integer id) {
        return bxdMapper.selectByPrimaryKey(id);
    }

    /**
     * 查看在休息时间内  有一个审核员审核，另一个审核员未审核的报修单的报修单
     * 休息时间：当天 18点 到第二天 10点
     */
    @Override
    public List<Bxd> xxsjBxd() {
        return bxdMapper.xxsjbxd();
    }

    /**
     * 查询前20个未派单的报修单
     */
    @Override
    public List<Bxd> selwpdbxd() {
        return bxdMapper.selwpdbxd();
    }

    /**
     * 根据一个map，查询符合条件的报修单
     * map包含的条件：
     * 1.当status状态不为空，则返回对应状态的报修单。支持多个。状态对应： 1已派单，2已维修，3撤销单，4已验收，5不通过验收
     * 2.当taskFgts不为空，则获取返工天数大于0的报修单
     * 3.当taskPjnr不为空，则获取评价内容为空、评价星级为空、返工天数为0的报修单
     */
    @Override
    public List<Bxd>  selBxdByMap(Map<String, Object> params) {
        return bxdMapper.selBxdByMap(params);
    }

    /**
     * 根据多个id，批量更新对应id的报修单
     * 更新条件：
     * 1.taskFgts不为空，代表返工天数未满15天，则进行返工天数减少1
     * 2.taskPjnr不为空，代表返工天数到达15天，则默认五星评价星级评价，内容为好评
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBatch(Map<String, Object> params) {
        return bxdMapper.updateBatch(params);
    }

    // ========================== 混入了接单人的mapper ==================================
    /**
     * 仅仅查询出有 接单人易班id、业务范围，不同状态的接单人信息
     * 还包括 所有在职的易班id总数
     */
    @Override
    public List<Jdr> seljdrforpd() {
        return jdrMapper.seljdrforpd();
    }
}
