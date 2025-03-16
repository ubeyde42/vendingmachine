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
        // Current method being executed
        MethodSignature methodSignature = (MethodSignature) org.aspectj.lang.reflect.MethodSignature.class.cast(org.aspectj.lang.JoinPoint.class.cast(this).getSignature());
        Method method = methodSignature.getMethod();

        // Eğer metot @ExcludeFromMachineCheck anotasyonuna sahipse, işlem yapılmaz
        if (method.isAnnotationPresent(ExcludeFromMachineCheck.class)) {
            return; // Hiçbir şey yapma, makine kontrolü yapılmaz
        }

        // Makine durumu kontrolü
        Machine machine = machineService.getMachineInfo();
        if (machine == null || machine.getStatus() != MachineStatus.ACTIVE) {
            throw new MachineNotActiveException("Machine is not active.");
        }
    }
}
