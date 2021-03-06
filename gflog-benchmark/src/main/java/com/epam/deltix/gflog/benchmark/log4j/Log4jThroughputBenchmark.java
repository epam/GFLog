package com.epam.deltix.gflog.benchmark.log4j;

import com.epam.deltix.gflog.benchmark.util.BenchmarkState;
import net.openhft.affinity.Affinity;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.ThreadParams;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.CommandLineOptions;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

import static com.epam.deltix.gflog.benchmark.util.BenchmarkUtil.*;

@Warmup(iterations = 3, time = 5)
@Measurement(iterations = 3, time = 15)
@Fork(1)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Threads(1)
public class Log4jThroughputBenchmark {

    @Param({
            "noop",
            "file"
    })
    public String config;

    @Setup
    public void prepare() throws Exception {
        Log4jBenchmarkUtil.prepare(config);
    }

    @TearDown
    public void cleanup() throws Exception {
        Log4jBenchmarkUtil.cleanup();
    }

    @Benchmark
    public void baseline(final ThreadState state) {
    }

    @Benchmark
    public void timestamp(final ThreadState state) {
        System.currentTimeMillis();
    }

    @Benchmark
    public void log0Arg(final ThreadState state) {
        Log4jBenchmarkUtil.log0Arg(state);
    }

    @Benchmark
    public void log1Arg(final ThreadState state) {
        Log4jBenchmarkUtil.log1Arg(state);
    }

    @Benchmark
    public void log5Args(final ThreadState state) {
        Log4jBenchmarkUtil.log5Args(state);
    }

    @Benchmark
    public void log10Args(final ThreadState state) {
        Log4jBenchmarkUtil.log10Args(state);
    }

    @Benchmark
    public void logException(final ThreadState state) {
        Log4jBenchmarkUtil.logException(state);
    }

    @Benchmark
    public void logCachedException(final ThreadState state) {
        Log4jBenchmarkUtil.logCachedException(state);
    }

    public static void main(final String[] args) throws Exception {
        final CommandLineOptions defaults = new CommandLineOptions(args);
        final Options options = new OptionsBuilder()
                .parent(defaults)
                .include(Log4jThroughputBenchmark.class.getName())
                // .addProfiler(GCProfiler.class)
                .build();

        new Runner(options).run();
    }

    @State(Scope.Thread)
    public static class ThreadState extends BenchmarkState {

        @Setup
        public void setup(final ThreadParams params) {
            Thread.currentThread().setName(THREAD);

            if (AFFINITY_BASE >= 0 && AFFINITY_STEP >= 0) {
                final int index = params.getThreadIndex();
                final int affinity = AFFINITY_BASE + index * AFFINITY_STEP;

                Affinity.setAffinity(affinity);
            }
        }

    }

}
