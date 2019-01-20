package com.lcydream.project.vlid;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Iterator;
import java.util.Set;

/**
 * VlidParam
 *
 * @author Luo Chun Yun
 * @date 2018/9/16 11:18
 */
public class VlidParam {

    private static final String split = "、";
    /**
     * 校验入参
     * @param obj 被校验的参数
     * @return 返回信息
     */
    public static VlidResult check(Object obj) {
        if (null == obj) {
            return new VlidResult(false,"验证入参不能为空");
        }
        Set<ConstraintViolation<Object>> validResult = Validation.buildDefaultValidatorFactory().getValidator().validate(obj);
        if (null != validResult && validResult.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (Iterator<ConstraintViolation<Object>> iterator = validResult.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<Object> constraintViolation = (ConstraintViolation<Object>) iterator.next();
                if (StringUtils.isNotBlank(constraintViolation.getMessage())) {
                    sb.append(constraintViolation.getMessage()).append(split);
                } else {
                    sb.append(constraintViolation.getPropertyPath().toString()).append("不合法、");
                }
            }
            if (sb.lastIndexOf(split) == sb.length() - 1) {
                sb.delete(sb.length() - 1, sb.length());
            }
            return new VlidResult(false,sb.toString());
        }
        return new VlidResult(true);
    }
}
