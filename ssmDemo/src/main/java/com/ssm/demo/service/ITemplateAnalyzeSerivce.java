package com.ssm.demo.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ITemplateAnalyzeSerivce {
  // excel格式验证
  public Boolean validationEXl(MultipartFile file);

  public List<Object[]> converExlToList(InputStream inputStream);

  public List<Object[]> converXlsxToList(InputStream inputStream);
}
