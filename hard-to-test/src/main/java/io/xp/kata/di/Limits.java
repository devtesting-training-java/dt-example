package io.xp.kata.di;

public final class Limits {
    private static Limits instance;

    private int from = 1;
    private int to = 100;
    private boolean hasConfigError;

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public boolean hasConfigError() {
        return hasConfigError;
    }

    private Limits() {
    }

    synchronized public static Limits getInstance() {
        if (instance == null) {
            instance = new Limits();
        }
        return instance;
    }

    public void init(String[] args, Config config) {
        readConfig(config);
        readArgs(args);
    }

    private void readArgs(String[] values) {
        try {
            if (values.length == 1) {
                to = Integer.parseInt(values[0]);
            }
            if (values.length >= 2) {
                from = Integer.parseInt(values[0]);
                to = Integer.parseInt(values[1]);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }

    private void readConfig(Config config) {
        try {
            int from = Integer.parseInt(config.getFrom());
            int to = Integer.parseInt(config.getTo());
            this.from = from;
            this.to = to;
            hasConfigError = false;
        } catch (NumberFormatException e) {
            hasConfigError = true;
        }
    }
}
