package com.ubeyde.sample.aspects;

import com.ubeyde.sample.annotations.ExcludeFromMachineCheck;
import com.ubeyde.sample.entity.Machine;
import com.ubeyde.sample.enums.MachineStatus;
import com.ubeyde.sample.exception.MachineNotActiveException;
import com.ubeyde.sample.service.MachineService;
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

    // Tüm metotlara uygulanacak, ancak @ExcludeFromMachineCheck anotasyonu olan metotlar hariç tutulacak
    @Before("execution(* com.example.controller.MachineController.*(..))")
    public void checkMachineStatus() throws Throwable {
        MethodSignature methodSignature = (MethodSignature) org.aspectj.lang.reflect.MethodSignature.class.cast(org.aspectj.lang.JoinPoint.class.cast(this).getSignature());
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
