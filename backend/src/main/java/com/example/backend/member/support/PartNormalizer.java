package com.example.backend.member.support;

import java.util.Map;

public final class PartNormalizer {
	private static final Map<String, String> PART_MAP = Map.ofEntries(
		Map.entry("ai", "AI"),
		Map.entry("인공지능", "AI"),
		Map.entry("front-end", "FE"),
		Map.entry("frontend", "FE"),
		Map.entry("front end", "FE"),
		Map.entry("프론트", "FE"),
		Map.entry("fe", "FE"),
		Map.entry("back-end", "BE"),
		Map.entry("backend", "BE"),
		Map.entry("back end", "BE"),
		Map.entry("백엔드", "BE"),
		Map.entry("be", "BE"),
		Map.entry("app", "APP"),
		Map.entry("앱", "APP"),
		Map.entry("모바일", "APP"),
		Map.entry("design", "DESIGN"),
		Map.entry("디자인", "DESIGN"),
		Map.entry("uiux", "DESIGN"),
		Map.entry("ui/ux", "DESIGN")
	);

	private PartNormalizer() {
	}

	public static String normalize(String raw) {
		if (raw == null || raw.trim().isEmpty()) {
			throw new IllegalArgumentException("part is required");
		}

		String key = raw.trim().toLowerCase();
		String mapped = PART_MAP.get(key);
		if (mapped != null) {
			return mapped;
		}
		return raw.trim();
	}
}

