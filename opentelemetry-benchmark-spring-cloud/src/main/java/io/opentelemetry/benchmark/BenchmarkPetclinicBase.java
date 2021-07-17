package io.opentelemetry.benchmark;

import org.openjdk.jmh.annotations.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import io.opentelemetry.benchmark.petclinic.PetClinicApplication;
import io.opentelemetry.benchmark.petclinic.owner.Owner;
import io.opentelemetry.benchmark.petclinic.owner.PetController;

public class BenchmarkPetclinicBase {

    public Owner findPetOwnerById(StateVariables state) {
        return state.petcontroller.findOwner(1);
    }

    @State(Scope.Benchmark)
    public static class StateVariables {
        public PetController petcontroller;
        public ConfigurableApplicationContext c;

        @TearDown(Level.Iteration)
        public void shutdownContext() {
            c.close();
        }

        public void initApplication() {
            c = SpringApplication.run(PetClinicApplication.class);
            petcontroller = c.getBean(PetController.class);
        }
    }

    public static class StateVariablesNoInstrumentation extends StateVariables {
        @Setup(Level.Iteration)
        public void doSetup() {
            System.setProperty("tracerresolver.disabled", Boolean.TRUE.toString());
            initApplication();
        }
    }

    public static class StateVariablesJaeger extends StateVariables {
        @Setup(Level.Iteration)
        public void doSetup() {

            System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "JAEGERTRACER");
            initApplication();
        }
    }

    public static class StateVariablesNoopTracer extends StateVariables {
        @Setup(Level.Iteration)
        public void doSetup() {

            System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "NOOPTRACER");
            initApplication();
        }
    }

    public static class StateVariablesOtlp extends StateVariables {
        @Setup(Level.Iteration)
        public void doSetup() {

            System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "OTLPTRACER");
            initApplication();
        }
    }

    

}




