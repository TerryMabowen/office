package com.mbw.office.demo.biz.statistics;

import com.github.binarywang.wxpay.bean.request.WxPayDownloadBillRequest;
import com.mbw.office.common.util.collection.CollectionUtil;
import com.mbw.office.common.util.date.DateUtil;
import com.mbw.office.common.util.json.GsonUtil;
import com.mbw.office.demo.biz.jalian.JlBillService;
import com.mbw.office.demo.biz.jalian.model.JlBill;
import com.mbw.office.demo.biz.jalian.model.JlBillDetail;
import com.mbw.office.demo.biz.statistics.dto.BudgetDepartmentJlBillDTO;
import com.mbw.office.demo.biz.statistics.dto.BudgetDepartmentWxBillDTO;
import com.mbw.office.demo.biz.statistics.dto.DepartmentAmountReceivableDTO;
import com.mbw.office.demo.biz.statistics.vo.DepartmentAmountReceivableVO;
import com.mbw.office.demo.biz.weixin.WxConfigFactory;
import com.mbw.office.demo.biz.weixin.model.WxBill;
import com.mbw.office.demo.biz.weixin.model.WxBillDetail;
import com.mbw.office.demo.biz.weixin.service.WxBillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 预算统计服务
 *
 * @author Mabowen
 * @date 2020-08-05 15:13
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class BudgetStatisticsService {
    @Autowired
    private JlBillService jlBillService;

    @Autowired
    private WxBillService wxBillService;
    private static final String ROOT_PATH = "/Users/apple_22/Desktop/F100/ding-bill/jl/";
    private static String[] settlementDayArray = {"2020-06-10", "2020-06-18", "2020-07-10", "2020-08-10", "2020-08-12", "2020-08-16", "2020-09-10"};
    private static String[] months = {"2020-05-01", "2020-06-01", "2020-07-01", "2020-08-01", "2020-09-01", "2020-10-01"};

    public List<DepartmentAmountReceivableVO> listDepartmentCurrentAmountReceivables() {
        List<DepartmentAmountReceivableVO> vos = new ArrayList<>();
        Date monthBegin = DateUtil.parseShort(DateUtil.getMonthBegin(new Date()));
        Date monthEnd = DateUtil.parseShort(DateUtil.getMonthEnd(new Date()));

        List<DepartmentAmountReceivableDTO> dtos = listDepartmentAmountReceivables(monthBegin, monthEnd);

        if (CollectionUtil.isNotEmpty(dtos)) {
            Random random = new Random();
            DepartmentAmountReceivableVO vo = new DepartmentAmountReceivableVO();
            vo.setDepartmentId(dtos.get(0).getDepartmentId());
            vo.setDepartmentName(dtos.get(0).getDepartmentName());
            vo.setStartDate(monthBegin);
            vo.setEndDate(monthEnd);

            int i = random.nextInt(1);
            vo.setBudgetRuleType(i == 0 ? 1 : 2);
            vo.setBudgetRuleDesc("规则" + vo.getBudgetRuleType() + ": " + dtos.get(0).getDepartmentName());

            BigDecimal totalAmountReceivable = dtos.stream().filter(d -> d.getAmountReceivable() != null).map(DepartmentAmountReceivableDTO::getAmountReceivable).reduce(BigDecimal.ZERO, BigDecimal::add);
            vo.setTotalAmountReceivable(totalAmountReceivable);

            long count = 0;
            for (DepartmentAmountReceivableDTO d : dtos) {
                if (d.getTotalRecords() != null) {
                    count += d.getTotalRecords();
                }
            }
            vo.setTotalRecords(count);

            log.error("{}日到{}日的部门收入为{}", DateUtil.formatShort(monthBegin), DateUtil.formatShort(monthEnd), GsonUtil.beanToJson(vo));
            vos.add(vo);
        }

        return vos;
    }

    public List<DepartmentAmountReceivableVO> listDepartmentHistoryAmountReceivables() {
        List<DepartmentAmountReceivableVO> vos = new ArrayList<>();
        for (String month : months) {
            List<DepartmentAmountReceivableVO> mvos = new ArrayList<>();
            Date monthBegin = DateUtil.parseShort(DateUtil.getMonthBegin(DateUtil.parseShort(month)));
            Date monthEnd = DateUtil.parseShort(DateUtil.getMonthEnd(DateUtil.parseShort(month)));

            List<DepartmentAmountReceivableDTO> dtos = listDepartmentAmountReceivables(monthBegin, monthEnd);

            if (CollectionUtil.isNotEmpty(dtos)) {
                Random random = new Random();
                DepartmentAmountReceivableVO vo = new DepartmentAmountReceivableVO();
                vo.setDepartmentId(dtos.get(0).getDepartmentId());
                vo.setDepartmentName(dtos.get(0).getDepartmentName());
                vo.setStartDate(monthBegin);
                vo.setEndDate(monthEnd);

                int i = random.nextInt(1);
                vo.setBudgetRuleType(i == 0 ? 1 : 2);
                vo.setBudgetRuleDesc("规则" + vo.getBudgetRuleType() + ": " + dtos.get(0).getDepartmentName());

                BigDecimal totalAmountReceivable = dtos.stream().filter(d -> d.getAmountReceivable() != null).map(DepartmentAmountReceivableDTO::getAmountReceivable).reduce(BigDecimal.ZERO, BigDecimal::add);
                vo.setTotalAmountReceivable(totalAmountReceivable);

                long count = 0;
                for (DepartmentAmountReceivableDTO d : dtos) {
                    if (d.getTotalRecords() != null) {
                        count += d.getTotalRecords();
                    }
                }
                vo.setTotalRecords(count);

                log.error("{}日到{}日的部门收入为{}", DateUtil.formatShort(monthBegin), DateUtil.formatShort(monthEnd), GsonUtil.beanToJson(vo));
                vos.add(vo);
            }
        }

        return vos;
    }

    public List<BudgetDepartmentJlBillDTO> listDepartmentJlBills() {
        String[] paths = {"51010718062610109608_20190605_5.txt", "51010718062610109608_20190606_5.txt", "51010718062610109608_20190607_5.txt", "51010718062610109608_20190608_5.txt"};
        List<JlBill> jlBills = new ArrayList<>();
        for (String path : paths) {
            jlBills.addAll(jlBillService.getJlBill(ROOT_PATH + path));
        }

        List<JlBillDetail> jlBillDetails = new ArrayList<>();
        Random random = new Random();
        for (JlBill jlBill : jlBills) {
            List<JlBillDetail> records = jlBill.getRecords();
            records.forEach(d -> {
                int i = random.nextInt(7);
                d.setDepartmentId((long) i);
                d.setSettlementDate(DateUtil.parseShort(settlementDayArray[i]));
            });

            jlBillDetails.addAll(records);
        }
        jlBillDetails = CollectionUtil.mergeList(jlBillDetails);

        Map<String, List<JlBillDetail>> jlBillDetailGroups = jlBillDetails.stream().collect(Collectors.groupingBy(bill -> bill.getDepartmentId() + "_" + DateUtil.formatShort(bill.getSettlementDate())));

        List<BudgetDepartmentJlBillDTO> dtos = new ArrayList<>();
        jlBillDetailGroups.keySet().forEach(key -> {
            Long departmentId = Long.parseLong(key.substring(0, key.indexOf("_")));
            Date settlementDate = DateUtil.parseShort(key.substring(key.indexOf("_") + 1));
            List<JlBillDetail> details = jlBillDetailGroups.get(key);

            BudgetDepartmentJlBillDTO dto = new BudgetDepartmentJlBillDTO();
            dto.setDepartmentId(departmentId);
            dto.setDepartmentName("部门" + key);
            dto.setSettlementDate(settlementDate);
            dto.setTotalRecord((long) details.size());

            BigDecimal tradeMoney = details.stream().filter(bill -> bill.getTradeMoney() != null).map(JlBillDetail::getTradeMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
            dto.setTradeMoney(tradeMoney);

            BigDecimal commissions = details.stream().filter(bill -> bill.getCommissions() != null).map(JlBillDetail::getCommissions).reduce(BigDecimal.ZERO, BigDecimal::add);
            dto.setCommissions(commissions);

            BigDecimal channelCommissions = details.stream().filter(bill -> bill.getChannelCommissions() != null).map(JlBillDetail::getChannelCommissions).reduce(BigDecimal.ZERO, BigDecimal::add);
            dto.setChannelCommissions(channelCommissions);

            BigDecimal extraCommissions = details.stream().filter(bill -> bill.getExtraCommissions() != null).map(JlBillDetail::getExtraCommissions).reduce(BigDecimal.ZERO, BigDecimal::add);
            dto.setExtraCommissions(extraCommissions);

            BigDecimal channelFee = details.stream().filter(bill -> bill.getChannelFee() != null).map(JlBillDetail::getChannelFee).reduce(BigDecimal.ZERO, BigDecimal::add);
            dto.setChannelFee(channelFee);

            //收入
            dto.setAmountReceivable(dto.getTradeMoney());

            dtos.add(dto);
        });

        return dtos;
    }

    public List<BudgetDepartmentWxBillDTO> listDepartmentWxBills() {
        List<WxBill> wxBills = new ArrayList<>();
        String[] billDates = {"20200609", "20200610", "20200612", "20200613", "20200706", "20200716", "20200720"};
        //通过查询数据库或者根据传递的参数操作
        List<WxConfigFactory.AppConfig> list = new ArrayList<>();
        list.add(new WxConfigFactory.AppConfig("wxba30f2ef0e485274", "1540637701", "A4659A63476446D29526D8FC78BJLEGO"));
        list.add(new WxConfigFactory.AppConfig("wx8c9148cee130fba4", "1540637701", "A4659A63476446D29526D8FC78BJLEGO"));
        Random rd = new Random();
        for (WxConfigFactory.AppConfig appConfig : list) {
            String key = appConfig.getAppId() + "_" + appConfig.getMchId();
            WxPayDownloadBillRequest request = new WxPayDownloadBillRequest();
            int i = rd.nextInt(7);
            request.setBillDate(billDates[i]);
            request.setBillType("ALL");

            wxBills.addAll(wxBillService.downloadBill(request, key));
        }

        wxBills = CollectionUtil.mergeList(wxBills);

        List<WxBillDetail> wxBillDetails = new ArrayList<>();
        for (WxBill wxBill : wxBills) {
            List<WxBillDetail> records = wxBill.getRecords();
            records.forEach(d -> {
                int i = rd.nextInt(7);
                d.setDepartmentId((long) i);
                d.setSettlementDate(DateUtil.parseShort(settlementDayArray[i]));
            });

            wxBillDetails.addAll(records);
        }

        wxBillDetails = CollectionUtil.mergeList(wxBillDetails);

        Map<String, List<WxBillDetail>> wxBillDetailGroups = wxBillDetails.stream().collect(Collectors.groupingBy(bill -> bill.getDepartmentId() + "_" + DateUtil.formatShort(bill.getSettlementDate())));

        List<BudgetDepartmentWxBillDTO> dtos = new ArrayList<>();
        wxBillDetailGroups.keySet().forEach(key -> {
            Long departmentId = Long.parseLong(key.substring(0, key.indexOf("_")));
            Date settlementDate = DateUtil.parseShort(key.substring(key.indexOf("_") + 1));
            List<WxBillDetail> details = wxBillDetailGroups.get(key);

            BudgetDepartmentWxBillDTO dto = new BudgetDepartmentWxBillDTO();
            dto.setDepartmentId(departmentId);
            dto.setDepartmentName("部门" + key);
            dto.setSettlementDate(settlementDate);
            dto.setTotalRecord((long) details.size());

            BigDecimal totalFee = details.stream().filter(bill -> bill.getTotalFee() != null).map(WxBillDetail::getTotalFee).reduce(BigDecimal.ZERO, BigDecimal::add);
            dto.setTotalFee(totalFee);

            BigDecimal settlementRefundFee = details.stream().filter(bill -> bill.getSettlementRefundFee() != null).map(WxBillDetail::getSettlementRefundFee).reduce(BigDecimal.ZERO, BigDecimal::add);
            dto.setSettlementRefundFee(settlementRefundFee);

            BigDecimal couponFee = details.stream().filter(bill -> bill.getCouponFee() != null).map(WxBillDetail::getCouponFee).reduce(BigDecimal.ZERO, BigDecimal::add);
            dto.setCouponFee(couponFee);

            BigDecimal poundage = details.stream().filter(bill -> bill.getPoundage() != null).map(WxBillDetail::getPoundage).reduce(BigDecimal.ZERO, BigDecimal::add);
            dto.setPoundage(poundage);

            BigDecimal couponRefundFee = details.stream().filter(bill -> bill.getCouponRefundFee() != null).map(WxBillDetail::getCouponRefundFee).reduce(BigDecimal.ZERO, BigDecimal::add);
            dto.setCouponRefundFee(couponRefundFee);

            BigDecimal totalAmount = details.stream().filter(bill -> bill.getTotalAmount() != null).map(WxBillDetail::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            dto.setTotalAmount(totalAmount);

            BigDecimal appliedRefundAmount = details.stream().filter(bill -> bill.getAppliedRefundAmount() != null).map(WxBillDetail::getAppliedRefundAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            dto.setAppliedRefundAmount(appliedRefundAmount);

            //收入
            dto.setAmountReceivable(dto.getTotalFee());

            dtos.add(dto);
        });

        return dtos;
    }

    private List<DepartmentAmountReceivableDTO> listDepartmentAmountReceivables(Date monthBegin, Date monthEnd) {
        List<BudgetDepartmentJlBillDTO> jlDtos = listDepartmentJlBills();
        List<BudgetDepartmentWxBillDTO> wxDtos = listDepartmentWxBills();
        List<DepartmentAmountReceivableDTO> dtos = new ArrayList<>();

        jlDtos.forEach(jl -> {
            Date settlementDate = jl.getSettlementDate();
            if (settlementDate.compareTo(monthBegin) >= 0 && settlementDate.compareTo(monthEnd) <= 0) {
                DepartmentAmountReceivableDTO dto = new DepartmentAmountReceivableDTO();
                dto.setDepartmentId(jl.getDepartmentId());
                dto.setDepartmentName(jl.getDepartmentName());
                dto.setStartDate(monthBegin);
                dto.setEndDate(monthEnd);
                dto.setAmountReceivable(jl.getAmountReceivable());
                dto.setTotalRecords(jl.getTotalRecord());
                dtos.add(dto);
            }
        });

        Map<String, DepartmentAmountReceivableDTO> dtoGroups = dtos.stream().collect(Collectors.toMap(d -> d.getDepartmentId() + "_" + DateUtil.formatShort(d.getStartDate()) + "_" + DateUtil.formatShort(d.getEndDate()), Function.identity()));

        wxDtos.forEach(wx -> {
            Date settlementDate = wx.getSettlementDate();
            if (settlementDate.compareTo(monthBegin) >= 0 && settlementDate.compareTo(monthEnd) <= 0) {
                String key = wx.getDepartmentId() + "_" + DateUtil.formatShort(monthBegin) + "_" + DateUtil.formatShort(monthEnd);
                DepartmentAmountReceivableDTO dto = dtoGroups.get(key);
                if (null == dto) {
                    DepartmentAmountReceivableDTO wxDto = new DepartmentAmountReceivableDTO();
                    wxDto.setDepartmentId(wx.getDepartmentId());
                    wxDto.setDepartmentName(wx.getDepartmentName());
                    wxDto.setStartDate(monthBegin);
                    wxDto.setEndDate(monthEnd);
                    wxDto.setAmountReceivable(wx.getAmountReceivable());
                    wxDto.setTotalRecords(wx.getTotalRecord());

                    dtos.add(wxDto);
                } else {
                    if (dto.getAmountReceivable() != null && wx.getAmountReceivable() != null) {
                        dto.setAmountReceivable(dto.getAmountReceivable().add(wx.getAmountReceivable()));
                    } else {
                        dto.setAmountReceivable(wx.getAmountReceivable());
                    }

                    if (dto.getTotalRecords() != null && wx.getTotalRecord() != null) {
                        dto.setTotalRecords(dto.getTotalRecords() + wx.getTotalRecord());
                    } else {
                        dto.setTotalRecords(wx.getTotalRecord());
                    }
                }
            }
        });

        return dtos;
    }
}
