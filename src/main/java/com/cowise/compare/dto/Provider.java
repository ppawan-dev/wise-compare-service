package com.cowise.compare.dto;

import java.util.List;

public record Provider(
        List<Quote> quotes,
        String alias,
        Integer id,
        Logos logos,
        String name,
        boolean partner,
        String type) {
}
