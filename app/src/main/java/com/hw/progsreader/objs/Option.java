package com.hw.progsreader.objs;

/**
 * Created by Administrator on 2016/7/11.
 */
public class Option implements Comparable<Option>{
    private String name;
    private String data;
    private String path;
    private boolean folder;
    private boolean patent;

    public Option(String n, String d, String p, boolean isF, boolean isP){
        name = n;
        data = d;
        path = p;
        folder = isF;
        patent = isP;
    }
    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

    public String getPath() {
        return path;
    }

    public boolean isFolder() {
        return folder;
    }

    public boolean isPatent() {
        return patent;
    }

    @Override
    public int compareTo(Option option) {
        if (this.name != null){
            return this.name.toLowerCase().compareTo(option.getName().toLowerCase());
        } else{
            throw new IllegalArgumentException();
        }
    }
}
