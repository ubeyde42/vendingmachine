package com.ubeyde.sample.aspects;

import com.ubeyde.sample.annotations.ExcludeFromMachineCheck;
import com.ubeyde.sample.entity.Machine;
import com.ubeyde.sample.enums.MachineStatus;
import com.ubeyde.sample.exception.MachineNotActiveException;
import com.ubeyde.sample.service.MachineService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class MachineStatusCheckAspect {

    private final MachineService machineService;

    public MachineStatusCheckAspect(MachineService machineService) {
        this.machineService = machineService;
    }

    // All methods except @ExcludeFromMachineCheck annotations
    @Before("execution(* com.ubeyde.sample.controller.MachineController.*(..))")
    public void checkMachineStatus(JoinPoint joinPoint) throws Throwable {
        // Get the method being called
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        // No check needed for excluded methods
        if (method.isAnnotationPresent(ExcludeFromMachineCheck.class)) {
            return;
        }

        // when machine is not active, operations will be prevented
        Machine machine = machineService.getMachineInfo();
        if (machine == null || machine.getStatus() != MachineStatus.ACTIVE) {
            throw new MachineNotActiveException("Makine aktif değil.");
        }
    }
}
