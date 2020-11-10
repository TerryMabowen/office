//package com.mbw.office.dingtalk.biz.bu;
//
//import ai.bell.dingtalk.gw.client.DTClientFactory;
//import ai.bell.dingtalk.gw.provider.spi.bu.IBusinessService;
//import ai.bell.dingtalk.gw.provider.spi.bu.vo.BusinessUnitVO;
//import com.smthit.dubbo.ResponseBean;
//import com.smthit.lang.exception.ServiceException;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.List;
//
///**
// * 钉钉中台事业部服务
// *
// * @author Mabowen
// * @date 2020-10-28 15:51
// */
//@Slf4j
////@Service
////@Transactional(readOnly = true)
//public class DtBusinessUnitService {
//    private IBusinessService businessService;
//
//    public DtBusinessUnitService(DTClientFactory dtClientFactory) {
//        this.businessService = dtClientFactory.getBusinessService();
//    }
//
//    public List<BusinessUnitVO> listBusinessUnits() {
//        try {
//            ResponseBean<List<BusinessUnitVO>> responseBean = businessService.listBusinessUnits();
//            if (responseBean.getCode() == ResponseBean.OK) {
//                return responseBean.getData();
//            } else {
//                log.error("调用钉钉中台获取事业部集合接口异常：" + responseBean.toString());
//                throw new ServiceException("调用钉钉中台获取事业部集合接口异常：" + responseBean.toString());
//            }
//        } catch (Exception e) {
//            log.error("调用钉钉中台获取事业部集合接口异常：" + e.getMessage(), e);
//            throw new ServiceException("调用钉钉中台获取事业部集合接口异常：" + e.getMessage(), e);
//        }
//    }
//}
