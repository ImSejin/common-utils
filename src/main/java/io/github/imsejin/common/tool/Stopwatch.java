/*
 * Copyright 2020 Sejin Im
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.imsejin.common.tool;

import io.github.imsejin.common.assertion.Asserts;
import io.github.imsejin.common.util.MathUtils;
import io.github.imsejin.common.util.StringUtils;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Stopwatch that supports various time units
 *
 * @see TimeUnit
 */
public final class Stopwatch {

    private static final int DECIMAL_PLACE = 6;

    /**
     * Recorded tasks.
     */
    private final Tasks tasks = new Tasks();

    /**
     * Start nano time for a task.
     */
    private long startNanoTime;

    /**
     * Total nano time for all tasks.
     */
    private long totalNanoTime;

    /**
     * Name of currently running task.
     */
    private String currentTaskName;

    /**
     * Time unit for task time to be printed or returned.
     */
    private TimeUnit timeUnit;

    /**
     * Returns a stopwatch that is set with default {@link TimeUnit}.
     *
     * <p> Default time unit is {@link TimeUnit#NANOSECONDS}.
     */
    public Stopwatch() {
        this.timeUnit = TimeUnit.NANOSECONDS;
    }

    /**
     * Returns a stopwatch that is set with custom {@link TimeUnit}.
     *
     * @param timeUnit time unit
     */
    public Stopwatch(@Nonnull TimeUnit timeUnit) {
        Asserts.that(timeUnit)
                .as("Stopwatch.timeUnit cannot be null")
                .isNotNull();

        this.timeUnit = timeUnit;
    }

    private static double convertTimeUnit(double amount, TimeUnit from, TimeUnit to) {
        return from.ordinal() < to.ordinal()
                ? amount / from.convert(1, to)
                : amount * to.convert(1, from);
    }

    private static String getTimeUnitAbbreviation(TimeUnit timeUnit) {
        switch (timeUnit) {
            case NANOSECONDS:
                return "ns";
            case MICROSECONDS:
                return "μs";
            case MILLISECONDS:
                return "ms";
            case SECONDS:
                return "sec";
            case MINUTES:
                return "min";
            case HOURS:
                return "hrs";
            case DAYS:
                return "days";
            default:
                throw new IllegalArgumentException("No TimeUnit equivalent for " + timeUnit);
        }
    }

    /**
     * Returns time unit of stopwatch.
     *
     * @return time unit
     */
    public TimeUnit getTimeUnit() {
        return this.timeUnit;
    }

    /**
     * Sets time unit.
     *
     * @param timeUnit time unit
     */
    public void setTimeUnit(@Nonnull TimeUnit timeUnit) {
        Asserts.that(timeUnit)
                .as("Stopwatch.timeUnit cannot be null")
                .isNotNull();

        this.timeUnit = timeUnit;
    }

    /**
     * Starts to run stopwatch.
     *
     * <p> Sets task name of current task with empty string.
     */
    public void start() {
        start("");
    }

    /**
     * Starts to run {@link Stopwatch}.
     *
     * <p> Sets up task name of current task.
     *
     * @param taskName current task name
     * @throws UnsupportedOperationException if stopwatch is running
     */
    public void start(@Nonnull String taskName) {
        Asserts.that(taskName)
                .as("Task name cannot be null")
                .isNotNull();
        Asserts.that(isRunning())
                .as("Stopwatch cannot start, when it is already running")
                .exception(UnsupportedOperationException::new)
                .isFalse();

        this.currentTaskName = taskName;
        this.startNanoTime = System.nanoTime();
    }

    /**
     * Runs stopwatch.
     *
     * <p> Sets up task name of current task.
     *
     * @param format format string as current task name
     * @param args   arguments
     * @throws IllegalArgumentException      if format is null
     * @throws UnsupportedOperationException if stopwatch is running
     */
    public void start(@Nonnull String format, Object... args) {
        Asserts.that(format)
                .as("Task name cannot be null")
                .isNotNull();
        Asserts.that(isRunning())
                .as("Stopwatch cannot start, when it is already running")
                .exception(UnsupportedOperationException::new)
                .isFalse();

        this.currentTaskName = String.format(format, args);
        this.startNanoTime = System.nanoTime();
    }

    /**
     * Stops stopwatch.
     *
     * <p> Current task will be saved and closed.
     *
     * @throws UnsupportedOperationException if stopwatch is not running
     */
    public void stop() {
        Asserts.that(isRunning())
                .as("Stopwatch cannot stop, when it is not running")
                .exception(UnsupportedOperationException::new)
                .isTrue();

        long elapsedNanoTime = System.nanoTime() - this.startNanoTime;
        this.totalNanoTime += elapsedNanoTime;
        this.tasks.add(elapsedNanoTime, this.currentTaskName);
        this.currentTaskName = null;
    }

    /**
     * Checks if stopwatch is running.
     *
     * @return whether stopwatch is running.
     */
    public boolean isRunning() {
        return this.currentTaskName != null;
    }

    /**
     * Checks if stopwatch has never been stopped.
     *
     * @return whether stopwatch has never been stopped.
     */
    public boolean hasNeverBeenStopped() {
        return this.tasks.isEmpty();
    }

    /**
     * Clears all tasks.
     */
    public void clear() {
        Asserts.that(isRunning())
                .as("Stopwatch is running. To clear, stop it first")
                .exception(UnsupportedOperationException::new)
                .isFalse();

        forceClear();
    }

    /**
     * Clears all tasks even if stopwatch is running.
     */
    public void forceClear() {
        this.tasks.clear();
        this.startNanoTime = 0;
        this.totalNanoTime = 0;
        this.currentTaskName = null;
    }

    /**
     * Returns the sum of the elapsed time of all saved tasks.
     *
     * <p> This total time will be converted with {@link Stopwatch#timeUnit}
     * and shown up to the millionths(sixth after decimal point).
     *
     * @return the sum of task times
     * @throws UnsupportedOperationException if stopwatch has never been stopped
     * @see MathUtils#floor(double, int)
     */
    public double getTotalTime() {
        Asserts.that(hasNeverBeenStopped())
                .as("Stopwatch has no total time, because it has never been stopped")
                .exception(UnsupportedOperationException::new)
                .isFalse();

        double converted = convertTimeUnit(this.totalNanoTime, TimeUnit.NANOSECONDS, this.timeUnit);
        return MathUtils.floor(converted, DECIMAL_PLACE);
    }

    /**
     * Returns total time and abbreviation of {@link Stopwatch#timeUnit}.
     *
     * @return summary of stopwatch
     * @see #getTotalTime()
     */
    public String getSummary() {
        String runningTime = BigDecimal.valueOf(getTotalTime()).stripTrailingZeros().toPlainString();
        return "Stopwatch: RUNNING_TIME = " + runningTime + ' ' + getTimeUnitAbbreviation(this.timeUnit);
    }

    /**
     * Returns statistical data for each task.
     *
     * <p> This shows the percentage of how long each task took
     * and how much time it took up in total time.
     *
     * @return statistics of stopwatch
     * @see #getSummary()
     */
    public String getStatistics() {
        double totalTime = getTotalTime();

        // Sets up task time and percentage to each task.
        for (Tasks.Task task : this.tasks) {
            double taskTime = convertTimeUnit(task.getTotalNanoTime(), TimeUnit.NANOSECONDS, this.timeUnit);
            task.setTaskTime(taskTime);
            task.setTotalTime(taskTime, this.timeUnit);

            int percentage = (int) Math.round(taskTime / totalTime * 100);
            task.setPercentage(percentage);
        }

        final int timeUnitIndex = this.tasks.stream()
                .map(task -> task.getTotalTime().length())
                .reduce(0, Math::max);
        String timeUnitColumn = String.format("%-" + timeUnitIndex + "s", getTimeUnitAbbreviation(this.timeUnit));

        for (Tasks.Task task : this.tasks) {
            task.setTotalTime(task.getTaskTime(), this.timeUnit, timeUnitIndex);
        }

        StringBuilder sb = new StringBuilder(getSummary());
        sb.append("\n----------------------------------------\n");
        sb.append(timeUnitColumn).append("  ").append(String.format("%-3c", '%')).append("  ").append("TASK_NAME");
        sb.append("\n----------------------------------------\n");
        for (Tasks.Task task : this.tasks) {
            sb.append(task).append('\n');
        }

        return sb.toString();
    }

}

/**
 * Stopwatch's tasks that support addition and clearance.
 * <p>
 * This can only add or clear, but not set, sort, remove, retain, replace.
 */
class Tasks implements Iterable<Tasks.Task> {

    private final List<Task> list = new ArrayList<>();

    public boolean add(long totalNanoTime, String name) {
        return this.list.add(new Task(totalNanoTime, name));
    }

    public void clear() {
        this.list.clear();
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    public Stream<Task> stream() {
        return this.list.stream();
    }

    @Override
    public Iterator<Task> iterator() {
        return this.list.iterator();
    }

    static final class Task {
        private final long totalNanoTime;
        private final String name;
        private double taskTime;
        private String totalTime;
        private String percentage;

        private Task(long totalNanoTime, String name) {
            this.totalNanoTime = totalNanoTime;
            this.name = name;
        }

        public long getTotalNanoTime() {
            return this.totalNanoTime;
        }

        public double getTaskTime() {
            return this.taskTime;
        }

        public void setTaskTime(double taskTime) {
            this.taskTime = taskTime;
        }

        public String getTotalTime() {
            return this.totalTime;
        }

        public void setTotalTime(double totalTime, TimeUnit timeUnit) {
            String format = timeUnit == TimeUnit.NANOSECONDS ? "%.0f" : "%.6f";
            this.totalTime = String.format(format, totalTime);
        }

        public void setTotalTime(double totalTime, TimeUnit timeUnit, int len) {
            String format = timeUnit == TimeUnit.NANOSECONDS ? "%.0f" : "%.6f";
            this.totalTime = StringUtils.padEnd(len, String.format(format, totalTime));
        }

        public void setPercentage(int percentage) {
            this.percentage = String.format("%03d", percentage);
        }

        @Override
        public String toString() {
            return this.totalTime + "  " + this.percentage + "  " + this.name;
        }
    }
}
