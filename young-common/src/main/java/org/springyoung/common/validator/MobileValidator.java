package org.springyoung.common.validator;

import org.apache.commons.lang3.StringUtils;
import org.springyoung.common.annotation.IsMobile;
import org.springyoung.common.constant.RegexpConstant;
import org.springyoung.common.utils.YoungUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MobileValidator implements ConstraintValidator<IsMobile, String> {

    @Override
    public void initialize(IsMobile isMobile) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (StringUtils.isBlank(s)) {
                return true;
            } else {
                String regex = RegexpConstant.MOBILE_REG;
                return YoungUtil.match(regex, s);
            }
        } catch (Exception e) {
            return false;
        }
    }
}