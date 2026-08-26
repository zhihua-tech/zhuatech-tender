/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.tender.domain;
import org.springframework.stereotype.Component;
import java.util.*;
@Component
public class DomainCatalog {
    private final Map<String,WorkflowAction> actions=new LinkedHashMap<>();
    public DomainCatalog(){
        actions.put("PUBLISH", new WorkflowAction("PUBLISH", "发布招标", List.of("草稿"), "已发布"));
actions.put("CLOSE_BID", new WorkflowAction("CLOSE_BID", "截止投标", List.of("已发布"), "已截标"));
actions.put("EVALUATE", new WorkflowAction("EVALUATE", "进入评审", List.of("已截标"), "评审中"));
actions.put("AWARD", new WorkflowAction("AWARD", "确认定标", List.of("评审中"), "已定标"));
    }
    public String systemName(){return "知华科技企业招投标管理系统";}
    public String scene(){return "招标项目、招标文件、供应商资格、投标文件、专家评审和中标管理";}
    public String initialStatus(){return "草稿";}
    public String partyLabel(){return "采购组织/供应商";} public String amountLabel(){return "项目预算";}
    public String quantityLabel(){return "投标方";} public String dueLabel(){return "截标日期";}
    public List<ModuleDefinition> modules(){return List.of(
        new ModuleDefinition("PROJECT","招标项目","完成采购立项、方式选择和计划审批"),
    new ModuleDefinition("SUPPLIER","供应商报名","收集资格文件并完成资格预审"),
    new ModuleDefinition("BID","投标管理","登记投标文件、保证金和密封状态"),
    new ModuleDefinition("EVALUATION","评审与定标","组织专家评分、澄清和中标审批")
    );}
    public Map<String,WorkflowAction> actions(){return Collections.unmodifiableMap(actions);}
    public record ModuleDefinition(String code,String name,String description){}
    public record WorkflowAction(String code,String label,List<String> from,String to){}
}
