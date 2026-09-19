/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.tender.service;
import jakarta.validation.Valid;import jakarta.validation.constraints.*;import org.springframework.stereotype.Service;import java.math.*;import java.util.*;import java.util.stream.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service public class EnterpriseTenderService {
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public EvaluationResult consolidate(@Valid EvaluationRequest req){
  Map<String,List<ScoredAssessment>> grouped=new LinkedHashMap<>();
  for(var item:req.assessments()){
   BigDecimal score=item.technical().multiply(req.technicalWeight()).add(item.commercial().multiply(req.commercialWeight())).add(item.service().multiply(req.serviceWeight()));
   grouped.computeIfAbsent(item.vendorNo(),k->new ArrayList<>()).add(new ScoredAssessment(item,score));
  }
  List<VendorResult> vendors=new ArrayList<>();
  grouped.forEach((vendor,items)->{
   BigDecimal average=items.stream().map(ScoredAssessment::score).reduce(BigDecimal.ZERO,BigDecimal::add).divide(BigDecimal.valueOf(items.size()),4,RoundingMode.HALF_UP);
   List<String> warnings=new ArrayList<>();boolean disqualified=items.stream().anyMatch(i->!i.source().compliancePassed());
   items.forEach(i->{if(i.score().subtract(average).abs().compareTo(req.outlierThreshold())>0)warnings.add("评委 "+i.source().judgeNo()+" 评分偏差超过阈值");});
   String decision=disqualified?"DISQUALIFIED":warnings.isEmpty()?"RANKABLE":"MANUAL_REVIEW";
   vendors.add(new VendorResult(vendor,items.getFirst().source().vendorName(),money(average),items.size(),warnings,decision,0));
  });
  List<VendorResult> ranked=vendors.stream().sorted(Comparator.comparing(VendorResult::weightedScore).reversed()).toList();List<VendorResult> output=new ArrayList<>();int rank=1;
  for(var v:ranked)output.add(new VendorResult(v.vendorNo(),v.vendorName(),v.weightedScore(),v.judgeCount(),v.warnings(),v.decision(),"RANKABLE".equals(v.decision())?rank++:0));
  return new EvaluationResult(output,output.stream().allMatch(v->v.warnings().isEmpty())?"READY_FOR_AWARD":"REVIEW_REQUIRED");
 }
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 private BigDecimal money(BigDecimal v){return v.setScale(2,RoundingMode.HALF_UP);}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 private record ScoredAssessment(Assessment source,BigDecimal score){}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public record EvaluationRequest(@NotNull @DecimalMin("0") @DecimalMax("1") BigDecimal technicalWeight,@NotNull @DecimalMin("0") @DecimalMax("1") BigDecimal commercialWeight,@NotNull @DecimalMin("0") @DecimalMax("1") BigDecimal serviceWeight,@NotNull @DecimalMin("0") BigDecimal outlierThreshold,@NotEmpty List<@Valid Assessment> assessments){
  /**
   * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
   */
  public EvaluationRequest{if(technicalWeight!=null&&commercialWeight!=null&&serviceWeight!=null&&technicalWeight.add(commercialWeight).add(serviceWeight).subtract(BigDecimal.ONE).abs().compareTo(new BigDecimal(".0001"))>0)throw new IllegalArgumentException("评分权重合计必须为1");}
 }
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public record Assessment(@NotBlank String vendorNo,@NotBlank String vendorName,@NotBlank String judgeNo,@NotNull @DecimalMin("0") @DecimalMax("100") BigDecimal technical,@NotNull @DecimalMin("0") @DecimalMax("100") BigDecimal commercial,@NotNull @DecimalMin("0") @DecimalMax("100") BigDecimal service,boolean compliancePassed){}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public record VendorResult(String vendorNo,String vendorName,BigDecimal weightedScore,int judgeCount,List<String> warnings,String decision,int rank){}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public record EvaluationResult(List<VendorResult> vendors,String decision){}
}
