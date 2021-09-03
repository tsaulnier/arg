package com.example.nfcproject;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;

import java.util.ArrayList;
import java.util.List;


class NdefMessageParser {

    private NdefMessageParser() {
    }

    static List<ParsedNdefRecord> parse(NdefMessage message) {
        return getRecords(message.getRecords());
    }

    static List<ParsedNdefRecord> getRecords(NdefRecord[] records) {
        List<ParsedNdefRecord> elements = new ArrayList<>();

        for (final NdefRecord record : records) {
            if (UriRecord.isUri(record)) {
                elements.add(UriRecord.parse(record));
            } else if (TextRecord.isText(record)) {
                elements.add(TextRecord.parse(record));
            } else if (SmartPoster.isPoster(record)) {
                elements.add(SmartPoster.parse(record));
            } else {
                elements.add(new ParsedNdefRecord() {
                    public String str() {
                        return new String(record.getPayload());
                    }
                });
            }
        }

        return elements;
    }
}

