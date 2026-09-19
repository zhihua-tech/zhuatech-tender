/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.tender;
import org.junit.jupiter.api.Test;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.boot.test.context.SpringBootTest;import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;import org.springframework.http.MediaType;import org.springframework.test.web.servlet.MockMvc;import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@SpringBootTest @AutoConfigureMockMvc class EnterpriseTenderApiTests {@Autowired MockMvc mvc;
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @Test void multipleJudgesAreConsolidatedAndRanked() throws Exception {mvc.perform(post("/api/enterprise/tender/consolidate-evaluation").with(httpBasic("operator","operator123")).contentType(MediaType.APPLICATION_JSON).content("""
 {"technicalWeight":0.5,"commercialWeight":0.35,"serviceWeight":0.15,"outlierThreshold":15,"assessments":[
 {"vendorNo":"V1","vendorName":"甲供应商","judgeNo":"J1","technical":90,"commercial":85,"service":80,"compliancePassed":true},
 {"vendorNo":"V1","vendorName":"甲供应商","judgeNo":"J2","technical":88,"commercial":87,"service":82,"compliancePassed":true},
 {"vendorNo":"V2","vendorName":"乙供应商","judgeNo":"J1","technical":70,"commercial":75,"service":80,"compliancePassed":true},
 {"vendorNo":"V2","vendorName":"乙供应商","judgeNo":"J2","technical":72,"commercial":73,"service":78,"compliancePassed":true}]}
 """)).andExpect(status().isOk()).andExpect(jsonPath("$.data.vendors[0].vendorNo").value("V1")).andExpect(jsonPath("$.data.vendors[0].rank").value(1)).andExpect(jsonPath("$.data.decision").value("READY_FOR_AWARD"));}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @Test void outlierScoresRequireManualReview() throws Exception {mvc.perform(post("/api/enterprise/tender/consolidate-evaluation").with(httpBasic("operator","operator123")).contentType(MediaType.APPLICATION_JSON).content("""
 {"technicalWeight":0.5,"commercialWeight":0.35,"serviceWeight":0.15,"outlierThreshold":10,"assessments":[
 {"vendorNo":"V1","vendorName":"甲","judgeNo":"J1","technical":100,"commercial":100,"service":100,"compliancePassed":true},
 {"vendorNo":"V1","vendorName":"甲","judgeNo":"J2","technical":40,"commercial":40,"service":40,"compliancePassed":true}]}
 """)).andExpect(status().isOk()).andExpect(jsonPath("$.data.vendors[0].decision").value("MANUAL_REVIEW")).andExpect(jsonPath("$.data.decision").value("REVIEW_REQUIRED"));}
}
