/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.tender.config;
import cn.zhuatech.tender.model.*;
import cn.zhuatech.tender.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
@Configuration public class DataInitializer {
    @Bean CommandLineRunner seed(BusinessRecordRepository records,SystemSettingRepository settings){return args->{
        if(records.count()>0)return;
            settings.save(new SystemSetting("tenderMode","公开邀请"));
    settings.save(new SystemSetting("evaluationMethod","综合评分法"));
    settings.save(new SystemSetting("depositRequired","是"));
    settings.save(new SystemSetting("archiveYears","10"));
            records.save(new BusinessRecord("TND-20260826-001","PROJECT","2027年度云资源框架采购","信息技术部","采购经理","已发布",new BigDecimal("1800000"),5,LocalDate.now().plusDays(12),"正常","公开邀请合格供应商参与"));
    records.save(new BusinessRecord("TND-20260826-002","SUPPLIER","实施外包服务供应商资格预审","交付中心","供应商管理员","草稿",new BigDecimal("960000"),8,LocalDate.now().plusDays(6),"关注","两家供应商资质待补充"));
    records.save(new BusinessRecord("TND-20260826-003","BID","办公设备集采投标接收","行政中心","采购专员","已截标",new BigDecimal("420000"),6,LocalDate.now().plusDays(-1),"正常","保证金和投标文件均已登记"));
    records.save(new BusinessRecord("TND-20260826-004","EVALUATION","数据平台建设项目专家评审","数据中心","评审秘书","评审中",new BigDecimal("2600000"),4,LocalDate.now().plusDays(2),"正常","技术与商务评分进行中"));
    };}
}
