package com.woc.common.common.utils;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

/**
 * SpringEl表达式解析
 */
public class SpringELUtils {

    public static String replaceEL(String scource, Map<String,Object> param){

        //表达式解析器
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();
        ((StandardEvaluationContext) context).setVariables(param);
        return parser.parseExpression(scource, new TemplateParserContext()).getValue(context).toString();
    }
}
