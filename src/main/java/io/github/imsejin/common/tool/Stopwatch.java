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
import io.github.imsejin.common.util.ArrayUtils;
import io.github.imsejin.common.util.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.VisibleForTesting;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Stopwatch that supports various time units
 *
 * @see TimeUnit
 */
public final class Stopwatch {

    @VisibleForTesting
    static final int ROUNDING_SCALE = 10;

    /**
     * Recorded tasks.
     */
    private final List<Task> tasks = new ArrayList<>();

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
        this(TimeUnit.NANOSECONDS);
    }

    /**
     * Returns a stopwatch that is set with custom {@link TimeUnit}.
     *
     * @param timeUnit time unit
     */
    public Stopwatch(TimeUnit timeUnit) {
        Asserts.that(timeUnit)
                .describedAs("Stopwatch.timeUnit cannot be null")
                .isNotNull();

        this.timeUnit = timeUnit;
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
    public void setTimeUnit(TimeUnit timeUnit) {
        Asserts.that(timeUnit)
                .describedAs("Stopwatch.timeUnit cannot be null")
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
     * Starts to run stopwatch.
     *
     * <p> Sets up task name of current task.
     *
     * @param format format string as current task name
     * @param args   arguments
     * @throws IllegalArgumentException      if format is null
     * @throws UnsupportedOperationException if stopwatch is running
     */
    public void start(String format, Object... args) {
        Asserts.that(format)
                .describedAs("Stopwatch.taskName cannot be null")
                .isNotNull();
        Asserts.that(isRunning())
                .describedAs("Stopwatch cannot start while running")
                .thrownBy(UnsupportedOperationException::new)
                .isFalse();

        this.currentTaskName = ArrayUtils.isNullOrEmpty(args) ? format : String.format(format, args);
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
                .describedAs("Stopwatch cannot stop while not running")
                .thrownBy(UnsupportedOperationException::new)
                .isTrue();

        long elapsedNanoTime = System.nanoTime() - this.startNanoTime;
        this.totalNanoTime += elapsedNanoTime;

        Task task = new Task(this.currentTaskName, this.tasks.size(), elapsedNanoTime);
        this.tasks.add(task);

        this.currentTaskName = null;
    }

    /**
     * Checks if stopwatch is running.
     *
     * @return whether stopwatch is running
     */
    public boolean isRunning() {
        return this.currentTaskName != null;
    }

    /**
     * Checks if stopwatch has never been stopped.
     *
     * @return whether stopwatch has never been stopped
     */
    public boolean hasNeverBeenStopped() {
        return this.tasks.isEmpty();
    }

    /**
     * Clears all tasks.
     */
    public void clear() {
        Asserts.that(isRunning())
                .describedAs("Stopwatch is running; stop it first to clear")
                .thrownBy(UnsupportedOperationException::new)
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
     * Returns all the tasks of stopwatch.
     *
     * @return tasks
     */
    public List<Task> getTasks() {
        return Collections.unmodifiableList(this.tasks);
    }

    /**
     * Returns the sum of the elapsed time of all saved tasks.
     *
     * <p> This total time will be converted with {@link Stopwatch#timeUnit}
     * and shown up to the millionths(sixth after decimal point).
     *
     * @return the sum of task times
     * @throws UnsupportedOperationException if stopwatch has never been stopped
     */
    public BigDecimal getTotalTime() {
        Asserts.that(hasNeverBeenStopped())
                .describedAs("Stopwatch has no total time, because it has never been stopped")
                .thrownBy(UnsupportedOperationException::new)
                .isFalse();

        BigDecimal amount = BigDecimal.valueOf(this.totalNanoTime);
        return convertTimeUnit(amount, TimeUnit.NANOSECONDS, this.timeUnit);
    }

    /**
     * Returns the average of all saved tasks.
     *
     * <p> This average time will be converted with {@link Stopwatch#timeUnit}
     * and shown up to the millionths(sixth after decimal point).
     *
     * @return the average of task times
     * @throws UnsupportedOperationException if stopwatch has never been stopped
     */
    public BigDecimal getAverageTime() {
        BigDecimal totalTime = BigDecimal.valueOf(this.totalNanoTime);
        BigDecimal taskCount = BigDecimal.valueOf(this.tasks.size());

        BigDecimal amount = totalTime.divide(taskCount, ROUNDING_SCALE, RoundingMode.HALF_UP);

        return convertTimeUnit(amount, TimeUnit.NANOSECONDS, this.timeUnit);
    }

    /**
     * Returns total time and abbreviation of {@link Stopwatch#timeUnit}.
     *
     * @return summary of stopwatch
     * @see #getTotalTime()
     * @see #getAverageTime()
     */
    public String getSummary() {
        BigDecimal totalTime = getTotalTime();
        BigDecimal averageTime = getAverageTime();
        String abbreviation = getTimeUnitAbbreviation(this.timeUnit);

        return String.format("TOTAL = %s %s, AVERAGE = %s %s",
                totalTime, abbreviation, averageTime, abbreviation);
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
        String unitAbbr = getTimeUnitAbbreviation(this.timeUnit);
        final int timeUnitIndex = Math.max(unitAbbr.length(), this.tasks.stream()
                .mapToInt(task -> task.getDisplayTime(this.timeUnit).length())
                .max().orElse(0));
        String timeUnitColumn = String.format("%-" + timeUnitIndex + "s", unitAbbr);

        StringBuilder sb = new StringBuilder();
        sb.append(getSummary());
        sb.append("\n--------------------------------------------------\n");
        sb.append(timeUnitColumn).append("  ").append(String.format("%-6c", '%')).append("  ").append("TASK_NAME");
        sb.append("\n--------------------------------------------------\n");

        BigDecimal totalTime = getTotalTime();

        for (Task task : this.tasks) {
            String displayTime = task.getDisplayTime(this.timeUnit);
            displayTime = StringUtils.padEnd(timeUnitIndex, displayTime);
            sb.append(displayTime).append("  ");

            BigDecimal percentage = this.tasks.size() == 1
                    ? BigDecimal.valueOf(100L)
                    : task.getPercentage(totalTime, this.timeUnit);
            sb.append(String.format("%-6.2f", percentage)).append("  ");

            sb.append(task.name).append('\n');
        }

        return sb.toString();
    }

    // -------------------------------------------------------------------------------------------------

    @VisibleForTesting
    static BigDecimal convertTimeUnit(BigDecimal amount, TimeUnit from, TimeUnit to) {
        BigDecimal converted;

        if (from == to) {
            converted = amount;
        } else if (from.ordinal() < to.ordinal()) {
            BigDecimal divisor = BigDecimal.valueOf(from.convert(1, to));
            converted = amount.divide(divisor, ROUNDING_SCALE, RoundingMode.HALF_UP);
        } else {
            BigDecimal multiplicand = BigDecimal.valueOf(to.convert(1, from));
            converted = amount.multiply(multiplicand);
        }

        return converted.stripTrailingZeros();
    }

    @VisibleForTesting
    static String getTimeUnitAbbreviation(TimeUnit timeUnit) {
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

    // -------------------------------------------------------------------------------------------------

    /**
     * Task of stopwatch
     */
    public static final class Task {
        private final String name;
        private final int order;
        private final long elapsedNanoTime;

        @VisibleForTesting
        Task(@NotNull String name, @Range(from = 0, to = Integer.MAX_VALUE - 1) int order,
             @Range(from = 0, to = Long.MAX_VALUE) long elapsedNanoTime) {
            this.name = Objects.requireNonNull(name, "Task.name cannot be null");
            this.order = order;
            this.elapsedNanoTime = elapsedNanoTime;
        }

        /**
         * Returns task name.
         *
         * @return task name
         */
        public String getName() {
            return this.name;
        }

        /**
         * Returns the order of task in {@link Stopwatch#tasks}.
         *
         * @return task order
         */
        public int getOrder() {
            return this.order;
        }

        /**
         * Returns task time with nanoseconds.
         *
         * @return task time
         */
        public long getElapsedNanoTime() {
            return this.elapsedNanoTime;
        }

        @VisibleForTesting
        String getDisplayTime(TimeUnit timeUnit) {
            BigDecimal elapsedNanoTime = BigDecimal.valueOf(this.elapsedNanoTime);
            BigDecimal taskTime = convertTimeUnit(elapsedNanoTime, TimeUnit.NANOSECONDS, timeUnit);

            return taskTime.toString();
        }

        @VisibleForTesting
        BigDecimal getPercentage(BigDecimal totalTime, TimeUnit timeUnit) {
            // Prevents ArithmeticException: Division by zero.
            if (totalTime.compareTo(BigDecimal.ZERO) == 0) {
                return BigDecimal.ZERO;
            }

            BigDecimal elapsedNanoTime = BigDecimal.valueOf(this.elapsedNanoTime);
            BigDecimal taskTime = convertTimeUnit(totalTime, timeUnit, TimeUnit.NANOSECONDS);
            BigDecimal ratio = elapsedNanoTime.divide(taskTime, ROUNDING_SCALE, RoundingMode.HALF_UP);

            return ratio.multiply(BigDecimal.valueOf(100L)).stripTrailingZeros();
        }

        @Override
        public String toString() {
            return "Task(name=" + this.name + ", order=" + this.order + ", elapsedNanoTime=" + this.elapsedNanoTime + ")";
        }
    }

}
