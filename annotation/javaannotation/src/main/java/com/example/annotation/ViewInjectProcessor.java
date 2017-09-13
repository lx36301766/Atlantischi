package com.example.annotation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * Created on 22/03/2017.
 *
 * @author lx
 */

@SupportedAnnotationTypes("com.lx.annotation.inject.CInjectView")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class ViewInjectProcessor extends AbstractProcessor {

    //所有注解处理器的列表
    List<AnnotationHandler> mHandlers = new LinkedList<AnnotationHandler>();
    //类型与字段的关联表,用于在写入Java文件时按类型来写不同的文件和字段
    final Map<String, List<VariableElement>> map = new HashMap<String, List<VariableElement>>();
    // 生成辅助累的Writer类
//    AdapterWriter mWriter;

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
    }

    public static void main(String[] args) {
        System.out.println(123);
    }

}
