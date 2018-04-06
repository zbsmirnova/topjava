package ru.javawebinar.topjava.service;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import java.util.concurrent.TimeUnit;
public class StopwatchImpl extends Stopwatch {


    @Override
    protected void finished(long nanos, Description description) {
        String result = String.format("\n%-25s %7d", description.getMethodName(),  TimeUnit.NANOSECONDS.toMillis(nanos));
        AbstractServiceTest.results.append(result);
        AbstractServiceTest.log.info(result + " ms\n");
    }


}
