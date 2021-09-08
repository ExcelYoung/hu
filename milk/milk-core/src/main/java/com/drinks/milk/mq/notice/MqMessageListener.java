package com.drinks.milk.mq.notice;

//import com.aliyun.openservices.ons.api.Action;
//import com.aliyun.openservices.ons.api.ConsumeContext;
//import com.aliyun.openservices.ons.api.Message;
//import com.aliyun.openservices.ons.api.MessageListener;
//import com.entity.response.Result;
//import com.insuranceman.chaos.enums.insure.ChaosInsureOrderStatusEnum;
//import com.insuranceman.chaos.enums.insure.ChaosOrderChannelTypeEnum;
//import com.insuranceman.chaos.enums.insure.ChaosOrderSourceTypeEnum;
//import com.insuranceman.chaos.enums.notice.NoticeStateEnum;
//import com.insuranceman.chaos.mapper.order.ChaosOrderMapper;
//import com.insuranceman.chaos.model.notice.Notice;
//import com.insuranceman.chaos.model.order.ChaosOrder;
//import com.insuranceman.chaos.model.order.ChaosOrderApplicant;
//import com.insuranceman.chaos.model.req.notice.OldNotice;
//import com.insuranceman.chaos.model.user.ChaosUserDTO;
//import com.insuranceman.chaos.service.notice.NoticeService;
//import com.insuranceman.chaos.service.order.ChaosOrderApplicantService;
//import com.insuranceman.chaos.service.user.ChaosCommonUserService;
//import com.insuranceman.chaos.utils.DateUtils;
//import com.itextpdf.text.DocumentException;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import java.io.IOException;
//import java.util.Date;
import org.springframework.stereotype.Component;
/**
 * 普通（默认同步）MQ消息监听消费
 *
 * @author laifuwei
 */
@Component
public class MqMessageListener {
//        implements MessageListener {
//
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Autowired
//    private ChaosOrderApplicantService chaosOrderApplicantService;
//
//    @Autowired
//    private NoticeService noticeService;
//
//    @Autowired
//    private ChaosOrderMapper chaosOrderMapper;
//
//    @Autowired
//    private ChaosCommonUserService chaosCommonUserService;
//
//    @Override
//    public Action consume(Message message, ConsumeContext context) {
//        logger.info("接收到MQ_Notice消息. Topic :" + message.getTopic() + ", tag :" + message.getTag() + " msgId : " + message.getMsgID() + ", Key :" + message.getKey() + ", body:" + new String(message.getBody()));
//        try {
//            String id = new String(message.getBody());
//            Long aLong = Long.valueOf(id);
//            ChaosOrder chaosOrder = chaosOrderMapper.selectById(aLong);
//            process(chaosOrder);
//            return Action.CommitMessage;
//        } catch (Exception e) {
//            logger.error("消费MQ消息失败！ msgId:" + message.getMsgID() + "----ExceptionMsg:" + e.getMessage());
//            return Action.ReconsumeLater;
//        }
//    }
//
//    public void process(ChaosOrder testMessage) throws IOException, DocumentException {
//        logger.info("DirectReceiver消费者收到消息  : " + testMessage.getId() + " userid:" + testMessage.getUserId()
//                + " orderCode:" + testMessage.getOrderCode());
//        // 查询订单表 投保订单不为空 并且 是个险保单
//        if (testMessage != null && StringUtils.isNotBlank(testMessage.getPrintNo())
//                && isMatch(testMessage)) {
//            Notice noticeByPrintId = noticeService.getNoticeByPrintId(testMessage.getPrintNo());
//            if (noticeByPrintId == null) {
//                if (Integer.valueOf(testMessage.getStatus()) == ChaosInsureOrderStatusEnum.APPROVAL.getCode()
//                        || ChaosInsureOrderStatusEnum.RECEIPTED.getCode() == Integer.valueOf(testMessage.getStatus())
//                        || ChaosInsureOrderStatusEnum.VISITED.getCode() == Integer.valueOf(testMessage.getStatus())) {
//                    // 查询告知书是否以前被删除过 如果删除过就恢复
//                    Integer noticeByPrintIdIgnorDelete = noticeService.getNoticeByPrintIdIgnorDelete(testMessage.getPrintNo());
//                    if (noticeByPrintIdIgnorDelete != null) {
//                        noticeService.updateUnDeleteById(noticeByPrintIdIgnorDelete);
//                        return;
//                    }
//                    String userId = testMessage.getUserId();
//                    String orderCode = testMessage.getOrderCode();
//                    Long orderId = testMessage.getId();
//                    String printNo = testMessage.getPrintNo();
//                    String companyName = testMessage.getCompanyName();
//                    String goodsName = testMessage.getGoodsName();
//                    Long premium = testMessage.getPremium();
//                    Date insureTime = testMessage.getInsureTime();
//                    String companyCode = testMessage.getCompanyCode();
//                    String goodsCode = testMessage.getGoodsCode();
//                    String orgNo = testMessage.getOrgNo();
//                    Notice notice = new Notice();
//                    notice.setOrgNo(orgNo);
//                    notice.setUserId(userId);
//                    notice.setOrderCode(orderCode);
//                    notice.setPrintId(printNo);
//                    notice.setCompany(companyName);
//                    notice.setCompanyId(companyCode);
//                    notice.setGoodsName(goodsName);
//                    notice.setGoodsId(goodsCode);
//                    notice.setState(NoticeStateEnum.UNSIGNED.getState());
//
//                    ChaosOrderApplicant applicant = chaosOrderApplicantService.getByOrderId(orderId);
//                    if (applicant != null) {
//                        String name = applicant.getName();
//                        String mobile = applicant.getMobile();
//                        notice.setName(name);
//                        notice.setMobile(mobile);
//                    }
//                    notice.setPrem(String.valueOf(premium));
//                    notice.setApplyDate(insureTime);
//                    notice.setCreateTime(new Date());
//                    if (StringUtils.isNotBlank(notice.getName()) && StringUtils.isNotBlank(notice.getMobile())) {
//                        // 网销api直投默认设置告知书状态为已确认
//                        if (ChaosOrderSourceTypeEnum.INUSRE.getCode().equals(testMessage.getChannelId()) &&
//                                ChaosOrderChannelTypeEnum.NET.getCode().equals(testMessage.getChannelType())) {
//                            notice.setState(NoticeStateEnum.CONFIRM.getState());
//                            Result<ChaosUserDTO> chaosUser = chaosCommonUserService.selectChaosUser(userId);
//                            String brokerCode = null;
//                            if (chaosUser != null) {
//                                ChaosUserDTO data = chaosUser.getData();
//                                brokerCode = data.getBrokerCode();
//                            }
//                            String tem = noticeService.pdfAddFieldValue(notice.getPrintId(), DateUtils.format(notice.getApplyDate()), brokerCode);
//                            notice.setPdfUrl(tem);
//                        }
//                        noticeService.insertSelective(notice);
//                    } else {
//                        logger.info("DirectReceiver2缺失name和mobile,orderId:{}", testMessage.getId());
//                    }
//                }
//            } else {
//                Integer id = noticeByPrintId.getId();
//                if (ChaosInsureOrderStatusEnum.REVOKED.getCode() == Integer.valueOf(testMessage.getStatus())) {
//                    noticeService.updateDeleteById(id);
//                } else {
//                    // 记录已经存在 对比一下uesrid orderCode name mobile
//                    String userId = noticeByPrintId.getUserId();
//                    String orderCode = noticeByPrintId.getOrderCode();
//                    String name = noticeByPrintId.getName();
//                    String mobile = noticeByPrintId.getMobile();
//                    String appname = null;
//                    String appmobile = null;
//                    ChaosOrderApplicant applicant = chaosOrderApplicantService.getByOrderId(testMessage.getId());
//                    if (applicant != null) {
//                        appname = applicant.getName();
//                        appmobile = applicant.getMobile();
//                    }
//                    if (testMessage.getUserId() != null && testMessage.getOrderCode() != null
//                            && appname != null && appmobile != null) {
//                        if (!testMessage.getUserId().equals(userId) ||
//                                !testMessage.getOrderCode().equals(orderCode) ||
//                                !appname.equals(name) ||
//                                !appmobile.equals(mobile)) {
//                            // 更新notice的值
//                            OldNotice oldNotice = new OldNotice();
//                            oldNotice.setMobile(appmobile);
//                            oldNotice.setName(appname);
//                            oldNotice.setOrderCode(testMessage.getOrderCode());
//                            oldNotice.setUserId(testMessage.getUserId());
//                            oldNotice.setId(id);
//                            noticeService.updateOldNoticeById(oldNotice);
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private boolean isMatch(ChaosOrder testMessage) {
//        String channelId = testMessage.getChannelId();
//        String channelType = testMessage.getChannelType();
//        // 个险核心/网销cps生成告知书
//        if (ChaosOrderSourceTypeEnum.HX.getCode().equals(channelId) ||
//                (ChaosOrderChannelTypeEnum.NET.getCode().equals(channelType) && ChaosOrderSourceTypeEnum.CPS.getCode().equals(channelId))) {
//            return true;
//        }
//        if (ChaosOrderSourceTypeEnum.INUSRE.getCode().equals(channelId)) {
//            if (ChaosOrderChannelTypeEnum.PERSONAL.getCode().equals(channelType) || ChaosOrderChannelTypeEnum.NET.getCode().equals(channelType)) {
//                return true;
//            }
//        }
//        return false;
//    }
}