package io.github.imsejin.common.tool;

import io.github.imsejin.common.util.MathUtils;
import io.github.imsejin.common.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Stopwatch that supports various {@link TimeUnit}.
 *
 * @see TimeUnit
 */
public final class Stopwatch {

    private final List<Task> tasks = new ArrayList<>();
    private long startNanoTime;
    private long totalNanoTime;
    private String currentTaskName;
    private TimeUnit timeUnit = TimeUnit.NANOSECONDS;

    /**
     * Returns {@link Stopwatch} that is set with default {@link TimeUnit}.
     *
     * <p> Default timeUnit is {@link TimeUnit#NANOSECONDS}.
     */
    public Stopwatch() {
    }

    /**
     * Returns {@link Stopwatch} that is set with custom {@link TimeUnit}.
     *
     * @param timeUnit time unit
     */
    public Stopwatch(TimeUnit timeUnit) {
        if (timeUnit == null) throw new IllegalArgumentException("Time unit cannot be null");
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
     * Sets up the {@link TimeUnit}.
     *
     * @param timeUnit time unit
     */
    public void setTimeUnit(TimeUnit timeUnit) {
        if (timeUnit == null) throw new IllegalArgumentException("Time unit cannot be null");
        this.timeUnit = timeUnit;
    }

    /**
     * Starts to run {@link Stopwatch}.
     *
     * <p> Sets up task name of current task with empty string.
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
     */
    public void start(String taskName) {
        if (taskName == null) throw new IllegalArgumentException("Task name cannot be null");
        if (isRunning()) throw new RuntimeException("Stopwatch is already running");

        this.currentTaskName = taskName;
        this.startNanoTime = System.nanoTime();
    }

    /**
     * Starts to run {@link Stopwatch}.
     *
     * <p> Sets up task name of current task.
     *
     * @param format format string as current task name
     * @param args   arguments
     */
    public void start(@Nonnull String format, Object... args) {
        if (format == null) throw new IllegalArgumentException("Task name cannot be null");
        if (isRunning()) throw new RuntimeException("Stopwatch is already running");

        this.currentTaskName = String.format(format, args);
        this.startNanoTime = System.nanoTime();
    }

    /**
     * Stops the {@link Stopwatch} running.
     *
     * <p> Current task will be saved and closed.
     */
    public void stop() {
        if (!isRunning()) throw new RuntimeException("Stopwatch is not running");

        long elapsedNanoTime = System.nanoTime() - this.startNanoTime;
        this.totalNanoTime += elapsedNanoTime;
        this.tasks.add(new Task(elapsedNanoTime, this.currentTaskName));
        this.currentTaskName = null;
    }

    /**
     * Checks if {@link Stopwatch} is running now.
     *
     * @return whether {@link Stopwatch} is running.
     */
    public boolean isRunning() {
        return this.currentTaskName != null;
    }

    /**
     * Returns the sum of the elapsed time of all saved tasks.
     *
     * <p> This total time will be converted with {@link Stopwatch}'s {@link TimeUnit}
     * and shown up to the millionths(sixth after decimal point).
     *
     * @return the sum of task times
     * @see MathUtils#floor(double, int)
     */
    public double getTotalTime() {
        return convertTimeUnit(this.totalNanoTime, TimeUnit.NANOSECONDS, this.timeUnit);
    }

    /**
     * Returns {@link #getTotalTime()} and abbreviation of {@link Stopwatch}'s {@link TimeUnit}.
     *
     * @return {@link Stopwatch}'s summary
     * @see #getTotalTime()
     */
    public String getSummary() {
        int decimalPlace = this.timeUnit == TimeUnit.NANOSECONDS ? 0 : 6;
        return "Stopwatch: RUNNING_TIME = " +
                BigDecimal.valueOf(MathUtils.floor(this.getTotalTime(), decimalPlace)).stripTrailingZeros().toPlainString() +
                ' ' +
                getTimeUnitAbbreviation(this.timeUnit);
    }

    /**
     * Returns statistical data for each task.
     *
     * <p> This shows the percentage of how long each task took
     * and how much time it took up in total time.
     *
     * @return {@link Stopwatch}'s statistics
     * @see #getSummary()
     */
    public String getStatistics() {
        double totalTime = getTotalTime();

        // Sets up task time and percentage to each tasks.
        for (Task task : this.tasks) {
            double taskTime = convertTimeUnit(task.totalNanoTime, TimeUnit.NANOSECONDS, this.timeUnit);
            task.setTaskTime(taskTime);
            task.setTotalTime(taskTime, this.timeUnit);

            int percentage = (int) Math.round(taskTime / totalTime * 100);
            task.setPercentage(percentage);
        }

        int timeUnitIndex = this.tasks.stream()
                .map(task -> task.totalTime)
                .max(Comparator.comparingInt(String::length))
                .orElse("")
                .length();
        String timeUnitColumn = String.format("%-" + timeUnitIndex + "s", getTimeUnitAbbreviation(this.timeUnit));

        for (Task task : this.tasks) {
            task.setTotalTime(task.taskTime, this.timeUnit, timeUnitIndex);
        }

        StringBuilder sb = new StringBuilder(getSummary());
        sb.append('\n');
        sb.append("----------------------------------------\n");
        sb.append(timeUnitColumn).append("  ").append(String.format("%-3c", '%')).append("  ").append("TASK_NAME\n");
        sb.append("----------------------------------------\n");
        for (Task task : this.tasks) {
            sb.append(task).append('\n');
        }

        return sb.toString();
    }

    private static final class Task {
        private final long totalNanoTime;
        private final String name;
        private double taskTime;
        private String totalTime;
        private String percentage;

        public Task(long totalNanoTime, String name) {
            this.totalNanoTime = totalNanoTime;
            this.name = name;
        }

        public void setTaskTime(double taskTime) {
            this.taskTime = taskTime;
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
