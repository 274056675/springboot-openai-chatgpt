package org.springblade.cgform.model.generate;

import java.util.List;
import java.util.Map;

public interface IGenerate
{
    Map<String, Object> dtaMapMethod() throws Exception;
    
    List<String> generateCodeFile(String paramString) throws Exception;
    
    List<String> generateCodeFile(String paramString1, String paramString2, String paramString3) throws Exception;
}
