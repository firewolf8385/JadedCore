/*
 * This file is part of JadedCore, licensed under the MIT License.
 *
 *  Copyright (c) JadedMC
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package net.jadedmc.jadedutils.chat;

import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A collection of String-related utilities.
 */
public class StringUtils {
    private static final char[] SMALL_CAPS_ALPHABET = "ᴀʙᴄᴅᴇꜰɢʜɪᴊᴋʟᴍɴᴏᴩqʀꜱᴛᴜᴠᴡxyᴢ".toCharArray();

    /**
     * Join multiple strings together into one string.
     * @param args List of strings to join together.
     * @param separator What should be between each string.
     * @return Combined string.
     */
    public static String join(final List<String> args, final String separator) {
        final StringBuilder temp = new StringBuilder();

        for(final String str : args) {
            if(!temp.toString().equals("")) {
                temp.append(separator);
            }

            temp.append(str);
        }

        return temp.toString();
    }

    /**
     * Join multiple strings together into one string.
     * @param args Array of strings to join together.
     * @param separator What should be between each string.
     * @return Combined string.
     */
    public static String join(final String[] args, final String separator) {
        return join(Arrays.asList(args), separator);
    }

    /**
     * Translate a message using legacy ChatColor enum.
     * Important for everything non-chat related, like item names and scoreboards.
     * @param message Message to translate.
     * @return Translated message.
     */
    public static String translateLegacyMessage(String message) {
        Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            String color = message.substring(matcher.start() + 1, matcher.end());
            message = message.replace("&" + color, ChatColor.of(color) + "");
            matcher = pattern.matcher(message);
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Convert a String to small caps.
     * @param text String to convert.
     * @return Converted String.
     */
    public static String toSmallCaps(@NotNull String text) {
        text = text.toLowerCase();

        final int length = text.length();
        final StringBuilder smallCaps = new StringBuilder(length);
        for(int i = 0; i < length; i++) {
            char c = text.charAt(i);
            if(c >= 'a' && c <= 'z') {
                smallCaps.append(SMALL_CAPS_ALPHABET[c - 'a']);
            } else {
                smallCaps.append(c);
            }
        }
        return smallCaps.toString();
    }

    /**
     * Convert an integer to Roman Numerals
     * @param input Integer to convert.
     * @return Roman Numeral of the integer.
     */
    public static String toRomanNumeral(int input) {
        if (input < 1 || input > 3999)
            return "Invalid Roman Number Value";
        String s = "";
        while (input >= 1000) {
            s += "M";
            input -= 1000;        }
        while (input >= 900) {
            s += "CM";
            input -= 900;
        }
        while (input >= 500) {
            s += "D";
            input -= 500;
        }
        while (input >= 400) {
            s += "CD";
            input -= 400;
        }
        while (input >= 100) {
            s += "C";
            input -= 100;
        }
        while (input >= 90) {
            s += "XC";
            input -= 90;
        }
        while (input >= 50) {
            s += "L";
            input -= 50;
        }
        while (input >= 40) {
            s += "XL";
            input -= 40;
        }
        while (input >= 10) {
            s += "X";
            input -= 10;
        }
        while (input >= 9) {
            s += "IX";
            input -= 9;
        }
        while (input >= 5) {
            s += "V";
            input -= 5;
        }
        while (input >= 4) {
            s += "IV";
            input -= 4;
        }
        while (input >= 1) {
            s += "I";
            input -= 1;
        }
        return s;
    }
}