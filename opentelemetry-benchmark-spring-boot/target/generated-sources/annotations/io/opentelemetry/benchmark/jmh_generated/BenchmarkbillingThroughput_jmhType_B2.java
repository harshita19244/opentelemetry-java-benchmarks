package io.opentelemetry.benchmark.jmh_generated;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
public class BenchmarkbillingThroughput_jmhType_B2 extends BenchmarkbillingThroughput_jmhType_B1 {
    public volatile int setupTrialMutex;
    public volatile int tearTrialMutex;
    public final static AtomicIntegerFieldUpdater<BenchmarkbillingThroughput_jmhType_B2> setupTrialMutexUpdater = AtomicIntegerFieldUpdater.newUpdater(BenchmarkbillingThroughput_jmhType_B2.class, "setupTrialMutex");
    public final static AtomicIntegerFieldUpdater<BenchmarkbillingThroughput_jmhType_B2> tearTrialMutexUpdater = AtomicIntegerFieldUpdater.newUpdater(BenchmarkbillingThroughput_jmhType_B2.class, "tearTrialMutex");

    public volatile int setupIterationMutex;
    public volatile int tearIterationMutex;
    public final static AtomicIntegerFieldUpdater<BenchmarkbillingThroughput_jmhType_B2> setupIterationMutexUpdater = AtomicIntegerFieldUpdater.newUpdater(BenchmarkbillingThroughput_jmhType_B2.class, "setupIterationMutex");
    public final static AtomicIntegerFieldUpdater<BenchmarkbillingThroughput_jmhType_B2> tearIterationMutexUpdater = AtomicIntegerFieldUpdater.newUpdater(BenchmarkbillingThroughput_jmhType_B2.class, "tearIterationMutex");

    public volatile int setupInvocationMutex;
    public volatile int tearInvocationMutex;
    public final static AtomicIntegerFieldUpdater<BenchmarkbillingThroughput_jmhType_B2> setupInvocationMutexUpdater = AtomicIntegerFieldUpdater.newUpdater(BenchmarkbillingThroughput_jmhType_B2.class, "setupInvocationMutex");
    public final static AtomicIntegerFieldUpdater<BenchmarkbillingThroughput_jmhType_B2> tearInvocationMutexUpdater = AtomicIntegerFieldUpdater.newUpdater(BenchmarkbillingThroughput_jmhType_B2.class, "tearInvocationMutex");

}
