package ru.kpfu.itis.gnt.fakestore

import java.util.stream.Collectors

class SetConverter {
    companion object {
        fun intToString(intSet : Set<Int>) : Set<String> {
            return intSet.stream()
                .map { s -> s.toString() }
                .collect(Collectors.toSet()) as Set<String>
        }
        fun stringToInt(stringSet : Set<String>) : Set<Int> {
            return stringSet.stream()
                .map { s -> s.toInt() }
                .collect(Collectors.toSet()) as Set<Int>
        }
    }
}
