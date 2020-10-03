package io.github.imsejin.tool;

import io.github.imsejin.util.NumberUtils;
import io.github.imsejin.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class Stopwatch {

    private final List<Task> tasks = new ArrayList<>();
    private long startNanoTime;
    private long totalNanoTime;
    private String currentTaskName;
    private TimeUnit timeUnit = TimeUnit.NANOSECONDS;

    public Stopwatch() {
    }

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

    public void setTimeUnit(TimeUnit timeUnit) {
        if (timeUnit == null) throw new IllegalArgumentException("Time unit cannot be null");
        this.timeUnit = timeUnit;
    }

    public void start() {
        start("");
    }

    public void start(String taskName) {
        if (taskName == null) throw new IllegalArgumentException("Task name cannot be null");
        if (isRunning()) throw new RuntimeException("Stopwatch is already running");

        this.currentTaskName = taskName;
        this.startNanoTime = System.nanoTime();
    }

    public void stop() {
        if (!isRunning()) throw new RuntimeException("Stopwatch is not running");

        long elapsedNanoTime = System.nanoTime() - this.startNanoTime;
        this.totalNanoTime += elapsedNanoTime;
        this.tasks.add(new Task(elapsedNanoTime, this.currentTaskName));
        this.currentTaskName = null;
    }

    public boolean isRunning() {
        return this.currentTaskName != null;
    }

    public double getTotalTime() {
        double totalTime = convertTimeUnit(this.totalNanoTime, TimeUnit.NANOSECONDS, this.timeUnit);
        return NumberUtils.floor(totalTime, 6);
    }

    public String getSummary() {
        String format = this.timeUnit == TimeUnit.NANOSECONDS ? "%.0f" : "%.6f";
        return "Stopwatch: RUNNING_TIME = " + String.format(format, this.getTotalTime()) + " " + getTimeUnitAbbreviation(this.timeUnit);
    }

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
