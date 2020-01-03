package ask.model;

public class Progress {
    private static boolean instanceCreated = false;
    private static final Progress thisInstance = createInstance();

    private double totalLines = 1;
    private double processed = 1;
    private double percentage = 0;
    private long timeInMillis = 0;

    private static Progress createInstance() {
        Progress p = null;
        if(! Progress.instanceCreated) {
            synchronized (Progress.class) {
                if(! Progress.instanceCreated) {
                    p = new Progress();
                    Progress.instanceCreated = true;
                }
            }
        }
        return p;
    }
    private Progress() {}

    public static void incrementCount() {
        synchronized (Progress.thisInstance) {
            Progress.thisInstance.totalLines++;
            Progress.thisInstance.percentage = ((Progress.thisInstance.processed / Progress.thisInstance.totalLines) * 100);
        }
    }

    public static void incrementProcessed() {
        synchronized (Progress.thisInstance) {
            Progress.thisInstance.processed++;
            Progress.thisInstance.percentage = ((Progress.thisInstance.processed / Progress.thisInstance.totalLines) * 100);
        }
    }

    public static String status() {
        return "{ \"status\": " + Progress.thisInstance.percentage +
                ", \"timeInMillis\": " + Progress.thisInstance.timeInMillis +
                ", \"UrlCount\": " + Progress.thisInstance.totalLines + "}";
    }

    public static void reset() {
        synchronized (Progress.thisInstance) {
            Progress.thisInstance.totalLines = 1;
            Progress.thisInstance.processed = 1;
            Progress.thisInstance.percentage = 0;
        }
    }

    public static void setTime(long millis) {
        synchronized (Progress.thisInstance) {
            Progress.thisInstance.timeInMillis = millis;
        }
    }

    public String toString() {
        return this.totalLines + "|" + this.processed + "|" + this.percentage;
    }
}
