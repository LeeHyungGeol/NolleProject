package com.example.adefault.Decoration;

public class SearchHistory {
    private int icon;
    private String history;

    public SearchHistory(int icon,String history){
        this.icon=icon;
        this.history=history;
    }
    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        icon = icon;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }


}
