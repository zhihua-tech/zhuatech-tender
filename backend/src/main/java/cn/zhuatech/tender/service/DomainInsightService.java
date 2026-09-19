/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.tender.service;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.math.*;
import java.util.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class DomainInsightService {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Map<String,Object> analyze(InsightRequest req){
        Map<String,Object> result=new LinkedHashMap<>();
        BigDecimal score=req.technicalScore().multiply(new BigDecimal("0.50")).add(req.commercialScore().multiply(new BigDecimal("0.35"))).add(req.serviceScore().multiply(new BigDecimal("0.15"))).setScale(2,RoundingMode.HALF_UP);
String decision=req.disqualified()||!req.compliancePassed()?"DISQUALIFIED":score.compareTo(new BigDecimal("80"))>=0?"SHORTLIST":"NOT_SHORTLISTED";
result.put("weightedScore",score);result.put("compliancePassed",req.compliancePassed());result.put("decision",decision);
        return result;
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private BigDecimal rate(long numerator,long denominator){return denominator==0?BigDecimal.ZERO:BigDecimal.valueOf(numerator).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(denominator),2,RoundingMode.HALF_UP);}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record InsightRequest(@DecimalMin("0.0") @DecimalMax("100.0") BigDecimal technicalScore, @DecimalMin("0.0") @DecimalMax("100.0") BigDecimal commercialScore, @DecimalMin("0.0") @DecimalMax("100.0") BigDecimal serviceScore, boolean compliancePassed, boolean disqualified){}
}
