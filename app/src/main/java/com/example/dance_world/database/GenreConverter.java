package com.example.dance_world.database;

import androidx.room.TypeConverter;

import com.example.dance_world.database.entities.Festival;

import java.util.ArrayList;
import java.util.List;

public class GenreConverter {
    @TypeConverter
    public List<String> gettingListFromString(String genreIds) {
        List<String> list = new ArrayList<>();

        String[] array = genreIds.split(",");

        for (String s : array) {
            if (!s.isEmpty()) {
                list.add(s);
            }
        }
        return list;
    }

    @TypeConverter
    public String writingStringFromList(List<String> list) {
        String genreIds = "";
        for (String i : list) {
            genreIds += "," + i;
        }
        return genreIds;
    }}
