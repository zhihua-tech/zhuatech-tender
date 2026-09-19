/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.tender.controller;import cn.zhuatech.tender.common.ApiResponse;import cn.zhuatech.tender.service.EnterpriseTenderService;import jakarta.validation.Valid;import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/enterprise/tender") public class EnterpriseTenderController {private final EnterpriseTenderService service;/**
                                                                                                                                                  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                  */
public EnterpriseTenderController(EnterpriseTenderService service){this.service=service;}/**
                                                                                                                                                                                                                                           * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                           */
@PostMapping("/consolidate-evaluation") ApiResponse<EnterpriseTenderService.EvaluationResult> consolidate(@Valid @RequestBody EnterpriseTenderService.EvaluationRequest request){return ApiResponse.ok(service.consolidate(request));}}
