//package com.gmail.s0rInb.authentication;
//
//import com.pflb.chronos.entity.SecurityPolicy;
//import com.pflb.chronos.entity.SecurityUserRole;
//import com.pflb.chronos.web.annotation.AccessRights;
//import com.pflb.chronos.web.aop.ExecutionLoger;
//import com.pflb.chronos.web.controller.BaseController;
//import com.pflb.chronos.web.exception.AccessDeniedException;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
///**
// * Created by Danila on 02.02.2016.
// */
//@Component
//@Aspect
//public class SecurityManager {
////    final Logger logger = LogManager.getLogger(ExecutionLoger.class);
//
//    @Pointcut("execution(* com.gmail.s0rInb.controller.*.get*(..))")
//    public void readExecution() {
//    }
//
//    @Pointcut("(execution(* com.gmail.s0rInb.controller.*.update*(..)) || execution(* com.gmail.s0rInb.controller.*.delete*(..)) || (* com.gmail.s0rInb.controller.*.upload*(..)))")
//    public void updateExecution() {
//
//    }
//
//    @Before("readExecution()")
//    public void readBeforeAdvice(JoinPoint joinPoint) {
//
//        Class type = ((BaseController) joinPoint.getTarget()).getType();
//        if (Objects.isNull(type)) {
////            logger.debug("class Null in controller: " + joinPoint.getTarget().getClass());
//            return;
//        }
//
//
//        List<SecurityPolicy> securityPolicies = new ArrayList<>();
//        for (SecurityUserRole securityUserRole : ((BaseController) joinPoint.getTarget()).getUser().getSecurityUserRoles()) {
//            securityPolicies.addAll(securityUserRole.getSecurityPolicies());
//        }
//        securityPolicies = securityPolicies.stream().filter(
//                securityPolicy -> securityPolicy.getSecurityClass().getName().equals(((BaseController) joinPoint.getTarget()).getType().getName())
//        ).collect(Collectors.toList());
//
//        if (securityPolicies.isEmpty())
//            throw new AccessDeniedException("Domain " + type.getName());
//
//        if (canRead(securityPolicies))
//            return;
//
//        if (!canAccess(securityPolicies))
//            throw new AccessDeniedException();
//
//        AccessRights accessRight = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(AccessRights.class);
//        if ((accessRight.rights() & AccessRights.ACCESS) != AccessRights.ACCESS) {
//            throw new AccessDeniedException();
//        }
//    }
//
//    @Before("updateExecution()")
//    public void updateBeforeAdvice(JoinPoint joinPoint) {
//        {
//            Class type = ((BaseController) joinPoint.getTarget()).getType();
//            if (Objects.isNull(type)) {
//                throw new AccessDeniedException();
//            }
//        }
//
//        List<SecurityPolicy> securityPolicies = new ArrayList<>();
//        for (SecurityUserRole securityUserRole : ((BaseController) joinPoint.getTarget()).getUser().getSecurityUserRoles()) {
//            securityPolicies.addAll(securityUserRole.getSecurityPolicies());
//        }
//        securityPolicies = securityPolicies.stream().filter(securityPolicy -> securityPolicy.getSecurityClass().getName().equals(((BaseController) joinPoint.getTarget()).getType().getName())).collect(Collectors.toList());
//        if (securityPolicies.isEmpty() || !canUpdate(securityPolicies)) {
//            throw new AccessDeniedException();
//        }
//    }
//
//
//    /**
//     * Checks for ACCESS permission.
//     *
//     * @param securityPolicies to check against.
//     * @return if policy contains the ACCESS right.
//     */
//    private boolean canAccess(List<SecurityPolicy> securityPolicies) {
//        int resultingPolicy = 0;
//        for (SecurityPolicy securityPolicy : securityPolicies) {
//            resultingPolicy = resultingPolicy | securityPolicy.getMask();
//        }
//        return (resultingPolicy & AccessRights.ACCESS) == AccessRights.ACCESS;
//    }
//
//}
