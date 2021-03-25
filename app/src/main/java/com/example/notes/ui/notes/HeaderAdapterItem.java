package com.example.notes.ui.notes;

public class HeaderAdapterItem implements AdapterItem {

    private String header;

    public HeaderAdapterItem(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }
}
