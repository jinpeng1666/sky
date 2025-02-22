package com.sky.aspect;

import com.sky.annotation.ClearCache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.Set;

@Aspect
@Component
@Slf4j
public class ClearCacheAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    @After("@annotation(clearCache)")
    public void clearCache(JoinPoint joinPoint, ClearCache clearCache) {
        // 获取注解参数
        String categoryIdSpEL = clearCache.categoryId();

        if (categoryIdSpEL == null || categoryIdSpEL.isEmpty()) {
            // 如果未传入分类ID，清理所有缓存
            Set keys = redisTemplate.keys("dish_*");
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
            }
            log.info("清理所有菜品缓存");
        } else {
            // 如果传入了分类ID，清理特定分类的缓存
            ExpressionParser parser = new SpelExpressionParser(); // ExpressionParser是SpEL的解析器，用于将字符串形式的表达式解析为可执行的表达式对象
            StandardEvaluationContext context = new StandardEvaluationContext(); // StandardEvaluationContext是SpEL的上下文对象，用于存储表达式计算时的变量和上下文信息

            // 设置方法参数到上下文
            Object[] args = joinPoint.getArgs();
            String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
            for (int i = 0; i < parameterNames.length; i++) {
                context.setVariable(parameterNames[i], args[i]);
            }

            // 解析 SpEL 表达式
            try {
                Long categoryId = parser.parseExpression(categoryIdSpEL).getValue(context, Long.class); // 将字符串表达式解析为SpEL表达式并从上下文中获取表达式的值，并将其转换为 Long 类型
                if (categoryId != null) {
                    String key = "dish_" + categoryId;
                    redisTemplate.delete(key);
                    log.info("清理分类ID为 " + categoryId + " 的菜品缓存");
                }
            } catch (Exception e) {
                System.err.println("SpEL 解析失败: " + e.getMessage());
            }
        }
    }
}
