package com.example.annotation;

import java.util.List;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.VariableElement;

/**
 * Created on 31/03/2017.
 *
 * @author lx
 */

public interface AnnotationHandler {

    // 关联ProcessingEnvironment
    void attachProcessingEnv(ProcessingEnvironment processingEnv);
    // 处理注解，将结果存储到Map中
    Map<String, List<VariableElement>> handleAnnotation(RoundEnvironment env);

}
